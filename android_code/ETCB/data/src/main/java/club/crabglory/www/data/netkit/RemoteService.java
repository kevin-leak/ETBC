package club.crabglory.www.data.netkit;

import java.util.List;

import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.data.model.db.Goods;
import club.crabglory.www.data.model.db.Live;
import club.crabglory.www.data.model.db.Micro;
import club.crabglory.www.data.model.net.AccountRspModel;
import club.crabglory.www.data.model.db.ETCBFile;
import club.crabglory.www.data.model.net.MaterialRspModel;
import club.crabglory.www.data.model.net.FileRspModel;
import club.crabglory.www.data.model.net.LoginRspModel;
import club.crabglory.www.data.model.net.ModifyRspModel;
import club.crabglory.www.data.model.net.PayRspModel;
import club.crabglory.www.data.model.net.RegisterRspModel;
import club.crabglory.www.data.model.net.RspModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author KevinLeak
 * 里面记录所有的网络接口
 * <p>
 * model是用来返回数据，用于gson 转化
 * piece 请求碎片，是用来封装请求信息。
 */
public interface RemoteService {

    // fixme 这里在后台进行调试的时候需要修改
    interface Constance {
        String HTTP_HEAD = "http://";
        String WEB_SOCKET_HEAD = "ws://";
        //        String BASE_IP_PORT = "192.168.136.111:8000/";
        String BASE_IP_PORT = "120.79.255.228:8000/";
        String BASE_PHONE_UTL = HTTP_HEAD + BASE_IP_PORT + "media/";
        String BASE_URL = HTTP_HEAD + BASE_IP_PORT + "android/";
        String WEB_VIEW_URL = BASE_URL + "web_view/";
        String WEB_SOCKET = WEB_SOCKET_HEAD + BASE_IP_PORT + "android/websocket/";
    }


    @POST("account/login/")
    Call<RspModel<AccountRspModel>> accountLogin(@Body LoginRspModel model);

    @POST("file/")
    Call<RspModel<ETCBFile>> saveFile(@Body FileRspModel model);

    @POST("account/register/")
    Call<RspModel<AccountRspModel>> accountRegister(@Body RegisterRspModel model); // 此处，后端需要request里面的body接受数据

    @GET("account/out/")
    Call<RspModel<String>> logout(@Body String id); // 此处，后端需要request里面的body接受数据

    @GET("account/codeRsp/")
    Call<RspModel> rspCode(@Body String phone);

    @POST("account/modify/")
    Call<RspModel<AccountRspModel>> accountModify(@Body ModifyRspModel model);

    @GET("live/{livePullId}/")
    Call<RspModel<List<Live>>> pullLive(@Path("livePullId") String liveGetId);

    @GET("micro/{microPullId}/")
    Call<RspModel<List<Micro>>> pullMicro(@Path("microPullId") String microPullId);

    @POST("goods/pullGoods/")
    Call<RspModel<List<Goods>>> pullGoods(@Body MaterialRspModel model);

    @POST("goods/pay/")
    Call<RspModel<List<Goods>>> payGoods(@Body List<PayRspModel> rspModels );

    @POST("goods/delete/")
    Call<RspModel<String>> deleteGoods(@Body List<String> idList);

    @POST("book/upBook/")
    Call<RspModel<Book>> upBook(@Body Book book);

    @POST("book/pullBook/")
    Call<RspModel<List<Book>>> pullBook(@Body MaterialRspModel model);

    @GET("book/pullByID/{bookPullId}/")
    Call<RspModel<Book>> pullBookByID(@Path("bookPullId") String bookId);

    @GET("book/delete/{bookDeleteId}/")
    Call<RspModel<String>> deleteBook(@Path("bookPullId") String bookId);
}
