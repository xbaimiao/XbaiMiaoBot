package com.xbaimiao.plugins.event;

import com.icecreamqaq.yuq.entity.Group;
import com.icecreamqaq.yuq.entity.Member;

public class GroupMemberLeaveEvent {

    private Group group;
    private Member member;
    public GroupMemberLeaveEvent(com.icecreamqaq.yuq.event.GroupMemberLeaveEvent event){
        group = event.getGroup();
        member = event.getMember();
    }

    public Group getGroup() {
        return group;
    }

    public Member getMember() {
        return member;
    }
}
