package club.crabglory.www.factory.contract;

import club.crabglory.www.common.basic.contract.BaseContract;

public interface BookUpContract {
    interface View extends BaseContract.View<Presenter> {
        void upSuccess();
    }


    interface Presenter extends BaseContract.Presenter {
        void upBook(String mVideoUrl, String mAvatarPath, String bookAuthor, String bookName,
                    String count, String price, String info, int type);


    }
}
