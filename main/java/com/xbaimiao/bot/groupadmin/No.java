package com.xbaimiao.bot.groupadmin;

import com.IceCreamQAQ.Yu.annotation.Event;
import com.IceCreamQAQ.Yu.annotation.EventListener;
import com.icecreamqaq.yuq.FunKt;
import com.icecreamqaq.yuq.entity.Group;
import com.icecreamqaq.yuq.event.GroupMessageEvent;
import com.icecreamqaq.yuq.event.MessageRecallEvent;
import com.icecreamqaq.yuq.message.Message;
import com.icecreamqaq.yuq.message.MessageItem;
import com.icecreamqaq.yuq.message.MessageSource;
import com.xbaimiao.bot.minecraft.Xbaimiao;
import com.xbaimiao.bot.minecraft.server.Main;
import com.xbaimiao.yamlconfig.ConfigMessage;

import java.io.File;
import java.util.ArrayList;

@EventListener
public class No {

    static ConfigMessage config = new ConfigMessage(new File(Main.path + "jilu.yml"));
    static ArrayList<Long> list = new ArrayList<>();

    @Event
    public void chat(GroupMessageEvent e){
        config.set(String.valueOf(e.getMessage().getId()),e.getMessage());
        MessageSource id = e.getMessage().getReply();
        if (id != null){
            for (MessageItem messageItem : e.getMessage().getBody()){
                if (messageItem.toPath().replace(" ","").equals("读消息")){
                    e.getGroup().sendMessage(new Message().plus(config.getString(String.valueOf(id.getId()))));
                    e.getGroup().sendMessage(config.getMessage(String.valueOf(id.getId())));
                }
            }
        }
        String msg = Xbaimiao.getMsg(e.getMessage());
        Group group = e.getGroup();
        if (list.contains(e.getSender().getId())){
            group.sendMessage(new Message().plus(FunKt.getMif().xmlEx(1,msg)));
            list.remove(e.getSender().getId());
            return;

        }
        if (msg.toUpperCase().equals("XMLCORE")) {
            group.sendMessage(new Message().plus("请输入xml"));
            list.add(e.getSender().getId());
        }
    }

}
