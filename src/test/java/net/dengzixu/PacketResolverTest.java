package net.dengzixu;

import net.dengzixu.constant.PacketOperationEnum;
import net.dengzixu.message.Message;
import net.dengzixu.packet.Packet;
import net.dengzixu.packet.PacketResolve;
import net.dengzixu.payload.PayloadResolver;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class PacketResolverTest {
    @Parameterized.Parameter
    public String demoData;


    @Parameterized.Parameters
    public static Collection<String> data() {
        return new ArrayList<>() {{
            // 数据: 进入房间 有粉丝牌
            add("AAABgAAQAAIAAAAFAAAAAHjabJCxihUxFIYvgr3PcOoUyUySyaQTtbBRWBYsREI2OTsbmEkuSa4" +
                    "gyy3st7fzBXwu9TUkM3cWLjjFkPzfyTn/+Q+HF/zw6tC+l+33CG7xoOH9h9t3N6/f3JpPH2/eAgFvqwX9CKfgQfNO9XL" +
                    "sCZyiXRA01JTMnOJk1vtFNy7NKYMGIBA8xhpqwAL6syA9YV8ILGUy9dsRQTMCOaWl9R6GbuAEaliwVLscQTPJRia6YaQ" +
                    "Eiku51Us5MqXYqAYpCNzbWMyC3s7NYrV5wmpas05IzinlBFZqZvyKc5N34eL/99OPPz+//336BTu4eO9HJaW6Ek2pNtf" +
                    "/I4wetFSCKsquyV3KHnODdFRCEAjFzGF6qOjX9aeTzX731xMoR3Sh7bOm51Jc96EEbHQPKZs9rr6N4sNzMoLSTnCqzuu" +
                    "Ecsxot3fb0YR4n7amLsWaw92phhRbbFO2HkHT83Otx+K22mrDbJqLxs//AgAA//+u9673");

            // 数据: 进入房间 一个带有粉丝牌 另一个不带
            add("AAAB9AAQAAIAAAAFAAAAAHjatJJBixM/GMaHhf/hf/MzvOcckpk00+akqAcvCsuCB5GQnWS7gZl" +
                    "JSVJBloK4oF2hiBcRRFDw4KKIB8GCiF+m0939FpJuW3drFQR3DsNMnpc3z/PwS5KNy8mlJD7/xdceFJUCDjdubl3fvHJ" +
                    "1S9y+tXkNECgZJPA96BsFPKMsYzQnCPq1rDRwaB4fNB/fT74+ad4eNk+Hk/Ho6PD78f7z6Yc3MJ8ShS2tAw6AwChdBxO" +
                    "M9sDvkLsIKt8V4X5PAycInLVVvIZQTHMEwVTaB1n1gBNGOoTSnGUIfGGdPnvE8hzBjqy9qLSSZXQbpOvqIOKyVo5x2s5" +
                    "wC8FMFqW+p0vg6eJ/nuT486uTl1+a8TdYCHPfDLO83aLnToUP0gXgJGVZmrbYeVHX6nfStnVKu7Oq8aI03d2gFXCMoNu" +
                    "XTi08YgS+pwsTQ83qK2w9C4URyLrYtU4sOktT2mFZq7PsJ+t0BrPtvue0VPNl8VOYeseeLixsHZzZ7gdj69hb10mlgeP" +
                    "BclZpX5zOBmlKER1EfZAkG///NT9pO2bOCP0J0HT47GT/3fThqPn0qBm+mIwPjl6PmuGDC6GH/koPJSn9Az14hRq8Qs0" +
                    "qLHgtJngNH3g9GPifEYGXYfFFgvAjAAD//5JtZw0=");

            // 数据: 发送弹幕 无粉丝牌
            add("AAAA3AAQAAIAAAAFAAAAAHjaRM6xSgNBEAbgS2HvM/z1X8zszu7eXRcQrWIjVushel5AiYnopQr" +
                    "phVT2eT7zGrIHkhn4meIbZqpqNqsuq1IXJXbo31/Q4mp+u7h/XNzdgHhdLzdocxYqXaDGlJLTMmhjFmKtFo1Ba3NNSJ5" +
                    "C9MPy6XmQBMrUQEf8fv+cjofT8QBmJ3UU5414+xw3H/9ORaScKT53zBoojCZxog9bET+EYkDtmIFJls31drXiDuMX2vN" +
                    "fRD+ihVkj137usT/TKaTb/wUAAP//Q7w95A==");

            // 数据: 发送弹幕 有粉丝牌
            add("AAABGwAQAAIAAAAFAAAAAHjaTE8xSzNBFLyv+Hp/w9SveO92993udRLBKjZitR4hRCNCcld4ViF" +
                    "FGgkYaxuxs1Ksbfw1evFvyB5o3IFhd5h98ybL/p1me1k6/xMtMJmfocTB/tHwZDQ8PgThsp42KGNkEsodiRbGW/YkKkG" +
                    "cFWdZi99XrsQEHftpHmQK4h5ARfh8u+3u1t3TOyja3BsNhtA2zWjW1Bejejw/T3ZJYOZ0S99i7gjd5n77uPraPIPQ3bx" +
                    "sH9Yfq9c/omHPYgsywat6QhqknoN37kdT75KJTF9DrWW2FUUnKVMLl1vryBaixBVFoA9Pu9fXsxkt0F6h3FUmTFqUCGY" +
                    "wUDEFljtrT1wtvwMAAP//TOpeDg==");
        }};
    }

    @Test
    public void testPacketResolver() {
        byte[] demoData = Base64.decodeBase64(this.demoData);

        PacketResolve resolver = new PacketResolve(demoData);

        List<Packet> packetList = resolver.getPacketList();

        for (Packet packet : packetList) {
            Message message = new PayloadResolver(packet.getPayload(),
                    PacketOperationEnum.getEnum(packet.getOperation())).resolve();

            switch (message.getMessageType()) {
                case DANMU_MSG:
                case INTERACT_WORD:
                case SEND_GIFT:
                    System.out.println(message);
                    break;
                case UNKNOWN:
                    break;
                default:
            }
        }
    }
}
