package club.crabglory.www.factory.presenter.book;

import java.util.List;

import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.common.basic.presenter.BasePresenter;
import club.crabglory.www.data.helper.BookDataHelper;
import club.crabglory.www.data.helper.GoodsDataHelper;
import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.data.helper.DbHelper;
import club.crabglory.www.data.model.db.Goods;
import club.crabglory.www.factory.Factory;
import club.crabglory.www.factory.R;
import club.crabglory.www.factory.contract.BooksShopContract;

public class BookShopPresenter extends BasePresenter<BooksShopContract.View>
        implements BooksShopContract.Presenter, DataSource.Callback<Book> {
    private String goodsId;

    public BookShopPresenter(BooksShopContract.View view) {
        super(view);
    }

    @Override
    public void onDataLoaded(final Book books) {
        getView().getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mView.refreshData(books);
            }
        });
    }

    @Override
    public void onDataNotAvailable(int strRes) {
        mView.showError(strRes);
    }

    @Override
    public void getBook(final String goodsId) {
        this.goodsId = goodsId;
        BookDataHelper.getBookData(goodsId, BookShopPresenter.this);
    }

    @Override
    public void saveCarGoods(Book book, int sales) {
        Goods goods = book.toCarGoods(sales);
        if (goods == null) {
            mView.showError(R.string.error_form_data_more);
            return;
        }
        // 本地加入，不进行网络请求
        DbHelper.save(Goods.class, goods);
        mView.addCarSuccess();
    }

    @Override
    public void prePay(Book book, int sales) {
        final Goods goods = book.toGoods(sales);
        if (goods == null) {
            mView.showError(R.string.error_form_data_more);
            return;
        }
        Factory.Companion.runOnAsync(new Runnable() {
            @Override
            public void run() {
                if (GoodsDataHelper.pay(BookShopPresenter.this, goods)) {
                    mView.payDone();
                }
            }
        });
    }
}
