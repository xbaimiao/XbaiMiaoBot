package com.xbaimiao.bot.minecraft.server.rcon;

import com.xbaimiao.bot.minecraft.server.Main;

import java.io.IOException;
import java.util.Objects;

public class RconKit {

    public static String Rcon(String key) {
        try {
            Rcon rcon = new Rcon(Main.Rconhost, Main.Rconport, Main.Rconpassword.getBytes());
            String cmd = rcon.command(key);
            rcon.disconnect();
            return cmd;
        } catch (IOException e) {
            return null;
        }
    }

    public static String sendCommand(String msg) {
        String cmd = msg.substring(1);
        if (Objects.equals(cmd, "") || cmd.isEmpty()) return "发送的指令不能为空";
        String back = Rcon(cmd);
        if (back == null) {
            return "服务器链接失败,ip密码正确了吗";
        }
        if (Objects.equals(back, "")) {
            return "指令执行成功,但无返回值";
        } else {
            return back;
        }
    }

    public static String sendMess(String msg, Long fromQQ) {
        if (msg.replace(" ", "").substring(1).equals("")) return "你需要喊话什么还没说呢";
        String id = Main.idyml.getString(fromQQ.toString());
        if (id == null) {
            return "请先绑定ID  命令为\"绑定ID\"";
        }
        String back = Rcon("sendmess §8[§7QQ群§8]§f" + "<" + id + "> " + msg.substring(1));
        if (back == null) {
            return "聊天服务器链接失败";
        }
        return back;
    }

    public static String getTps() {
        long time1 = System.currentTimeMillis();
        String back = Rcon("serverinfogc");
        if (back == null) {
            return "服务器链接失败,ip密码正确了吗";
        }
        long time = System.currentTimeMillis() - time1;
        return "延迟: " + time + "ms\n" + back;
    }

}
