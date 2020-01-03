package club.crabglory.www.factory.repository;


import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import javax.security.auth.login.LoginException;

import club.crabglory.www.data.helper.DbHelper;
import club.crabglory.www.data.model.net.MaterialRspModel;
import club.crabglory.www.data.model.net.PayRspModel;
import club.crabglory.www.factory.contract.BookDataSource;
import club.crabglory.www.data.model.persistence.Account;
import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.data.model.db.Book_Table;


public class BookShowRepository extends BaseDbRepository<Book> implements BookDataSource {

    private int type;
    private int times = 1;

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
        if (type == MaterialRspModel.TYPE_SEARCH) return; // 搜索不进初始化
        Log.e("BookShowRepository", "type is :" + type + "Account.getUserId : " + Account.getUserId());
        int count = 30;
        if (MaterialRspModel.TYPE_MY_UP == type) {
            SQLite.select().from(Book.class)
                    .where(Book_Table.upper_id.eq(Account.getUserId()))
                    .and(Book_Table.count.greaterThan(0))
                    .limit(count * times)
                    .async()
                    .queryListResultCallback(this)
                    .execute();
        } else if (MaterialRspModel.TYPE_DAILY == type || MaterialRspModel.TYPE_RANDOM == type) {
            int offset = 0;
            if (MaterialRspModel.TYPE_RANDOM == type) {
                offset += 4;
                count *= times;
            } else {
                count = 4;
            }
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
    public void onDataSave(Book... list) {
        boolean isChanged = false;
        // 当数据库数据变更的操作
        for (Book data : list) {
            if (isRequired(data)) {
                insertOrUpdate(data);
                isChanged = true;
            }else if (data.getCount() <= 0){
                dataList.remove(data);
                isChanged = true;
            }
        }
        if (list.length == 0) isChanged = true; // 为了避免第一次加载本地没有数据的情况
        // 有数据变更，则进行界面刷新
        if (isChanged)
            notifyDataChange();
    }

    @Override
    public boolean isRequired(Book book) {
        // 来源三种：推送的数据、网络加载的数据、本地数据库查询的数据
        // 如果是个人展示页面，无所谓数量
        // 如果是其他的展示页面，必须是数量大于0且同时不是当前用户所发表
        if (type == MaterialRspModel.TYPE_MY_UP) {
            return true;
        }
        if (book.getUpper().getId().equals(Account.getUser().getId())) return false;
        return book.getCount() > 0;
    }
}
