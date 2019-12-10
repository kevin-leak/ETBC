package club.crabglory.www.etcb.main;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.basic.view.BaseActivity;
import club.crabglory.www.common.basic.view.BaseFragment;
import club.crabglory.www.common.widget.recycler.RecyclerAdapter;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.frags.book.BookShowFragment;
import club.crabglory.www.etcb.frags.book.BooksActivity;
import club.crabglory.www.etcb.frags.SearchActivity;
import club.crabglory.www.data.db.Book;

public class BookFragment extends BaseFragment {

    private final String TAG = "BaseFragment";

    @BindView(R.id.mrl_refresh)
    MaterialRefreshLayout mrlRefresh;
    @BindView(R.id.sv_Search)
    SearchView svSearch;
    @BindView(R.id.ll_select)
    LinearLayout llSelect;
    private RecyclerAdapter<Book> dailyAdapter;
    private RecyclerAdapter<Book> randomAdapter;
    private FragmentManager manager;
    private BookShowFragment randomFragment;
    private BookShowFragment dailyFragment;

    /**
     * 1. 搜索
     * 2. 类别
     * 3. 新书阁
     * 4. 百书阁
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_nav_book;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);
        svSearch.setQueryRefinementEnabled(false);
        filterBook();
        trigger();

        mrlRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                //下拉刷新...
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //todo
                        dailyFragment.refresh();
                        randomFragment.refresh();
                        mrlRefresh.finishRefresh();
                    }
                }, 1500);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //todo
                        dailyFragment.refresh();
                        randomFragment.refresh();
                        mrlRefresh.finishRefreshLoadMore();
                    }
                }, 1500);
            }
        });
    }

    private void trigger() {
        if (randomFragment == null || dailyFragment == null) {
            manager = this.getActivity().getSupportFragmentManager();
            FragmentTransaction dailyTransact = manager.beginTransaction();
            FragmentTransaction randomTransact = manager.beginTransaction();
            dailyFragment = new BookShowFragment();
            dailyFragment.setType(BookShowFragment.TYPE_DAILY);

            randomFragment = new BookShowFragment();
            randomFragment.setType(BookShowFragment.TYPE_RANDOM);

            dailyTransact.add(R.id.lay_container_daily, dailyFragment).commit();
            randomTransact.replace(R.id.lay_container_random, randomFragment).commit();
        }
    }

    private void filterBook() {
        for (int i = 0; i < 4; i++) {
            final Bundle bundle = new Bundle();
            bundle.putInt(Book.TYPE, i);
            llSelect.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "onClick: ");
                    BooksActivity.show((BaseActivity) BookFragment.this.getActivity(),
                            BooksActivity.class, bundle, false);
                }
            });
        }
    }

    @OnClick(R.id.sv_Search)
    public void onClick() {
        SearchActivity.show(BookFragment.this.getActivity(), SearchActivity.class);
    }


}

