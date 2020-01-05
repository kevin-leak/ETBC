package club.crabglory.www.etcb.frags;

import android.support.v7.widget.CardView;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.basic.view.PresentToolActivity;
import club.crabglory.www.common.utils.StatusBarUtils;
import club.crabglory.www.factory.contract.SettingsContract;
import club.crabglory.www.etcb.R;
import club.crabglory.www.factory.presenter.SettingsPresenter;

public class SettingsActivity extends PresentToolActivity<SettingsContract.Presenter>
        implements SettingsContract.View {
    @BindView(R.id.cv_quit)
    CardView cvQuit;

    @Override
    protected void initWindows() {
        super.initWindows();
        StatusBarUtils.setDarkColor(getWindow());
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_settings;
    }


    @OnClick(R.id.cv_quit)
    public void onClick() {
        mPresenter.loginOut();
    }

    @Override
    protected SettingsPresenter initPresenter() {
        return new SettingsPresenter(this);
    }
}