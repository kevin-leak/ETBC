package club.crabglory.www.data.model.net;

import club.crabglory.www.data.DataKit;

public class FileRspModel {

    /**
     * name : ksadjk
     * content : asdfjkal
     */

    private String name;
    private String content;

    public FileRspModel(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toString() {
        return DataKit.Companion.getGson().toJson(this);
    }

}
