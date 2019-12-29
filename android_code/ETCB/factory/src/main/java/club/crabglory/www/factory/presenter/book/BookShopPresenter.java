package club.crabglory.www.factory.presenter.book;

import android.util.Log;

import java.util.List;

import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.common.basic.presenter.BasePresenter;
import club.crabglory.www.data.DataKit;
import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.data.model.db.DbHelper;
import club.crabglory.www.data.model.db.Goods;
import club.crabglory.www.data.model.net.BookRspModel;
import club.crabglory.www.factory.R;
import club.crabglory.www.factory.contract.BooksShopContract;

public class BookShopPresenter extends BasePresenter<BooksShopContract.View>
        implements BooksShopContract.Presenter, DataSource.Callback<List<Book>> {
    public BookShopPresenter(BooksShopContract.View view) {
        super(view);
    }

    @Override
    public void onDataLoaded(final List<Book> books) {
        if (books.size() <= 0) return;
        getView().getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("BookShopPresenter", books.get(0).toString());
                mView.refreshData(books.get(0));
            }
        });
    }

    @Override
    public void onDataNotAvailable(int strRes) {
        mView.showError(strRes);
    }

    @Override
    public void getGoods(final String goodsId) {
        DataKit.Companion.runOnAsysc(new Runnable() {
            @Override
            public void run() {
                BookRspModel model = new BookRspModel();
                model.setId(goodsId);
                BookHelper.getLocal(goodsId, BookShopPresenter.this);
                BookHelper.refreshBooks(model, BookShopPresenter.this);
            }
        });
    }

    @Override
    public void saveCarGoods(Book book, int sales) {
        Goods goods = book.toCarGoods(sales);
        if (goods == null) {
            mView.showError(R.string.error_form_data_more);
            return;
        }
        DbHelper.save(Goods.class, goods);
        mView.addCarSuccess();
    }

    @Override
    public void prePay(Book book, int sales) {
        Goods goods = book.toGoods(sales);
        if (goods == null) {
            mView.showError(R.string.error_form_data_more);
            return;
        }
        DbHelper.updateGoods(Goods.class, goods);
        // todo 后面支付后回调
        mView.addCarSuccess();
    }
}
