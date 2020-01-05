package club.crabglory.www.data.model.net;

import android.support.annotation.NonNull;

import club.crabglory.www.data.DataKit;

// 只用于数据的拉取
public class MaterialRspModel {

    public static String TYPE_KEY = "Material-TYPE";
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
    public static final int TYPE_SEARCH = 8;
    /*
    * 以上8中类型都会作为type的类型传入后台，进行数据加载
    * TYPE_MY_BUY：text为用户的ID
    * TYPE_MY_UP：text为用户的ID
    * TYPE_SEARCH：text为搜索的数据
    * */

    private String text;
    private int type; // 1. 某个用户的商品(个人商品展示)，2. 就是某个商品(shopActivity)
    private boolean isMore;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    @NonNull
    @Override
    public String toString() {
        return DataKit.Companion.getGson().toJson(this);
    }
}
