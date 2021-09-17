package net.dengzixu.listener;

import net.dengzixu.message.Message;

public interface Listener {
    void onMessage(Message message);
}
