package club.crabglory.www.common.basic.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import club.crabglory.www.common.Application;
import club.crabglory.www.common.R;
import club.crabglory.www.common.basic.contract.BaseContract;


public abstract class PresentToolActivity<Presenter extends BaseContract.Presenter>
        extends ToolbarActivity implements BaseContract.View<Presenter> {


    protected Presenter mPresenter;
    protected ProgressDialog mLoadingDialog;

    @Override
    protected void initBefore() {
        super.initBefore();

        mPresenter = initPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 界面关闭时进行销毁的操作
        if (mPresenter != null) {
            mPresenter.destroy();
            hideLoading();
        }
    }

    /**
     * 初始化Presenter
     *
     * @return Presenter
     */
    protected abstract Presenter initPresenter();

    @Override
    public void showError(int str) {
        // 不管你怎么样，我先隐藏我
        hideLoading();
        Application.Companion.showToast(getActivity(),str);
    }

    @Override
    public void showDialog() {
        ProgressDialog dialog = mLoadingDialog;
        if (dialog == null) {
            dialog = new ProgressDialog(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
            // 不可触摸取消
            dialog.setCanceledOnTouchOutside(false);
            // 强制取消关闭界面
            dialog.setCancelable(true);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });
            mLoadingDialog = dialog;
            dialog.setMessage(getText(R.string.prompt_loading));
            dialog.show();

        }
    }

    private void hideDialog() {
        ProgressDialog dialog = mLoadingDialog;
        if (dialog != null) {
            mLoadingDialog = null;
            dialog.dismiss();
        }
    }

    protected void hideLoading() {
        // 不管你怎么样，我先隐藏我
        hideDialog();
    }

    @Override
    public void setPresenter(Presenter present) {

        this.mPresenter = present;
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
