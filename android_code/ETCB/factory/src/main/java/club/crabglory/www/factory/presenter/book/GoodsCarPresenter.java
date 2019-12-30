package club.crabglory.www.factory.presenter.book;

import android.support.v7.util.DiffUtil;
import android.util.Log;

import java.util.List;

import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.common.basic.presenter.RecyclerSourcePresenter;
import club.crabglory.www.common.widget.recycler.RecyclerAdapter;
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
        // todo 数据到来了是追加还是替代，头部追加还是尾部追加
        Log.e("GoodsCarPresenter", "" + goodsList.size());
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
        mView.dealSuccess();
    }


    @Override
    public void prePay(double parseInt, List<Goods> checkGoods) {
        // todo for pay
        // 回调删除就行
        DbHelper.updateGoods(Goods.class, checkGoods.toArray(new Goods[0]));
        mView.dealSuccess();
    }

    @Override
    public void saveChange(List<Goods> items) {
        DbHelper.save(Goods.class, items.toArray(new Goods[0]));
    }

    @Override
    public void preRefresh(boolean isMore) {
        start();
    }
}
