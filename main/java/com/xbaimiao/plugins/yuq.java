package com.xbaimiao.plugins;

import com.icecreamqaq.yuq.FunKt;
import com.icecreamqaq.yuq.YuQ;
import com.icecreamqaq.yuq.message.MessageItemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;

public class yuq {

    protected static final HashMap<Class<?>, Method> GROUP_MESSAGE = new HashMap<>(); //群聊消息
    protected static final HashMap<Class<?>, Method> PRIVATE_MESSAGE = new HashMap<>(); //私聊消息
    protected static final HashMap<Class<?>, Method> RECALL_MESSAGE = new HashMap<>(); //撤回消息
    protected static final HashMap<Class<?>, Method> FriendAdd = new HashMap<>(); //好友新增
    protected static final HashMap<Class<?>, Method> FriendDelete = new HashMap<>(); //好友删除
    protected static final HashMap<Class<?>, Method> GROUP_JOIN = new HashMap<>(); //加群事件
    protected static final HashMap<Class<?>, Method> GROUP_JOIN_Invite = new HashMap<>(); //加群事件 - 邀请
    protected static final HashMap<Class<?>, Method> GROUP_Leave = new HashMap<>(); //退群事件
    protected static final HashMap<Class<?>, Method> GROUP_Kick = new HashMap<>(); //被踢事件
    protected static final HashMap<Class<?>, Method> GROUP_Ban = new HashMap<>(); //群员被禁言事件
    protected static final HashMap<Class<?>, Method> GROUP_UnBan = new HashMap<>(); //群员被取消禁言事件
    protected static final HashMap<Class<?>, Method> GROUP_New = new HashMap<>(); //群员被取消禁言事件

    private static final Logger logger = LoggerFactory.getLogger(JavaPlugin.class);

    /**
     * 注册一个监听器
     *
     * @param listener Listener对象
     */
    public static void registerEvents(Listener listener) {
        Class<?> listenerClass = listener.getClass();
        for (Method method : listenerClass.getMethods()) { //遍历类的方法
            Parameter[] parameters = method.getParameters(); //方法的参数
            if (parameters.length == 1) { //如果方法只有一个参数
                EventListener event = method.getAnnotation(EventListener.class); //获取注解
                if (event != null) {
                    switch (parameters[0].getType().getName()) {
                        case "com.xbaimiao.plugins.event.GroupMessageEvent": {
                            GROUP_MESSAGE.put(listenerClass, method);
                            break;
                        }
                        case "com.xbaimiao.plugins.event.PrivateMessageEvent": {
                            PRIVATE_MESSAGE.put(listenerClass, method);
                            break;
                        }
                        case "com.xbaimiao.plugins.event.MessageRecallEvent": {
                            RECALL_MESSAGE.put(listenerClass, method);
                            break;
                        }
                        case "com.xbaimiao.plugins.event.FriendAddEvent": {
                            FriendAdd.put(listenerClass, method);
                            break;
                        }
                        case "com.xbaimiao.plugins.event.FriendDeleteEvent": {
                            FriendDelete.put(listenerClass, method);
                            break;
                        }
                        case "com.xbaimiao.plugins.event.GroupMemberJoinEvent":{
                            GROUP_JOIN.put(listenerClass, method);
                            break;
                        }
                        case "com.xbaimiao.plugins.event.GroupMemberInviteEvent":{
                            GROUP_JOIN_Invite.put(listenerClass, method);
                            break;
                        }
                        case "com.xbaimiao.plugins.event.GroupMemberLeaveEvent":{
                            GROUP_Leave.put(listenerClass, method);
                            break;
                        }
                        case "com.xbaimiao.plugins.event.GroupMemberKickEvent":{
                            GROUP_Kick.put(listenerClass, method);
                            break;
                        }
                        case "com.xbaimiao.plugins.event.GroupBanMemberEvent":{
                            GROUP_Ban.put(listenerClass, method);
                            break;
                        }
                        case "com.xbaimiao.plugins.event.GroupUnBanMemberEvent":{
                            GROUP_UnBan.put(listenerClass, method);
                            break;
                        }
                        case "com.xbaimiao.plugins.event.GroupMemberRequestEvent":{
                            GROUP_New.put(listenerClass, method);
                            break;
                        }
                    }

                }
            }
        }
    }

    /**
     * 获取日志输出器
     *
     * @return 日志输出器
     */
    public static Logger getLogger() {
        return logger;
    }

    /**
     * 构造消息的mif对象
     *
     * @return mif对象
     */
    public static MessageItemFactory getMIF() {
        return FunKt.getMif();
    }

    /**
     * @return yuq对象
     */
    public static YuQ getYuQ() {
        return FunKt.getYuq();
    }

    /**
     * @param name 插件名称 如果插件未在plugin.yml填写名称  将为文件名字
     * @return 一个插件对象
     */
    public static Plugin getPlugin(String name) {
        return EventTrigger.pluginMap.get(name);
    }

}
