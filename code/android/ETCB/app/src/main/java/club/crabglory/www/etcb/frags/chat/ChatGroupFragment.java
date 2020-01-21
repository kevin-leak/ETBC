package club.crabglory.www.etcb.frags.chat;

import android.view.View;

import java.util.List;

import club.crabglory.www.data.model.db.Group;
import club.crabglory.www.data.model.db.GroupMember;
import club.crabglory.www.etcb.R;
import club.crabglory.www.factory.contract.ChatContract;

public class ChatGroupFragment extends ChatFragment<Group> implements ChatContract.GroupView {


    @Override
    protected int getHeaderLayoutId() {
        return R.layout.hello;
    }

    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);


    }


    @Override
    public void onInit(Group group) {

    }

    @Override
    public void showAdminOption(boolean isAdmin) {

    }

    @Override
    public void onInitGroupMembers(List<GroupMember> members, long moreCount) {

    }

    /**
     * 初始化presenter，要求子类必须实现
     */
    @Override
    protected ChatContract.Presenter initPresent() {
        return null;
    }
}
