package club.crabglory.www.factory.contract;


import club.crabglory.www.common.basic.contract.BaseContract;

public interface LoginContract {
    interface View extends BaseContract.View<Presenter>{
        void loginSuccess();
    }

    interface Presenter extends BaseContract.Presenter {
        void login(String phone, String password, boolean flag);
        // 发送验证码请求
        void sendCodeRsp(String phone);
        // 检查手机号是否正确, 实现给presenter用
        boolean checkMobile(String phone);
    }
}
