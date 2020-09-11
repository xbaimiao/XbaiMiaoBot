package com.xbaimiao.bot.groupadmin;

import com.IceCreamQAQ.Yu.annotation.Event;
import com.IceCreamQAQ.Yu.annotation.EventListener;
import com.icecreamqaq.yuq.event.GroupMessageEvent;
import com.icecreamqaq.yuq.event.MessageRecallEvent;
import com.icecreamqaq.yuq.message.Message;
import com.icecreamqaq.yuq.message.MessageSource;
import com.xbaimiao.yamlconfig.ConfigMessage;

import java.io.File;

@EventListener
public class No {

    static ConfigMessage config = new ConfigMessage(new File("D:\\dddd.yml"));

    @Event
    public void chat(GroupMessageEvent e){
        if (e.getMessage().getBody().size() > 1){
            System.out.println(e.getMessage().getBody().get(1).toPath());
            if (e.getMessage().getBody().get(1).toPath().contains("读消息")){
                MessageSource id = e.getMessage().getReply();
                if (id == null){
                    e.getGroup().sendMessage(new Message().plus("请回复需要读的消息"));
                    return;
                }
                e.getGroup().sendMessage(config.getMessage(String.valueOf(id.getId())));
                return;
            }
        }
        config.set(String.valueOf(e.getMessage().getId()),e.getMessage());
    }

    @Event
    public void call(MessageRecallEvent event){
        event.getOperator().sendMessage(new Message().plus("你撤回了").plus(config.getMessage(String.valueOf(event.getMessageId()))));
    }

}
