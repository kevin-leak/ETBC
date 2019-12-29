package club.crabglory.www.data.model.net;

import android.support.annotation.NonNull;

import club.crabglory.www.data.DataKit;

public class ModifyRspModel {
    public static final int VALUE_AVATAR = 0;
    public static final int VALUE_NAME = 1;
    public static final int VALUE_SEX = 2;
    public static final int VALUE_ADDRESS = 3;
    private String userId;
    private String code;
    private int type;

    public ModifyRspModel(String userId, String code, int type) {
        this.userId = userId;
        this.code = code;
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @NonNull
    @Override
    public String toString() {
        return DataKit.Companion.getGson().toJson(this);
    }
}
