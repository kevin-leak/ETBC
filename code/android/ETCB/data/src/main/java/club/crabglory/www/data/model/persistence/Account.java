package club.crabglory.www.data.model.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import club.crabglory.www.data.DataKit;

import club.crabglory.www.data.model.StaticData;
import club.crabglory.www.data.model.db.User;
import club.crabglory.www.data.model.db.User_Table;
import club.crabglory.www.data.model.net.AccountRspModel;

public class Account {

    private static String userId = "";
    // fixme 留待以后进行推送下线;
    private static String token = "";
    // fixme 留待以后做消息推送的时候进行修改;
    private static String pushId = "";
    private static boolean isBind = false;

    // 持久化键
    private static final String KEY_PUSH_ID = "KEY_PUSH_ID";
    private static final String KEY_USER_ID = "KEY_USER_ID";
    private static final String KEY_IS_BIND = "KEY_IS_BIND";
    private static final String KEY_TOKEN = "KEY_TOKEN";


    public static boolean isLogin(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(), Context.MODE_PRIVATE);
        Account.userId = sp.getString(KEY_USER_ID, "");
        // fixme just for test
//        return !TextUtils.isEmpty(userId) && !TextUtils.isEmpty(token);
        return !TextUtils.isEmpty(userId);
    }

    public static boolean isLogin() {
        // fixme just for test
//        return !TextUtils.isEmpty(userId) && !TextUtils.isEmpty(token);
        return !TextUtils.isEmpty(userId);
    }

    public static void load(Context context) {

        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(), Context.MODE_PRIVATE);
        Account.pushId = sp.getString(KEY_PUSH_ID, "");
        Account.userId = sp.getString(KEY_USER_ID, "");
        Account.token = sp.getString(KEY_TOKEN, "");
        Account.isBind = sp.getBoolean(KEY_IS_BIND, false);
    }

    public static void loginToLocal(AccountRspModel model) {
        Account.userId = model.getUser().getId();
        save(DataKit.Companion.app());
    }

    public static void save(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(), Context.MODE_PRIVATE);
        sp.edit().putBoolean(KEY_IS_BIND, isBind)
                .putString(KEY_PUSH_ID, pushId)
                .putString(KEY_TOKEN, token)
                .putString(KEY_USER_ID, userId)
                .apply();
    }

    public static User getUser() {
        return SQLite.select().from(User.class)
                .where(User_Table.id.eq(userId)).
                        querySingle();
    }

    public static String getUserId() {
        if (getUser() == null) return "";
        return getUser().getId();
    }

    public static boolean isComplete() {
        // fixme 个人信息与登入注册分离，这后面再说
        return true;
    }

    public static boolean removeLogin(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(), Context.MODE_PRIVATE);
        getUser().setId("");
        sp.edit().clear().apply();
        return true;
    }
}
