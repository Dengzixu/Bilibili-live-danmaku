package net.dengzixu.body;

import net.dengzixu.constant.BodyCommandEnum;
import net.dengzixu.exception.ErrorCmdException;
import net.dengzixu.exception.UnknownDataFormatException;
import net.dengzixu.message.FansMedal;
import net.dengzixu.message.Message;
import net.dengzixu.message.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SendGiftResolver extends BodyResolver {
    private static final BodyCommandEnum BODY_COMMAND = BodyCommandEnum.SEND_GIFT;

    private static final Logger logger = LoggerFactory.getLogger(SendGiftResolver.class);

    public SendGiftResolver(Map<String, Object> payloadMap) {
        super(payloadMap);

        if (!BODY_COMMAND.toString().equals(this.payloadCmd)) {
            throw new ErrorCmdException();
        }
    }

    @Override
    public Message resolve() {
        Message message = new Message();
        message.setBodyCommand(BODY_COMMAND);

        if (!(payloadMap.get("data") instanceof Map<?, ?>)) {
            throw new UnknownDataFormatException();
        }
        final Map<?, ?> dataMap = (Map<?, ?>) payloadMap.get("data");

        // 礼物信息
//        message.setContent((HashMap<?, ?>) dataMap);
        message.setContent(new HashMap<>() {{
            put("action", dataMap.get("action"));
            put("batch_combo_id", dataMap.get("batch_combo_id"));
            put("batch_combo_send", dataMap.get("batch_combo_send"));
            put("coin_type", dataMap.get("coin_type"));
            put("discount_price", dataMap.get("discount_price"));
            put("gift_id", dataMap.get("giftId"));
            put("gift_name", dataMap.get("giftName"));
            put("gift_type", dataMap.get("giftType"));
            put("is_first", dataMap.get("is_first"));
            put("num", dataMap.get("num"));
            put("price", dataMap.get("price"));
            put("total_coin", dataMap.get("total_coin"));
        }});

        // 用户信息
        message.setUserInfo(new UserInfo() {{
            setUsername((String) dataMap.get("uname"));
            setUid((int) dataMap.get("uid"));
            setFace((String) dataMap.get("face"));
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
