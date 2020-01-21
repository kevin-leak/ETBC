package club.crabglory.www.data.model.db;


import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.Date;

import club.crabglory.www.common.basic.model.Author;
import club.crabglory.www.data.DataKit;
import club.crabglory.www.data.model.db.utils.DiffUiDataCallback;
import club.crabglory.www.data.model.BaseDbModel;

@Table(database = AppDatabase.class)
public class User extends BaseDbModel<User> implements DiffUiDataCallback.UiDataDiffer<User>
    , Author {

    // 主键
    @PrimaryKey
    private String id = "";
    @Column
    private String name = "";
    @Column
    private String phone = "";
    @Column
    private String avatar = "";
    @Column
    private String address = "";
    @Column
    private int sex = 0;

    // 我对某人的备注信息，也应该写入到数据库中
    @Column
    private String alias = "";

    // 用户关注人的数量
    @Column
    private int follows = 0;

    // 用户粉丝的数量
    @Column
    private int following = 0;

    // 我与当前User的关系状态，是否已经关注了这个人
    @Column
    private boolean isFollow = false;

    @Column
    private float favorite = 0;

    // 时间字段
    @Column
    private Date modifyAt = new Date();


    public float getFavorite() {
        return favorite;
    }



    public void setFavorite(float favorite) {
        this.favorite = favorite;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean isSame(User old) {
        return false;
    }

    @Override
    public boolean isUiContentSame(User old) {
        return false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    @NonNull
    @Override
    public String toString() {
        return DataKit.Companion.getGson().toJson(this);
    }
}

