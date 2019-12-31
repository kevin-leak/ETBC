package club.crabglory.www.data.helper;

import android.util.Log;

import java.util.List;

import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.data.R;
import club.crabglory.www.data.model.db.Live;
import club.crabglory.www.data.model.net.RspModel;
import club.crabglory.www.data.netkit.NetKit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataHelper {
    public static void getLive(String displayId, DataSource.Callback<List<Live>> callback) {
        Call<RspModel<List<Live>>> call = NetKit.remote().pullLive(displayId);
        // 异步的请求
        call.enqueue(new LiveRspCallback(callback));
    }

    private static class LiveRspCallback implements Callback<RspModel<List<Live>>> {
        private DataSource.Callback<List<Live>> callback;

        public LiveRspCallback(DataSource.Callback<List<Live>> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<RspModel<List<Live>>> call, Response<RspModel<List<Live>>> response) {
            RspModel<List<Live>> body = response.body();
            if (body != null && body.isSuccess()) {
                List<Live> lives = body.getResult();
                if (lives != null) {
                    DbHelper.save(Live.class, lives.toArray(new Live[0]));
                } else {
                    NetKit.decodeRep(body, callback);
                }
            }else {
                if (body != null)
                    NetKit.decodeRep(body, callback);
            }
        }

        @Override
        public void onFailure(Call<RspModel<List<Live>>> call, Throwable t) {
            Log.e("LiveDataHelper", "live refreshBooks");
            call.cancel();
            callback.onDataNotAvailable(R.string.error_data_network_error);
        }
    }
}
