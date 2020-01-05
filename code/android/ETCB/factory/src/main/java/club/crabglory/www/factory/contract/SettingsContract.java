package club.crabglory.www.factory.contract;

import club.crabglory.www.common.basic.contract.BaseContract;

public interface SettingsContract {
    interface View extends BaseContract.View<Presenter>{
    }


    interface Presenter extends BaseContract.Presenter{
        void loginOut();
    }
}
