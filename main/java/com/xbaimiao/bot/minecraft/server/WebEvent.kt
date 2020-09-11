package com.xbaimiao.bot.minecraft.server

import com.IceCreamQAQ.Yu.annotation.Action
import com.IceCreamQAQ.YuWeb.annotation.WebController
import com.icecreamqaq.yuq.message.Message
import com.icecreamqaq.yuq.yuq
import com.xbaimiao.bot.groupadmin.Operation

@WebController
class WebEvent {

    @Action("xbaimiao")
    fun gameChatForGroup(msg: String, key: String, group: String): String {
        if (key != "55630592g@") return "Error"
        val mess = Message() + msg
        yuq.groups[group.toLong()]?.sendMessage(mess)
        return "ok"
    }

    @Action("blacklist")
    fun list(): String {
        return "Currently, there are ${Operation.blackList.size}" + Operation.blackList.toString()
    }

}