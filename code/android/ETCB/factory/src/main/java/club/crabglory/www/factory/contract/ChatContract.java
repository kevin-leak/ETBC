package club.crabglory.www.factory.contract;


import java.util.List;

import club.crabglory.www.common.basic.contract.BaseContract;
import club.crabglory.www.data.model.db.Group;
import club.crabglory.www.data.model.db.GroupMember;
import club.crabglory.www.data.model.db.Message;
import club.crabglory.www.data.model.db.User;

public interface ChatContract {


    interface Presenter extends BaseContract.Presenter {

        // 发送文字
        void pushText(String content);

        // 发送语音
        void pushAudio(String path, long time);

        // 发送图片
        void pushImages(String[] paths);

        // 重新发送一个对话，返回是否调度成功
        boolean rePush(Message msg);

    }

    /**
     * @param <InitModel>
     * 这里先定义一个chat  view 的初始化数据的基类方法， 因为我们要基础的view 有三种，所以设置泛型
     */
    // 界面的基类
    interface View<InitModel> extends BaseContract.RecyclerView<Presenter, Message> {
        // 初始化的Model
        void onInit(InitModel model);
    }

    // 人聊天的界面
    interface UserView extends View<User> {

    }

    // 群聊天的界面
    interface GroupView extends View<Group> {
        // 显示管理员菜单
        void showAdminOption(boolean isAdmin);

        // 初始化成员信息
        void onInitGroupMembers(List<GroupMember> members, long moreCount);
    }

}
