package club.crabglory.www.factory.presenter.account;

import android.util.Log;

import java.io.File;
import java.io.IOException;

import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.common.utils.StringsUtils;
import club.crabglory.www.data.model.db.DbHelper;
import club.crabglory.www.data.model.db.ETCBFile;
import club.crabglory.www.data.model.db.User;
import club.crabglory.www.data.model.net.AccountRspModel;
import club.crabglory.www.data.model.net.FileRspModel;
import club.crabglory.www.data.model.net.LoginRspModel;
import club.crabglory.www.data.model.net.ModifyRspModel;
import club.crabglory.www.data.model.net.RegisterRspModel;
import club.crabglory.www.data.model.net.RspModel;
import club.crabglory.www.data.netkit.NetKit;
import club.crabglory.www.data.netkit.RemoteService;
import club.crabglory.www.data.persistence.Account;
import club.crabglory.www.factory.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountHelper {

    static void register(RegisterRspModel model, DataSource.Callback<User> callback) {
        // todo here to open register
        Log.e("AccountHelper", model.toString());
        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService remote = NetKit.remote();
        // 得到一个Call
        Call<RspModel<AccountRspModel>> call = remote.accountRegister(model);
        // 异步的请求
        call.enqueue(new AccountRspCallback(callback));

    }

    static void login(LoginRspModel model, DataSource.Callback<User> callback) {
        // todo here to open login
        Log.e("AccountHelper", model.toString());
        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService remote = NetKit.remote();
        // 得到一个Call
        Call<RspModel<AccountRspModel>> call = remote.accountLogin(model);
        // 异步的请求
        call.enqueue(new AccountRspCallback(callback));

    }

    static void rspCode(String phone, DataSource.Callback<User> callback) {
        Call<RspModel> task = NetKit.remote().rspCode(phone);
        try {
            task.execute();
        } catch (IOException e) {
            callback.onDataNotAvailable(R.string.error_data_network_error);
        }
    }

    public static void modify(ModifyRspModel model, DataSource.Callback<User> callback) {
        // todo here to open modify
        Log.e("AccountHelper", model.toString());
        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService remote = NetKit.remote();
        // 得到一个Call
        Call<RspModel<AccountRspModel>> call = remote.accountModify(model);
        // 异步的请求
        call.enqueue(new AccountRspCallback(callback));
    }


    public static void loginOut(String id, final DataSource.Callback<String> callback) {

        RemoteService remote = NetKit.remote();
        final Call<RspModel<String>> logout = remote.logout(id);
        logout.enqueue(new Callback<RspModel<String>>() {
            @Override
            public void onResponse(Call<RspModel<String>> call, Response<RspModel<String>> response) {
                RspModel<String> body = response.body();
                if (body != null && body.isSuccess()) {
                    callback.onDataLoaded(body.getResult());
                } else {
                    if (body != null) {
                        NetKit.decodeRep(body, callback);
                    }
                }
            }

            @Override
            public void onFailure(Call<RspModel<String>> call, Throwable t) {
//                Log.e(TAG, "onFailure: " + "out----------");
            }
        });

    }

    static class AccountRspCallback implements Callback<RspModel<AccountRspModel>> {
        private DataSource.Callback<User> callback;

        AccountRspCallback(DataSource.Callback<User> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<RspModel<AccountRspModel>> call,
                               Response<RspModel<AccountRspModel>> response) {
            Log.e("AccountHelper", "Account info come");
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
            Log.e("AccountHelper", "Account info ruin");
            call.cancel();
            callback.onDataNotAvailable(R.string.error_data_network_error);
        }
    }
}
