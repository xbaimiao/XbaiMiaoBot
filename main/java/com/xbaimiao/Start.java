package com.xbaimiao;

import com.icecreamqaq.yuq.mirai.YuQMiraiStart;
import com.xbaimiao.yamlconfig.Config;

import java.io.*;

public class Start {

    public static void main(String[] args) {
        init();
        YuQMiraiStart.start(args);
    }

    public static void init(){
        File settings = new File(System.getProperty("user.dir") + File.separator + "settings.yml");
        Config config = new Config(settings);
        if (config.isEmpty()){
            config.set("QQ账号",123456789);
            config.set("QQ密码",123456);
            config.save();
            System.exit(0);
        }
        File conf = new File(System.getProperty("user.dir") + File.separator + "conf" +
                File.separator + "YuQ.properties");
        if (!conf.getParentFile().exists()){
            conf.getParentFile().mkdirs();
        }
        try {
            if (!conf.exists()){
                conf.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(conf)));
            bw.write("yu.scanPackages=com.xbaimiao\n" +
                    "YuQ.Mirai.user.qq=" + config.getString("QQ账号" ) + "\n" +
                    "YuQ.Mirai.user.pwd=" + config.getString("QQ密码" ) + "\n" +
                    "#webServer.port=8000");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
