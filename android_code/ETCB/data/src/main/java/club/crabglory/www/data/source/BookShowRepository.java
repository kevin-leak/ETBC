package club.crabglory.www.data.source;

import java.util.List;

import club.crabglory.www.common.basic.model.BaseDbRepository;
import club.crabglory.www.data.contract.BookDataSource;
import club.crabglory.www.data.db.Book;

public class BookShowRepository extends BaseDbRepository<Book> implements BookDataSource {

    @Override
    public void load(SucceedCallback<List<Book>> callback) {
        super.load(callback);
        // todo 从本地加载数据
    }

    @Override
    public boolean isRequired(Book book) {
        return true;
    }
}
