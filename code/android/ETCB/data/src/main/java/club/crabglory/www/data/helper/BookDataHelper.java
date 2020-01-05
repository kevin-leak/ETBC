package club.crabglory.www.data.helper;

import android.support.annotation.NonNull;
import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.IOException;
import java.util.List;

import club.crabglory.www.common.Application;
import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.common.utils.NetUtils;
import club.crabglory.www.data.R;
import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.data.model.db.Book_Table;
import club.crabglory.www.data.model.net.MaterialRspModel;
import club.crabglory.www.data.model.net.RspModel;
import club.crabglory.www.data.netkit.NetKit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDataHelper {

    private static final String TAG = "BookDataHelper";

    // 使用在book在recycle view中，根据type类型来pull
    public static void getBooks(MaterialRspModel model, DataSource.Callback<List<Book>> callback) {
        Log.e(TAG, model.toString());
        Call<RspModel<List<Book>>> call = NetKit.remote().pullBook(model);
        call.enqueue(new BookRspCallback(callback));
    }

    public static Book getFromLocalByID(String goodsId) {
        Book book = SQLite.select().from(Book.class)
                .where(Book_Table.id.eq(goodsId))
                .querySingle();
        return book;
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
//                Log.e("BookDataHelper", "book getBooks");
//                call.cancel();
//                callback.onDataNotAvailable(R.string.error_data_network_error);
//            }
//        });
    }

    // 使用在BookShop中，因为没有缓存所以必须写一个单独的
    public static void getBookByID(String bookId, DataSource.Callback<Book> callback) {
        // 优先从网上拉取
        Call<RspModel<Book>> call = NetKit.remote().pullBookByID(bookId);
        try {
            RspModel<Book> body = call.execute().body();
            Book result;
            if (body != null && body.isSuccess()) {
                result = body.getResult();
            } else {
                callback.onDataNotAvailable(R.string.error_data_network_error);
                result = getFromLocalByID(bookId);
            }
            callback.onDataLoaded(result);
        } catch (IOException e) {
            e.printStackTrace();
            callback.onDataNotAvailable(R.string.error_data_network_error);
            callback.onDataLoaded(getFromLocalByID(bookId));
        }
    }

    public static void search(MaterialRspModel rspModel, DataSource.Callback<List<Book>> callback) {
        // 优先从网上拉取
        Call<RspModel<List<Book>>> call = NetKit.remote().pullBook(rspModel);
        try {
            RspModel<List<Book>> body = call.execute().body();
            List<Book> result;
            if (body != null && body.isSuccess()) {
                result = body.getResult();
            } else {
                callback.onDataNotAvailable(R.string.error_data_network_error);
                result = getFromLocal(rspModel);
            }
            callback.onDataLoaded(result);
        } catch (IOException e) {
            e.printStackTrace();
            callback.onDataNotAvailable(R.string.error_data_network_error);
            callback.onDataLoaded(getFromLocal(rspModel));
        }
    }

    // 先名字的模糊查询
    private static List<Book> getFromLocal(MaterialRspModel rspModel) {
        List<Book> list;
        list = SQLite.select().from(Book.class)
                .where(Book_Table.name.like("%" + rspModel.getText() + "%"))
                .queryList();
        Log.e(TAG, "get from local" + list.size());
        return list;
    }

    public static boolean deleteBook(String bookId, DataSource.Callback<String> callback) {
        if (NetUtils.isNetworkConnected(Application.Companion.getInstance())) {
            deleteFromLocal(bookId);
            if (!deleteFormNet(bookId))
                callback.onDataNotAvailable(R.string.error_data_network_error);
            else
                return false;
        } else {
            callback.onDataNotAvailable(R.string.error_data_network_error);
            return false;
        }
        return true;
    }

    private static boolean deleteFormNet(String bookId) {

        Call<RspModel<String>> call = NetKit.remote().deleteBook(bookId);
        try {
            RspModel<String> body = call.execute().body();
            if (body == null || !body.getResult().equals("ok")) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static void deleteFromLocal(String bookId) {
        Book book = getFromLocalByID(bookId);
        DbHelper.delete(Book.class, book);
    }

    private static class BookRspCallback implements Callback<RspModel<List<Book>>> {
        private DataSource.Callback<List<Book>> callback;

        BookRspCallback(DataSource.Callback<List<Book>> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(@NonNull Call<RspModel<List<Book>>> call, Response<RspModel<List<Book>>> response) {
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
        public void onFailure(Call<RspModel<List<Book>>> call, @NonNull Throwable t) {
            Log.e("BookDataHelper", "book getBooks");
            call.cancel();
            callback.onDataNotAvailable(R.string.error_data_network_error);
        }
    }


}
