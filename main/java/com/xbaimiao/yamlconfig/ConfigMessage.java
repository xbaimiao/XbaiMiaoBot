package com.xbaimiao.yamlconfig;

import com.icecreamqaq.yuq.FunKt;
import com.icecreamqaq.yuq.message.*;

import java.io.File;
import java.util.ArrayList;

public class ConfigMessage extends Config{

    public ConfigMessage(File file) {
        super(file);
    }

    public Message getMessage(String key){
        String stringMessage = super.getString(key);
        String[] msgList = stringMessage.split("\\|");
        MessageItemFactory mif = FunKt.getMif();
        Message message = new Message();
        for (String msg : msgList){
            if (msg.startsWith(ConfigMessageType.AT.getType())) {
                message.plus(mif.at(Long.parseLong(msg.substring(ConfigMessageType.AT.getSize()))));
                continue;
            }
            if (msg.startsWith(ConfigMessageType.IMAGE.getType())) {
                message.plus(mif.image(msg.substring(ConfigMessageType.IMAGE.getSize())));
                continue;
            }
            if (msg.startsWith(ConfigMessageType.FACE.getType())) {
                message.plus(mif.face(Integer.parseInt(msg.substring(ConfigMessageType.FACE.getSize()))));
                continue;
            }
            message.plus(msg);
        }
        return message;
    }

    public void setMessage(String key, Message message) {
        ArrayList<MessageItem> list = message.getBody();
        StringBuilder msg = new StringBuilder();
        for (MessageItem s : list) {
            if (s.toPath().equals(ConfigMessageType.NULL.getType())) continue;
            if (s.toPath().startsWith(ConfigMessageType.AT.getType())) {
                msg.append(s.toPath()).append("|");
                continue;
            }
            if (s instanceof Face) {
                msg.append(ConfigMessageType.FACE.getType()).append(((Face) s).getFaceId()).append("|");
                continue;
            }
            if (s instanceof Image) {
                msg.append(ConfigMessageType.IMAGE.getType()).append(((Image) s).getUrl()).append("|");
                continue;
            }
            msg.append(s.toPath()).append("|");
        }
        super.set(key, msg.substring(0,msg.toString().length() - 1));
        super.save();
    }
}
