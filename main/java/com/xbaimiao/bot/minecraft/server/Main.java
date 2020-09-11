package com.xbaimiao.bot.minecraft.server;

import com.IceCreamQAQ.Yu.annotation.Event;
import com.IceCreamQAQ.Yu.annotation.EventListener;
import com.IceCreamQAQ.Yu.event.events.AppStartEvent;
import com.icecreamqaq.yuq.entity.Group;
import com.icecreamqaq.yuq.event.GroupMessageEvent;
import com.icecreamqaq.yuq.message.Message;
import com.xbaimiao.bot.minecraft.Xbaimiao;
import com.xbaimiao.bot.minecraft.server.rcon.RconKit;
import com.xbaimiao.yamlconfig.ConfigList;
import com.xbaimiao.yamlconfig.ConfigMessage;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@EventListener
public class Main {
    public static final String path = System.getProperty("user.dir") + File.separator + "MineCraft" + File.separator;
    private static final Thread cmd = new Thread(() -> {
        Scanner scan = new Scanner(System.in);
        String cmd;
        while ((cmd = scan.nextLine()) != null) {
            switch (cmd) {
                case "stop": {
                    System.out.println("[INFO] 机器人已关闭");
                    stop();
                    break;
                }
                case "": {
                    break;
                }
                default: {
                    System.out.println("[INFO] 未知命令");
                }
            }
        }
    });
    public static ConfigMessage idyml = new ConfigMessage(new File(path + "id.yml"));
    public static ConfigMessage config = new ConfigMessage(new File(path + "config.yml"));
    public static String Rconpassword;
    public static String Rconhost;
    public static int Rconport;
    private static List<String> QQlist;
    private static List<String> Grouplist;

    private static void stop() {
        ConfigList.saveAll();
        cmd.stop();
        System.exit(-1);
    }

    @Event
    public void onGroupMessage(GroupMessageEvent e) {
        Message message = e.getMessage();
        Group group = e.getGroup();
        Long fromGroup = group.getId();
        String msg = Xbaimiao.getMsg(message);
        if (Grouplist.contains(String.valueOf(fromGroup))) {
            Long fromQQ = e.getSender().getId();
            switch (msg.toUpperCase()) {
                case "TPS": {
                    group.sendMessage(new Message().plus(RconKit.getTps()));
                    break;
                }
                case "赞助列表": {
                    group.sendMessage(new Message().plus(Xbaimiao.readFile(path + "sponsor.yml", "utf-8")));
                    break;
                }
                default:
                    break;
            }
            if (msg.startsWith("!") | msg.startsWith("！")) {
                group.sendMessage(new Message().plus(RconKit.sendMess(msg, fromQQ)));
                return;
            }
            if (QQlist.contains(String.valueOf(fromQQ))) {
                if (msg.startsWith("#")) {
                    group.sendMessage(new Message().plus(RconKit.sendCommand(msg)));
                }
            }
            if (msg.startsWith("缩短 ")) {
                try {
                    String iP = msg.substring(3).contains("http://") || msg.contains("https://")
                            ? msg.substring(3) : "http://" + msg.substring(3);

                    URL url = new URL(iP);
                    BufferedReader getDurl = new BufferedReader(new InputStreamReader(
                            new URL("http://yuq.ink:81/?ip=" + iP).openStream()));
                    String ip = getDurl.readLine();
                    group.sendMessage(new Message().plus(ip));
                } catch (Exception b) {
                    b.printStackTrace();
                }

            }
        }
    }

    @Event
    public void init(AppStartEvent e) {
        cmd.start();
        if (config.isEmpty()) {
            config.set("host", "服务器IP");
            config.set("port", "服务器端口");
            config.set("password", "rcon密码");
            config.set("onGroup", "启用的群聊，多个请用@分开");
            config.set("onQQ", "可以使用rcon的QQ，多个请用@分开");
            config.save();
            stop();
        }
        Rconpassword = config.getString("password");
        Rconhost = config.getString("host");
        Rconport = config.getInt("port");
        QQlist = Arrays.asList(config.getString("onQQ").split("@"));
        Grouplist = Arrays.asList(config.getString("onGroup").split("@"));
    }

}
