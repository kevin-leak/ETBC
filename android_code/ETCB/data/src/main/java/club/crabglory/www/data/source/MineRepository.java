package club.crabglory.www.data.source;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import club.crabglory.www.data.contract.UserDataSource;
import club.crabglory.www.data.model.db.User;
import club.crabglory.www.data.model.db.User_Table;
import club.crabglory.www.data.persistence.Account;

public class MineRepository extends BaseDbRepository<User> implements UserDataSource {

    @Override
    public void load(SucceedCallback<List<User>> callback) {
        super.load(callback);
        // todo, 加载个人信息
//        if (Account.isLogin())
        SQLite.select().from(User.class)
                .where(User_Table.id.eq(Account.getUserId()))
                .async()
                .queryListResultCallback(this)
                .execute();
    }

    @Override
    public boolean isRequired(User user) {
        return true;
    }

}
