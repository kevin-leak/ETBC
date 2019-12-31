package club.crabglory.www.data.helper;

import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.data.R;
import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.data.model.db.Goods;
import club.crabglory.www.data.model.db.Goods_Table;
import club.crabglory.www.data.model.net.GoodsRspModel;
import club.crabglory.www.data.model.net.PayRspModel;
import club.crabglory.www.data.model.net.RspModel;
import club.crabglory.www.data.model.persistence.Account;
import club.crabglory.www.data.netkit.NetKit;
import club.crabglory.www.data.netkit.RemoteService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoodsDataHelper {

    public static void refreshGoods(GoodsRspModel goodsRspModel, DataSource.Callback<List<Goods>> callback) {
        Call<RspModel<List<Goods>>> call = NetKit.remote().getOwnerGoods(goodsRspModel);
        // 异步的请求
        call.enqueue(new GoodsRspCallback(callback));
    }

    // 书籍被购买的时候，反馈的还是书籍本省信息，通知Db更新
    // fixme 注意在购买的时候，后台数据是实时更新的，需要注意产品是否下线
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
//            if (body != null && body.isSuccess()) {
//                List<Goods> result = body.getResult();
//                if (result != null && result.size() > 0) {
//                    DbHelper.updateGoods(Goods.class, result.get(0));
//                    return true;
//                } else {
//                    NetKit.decodeRep(body, callback);
//                    return false;
//                }
//            }else {
//                if (body != null)
//                    NetKit.decodeRep(body, callback);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e("goodsHelper", "IOException: " + e.getMessage());
//        }
//        return false;
    }

    public static void getFormLocal(boolean state, QueryTransaction.QueryResultListCallback<Goods> callback) {
        SQLite.select().from(Goods.class)
                .where(Goods_Table.customer_id.eq(Account.getUserId()))
                .and(Goods_Table.state.eq(state))
                .orderBy(Goods_Table.createAt, false)
                .async()
                .queryListResultCallback(callback)
                .execute();
    }


    private static class GoodsRspCallback implements Callback<RspModel<List<Goods>>> {
        private DataSource.Callback<List<Goods>> callback;

        public GoodsRspCallback(DataSource.Callback<List<Goods>> callback) {
            this.callback = callback;
        }


        @Override
        public void onResponse(Call<RspModel<List<Goods>>> call, Response<RspModel<List<Goods>>> response) {
            RspModel<List<Goods>> body = response.body();
            if (body != null) {
                List<Goods> goodsList = body.getResult();
                if (goodsList != null) {
                    DbHelper.save(Goods.class, goodsList.toArray(new Goods[0]));
                    // 不需要直接回调，有存通知
                } else {
                    NetKit.decodeRep(body, callback);
                }
            }
        }

        @Override
        public void onFailure(Call<RspModel<List<Goods>>> call, Throwable t) {
            Log.e("GoodsDataHelper", "goods refreshBooks");
            call.cancel();
            callback.onDataNotAvailable(R.string.error_data_network_error);
        }
    }
}
