package club.crabglory.www.data.model.dispatch;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import club.crabglory.www.common.basic.view.BaseActivity;
import club.crabglory.www.data.DataKit;
import club.crabglory.www.data.helper.DbHelper;
import club.crabglory.www.data.helper.MessageDataHelper;
import club.crabglory.www.data.model.db.Message;
import club.crabglory.www.data.model.view.MessageViewModel;

public class MessageDispatch implements MessageCenter {

    final static String TAG = "MessageDispatch";

    private static MessageCenter instance;
    //建立单线程
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private BaseActivity context;

    public static MessageCenter instance() {
        if (instance == null) {
            synchronized (MessageDispatch.class) {
                if (instance == null)
                    instance = new MessageDispatch();
            }
        }
        return instance;
    }


    @Override
    public void registerBroadCast(BaseActivity activity) {
        this.context = activity;
    }

    @Override
    public void dispatch(MessageViewModel... model) {
        if (model == null || model.length == 0) return;
        // 丢到单线程池中
        executor.execute(new ConversationModelHandler(model));
    }

    private class ConversationModelHandler implements Runnable {
        private MessageViewModel[] models;

        public ConversationModelHandler(MessageViewModel[] models) {
            this.models = models;
        }


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void run() {
            List<Message> messageList = new ArrayList<>();
            // 遍历
            for (MessageViewModel model : models) {
                // 卡片基础信息过滤，错误卡片直接过滤
                if (model == null || TextUtils.isEmpty(model.getId())
                        || TextUtils.isEmpty(model.getReceiverId())
                        || (TextUtils.isEmpty(model.getSenderId())))
                    continue;
                Log.e(TAG, "--------------");
                // 发送消息流程：写消息->发送网络->网络返回->存储本地->->刷新本地状态
                // 查询是否在本地数据库创建，如果已经创建了的，一定是从摸个数据的状态发生了变化，只需要做修改
                Message msg = MessageDataHelper.findFromLocal(model.getId());
                if (msg == null) {
                    // 没找到本地消息，初次在数据库存储
                    // 可能是本地数据的第一次发送
                    // 可能是后端推送过来的数据
                    msg = MessageViewModel.build(model);

                }else {
                    msg.setStatus(Message.STATUS_DONE);
                }
                messageList.add(msg);
            }
            Log.e(TAG, messageList.get(0).toString());
            if (messageList.size() > 0) {
                DbHelper.save(Message.class, messageList.toArray(new Message[0]));
            }
            for (Message msg : messageList){
                DataKit.Companion.messageArrivals(msg, context.getClass());
            }
        }
    }
}
