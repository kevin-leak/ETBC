package club.crabglory.www.data.model.view;

public class MsgListViewModel {
    private int image;
    private String name;
    private String content;
    private String time;
    private int msgCount;
    private String chatId;

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public int getImage() {
        return image;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public void setImage(int image) {

        this.image = image;
    }

    public String getChatId() {
        return this.chatId;
    }

    public void setId(String chatId) {
        this.chatId = chatId;
    }
}

