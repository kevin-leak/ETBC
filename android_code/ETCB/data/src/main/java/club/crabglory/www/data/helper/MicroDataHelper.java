package club.crabglory.www.data.helper;

import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.util.List;

import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.data.R;
import club.crabglory.www.data.model.db.Live;
import club.crabglory.www.data.model.db.Live_Table;
import club.crabglory.www.data.model.db.Micro;
import club.crabglory.www.data.model.db.Micro_Table;
import club.crabglory.www.data.model.net.RspModel;
import club.crabglory.www.data.netkit.NetKit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MicroDataHelper {
    public static void getMicro(String displayId, DataSource.Callback<List<Micro>> callback) {
        Call<RspModel<List<Micro>>> call = NetKit.remote().pullMicro(displayId);
        // 异步的请求
        call.enqueue(new MicroRspCallback(callback));
    }

    // 从本地拉取
    public static void getFromLocal(String displayId, QueryTransaction.QueryResultListCallback<Micro> callback) {
        SQLite.select().from(Micro.class)
                .where(Micro_Table.upper_id.eq(displayId))
                .orderBy(Live_Table.createAt, false)
                .async()
                .queryListResultCallback(callback)
                .execute();
    }

    private static class MicroRspCallback implements Callback<RspModel<List<Micro>>> {
        private DataSource.Callback<List<Micro>> callback;

        public MicroRspCallback(DataSource.Callback<List<Micro>> callback) {

            this.callback = callback;
        }

        @Override
        public void onResponse(Call<RspModel<List<Micro>>> call, Response<RspModel<List<Micro>>> response) {
            RspModel<List<Micro>> body = response.body();
            if (body != null && body.isSuccess()) {
                List<Micro> micros = body.getResult();
                if (micros != null) {
                    DbHelper.save(Micro.class, micros.toArray(new Micro[0]));
                    // 不用直接回调，在DBHelper存储的时候回进行通知
                } else {
                    NetKit.decodeRep(body, callback);
                }
            } else {
                if (body != null)
                    NetKit.decodeRep(body, callback);
            }
        }

        @Override
        public void onFailure(Call<RspModel<List<Micro>>> call, Throwable t) {
            Log.e("MicroDataHelper", "micro getBooks");
            call.cancel();
            callback.onDataNotAvailable(R.string.error_data_network_error);
        }
    }
}
