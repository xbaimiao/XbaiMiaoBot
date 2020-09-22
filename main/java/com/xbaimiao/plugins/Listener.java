package com.xbaimiao.plugins;

import com.icecreamqaq.yuq.entity.Contact;
import com.icecreamqaq.yuq.entity.Group;
import com.icecreamqaq.yuq.entity.Member;
import com.icecreamqaq.yuq.message.Message;
import com.xbaimiao.plugins.event.GroupMessageEvent;

public interface Listener {

    /**
     * 群聊事件
     *
     * @param event  监听器
     */
    void GroupMessageEvent(GroupMessageEvent event);

    /**
     * 私聊事件
     * @param sender 消息发送者
     * @param message 消息对象
     * @param msg 消息文本内容
     */
    void PrivateMessageEvent(Contact sender , Message message, String msg);

    /**
     * 消息撤回事件
     * @param sender 消息发送者
     * @param Operator 消息撤回者
     * @param MessageId 消息ID
     */
    void MessageRecallEvent(Contact sender , Contact Operator , int MessageId);


}
