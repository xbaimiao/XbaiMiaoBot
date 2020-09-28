package com.xbaimiao.plugins.event;

import com.icecreamqaq.yuq.entity.Group;

public class GroupMemberRequestEvent {

    private final com.icecreamqaq.yuq.event.GroupMemberRequestEvent event;
    private final String name;
    private final String message;
    private final Long qq;
    private boolean accept;
    private final Group group;

    public GroupMemberRequestEvent(com.icecreamqaq.yuq.event.GroupMemberRequestEvent event){
        this.event = event;
        group = event.getGroup();
        name = event.getName();
        qq = event.getQq();
        accept = event.getAccept();
        message = event.getMessage();
    }

    public boolean getAccept(){
        return accept;
    }

    public void setAccept(Boolean accept){
        event.setAccept(accept);
        this.accept = accept;
    }

    public Group getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public Long getQq() {
        return qq;
    }

    public String getMessage() {
        return message;
    }

}
