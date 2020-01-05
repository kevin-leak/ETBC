package club.crabglory.www.data.model.net;

import club.crabglory.www.data.DataKit;

public class PayRspModel {
    public static final int TYPE_GOODS = 0;

    private String id;
    private int type;   // 用来扩展，以后不是书籍的情况
    private int count = 0;
    private String consumer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public int getType() {
        return type;
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

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    @Override
    public String toString() {
        return DataKit.Companion.getGson().toJson(this);
    }
}
