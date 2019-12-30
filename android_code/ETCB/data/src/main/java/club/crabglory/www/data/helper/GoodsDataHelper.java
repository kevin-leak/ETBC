package club.crabglory.www.data.helper;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.data.R;
import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.data.model.db.Goods;
import club.crabglory.www.data.model.net.GoodsRspModel;
import club.crabglory.www.data.model.net.PayRspModel;
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

    // 书籍被购买的时候，反馈的还是书籍本省信息，通知Db更新
    public static boolean pay(DataSource.Callback callback, Goods... goods) {
        // fixme just fot test
        DbHelper.updateGoods(Goods.class, goods);
        return true;
        // fixme just fot test

//        RemoteService remote = NetKit.remote();
//        List<PayRspModel> rspModels = new ArrayList<>();
//        for (Goods g : goods)
//            rspModels.add(g.toPayRspModel());
//        Call<RspModel<List<Goods>>> call = remote.payGoods(rspModels);
//        try {
//            RspModel<List<Goods>> body = call.execute().body();
//            if (body != null) {
//                List<Goods> result = body.getResult();
//                if (result != null && result.size() > 0) {
//                    DbHelper.updateGoods(Goods.class, result.get(0));
//                    return true;
//                } else {
//                    NetKit.decodeRep(body, callback);
//                    return false;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e("goodsHelper", "IOException: " + e.getMessage());
//        }
//        return false;
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
