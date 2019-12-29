package club.crabglory.www.etcb.frags.book;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import butterknife.BindView;
import club.crabglory.www.common.basic.view.ToolbarActivity;
import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.etcb.R;

public class BooksActivity extends ToolbarActivity {
    @BindView(R.id.mrl_refresh)
    MaterialRefreshLayout mrlRefresh;
    private int type;
    private BookShowFragment booksFragment;

    @Override
    protected boolean initArgs(Bundle bundle) {
        type = bundle.getInt(Book.TYPE_KEY);
        return super.initArgs(bundle);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activtiy_book;
    }

    @SuppressLint("CommitTransaction")
    @Override
    protected void initWidget() {
        super.initWidget();
        final FragmentManager manager = getSupportFragmentManager();
        //开启事务
        FragmentTransaction transaction = manager.beginTransaction();
        //碎片
        booksFragment = new BookShowFragment();
        booksFragment.setType(type);
        //提交事务
        transaction.add(R.id.lay_container, booksFragment).commit();

        mrlRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout mRefresh) {
                booksFragment.preRefresh(mRefresh, false);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout mRefresh) {
                booksFragment.preRefresh(mRefresh, false);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
    }

}
