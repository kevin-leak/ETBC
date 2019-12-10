package club.crabglory.www.factory.contract;


import club.crabglory.www.common.basic.contract.BaseContract;

public interface LoginContract {
    interface View extends BaseContract.View<Presenter>{
        void loginSuccess();
    }

    interface Presenter extends BaseContract.Presenter {
        void login(String phone, String password);
        boolean checkMobile(String phone);
        boolean checkPsd(String psd);
        void postLogin();
    }
}
