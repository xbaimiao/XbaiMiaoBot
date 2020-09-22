package com.xbaimiao.plugins;

import com.IceCreamQAQ.Yu.annotation.Event;
import com.IceCreamQAQ.Yu.annotation.EventListener;
import com.IceCreamQAQ.Yu.event.events.AppStartEvent;
import com.icecreamqaq.yuq.event.GroupMessageEvent;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EventListener
public class EventTrigger {

    protected static String pluginsPath = System.getProperty("user.dir") + File.separator + "plugins";

    @Event
    public void d(GroupMessageEvent e){
        for (Map.Entry<Class<?>,Method> map : yuq.GROUP_MESSAGE.entrySet()){
            try {
                map.getValue().invoke(map.getKey().newInstance(),new com.xbaimiao.plugins.event.GroupMessageEvent(e));
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        }
    }


    @Event
    public void start(AppStartEvent event) {
        File pluginsFile = new File(pluginsPath);
        if (!pluginsFile.exists()){
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
