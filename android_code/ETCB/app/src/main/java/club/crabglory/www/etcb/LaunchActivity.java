package club.crabglory.www.etcb;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import club.crabglory.www.common.basic.BaseActivity;


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
                MainActivity.show(LaunchActivity.this, MainActivity.class);
            }
        }, 2000);
    }

}
