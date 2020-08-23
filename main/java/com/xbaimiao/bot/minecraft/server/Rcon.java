package com.xbaimiao.bot.minecraft.server;

import com.xbaimiao.bot.minecraft.Xbaimiao;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Rcon {

    private final Object sync = new Object();
    private final Random rand = new Random();

    private int requestId;
    private Socket socket;

    private String charset;

    public Rcon(String host, int port, byte[] password) throws IOException {
        this.charset = "utf-8";
        this.connect(host, port, password);
    }

    public void connect(String host, int port, byte[] password) throws IOException {
        if (host == null || host.trim().isEmpty()) {
            throw new IllegalArgumentException("地址为空");
        }

        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("端口超出范围");
        }
        synchronized (sync) {
            this.requestId = rand.nextInt();
            this.socket = new Socket(host, port);
        }
        RconPacket res = this.send(RconPacket.SERVERDATA_AUTH, password);
        if (res.getRequestId() == -1) {
            throw new IllegalArgumentException("错误的密码");
        }
    }

    public void disconnect() throws IOException {
        synchronized (sync) {
            this.socket.close();
        }
    }

    public String command(String payload) throws IOException {
        if (payload == null || payload.trim().isEmpty() || payload.equals("")) return ("发送的指令不能为空");

        RconPacket response = this.send(RconPacket.SERVERDATA_EXECCOMMAND, payload.getBytes(StandardCharsets.UTF_8));
        return Xbaimiao.replace(new String(response.getPayload(), charset));
    }

    private RconPacket send(int type, byte[] payload) throws IOException {
        synchronized (sync) {
            return RconPacket.send(this, type, payload);
        }
    }

    public int getRequestId() {
        return requestId;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

}
