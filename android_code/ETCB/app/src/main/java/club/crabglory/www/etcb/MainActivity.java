package club.crabglory.www.etcb;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import club.crabglory.www.common.basic.view.BaseActivity;
import club.crabglory.www.common.utils.StatusBarUtils;
import club.crabglory.www.etcb.hepler.NavHelper;
import club.crabglory.www.etcb.main.BookFragment;
import club.crabglory.www.etcb.main.AtticFragment;
import club.crabglory.www.etcb.main.MineFragment;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener, NavHelper.OnNavChangeListener {

    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;
    private NavHelper mHelper;
    private Menu menu;

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
        EasyPermissions.requestPermissions(this, "录像权限申请", 0 ,
                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        bindFragment();
        menu = mNavigation.getMenu();
        menu.performIdentifierAction(R.id.action_attic, 1);
        mNavigation.setSelectedItemId(R.id.action_attic);
    }

    private void bindFragment() {
        mHelper = new NavHelper(this, R.id.lay_container, getSupportFragmentManager(), this);
        mHelper.add(R.id.action_book, new NavHelper.Tab(BookFragment.class, R.string.nav_book));
        mHelper.add(R.id.action_attic, new NavHelper.Tab<>(AtticFragment.class, R.string.nav_attic));
        mHelper.add(R.id.action_mine, new NavHelper.Tab<>(MineFragment.class, R.string.nav_mine));
        //初始化设置第一个fragment显示
        mNavigation.setOnNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return mHelper.performClickMenu(menuItem.getItemId());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    @Override
    public void OnNavChanged(NavHelper.Tab newTab, NavHelper.Tab oldTab) {
        if (newTab.clx == AtticFragment.class){
        }
    }
}
