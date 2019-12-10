package club.crabglory.www.etcb;


import club.crabglory.www.common.Application;
import club.crabglory.www.data.DataKit;
import club.crabglory.www.data.db.User;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // todo

        DataKit.initDb();
        new User();
    }

}
