package com.xbaimiao.bot.minecraft

import com.IceCreamQAQ.Yu.annotation.Event
import com.IceCreamQAQ.Yu.annotation.EventListener
import com.icecreamqaq.yuq.event.GroupMessageEvent
import com.icecreamqaq.yuq.message.Message
import com.icecreamqaq.yuq.mif
import com.icecreamqaq.yuq.yuq
import studio.trc.minecraft.serverpinglib.API.MCServerSocket
import studio.trc.minecraft.serverpinglib.Protocol.ProtocolVersion
import java.io.File

@EventListener
class PingMServer {

    @Event
    fun ping(e: GroupMessageEvent) {
        val message = e.message
        val msg = Xbaimiao.getMsg(message)
        ping(msg, e.group.id)
    }

    private fun ping(message: String, fromGroup: Long) {
        val msg = message.replace(" ", "").replace("\n", "")
        if (msg.startsWith("ping")) {
            if (msg == "ping") {
                yuq.groups[fromGroup]?.sendMessage(Message() + "请输入服务器IP 例子\"ping 服务器地址\"")
                return
            }
            if (msg.contains("http")) {
                yuq.groups[fromGroup]?.sendMessage(Message() + "你输入的是一个网站吧,并不是mc服务器")
                return
            }
            val ip = msg.substring(4)
            if (ip.contains(":")) {
                val a = ip.split(":").toTypedArray()
                yuq.groups[fromGroup]?.sendMessage(getMotd(a[0], a[1].toInt()))
            } else {
                yuq.groups[fromGroup]?.sendMessage(getMotd(ip, 25565))
            }
        }
    }

    private val path = System.getProperty("user.dir") + File.separator + "icon.jpg"

    private fun getMotd(ip: String, port: Int): Message {
        val socket = MCServerSocket.getInstance(ip, port) ?: return Message() + "这个服务器是关闭状态"
        val status = socket.getStatus(ProtocolVersion.v1_12_2)
        if (socket.isClosed) return Message() + "这个服务器是关闭状态"
        if (!status.isMCServer) return Message() + "这不是一个MineCraft服务器."

        val serverip = socket.ip.toString() + ":" + socket.port
        val isSrv = socket.isSRVDomainName
        val version = status.version

        if (status.icon.hasServerIcon()) {
            status.icon.saveImageFile("icon.jpg")
            val icon = mif.image(File(path))
            return Message() + "服务器地址： $serverip\n" +
                    "SRV解析: $isSrv\n" +
                    "版本: $version\n" +
                    "协议版本： ${status.protocolVersion}\n" +
                    "在线人数: ${status.onlinePlayers}/${status.maxPlayers} \n" +
                    "Motd: " + icon + "\n${status.motd.line1MotdText} \n ${status.motd.line2MotdText}"
        }

        return Message() + "服务器地址： $serverip\n" +
                "SRV解析: $isSrv\n" +
                "版本: $version\n" +
                "协议版本： ${status.protocolVersion}\n" +
                "在线人数: ${status.onlinePlayers}/${status.maxPlayers} \n" +
                "Motd: ${status.motd.line1MotdText} \n ${status.motd.line2MotdText}"
    }

}