package club.crabglory.www.etcb;

import android.Manifest;
import android.os.Handler;

import club.crabglory.www.common.basic.view.BaseActivity;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * 两个任务：
 * 1. 登入信息的核对，是否要重新登入
 * 2. 第一次打开，权限的获取
 * 3. 版本更新的检测
 */
public class LaunchActivity extends BaseActivity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        EasyPermissions.requestPermissions(this, "录像权限申请", 0 ,
                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        /* todo animation
         * 1. 图标缓慢显示
         * 2. 抖动
         */
    }

    @Override
    protected void initData() {
        super.initData();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                /* todo
                 * 初始化缓存资源
                 */
                MainActivity.show(LaunchActivity.this, MainActivity.class, null, true);
            }
        }, 2000);
    }

}
