package com.xbaimiao.plugins;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

public class Plugin {

    private String jar;
    private String name;
    private String main;
    private String version;
    private String author;
    private boolean isPlugin;

    protected Plugin(File plugin) {
        try {
            JarFile zipFile = new JarFile(plugin);
            JarInputStream in = new JarInputStream(new BufferedInputStream(new FileInputStream(plugin)));
            ZipEntry pluginYML = zipFile.getEntry("plugin.yml");
            if (pluginYML == null) {
                isPlugin = false;
                return;
            }
            isPlugin = true;
            ReadConfig config = new ReadConfig(zipFile.getInputStream(pluginYML));
            name = config.getString("name") == null ? plugin.getName() : config.getString("name");
            jar = plugin.getPath();
            main = config.getString("main");
            version = config.getString("version");
            author = config.getString("author");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean isPlugin() {
        return isPlugin;
    }

    public String getMain() {
        return main;
    }

    public String getJar() {
        return jar;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getVersion() {
        return version;
    }
}
