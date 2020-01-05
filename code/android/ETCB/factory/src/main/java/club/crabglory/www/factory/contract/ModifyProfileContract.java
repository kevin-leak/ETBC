package club.crabglory.www.factory.contract;

import club.crabglory.www.common.basic.contract.BaseContract;

public interface ModifyProfileContract {

    interface View extends BaseContract.View<Presenter>{

        void modifySuccess();
    }


    interface Presenter extends BaseContract.Presenter{
        void modifyName(String name);
        void modifySex(int sex);
        void modifyAddress(String address);
        void modifyAvatar(String mAvatarPath);
    }
}
