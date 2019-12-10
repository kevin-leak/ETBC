package club.crabglory.www.etcb.frags.account;

import android.widget.EditText;

import butterknife.BindView;
import club.crabglory.www.common.basic.view.BasePresenterFragment;
import club.crabglory.www.etcb.R;
import club.crabglory.www.factory.contract.RegisterContract;
import club.crabglory.www.factory.presenter.account.RegisterPresenter;

public class RegisterFragment extends BasePresenterFragment<RegisterContract.Presenter>
        implements RegisterContract.View {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_sex)
    EditText etSex;
    @BindView(R.id.et_psd)
    EditText etPsd;
    @BindView(R.id.et_re_psd)
    EditText etRePsd;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_register;
    }

    public void preRegister(String mAvatarPath) {
        String userName = etName.getText().toString();
        String phoneString = etPhone.getText().toString();
        String sex = etSex.getText().toString();
        String psd = etPsd.getText().toString();
        String rePsd = etRePsd.getText().toString();
        if (presenter.checkPsd(psd, rePsd) && presenter.checkUserName(userName)
                && presenter.checkMobile(phoneString)){
            presenter.register(mAvatarPath, userName, phoneString, psd,
                                sex.equals("男") ? 0 : 1);
        }
    }

    @Override
    protected RegisterContract.Presenter initPresent() {
        return new RegisterPresenter(this);
    }

    @Override
    public void registerSuccess() {
        presenter.postRegister();
    }
}
