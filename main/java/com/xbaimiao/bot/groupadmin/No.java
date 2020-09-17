package com.xbaimiao.bot.groupadmin;

import com.IceCreamQAQ.Yu.annotation.Event;
import com.IceCreamQAQ.Yu.annotation.EventListener;
import com.icecreamqaq.yuq.FunKt;
import com.icecreamqaq.yuq.entity.Group;
import com.icecreamqaq.yuq.event.GroupMessageEvent;
import com.icecreamqaq.yuq.message.Message;
import com.icecreamqaq.yuq.message.MessageItem;
import com.icecreamqaq.yuq.message.MessageSource;
import com.xbaimiao.bot.minecraft.Xbaimiao;
import com.xbaimiao.bot.minecraft.server.Main;
import com.xbaimiao.yamlconfig.ConfigMessage;
import net.mamoe.mirai.message.data.FlashImage;

import java.io.File;
import java.util.ArrayList;

@EventListener
public class No {

    static final ArrayList<Long> XML_LIST = new ArrayList<>();
    static final ArrayList<Long> JSON_LIST = new ArrayList<>();
    static ConfigMessage config = new ConfigMessage(new File(Main.path + "jilu.yml"));

    @Event
    public void chat(GroupMessageEvent e) {
//        e.getMessage().getBody().forEach((messageItem -> {
//            if (messageItem instanceof FlashImage){
//                e.getGroup().sendMessage(FunKt.getMif().image().toMessage());
//                ((FlashImage) messageItem).getImage();
//            }
//        }));
        config.set(String.valueOf(e.getMessage().getId()), e.getMessage());
        MessageSource id = e.getMessage().getReply();
        if (id != null) {
            for (MessageItem messageItem : e.getMessage().getBody()) {
                if (messageItem.toPath().replace(" ", "").equals("读消息")) {
                    e.getGroup().sendMessage(new Message().plus(config.getString(String.valueOf(id.getId()))));
                    e.getGroup().sendMessage(config.getMessage(String.valueOf(id.getId())));
                }
            }
        }
        String msg = Xbaimiao.getMsg(e.getMessage());
        Long qq = e.getSender().getId();
        Group group = e.getGroup();
        if (XML_LIST.contains(qq)) {
            group.sendMessage(new Message().plus(FunKt.getMif().xmlEx(1, msg)));
            XML_LIST.remove(qq);
            return;

        }
        if (JSON_LIST.contains(qq)) {
            group.sendMessage(new Message().plus(FunKt.getMif().jsonEx(msg)));
            JSON_LIST.remove(qq);
            return;
        }
        if (msg.toUpperCase().equals("准备XML")) {
            group.sendMessage(new Message().plus("请输入xml"));
            XML_LIST.add(qq);
        }

        if (msg.toUpperCase().equals("准备JSON")) {
            group.sendMessage(new Message().plus("请输入json"));
            JSON_LIST.add(qq);
        }
    }

}
