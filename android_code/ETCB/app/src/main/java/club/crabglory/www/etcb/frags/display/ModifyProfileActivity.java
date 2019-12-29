package club.crabglory.www.etcb.frags.display;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.Application;
import club.crabglory.www.common.basic.view.ToolbarActivity;
import club.crabglory.www.data.model.net.ModifyRspModel;
import club.crabglory.www.data.persistence.Account;
import club.crabglory.www.etcb.R;
import club.crabglory.www.factory.presenter.account.ModifyProfilePresenter;

// fixme ModifyProfileActivity：这里设计简陋，留待以后修改
public class ModifyProfileActivity extends ToolbarActivity {

    public static final String KEY = "ModifyProfileActivity-KEY_Books";
    public static final String KEY_PRESENTER = "ModifyProfileActivity-KEY_Books-PRESENTER";
    //    public static final int VALUE_AVATAR = 0;
    private static int VALUE;
    private static ModifyProfilePresenter mPresenter = MineProfileActivity.presenter;
    @BindView(R.id.et_info)
    EditText etInfo;
    @BindView(R.id.tv_save)
    TextView tvSave;


    @Override
    protected boolean initArgs(Bundle bundle) {
        ModifyProfileActivity.VALUE = bundle.getInt(ModifyProfileActivity.KEY);
        return super.initArgs(bundle);
    }

    @Override
    protected void initData() {
        super.initData();
        switch (ModifyProfileActivity.VALUE) {
            case ModifyRspModel.VALUE_NAME:
                etInfo.setText(Account.getUser().getName());
                break;
            case ModifyRspModel.VALUE_SEX:
                etInfo.setText(Account.getUser().getSex() == 0 ? "male" : "female");
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
                if (info.equals("male") || info.equals("female"))
                    mPresenter.modifySex(info.equals("male") ? 0 : 1);
                else
                    Application.Companion.showToast(this, R.string.error_form_sex);
                break;
            case ModifyRspModel.VALUE_ADDRESS:
                mPresenter.modifyAddress(info);
                break;
        }
        finish();
    }


}
