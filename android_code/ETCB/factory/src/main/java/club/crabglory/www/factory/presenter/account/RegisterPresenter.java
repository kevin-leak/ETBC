package club.crabglory.www.factory.presenter.account;

import club.crabglory.www.common.basic.presenter.BasePresenter;
import club.crabglory.www.common.basic.view.BasePresenterFragment;
import club.crabglory.www.factory.contract.RegisterContract;

public class RegisterPresenter extends BasePresenter<RegisterContract.View>
        implements RegisterContract.Presenter{

    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }


    @Override
    public void register(String avatarPath, String name, String phone, String password, int sex) {

    }

    @Override
    public boolean checkMobile(String phone) {
        return false;
    }

    @Override
    public boolean checkPsd(String psd, String re_psd) {
        return false;
    }

    @Override
    public boolean checkUserName(String username) {
        return false;
    }

    @Override
    public void postRegister() {

    }
}
