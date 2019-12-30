package club.crabglory.www.factory.presenter.account;

import android.text.TextUtils;
import android.util.Log;

import java.io.Serializable;

import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.common.basic.presenter.BasePresenter;
import club.crabglory.www.data.helper.AccountDataHelper;
import club.crabglory.www.factory.Factory;
import club.crabglory.www.factory.contract.ModifyProfileContract;
import club.crabglory.www.data.model.db.User;
import club.crabglory.www.data.model.net.ModifyRspModel;
import club.crabglory.www.data.model.persistence.Account;
import club.crabglory.www.factory.R;
import club.crabglory.www.data.helper.FileDataHelper;

public class ModifyProfilePresenter extends BasePresenter<ModifyProfileContract.View>
        implements ModifyProfileContract.Presenter, DataSource.Callback<User>, Serializable {

    public ModifyProfilePresenter(ModifyProfileContract.View view) {
        super(view);
    }

    @Override
    public void modifyName(final String name) {
        mView.showDialog();
        Factory.Companion.runOnAsync(new Runnable() {
            @Override
            public void run() {
                ModifyRspModel model = new ModifyRspModel(Account.getUserId(), name, ModifyRspModel.VALUE_NAME);
                AccountDataHelper.modify(model, ModifyProfilePresenter.this);
            }
        });
    }

    @Override
    public void modifySex(final int sex) {
        mView.showDialog();
        Factory.Companion.runOnAsync(new Runnable() {
            @Override
            public void run() {
                ModifyRspModel model = new ModifyRspModel(Account.getUserId(), sex + "", ModifyRspModel.VALUE_SEX);
                AccountDataHelper.modify(model, ModifyProfilePresenter.this);
            }
        });
    }

    @Override
    public void modifyAddress(final String address) {
        mView.showDialog();
        Factory.Companion.runOnAsync(new Runnable() {
            @Override
            public void run() {
                ModifyRspModel model = new ModifyRspModel(Account.getUserId(), address, ModifyRspModel.VALUE_ADDRESS);
                AccountDataHelper.modify(model, ModifyProfilePresenter.this);
            }
        });

    }

    @Override
    public void modifyAvatar(final String mAvatarPath) {
        mView.showDialog();
        Factory.Companion.runOnAsync(new Runnable() {
            @Override
            public void run() {
                String avatarUrl = FileDataHelper.fetchBackgroundFile(mAvatarPath);
                if (TextUtils.isEmpty(avatarUrl)) {
                    // 如果上传图片没有上传成功，则报错
                    ModifyProfilePresenter.this.onDataNotAvailable(R.string.error_data_unknown);
                    return;
                }
                ModifyRspModel model = new ModifyRspModel(Account.getUserId(), avatarUrl, ModifyRspModel.VALUE_ADDRESS);
                AccountDataHelper.modify(model, ModifyProfilePresenter.this);
            }
        });
    }

    @Override
    public void onDataLoaded(User user) {
        final ModifyProfileContract.View view = getView();
        if (view == null)
            return;
        if (user != null) {
            view.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("ModifyProfilePresenter:", "show modifySuccess");
                    view.modifySuccess();
                }
            });
        }

    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        final ModifyProfileContract.View view = getView();
        view.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("ModifyProfilePresenter:", "show error");
                view.showError(strRes);
            }
        });
    }
}
