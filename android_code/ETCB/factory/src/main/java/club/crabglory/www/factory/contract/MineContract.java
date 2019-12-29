package club.crabglory.www.factory.contract;

import club.crabglory.www.common.basic.contract.BaseContract;
import club.crabglory.www.data.model.db.User;
import club.crabglory.www.data.model.view.UserModelView;

public interface MineContract {

    // 什么都不需要额外定义，开始就是调用start即可
    interface Presenter extends BaseContract.Presenter {

    }

    // 都在基类完成了
    interface View extends BaseContract.View<Presenter> {
        void loadUserInfo(User user);
    }
}
