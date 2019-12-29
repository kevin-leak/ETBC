package club.crabglory.www.etcb.frags.account;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import club.crabglory.www.common.basic.view.BasePresenterFragment;
import club.crabglory.www.etcb.MainActivity;
import club.crabglory.www.etcb.R;
import club.crabglory.www.factory.contract.RegisterContract;
import club.crabglory.www.factory.presenter.account.RegisterPresenter;

public class RegisterFragment extends BasePresenterFragment<RegisterContract.Presenter>
        implements RegisterContract.View {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.sp_sex)
    Spinner spSex;
    @BindView(R.id.et_psd)
    EditText etPsd;
    @BindView(R.id.et_re_psd)
    EditText etRePsd;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_register;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);
        spSex.setPopupBackgroundDrawable(getResources().getDrawable(R.drawable.shape_frame));
    }


    public void preRegister(String mAvatarPath) {
        String userName = etName.getText().toString();
        String phoneString = etPhone.getText().toString();
        if (TextUtils.isEmpty(mAvatarPath)) {
            Resources resources = getResources();
            mAvatarPath = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                    + resources.getResourcePackageName(R.mipmap.avatar) + "/"
                    + resources.getResourceTypeName(R.mipmap.avatar) + "/"
                    + resources.getResourceEntryName(R.mipmap.avatar);
        }
        int sex = spSex.getSelectedItemPosition();
        String psd = etPsd.getText().toString();
        String rePsd = etRePsd.getText().toString();
        if (presenter.checkPsd(psd, rePsd) && presenter.checkUserName(userName)
                && presenter.checkMobile(phoneString)) {
            presenter.register(mAvatarPath, userName, phoneString, psd, sex);
        }

    }

    @Override
    protected RegisterContract.Presenter initPresent() {
        return new RegisterPresenter(this);
    }

    @Override
    public void registerSuccess() {
        getActivity().setResult(MainActivity.successCode);
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
}
