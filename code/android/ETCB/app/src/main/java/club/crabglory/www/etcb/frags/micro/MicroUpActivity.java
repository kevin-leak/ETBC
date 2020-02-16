package club.crabglory.www.etcb.frags.micro;

import club.crabglory.www.common.basic.view.ToolbarActivity;
import club.crabglory.www.common.utils.StatusBarUtils;
import club.crabglory.www.etcb.R;


public class MicroUpActivity extends ToolbarActivity {
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_mine_micro;
    }

    @Override
    protected void initWidget() {
        StatusBarUtils.setLightColor(getWindow());
        super.initWidget();
    }

}
