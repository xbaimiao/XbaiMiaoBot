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
    static HashMap<String, String> xx = Save.loadDataFile();
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
        Long qqId = qq.getId();
        if (qqId == 3104026189L) {
            Group group = event.getGroup();
            if (!removeQA.contains(qqId)) {
                if (msg.equals("移除问答")) {
                    group.sendMessage(new Message().plus("请输入移除的问答"));
                    removeQA.add(qqId);
                    return;
                }
            } else {
                removeQA.remove(qqId);
                if (Save.removeMessage(msg)) {
                    group.sendMessage(new Message().plus("移除成功"));
                    xx = Save.loadDataFile();
                } else {
                    group.sendMessage(new Message().plus("该问答不存在"));
                }
                return;
            }
            if (list.containsKey(qqId)) {
                switch (list.get(qqId)) {
                    case 1: {
                        msgmap.put(qqId, msg);
                        group.sendMessage(new Message().plus("请输入回答消息"));
                        list.put(qqId, 2);
                        break;
                    }
                    case 2: {
                        list.remove(qqId);
                        Save.saveMessage(msgmap.get(qqId), event.getMessage());
                        group.sendMessage(new Message().plus("添加成功"));
                        xx = Save.loadDataFile();
                        break;
                    }
                }
            }
            if (msg.equalsIgnoreCase("添加问答")) {
                group.sendMessage(new Message().plus("请输入触发文字"));
                list.put(qqId, 1);
                return;
            }
            if (msg.equalsIgnoreCase("重载机器人")) {
                xx = Save.loadDataFile();
                return;
            }
        }
        if (xx.containsKey(msg)) {
            event.getGroup().sendMessage(new BuildMessage(msg).getMessage());
            return;
        }
    }
}
