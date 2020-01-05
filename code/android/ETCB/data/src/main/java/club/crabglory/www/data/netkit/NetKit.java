package club.crabglory.www.data.netkit;

import android.support.annotation.StringRes;

import java.util.concurrent.TimeUnit;

import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.data.DataKit;
import club.crabglory.www.data.R;
import club.crabglory.www.data.model.net.RspModel;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 1.为了防止对于retrofit 对象的重复书写，对其进行抽象表现为单例模式
 * 2.处理 csrf， 设置前后的拦截器
 *
 * @author KevinLeak
 */
public class NetKit {

    private static NetKit instance;
    private Retrofit retrofit;
    private OkHttpClient client;

    static {
        instance = new NetKit();
    }

    private NetKit() {
    }

    /**
     * @return 返回单例模式的netWorker
     */
    public static OkHttpClient getClient() {

        if (instance.client != null) {
            return instance.client;
        }

        instance.client = new OkHttpClient
                .Builder()
                .readTimeout(3, TimeUnit.SECONDS)   // 断网重连
                .retryOnConnectionFailure(true) //http数据log，日志中打印出HTTP请求&响应数据
                .build();
        return instance.client;

    }


    /**
     * 建立网络连接的单例，提高性能
     * 此处还需要对token值进行一个拦截，封装转发处理
     * @return 返回一个单例对象
     */
    public static Retrofit getRetrofit() {

        if (instance.retrofit != null) {
            return instance.retrofit;
        }
        instance.retrofit = new Retrofit.Builder()
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create(DataKit.Companion.getGson()))
                //设置网络请求的Url地址
                .baseUrl(RemoteService.Constance.BASE_URL)
                .client(NetKit.getClient())
                .build();

        return instance.retrofit;
    }

    /**
     * 与服务器建立连接的通道
     *
     * @return 返回一个带着与服务器交互的所有的基础接口
     */
    public static RemoteService remote() {

        return NetKit.getRetrofit().create(RemoteService.class);
    }


    public static void decodeRep(RspModel piece, DataSource.FailedCallback callback) {
        switch (piece.getStatus()) {
            case RspModel.SUCCEED:
                return;
            case RspModel.ERROR_NET:
                decodeRep(R.string.error_data_network_error, callback);
                break;
            case RspModel.ERROR_PASSWORD:
                decodeRep(R.string.error_password, callback);
                break;
            case RspModel.ERROR_REPEAT_LOGIN:
                decodeRep(R.string.error_repeat_login, callback);
                break;
            case RspModel.FORMAT_ERROR_AVATAR:
                decodeRep(R.string.error_form_avatar, callback);
                break;
            case RspModel.FORMAT_ERROR_FILE:
                decodeRep(R.string.error_format_file, callback);
                break;
            case RspModel.SAME_PHONE:
                decodeRep(R.string.error_same_phone, callback);
                break;
            case RspModel.SAME_USERNAME:
                decodeRep(R.string.error_same_username, callback);
            case RspModel.NULL_USER:
                decodeRep(R.string.error_null_user, callback);
                break;
            case RspModel.NULL_DATA:
                decodeRep(R.string.error_null_data, callback);
                break;
            case RspModel.EXIST_FRIENDS:
                decodeRep(R.string.error_friend_exit, callback);
                break;
        }
    }

    public static void decodeRep(@StringRes final int resId,
                                 final DataSource.FailedCallback callback) {
        if (callback != null) {
            callback.onDataNotAvailable(resId);
        }
    }

}
