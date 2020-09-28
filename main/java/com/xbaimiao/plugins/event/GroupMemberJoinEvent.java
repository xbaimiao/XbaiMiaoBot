package com.xbaimiao.plugins.event;

import com.icecreamqaq.yuq.entity.Group;
import com.icecreamqaq.yuq.entity.Member;

public class GroupMemberJoinEvent {

    private final Group group;
    private final Member member;

    public GroupMemberJoinEvent(com.icecreamqaq.yuq.event.GroupMemberJoinEvent event){
        group =  event.getGroup();
        member = event.getMember();
    }

    /**
     *
     * @return 群
     */
    public Group getGroup() {
        return group;
    }

    /**
     *
     * @return 加群者
     */
    public Member getMember() {
        return member;
    }
}
