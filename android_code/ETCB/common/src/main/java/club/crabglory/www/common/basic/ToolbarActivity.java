package club.crabglory.www.common.basic;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;


import club.crabglory.www.common.R;
import club.crabglory.www.common.utils.StatusBarUtils;

/**
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public abstract class ToolbarActivity extends BaseActivity {
    protected Toolbar mToolbar;

    @Override
    protected void initWindows() {
        super.initWindows();
        StatusBarUtils.setDarkColor(getWindow());
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initToolbar((Toolbar) findViewById(R.id.toolbar));
    }

    /**
     * 初始化toolbar
     *
     * @param toolbar Toolbar
     */
    public void initToolbar(Toolbar toolbar) {
        mToolbar = toolbar;
        if (toolbar != null) {
            mToolbar.setNavigationIcon(R.drawable.ic_back);
            setSupportActionBar(toolbar);
        }
        initTitleNeedBack();
    }

    protected void initTitleNeedBack() {
        // 设置左上角的返回按钮为实际的返回效果
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }
}
