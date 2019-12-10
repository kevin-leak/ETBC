package club.crabglory.www.factory.contract;


import club.crabglory.www.common.basic.contract.BaseContract;

public interface RegisterContract {

    interface View extends BaseContract.View<Presenter>{
        // 注册成功
        void registerSuccess();

    }
    interface Presenter extends BaseContract.Presenter {
        // 发起一个注册
        void register(String avatarPath, String name, String phone, String password, int sex);
        // 检查手机号是否正确, 实现给presenter用
        boolean checkMobile(String phone);
        boolean checkPsd(String psd, String re_psd);
        // 按格式输入名字
        boolean checkUserName(String username);
        void postRegister();
    }
}
