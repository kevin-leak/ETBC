package club.crabglory.www.etcb.frags.chat;


import android.os.Bundle;
import android.view.View;

import club.crabglory.www.common.basic.view.ToolbarActivity;
import club.crabglory.www.data.model.db.Message;
import club.crabglory.www.data.model.db.User;
import club.crabglory.www.etcb.R;
import club.crabglory.www.factory.contract.ChatContract;
import club.crabglory.www.factory.presenter.chat.ChatUserPresenter;


public class ChatUserFragment extends ChatFragment<User> implements ChatContract.UserView {


    @Override
    protected int getHeaderLayoutId() {
        return R.layout.fragment_chat_user;
    }

    @Override
    public void onInit(User user) {
        if (user != null)
            mToolbar.setTitle(user.getName());
    }

    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);
    }

    /**
     * 初始化presenter，要求子类必须实现
     */
    @Override
    protected ChatContract.Presenter initPresent() {
        return new ChatUserPresenter(this, receiveId);
    }


}
