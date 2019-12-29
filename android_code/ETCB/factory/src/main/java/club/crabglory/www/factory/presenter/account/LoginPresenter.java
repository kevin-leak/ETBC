package club.crabglory.www.factory.presenter.account;

import android.text.TextUtils;

import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.common.basic.presenter.BasePresenter;
import club.crabglory.www.common.utils.ValidateUtils;
import club.crabglory.www.data.DataKit;
import club.crabglory.www.data.model.db.User;
import club.crabglory.www.data.model.net.LoginRspModel;
import club.crabglory.www.data.model.net.RegisterRspModel;
import club.crabglory.www.factory.R;
import club.crabglory.www.factory.contract.LoginContract;
import club.crabglory.www.factory.contract.RegisterContract;
import club.crabglory.www.factory.presenter.FileHelper;

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
        mView.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mView.showError(strRes);
            }
        });
    }

    @Override
    public void login(final String phone, final String code, final boolean flag) {
        mView.showDialog();
        if (TextUtils.isEmpty(code)){
            onDataNotAvailable(R.string.error_null_data);
            return;
        }
        DataKit.Companion.runOnAsysc(new Runnable() {
            @Override
            public void run() {
                LoginRspModel model = new LoginRspModel(phone, code, flag);
                AccountHelper.login(model, LoginPresenter.this);
            }
        });
    }

    @Override
    public void sendCodeRsp(final String phone) {
        DataKit.Companion.runOnAsysc(new Runnable() {
            @Override
            public void run() {
                AccountHelper.rspCode(phone, LoginPresenter.this);
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
