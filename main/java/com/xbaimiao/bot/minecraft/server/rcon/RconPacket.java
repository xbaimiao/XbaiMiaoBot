package com.xbaimiao.bot.minecraft.server.rcon;

import java.io.*;
import java.net.SocketException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class RconPacket {
    public static final int SERVERDATA_EXECCOMMAND = 2;
    public static final int SERVERDATA_AUTH = 3;
    private final int requestId;
    private final int type;
    private final byte[] payload;

    private RconPacket(int requestId, int type, byte[] payload) {
        this.requestId = requestId;
        this.type = type;
        this.payload = payload;
    }

    /**
     * 发送一个Rcon包并获取响应
     *
     * @param rcon    rcon实例
     * @param type    包类型
     * @param payload 字节组（密码、命令等）
     * @return 返回包含响应的RconPacket对象,
     * @throws IOException 抛出格式错误的包异常
     */
    protected static RconPacket send(Rcon rcon, int type, byte[] payload) throws IOException {
        try {
            RconPacket.write(rcon.getSocket().getOutputStream(), rcon.getRequestId(), type, payload);
        } catch (SocketException se) {
            rcon.getSocket().close();
            throw se;
        }

        return RconPacket.read(rcon.getSocket().getInputStream());
    }

    /**
     * 在outputstream上写入rcon包
     *
     * @param out       要写入的输出流
     * @param requestId 请求id
     * @param type      包类型
     * @param payload   字节组
     * @抛出IOException
     */
    private static void write(OutputStream out, int requestId, int type, byte[] payload) throws IOException {
        int bodyLength = RconPacket.getBodyLength(payload.length);
        int packetLength = RconPacket.getPacketLength(bodyLength);

        ByteBuffer buffer = ByteBuffer.allocate(packetLength);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        buffer.putInt(bodyLength);
        buffer.putInt(requestId);
        buffer.putInt(type);
        buffer.put(payload);

        buffer.put((byte) 0);
        buffer.put((byte) 0);

        out.write(buffer.array());
        out.flush();
    }

    /**
     * 读取传入的rcon数据包
     *
     * @param in 1
     * @返回读取的 RconPacket
     * @抛出IOException
     * @抛出格式错误的包异常
     */
    private static RconPacket read(InputStream in) throws IOException {
        byte[] header = new byte[4 * 3];
        in.read(header);
        try {
            ByteBuffer buffer = ByteBuffer.wrap(header);
            buffer.order(ByteOrder.LITTLE_ENDIAN);

            int length = buffer.getInt();
            int requestId = buffer.getInt();
            int type = buffer.getInt();

            byte[] payload = new byte[length - 4 - 4 - 2];
            DataInputStream dis = new DataInputStream(in);
            dis.readFully(payload);
            dis.read(new byte[2]);

            return new RconPacket(requestId, type, payload);
        } catch (BufferUnderflowException | EOFException e) {
            System.out.println("无法读取整个数据包");
        }
        return null;
    }

    private static int getPacketLength(int bodyLength) {
        return 4 + bodyLength;
    }

    private static int getBodyLength(int payloadLength) {
        return 4 + 4 + payloadLength + 2;
    }

    public int getRequestId() {
        return requestId;
    }

    public int getType() {
        return type;
    }

    public byte[] getPayload() {
        return payload;
    }

}
