package com.xbaimiao.bot.minecraft.gourp

import com.IceCreamQAQ.Yu.annotation.Event
import com.IceCreamQAQ.Yu.annotation.EventListener
import com.icecreamqaq.yuq.event.GroupMemberJoinEvent
import com.icecreamqaq.yuq.event.GroupMessageEvent
import com.icecreamqaq.yuq.mf
import com.icecreamqaq.yuq.mif
import com.icecreamqaq.yuq.yuq

@EventListener
class NoDummyPlayer {

	private val timeingThread = HashMap<Long, Thread>()

	@Event
	fun playerJoin(event: GroupMemberJoinEvent) {
		val qq = event.member.id
		val group = event.group.id
		if (group != 1128456281L) {
			return
		}
		timeing(group, qq)
		yuq.groups[group]?.sendMessage(mf.newMessage() + mif.at(qq) + "欢迎新玩家，进服教程请点击http://server.xbaimiao.com:25567/")
	}

	@Event
	fun newsPlayerChat(event: GroupMessageEvent) {
		val qq = event.message.qq
		if (qq in timeingThread.keys) {
			timeingThread[qq]?.stop()
			timeingThread.remove(qq)
		}
	}

	private fun timeing(gourp: Long, qq: Long) {
		val run = Runnable {
			Thread.sleep(600000)
			yuq.groups[gourp]?.get(qq)?.kick("你未在10分钟内发言")
		}
		val thread = Thread(run)
		timeingThread.put(qq, thread)
		thread.start()
	}
}