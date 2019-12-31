package club.crabglory.www.data.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import club.crabglory.www.data.model.BaseDdModel;

// 三层多叉树
@Table(database = AppDatabase.class)
public class Comment extends BaseDdModel<Comment> {
    @PrimaryKey
    private String id;
    @Column
    private String parentId;
    @Column
    private String parentName;
    @Column
    private String parentAvatar;
    @Column
    private String sonId;
    @Column
    private String sonName;
    @Column
    private String sonAvatar;
    @Column
    private String text;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentAvatar() {
        return parentAvatar;
    }

    public void setParentAvatar(String parentAvatar) {
        this.parentAvatar = parentAvatar;
    }

    public String getSonId() {
        return sonId;
    }

    public void setSonId(String sonId) {
        this.sonId = sonId;
    }

    public String getSonName() {
        return sonName;
    }

    public void setSonName(String sonName) {
        this.sonName = sonName;
    }

    public String getSonAvatar() {
        return sonAvatar;
    }

    public void setSonAvatar(String sonAvatar) {
        this.sonAvatar = sonAvatar;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean isSame(Comment old) {
        return false;
    }

    @Override
    public boolean isUiContentSame(Comment old) {
        return false;
    }
}
