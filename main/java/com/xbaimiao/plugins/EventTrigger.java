package com.xbaimiao.plugins;

import com.IceCreamQAQ.Yu.annotation.Event;
import com.IceCreamQAQ.Yu.annotation.EventListener;
import com.IceCreamQAQ.Yu.event.events.AppStartEvent;
import com.icecreamqaq.yuq.event.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@EventListener
public class EventTrigger {

    protected static final HashMap<String, Plugin> pluginMap = new HashMap<>();
    protected static String pluginsPath = System.getProperty("user.dir") + File.separator + "plugins";
    static ArrayList<JavaPlugin> list = new ArrayList<>();
    private static final Thread cmd = new Thread(() -> {
        Scanner scan = new Scanner(System.in);
        String cmd;
        while ((cmd = scan.nextLine()) != null) {
            switch (cmd) {
                case "stop": {
                    stop();
                    break;
                }
                case "": {
                    break;
                }
                default: {
                    yuq.getLogger().info("未知命令");
                }
            }
        }
    });

    private static void run(Map.Entry<Listener, Method> map, Object v) {
        try {
            map.getValue().invoke(map.getKey(), v);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static void stop() {
        list.forEach(JavaPlugin::onDisable);
        cmd.interrupt();
        yuq.getLogger().info("机器人已关闭");
        System.exit(-1);
    }

    @Event
    public void group(GroupMessageEvent event) {
        for (Map.Entry<Listener, Method> map : yuq.GROUP_MESSAGE.entrySet()) {
            com.xbaimiao.plugins.event.GroupMessageEvent v = new com.xbaimiao.plugins.event.GroupMessageEvent(event);
            run(map, v);
        }
    }

    @Event
    public void privat(PrivateMessageEvent event) {
        for (Map.Entry<Listener, Method> map : yuq.PRIVATE_MESSAGE.entrySet()) {
            com.xbaimiao.plugins.event.PrivateMessageEvent v = new com.xbaimiao.plugins.event.PrivateMessageEvent(event);
            run(map, v);
        }
    }

    @Event
    public void recall(MessageRecallEvent event) {
        for (Map.Entry<Listener, Method> map : yuq.RECALL_MESSAGE.entrySet()) {
            com.xbaimiao.plugins.event.MessageRecallEvent v = new com.xbaimiao.plugins.event.MessageRecallEvent(event);
            run(map, v);
        }
    }

    @Event
    public void dedete(FriendDeleteEvent event) {
        for (Map.Entry<Listener, Method> map : yuq.FriendDelete.entrySet()) {
            com.xbaimiao.plugins.event.FriendDeleteEvent v = new com.xbaimiao.plugins.event.FriendDeleteEvent(event);
            run(map, v);
        }
    }

    @Event
    public void add(FriendAddEvent event) {
        for (Map.Entry<Listener, Method> map : yuq.FriendAdd.entrySet()) {
            com.xbaimiao.plugins.event.FriendAddEvent v = new com.xbaimiao.plugins.event.FriendAddEvent(event);
            run(map, v);
        }
    }

    @Event
    public void join(GroupMemberJoinEvent event) {
        for (Map.Entry<Listener, Method> map : yuq.GROUP_JOIN.entrySet()) {
            com.xbaimiao.plugins.event.GroupMemberJoinEvent v = new com.xbaimiao.plugins.event.GroupMemberJoinEvent(event);
            run(map, v);
        }
    }

    @Event
    public void join_invite(GroupMemberInviteEvent event) {
        for (Map.Entry<Listener, Method> map : yuq.GROUP_JOIN_Invite.entrySet()) {
            com.xbaimiao.plugins.event.GroupMemberInviteEvent v = new com.xbaimiao.plugins.event.GroupMemberInviteEvent(event);
            run(map, v);
        }
    }

    @Event
    public void leave(GroupMemberLeaveEvent event) {
        for (Map.Entry<Listener, Method> map : yuq.GROUP_Leave.entrySet()) {
            com.xbaimiao.plugins.event.GroupMemberLeaveEvent v = new com.xbaimiao.plugins.event.GroupMemberLeaveEvent(event);
            run(map, v);
        }
    }

    @Event
    public void kick(GroupMemberKickEvent event) {
        for (Map.Entry<Listener, Method> map : yuq.GROUP_Kick.entrySet()) {
            com.xbaimiao.plugins.event.GroupMemberKickEvent v = new com.xbaimiao.plugins.event.GroupMemberKickEvent(event);
            run(map, v);
        }
    }

    @Event
    public void ban(GroupBanMemberEvent event) {
        for (Map.Entry<Listener, Method> map : yuq.GROUP_Ban.entrySet()) {
            com.xbaimiao.plugins.event.GroupBanMemberEvent v = new com.xbaimiao.plugins.event.GroupBanMemberEvent(event);
            run(map, v);
        }
    }

    @Event
    public void ban(GroupUnBanMemberEvent event) {
        for (Map.Entry<Listener, Method> map : yuq.GROUP_UnBan.entrySet()) {
            com.xbaimiao.plugins.event.GroupUnBanMemberEvent v = new com.xbaimiao.plugins.event.GroupUnBanMemberEvent(event);
            run(map, v);
        }
    }

    @Event
    public void ban(GroupMemberRequestEvent event) {
        for (Map.Entry<Listener, Method> map : yuq.GROUP_New.entrySet()) {
            com.xbaimiao.plugins.event.GroupMemberRequestEvent v = new com.xbaimiao.plugins.event.GroupMemberRequestEvent(event);
            run(map, v);
        }
    }

    @Event
    public void ban(GroupRecallEvent event) {
        for (Map.Entry<Listener, Method> map : yuq.GROUP_Reacll.entrySet()) {
            com.xbaimiao.plugins.event.GroupRecallEvent v = new com.xbaimiao.plugins.event.GroupRecallEvent(event);
            run(map, v);
        }
    }

    @Event
    public void ban(PrivateRecallEvent event) {
        for (Map.Entry<Listener, Method> map : yuq.Private_Reacll.entrySet()) {
            com.xbaimiao.plugins.event.PrivateRecallEvent v = new com.xbaimiao.plugins.event.PrivateRecallEvent(event);
            run(map, v);
        }
    }

    @Event
    public void start(AppStartEvent event) {
        cmd.start();
        File pluginsFile = new File(pluginsPath);
        if (!pluginsFile.exists()) {
            pluginsFile.mkdirs();
        }
        File[] files = pluginsFile.listFiles();
        if (files != null) {
            List<Plugin> plugins = new ArrayList<>();
            for (File file : files) {
                if (file.getName().endsWith(".jar")) {
                    Plugin plugin = new Plugin(file);
                    if (plugin.isPlugin()) {
                        plugins.add(plugin);
                    }
                }
            }
            PluginManager pluginManager = new PluginManager(plugins);
            for (Plugin plugin : plugins) {
                pluginMap.put(plugin.getName(), plugin);
                if (plugin.getMain() == null) {
                    yuq.getLogger().error("- Register Plugin: " + plugin.getName() + " 未指定主类");
                    continue;
                }
                JavaPlugin javaPlugin = pluginManager.getInstance(plugin.getMain());
                String dataFile = pluginsPath + File.separator + plugin.getName();
                if (javaPlugin != null) {
                    yuq.getLogger().info("- Register Plugin: " + plugin.getName() + ".");
                    javaPlugin.init(new File(dataFile + File.separator + "config.yml"), new File(dataFile), plugin);
                    javaPlugin.onEnable();
                    yuq.getLogger().info("- Register Plugin: " + plugin.getName() + "  Success!");
                }
            }
        }
    }
}
