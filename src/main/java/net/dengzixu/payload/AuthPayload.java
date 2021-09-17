package net.dengzixu.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthPayload {
    /**
     * uid
     * 游客为 0
     */
    private Integer uid = 0;

    /**
     * 房间 ID
     */
    @JsonProperty("roomid")
    private long roomId;

    /**
     * 是协议版本
     * 为 1 时不会使用zlib压缩
     * 为 2 时会发送带有zlib压缩的包，也就是数据包协议为 2
     */
    @JsonProperty("protover")
    private Integer protoVer = 2;

    /**
     * 平台
     */
    private String platform = "web";

    /**
     * 未知
     * 取 2
     */
    private int type = 2;

    /**
     * key
     * 可以通过 https://api.live.bilibili.com/room/v1/Danmu/getConf?room_id={ROOM_ID} 获取
     */
    private String key;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public Integer getProtoVer() {
        return protoVer;
    }

    public void setProtoVer(Integer protoVer) {
        this.protoVer = protoVer;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
