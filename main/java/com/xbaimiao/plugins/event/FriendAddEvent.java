package com.xbaimiao.plugins.event;

import com.icecreamqaq.yuq.entity.Friend;

public class FriendAddEvent {
    Friend friend;

    public FriendAddEvent(com.icecreamqaq.yuq.event.FriendAddEvent event) {
        friend = event.getFriend();
    }

    public Friend getFriend() {
        return friend;
    }
}
