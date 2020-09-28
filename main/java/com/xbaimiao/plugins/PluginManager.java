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

    protected JavaPlugin getInstance(String main) {
        try {
            Class<?> clas = urlClassLoader.loadClass(main);
            Object in = clas.newInstance();
            if (in instanceof JavaPlugin) {
                return (JavaPlugin) in;
            }
            throw new JarNotisPlugin(clas.getName() + "未继承自JavaPlugin");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
