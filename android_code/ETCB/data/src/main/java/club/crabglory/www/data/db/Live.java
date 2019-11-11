package club.crabglory.www.data.db;

public class Live {
    private String title;
    private String subTitle;
    private String time;
    private boolean state;

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
}
