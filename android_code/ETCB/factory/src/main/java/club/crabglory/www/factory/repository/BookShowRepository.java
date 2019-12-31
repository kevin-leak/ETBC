package club.crabglory.www.factory.repository;


import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import club.crabglory.www.factory.contract.BookDataSource;
import club.crabglory.www.data.model.persistence.Account;
import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.data.model.db.Book_Table;


public class BookShowRepository extends BaseDbRepository<Book> implements BookDataSource {

    private int type;
    private int times = 0;

    public BookShowRepository(int type) {
        super();
        this.type = type;
    }

    @Override
    public void load(SucceedCallback<List<Book>> callback) {
        super.load(callback);
        /*
         * 1. 数量限制：daily 4条，其他30
         * 2. 类型限制：daily 和 Random 没有累别就按时间显示就行
         *    特别：MY_UP 要另外建立查询，
         * */

        Log.e("BookShowRepository", "type is :" + type + "Account.getUserId : " + Account.getUserId());
        int count = 30;
        if (type == Book.TYPE_DAILY) count = 4;
        // fixme 区分buy 与 up
        if (Book.TYPE_MY_UP == type) {
            SQLite.select().from(Book.class)
                    .where(Book_Table.upper_id.eq(Account.getUserId()))
                    .and(Book_Table.count.greaterThan(0))
                    .async()
                    .queryListResultCallback(this)
                    .execute();
        } else if (Book.TYPE_DAILY == type || Book.TYPE_RANDOM == type) {
            int offset = count * times;
            if (Book.TYPE_RANDOM == type) offset += 4;
            SQLite.select().from(Book.class)
                    .where(Book_Table.count.greaterThan(0))
                    .offset(offset).limit(count)
                    .async()
                    .queryListResultCallback(this)
                    .execute();
        } else {
            SQLite.select().from(Book.class)
                    .where(Book_Table.type.eq(type))
                    .and(Book_Table.count.greaterThan(0))
                    .async()
                    .queryListResultCallback(this)
                    .execute();

        }
        times++;
    }

    @Override
    public boolean isRequired(Book book) {
        // 来源三种：推送的数据、网络加载的数据、本地数据库查询的数据
        // 都要进行更新。
        return book.getCount() > 0
                && (book.getUpper() == null
                    || !book.getUpper().getId().equals(Account.getUserId()) || type == Book.TYPE_MY_UP);
    }
}
