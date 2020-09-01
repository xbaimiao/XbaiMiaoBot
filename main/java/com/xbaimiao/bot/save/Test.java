package com.xbaimiao.bot.save;

import com.IceCreamQAQ.Yu.annotation.Event;
import com.IceCreamQAQ.Yu.annotation.EventListener;
import com.icecreamqaq.yuq.entity.Group;
import com.icecreamqaq.yuq.entity.Member;
import com.icecreamqaq.yuq.event.GroupMessageEvent;
import com.icecreamqaq.yuq.message.Message;
import com.icecreamqaq.yuq.message.MessageItemFactory;
import com.xbaimiao.bot.minecraft.Xbaimiao;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;

@EventListener
public class Test {

    private final HashMap<Long, Integer> list = new HashMap<>();
    private final HashMap<String, Message> map = new HashMap<>();
    private final HashMap<Long, String> msgmap = new HashMap<>();
    private final ArrayList<Long> removeQA = new ArrayList<>();
    @Inject
    private MessageItemFactory mif;

    @Event
    public void eventKeys(GroupMessageEvent event) {
        String msg = Xbaimiao.getMsg(event.getMessage());
        Member qq = event.getSender();
        long qqId = qq.getId();
        if (qqId == 3104026189L) {
            Group group = event.getGroup();

        }
    }
}
