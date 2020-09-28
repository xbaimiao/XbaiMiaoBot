package com.xbaimiao.plugins.event;

import com.icecreamqaq.yuq.entity.Group;
import com.icecreamqaq.yuq.entity.UserInfo;

public class GroupMemberRequestEvent {

    private final com.icecreamqaq.yuq.event.GroupMemberRequestEvent event;
    private final String message;
    private final UserInfo qq;
    private boolean accept;
    private final Group group;

    public GroupMemberRequestEvent(com.icecreamqaq.yuq.event.GroupMemberRequestEvent event){
        this.event = event;
        group = event.getGroup();
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

    /**
     * 触发此事件的QQ
     * @return 触发此事件的QQ
     */
    public UserInfo getQq() {
        return qq;
    }

    public String getMessage() {
        return message;
    }

}
