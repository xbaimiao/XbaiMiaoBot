package com.xbaimiao.plugins.event;

import com.icecreamqaq.yuq.entity.Group;
import com.icecreamqaq.yuq.entity.Member;

public class GroupMemberKickEvent {

    private final Group group;
    private final Member member;
    private final Member operator;

    public GroupMemberKickEvent(com.icecreamqaq.yuq.event.GroupMemberKickEvent event){
        operator = event.getOperator();
        group = event.getGroup();
        member = event.getMember();
    }

    public Member getMember() {
        return member;
    }

    public Group getGroup() {
        return group;
    }

    public Member getOperator() {
        return operator;
    }
}
