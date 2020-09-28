package com.xbaimiao.plugins.event;

import com.icecreamqaq.yuq.entity.Group;
import com.icecreamqaq.yuq.entity.Member;

public class GroupMemberInviteEvent {
    private final Group group;
    private final Member member;
    private final Member inviter;

    public GroupMemberInviteEvent(com.icecreamqaq.yuq.event.GroupMemberInviteEvent event){
        group = event.getGroup();
        member = event.getMember();
        inviter =  event.getInviter();
    }

    public Member getMember() {
        return member;
    }

    public Group getGroup() {
        return group;
    }

    /**
     *
     * @return 邀请者
     */
    public Member getInviter() {
        return inviter;
    }
}
