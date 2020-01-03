package club.crabglory.www.etcb.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.basic.view.BaseFragment;
import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.data.model.net.MaterialRspModel;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.frags.search.SearchActivity;
import club.crabglory.www.etcb.frags.book.BookShowFragment;
import club.crabglory.www.etcb.frags.book.BooksActivity;

public class BookFragment extends BaseFragment {

    private final String TAG = "BaseFragment";


    @BindView(R.id.mrl_refresh)
    MaterialRefreshLayout mrlRefresh;
    @BindView(R.id.sv_Search)
    TextView svSearch;
    @BindView(R.id.ll_select)
    LinearLayout llSelect;
    private BookShowFragment randomFragment;
    private BookShowFragment dailyFragment;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_nav_book;
    }

    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);
        filterTypeBook();   // 选择类型查看书籍
        spliceFragment();      // 选择类型查看书籍

        mrlRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                randomFragment.notifyRefresh(mrlRefresh, false);
            }
            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                randomFragment.notifyRefresh(mrlRefresh, true);
            }
        });
    }

    private void spliceFragment() {
        if (randomFragment == null || dailyFragment == null) {
            FragmentManager manager = this.getActivity().getSupportFragmentManager();
            FragmentTransaction dailyTransact = manager.beginTransaction();
            FragmentTransaction randomTransact = manager.beginTransaction();
            dailyFragment = new BookShowFragment();
            dailyFragment.obtainType(MaterialRspModel.TYPE_DAILY);
            randomFragment = new BookShowFragment();
            randomFragment.obtainType(MaterialRspModel.TYPE_RANDOM);
            dailyTransact.add(R.id.lay_container_daily, dailyFragment).commit();
            randomTransact.replace(R.id.lay_container_random, randomFragment).commit();
        }
    }

    private void filterTypeBook() {
        for (int i = 0; i < 4; i++) {
            final Bundle bundle = new Bundle();
            bundle.putInt(MaterialRspModel.TYPE_KEY, i);
            llSelect.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BooksActivity.show(BookFragment.this.getActivity(),
                            BooksActivity.class, bundle, false);
                }
            });
        }
    }

    @OnClick({R.id.sv_Search, R.id.rl_random})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sv_Search:
                SearchActivity.show(BookFragment.this.getActivity(), SearchActivity.class);
                break;
            case R.id.rl_random:
                final Bundle bundle = new Bundle();
                bundle.putInt(MaterialRspModel.TYPE_KEY, MaterialRspModel.TYPE_RANDOM);
                BooksActivity.show(BookFragment.this.getActivity(),
                        BooksActivity.class, bundle, false);
                break;
        }
    }
}

