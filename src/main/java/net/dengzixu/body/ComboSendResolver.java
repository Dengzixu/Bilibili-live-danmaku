package net.dengzixu.body;

import net.dengzixu.annotation.BodyResolver;
import net.dengzixu.constant.BodyCommandEnum;
import net.dengzixu.constant.MessageTypeEnum;
import net.dengzixu.exception.ErrorCmdException;
import net.dengzixu.exception.UnknownDataFormatException;
import net.dengzixu.message.FansMedal;
import net.dengzixu.message.Message;
import net.dengzixu.message.UserInfo;
import net.dengzixu.profile.DanmakuProfile;
import net.dengzixu.utils.FaceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@BodyResolver(bodyCommand = BodyCommandEnum.COMBO_SEND)
public class ComboSendResolver extends AbstractBodyResolver {
    private static final BodyCommandEnum BODY_COMMAND = BodyCommandEnum.COMBO_SEND;

    private static final Logger logger = LoggerFactory.getLogger(ComboSendResolver.class);

    public ComboSendResolver(Map<String, Object> payloadMap) {
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

        // 连击信息
        message.setContent(new HashMap<>() {{
            put("action", dataMap.get("action"));
            put("batch_combo_id", dataMap.get("batch_combo_id"));
            put("batch_combo_num", dataMap.get("batch_combo_num"));
            put("combo_num", dataMap.get("combo_num"));
            put("combo_total_coin", dataMap.get("combo_total_coin"));
            put("gift_id", dataMap.get("gift_id"));
            put("gift_name", dataMap.get("gift_name"));
        }});

        // 用户信息
        message.setUserInfo(new UserInfo() {{
            setUid((int) dataMap.get("uid"));
            setUsername((String) dataMap.get("uname"));
            // body 中不包含 face 需要手动获取
            if (DanmakuProfile.getInstance().getAutoPullFace()) {
                setFace(FaceUtil.getFace((int) dataMap.get("uid")));
            }
        }});

        // 粉丝牌信息
        if (!(dataMap.get("medal_info") instanceof Map<?, ?>)) {
            throw new UnknownDataFormatException();
        }

        final Map<?, ?> fansMedalMap = (Map<?, ?>) dataMap.get("medal_info");
        message.setFansMedal(new FansMedal() {{
            setMedalLevel((int) fansMedalMap.get("medal_level"));
            setMedalName((String) fansMedalMap.get("medal_name"));
            setMedalColor((int) fansMedalMap.get("medal_color"));
            setMedalColorBorder((int) fansMedalMap.get("medal_color_border"));
            setMedalColorStart((int) fansMedalMap.get("medal_color_start"));
            setMedalColorEnd((int) fansMedalMap.get("medal_color_end"));
            setLighted((int) fansMedalMap.get("is_lighted") == 1);
        }});

        return message;
    }
}
