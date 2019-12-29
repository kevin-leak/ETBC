package club.crabglory.www.etcb.frags.goods;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.basic.view.PresentToolActivity;
import club.crabglory.www.common.utils.StatusBarUtils;
import club.crabglory.www.common.widget.recycler.RecyclerAdapter;
import club.crabglory.www.data.model.db.Goods;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.frags.book.BooksActivity;
import club.crabglory.www.etcb.frags.book.BooksShopActivity;
import club.crabglory.www.etcb.holders.GoodsCarHolder;
import club.crabglory.www.factory.contract.GoodsCarContract;
import club.crabglory.www.factory.presenter.book.GoodsCarPresenter;

public class GoodsCarActivity extends PresentToolActivity<GoodsCarContract.Presenter>
        implements GoodsCarContract.View, GoodsCarContract.CheckGoodsListener {


    @BindView(R.id.rv_goods_list)
    RecyclerView rvGoods;
    @BindView(R.id.tv_sum_money)
    TextView tvSumMoney;
    @BindView(R.id.cb_all)
    CheckBox cbCall;
    @BindView(R.id.sf_goods_list)
    MaterialRefreshLayout sfGoodsList;
    private RecyclerAdapter<Goods> goodsAdapter;
    // 选定的holder集合
    private List<CheckBox> checkBoxes = new ArrayList<>();
    // 存储选定的goods集合，用于删除
    private List<Goods> checkGoods = new ArrayList<>();

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_goods_car;
    }

    @Override
    protected void initWindows() {
        super.initWindows();
        StatusBarUtils.setLightColor(getWindow());
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        rvGoods.setLayoutManager(new LinearLayoutManager(this));
        goodsAdapter = new RecyclerAdapter<Goods>() {
            @Override
            protected int getItemViewType(int position, Goods goods) {
                return R.layout.holder_car_book;
            }

            @Override
            protected ViewHolder<Goods> onCreateViewHolder(View root, int viewType) {
                return new GoodsCarHolder(root, GoodsCarActivity.this, checkBoxes);
            }
        };
        rvGoods.setAdapter(goodsAdapter);

        goodsAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<Goods>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Goods goods) {
                // todo book 信息处理
                Bundle bundle = new Bundle();
                bundle.putSerializable(BooksShopActivity.KEY, goods.getBook().getId());
                BooksActivity.show(GoodsCarActivity.this,
                        BooksShopActivity.class, bundle, false);
            }
        });



        sfGoodsList.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout mRefresh) {
                mPresenter.preRefresh(false);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout mRefresh) {
                mPresenter.preRefresh(false);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.start();
    }

    @Override
    public RecyclerAdapter<Goods> getRecyclerAdapter() {
        return goodsAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        hideLoading();
        sfGoodsList.finishRefresh();
    }

    @Override
    protected GoodsCarContract.Presenter initPresenter() {
        return new GoodsCarPresenter(this);
    }

    @Override
    public void checkBuy(Goods goods, float addSum, boolean isAddNew) {
        if (addSum == 0 && !isAddNew) cbCall.setChecked(false);
        String money = tvSumMoney.getText().toString();
        float payMoney = Float.parseFloat(money.substring(0, money.length() - 1));
        if (!checkGoods.contains(goods) && isAddNew) {
            checkGoods.add(goods);
            payMoney += addSum;
        } else {
            payMoney += addSum * goods.getBook().getPrice();
            if (addSum == 0) {
                payMoney -= goods.getBook().getPrice() * goods.getCount();
                checkGoods.remove(goods);
            }
        }
        tvSumMoney.setText(String.format("%s $", payMoney));
    }

    @OnClick({R.id.tv_delete, R.id.tv_pay, R.id.cb_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_delete:
                // todo 如果删除成功要清除选择
                mPresenter.emptyCart(checkGoods);
                break;
            case R.id.tv_pay:
                String money = tvSumMoney.getText().toString();
                mPresenter.prePay(Float.parseFloat(money.substring(0, money.length() - 1)), checkGoods);
                break;
            case R.id.cb_all:
                if (checkBoxes.size() <= 0) return;
                float payMoney = 0;
                for (CheckBox box : checkBoxes) {
                    if (box != null)
                        box.setChecked(((CheckBox) view).isChecked());
                }
                checkGoods.clear();
                if (((CheckBox) view).isChecked()) {
                    checkGoods.addAll(goodsAdapter.getItems());
                    for (Goods goods : checkGoods)
                        payMoney += goods.getCount() * goods.getBook().getPrice();
                }
                tvSumMoney.setText(String.format("%s $", payMoney));
                break;
        }
    }

    @Override
    public void dealSuccess() {
        for (CheckBox box : checkBoxes) {
            if (box != null) box.setChecked(false);
        }
        cbCall.setChecked(false);
        tvSumMoney.setText(String.format("%s $", 0));
    }

    @Override
    protected void onPause() {
        mPresenter.saveChange(goodsAdapter.getItems());
        super.onPause();
    }
}
