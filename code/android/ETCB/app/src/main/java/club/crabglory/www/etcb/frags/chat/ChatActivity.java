package club.crabglory.www.etcb.frags.chat;

import android.os.Bundle;
import android.util.Log;

import javax.security.auth.login.LoginException;

import club.crabglory.www.common.basic.view.BaseActivity;
import club.crabglory.www.common.basic.view.ToolbarActivity;
import club.crabglory.www.etcb.R;

/**
 * 聊天窗口
 */
public class ChatActivity extends ToolbarActivity {

    public static final String KEY_RECEIVE_ID = "KEY_Reciver_ID";
    public static final String KEY_IS_GROUP = "KEY_IS_GROUP";
    private final String tag = ChatActivity.this.getClass().getName();
    private boolean isGroup;

    public static void showUserChat(BaseActivity activity, String useID) {
        Bundle bundle;
        bundle = new Bundle();
        bundle.putString(ChatActivity.KEY_RECEIVE_ID, useID);
        bundle.putBoolean(ChatActivity.KEY_IS_GROUP, false);
        ChatActivity.show(activity, ChatActivity.class, bundle, false);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_chat;
    }



    @Override
    protected boolean initArgs(Bundle bundle) {
        if (bundle != null) {
            isGroup = bundle.getBoolean(ChatActivity.KEY_IS_GROUP, false);
        }
        return super.initArgs(bundle);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        ChatFragment chatFragment;
        if (isGroup) {
            chatFragment = new ChatGroupFragment();
        } else {
            chatFragment = new ChatUserFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, chatFragment)
                .commit();
    }
}
