package net.dengzixu.body;

import net.dengzixu.annotation.BodyResolver;
import net.dengzixu.constant.BodyCommandEnum;
import net.dengzixu.constant.MessageTypeEnum;
import net.dengzixu.exception.ErrorCmdException;
import net.dengzixu.exception.UnknownDataFormatException;
import net.dengzixu.message.FansMedal;
import net.dengzixu.message.Message;
import net.dengzixu.message.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@BodyResolver(bodyCommand = BodyCommandEnum.INTERACT_WORD)
public class InteractWordResolver extends AbstractBodyResolver {
    private static final BodyCommandEnum BODY_COMMAND = BodyCommandEnum.INTERACT_WORD;

    private static final Logger logger = LoggerFactory.getLogger(InteractWordResolver.class);

    public InteractWordResolver(Map<String, Object> payloadMap) {
        super(payloadMap);

        if (!BODY_COMMAND.toString().equals(this.payloadCmd)) {
            throw new ErrorCmdException();
        }
    }

    @Override
    public Message resolve() {
        Message message = new Message();
//        message.setBodyCommand(BODY_COMMAND);
        message.setMessageType(MessageTypeEnum.getEnum(BODY_COMMAND.command()));


        if (!(payloadMap.get("data") instanceof Map<?, ?>)) {
            throw new UnknownDataFormatException();
        }
        final Map<?, ?> dataMap = (Map<?, ?>) payloadMap.get("data");

        // 时间戳
        message.setTimestamp(((Number) dataMap.get("timestamp")).longValue());

        // 消息类型
        message.setContent(new HashMap<>() {{
            put("msg_type", dataMap.get("msg_type"));
        }});

        // 用户信息
        message.setUserInfo(new UserInfo() {{
            setUid((int) dataMap.get("uid"));
            setUsername((String) dataMap.get("uname"));
        }});

        // 粉丝牌信息
        if (!(dataMap.get("fans_medal") instanceof Map<?, ?>)) {
            throw new UnknownDataFormatException();
        }
        final Map<?, ?> fansMedalMap = (Map<?, ?>) dataMap.get("fans_medal");

        if ((int) fansMedalMap.get("target_id") != 0) {
            message.setFansMedal(new FansMedal() {{
                setMedalLevel((int) fansMedalMap.get("medal_level"));
                setMedalName((String) fansMedalMap.get("medal_name"));
                setMedalColor((int) fansMedalMap.get("medal_color"));
                setMedalColorBorder((int) fansMedalMap.get("medal_color_border"));
                setMedalColorStart((int) fansMedalMap.get("medal_color_start"));
                setMedalColorEnd((int) fansMedalMap.get("medal_color_end"));
                setLighted((int) fansMedalMap.get("is_lighted") == 1);
            }});
        }

        return message;
    }
}
