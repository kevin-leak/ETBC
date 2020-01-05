package club.crabglory.www.factory.presenter;

import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.common.basic.presenter.BasePresenter;
import club.crabglory.www.factory.Factory;
import club.crabglory.www.factory.contract.SettingsContract;
import club.crabglory.www.data.model.persistence.Account;
import club.crabglory.www.factory.R;
import club.crabglory.www.data.helper.AccountDataHelper;

public class SettingsPresenter extends BasePresenter<SettingsContract.View>
        implements SettingsContract.Presenter,  DataSource.Callback<String> {

    public SettingsPresenter(SettingsContract.View view) {
        super(view);
    }

    @Override
    public void loginOut() {
        mView.showDialog();
        if (Account.isLogin())
            Factory.Companion.runOnAsync(new Runnable() {
                @Override
                public void run() {
                    AccountDataHelper.loginOut(Account.getUserId(), SettingsPresenter.this);
                }
            });
        else
            mView.showError(R.string.error_not_login);
    }

    @Override
    public void onDataLoaded(String s) {
        if ("ok".equals(s)){
            if (Account.removeLogin(getView().getActivity()))
                getView().getActivity().finish();
        }
    }

    @Override
    public void onDataNotAvailable(int strRes) {
        mView.showError(strRes);
    }

}
