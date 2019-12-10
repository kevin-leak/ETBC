package club.crabglory.www.data;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import club.crabglory.www.common.Application;
import club.crabglory.www.data.persistence.Account;

public class DataKit {


    private final ExecutorService executor;
    private static DataKit dataKit;

    public DataKit() {
        executor = Executors.newFixedThreadPool(4);
    }

    public static void runOnAsync(Runnable runnable){
        DataKit.getInstance().executor.execute(runnable);
    }

    static {
        dataKit = new DataKit();
    }
    static DataKit getInstance(){
        return dataKit;
    }


    public static Application app() {
        return Application.getInstance();
    }

    public static void initDb() {
        // 初始化数据库
        FlowManager.init(new FlowConfig.Builder(app())
                .openDatabasesOnInit(true) // 数据库初始化的时候就开始打开
                .build());

        // 持久化的数据进行初始化
        Account.load(DataKit.app());

    }

}
