package club.crabglory.www.factory.repository;


import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import club.crabglory.www.data.helper.MicroDataHelper;
import club.crabglory.www.data.model.db.Live;
import club.crabglory.www.data.model.db.Live_Table;
import club.crabglory.www.data.model.db.Micro;
import club.crabglory.www.data.model.db.Micro_Table;
import club.crabglory.www.factory.contract.LiveDataSource;
import club.crabglory.www.factory.contract.MicroDataSource;


public class DisplayMicroRepository extends BaseDbRepository<Micro> implements MicroDataSource {


    private String displayId;

    public DisplayMicroRepository(String displayId) {
        super();
        this.displayId = displayId;
    }

    @Override
    public boolean isRequired(Micro micro) {
        return micro.getUpper().getId().equals(displayId);
    }

    @Override
    public void load(SucceedCallback<List<Micro>> callback) {
        super.load(callback);
        MicroDataHelper.getFromLocal(displayId, this);
    }
}
