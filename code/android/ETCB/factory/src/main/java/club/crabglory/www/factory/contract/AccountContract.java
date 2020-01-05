package club.crabglory.www.factory.contract;

import club.crabglory.www.common.basic.contract.BaseContract;

public interface AccountContract {

    interface View extends BaseContract.View<AccountContract.Presenter> {

    }

    interface Presenter extends BaseContract.Presenter {

    }
}
