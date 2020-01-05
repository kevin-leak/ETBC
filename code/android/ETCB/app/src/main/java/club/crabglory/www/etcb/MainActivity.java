package club.crabglory.www.etcb;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import club.crabglory.www.common.basic.view.BaseActivity;
import club.crabglory.www.common.utils.StatusBarUtils;
import club.crabglory.www.data.model.StaticData;
import club.crabglory.www.data.model.persistence.Account;
import club.crabglory.www.etcb.frags.account.AccountActivity;
import club.crabglory.www.etcb.hepler.NavHelper;
import club.crabglory.www.etcb.main.BookFragment;
import club.crabglory.www.etcb.main.AtticFragment;
import club.crabglory.www.etcb.main.MineFragment;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener, NavHelper.OnNavChangeListener<Integer> {

    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;
    private NavHelper<Integer> mHelper;
    private Menu menu;
    public static final int successCode = 0x002;
    public static final int failCode = 0x003;

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
        EasyPermissions.requestPermissions(this, "录像权限申请", 0,
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
        mHelper = new NavHelper<>(this.getApplicationContext(), R.id.lay_container, getSupportFragmentManager(), this);
        mHelper.add(R.id.action_book, new NavHelper.Tab<>(BookFragment.class, R.string.nav_book));
        mHelper.add(R.id.action_attic, new NavHelper.Tab<>(AtticFragment.class, R.string.nav_attic));
        mHelper.add(R.id.action_mine, new NavHelper.Tab<>(MineFragment.class, R.string.nav_mine));
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
        if (newTab.clx == MineFragment.class && !Account.isLogin()) {
            Intent intent = new Intent(MainActivity.this, AccountActivity.class);
            startActivityForResult(intent, AccountActivity.requestCode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AccountActivity.requestCode) {
            if (MainActivity.successCode == resultCode) {
                mNavigation.setSelectedItemId(R.id.action_mine);
                menu.performIdentifierAction(R.id.action_mine, 2);
                mNavigation.setSelectedItemId(R.id.action_mine);
                // fixme just for local test
                StaticData.initLive();
                StaticData.initVideo();
            } else {
                menu = mNavigation.getMenu();
                menu.performIdentifierAction(R.id.action_book, 0);
                mNavigation.setSelectedItemId(R.id.action_book);
            }
        }
    }

}
