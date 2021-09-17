package net.dengzixu.body;

import net.dengzixu.exception.ErrorCmdException;
import net.dengzixu.message.Message;

import java.util.Map;
import java.util.Objects;

public abstract class BodyResolver {
    protected Map<String, Object> payloadMap;
    protected String payloadCmd;

    public BodyResolver(Map<String, Object> payloadMap) {
        Objects.requireNonNull(payloadMap);

        this.payloadMap = payloadMap;

        if (null == payloadMap.get("cmd")) {
            throw new ErrorCmdException();
        }
        payloadCmd = (String) payloadMap.get("cmd");
    }

    public abstract Message resolve();
}
