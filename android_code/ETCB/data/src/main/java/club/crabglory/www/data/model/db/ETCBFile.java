package club.crabglory.www.data.model.db;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.Date;

import club.crabglory.www.data.DataKit;

@Table(database = AppDatabase.class)
public class ETCBFile {
    // 主键
    @PrimaryKey
    private String id;
    @Column
    private String path;
    @Column
    private Date createAt;

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @NonNull
    @Override
    public String toString() {
        return DataKit.Companion.getGson().toJson(this);
    }
}
