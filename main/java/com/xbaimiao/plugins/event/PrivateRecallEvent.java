package com.xbaimiao.plugins.event;

import com.icecreamqaq.yuq.entity.Contact;

public class PrivateRecallEvent {

    private final int messageId;
    private final Contact sender;
    private final Contact operator;

    public PrivateRecallEvent(com.icecreamqaq.yuq.event.PrivateRecallEvent event) {
        messageId = event.getMessageId();
        sender = event.getOperator();
        operator = event.getSender();
    }

    public Contact getSender() {
        return sender;
    }

    public Contact getOperator() {
        return operator;
    }

    public int getMessageId() {
        return messageId;
    }

}
