package com.xbaimiao.bot.groupadmin

import com.IceCreamQAQ.Yu.annotation.Event
import com.IceCreamQAQ.Yu.annotation.EventListener
import com.IceCreamQAQ.Yu.event.events.AppStartEvent
import com.icecreamqaq.yuq.entity.Member
import com.icecreamqaq.yuq.event.GroupMemberJoinEvent
import com.icecreamqaq.yuq.event.GroupMessageEvent
import com.icecreamqaq.yuq.yuq
import com.xbaimiao.bot.minecraft.Xbaimiao

@EventListener
class EventMsg {

	companion object {
		var adminList: ArrayList<Long> = ArrayList()
	}

	private var groupList = ArrayList<String>()

	@Event
	fun chatEvent(event: GroupMessageEvent) {
		val message = event.message
		val qq = event.sender
		val group = event.group
		if (group.id.toString() in groupList) {
			val msg = Xbaimiao.getMsg(message)
			if (!Operation.isAdmin(qq.id)) {
				Operation.memberManage(qq, msg, message)
			} else {
				Operation.ban(group, msg, message)
				Operation.blackCommands(group, msg, message)
			}
		}
	}

	@Event
	fun start(e: AppStartEvent) {
		println(e.javaClass.name)
		if (Operation.configYml.isEmpty) {
			Operation.configYml.set("group", "这里写启用的群聊，用,分隔开")
			Operation.configYml.save()
		}
		groupList = Operation.configYml.getString("group").split(",") as ArrayList<String>
		if (!Operation.cruxYml.exists()) Operation.cruxYml.createNewFile()
		if (!Operation.blackYml.exists()) Operation.blackYml.createNewFile()
		val map: Map<Long, Member> = yuq.groups[885614586L]?.members ?: HashMap()
		adminList.addAll(map.keys)

	}

	@Event
	fun onGroupMemberJoinEvent(e: GroupMemberJoinEvent) {
		if (Operation.blackList.contains(e.member.id.toString())) {
			e.member.kick("gun")
		}
	}

}