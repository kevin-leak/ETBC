package club.crabglory.www.factory.repository;


import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import club.crabglory.www.factory.contract.GoodsDataSource;
import club.crabglory.www.data.model.persistence.Account;
import club.crabglory.www.data.model.db.Goods;
import club.crabglory.www.data.model.db.Goods_Table;


public class GoodsRepository extends BaseDbRepository<Goods> implements GoodsDataSource {


    private boolean state;

    public GoodsRepository(boolean state) {
        super();
        this.state = state;
    }

    @Override
    public void load(SucceedCallback<List<Goods>> callback) {
        super.load(callback);
        SQLite.select().from(Goods.class)
                .where(Goods_Table.customer_id.eq(Account.getUserId()))
                .and(Goods_Table.state.eq(state))
                .orderBy(Goods_Table.createAt, false)
                .async()
                .queryListResultCallback(this)
                .execute();
    }

    @Override
    public void onDataDelete(Goods[] list) {
        super.onDataDelete(list);

    }

    @Override
    public boolean isRequired(Goods goods) {
        // 来源三种：推送的数据、网络加载的数据、本地数据库查询的数据
        // 都要进行更新。
        return goods.isState() == state && goods.getCount() > 0;
    }
}
