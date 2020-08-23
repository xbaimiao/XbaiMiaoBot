package com.xbaimiao.bot.groupadmin

import com.IceCreamQAQ.Yu.annotation.Event
import com.IceCreamQAQ.Yu.annotation.EventListener
import com.icecreamqaq.yuq.event.GroupMessageEvent
import com.xbaimiao.bot.minecraft.Xbaimiao
import java.io.*
import java.net.URL

@EventListener
class GetIp {

	@Event
	fun send(event: GroupMessageEvent){
		val message = event.message
		val msg = Xbaimiao.getMsg(message)
		if (msg.toUpperCase().startsWith("test")){
			val url = URL("http://www.baidu.con")
			val read = BufferedReader(InputStreamReader(url.openStream()))
			var urlString:String
			val sb = StringBuilder()
			while (read.readLine().also { urlString = it } != null){
				sb.append(urlString)
			}
			println(sb)
		}
	}



}
fun main(){
	val url = URL("http://tzaolong.com.cn/plugin.php?id=mini_gamelist")
	val read = BufferedReader(InputStreamReader(url.openStream()))
	var urlString:String?
	val sb = StringBuilder()
	while (read.readLine().also { urlString = it } != null){
		sb.append(urlString).append("\n")
	}
	val list = sb.toString().split("<div class=\"minigamelist\">")[1].split("<style>")[0] //服务器列表
	val server = list.split("<dl>")
	for (s in server){
		if (s.contains("a href=")){
			println("服务器名字 ${s.replace(" ","").split("<p>")[1].split("</p>")[0]}")
			println("服务器帖子地址 http://tzaolong.com.cn/${s.split("\">")[0].replace(" ","")
					.replace("&amp;","&").substring(9)}")
		}
	}
}