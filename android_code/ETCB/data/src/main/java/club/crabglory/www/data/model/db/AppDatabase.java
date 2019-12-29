package club.crabglory.www.data.model.db;


import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {
    static final String NAME = "ETCBDatabase";
    static final int VERSION = 2;
}
