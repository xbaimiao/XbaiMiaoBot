package com.xbaimiao.yamlconfig;

import java.util.ArrayList;

public class ConfigList {

    protected static ArrayList<Config> configs = new ArrayList<>();

    public static void saveAll() {
        for (Config config : configs) {
            config.save();
        }
    }

}
