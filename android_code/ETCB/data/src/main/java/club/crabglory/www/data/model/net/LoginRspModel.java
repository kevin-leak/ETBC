package club.crabglory.www.data.model.net;

import club.crabglory.www.data.DataKit;

public class LoginRspModel {
    private String phone;
    private String code;
    private boolean flag;

    public LoginRspModel(String phone, String code, boolean flag) {
        this.phone = phone;
        this.code = code;
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String toString() {
        return DataKit.Companion.getGson().toJson(this);
    }
}
