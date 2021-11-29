package net.dengzixu.profile;

public class DanmakuProfile {
    private static final DanmakuProfile danmakuProfile = new DanmakuProfile();

    private Boolean autoPullFace = true;

    private DanmakuProfile() {
    }

    public static DanmakuProfile getInstance() {
        return danmakuProfile;
    }

    public Boolean getAutoPullFace() {
        return autoPullFace;
    }

    public void setAutoPullFace(Boolean autoPullFace) {
        this.autoPullFace = autoPullFace;
    }
}
