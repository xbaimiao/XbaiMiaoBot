package com.xbaimiao.plugins.event;

import com.icecreamqaq.yuq.entity.Group;
import com.icecreamqaq.yuq.entity.Member;

public class GroupUnBanMemberEvent {

    private final Group group;
    private final Member member;
    private final Member operator;
    public GroupUnBanMemberEvent(com.icecreamqaq.yuq.event.GroupUnBanMemberEvent event){
        group = event.getGroup();
        member = event.getMember();
        operator = event.getOperator();
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
