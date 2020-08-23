package com.xbaimiao.bot.save;

import com.icecreamqaq.yuq.FunKt;
import com.icecreamqaq.yuq.message.Message;
import studio.trc.minecraft.serverpinglib.API.MCServerSocket;
import studio.trc.minecraft.serverpinglib.API.MCServerStatus;
import studio.trc.minecraft.serverpinglib.Protocol.ProtocolVersion;

import java.util.Arrays;
import java.util.List;

public class BuildMessage {

    private final Message message = new Message();

    public BuildMessage(String name) {
        String data = Test.xx.get(name);
        if (data == null) {
            System.out.println("目标key读取为空");
            return;
        }
        String[] arr = data.split("\\|");
        init(Arrays.asList(arr));
    }

    private void init(List<String> list) {
        for (String msg : list) {
            if (list.toString().contains("ping_")) {
                if (msg.startsWith("ping_")) {
                    pingMessage(msg.substring(5), list);
                    return;
                }
                continue;
            }
            if (build(msg)) continue;
            message.plus(msg);
        }
    }

    private void pingMessage(String ip, List<String> list) {
        MCServerSocket socket;
        System.out.println(ip);
        if (ip.contains(":")) {
            String[] a = ip.split(":");
            socket = MCServerSocket.getInstance(a[0], Integer.parseInt(a[1]));
        } else socket = MCServerSocket.getInstance(ip, 25565);
        if (socket == null) {
            message.plus("这个服务器是关闭状态");
            return;
        }
        if (socket.isClosed()) {
            message.plus("这个服务器是关闭状态");
            return;
        }
        MCServerStatus status = socket.getStatus(ProtocolVersion.v1_12_2);

        if (!status.isMCServer()) {
            message.plus("这个IP不是一个Mc服务器");
            return;
        }
        for (String msg : list) {
            if (msg.startsWith("ping_")) {
                continue;
            }
            if (build(msg)) continue;
            message.plus(msg.replace("%ping%", status.getPing() + "").replace("%online_max%", status.getMaxPlayers() + "")
                    .replace("%online%", status.getOnlinePlayers() + "").replace("%version%", status.getVersion()));
        }
    }

    private boolean build(String msg) {
        if (msg.startsWith("At_")) {
            message.plus(FunKt.getMif().at(Long.parseLong(msg.substring(3))));
            return true;
        }
        if (msg.startsWith("image_")) {
            message.plus(FunKt.getMif().image(msg.substring(6)));
            return true;
        }
        if (msg.startsWith("face_")) {
            message.plus(FunKt.getMif().face(Integer.parseInt(msg.substring(5))));
            return true;
        }
        return false;
    }

    public Message getMessage() {
        return this.message;
    }
}

