package net.dengzixu.body;

import net.dengzixu.annotation.BodyResolver;
import net.dengzixu.constant.BodyCommandEnum;
import net.dengzixu.constant.MessageTypeEnum;
import net.dengzixu.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@BodyResolver(bodyCommand = BodyCommandEnum.UNKNOWN)
public class UnknownBodyResolver extends AbstractBodyResolver {
    private static final Logger logger = LoggerFactory.getLogger(UnknownBodyResolver.class);


    public UnknownBodyResolver(Map<String, Object> payloadMap) {
        super(new HashMap<>() {{
            put("cmd", "unknown");
        }});
    }

    @Override
    public Message resolve() {
        return new Message() {{
//            setBodyCommand(BodyCommandEnum.UNKNOWN);
            setMessageType(MessageTypeEnum.UNKNOWN);

        }};
    }
}
