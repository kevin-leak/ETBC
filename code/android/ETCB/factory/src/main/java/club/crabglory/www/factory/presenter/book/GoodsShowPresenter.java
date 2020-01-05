package club.crabglory.www.factory.presenter.book;

import android.support.v7.util.DiffUtil;
import android.util.Log;

import java.util.List;

import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.common.basic.presenter.RecyclerSourcePresenter;
import club.crabglory.www.common.widget.recycler.RecyclerAdapter;
import club.crabglory.www.data.model.net.MaterialRspModel;
import club.crabglory.www.factory.contract.GoodsDataSource;
import club.crabglory.www.data.helper.GoodsDataHelper;
import club.crabglory.www.data.helper.DbHelper;
import club.crabglory.www.data.model.db.Goods;
import club.crabglory.www.data.model.db.utils.DiffUiDataCallback;
import club.crabglory.www.factory.repository.GoodsRepository;
import club.crabglory.www.factory.contract.GoodsShowContract;

public class GoodsShowPresenter extends
        RecyclerSourcePresenter<Goods, Goods, GoodsDataSource, GoodsShowContract.View>
        implements GoodsShowContract.Presenter, DataSource.Callback<List<Goods>> {


    private int type;

    public GoodsShowPresenter(GoodsShowContract.View view, int type) {
        super(new GoodsRepository(true), view);
        this.type = type;
    }


    @Override
    public void onDataLoaded(final List<Goods> goodsList) {
        super.onDataLoaded(goodsList);
        // 数据到来了是追加还是替代，头部追加还是尾部追加
        Log.e("GoodsShowPresenter", "" + goodsList.size());
        RecyclerAdapter<Goods> adapter = mView.getRecyclerAdapter();
        List<Goods> old = adapter.getItems();
        // 进行数据对比
        DiffUtil.Callback callback = new DiffUiDataCallback<>(old, goodsList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        refreshData(result, goodsList);

    }

    @Override
    public void toRefresh(final boolean isMore) {
        MaterialRspModel rspModel = new MaterialRspModel();
        rspModel.setType(type);
        rspModel.setMore(isMore);
        GoodsDataHelper.refreshGoods(rspModel, GoodsShowPresenter.this);
    }

    @Override
    public void toDeleteGoods(List<Goods> listGoods) {
        if (listGoods.size() > 0) {
            // todo delete
            GoodsDataHelper.deleteGoods(listGoods, this);
        }
        mView.dealSuccess();
    }


    @Override
    public void onDataNotAvailable(final int strRes) {
        mView.showError(strRes);
        // 如果网络加载失败，这里本地再一次进行刷新
        start();
    }
}
