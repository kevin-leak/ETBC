package club.crabglory.www.factory.presenter.book;

import android.support.v7.util.DiffUtil;
import android.util.Log;

import java.util.List;

import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.common.basic.presenter.RecyclerSourcePresenter;
import club.crabglory.www.common.widget.recycler.RecyclerAdapter;
import club.crabglory.www.data.helper.GoodsDataHelper;
import club.crabglory.www.factory.Factory;
import club.crabglory.www.factory.contract.GoodsDataSource;
import club.crabglory.www.data.helper.DbHelper;
import club.crabglory.www.data.model.db.Goods;
import club.crabglory.www.data.model.db.utils.DiffUiDataCallback;
import club.crabglory.www.factory.repository.GoodsRepository;
import club.crabglory.www.factory.contract.GoodsCarContract;

public class GoodsCarPresenter extends
        RecyclerSourcePresenter<Goods, Goods, GoodsDataSource, GoodsCarContract.View>
        implements GoodsCarContract.Presenter, DataSource.Callback<List<Goods>> {


    public GoodsCarPresenter(GoodsCarContract.View view) {
        super(new GoodsRepository(false), view);
    }

    @Override
    public void onDataNotAvailable(int strRes) {
        mView.showError(strRes);
    }

    @Override
    public void onDataLoaded(final List<Goods> goodsList) {
        RecyclerAdapter<Goods> adapter = mView.getRecyclerAdapter();
        List<Goods> old = adapter.getItems();
        // 进行数据对比
        DiffUtil.Callback callback = new DiffUiDataCallback<>(old, goodsList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        refreshData(result, goodsList);
    }

    @Override
    public void emptyCart(List<Goods> list) {
        DbHelper.delete(Goods.class, list.toArray(new Goods[0]));
        // 无论是否删除都执行
        mView.dealSuccess();
    }


    @Override
    public void prePay(double parseInt, final List<Goods> checkGoods){
        Factory.Companion.runOnAsync(new Runnable() {
            @Override
            public void run() {
                if (GoodsDataHelper.pay(GoodsCarPresenter.this, checkGoods.toArray(new Goods[0]))) {
                    mView.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mView.dealSuccess();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void saveChange(List<Goods> items) {
        DbHelper.save(Goods.class, items.toArray(new Goods[0]));
    }

    @Override
    public void preRefresh(boolean isMore) {
        // 因为只存储在本地，所以只需要进行本地的加载
        start();
    }
}
