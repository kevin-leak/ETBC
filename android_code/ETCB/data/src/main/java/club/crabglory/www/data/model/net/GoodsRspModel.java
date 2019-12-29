package club.crabglory.www.data.model.net;

public class GoodsRspModel {
    // 包含非book类型：daily,random,my_up,my_buy
    private String id;
    private int type;
    private boolean isMore;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
