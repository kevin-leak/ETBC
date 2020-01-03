package club.crabglory.www.factory.contract;

import java.util.List;

import club.crabglory.www.common.basic.contract.BaseContract;
import club.crabglory.www.data.model.db.Goods;

public interface GoodsCarContract {

    // 什么都不需要额外定义，开始就是调用start即可
    interface Presenter extends BaseContract.Presenter {
        void emptyCart(List<Goods> list);

        void prePay(double parseInt, List<Goods> checkGoods);

        // 如果发生了变化，则在销毁Activity的时候，要进行保存
        void saveChange(List<Goods> items);

        void preRefresh(boolean isMore);
    }

    // 都在基类完成了
    interface View extends BaseContract.RecyclerView<Presenter, Goods> {
        // 指的是delete与pay
        void dealSuccess();
    }

    interface CheckGoodsListener {
        void checkBuy(Goods goods, float addSum, boolean isAddNew);

    }
}
