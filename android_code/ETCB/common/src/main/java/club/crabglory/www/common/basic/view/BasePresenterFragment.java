package club.crabglory.www.common.basic.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import club.crabglory.www.common.Application;
import club.crabglory.www.common.R;
import club.crabglory.www.common.basic.contract.BaseContract;


/**
 * 此类是为了建议统一的fragment View 中的一个标准，在其中可以通过他对想要继承的子类进行约束
 * 如：protected abstract void initPresent();
 * 同时我们将此类设置为抽象类，这同时使得基础的 BaseFragment 的约束不会改变
 * <p>
 * 记录一下：突然明白，理解了泛型是对属性的类型的一种扩展，抽象类型是对子类的一种约束，接口是对属性的一种扩展
 * <p>
 * 这里还统一实现了以fragment这一层次的抽象出来的功能，同时供子类复写
 *
 * @param <Presenter> 传入一个自己需求的presenter
 */
public abstract class BasePresenterFragment<Presenter extends BaseContract.Presenter>
        extends BaseFragment implements BaseContract.View<Presenter> {

    /**
     * 为了可以被子类调用 protected
     */
    protected Presenter presenter;
    private ProgressDialog dialog;
    private Context context;
    private ProgressDialog mLoadingDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        //在activity 和 fragment建立连接之前 初始化presenter
        initPresent();
    }

    /**
     * 初始化presenter，要求子类必须实现
     */
    protected abstract Presenter initPresent();

    @Override
    public void showError(int error) {
        hideLoading();
        Application.Companion.showToast((Activity) context, error);
    }

    @Override
    public void setPresenter(Presenter presenter) {

        this.presenter = presenter;
    }

    @Override
    public void showDialog() {
        ProgressDialog dialog = mLoadingDialog;
        if (dialog == null) {
            dialog = new ProgressDialog(this.getActivity(), R.style.Theme_AppCompat_Light_Dialog_Alert);
            // 不可触摸取消
            dialog.setCanceledOnTouchOutside(false);
            // 强制取消关闭界面
            dialog.setCancelable(true);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    BasePresenterFragment.this.getActivity().finish();
                }
            });
            mLoadingDialog = dialog;
            dialog.setMessage(getText(R.string.prompt_loading));
            dialog.show();
        }
    }

    protected void hideDialog() {
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


}
