package club.crabglory.www.factory.presenter.account;

import android.text.TextUtils;

import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.common.basic.presenter.BasePresenter;
import club.crabglory.www.common.utils.ValidateUtils;
import club.crabglory.www.data.helper.AccountDataHelper;
import club.crabglory.www.data.model.db.User;
import club.crabglory.www.data.model.net.LoginRspModel;
import club.crabglory.www.factory.Factory;
import club.crabglory.www.factory.R;
import club.crabglory.www.factory.contract.LoginContract;

public class LoginPresenter extends BasePresenter<LoginContract.View>
        implements LoginContract.Presenter, DataSource.Callback<User> {

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void onDataLoaded(User user) {
        if (mView == null)
            return;
        if (user != null) {
            mView.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mView.loginSuccess();
                }
            });
        }

    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        mView.showError(strRes);
    }

    @Override
    public void login(final String phone, final String code, final boolean flag) {
        mView.showDialog();
        if (TextUtils.isEmpty(code)) {
            onDataNotAvailable(R.string.error_null_data);
            return;
        }
        LoginRspModel model = new LoginRspModel(phone, code, flag);
        AccountDataHelper.login(model, LoginPresenter.this);
    }

    @Override
    public void sendCodeRsp(final String phone) {
        Factory.Companion.runOnAsync(new Runnable() {
            @Override
            public void run() {
                AccountDataHelper.rspCode(phone, LoginPresenter.this);
            }
        });
    }

    @Override
    public boolean checkMobile(String phone) {
        if (ValidateUtils.isMobile(phone)) {
            return true;
        }
        getView().showError(R.string.error_form_phone);
        return false;
    }
}
