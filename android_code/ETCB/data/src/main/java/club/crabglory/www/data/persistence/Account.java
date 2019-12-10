package club.crabglory.www.data.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import club.crabglory.www.data.DataKit;
import club.crabglory.www.data.db.User;
import club.crabglory.www.data.model.UserRspModel;

public class Account {

    private static String userId;
    // fixme 留待以后进行推送下线;
    private static String token = "";
    // fixme 留待以后做消息推送的时候进行修改;
    private static String pushId;
    private static boolean isBind = false;

    // 持久化键
    private static final String KEY_PUSH_ID = "KEY_PUSH_ID";
    private static final String KEY_USER_ID = "KEY_USER_ID";
    private static final String KEY_IS_BIND = "KEY_USER_ID";
    private static final String KEY_TOKEN = "KEY_TOKEN";


    public static boolean isLogin() {
        return !TextUtils.isEmpty(userId) && !TextUtils.isEmpty(token);
    }

    public static void load(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(), Context.MODE_PRIVATE);
        Account.pushId = sp.getString(KEY_PUSH_ID, "");
        Account.userId = sp.getString(KEY_USER_ID, "");
        Account.token = sp.getString(KEY_TOKEN, "");
        Account.isBind = sp.getBoolean(KEY_IS_BIND, false);

    }

    public static void loginToLocal(UserRspModel model){
        Account.userId = model.getUserId();
        save(DataKit.app());
    }

    public static void save(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(), Context.MODE_PRIVATE);
        sp.edit()
                .putString(KEY_PUSH_ID, pushId)
                .putString(KEY_USER_ID, userId)
                .putBoolean(KEY_IS_BIND, isBind)
                .putString(KEY_TOKEN, token)
                .apply();
    }

    public static User getUser(){
        // fixme 返回数据库的用户
        return SQLite.select().from(User.class).querySingle();
    }
    public static String getUserId(){
        return getUser().getId();
    }
}
