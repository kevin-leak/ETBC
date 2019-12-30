package club.crabglory.www.data.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.Date;

@Table(database = AppDatabase.class)
public class MicroVideo {
    @PrimaryKey
    private String id;
    private int img;
    private int video;
    @Column
    private String imgAdvance;
    @Column
    private String micro;
    @Column
    private int favorite;
    @ForeignKey(tableClass = Book.class, stubbedRelationship = true)
    private Book book;
    @ForeignKey(tableClass = User.class, stubbedRelationship = true)
    private User upper;
    @ForeignKey(tableClass = Comment.class, stubbedRelationship = true)
    private Comment comment;
    @Column
    private Date createAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgAdvance() {
        return imgAdvance;
    }

    public void setImgAdvance(String imgAdvance) {
        this.imgAdvance = imgAdvance;
    }

    public String getMicro() {
        return micro;
    }

    public void setMicro(String micro) {
        this.micro = micro;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUpper() {
        return upper;
    }

    public void setUpper(User upper) {
        this.upper = upper;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getVideo() {
        return video;
    }

    public void setVideo(int video) {
        this.video = video;
    }

}
