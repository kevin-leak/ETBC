package club.crabglory.www.etcb;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import club.crabglory.www.common.basic.BaseActivity;
import club.crabglory.www.common.utils.StatusBarUtils;
import club.crabglory.www.etcb.hepler.NavHelper;
import club.crabglory.www.etcb.main.BookFragment;
import club.crabglory.www.etcb.main.MicroFragment;
import club.crabglory.www.etcb.main.MineFragment;

public class MainActivity extends BaseActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener, NavHelper.OnNavChangeListener {

    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;
    private NavHelper mHelper;

    /**
     * 1. 页面切换
     * 2. fragment的缓存问题
     */

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWindows() {
        super.initWindows();
        StatusBarUtils.setDarkColor(getWindow());
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        bindFragment();
        Menu menu = mNavigation.getMenu();
        menu.performIdentifierAction(R.id.action_micro, 1);
        mNavigation.setSelectedItemId(R.id.action_micro);
    }

    private void bindFragment() {
        mHelper = new NavHelper(this, R.id.lay_container, getSupportFragmentManager(), this);
        mHelper.add(R.id.action_book, new NavHelper.Tab(BookFragment.class, R.string.nav_book));
        mHelper.add(R.id.action_micro, new NavHelper.Tab<>(MicroFragment.class, R.string.nav_micro));
        mHelper.add(R.id.action_mine, new NavHelper.Tab<>(MineFragment.class, R.string.nav_mine));
        //初始化设置第一个fragment显示
        mNavigation.setOnNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return mHelper.performClickMenu(menuItem.getItemId());
    }

    @Override
    public void OnNavChanged(NavHelper.Tab newTab, NavHelper.Tab oldTab) {

    }
}
