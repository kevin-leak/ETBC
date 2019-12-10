package club.crabglory.www.data.db;

import java.io.Serializable;

import club.crabglory.www.common.basic.model.BaseDdModel;

public class Book extends BaseDdModel<Book> implements Serializable {

    public static final String TYPE = "TYPE";
    private int image;
    private String name;
    private String description;
    private String upper;
    private String price;

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
    public String getUpper() { return upper; }
    public void setUpper(String upper) { this.upper = upper; }
    public void setPrice(double price) { this.price = price + " $"; }
    public String getPrice() { return price; }

    @Override
    public boolean isSame(Book old) {
        return false;
    }

    @Override
    public boolean isUiContentSame(Book old) {
        return false;
    }
}
