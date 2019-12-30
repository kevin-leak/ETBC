package club.crabglory.www.etcb.frags.account;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.Application;
import club.crabglory.www.common.basic.view.BasePresenterFragment;
import club.crabglory.www.data.model.persistence.Account;
import club.crabglory.www.etcb.MainActivity;
import club.crabglory.www.etcb.R;
import club.crabglory.www.factory.contract.LoginContract;
import club.crabglory.www.factory.presenter.account.LoginPresenter;

public class LoginFragment extends BasePresenterFragment<LoginContract.Presenter>
        implements LoginContract.View {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_way)
    EditText etWay;
    @BindView(R.id.login_way)
    ImageView loginWay;
    // forget 则用短信登入
    boolean flag = false;
    @BindView(R.id.cv_send)
    CardView cvSend;
    private CountDownTimer timer;


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }

    public void preLogin() {
        if (Account.isLogin()) return;
        String phoneString = etPhone.getText().toString();
        String code = etWay.getText().toString();
        presenter.login(phoneString, code, flag);
    }

    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);
        timer = new CountDownTimer(60 * 1000, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                ((TextView) cvSend.getChildAt(0)).setText("" + millisUntilFinished / 1000);
                cvSend.getChildAt(0).setClickable(false);
            }

            @Override
            public void onFinish() {
                ((TextView) cvSend.getChildAt(0)).setText(R.string.send);
                cvSend.getChildAt(0).setClickable(true);
            }
        };
    }

    @Override
    protected LoginContract.Presenter initPresent() {
        return new LoginPresenter(this);
    }

    @Override
    public void loginSuccess() {
        getActivity().setResult(MainActivity.successCode);
        getActivity().finish();
    }

    @Override
    public void showError(int error) {
        super.showError(error);
        getActivity().setResult(MainActivity.failCode);
    }

    @Override
    public boolean onBackPressed() {
        getActivity().setResult(MainActivity.failCode);
        return super.onBackPressed();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick(R.id.tv_forget)
    public void onClick(View v) {
        TextView view = (TextView) v;
        flag = !flag;
        if (flag) {
            loginWay.setImageResource(R.drawable.account_verification);
            view.setText(R.string.login_by_code);
            etWay.setHint(R.string.login_code);
            etWay.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            cvSend.setVisibility(View.VISIBLE);
            Application.Companion.showToast(Objects.requireNonNull(LoginFragment.this.getActivity()), R.string.login_by_code);
        } else {
            loginWay.setImageResource(R.drawable.account_psd);
            view.setText(R.string.forget_password);
            etWay.setHint(R.string.psdHint);
            etWay.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            cvSend.setVisibility(View.GONE);
            Application.Companion.showToast(Objects.requireNonNull(LoginFragment.this.getActivity()), R.string.psdHint);
        }
    }

    @OnClick(R.id.cv_send)
    public void onClick() {
        String phoneString = etPhone.getText().toString();
        if (presenter.checkMobile(phoneString)) {
            timer.start();
            presenter.sendCodeRsp(phoneString);
        }

    }
}
