package club.crabglory.www.data.model.net;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.UUID;

import club.crabglory.www.common.utils.DateTimeUtil;
import club.crabglory.www.data.DataKit;
import club.crabglory.www.data.model.db.Message;
import club.crabglory.www.data.model.db.User;
import club.crabglory.www.data.model.persistence.Account;
import club.crabglory.www.data.model.view.MessageViewModel;

/**
 * 构建一个请求体
 */
public class MessageRspModel {

    private transient String conId; // 不会被转化为json

    /**
     * chatId :
     * fromId :
     * toId :
     * type :
     * info : ""
     * createTime :
     */

    private String chatId;
    private String fromId;
    private String toId;
    private int type;
    private String createTime;
    private ContentBean content;
    private String attach;

    /**
     * fixme just for test, local builder net data
     */
    public MessageViewModel toMessageViewModel() {
        MessageViewModel model = new MessageViewModel();
        model.setSenderId(toId);
        model.setReceiverId(fromId);
        model.setGroupId("");
        model.setAttach(this.getAttach());
        model.setCreateAt(DateTimeUtil.getDateTime(createTime, DateTimeUtil.Detail_TIME_JOSN));
        model.setId(chatId);
        model.setStatus(Message.STATUS_CREATED);
        model.setType(content.category);
        model.setContent(content.getInfo().toString());
        return model;
    }

    /**
     * fixme just for test, local builder net data
     */
    public Message toMessage() {
        MessageViewModel model = this.toMessageViewModel();
        Message message = MessageViewModel.build(model);
        User sender = message.getSender();
        message.setSender(Account.getUser());
        message.setReceiver(sender);
        return message;
    }

    public String getAttach() {
        return attach;
    }

    public static class Builder {

        private final MessageRspModel model;

        public Builder() {
            model = new MessageRspModel();
        }

        public Builder setReceiver(String receiverId, int type) {
            model.setToId(receiverId);
            model.setType(type);
            return this;
        }

        public <T> Builder addContent(T text, int category) {
            ContentBean<T> contentBean = new ContentBean<>();
            contentBean.setCategory(category);
            contentBean.setAttach(text);
            model.setContent(contentBean);
            return this;
        }

        public Builder addAttach(String s) {
            model.setAttach(s);
            return this;
        }

        public MessageRspModel build() {
            model.setFromId(Account.getUserId());
            model.setChatId(UUID.randomUUID().toString());
            model.setCreateTime(DateTimeUtil.getNowTimeAfter(0, DateTimeUtil.Detail_TIME_JOSN));
            return model;
        }

    }


    public static class ContentBean<T> {
        /**
         * category :
         * info :
         */

        private int category;
        private T info;

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public T getInfo() {
            return info;
        }

        public void setAttach(T content) {
            this.info = content;
        }
    }


    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    private void setAttach(String s) {
        this.attach = s;
    }

    @NonNull
    @Override
    public String toString() {
        return DataKit.Companion.getGson().toJson(this);
    }
}
