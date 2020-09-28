package com.xbaimiao.plugins.event;

import com.icecreamqaq.yuq.entity.Contact;
import com.icecreamqaq.yuq.message.Message;
import com.xbaimiao.plugins.Xbaimiao;

public class PrivateMessageEvent {

    private final Message message;
    private final Contact sender;

    public PrivateMessageEvent(com.icecreamqaq.yuq.event.PrivateMessageEvent event) {
        message = event.getMessage();
        sender = event.getSender();
    }

    /**
     * 消息
     */
    public Message getMessage() {
        return message;
    }

    /**
     * 消息发送者
     */
    public Contact getSender() {
        return sender;
    }

    /**
     * 消息文字
     */
    public String getMesageString() {
        return Xbaimiao.getMsg(message);
    }
}
