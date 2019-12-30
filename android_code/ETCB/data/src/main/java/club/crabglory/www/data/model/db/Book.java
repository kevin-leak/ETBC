package club.crabglory.www.data.model.db;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import club.crabglory.www.data.DataKit;
import club.crabglory.www.data.R;
import club.crabglory.www.data.model.db.utils.DiffUiDataCallback;
import club.crabglory.www.data.model.BaseDdModel;
import club.crabglory.www.data.model.persistence.Account;

@Table(database = AppDatabase.class)
public class Book extends BaseDdModel<Book>
        implements DiffUiDataCallback.UiDataDiffer<Book>, Serializable {

    /**
     * 这个表用于存储别人上传的book以及个人上传的book
     */

    public static String TYPE_KEY = "BOOK-TYPE";
    // 实际书本分类
    public static final int TYPE_EDUCATION = 0;
    public static final int TYPE_ECONOMIC = 1;
    public static final int TYPE_SOCIETY = 2;
    public static final int TYPE_SCIENCE = 3;

    // 前端页面展示分类
    public static final int TYPE_RANDOM = 4;
    public static final int TYPE_DAILY = 5;
    public static final int TYPE_MY_BUY = 6;    // 自己购买的产品，购物车的产品不是存在云端，放在mine goods
    public static final int TYPE_MY_UP = 7;     // 我自己上传的放在 display中


    // 主键
    @PrimaryKey
    @Column
    private String id;
    @Column
    private String author;
    @Column
    private String image;
    @Column
    private String video;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private float price;
    @Column
    private int type;
    @Column
    private int count = 0;
    @Column
    private int sales = 0; // 销量
    @ForeignKey(tableClass = User.class)
    private User upper;
    @Column
    private Date upTime;    // 这里指的是
    @Column
    private Date modifyAt;


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public Date getUpTime() {
        return upTime;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public int getTypeString() {
        int strRst = -1;
        switch (type) {
            case TYPE_EDUCATION:
                strRst = R.string.book_type_education;
                break;
            case TYPE_ECONOMIC:
                strRst = R.string.book_type_economic;
                break;
            case TYPE_SOCIETY:
                strRst = R.string.book_type_society;
                break;
            case TYPE_SCIENCE:
                strRst = R.string.book_type_science;
                break;
        }
        return strRst;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public User getUpper() {
        return upper;
    }

    public void setUpper(User upper) {
        this.upper = upper;
    }

    public String getName() {
        return name;
    }

    public void setName(String name)  {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean isSame(Book old) {
        return false;
    }

    @Override
    public boolean isUiContentSame(Book old) {
        return false;
    }


    @NonNull
    @Override
    public String toString() {
        return DataKit.Companion.getGson().toJson(this);
    }

    public Goods toCarGoods(int sales) {
        if (sales > this.count) return null;
        Goods goods = SQLite.select().from(Goods.class)
                .where(Goods_Table.book_id.eq(this.id))
                .and(Goods_Table.state.eq(false)).querySingle();
        if (goods != null) {
            // 购买的数量超出总数量
            if (goods.getCount() + sales > this.getCount())
                return null;
            goods.setCount(goods.getCount() + sales);
            return goods;
        }
        // 如果是购物车中没有
        goods = toGoods(sales);
        goods.setState(false);
        return goods;
    }

    // 在页面直接付款的商品
    public Goods toGoods(int sales) {
        if (sales > this.count) return null;
        Goods goods = new Goods();
        goods.setId(UUID.randomUUID().toString());
        goods.setBook(this);
        goods.setState(true);
        goods.setCount(sales);
        goods.setCustomer(Account.getUser());
        // book的更改，在存入本地数据之前处理，或者发送请求到服务器，由服务器转发book的更新
        return goods;
    }
}
