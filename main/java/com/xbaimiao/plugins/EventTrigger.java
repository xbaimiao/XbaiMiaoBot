package com.xbaimiao.plugins;

import com.IceCreamQAQ.Yu.annotation.Event;
import com.IceCreamQAQ.Yu.annotation.EventListener;
import com.IceCreamQAQ.Yu.event.events.AppStartEvent;
import com.icecreamqaq.yuq.event.GroupMessageEvent;
import com.icecreamqaq.yuq.event.MessageRecallEvent;
import com.icecreamqaq.yuq.event.PrivateMessageEvent;
import com.xbaimiao.bot.minecraft.Xbaimiao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@EventListener
public class EventTrigger {

    protected static String pluginsPath = System.getProperty("user.dir") + File.separator + "plugins";

    @Event
    public void GroupMessageEvent(GroupMessageEvent event) {
        yuq.eventList.forEach((l) ->{
            l.GroupMessageEvent(event.getGroup(), event.getMessage(), event.getSender(), Xbaimiao.getMsg(event.getMessage()));
        });
    }

    @Event
    public void PrivateMessageEvent(PrivateMessageEvent event){
        yuq.eventList.forEach((l ->
                l.PrivateMessageEvent(event.getSender(),event.getMessage(),Xbaimiao.getMsg(event.getMessage())))
        );
    }

    @Event
    public void MessageRecallEvent(MessageRecallEvent event){
        yuq.eventList.forEach( (l) ->
                l.MessageRecallEvent(event.getSender(),event.getOperator(),event.getMessageId())
        );
    }


    @Event
    public void start(AppStartEvent event) {
        File[] files = new File(pluginsPath).listFiles();
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
                if (plugin.getMain() == null){
                    yuq.getLogger().error("- Register Plugin: " + plugin.getName() + " 未指定主类");
                    continue;
                }
                JavaPlugin javaPlugin = pluginManager.getInstance(plugin.getMain(),plugin.getName());
                String dataFile = pluginsPath + File.separator + plugin.getName();
                if (javaPlugin != null) {
                    yuq.getLogger().info("- Register Plugin: " + plugin.getName() + ".");
                    javaPlugin.init(new File(dataFile + File.separator + "config.yml"),new File(dataFile),plugin);
                    javaPlugin.onEnable();
                    yuq.getLogger().info("- Register Plugin: " + plugin.getName() + "  Success!");
                }
            }
        }
    }
}
