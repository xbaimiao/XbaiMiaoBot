package com.xbaimiao.bot.minecraft.gourp

import com.IceCreamQAQ.Yu.annotation.Action
import com.IceCreamQAQ.YuWeb.annotation.WebController
import com.icecreamqaq.yuq.mf
import com.icecreamqaq.yuq.send
import com.xbaimiao.bot.groupadmin.Operation

@WebController
class WebEvent {

	@Action("xbaimiao")
	fun gameChatForGroup(msg: String, key: String, group: String): String {
		if (key != "55630592g@") return "Error"
		val mess = mf.newMessage() + msg
		mess.group = group.toLong()
		mess.send()
		return "ok"
	}

	@Action("blacklist")
	fun list(): String {
		return "Currently, there are ${Operation.blackList.size}" + Operation.blackList.toString()
	}

}