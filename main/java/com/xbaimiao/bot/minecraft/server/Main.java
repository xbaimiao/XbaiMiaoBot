package com.xbaimiao.bot.minecraft.server;

import com.IceCreamQAQ.Yu.annotation.Event;
import com.IceCreamQAQ.Yu.annotation.EventListener;
import com.IceCreamQAQ.Yu.event.events.AppStartEvent;
import com.icecreamqaq.yuq.YuQ;
import com.icecreamqaq.yuq.event.GroupMessageEvent;
import com.icecreamqaq.yuq.message.Message;
import com.icecreamqaq.yuq.message.MessageFactory;
import com.xbaimiao.bot.minecraft.Xbaimiao;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.io.*;
import java.net.URL;
import java.util.*;

@EventListener
public class Main {
    public static final String path = System.getProperty("user.dir") + File.separator + "MineCraft" + File.separator;
    public static final String IDpath = path + "ID.Properties";
    public static Properties config;
    public static String Rconpassword;
    public static String Rconhost;
    public static int Rconport;
    public static List<String> QQlist;
    public static List<String> Grouplist;
    @Inject
    private static MessageFactory mf;
    @Inject
    private static YuQ yuq;
    Thread jvmcmd = new Thread(() -> {
        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();
        if (str.equals("stop")) {
            System.out.println("[INFO] 机器人已关闭");
            System.exit(0);
        } else if (!str.equals("")) {
            System.out.println("[INFO] 未知命令");
        }
        runcmd();
    });

    @Event
    public void onGroupMessage(GroupMessageEvent e) {
        Message message = e.getMessage();
        Long fromGroup = message.getGroup();
        assert fromGroup != null;
        String msg = Xbaimiao.getMsg(message);
        if (Grouplist.contains(String.valueOf(fromGroup))) {
            Long fromQQ = message.getQq();
            assert fromQQ != null;
            switch (msg.toUpperCase()) {
                case "TPS": {
                    yuq.getGroups().get(fromGroup).sendMessage(mf.newMessage().plus(getTps()));
                    break;
                }
                case "赞助列表": {
                    yuq.getGroups().get(fromGroup).sendMessage(mf.newMessage().plus(Xbaimiao.readfile(path + "sponsor.yml", "utf-8")));
                    break;
                }
                default:
                    break;
            }
            if (msg.startsWith("!") | msg.startsWith("！")) {
                yuq.getGroups().get(fromGroup).sendMessage(mf.newMessage().plus(sendMess(msg, fromQQ)));
                return;
            }
            if (QQlist.contains(String.valueOf(fromQQ))) {
                if (msg.startsWith("#")) {
                    yuq.getGroups().get(fromGroup).sendMessage(mf.newMessage().plus(sendCommand(msg)));
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
                    e.getGroup().sendMessage(new Message().plus(ip));
                } catch (Exception b) {
                    b.printStackTrace();
                }

            }
        }
    }

    @Event
    public void loadconfig(AppStartEvent e) {
        runcmd();
        try {
            loadConfig();
            Rconpassword = (String) config.get("password");
            Rconhost = (String) config.get("host");
            Rconport = Integer.parseInt(config.get("port").toString());
            String qq = (String) config.get("QQ");
            QQlist = Arrays.asList(qq.split("@"));
            String group = (String) config.get("Group");
            Grouplist = Arrays.asList(group.split("@"));
        } catch (IOException a) {
            a.printStackTrace();
        }
    }

    private String sendCommand(String msg) {
        try {
            String cmd = msg.substring(1);
            if (Objects.equals(cmd, "") || cmd.isEmpty()) return "发送的指令不能为空";
            Rcon rcon = new Rcon(Main.Rconhost, Main.Rconport, Main.Rconpassword.getBytes());
            String back = rcon.command(cmd);
            if (Objects.equals(back, "")) return "指令执行成功,但无返回值";
            else return back;
        } catch (IOException e) {
            return "链接失败\n请检查服务器IP密码是否正确";
        }
    }

    private String sendMess(String msg, Long fromQQ) {
        try {
            if (msg.replace(" ", "").substring(1).equals("")) return "你需要喊话什么还没说呢";
            String ID = Xbaimiao.readconfig(Main.IDpath, fromQQ.toString());
            if (Objects.equals(ID, "读取配置项为空")) return "请先绑定ID  命令为\"绑定ID\"";
            Rcon rcon = new Rcon(Main.Rconhost, Main.Rconport, Main.Rconpassword.getBytes());
            return rcon.command("sendmess §8[§7QQ群§8]§f" + "<" + ID + "> " + msg.substring(1));
        } catch (IOException e) {
            return "获取失败\n请检查服务器IP密码是否正确";
        }
    }

    @NotNull
    private String getTps() {
        try {
            Rcon rcon = new Rcon(Main.Rconhost, Main.Rconport, Main.Rconpassword.getBytes());
            long time1 = System.currentTimeMillis();
            String serverinfogc = rcon.command("serverinfogc");
            long time = System.currentTimeMillis() - time1;
            return "延迟: " + time + "ms\n" + serverinfogc;
        } catch (IOException e) {
            return "获取失败\n请检查服务器IP密码是否正确";
        }
    }

    private void loadConfig() throws IOException {
        config = new Properties();
        File file = new File(path + "config.Properties");
        File fileParent = file.getParentFile();
        File IDfile = new File(IDpath);
        if (!fileParent.exists()) {
            if (fileParent.mkdirs()) System.out.println("[INFO] MineCraft文件夹已生成");
        }
        if (!file.exists()) {
            if (file.createNewFile()) {
                Xbaimiao.writerfile(path + "config.Properties", "utf-8", "#服务器端口\r\n"
                        + "port=25575\r\n" + "#服务器IP\r\n" + "host=127.0.0.1\r\n"
                        + "#RCON密码\r\n" + "password=123456789\r\n"
                        + "#启用的群多个请使用@分割开\r\n"
                        + "Group=937521538@123456\r\n"
                        + "#可以使用此功能的QQ多个请使用@分割开\r\n"
                        + "QQ=3104026189");
            }
        }
        if (!IDfile.exists()) {
            if (IDfile.createNewFile()) {
                Xbaimiao.writerfile(IDpath, "UTF-8", "#此文件用于记录玩家ID");
            }
        }
        FileInputStream in = new FileInputStream(file);
        config.load(in);
        in.close();
    }

    private void runcmd() {
        Main a = new Main();
        a.jvmcmd.start();
    }

}
