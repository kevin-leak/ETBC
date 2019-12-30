package club.crabglory.www.factory.presenter.book;

import android.util.Log;

import java.util.List;

import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.common.basic.presenter.RecyclerSourcePresenter;
import club.crabglory.www.data.helper.BookDataHelper;
import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.factory.Factory;
import club.crabglory.www.factory.contract.BookDataSource;
import club.crabglory.www.data.model.net.BookRspModel;
import club.crabglory.www.factory.repository.BookShowRepository;
import club.crabglory.www.factory.contract.BookShowContract;

public class BookShowPresenter extends
        RecyclerSourcePresenter<Book, Book, BookDataSource, BookShowContract.View>
        implements BookShowContract.Presenter, DataSource.Callback<List<Book>> {

    private int type;

    public BookShowPresenter(BookShowContract.View view, int type) {
        super(new BookShowRepository(type), view);
        this.mView = view;
        this.type = type;
    }

    @Override
    public void start() {
        if (mSource != null)
            mSource.load(this);
    }

    @Override
    public void onDataLoaded(List<Book> books) {
        super.onDataLoaded(books);
        // 数据到来了是追加还是替代，头部追加还是尾部追加
        refreshData(books);
        // 用来停止loading
        Log.e("BookShowPresenter", books.toString());
    }

    @Override
    public void toRefresh(final boolean isMore) {
        // 包含非book类型：daily,random,my_up,my_buy
        Factory.Companion.runOnAsync(new Runnable() {
            @Override
            public void run() {
                BookRspModel bookRspModel = new BookRspModel();
                bookRspModel.setType(type);
                bookRspModel.setMore(isMore);
                BookDataHelper.refreshBooks(bookRspModel, BookShowPresenter.this);
            }
        });
    }


    @Override
    public void onDataNotAvailable(final int strRes) {
        getView().getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mView.showError(strRes);
            }
        });
    }
}
