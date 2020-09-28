package com.xbaimiao.plugins.event;

import com.icecreamqaq.yuq.entity.Friend;

public class FriendDeleteEvent {

    Friend friend;

    public FriendDeleteEvent(com.icecreamqaq.yuq.event.FriendDeleteEvent event) {
        friend = event.getFriend();
    }

    public Friend getFriend() {
        return friend;
    }
}
