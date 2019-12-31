package club.crabglory.www.factory.repository;


import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.data.model.db.Book_Table;
import club.crabglory.www.data.model.db.Live;
import club.crabglory.www.data.model.db.Live_Table;
import club.crabglory.www.data.model.persistence.Account;
import club.crabglory.www.factory.contract.BookDataSource;
import club.crabglory.www.factory.contract.LiveDataSource;


public class DisplayLiveRepository extends BaseDbRepository<Live> implements LiveDataSource {


    private String displayId;

    public DisplayLiveRepository(String displayId) {
        super();
        this.displayId = displayId;
    }

    @Override
    public boolean isRequired(Live live) {
        return live.getUpper().getId().equals(displayId);
    }

    @Override
    public void load(SucceedCallback<List<Live>> callback) {
        super.load(callback);
        SQLite.select().from(Live.class)
                .where(Live_Table.upper_id.eq(displayId))
                .orderBy(Live_Table.createAt, false)
                .async()
                .queryListResultCallback(this)
                .execute();
    }
}
