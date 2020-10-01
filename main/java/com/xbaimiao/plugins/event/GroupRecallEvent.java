package com.xbaimiao.plugins.event;

import com.icecreamqaq.yuq.entity.Contact;
import com.icecreamqaq.yuq.entity.Group;

public class GroupRecallEvent {

    private final Group group;
    private final int messageid;
    private final Contact sender;
    private final Contact operator;

    public GroupRecallEvent(com.icecreamqaq.yuq.event.GroupRecallEvent  event){
        group = event.getGroup();
        messageid = event.getMessageId();
        operator = event.getOperator();
        sender = event.getSender();
    }

    public Group getGroup() {
        return group;
    }

    public Contact getOperator() {
        return operator;
    }

    public Contact getSender() {
        return sender;
    }

    public int getMessageid() {
        return messageid;
    }
}
