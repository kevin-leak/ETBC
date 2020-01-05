package club.crabglory.www.data.model.view;

import java.io.Serializable;

public class GoodsModelView implements Serializable {

    private int image;
    private String name;
    private String description;
    private String price;
    private int currentCount;
    private String TYPE;

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    private boolean check;

    public void setImage(int image) {
        this.image = image;
    }
    public void setName(String resourceName) {
        this.name = resourceName;
    }
    public int getImage() { return image; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price + " $"; }
    public String getPrice() { return price; }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }


}
