package club.crabglory.www.data.model.net;

import club.crabglory.www.data.DataKit;

public class LoginRspModel {
    private String code;
    private String password;
    private boolean flag;

    public LoginRspModel(String code, String password, boolean flag) {
        this.code = code;
        this.password = password;
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String phone) {
        this.code = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return DataKit.Companion.getGson().toJson(this);
    }
}
