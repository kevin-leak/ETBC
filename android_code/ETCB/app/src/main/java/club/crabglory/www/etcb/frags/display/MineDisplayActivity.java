package club.crabglory.www.etcb.frags.display;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import club.crabglory.www.common.basic.BaseFragment;
import club.crabglory.www.common.basic.ToolbarActivity;
import club.crabglory.www.common.utils.StatusBarUtils;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.frags.display.DisplayBooksFragment;
import club.crabglory.www.etcb.frags.display.DisplayLiveFragment;
import club.crabglory.www.etcb.frags.display.DisplayMicroFragment;
import club.crabglory.www.etcb.hepler.ViewPageHelper;

public class MineDisplayActivity extends ToolbarActivity implements ViewPageHelper.ViewPagerCallback {

    @BindViews({R.id.tv_micro, R.id.tv_books, R.id.tv_live})
    List<TextView> navigationList;
    @BindView(R.id.vp_container)
    ViewPager vpContainer;
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
                .addItem(navigationList.get(1), new DisplayBooksFragment())
                .addItem(navigationList.get(2), new DisplayLiveFragment());
    }

    @Override
    public void onChangedFragment(Fragment currentFragment) {

    }
}
