package club.crabglory.www.factory.presenter.chat;

import android.support.v7.util.DiffUtil;
import android.util.Log;

import java.util.List;

import club.crabglory.www.common.widget.recycler.RecyclerAdapter;
import club.crabglory.www.data.helper.AccountDataHelper;
import club.crabglory.www.data.model.db.Message;
import club.crabglory.www.data.model.db.User;
import club.crabglory.www.data.model.db.utils.DiffUiDataCallback;
import club.crabglory.www.data.model.persistence.Account;
import club.crabglory.www.factory.contract.ChatContract;
import club.crabglory.www.factory.repository.MessageRepository;

public class ChatUserPresenter extends ChatPresenter<ChatContract.UserView>
        implements ChatContract.Presenter{

    private String receiverId;

    public ChatUserPresenter(ChatContract.UserView view, String receiverId) {
        super(new MessageRepository(receiverId), view, receiverId, Message.RECEIVER_TYPE_NONE);
        this.receiverId = receiverId;
    }

    @Override
    public void start() {
        super.start();
        // 从本地拿这个人的信息
        User receiver = AccountDataHelper.findFromLocal(receiverId);
        getView().onInit(receiver);
    }
}
