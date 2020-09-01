package com.xbaimiao.bot.minecraft.server;

import com.IceCreamQAQ.Yu.annotation.Event;
import com.IceCreamQAQ.Yu.annotation.EventListener;
import com.icecreamqaq.yuq.entity.Group;
import com.icecreamqaq.yuq.entity.Member;
import com.icecreamqaq.yuq.event.GroupMessageEvent;
import com.icecreamqaq.yuq.message.Message;
import com.xbaimiao.bot.groupadmin.Operation;
import com.xbaimiao.bot.minecraft.Xbaimiao;
import com.xbaimiao.yamlconfig.ConfigMessage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

@EventListener
public class QA {

    private static final HashMap<Long,Integer> addQA = new HashMap<>();
    private static final HashMap<Long, String> trigger = new HashMap<>();
    private static final ArrayList<Long> removeQA = new ArrayList<>();

    private static final ConfigMessage config = new ConfigMessage(new File(System.getProperty("user.dir") + File.separator + "MineCraft" + File.separator + "QA.yml"));

    @Event
    public void eventKeys(GroupMessageEvent event) {
        String msg = Xbaimiao.getMsg(event.getMessage());
        Member qq = event.getSender();
        long qqId = qq.getId();
        Group group = event.getGroup();
        if (Operation.isAdmin(qqId)) {
            if (addQA.containsKey(qqId)) {
                switch (addQA.get(qqId)){
                    case 1:{
                        trigger.put(qqId,msg);
                        group.sendMessage(new Message().plus("请输入回复消息"));
                        addQA.put(qqId,2);
                        break;
                    }
                    case 2:{
                        config.set(trigger.get(qqId), event.getMessage());
                        group.sendMessage(new Message().plus("添加完成"));
                        config.save();
                        addQA.remove(qqId);
                        break;
                    }
                }
            }
            if (removeQA.contains(qqId)){
                if (config.getKeys().contains(msg)){
                    config.remove(msg);
                    config.save();
                    group.sendMessage(new Message().plus("移除成功"));
                }else group.sendMessage(new Message().plus("没有此问答，如需查看请输入 查看问答"));
                removeQA.remove(qqId);
                return;
            }
            switch (msg){
                case "添加问答":{
                    group.sendMessage(new Message().plus("请输入触发消息"));
                    addQA.put(qqId,1);
                    break;
                }
                case "查看问答":{
                    Set<String> set = config.getKeys();
                    if (set.size() < 1){
                        group.sendMessage(new Message().plus("暂无问答"));
                        break;
                    }
                    group.sendMessage(new Message().plus(set.toString()));
                    break;
                }
                case "移除问答":{
                    group.sendMessage(new Message().plus("请输入需要移除的问答"));
                    removeQA.add(qqId);
                    break;
                }
            }
        }
        if (config.getKeys().contains(msg)){
            group.sendMessage(config.getMessage(msg));
        }
    }
}
