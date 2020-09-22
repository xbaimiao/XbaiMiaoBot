package com.xbaimiao.plugins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class yuq {

    private static final Logger logger = LoggerFactory.getLogger(JavaPlugin.class);
    protected static final ArrayList<Listener> MESSAGE_EVENT = new ArrayList<>();

    /**
     * 注册一个监听器
     *
     * @param listener Listener对象
     */
    public static void regEvent(Listener listener) {
        if (!MESSAGE_EVENT.contains(listener)) {
            MESSAGE_EVENT.add(listener);
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

}
