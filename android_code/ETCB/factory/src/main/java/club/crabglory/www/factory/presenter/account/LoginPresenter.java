package club.crabglory.www.factory.presenter.account;

import club.crabglory.www.common.basic.presenter.BasePresenter;
import club.crabglory.www.common.utils.ValidateUtils;
import club.crabglory.www.factory.R;
import club.crabglory.www.factory.contract.LoginContract;

public class LoginPresenter extends BasePresenter<LoginContract.View>
        implements LoginContract.Presenter {

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void login(String phone, String password) {

    }

    @Override
    public boolean checkMobile(String phone) {
        if (ValidateUtils.isMobile(phone)){
            return true;
        }
        getView().showError(R.string.form_phone_error);
        return false;
    }

    @Override
    public boolean checkPsd(String psd) {
        // todo inject judge
        return true;
    }

    @Override
    public void postLogin() {
        // todo to post message to MineFragment and update mine info
    }
}
