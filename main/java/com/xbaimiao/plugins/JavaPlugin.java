package com.xbaimiao.plugins;

import com.icecreamqaq.yuq.FunKt;
import com.icecreamqaq.yuq.YuQ;
import com.icecreamqaq.yuq.message.MessageItemFactory;
import com.xbaimiao.yamlconfig.ConfigMessage;
import org.slf4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;

public abstract class JavaPlugin {

    File dataFolder;
    File file;
    ConfigMessage configMessage = null;
    Plugin plugin = null;

    public JavaPlugin() {

    }

    /**
     * 机器人启动时调用
     */
    public void onEnable() {
    }

    /**
     * 机器人关闭时候调用
     */
    public void onDisable() {
    }

    /**
     * 将jar内的 config.yml 文件保存至插件文件夹
     */
    public void saveDefaultConfig() {
        if (file.exists()) {
            return;
        }
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(plugin.getConfigInput()));
            try {
                if (!dataFolder.exists()) {
                    dataFolder.mkdirs();
                }
                if (!file.exists()) {
                    file.createNewFile();
                }
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
                String s;
                StringBuilder sb = new StringBuilder();
                while ((s = br.readLine()) != null) {
                    sb.append(s);
                }
                bw.write(sb.toString());
                bw.close();
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Plugin.ConfigNotFoundError configNotFound) {
            configNotFound.printStackTrace();
        }
    }

    /**
     * 插件文件夹的Config.yml
     *
     * @return 插件文件夹的Config.yml
     */
    public ConfigMessage getConfig() {
        if (configMessage == null) {
            return (configMessage = new ConfigMessage(file));
        }
        return configMessage;
    }

    /**
     * 保存配置文件至文件
     */
    public void saveConfig() {
        if (configMessage == null) {
            configMessage = new ConfigMessage(file);
        }
        configMessage.save();
    }

    /**
     * 将文件的内容重载到Config
     */
    public void reloadConfig() {
        if (configMessage == null) {
            configMessage = new ConfigMessage(file);
        }
        configMessage.reload();
    }

    /**
     * @return 插件文件夹
     */
    public File getDataFolder() {
        return dataFolder;
    }


    public void init(File config, File folder, Plugin plugin) {
        this.file = config;
        this.dataFolder = folder;
        this.plugin = plugin;
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
     *
     * @return 日志输出器
     */
    public Logger getLogger() {
        return yuq.getLogger();
    }

}
