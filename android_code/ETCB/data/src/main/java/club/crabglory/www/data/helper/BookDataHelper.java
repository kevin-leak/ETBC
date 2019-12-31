package club.crabglory.www.data.helper;

import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.IOException;
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
        Call<RspModel<List<Book>>> call = NetKit.remote().refreshBook(model);
        call.enqueue(new BookRspCallback(callback));
    }

    public static void getFromLocal(String goodsId, DataSource.Callback<Book> callback) {
        Book books = SQLite.select().from(Book.class)
                .where(Book_Table.id.eq(goodsId))
                .querySingle();
        callback.onDataLoaded(books);
        callback.onDataNotAvailable(R.string.data_from_loacl);
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

    public static void getBookData(String bookId, DataSource.Callback<Book> callback) {
        // 优先从网上拉取
        Call<RspModel<Book>> call = NetKit.remote().pullBook(bookId);
        try {
            RspModel<Book> body = call.execute().body();
            if (body != null && body.isSuccess()) {
                Book result = body.getResult();
                callback.onDataLoaded(result);
            }else {
                callback.onDataNotAvailable(R.string.error_data_network_error);
                getFromLocal(bookId, callback);
            }
        } catch (IOException e) {
            e.printStackTrace();
            callback.onDataNotAvailable(R.string.error_data_network_error);
            getFromLocal(bookId, callback);
        }
    }

    private static class BookRspCallback implements Callback<RspModel<List<Book>>> {
        private DataSource.Callback<List<Book>> callback;

        public BookRspCallback(DataSource.Callback<List<Book>> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<RspModel<List<Book>>> call, Response<RspModel<List<Book>>> response) {
            RspModel<List<Book>> body = response.body();
            if (body != null && body.isSuccess()) {
                List<Book> booksList = body.getResult();
                if (booksList != null) {
                    DbHelper.save(Book.class, booksList.toArray(new Book[0]));
                    // 不需要直接回调
                } else {
                    NetKit.decodeRep(body, callback);
                }
            } else {
                if (body != null)
                    NetKit.decodeRep(body, callback);
            }
        }

        @Override
        public void onFailure(Call<RspModel<List<Book>>> call, Throwable t) {
            Log.e("BookDataHelper", "book refreshBooks");
            call.cancel();
            callback.onDataNotAvailable(R.string.error_data_network_error);
        }
    }


}
