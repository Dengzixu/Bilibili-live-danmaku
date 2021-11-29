package net.dengzixu.webscoket;

import net.dengzixu.constant.Constant;
import net.dengzixu.constant.PacketOperationEnum;
import net.dengzixu.constant.PacketProtocolVersionEnum;
import net.dengzixu.packet.PacketBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class DanmakuWebSocketClient {
    private static final Logger logger = LoggerFactory.getLogger(DanmakuWebSocketClient.class);

    private final AtomicInteger reconnectTime = new AtomicInteger(0);

    private final long roomId;
    private final WebSocketListener webSocketListener;

    private final OkHttpClient okHttpClient;
    private final Request request;

    /**
     * 重连相关设置
     */
    private final static Integer MAX_RECONNECT_TIME = 5;
    private final static Integer RECONNECT_INTERVAL_SECOND = 30;
    private static Long lastReconnectTime;
    private boolean allowReconnect;

    private Timer heartbeatTimer;

    private WebSocket webSocket;

    public DanmakuWebSocketClient(long roomId, WebSocketListener webSocketListener, boolean allowReconnect) {
        this.roomId = roomId;
        this.webSocketListener = webSocketListener;
        this.allowReconnect = allowReconnect;

        okHttpClient = new OkHttpClient.Builder()
                .build();

        request = new Request.Builder()
                .url(Constant.BILIBILI_LIVE_WS_URL)
                .build();

    }

    public DanmakuWebSocketClient(long roomId, WebSocketListener webSocketListener) {
        this(roomId, webSocketListener, true);
    }

    public void connect() {
        if (null == webSocket) {
            webSocket = okHttpClient.newWebSocket(request, webSocketListener);
        }
    }

    public void disconnect() {
        this.allowReconnect = false;

        webSocket.close(1000, "");

//        webSocket.cancel();
    }

    public void reconnect() {
        if (!allowReconnect) {
            return;
        }

        if (System.currentTimeMillis() - lastReconnectTime > (RECONNECT_INTERVAL_SECOND * 1000)) {
            reconnectTime.set(0);
        }

        lastReconnectTime = System.currentTimeMillis();

        // 如果大于最大重试次数 放弃重连
        if (reconnectTime.incrementAndGet() > MAX_RECONNECT_TIME) {
            logger.info("[直播间: {}] 达到最大重试次数，放弃重连", roomId);
            return;
        }

        logger.info("[直播间: {}] 准备重新建立链接……", roomId);

        this.webSocket = okHttpClient.newWebSocket(request, webSocketListener);
    }

    /**
     * 开始发送心跳
     */
    public void startHeartbeat() {
        if (null == heartbeatTimer) {
            heartbeatTimer = new Timer();
        }

        heartbeatTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                byte[] bytes = new PacketBuilder(PacketProtocolVersionEnum.PROTOCOL_VERSION_1,
                        PacketOperationEnum.OPERATION_2,
                        "[object Object]").buildArrays();
                webSocket.send(new ByteString(bytes));
            }
        }, 0, 1000 * 30);
    }

    /**
     * 停止发送心跳
     */
    public void stopHeartbeat() {
        if (null != heartbeatTimer) {
            heartbeatTimer.cancel();
            heartbeatTimer = null;
        }
    }

    @Deprecated
    public WebSocket getWebSocket() {
        return Objects.requireNonNull(this.webSocket);
    }
}
