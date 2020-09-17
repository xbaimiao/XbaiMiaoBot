package com.xbaimiao.plugins;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

public class PluginManager {

    private List<Plugin> plugins;
    private URLClassLoader urlClassLoader;

    protected PluginManager(List<Plugin> plugins) {
        try {
            this.plugins = plugins;
            URL[] urls = new URL[plugins.size()];
            for (int i = 0; i < plugins.size(); i++) {
                String filePath = plugins.get(i).getJar();
                urls[i] = new URL("file:" + filePath);
                urlClassLoader = new URLClassLoader(urls, this.getClass().getClassLoader());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    protected JavaPlugin getInstance(String main,String name) {
        try {
            Class<?> clas = urlClassLoader.loadClass(main);
            Object in = clas.newInstance();
            if (in instanceof JavaPlugin) {
                return (JavaPlugin) in;
            }
            yuq.getLogger().error("- Register Plugin: " + name + " 主类未继承自JavaPlugin");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
