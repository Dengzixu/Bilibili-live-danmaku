package net.dengzixu.message;

import net.dengzixu.constant.BodyCommandEnum;

import java.util.HashMap;

public class Message {
    public HashMap<?, ?> content;

    public BodyCommandEnum bodyCommand;

    public UserInfo userInfo;
    public FansMedal fansMedal;

    public Long timestamp;

    public HashMap<?, ?> getContent() {
        return content;
    }

    public void setContent(HashMap<?, ?> content) {
        this.content = content;
    }

    public BodyCommandEnum getBodyCommand() {
        return bodyCommand;
    }

    public void setBodyCommand(BodyCommandEnum bodyCommand) {
        this.bodyCommand = bodyCommand;
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
                ", userInfo=" + userInfo +
                ", fansMedal=" + fansMedal +
                ", timestamp=" + timestamp +
                '}';
    }
}
