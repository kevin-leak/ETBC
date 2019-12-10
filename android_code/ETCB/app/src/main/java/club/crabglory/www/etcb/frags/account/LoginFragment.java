package club.crabglory.www.etcb.frags.account;

import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.basic.view.BasePresenterFragment;
import club.crabglory.www.data.persistence.Account;
import club.crabglory.www.etcb.R;
import club.crabglory.www.factory.contract.LoginContract;
import club.crabglory.www.factory.presenter.account.LoginPresenter;

public class LoginFragment extends BasePresenterFragment<LoginContract.Presenter>
        implements LoginContract.View {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_psd)
    EditText etPsd;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }

    public void preLogin() {
        if(Account.isLogin()) return;
        String phoneString = etPhone.getText().toString();
        String psdString = etPsd.getText().toString();
        if (presenter.checkMobile(phoneString)
                && presenter.checkPsd(psdString) ){
            presenter.login(phoneString, psdString);
        }
    }

    @Override
    protected LoginContract.Presenter initPresent() {
        return new LoginPresenter(this);
    }

    @Override
    public void loginSuccess() {
        getActivity().finish();
        presenter.postLogin();
    }

    @OnClick(R.id.tv_forget)
    public void onClick() {
        // todo forget password
    }
}
