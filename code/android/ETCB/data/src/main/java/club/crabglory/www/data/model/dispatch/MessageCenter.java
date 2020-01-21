package club.crabglory.www.data.model.dispatch;


import club.crabglory.www.common.basic.view.BaseActivity;
import club.crabglory.www.data.model.view.MessageViewModel;

public interface MessageCenter {

    void registerBroadCast(BaseActivity activity);

    // 分发处理一堆用户卡片的信息，并更新到数据库
    void dispatch(MessageViewModel... cards);

}
