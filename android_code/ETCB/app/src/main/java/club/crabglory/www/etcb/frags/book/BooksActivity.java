package club.crabglory.www.etcb.frags.book;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import butterknife.BindView;
import club.crabglory.www.common.basic.view.ToolbarActivity;
import club.crabglory.www.etcb.R;

public class BooksActivity extends ToolbarActivity {
    public static final int RANDOM = 1;
    public static final int MINE = 2;
    public static final int RECORD = 3;
    @BindView(R.id.lay_container)
    FrameLayout flContainer;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private BookShowFragment booksFragment;
    @BindView(R.id.mrl_refresh)
    MaterialRefreshLayout mrlRefresh;


    @Override
    protected int getContentLayoutId() {
        return R.layout.activtiy_book;
    }

    @SuppressLint("CommitTransaction")
    @Override
    protected void initWidget() {
        super.initWidget();
        manager = getSupportFragmentManager();
        //开启事务
        transaction = manager.beginTransaction();
        //碎片
        booksFragment = new BookShowFragment();
        //提交事务
        transaction.add(R.id.lay_container, booksFragment ).commit();

        mrlRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                //下拉刷新...
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //todo
                        mrlRefresh.finishRefresh();
                    }
                }, 1500);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //todo
                        mrlRefresh.finishRefreshLoadMore();
                    }
                }, 1500);
                //上拉加载更多...

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
    }

}
