package club.crabglory.www.factory.presenter.account;

import club.crabglory.www.common.basic.presenter.BasePresenter;
import club.crabglory.www.factory.contract.AccountContract;

public class AccountPresenter extends BasePresenter<AccountContract.View>
        implements AccountContract.Presenter {
    public AccountPresenter(AccountContract.View view) {
        super(view);
    }
}
