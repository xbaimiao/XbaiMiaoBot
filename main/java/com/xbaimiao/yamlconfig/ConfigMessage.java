package com.xbaimiao.yamlconfig;

import com.icecreamqaq.yuq.FunKt;
import com.icecreamqaq.yuq.message.*;

import java.io.File;
import java.util.ArrayList;

public class ConfigMessage extends Config {

    public ConfigMessage(File file) {
        super(file);
    }

    /**
     * 获取目标key值的 Message 对象
     *
     * @param key 键值
     * @return Message 对象
     */
    public Message getMessage(String key) {
        String stringMessage = super.getString(key);
        if (stringMessage == null) {
            return new Message().plus("目标Key值为空");
        }
        String[] msgList = stringMessage.split("\\|");
        MessageItemFactory mif = FunKt.getMif();
        Message message = new Message();
        for (String msg : msgList) {
            if (msg.startsWith(MsgType.AT.getType())) {
                message.plus(mif.at(Long.parseLong(msg.substring(MsgType.AT.getSize()))));
                continue;
            }
            if (msg.startsWith(MsgType.IMAGE.getType())) {
                message.plus(mif.image(msg.substring(MsgType.IMAGE.getSize())));
                continue;
            }
            if (msg.startsWith(MsgType.FACE.getType())) {
                message.plus(mif.face(Integer.parseInt(msg.substring(MsgType.FACE.getSize()))));
                continue;
            }
            if (msg.startsWith(MsgType.XML.getType())) {
                String mSg = msg.substring(MsgType.XML.getSize());
                String[] args = mSg.split(",");
                int id = Integer.parseInt(args[0].substring(4));
                String xml = args[1].substring(6, args[1].length() - 1);
                message.plus(mif.xmlEx(id, xml));
                continue;
            }
            if (msg.startsWith(MsgType.JSONEX.getType())) {
                message.plus(mif.jsonEx(msg.substring(MsgType.JSONEX.getSize())));
                continue;
            }
            message.plus(msg);
        }
        return message;
    }

    /**
     * 将目标key值设置为提供的 Message对象
     *
     * @param key     键值
     * @param message Message 对象
     */
    public void set(String key, Message message) {
        ArrayList<MessageItem> list = message.getBody();
        StringBuilder msg = new StringBuilder();
        for (MessageItem s : list) {
            if (s.toPath().equals(MsgType.NULL.getType())) continue;
            if (s.toPath().startsWith(MsgType.AT.getType())) {
                msg.append(s.toPath()).append("|");
                continue;
            }
            if (s instanceof Face) {
                msg.append(MsgType.FACE.getType()).append(((Face) s).getFaceId()).append("|");
                continue;
            }
            if (s instanceof Image) {
                msg.append(MsgType.IMAGE.getType()).append(((Image) s).getUrl()).append("|");
                continue;
            }
            if (s instanceof XmlEx) {
                msg.append(MsgType.XML.getType()).append("{id:").append(((XmlEx) s).getServiceId()).append(",value:").append(((XmlEx) s).getValue()).append("}|");
                continue;
            }
            if (s instanceof JsonEx) {
                msg.append(MsgType.JSONEX.getType()).append(((JsonEx) s).getValue()).append("|");
            }
            msg.append(s.toPath()).append("|");
        }
        String vault = msg.toString().length() < 2 ? msg.toString() : msg.substring(0, msg.toString().length() - 1);
        super.set(key, vault);
    }
}
