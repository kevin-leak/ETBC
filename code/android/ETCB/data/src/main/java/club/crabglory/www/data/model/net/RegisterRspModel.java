package club.crabglory.www.data.model.net;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.UUID;

import club.crabglory.www.data.DataKit;
import club.crabglory.www.data.model.db.User;
import club.crabglory.www.data.netkit.NetKit;

public class RegisterRspModel {


    private String avatarUrl;
    private String name;
    private String phone;
    private String password;
    private int sex;

    public RegisterRspModel(String avatarUrl, String name, String phone, String password, int sex) {
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.sex = sex;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarPath) {
        this.avatarUrl = avatarPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NonNull
    @Override
    public String toString() {
        return DataKit.Companion.getGson().toJson(this);
    }

    // fixme account for test
    public User toUser(){
        User user = new User();
        user.setAvatar(this.avatarUrl);
        user.setId(UUID.randomUUID().toString());
        user.setName(this.name);
        user.setSex(this.sex);
        user.setPhone(this.phone);
        user.setFavorite(0);
        user.setFollow(false);
        user.setFollowing(0);
        user.setAddress("");
        user.setModifyAt(new Date());
        user.setAlias(this.name);
        return user;
    }
}
