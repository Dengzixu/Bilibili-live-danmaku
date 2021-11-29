package net.dengzixu.body;

import net.dengzixu.constant.BodyCommandEnum;
import net.dengzixu.exception.ErrorCmdException;
import net.dengzixu.exception.UnknownDataFormatException;
import net.dengzixu.message.FansMedal;
import net.dengzixu.message.Message;
import net.dengzixu.message.UserInfo;
import net.dengzixu.profile.DanmakuProfile;
import net.dengzixu.utils.FaceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DanmuBodyResolver extends BodyResolver {
    private static final BodyCommandEnum BODY_COMMAND = BodyCommandEnum.DANMU_MSG;

    private static final Logger logger = LoggerFactory.getLogger(DanmuBodyResolver.class);

    public DanmuBodyResolver(Map<String, Object> payloadMap) {
        super(payloadMap);

        if (!BODY_COMMAND.toString().equals(this.payloadCmd)) {
            throw new ErrorCmdException();
        }
    }

    @Override
    public Message resolve() {
        Message message = new Message();
        message.setBodyCommand(BODY_COMMAND);

        // ["info"] 弹幕内容
        if (!(this.payloadMap.get("info") instanceof ArrayList<?>)) {
            throw new UnknownDataFormatException();
        }

        final ArrayList<?> infoList = (ArrayList<?>) this.payloadMap.get("info");

        // 时间戳
        if (infoList.get(0) instanceof List<?>) {
            List<?> baseInfoList = (List<?>) infoList.get(0);
            message.setTimestamp(((Number) baseInfoList.get(4)).longValue() / 1000);
        }

        // 弹幕内容
        message.setContent(new HashMap<>() {{
            put("danmu", infoList.get(1));
        }});

        // 用户信息
        if (!(infoList.get(2) instanceof ArrayList<?>)) {
            throw new UnknownDataFormatException();
        }

        final ArrayList<?> userInfoList = (ArrayList<?>) infoList.get(2);

        message.setUserInfo(new UserInfo() {{
            setUid((int) userInfoList.get(0));
            setUsername((String) userInfoList.get(1));
            // body 中不包含 face 需要手动获取
            if (DanmakuProfile.getInstance().getAutoPullFace()) {
                setFace(FaceUtil.getFace((int) userInfoList.get(0)));
            }
        }});


        // 粉丝牌信息
        if (!(infoList.get(3) instanceof ArrayList<?>)) {
            throw new UnknownDataFormatException();
        }
        final ArrayList<?> fansMedalList = (ArrayList<?>) infoList.get(3);

        if (!fansMedalList.isEmpty()) {
            message.setFansMedal(new FansMedal() {{
                setMedalLevel((int) fansMedalList.get(0));
                setMedalName((String) fansMedalList.get(1));
                setMedalColor((int) fansMedalList.get(4));
                setMedalColorBorder((int) fansMedalList.get(7));
                setMedalColorStart((int) fansMedalList.get(8));
                setMedalColorEnd((int) fansMedalList.get(9));
                setLighted((int) fansMedalList.get(11) == 1);
            }});
        }

        return message;
    }
}
