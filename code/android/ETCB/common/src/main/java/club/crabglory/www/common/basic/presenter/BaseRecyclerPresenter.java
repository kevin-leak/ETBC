package club.crabglory.www.common.basic.presenter;


import android.provider.ContactsContract;
import android.support.v7.util.DiffUtil;

import java.util.List;

import club.crabglory.www.common.widget.recycler.RecyclerAdapter;
import club.crabglory.www.common.basic.contract.BaseContract;

/**
 * 对具有recycleview的view 再一次封装，添加两个刷新数据的功能方法
 */
class BaseRecyclerPresenter<ViewMode, View extends BaseContract.RecyclerView>
        extends BasePresenter<View> {

    BaseRecyclerPresenter(View view) {
        super(view);
    }

    /**
     * 刷新一堆新数据到界面中
     *
     * @param dataList 新数据
     */
    protected void refreshData(final List<ViewMode> dataList) {
        if (getView().getActivity() == null) return;
        getView().getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View view = getView();
                if (view == null) return;
                // 基本的更新数据并刷新界面
                RecyclerAdapter<ViewMode> adapter = view.getRecyclerAdapter();
                if (adapter != null) {
                    adapter.replace(dataList);
                    view.onAdapterDataChanged();
                }
            }
        });
    }

    protected void refreshData(final DiffUtil.DiffResult diffResult, final List<ViewMode> dataList) {
        if (getView().getActivity() != null)
            getView().getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // 这里是主线程运行时
                    refreshDataOnUiThread(diffResult, dataList);
                }
            });
    }


    private void refreshDataOnUiThread(final DiffUtil.DiffResult diffResult, final List<ViewMode> dataList) {
        View view = getView();
        if (view == null)
            return;
        // 基本的更新数据并刷新界面
        RecyclerAdapter<ViewMode> adapter = view.getRecyclerAdapter();
        // 改变数据集合并不通知界面刷新
        adapter.getItems().clear();
        adapter.getItems().addAll(dataList);
        adapter.notifyDataSetChanged();
//         通知界面刷新占位布局
        view.onAdapterDataChanged();
//         进行增量更新
        diffResult.dispatchUpdatesTo(adapter);

    }


}
