package net.dengzixu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dengzixu.api.GetAuthToken;
import net.dengzixu.constant.PacketOperationEnum;
import net.dengzixu.constant.PacketProtocolVersionEnum;
import net.dengzixu.filter.Filter;
import net.dengzixu.listener.Listener;
import net.dengzixu.message.Message;
import net.dengzixu.packet.PacketBuilder;
import net.dengzixu.packet.PacketResolve;
import net.dengzixu.payload.AuthPayload;
import net.dengzixu.payload.PayloadResolver;
import net.dengzixu.webscoket.DanmakuWebSocketClient;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DanmakuListener {
    private final Logger logger = LoggerFactory.getLogger(DanmakuListener.class);

    private final static Map<Long, DanmakuListener> danmakuListenerMap = new ConcurrentHashMap<>();

    private final long roomId;

    private final DanmakuWebSocketClient danmakuWebSocketClient;

    private final List<Listener> listenerList = new ArrayList<>();
    private final List<Filter> filterList = new ArrayList<>();

    private DanmakuListener(long roomId) {
        this.roomId = roomId;

        this.danmakuWebSocketClient = new DanmakuWebSocketClient(roomId, createWebSocketListener());
    }

    public static DanmakuListener getInstance(long roomId) {
        if (null != danmakuListenerMap.get(roomId)) {
            return danmakuListenerMap.get(roomId);
        }

        synchronized (DanmakuListener.class) {
            if (null == danmakuListenerMap.get(roomId)) {
                danmakuListenerMap.put(roomId, new DanmakuListener(roomId));
            }
        }

        return danmakuListenerMap.get(roomId);
    }

    public DanmakuListener registerListener(Listener listener) {
        if (null != listener) {
            listenerList.add(listener);
        }
        return this;
    }

    public DanmakuListener removeListener(Listener listener) {
        if (null != listener) {
            this.listenerList.remove(listener);
        }
        return this;
    }

    public DanmakuListener registerFilter(Filter filter) {
        if (null != filter) {
            filterList.add(filter);
        }
        return this;
    }

    public DanmakuListener removeFilter(Filter filter) {
        if (null != filter) {
            filterList.remove(filter);
        }
        return this;
    }

    public DanmakuListener connect() {
        this.danmakuWebSocketClient.connect();
        return this;
    }

    public void destroy() {
        this.danmakuWebSocketClient.disconnect();

        danmakuListenerMap.remove(this.roomId);
    }

    private WebSocketListener createWebSocketListener() {
        return new WebSocketListener() {
            @Override
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                logger.info("[直播间: {}] 直播间断开链接", roomId);
            }

            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                logger.info("[直播间: {}] 直播间断开链接", roomId);

                // 停止心跳
                danmakuWebSocketClient.stopHeartbeat();

                t.printStackTrace();


                // 重新链接
                danmakuWebSocketClient.reconnect();
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
                PacketResolve packetResolve = new PacketResolve(bytes.toByteArray());

                packetResolve.getPacketList().forEach(item -> {
                    Message message = new PayloadResolver(item.getPayload(),
                            PacketOperationEnum.getEnum(item.getOperation())).resolve();

                    switch (message.getBodyCommand()) {
                        case DANMU_MSG:
                        case INTERACT_WORD:
                        case SEND_GIFT:
                        case COMBO_SEND:
                            listenerList.forEach(listener -> listener.onMessage(message));
                            break;
                        case AUTH_SUCCESS:
                            // 认证成功后 开始发送心跳
                            danmakuWebSocketClient.startHeartbeat();

                            logger.info("[直播间: {}] 身份认证成功", roomId);
                        case UNKNOWN:
                            break;
                        default:
                    }
                });
            }

            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                logger.info("[直播间: {}] 准备建立链接", roomId);

                this.auth(webSocket);
            }

            private void auth(@NotNull WebSocket webSocket) {
                logger.info("[直播间: {}] 进行身份认证……", roomId);

                AuthPayload authPayload = new AuthPayload();
                authPayload.setRoomId(roomId);
                authPayload.setKey(new GetAuthToken().get(roomId));

                String payloadString;

                try {
                    payloadString = new ObjectMapper().writeValueAsString(authPayload);
                } catch (JsonProcessingException ignored) {
                    webSocket.close(1001, "");
                    return;
                }

                if (null != payloadString) {
                    byte[] packetArray = new PacketBuilder(PacketProtocolVersionEnum.PROTOCOL_VERSION_1,
                            PacketOperationEnum.OPERATION_7,
                            payloadString).buildArrays();
                    webSocket.send(new ByteString(packetArray));
                }
            }
        };
    }
}
