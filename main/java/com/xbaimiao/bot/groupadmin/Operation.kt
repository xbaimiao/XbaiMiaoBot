package com.xbaimiao.bot.groupadmin

import com.icecreamqaq.yuq.entity.Group
import com.icecreamqaq.yuq.entity.Member
import com.icecreamqaq.yuq.message.Message
import com.icecreamqaq.yuq.mif
import com.xbaimiao.bot.minecraft.Xbaimiao
import com.xbaimiao.yamlconfig.ConfigMessage
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

object Operation {

    private val prefix = System.getProperty("user.dir") + File.separator + "GroupAdmin" + File.separator
    val configYml = ConfigMessage(File("${prefix}config.yml"))
    val blackYml = File("${prefix}black.yml")
    val cruxYml = File("${prefix}crux.yml")
    private val recordYml = ConfigMessage(File("${prefix}record.yml"))
    var help = File("${prefix}help.jpg")
    var blackList = Xbaimiao.readFile(blackYml.path, "utf-8").split(",")
    var cruxList = Xbaimiao.readFile(cruxYml.path, "utf-8").split(",")

    @JvmStatic
    fun isAdmin(long: Long): Boolean = EventMsg.adminList.contains(long)

    fun memberManage(qq: Member, msg: String, message: Message) {
        val fromQQ = qq.id
        if (blackList.contains(fromQQ.toString())) {
            qq.kick("爪")
            message.recall()
            return
        }
        if (msg.contains("\n\n\n\n")) {
            message.recall()
            recordYml.set(fromQQ.toString(), "刷屏")
            qq.ban(600)
            return
        }
        if (msg.contains("https://jq.qq.com/?")) {
            message.recall()
            qq.ban(600)
            return
        }
        if (msg.contains("[QQ红包]我发了一个“口令红包”，请使用新版手机QQ查收红包")) {
            message.recall()
            qq.ban(60)
            qq.sendMessage(Message().plus("此群禁止发送口令红包"))
            return
        }
        val mgc = msg.replace("[。 ?/？!@#$%^&*]".toRegex(), "")
        for (s in cruxList) {
            if (s == "") continue
            if (mgc.contains(s)) {
                message.recall()
                qq.ban(600)
                recordYml.set(fromQQ.toString(), "触发了敏感词${s}\\n他的原话为\"$msg\"")
                recordYml.save()
                return
            }
        }
    }

    fun ban(group: Group, msg: String, message: Message) {
        if (msg.startsWith("禁言")) {
            if (!msg.contains("At_")) {
                group.sendMessage(Message() + "禁言谁请艾特一下")
                return
            }
            val qList = Xbaimiao.getAtList(message)
            for (q in qList) {
                if (isAdmin(q)) continue
                group[q].ban(1200)
            }
            return
        }
        if (msg.startsWith("解禁")) {
            if (!msg.contains("At_")) {
                group.sendMessage(Message() + "解禁谁请艾特一下")
                return
            }
            val qList = Xbaimiao.getAtList(message)
            for (q in qList) {
                group[q].ban(0)
            }
            return
        }
        if (msg == "查看敏感词") {
            group.sendMessage(Message() + cruxList.toString())
            return
        }
        if (msg == "/help") {
            group.sendMessage(Message() + mif.image(help))
            return
        }
    }

    fun blackCommands(group: Group, msg: String, message: Message) {
        if (msg.startsWith("查询")) {
            val cx = msg.replace(" ", "")
            if (cx == "查询") {
                group.sendMessage(Message() + "你还没带上要查询的QQ")
                return
            }
            if (cx.length > 8) {
                val jl = recordYml.getString(cx.substring(2))
                if (jl == null) {
                    group.sendMessage(Message() + "该QQ暂无记录")
                    return
                }
                group.sendMessage(Message() + jl.replace("\\n", "\n"))
            } else {
                group.sendMessage(Message() + "你输入的不是一个QQ")
                return
            }
        }
        if (msg.startsWith("添加黑名单")) {
            if (msg == "添加黑名单") {
                group.sendMessage(Message() + "你还没有指明需要添加谁")
                return
            }
            val xx = msg.replace(" ", "")
            if (!msg.contains("At_")) {
                if (xx.length > 11) {
                    val q = xx.substring(5)
                    if (blackList.contains(q)) {
                        group.sendMessage(Message() + "黑名单内已有QQ$q")
                        return
                    }
                    if (isAdmin(q.toLong())) {
                        group.sendMessage(Message() + "${q}是管理员的啊")
                        return
                    }
                    addBlack(q)
                    group.sendMessage(Message() + ("QQ为${q}的用户成功被拉入黑名单"))
                    group[q.toLong()].kick("你已被添加黑名单")
                } else {
                    group.sendMessage(Message() + ("你输入的不是一个QQ号"))
                }
            } else {
                val q: Long = Xbaimiao.getAt(message)
                if (blackList.contains(q.toString())) {
                    group.sendMessage(Message() + ("黑名单内已有QQ$q"))
                    return
                }
                if (isAdmin(q)) {
                    group.sendMessage(Message() + "${q}是管理员的啊")
                    return
                }
                addBlack(q.toString())
                group.sendMessage(Message() + ("QQ为${q}的用户成功被拉入黑名单"))
                group[q].kick("你已被添加黑名单")
            }
        }
        if (msg.startsWith("删除黑名单")) {
            if (msg == "删除黑名单") {
                group.sendMessage(Message().plus("你并没有指明需要删除谁 , 在后面带上需要删除的QQ"))
                return
            }
            val xx = msg.replace(" ", "")
            val q = xx.substring(5)
            if (!blackList.contains(q)) {
                group.sendMessage(Message().plus("${q}不在黑名单列表"))
                return
            }
            removeBlack(q)
            group.sendMessage(Message().plus("QQ为" + q + "的用户黑名单已被解除"))
        }
        if (msg.startsWith("添加敏感词")) {
            if (msg == "添加敏感词") {
                group.sendMessage(Message().plus("添加的敏感词内容你没有进行说明 , 在后面带上需要添加的敏感词 不要加："))
                return
            }
            val xx = msg.replace(" ", "")
            val m = xx.substring(5)
            for (s in cruxList) {
                if (m.contains(s)) {
                    group.sendMessage(Message().plus("你添加的敏感词\"$m\"和敏感词 \"$s\"重复了,所以此次添加操作取消"))
                    return
                }
            }
            for (s in cruxList) {
                if (s.contains(m)) {
                    group.sendMessage(Message().plus("你添加的敏感词\"$m\"和敏感词 \"$s\"重复了,\n此次操作自动删除\n\"$s\""))
                    removeMgc(s)
                }
            }
            if (m in cruxList) {
                group.sendMessage(Message().plus("\"$m\"已经在敏感词列表了,没必要在加一次"))
                return
            }
            if (xx.length == 6) {
                group.sendMessage(Message().plus("\"$m\"属于单个字符,容易误禁言,取消添加"))
                return
            }
            addMgc(m)
            group.sendMessage(Message().plus("敏感词\"$m\"添加成功"))
        }
        if (msg.startsWith("删除敏感词")) {
            if (msg == "删除敏感词") {
                group.sendMessage(Message().plus("你没有说明需要删除什么"))
                return
            }
            val xx = msg.replace(" ", "")
            val m = xx.substring(5)
            if (!cruxList.contains(m)) {
                group.sendMessage(Message().plus("\"$m\"不在敏感词列表了,检查一下是不是输错了"))
                return
            }
            removeMgc(m)
            group.sendMessage(Message().plus("敏感词\"$m\"删除成功"))
        }
    }

    private fun addBlack(qq: String) {
        val qqlist = Xbaimiao.readFile(blackYml.path, "UTF-8")
        val a = "$qqlist,$qq"
        blackList = a.split(",")
        val bw = BufferedWriter(OutputStreamWriter(FileOutputStream(blackYml), "UTF-8"))
        bw.write(a)
        bw.close()
    }

    private fun removeBlack(qq: String) {
        val qqlist = Xbaimiao.readFile(blackYml.path, "UTF-8")
        val a = qqlist.replace(",$qq", "")
        blackList = a.split(",")
        val bw = BufferedWriter(OutputStreamWriter(FileOutputStream(blackYml), "UTF-8"))
        bw.write(a)
        bw.close()
    }

    private fun removeMgc(msg: String) {
        val mgc = Xbaimiao.readFile(cruxYml.path, "UTF-8")
        val a = mgc.replace(",$msg", "")
        cruxList = a.split(",")
        val bw = BufferedWriter(OutputStreamWriter(FileOutputStream(cruxYml), "UTF-8"))
        bw.write(a)
        bw.close()
    }

    private fun addMgc(msg: String) {
        val mgc = Xbaimiao.readFile(cruxYml.path, "UTF-8")
        val a = "$mgc,$msg"
        cruxList = a.split(",")
        val bw = BufferedWriter(OutputStreamWriter(FileOutputStream(cruxYml), "UTF-8"))
        bw.write(a.replace("[。 ?/？!@#$%^&*]".toRegex(), ""))
        bw.close()
    }
}