package com.xbaimiao.plugins;

import com.icecreamqaq.yuq.FunKt;
import com.icecreamqaq.yuq.YuQ;
import com.icecreamqaq.yuq.message.MessageItemFactory;
import com.xbaimiao.yamlconfig.ConfigMessage;
import org.slf4j.Logger;

import java.io.File;

public abstract class JavaPlugin {

    File dataFolder;
    File file;
    ConfigMessage configMessage = null;

    public JavaPlugin() {

    }

    public void setFile(File file) {
        this.file = file;
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    /**
     * 插件文件夹的Config.yml
     *
     * @return 插件文件夹的Config.yml
     */
    public ConfigMessage getConfig() {
        if (configMessage == null) {
            return configMessage = new ConfigMessage(file);
        }
        return configMessage;
    }

    /**
     * @return 插件文件夹
     */
    public File getDataFolder() {
        return dataFolder;
    }

    public void setDataFolder(File folder) {
        this.dataFolder = folder;
    }

    /**
     * 构造消息的mif对象
     *
     * @return mif对象
     */
    public MessageItemFactory getMIF() {
        return FunKt.getMif();
    }

    /**
     * @return yuq对象
     */
    public YuQ getYuQ() {
        return FunKt.getYuq();
    }

    /**
     * 日志输出器
     * @return 日志输出器
     */
    public Logger getLogger() {
        return yuq.getLogger();
    }

}
