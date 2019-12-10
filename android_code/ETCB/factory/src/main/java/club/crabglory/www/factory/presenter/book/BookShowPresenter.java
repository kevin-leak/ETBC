package club.crabglory.www.factory.presenter.book;

import club.crabglory.www.common.basic.presenter.BaseSourcePresenter;
import club.crabglory.www.data.db.Book;
import club.crabglory.www.data.contract.BookDataSource;
import club.crabglory.www.data.source.BookShowRepository;
import club.crabglory.www.factory.contract.BookShowContract;

public class BookShowPresenter extends BaseSourcePresenter<Book, Book, BookDataSource,BookShowContract.View>
        implements BookShowContract.Presenter {


    public BookShowPresenter(BookShowContract.View view) {
        super(new BookShowRepository(), view);
    }


}
