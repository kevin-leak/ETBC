package club.crabglory.www.data.model.db;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.Date;

import club.crabglory.www.data.DataKit;
import club.crabglory.www.data.model.BaseDbModel;
import club.crabglory.www.data.model.db.utils.DiffUiDataCallback;
import club.crabglory.www.data.model.net.PayRspModel;

// 指的是当前用户购买，或者放入购物车的book
@Table(database = AppDatabase.class)
public class Goods extends BaseDbModel<Goods>
        implements DiffUiDataCallback.UiDataDiffer<Goods> {

    /**
     * 1. 用于购物车数据存储
     */
    // 主键
    @PrimaryKey
    private String id;
    @Column
    private int count;
    @Column
    private boolean state;
    // 注意这里是懒加载
    @ForeignKey(tableClass = User.class, stubbedRelationship = true)
    private User customer = null;
    @ForeignKey(tableClass = Book.class)
    private Book book = null;
    @Column
    private Date createAt;// 用区分付款的商品


    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean isSame(Goods old) {
        return this.id.equals(old.id);
    }

    @Override
    public boolean isUiContentSame(Goods old) {
        return this.id.equals(old.id) &&
                this.count == old.count
                && this.customer == old.customer
                && this.state == old.state
                && this.createAt == old.createAt;
    }

    @NonNull
    @Override
    public String toString() {
        return DataKit.Companion.getGson().toJson(this);
    }

    public PayRspModel toPayRspModel() {
        PayRspModel rspModel = new PayRspModel();
        rspModel.setId(this.getBook().getId());
        rspModel.setConsumer(this.getCustomer().getId());
        rspModel.setCount(this.getCount());
        rspModel.setType(PayRspModel.TYPE_GOODS);
        return rspModel;
    }
}
