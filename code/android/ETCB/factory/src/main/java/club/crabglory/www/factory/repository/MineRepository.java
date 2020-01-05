package club.crabglory.www.factory.repository;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import club.crabglory.www.data.helper.AccountDataHelper;
import club.crabglory.www.factory.contract.AccountDataSource;
import club.crabglory.www.data.model.persistence.Account;
import club.crabglory.www.data.model.db.User;
import club.crabglory.www.data.model.db.User_Table;

public class MineRepository extends BaseDbRepository<User> implements AccountDataSource {

    @Override
    public void load(SucceedCallback<List<User>> callback) {
        super.load(callback);
        AccountDataHelper.getFromLocal(Account.getUserId(), this);
    }

    @Override
    public boolean isRequired(User user) {
        return true;
    }

}
