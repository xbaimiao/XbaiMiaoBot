package com.xbaimiao.plugins.event;

import com.icecreamqaq.yuq.entity.Group;
import com.icecreamqaq.yuq.entity.Member;
import com.icecreamqaq.yuq.message.Message;
import com.xbaimiao.bot.minecraft.Xbaimiao;

public class GroupMessageEvent {

    private final Message message;
    private final String msg;
    private final Group group;
    private final Member member;

    public GroupMessageEvent(com.icecreamqaq.yuq.event.GroupMessageEvent event){
       this.message = event.getMessage();
       this.msg = Xbaimiao.getMsg(message);
       this.group = event.getGroup();
       this.member = event.getSender();
    }

    /**
     * Message对象
     * @return Message对象
     */
    public Message getMessage(){
        return message;
    }

    /**
     * 消息文字内容
     * @return 消息文字内容
     */
    public String getMsg(){
        return msg;
    }

    /**
     * 群聊对象
     * @return 群聊对象
     */
    public Group getGroup() {
        return group;
    }

    /**
     * 成员对象
     * @return 成员对象
     */
    public Member getMember() {
        return member;
    }
}
