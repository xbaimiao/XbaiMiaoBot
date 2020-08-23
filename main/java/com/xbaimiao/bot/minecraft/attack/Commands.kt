package com.xbaimiao.bot.minecraft.attack

import com.IceCreamQAQ.Yu.annotation.Event
import com.IceCreamQAQ.Yu.annotation.EventListener
import com.icecreamqaq.yuq.event.GroupMessageEvent
import com.icecreamqaq.yuq.message.Message
import com.icecreamqaq.yuq.mf
import com.icecreamqaq.yuq.yuq
import com.xbaimiao.bot.minecraft.Xbaimiao
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

@EventListener
class Commands {

	private val weblist = ArrayList<Long>()

	@Event
	fun attackMc(e: GroupMessageEvent) {
		val msg = Xbaimiao.getMsg(e.message).replace("NoImpl", "")
		val qq = e.message.qq!!
		val group = e.message.group!!
		webop(group, qq, msg)
		if (qq == 3104026189) {
			when (msg) {
				"网页压力测试" -> {
					if (WebAttack.isRun) {
						sendMessage(group, mf.newMessage() + "请耐心等待上一次的压力测试结束")
						return
					}
					sendMessage(group, mf.newMessage() + "请输入网页地址")
					weblist.add(qq)
				}
				"MC压力测试" -> {
					sendMessage(group, mf.newMessage() + "暂未推出")
				}
			}
		}
	}

	private fun sendMessage(fromGroup: Long, msg: Message) {
		yuq.groups[fromGroup]?.sendMessage(msg)
	}

	private fun webop(group: Long, qq: Long, msg: String) {
		if (qq in weblist) {
			if (WebAttack.isRun) {
				sendMessage(group, mf.newMessage() + "请耐心等待上一次的压力测试结束")
				weblist.remove(qq)
				return
			}
			weblist.remove(qq)
			var ip = if (msg.contains("http")) msg.replace(" ", "")
			else "http://" + msg.replace(" ", "")
			try {
				var url = URL(ip)
				try {
					url.openStream()
				} catch (e: IOException) {
					ip = "https://" + msg.replace(" ", "")
				}
				url = URL(ip)
				sendMessage(group, mf.newMessage() + "已开始压力测试,5分钟后自动停止运行")
				WebAttack.webAttack(group, url)
			} catch (o: MalformedURLException) {
				sendMessage(group, mf.newMessage() + "网页格式错误")
				return
			}
		}
	}
}