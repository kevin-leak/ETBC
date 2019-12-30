package club.crabglory.www.data.helper;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.data.R;
import club.crabglory.www.data.model.db.User;
import club.crabglory.www.data.model.net.AccountRspModel;
import club.crabglory.www.data.model.net.LoginRspModel;
import club.crabglory.www.data.model.net.ModifyRspModel;
import club.crabglory.www.data.model.net.RegisterRspModel;
import club.crabglory.www.data.model.net.RspModel;
import club.crabglory.www.data.model.persistence.Account;
import club.crabglory.www.data.netkit.NetKit;
import club.crabglory.www.data.netkit.RemoteService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountDataHelper {

    public static void register(RegisterRspModel model, DataSource.Callback<User> callback) {
        String avatarUrl = FileDataHelper.fetchBackgroundFile(model.getAvatarUrl());
        if (TextUtils.isEmpty(avatarUrl)) {
            // 如果上传图片没有上传成功，则报错
            callback.onDataNotAvailable(R.string.error_data_unknown);
            // fixme local_test_register
                callback.onDataNotAvailable(R.string.local_test_register);
                AccountRspModel rspModel = new AccountRspModel();
                rspModel.setUser(model.toUser());
                Account.loginToLocal(rspModel);
                DbHelper.save(User.class, rspModel.getUser());
                callback.onDataLoaded(rspModel.getUser());
            // fixme local_test_register
            return;
        }
        RemoteService remote = NetKit.remote();
        Call<RspModel<AccountRspModel>> call = remote.accountRegister(model);
        call.enqueue(new AccountRspCallback(callback));

    }

    public static void login(LoginRspModel model, DataSource.Callback<User> callback) {
        // todo here to open login
        Log.e("AccountDataHelper", model.toString());
        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService remote = NetKit.remote();
        // 得到一个Call
        Call<RspModel<AccountRspModel>> call = remote.accountLogin(model);
        // 异步的请求
        call.enqueue(new AccountRspCallback(callback));

    }

    public static void rspCode(String phone, DataSource.Callback<User> callback) {
        Call<RspModel> task = NetKit.remote().rspCode(phone);
        try {
            task.execute();
        } catch (IOException e) {
            callback.onDataNotAvailable(R.string.error_data_network_error);
        }
    }

    public static void modify(ModifyRspModel model, DataSource.Callback<User> callback) {
        // fixme local_test_register
            callback.onDataNotAvailable(R.string.local_test);
            User user = model.toUser();
            DbHelper.save(User.class, user);
            callback.onDataLoaded(user);
            return;
        // fixme local_test_register
//        RemoteService remote = NetKit.remote();
//        Call<RspModel<AccountRspModel>> call = remote.accountModify(model);
//        call.enqueue(new AccountRspCallback(callback));
    }


    public static void loginOut(String id, final DataSource.Callback<String> callback) {
        // fixme just for test
        callback.onDataLoaded("ok");
        return;
        // fixme just for test
//        RemoteService remote = NetKit.remote();
//        final Call<RspModel<String>> logout = remote.logout(id);
//        logout.enqueue(new Callback<RspModel<String>>() {
//            @Override
//            public void onResponse(Call<RspModel<String>> call, Response<RspModel<String>> response) {
//                RspModel<String> body = response.body();
//                if (body != null && body.isSuccess()) {
//                    callback.onDataLoaded(body.getResult());
//                } else {
//                    if (body != null) {
//                        NetKit.decodeRep(body, callback);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RspModel<String>> call, Throwable t) {
////                Log.e(TAG, "onFailure: " + "out----------");
//            }
//        });

    }

    static class AccountRspCallback implements Callback<RspModel<AccountRspModel>> {
        private DataSource.Callback<User> callback;

        AccountRspCallback(DataSource.Callback<User> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<RspModel<AccountRspModel>> call,
                               Response<RspModel<AccountRspModel>> response) {
            Log.e("AccountDataHelper", "Account info come");
            RspModel<AccountRspModel> rspPiece = response.body();
            if (rspPiece != null && rspPiece.isSuccess()) {
                AccountRspModel accountPiece = rspPiece.getResult();
                User user = accountPiece.getUser();
                DbHelper.save(User.class, user);
                // 对数据进行本地化处理
                Account.loginToLocal(accountPiece);
                // todo c这里需要对推送的id 进行一个绑定
                if (Account.isComplete()) {
                    // fixme 判断绑定状态，是否绑定设备，这里做即时通信操作
//                    if (accountPiece.isBind()) {
//                        // 设置绑定状态为True
//                        Account.setBind(true);
//                    } else {
//                        bindPush(callback);
//                    }
                }
                // 不论如何都要回调
                if (callback != null)
                    callback.onDataLoaded(user);
            } else {
                if (rspPiece != null) {
                    NetKit.decodeRep(rspPiece, callback);
                }
            }
        }

        @Override
        public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable t) {
            // todo net
            Log.e("AccountDataHelper", "Account info ruin");
            call.cancel();
            callback.onDataNotAvailable(R.string.error_data_network_error);
        }
    }
}
