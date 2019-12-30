package club.crabglory.www.data.helper;

import android.text.TextUtils;
import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.data.R;
import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.data.model.db.Book_Table;
import club.crabglory.www.data.model.net.BookRspModel;
import club.crabglory.www.data.model.net.RspModel;
import club.crabglory.www.data.netkit.NetKit;
import club.crabglory.www.data.netkit.RemoteService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDataHelper {
    public static void refreshBooks(BookRspModel model, DataSource.Callback<List<Book>> callback) {
        Log.e("BookDataHelper", model.toString());
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


    public static void upBook(Book book, final DataSource.Callback<String> callback) {
        // fixme just fot local test
        DbHelper.save(Book.class, book);
        callback.onDataLoaded("ok");
        // fixme just fot local test
//        String s = FileDataHelper.fetchBackgroundFile(book.getImage());
//        if (TextUtils.isEmpty(s)) {
//            callback.onDataNotAvailable(R.string.error_form_avatar);
//            return;
//        }
//        book.setImage(s);
//        final RemoteService remote = NetKit.remote();
//        Call<RspModel<Book>> call = remote.upBook(book);
//        call.enqueue(new Callback<RspModel<Book>>() {
//            @Override
//            public void onResponse(Call<RspModel<Book>> call, Response<RspModel<Book>> response) {
//                RspModel<Book> body = response.body();
//                if (body != null) {
//                    Book book = body.getResult();
//                    if (book != null) {
//                        DbHelper.save(Book.class, book);
//                        callback.onDataLoaded("ok");
//                        return;
//                    }
//                }
//                NetKit.decodeRep(body, callback);
//            }
//
//            @Override
//            public void onFailure(Call<RspModel<Book>> call, Throwable t) {
//                Log.e("BookDataHelper", "book refreshBooks");
//                call.cancel();
//                callback.onDataNotAvailable(R.string.error_data_network_error);
//            }
//        });
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
            Log.e("BookDataHelper", "book refreshBooks");
            call.cancel();
            callback.onDataNotAvailable(R.string.error_data_network_error);
        }
    }


}
