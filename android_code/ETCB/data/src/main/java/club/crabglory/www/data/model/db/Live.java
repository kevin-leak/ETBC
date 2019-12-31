package club.crabglory.www.data.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.Date;

import club.crabglory.www.data.model.BaseDdModel;
import club.crabglory.www.data.model.db.utils.DiffUiDataCallback;

@Table(database = AppDatabase.class)
public class Live  extends BaseDdModel<Live>
        implements DiffUiDataCallback.UiDataDiffer<Live>{
    @PrimaryKey
    private String id;
    @Column
    private String title;
    @Column
    private String subTitle;
    @Column
    private String time;
    @Column
    private boolean state;
    @Column
    private Date createAt;
    @ForeignKey(tableClass = User.class, stubbedRelationship = true)
    private User upper;
    @Column
    private int consumer;
    @Column
    private boolean isCustoms;

    public User getUpper() {
        return upper;
    }

    public void setUpper(User upper) {
        this.upper = upper;
    }

    public int getConsumer() {
        return consumer;
    }

    public void setConsumer(int consumer) {
        this.consumer = consumer;
    }

    public boolean isCustoms() {
        return isCustoms;
    }

    public void setCustoms(boolean customs) {
        isCustoms = customs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubTitle(String subTitle) {

        this.subTitle = subTitle;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean isSame(Live old) {
        return false;
    }

    @Override
    public boolean isUiContentSame(Live old) {
        return false;
    }
}
