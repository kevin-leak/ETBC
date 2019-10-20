package club.crabglory.www.common.basic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;

/**
 * @author kevinleak
 *
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindows();
        if (initArgs(getIntent().getExtras())) {
            int contentLayoutId = getContentLayoutId();
            setContentView(contentLayoutId);
            initBefore();
            initWidget();
            initData();
        } else {
            finish();
        }
    }

    protected void initBefore() { }
    protected void initData() {}
    protected void initWidget() {ButterKnife.bind(this);}
    protected boolean initArgs(Bundle bundle) {
        return true;
    }
    protected void initWindows() {}
    protected abstract int getContentLayoutId();
    // 当点击返回键时销毁当前的activity
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    /**
     * 处理一个界面有多个fragment的情况
     */
    @Override
    public void onBackPressed() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                if (fragment instanceof BaseFragment) {
                    // 如果已经处理则会返回正确，如果没有，则有activity 处理
                    if (((BaseFragment) fragment).onBackPressed()) {
                        return;
                    }
                }
            }
        }
        super.onBackPressed();
        finish();
    }

    public static void show(BaseActivity activity, Class<? extends BaseActivity> cls){
        activity.startActivity(new Intent(activity, cls));
        activity.finish();
    }
}
