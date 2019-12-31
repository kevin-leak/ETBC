package club.crabglory.www.factory.presenter.account;

import android.text.TextUtils;

import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.common.basic.presenter.BasePresenter;
import club.crabglory.www.common.utils.ValidateUtils;
import club.crabglory.www.data.helper.AccountDataHelper;
import club.crabglory.www.data.model.db.User;
import club.crabglory.www.data.model.net.RegisterRspModel;
import club.crabglory.www.factory.Factory;
import club.crabglory.www.factory.R;
import club.crabglory.www.factory.contract.RegisterContract;
import club.crabglory.www.data.helper.FileDataHelper;

public class RegisterPresenter extends BasePresenter<RegisterContract.View>
        implements RegisterContract.Presenter, DataSource.Callback<User> {

    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }

    @Override
    public void register(final String avatarPath, final String name, final String phone, final String password, final int sex) {
        mView.showDialog();
        RegisterRspModel model = new RegisterRspModel(avatarPath, name, phone, password, sex);
        AccountDataHelper.register(model, RegisterPresenter.this);
    }

    @Override
    public boolean checkMobile(String phone) {
        if (ValidateUtils.isMobile(phone)) {
            return true;
        }
        getView().showError(R.string.error_form_phone);
        return false;
    }

    @Override
    public boolean checkPsd(String psd, String re_psd) {

        if (!psd.equals(re_psd)) {
            getView().showError(R.string.error_data_psd_not_same);
            return false;
        } else if (!ValidateUtils.isPassword(psd)) {
            getView().showError(R.string.error_form_phone);
            return false;
        }

        return true;
    }

    @Override
    public boolean checkUserName(String username) {
        if (ValidateUtils.isUsername(username)) {
            return true;
        }
        getView().showError(R.string.error_same_username);
        return false;
    }

    @Override
    public void onDataLoaded(User user) {

        final RegisterContract.View view = getView();
        if (view == null)
            return;
        if (user != null) {
            view.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    view.registerSuccess();
                }
            });
        }

    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        final RegisterContract.View view = getView();
        view.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.showError(strRes);
            }
        });
    }
}
