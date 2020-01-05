package club.crabglory.www.data.model.net;

import club.crabglory.www.data.DataKit;
import club.crabglory.www.data.model.db.User;

public class AccountRspModel {
    // 用户基本信息
    private User user = new User();
    // 当前登录的账号
    private String account = "";
    // 标示是否已经绑定到了设备PushId
    private boolean isBind = false;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean isBind() {
        return isBind;
    }

    public void setBind(boolean bind) {
        isBind = bind;
    }

    public String toString() {
        return DataKit.Companion.getGson().toJson(this);
    }
}
