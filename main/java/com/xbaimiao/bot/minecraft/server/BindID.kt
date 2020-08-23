package com.xbaimiao.bot.minecraft.server

import com.IceCreamQAQ.Yu.annotation.Action
import com.IceCreamQAQ.Yu.annotation.Before
import com.icecreamqaq.yuq.annotation.*
import com.icecreamqaq.yuq.controller.BotActionContext
import com.icecreamqaq.yuq.message.Message
import com.icecreamqaq.yuq.mif
import com.xbaimiao.bot.minecraft.Xbaimiao
import java.io.IOException

@GroupController
@ContextController
class BindID {

	@Before
	@Throws(Message::class)
	fun before(qq: Long) {
		try {
			val ID = Xbaimiao.readconfig(Main.IDpath, qq.toString())
			if (ID != "读取配置项为空") throw mif.text("你已经绑定过ID了").toMessage()
		} catch (e: IOException) {
			e.printStackTrace()
			throw mif.text("请重试").toMessage()
		}
	}

	@Action("\\绑定(i|I)(d|D)\\")
	@NextContext("bindid")
	fun bindid(@Save @PathVar(1) id: String?, actionContext: BotActionContext): String {
		if (id == null) throw mif.text("你在绑定空气吗= - = 告诉我你的ID").toMessage()
		val yzm = Xbaimiao.yzm()
		return try {
			val rcon = Rcon(Main.Rconhost, Main.Rconport, Main.Rconpassword.toByteArray())
			val str = rcon.command("msg $id 验证码为\"$yzm\"")
			if (str.contains("请输入正确的ID")) {
				throw mif.text(str).toMessage()
			}
			actionContext.session["key"] = yzm
			"验证码已发送至你的游戏聊天框，请在接下来的 30 分钟内在本群回复你的验证码"
		} catch (e: IOException) {
			throw mif.text("服务器连接失败").toMessage()
		}
	}

	@Action("bindid")
	fun dobindid(@PathVar(0) rekey: String, id: String, key: String, qq: Long): String {
		return if (key == rekey) {
			try {
				Xbaimiao.writeConfig(Main.IDpath, qq.toString(), id)
				"ID'$id'绑定成功"
			} catch (e: IOException) {
				"ID'$id'绑定失败"
			}
		} else "验证码错误，请注意区分大小写"
	}
}