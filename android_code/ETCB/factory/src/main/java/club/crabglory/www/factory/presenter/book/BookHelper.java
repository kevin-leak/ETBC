package club.crabglory.www.factory.presenter.book;

import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.data.model.db.Book_Table;
import club.crabglory.www.data.model.db.DbHelper;
import club.crabglory.www.data.model.db.Goods;
import club.crabglory.www.data.model.net.BookRspModel;
import club.crabglory.www.data.model.net.GoodsRspModel;
import club.crabglory.www.data.model.net.RspModel;
import club.crabglory.www.data.netkit.NetKit;
import club.crabglory.www.data.netkit.RemoteService;
import club.crabglory.www.factory.R;
import club.crabglory.www.factory.presenter.FileHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class BookHelper {
    static void refreshBooks(BookRspModel model, DataSource.Callback<List<Book>> callback) {
        Log.e("BookHelper", model.toString());
        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService remote = NetKit.remote();
        // 得到一个Call
        Call<RspModel<List<Book>>> call = remote.refreshBook(model);
        // 异步的请求
        call.enqueue(new BookRspCallback(callback));
    }

    public static void getLocal(String goodsId, DataSource.Callback<List<Book>> callback) {
        List<Book> books = SQLite.select().from(Book.class)
                .where(Book_Table.id.eq(goodsId))
                .queryList();
        callback.onDataLoaded(books);
    }



    public static void upBook(Book book, final DataSource.FailedCallback callback) {
        String s = FileHelper.fetchBackgroundFile(book.getImage());
        if (TextUtils.isEmpty(s)) {
            callback.onDataNotAvailable(R.string.error_form_avatar);
            return;
        }
        Log.e("BookHelp", "upBook");
        book.setImage(s);
        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService remote = NetKit.remote();
        // 得到一个Call
        Call<RspModel<Book>> call = remote.upBook(book);
        // 异步的请求
        call.enqueue(new Callback<RspModel<Book>>() {
            @Override
            public void onResponse(Call<RspModel<Book>> call, Response<RspModel<Book>> response) {
                // todo save to local db
            }

            @Override
            public void onFailure(Call<RspModel<Book>> call, Throwable t) {
                Log.e("BookHelper", "book refreshBooks");
                call.cancel();
                callback.onDataNotAvailable(R.string.error_data_network_error);
            }
        });
    }


    private static class BookRspCallback implements Callback<RspModel<List<Book>>> {
        private DataSource.Callback<List<Book>> callback;

        public BookRspCallback(DataSource.Callback<List<Book>> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<RspModel<List<Book>>> call, Response<RspModel<List<Book>>> response) {

        }

        @Override
        public void onFailure(Call<RspModel<List<Book>>> call, Throwable t) {
            Log.e("BookHelper", "book refreshBooks");
            call.cancel();
            callback.onDataNotAvailable(R.string.error_data_network_error);
        }
    }


}
