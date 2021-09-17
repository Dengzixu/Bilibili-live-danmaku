package net.dengzixu.filter;

import net.dengzixu.message.Message;

public interface Filter {
    void doFilter(Message message);
}
