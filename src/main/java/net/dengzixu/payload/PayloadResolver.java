package net.dengzixu.payload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dengzixu.annotation.BodyResolverAnnotationProcessor;
import net.dengzixu.constant.BodyCommandEnum;
import net.dengzixu.constant.MessageTypeEnum;
import net.dengzixu.constant.PacketOperationEnum;
import net.dengzixu.message.Message;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PayloadResolver {
    private final Logger logger = LoggerFactory.getLogger(PayloadResolver.class);

    private final byte[] payload;
    private final PacketOperationEnum operation;

    public PayloadResolver(@NotNull String payload, PacketOperationEnum packetOperationEnum) {
        this(payload.getBytes(StandardCharsets.UTF_8), packetOperationEnum);
    }

    public PayloadResolver(byte[] payload, PacketOperationEnum packetOperationEnum) {
        this.payload = payload;
        this.operation = packetOperationEnum;
    }

    public Message resolve() {
        Message message = new Message();

        switch (operation) {
            case OPERATION_3 -> {
                ByteBuffer byteBuffer = ByteBuffer.allocate(payload.length).put(payload);
//                message.setBodyCommand(BodyCommandEnum.POPULARITY);
                message.setMessageType(MessageTypeEnum.POPULARITY);

                message.setContent(new HashMap<>() {{
                    put("popularity", byteBuffer.order(ByteOrder.BIG_ENDIAN).getInt(0));
                }});
            }
            case OPERATION_5 -> {
                Map<String, Object> payloadMap;

                // εεΊεε
                try {
                    payloadMap = new ObjectMapper().readValue(new String(payload), new TypeReference<>() {
                    });
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    break;
                }

                // θ·ε Body Command
                BodyCommandEnum bodyCommandEnum = BodyCommandEnum.getEnum((String) payloadMap.get("cmd"));

                Class<?> clazz = BodyResolverAnnotationProcessor.BODY_RESOLVERS.get(bodyCommandEnum);

                if (clazz != null) {
                    try {
                        Object instance = clazz.getDeclaredConstructor(Map.class).newInstance(payloadMap);

                        message = ((net.dengzixu.message.Message) clazz.getMethod("resolve").invoke(instance));

                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        logger.error("Resolver Error", e);
                    }
                } else {
                    logger.warn("ζ ζ³θ§£ζ {}, ζΎδΈε°ηΈεΊηθ§£ζε¨", bodyCommandEnum);
                }
            }
            case OPERATION_8 -> {
                Map<String, Object> payloadMap;
                try {
                    // εεΊεε
                    payloadMap = new ObjectMapper().readValue(new String(payload), new TypeReference<>() {
                    });
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    break;
                }
                // ηΉζ?ε€η
                // θΏιδΈη₯ιδΈΊδ»δΉ δΉεθ¦η¨Try Catch θΏδΈζ’ε ι€
                try {
                    if ((int) payloadMap.get("code") == 0) {
                        message = new Message() {{
//                            setBodyCommand(BodyCommandEnum.AUTH_SUCCESS);
                            setMessageType(MessageTypeEnum.AUTH_SUCCESS);

                        }};
                    }
                } catch (Exception ignored) {
                }
            }
            default -> {
                message = new Message() {{
//                    setBodyCommand(BodyCommandEnum.UNKNOWN);
                    setMessageType(MessageTypeEnum.UNKNOWN);
                }};
            }
        }
        return message;
    }
}
