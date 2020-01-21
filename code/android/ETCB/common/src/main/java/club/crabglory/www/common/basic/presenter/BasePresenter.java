package club.crabglory.www.common.basic.presenter;


import club.crabglory.www.common.R;
import club.crabglory.www.common.basic.contract.BaseContract;
import club.crabglory.www.common.utils.NetUtils;

/**
 * @author KevinLeak
 * 抽象实现present共同具有的功能
 * 1. 抽象出present而的构造方法，要求同时设置view，当所有的子类继承他时候，必须实现它
 * 2. 规划一个开始，和一个摧毁
 */
public class BasePresenter<T extends BaseContract.View> implements BaseContract.Presenter {


    public T mView;

    public BasePresenter(T view) {
        setView(view);
    }

    /**
     * 此处是为扩展，我们可能设置view的方法不一样，所有不放在初始化里面执行
     * @param view 传入所需view的实例对象
     */
    public void setView(T view){
        this.mView = view;
        this.mView.setPresenter(this);
    }

    public T getView(){
        return this.mView;
    }

    @Override
    public void start() {
        // 为了不再显示loading
        T view = mView;
        // fixme 关于加载框的显示，要慎重。
        if (view != null && !(this instanceof BaseRecyclerPresenter)) {
            view.showDialog();
        }
    }

    public boolean requsetNet() {
        if (!NetUtils.isNetworkConnected(mView.getActivity())){
            mView.showError(R.string.error_data_network_error);
            return true;
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
