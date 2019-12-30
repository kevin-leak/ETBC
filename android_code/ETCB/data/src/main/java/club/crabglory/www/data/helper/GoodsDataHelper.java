package club.crabglory.www.data.helper;

import android.util.Log;

import java.util.List;

import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.data.R;
import club.crabglory.www.data.model.db.Goods;
import club.crabglory.www.data.model.net.GoodsRspModel;
import club.crabglory.www.data.model.net.RspModel;
import club.crabglory.www.data.netkit.NetKit;
import club.crabglory.www.data.netkit.RemoteService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoodsDataHelper {

    public static void refreshGoods(GoodsRspModel goodsRspModel, DataSource.Callback<List<Goods>> callback) {

        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService remote = NetKit.remote();
        // 得到一个Call
        Call<RspModel<List<Goods>>> call = remote.getOwnerGoods(goodsRspModel);
        // 异步的请求
        call.enqueue(new GoodsRspCallback(callback));

    }


    private static class GoodsRspCallback implements Callback<RspModel<List<Goods>>> {
        private DataSource.Callback<List<Goods>> callback;

        public GoodsRspCallback(DataSource.Callback<List<Goods>> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<RspModel<List<Goods>>> call, Response<RspModel<List<Goods>>> response) {

        }

        @Override
        public void onFailure(Call<RspModel<List<Goods>>> call, Throwable t) {
            Log.e("BookDataHelper", "goods refreshBooks");
            call.cancel();
            callback.onDataNotAvailable(R.string.error_data_network_error);
        }
    }
}
