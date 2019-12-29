package club.crabglory.www.common;

/**
 * 设置些不变的常量
 */
public class Common {

    // fixme 这里在后台进行调试的时候需要修改
    public interface Constance {
        String HTTP_HEAD = "http://";
        String WEB_SOCKET_HEAD = "ws://";
//        String BASE_IP_PORT = "192.168.136.111:8000/";
        String BASE_IP_PORT = "120.79.255.228:8000/";
        String BASE_PHONE_UTL = HTTP_HEAD + BASE_IP_PORT + "media/";
        String BASE_URL = HTTP_HEAD + BASE_IP_PORT + "android/";
        String WEB_VIEW_URL = BASE_URL + "web_view/";
        String WEB_SOCKET = WEB_SOCKET_HEAD + BASE_IP_PORT + "android/websocket/";
    }
}
