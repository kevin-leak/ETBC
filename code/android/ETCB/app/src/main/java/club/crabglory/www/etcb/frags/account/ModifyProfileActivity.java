package club.crabglory.www.etcb.frags.account;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.basic.view.ToolbarActivity;
import club.crabglory.www.data.model.net.ModifyRspModel;
import club.crabglory.www.data.model.persistence.Account;
import club.crabglory.www.etcb.R;
import club.crabglory.www.factory.presenter.account.ModifyProfilePresenter;

// fixme ModifyProfileActivity：地址的添加需要重新设定
public class ModifyProfileActivity extends ToolbarActivity {

    public static final String KEY = "ModifyProfileActivity-KEY_Books";
    private static int VALUE;
    private static ModifyProfilePresenter mPresenter = MineProfileActivity.presenter;
    @BindView(R.id.et_info)
    EditText etInfo;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.rl_sex)
    RelativeLayout rlSex;
    @BindView(R.id.sp_sex)
    Spinner spSex;


    @Override
    protected boolean initArgs(Bundle bundle) {
        ModifyProfileActivity.VALUE = bundle.getInt(ModifyProfileActivity.KEY);
        return super.initArgs(bundle);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initData() {
        super.initData();
        switch (ModifyProfileActivity.VALUE) {
            case ModifyRspModel.VALUE_NAME:
                etInfo.setText(Account.getUser().getName());
                break;
            case ModifyRspModel.VALUE_SEX:
                rlSex.setVisibility(View.VISIBLE);
                spSex.setPopupBackgroundDrawable(getResources().getDrawable(R.drawable.shape_frame));
                etInfo.setVisibility(View.GONE);
                break;
            case ModifyRspModel.VALUE_ADDRESS:
                etInfo.setText(Account.getUser().getAddress());
                break;
        }
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activty_profile_modify;
    }

    @OnClick(R.id.tv_save)
    public void onClick() {
        String info = etInfo.getText().toString();
        switch (ModifyProfileActivity.VALUE) {
            case ModifyRspModel.VALUE_NAME:
                mPresenter.modifyName(info);
                break;
            case ModifyRspModel.VALUE_SEX:
                mPresenter.modifySex(spSex.getSelectedItemPosition());
            case ModifyRspModel.VALUE_ADDRESS:
                mPresenter.modifyAddress(info);
                break;
        }
        finish();
    }


}
