package club.crabglory.www.factory.presenter.book;

import android.support.v7.util.DiffUtil;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.common.basic.presenter.RecyclerSourcePresenter;
import club.crabglory.www.common.widget.recycler.RecyclerAdapter;
import club.crabglory.www.data.helper.BookDataHelper;
import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.data.model.db.utils.DiffUiDataCallback;
import club.crabglory.www.factory.contract.BookDataSource;
import club.crabglory.www.data.model.net.MaterialRspModel;
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
        // 取消dialog
        if (mSource != null)
            mSource.load(this);
    }

    @Override
    public void onDataLoaded(List<Book> books) {
        if (type == MaterialRspModel.TYPE_DAILY) {   // 限制Daily的更新大小
            for (int i = 0; i < books.size(); i++)
                if (i >= 4) books.remove(i);
        }
        RecyclerAdapter<Book> adapter = mView.getRecyclerAdapter();
        List<Book> old = adapter.getItems();
        DiffUtil.Callback callback = new DiffUiDataCallback<>(old, books);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        refreshData(result, books);
    }

    @Override
    public void toRefresh(final boolean isMore) {
        // 包含非book类型：daily,random,my_up,my_buy
        MaterialRspModel materialRspModel = new MaterialRspModel();
        materialRspModel.setType(type);
        materialRspModel.setMore(isMore);
        BookDataHelper.getBooks(materialRspModel, BookShowPresenter.this);
    }

    @Override
    public void searchBook(String s) {
        MaterialRspModel materialRspModel = new MaterialRspModel();
        materialRspModel.setText(s);
        materialRspModel.setType(type);
        materialRspModel.setMore(false);
        BookDataHelper.search(materialRspModel, BookShowPresenter.this);
    }


    @Override
    public void onDataNotAvailable(final int strRes) {
        mView.showError(strRes);
        // 本地再进行一次刷新
        start();
    }
}
