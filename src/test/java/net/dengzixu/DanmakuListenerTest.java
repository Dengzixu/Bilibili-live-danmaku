package net.dengzixu;


import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DanmakuListenerTest {
    private static final Logger logger = LoggerFactory.getLogger(DanmakuListener.class);

    private static final int roomId = 6103516;

    public static void main(String[] args) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger logger1 = loggerContext.getLogger("root");
        logger1.setLevel(Level.INFO);

        DanmakuListener.getInstance(roomId).connect().registerListener(message -> {
//            logger.info("[直播间: {}] [消息] {}", roomId, message);

            System.out.println(message.getTimestamp());

            switch (message.getBodyCommand()) {
                case DANMU_MSG:
                    if (null != message.getFansMedal() && message.getFansMedal().isLighted()) {
                        logger.info("[直播间: {}] [弹幕消息] [{}] [{}-{}] {}", roomId,
                                message.getUserInfo().getUsername(),
                                message.getFansMedal().getMedalName(),
                                message.getFansMedal().getMedalLevel(),
                                message.getContent().get("danmu"));
                    } else {
                        logger.info("[直播间: {}] [弹幕消息] [{}] {}", roomId,
                                message.getUserInfo().getUsername(),
                                message.getContent().get("danmu"));
                    }
                    break;
                case INTERACT_WORD:
                    switch ((int) message.getContent().get("msg_type")) {
                        case 1:
                            logger.info("[直播间: {}] [互动消息] [{}] 进入了直播间", roomId,
                                    message.userInfo.getUsername());

                            break;
                        case 2:
                            logger.info("[直播间: {}] [互动消息] [{}] 关注了直播间", roomId,
                                    message.userInfo.getUsername());
                            break;
                        case 3:
                            logger.info("[直播间: {}] [互动消息] [{}] 分享了直播间", roomId,
                                    message.userInfo.getUsername());
                            break;
                    }
                    break;
                case SEND_GIFT:
                    logger.debug("[GIFT DEBUG] Combo ID: {}", message.getContent().get("batch_combo_id"));
                    if (message.getContent().get("is_first").equals(true)) {
                        if (null != message.getFansMedal() && message.getFansMedal().isLighted()) {
                            logger.info("[直播间: {}] [礼物消息] [{}] [{}-{}] 赠送: {} x {} 个", roomId,
                                    message.getUserInfo().getUsername(),
                                    message.getFansMedal().getMedalName(),
                                    message.getFansMedal().getMedalLevel(),
                                    message.getContent().get("gift_name"),
                                    message.getContent().get("num"));
                        } else {
                            logger.info("[直播间: {}] [礼物消息] [{}] 赠送: {} x {} 个", roomId,
                                    message.getUserInfo().getUsername(),
                                    message.getContent().get("gift_name"),
                                    message.getContent().get("num"));
                        }
                    }
                    break;
                case COMBO_SEND:
                    logger.debug("[GIFT DEBUG] Combo ID: {}", message.getContent().get("batch_combo_id"));
                    if (null != message.getFansMedal() && message.getFansMedal().isLighted()) {
                        logger.info("[直播间: {}] [礼物 Combo 消息] [{}] [{}-{}] 赠送: {} 共计 {} 个", roomId,
                                message.getUserInfo().getUsername(),
                                message.getFansMedal().getMedalName(),
                                message.getFansMedal().getMedalLevel(),
                                message.getContent().get("gift_name"),
                                message.getContent().get("combo_num"));
                    } else {
                        logger.info("[直播间: {}] [礼物 Combo 消息] [{}] 赠送: {} x {} 个", roomId,
                                message.getUserInfo().getUsername(),
                                message.getContent().get("gift_name"),
                                message.getContent().get("combo_num"));
                    }
                    break;
            }
        });
    }
}
