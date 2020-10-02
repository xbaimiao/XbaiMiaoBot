package com.xbaimiao.plugins.event;


import com.icecreamqaq.yuq.entity.Group;
import com.icecreamqaq.yuq.entity.Member;

public class GroupBanMemberEvent {

    private final Member member;
    private final Group group;
    private final Member operator;
    private final int time;

    public GroupBanMemberEvent(com.icecreamqaq.yuq.event.GroupBanMemberEvent event) {
        member = event.getMember();
        group = event.getGroup();
        operator = event.getOperator();
        time = event.getTime();
    }

    public Member getOperator() {
        return operator;
    }

    public Group getGroup() {
        return group;
    }

    public Member getMember() {
        return member;
    }

    public int getTime() {
        return time;
    }
}
