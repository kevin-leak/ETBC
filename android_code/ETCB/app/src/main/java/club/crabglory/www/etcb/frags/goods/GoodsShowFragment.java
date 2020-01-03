package club.crabglory.www.etcb.frags.goods;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.cjj.MaterialRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import club.crabglory.www.common.basic.view.BasePresenterFragment;
import club.crabglory.www.common.widget.recycler.RecyclerAdapter;
import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.data.model.db.Goods;
import club.crabglory.www.data.model.net.MaterialRspModel;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.frags.book.BooksActivity;
import club.crabglory.www.etcb.frags.book.BooksShopActivity;
import club.crabglory.www.etcb.holders.GoodsRandomHolder;
import club.crabglory.www.factory.contract.GoodsShowContract;
import club.crabglory.www.factory.presenter.book.GoodsShowPresenter;

public class GoodsShowFragment extends BasePresenterFragment<GoodsShowContract.Presenter>
        implements GoodsShowContract.View, GoodsShowContract.CheckGoodsListener {

    @BindView(R.id.rv_goods)
    RecyclerView rvGoods;
    private RecyclerAdapter<Goods> bookAdapter;
    private int type;
    private MaterialRefreshLayout mrlRefresh;
    private List<Goods> listGoods = new ArrayList<>();
    private List<CheckBox> listCheckBoxes = new ArrayList<>();

    public GoodsShowFragment setType(int type) {
        this.type = type;
        return this;
    }

    // 用于和Activity交互
    public void preDeleteGoods() {
        presenter.toDeleteGoods(listGoods);
    }

    public void openCheckGoods() {
        for (CheckBox box : listCheckBoxes) box.setVisibility(View.VISIBLE);
    }


    public void preRefresh(MaterialRefreshLayout mrlRefresh, boolean isMore) {

        this.mrlRefresh = mrlRefresh;
        presenter.toRefresh(isMore);
    }

    @Override
    protected void intiArgs(Bundle arguments) {
        super.intiArgs(arguments);
        if (arguments != null)
            type = arguments.getInt(MaterialRspModel.TYPE_KEY);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_show_goods;
    }

    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);
        rvGoods.setLayoutManager(
                new LinearLayoutManager(this.getActivity()));
        bookAdapter = new RecyclerAdapter<Goods>() {
            @Override
            protected int getItemViewType(int position, Goods book) {
                return R.layout.holder_goods;
            }

            @Override
            protected ViewHolder<Goods> onCreateViewHolder(View root, int viewType) {
                return new GoodsRandomHolder(root, GoodsShowFragment.this, listCheckBoxes);
            }
        };
        bookAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<Goods>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Goods goods) {
                Bundle bundle = new Bundle();
                bundle.putString(BooksShopActivity.KEY, goods.getBook().getId());
                BooksActivity.show(GoodsShowFragment.this.getActivity(),
                        BooksShopActivity.class, bundle,
                        false);
            }
        });
        rvGoods.setAdapter(bookAdapter);
    }


    @Override
    protected void onFirstInit() {
        super.onFirstInit();
        presenter.start();
    }

    @Override
    public RecyclerAdapter<Goods> getRecyclerAdapter() {
        return bookAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        if (mrlRefresh != null) mrlRefresh.finishRefresh();
        hideLoading();
    }

    @Override
    protected GoodsShowContract.Presenter initPresent() {
        return new GoodsShowPresenter(this, type);
    }

    @Override
    public void showError(int error) {
        super.showError(error);
        mrlRefresh.finishRefresh();
    }

    @Override
    public void checkDelete(Goods goods) {
        if (listGoods.contains(goods))
            listGoods.remove(goods);
        else listGoods.add(goods);
    }


    @Override
    public void dealSuccess() {
        for (CheckBox box : listCheckBoxes) box.setVisibility(View.GONE);
    }

}
