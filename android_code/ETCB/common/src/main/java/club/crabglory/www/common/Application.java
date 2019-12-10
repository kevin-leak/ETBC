package club.crabglory.www.common;

import android.app.Activity;
import android.os.SystemClock;
import android.support.annotation.StringRes;
import android.widget.Toast;

import java.io.File;

/**
 * 做为一个维护应用程序全局状态的基类
 *
 * @author KevinLeak
 */
public class Application extends android.app.Application {

    private static Application instance;

    /**
     * @return 返回一个临时缓存的文件路径， 放在cache文件夹下的avatar下
     * 为了区别文件名字，我们以时间戳问名字进行名
     */
    public static File getAvatarTmpFile() {

        // 得到头像目录的缓存地址
        File dir = new File(getCacheDirFile(), "avatar");
        // 创建所有的对应的文件夹
        //noinspection ResultOfMethodCallIgnored
        dir.mkdirs();

        // 删除旧的一些缓存文件
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }
        }

        // 返回一个当前时间戳的目录文件地址
        File path = new File(dir, SystemClock.uptimeMillis() + ".jpg");
        return path.getAbsoluteFile();
    }

    /**
     * 获取缓存文件夹地址
     *
     * @return 当前APP的缓存文件夹地址
     */
    public static File getCacheDirFile() {
        return instance.getCacheDir();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }


    /**
     * 显示一个Toast
     *
     * @param msg 字符串
     */
    public static void showToast(Activity activity, final String msg) {
        // Toast 只能在主线程中显示，所有需要进行线程转换，
        // 保证一定是在主线程进行的show操作
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(instance, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 显示一个Toast
     *
     * @param msgId 传递的是字符串的资源
     */
    public static void showToast(Activity activity, @StringRes int msgId) {
        showToast(activity, instance.getString(msgId));
    }

    /**
     * @return 获取到修饰过的app的单例对象
     */
    public static Application getInstance() {
        return instance;
    }


}
