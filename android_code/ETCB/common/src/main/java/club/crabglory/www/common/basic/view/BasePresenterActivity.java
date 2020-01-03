package club.crabglory.www.common.basic.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import club.crabglory.www.common.Application;
import club.crabglory.www.common.R;
import club.crabglory.www.common.basic.contract.BaseContract;

public abstract class BasePresenterActivity<Presenter extends BaseContract.Presenter>
        extends BaseActivity implements BaseContract.View<Presenter> {


    protected Presenter presenter;
    protected ProgressDialog dialog;
    private ProgressDialog mLoadingDialog;


    /**
     * View 必须具有展示错误信息的能力
     *
     * @param error 传入错误信息
     */
    @Override
    public void showError(int error) {
        hideLoading();
        Application.Companion.showToast(getActivity(), error);
    }

    @Override
    protected void initBefore() {
        super.initBefore();

        presenter = initPresent();
    }

    /**
     * 初始化presenter，要求子类必须实现
     */
    protected abstract Presenter initPresent();

    @Override
    public void setPresenter(Presenter presenter) {

        this.presenter = presenter;
    }


    /**
     * 展示进度栏
     */
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


    /**
     * 由于子类在都是fragment的情况下这个会被自动的复写
     * 我们不需要管她，在这里我们只是先声明一下我们子类具有这个方法
     * 方便我们在主线程和子线程之间的切换
     */
    @Override
    public Activity getActivity() {
        return this;
    }
}
