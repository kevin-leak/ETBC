package club.crabglory.www.factory.repository;


import java.util.List;

import club.crabglory.www.data.helper.GoodsDataHelper;
import club.crabglory.www.factory.contract.GoodsDataSource;
import club.crabglory.www.data.model.db.Goods;


public class GoodsRepository extends BaseDbRepository<Goods> implements GoodsDataSource {


    private boolean state;

    public GoodsRepository(boolean state) {
        super();
        this.state = state;
    }


    @Override
    public void load(SucceedCallback<List<Goods>> callback) {
        super.load(callback);
        GoodsDataHelper.getFormLocal(state,  this);
    }

    @Override
    public boolean isRequired(Goods goods) {
        // 来源三种：推送的数据、网络加载的数据、本地数据库查询的数据
        // 都要进行更新。
        return goods.isState() == state && goods.getCount() > 0;
    }
}
