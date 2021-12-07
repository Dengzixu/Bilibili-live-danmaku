package net.dengzixu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dengzixu.constant.BodyCommandEnum;
import net.dengzixu.constant.MessageTypeEnum;
import net.dengzixu.message.FansMedal;
import net.dengzixu.message.Message;
import net.dengzixu.message.UserInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class MessageSerializationTest {
    private Message message;

    @Before
    public void init() {
        message = new Message() {{
            setContent(new HashMap<String, Object>());
            setMessageType(MessageTypeEnum.DANMU_MSG);
            setFansMedal(new FansMedal());
            setUserInfo(new UserInfo());
        }};
    }

    @Test
    public void test() {
        try {
            System.out.println(new ObjectMapper().writeValueAsString(message));
        } catch (JsonProcessingException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }
}
