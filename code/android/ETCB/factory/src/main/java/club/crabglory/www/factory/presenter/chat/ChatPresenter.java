package club.crabglory.www.factory.presenter.chat;

import android.support.v7.util.DiffUtil;


import java.util.List;

import club.crabglory.www.common.basic.presenter.RecyclerSourcePresenter;
import club.crabglory.www.common.basic.view.BaseActivity;
import club.crabglory.www.data.DataKit;
import club.crabglory.www.data.helper.MessageDataHelper;
import club.crabglory.www.data.model.db.Message;
import club.crabglory.www.data.model.db.utils.DiffUiDataCallback;
import club.crabglory.www.data.model.net.MessageRspModel;
import club.crabglory.www.factory.contract.ChatContract;
import club.crabglory.www.factory.contract.MessageDataSource;

public class ChatPresenter<View extends ChatContract.View>
        extends RecyclerSourcePresenter<Message, Message, MessageDataSource, View>
        implements ChatContract.Presenter {

    public final static String TAG = ChatPresenter.class.getName();

    private String receiverId;
    private int receiverType;

    public ChatPresenter(MessageDataSource source, View view,
                         String receiverId, int receiverType) {
        super(source, view);
        this.receiverId = receiverId;
        this.receiverType = receiverType;
    }

    @Override
    public void onDataLoaded(List<Message> conversations) {
        ChatContract.View view = getView();
        if (view == null)
            return;

        // 拿到老数据
        @SuppressWarnings("unchecked")
        List<Message> old = view.getRecyclerAdapter().getItems();

        // 差异计算
        DiffUiDataCallback<Message> callback = new DiffUiDataCallback<>(old, conversations);
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        // 进行界面刷新
        refreshData(result, conversations);
    }

    @Override
    public void pushText(String content) {
        requsetNet();
        // 设置数据通知
        DataKit.Companion.getMessageCenter().registerBroadCast((BaseActivity) mView.getActivity());
        MessageRspModel pieces = new MessageRspModel.Builder()
                .setReceiver(receiverId, receiverType)
                .addContent(content, Message.TYPE_STR).build();
        MessageDataHelper.push(pieces);
    }

    @Override
    public void pushAudio(String path, long time) {
        // 设置数据通知
        DataKit.Companion.getMessageCenter().registerBroadCast((BaseActivity) mView.getActivity());
        requsetNet();
        // content中放置path
        // 设置数据通知
        // fixme 大文件传输
        MessageRspModel pieces = new MessageRspModel.Builder()
                .setReceiver(receiverId, receiverType)
                .addContent(path, Message.TYPE_AUDIO)
                .addAttach("" + time).build();
        MessageDataHelper.push(pieces);
    }

    @Override
    public void pushImages(String[] paths) {
        // 设置数据通知
        DataKit.Companion.getMessageCenter().registerBroadCast((BaseActivity) mView.getActivity());

    }

    @Override
    public boolean rePush(Message conversation) {
        return false;
    }

}
