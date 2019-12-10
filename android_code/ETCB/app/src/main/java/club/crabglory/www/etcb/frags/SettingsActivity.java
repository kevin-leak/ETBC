package club.crabglory.www.etcb.frags;

import club.crabglory.www.common.basic.view.ToolbarActivity;
import club.crabglory.www.common.utils.StatusBarUtils;
import club.crabglory.www.etcb.R;

public class SettingsActivity  extends ToolbarActivity {

    @Override
    protected void initWindows() {
        super.initWindows();
        StatusBarUtils.setDarkColor(getWindow());
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_settings;
    }
}