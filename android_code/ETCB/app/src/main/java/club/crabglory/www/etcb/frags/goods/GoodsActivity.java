package club.crabglory.www.etcb.frags.goods;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.basic.view.ToolbarActivity;
import club.crabglory.www.etcb.R;

public class GoodsActivity extends ToolbarActivity {
    @BindView(R.id.mrl_refresh)
    MaterialRefreshLayout mrlRefresh;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    private int type;
    private GoodsShowFragment goodsFragment;

    private boolean flag = true;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activtiy_goods;
    }

    @SuppressLint("CommitTransaction")
    @Override
    protected void initWidget() {
        super.initWidget();
        final FragmentManager manager = getSupportFragmentManager();
        //开启事务
        FragmentTransaction transaction = manager.beginTransaction();
        //碎片
        goodsFragment = new GoodsShowFragment();
        goodsFragment.setType(type);
        //提交事务
        transaction.add(R.id.lay_container, goodsFragment).commit();

        mrlRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout mRefresh) {
                goodsFragment.preRefresh(mRefresh, false);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout mRefresh) {
                goodsFragment.preRefresh(mRefresh, false);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
    }


    @OnClick(R.id.tv_delete)
    public void onClick() {
        if (flag) { // 打开删除
            tvDelete.setText(R.string.del);
            tvDelete.setTextColor(getResources().getColor(R.color.etcb));
            goodsFragment.openCheckGoods();
            flag = !flag;
        } else {
            goodsFragment.preDeleteGoods();
            tvDelete.setText(R.string.manager);
            tvDelete.setTextColor(getResources().getColor(R.color.colorBlue));
            flag = !flag;
        }
    }
}
