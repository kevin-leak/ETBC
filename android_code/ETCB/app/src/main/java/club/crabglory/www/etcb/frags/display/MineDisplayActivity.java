package club.crabglory.www.etcb.frags.display;

import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import club.crabglory.www.common.basic.view.BaseFragment;
import club.crabglory.www.common.basic.view.ToolbarActivity;
import club.crabglory.www.common.utils.StatusBarUtils;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.frags.book.BookShowFragment;
import club.crabglory.www.etcb.hepler.ViewPageHelper;

public class MineDisplayActivity extends ToolbarActivity implements ViewPageHelper.ViewPagerCallback {

    @BindViews({R.id.tv_micro, R.id.tv_books, R.id.tv_live})
    List<TextView> navigationList;
    @BindView(R.id.vp_container)
    ViewPager vpContainer;
    @BindView(R.id.mrl_refresh)
    MaterialRefreshLayout mrlRefresh;
    private ViewPageHelper<TextView, BaseFragment> helper;


    @Override
    protected void initWindows() {
        super.initWindows();
        StatusBarUtils.setDarkColor(getWindow());
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_mine_display;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initWidget() {
        super.initWidget();
        helper = new ViewPageHelper<>(vpContainer, this, this);
        helper.addItem(navigationList.get(0), new DisplayMicroFragment())
                .addItem(navigationList.get(1), new BookShowFragment())
                .addItem(navigationList.get(2), new DisplayLiveFragment());

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
    public void onChangedFragment(Fragment currentFragment) {

    }
}
