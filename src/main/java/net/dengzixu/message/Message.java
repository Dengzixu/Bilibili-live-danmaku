package net.dengzixu.message;

import net.dengzixu.constant.BodyCommandEnum;
import net.dengzixu.constant.MessageTypeEnum;

import java.util.HashMap;

public class Message {
    public HashMap<?, ?> content;

    public BodyCommandEnum bodyCommand;
    public MessageTypeEnum messageType;

    public UserInfo userInfo;
    public FansMedal fansMedal;

    public Long timestamp;

    public HashMap<?, ?> getContent() {
        return content;
    }

    public void setContent(HashMap<?, ?> content) {
        this.content = content;
    }

    @Deprecated
    public BodyCommandEnum getBodyCommand() {
        return bodyCommand;
    }

    @Deprecated
    public void setBodyCommand(BodyCommandEnum bodyCommand) {
        this.bodyCommand = bodyCommand;
    }

    public MessageTypeEnum getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageTypeEnum messageType) {
        this.messageType = messageType;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public FansMedal getFansMedal() {
        return fansMedal;
    }

    public void setFansMedal(FansMedal fansMedal) {
        this.fansMedal = fansMedal;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content=" + content +
                ", bodyCommand=" + bodyCommand +
                ", messageType=" + messageType +
                ", userInfo=" + userInfo +
                ", fansMedal=" + fansMedal +
                ", timestamp=" + timestamp +
                '}';
    }
}
