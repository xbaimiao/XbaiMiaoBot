package com.xbaimiao.bot.minecraft;

import com.icecreamqaq.yuq.message.At;
import com.icecreamqaq.yuq.message.Message;
import com.icecreamqaq.yuq.message.MessageItem;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Xbaimiao {

    public static long getAt(Message message) {
        for (MessageItem item : message.getBody()) {
            if (item instanceof At) return ((At) item).getUser();
        }
        return -1000;
    }

    public static List<Long> getAtList(Message message) {
        ArrayList<Long> list = new ArrayList<>();
        for (MessageItem item : message.getBody()) {
            if (item instanceof At) {
                list.add(((At) item).getUser());
            }
        }
        return list;
    }

    /**
     * @return 随机验证码
     */
    public static String yzm() {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {
            char ch = str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        return sb.toString();
    }

    /**
     * 将 §替换为空
     *
     * @param string 需要进行操作的字符串
     * @return 删除§后的字串符
     */
    public static String replace(String string) {
        String regex = "\\n(?=((?!\\n).)*$)";
        string = string.replaceAll(regex, "").replaceAll("§([klmnor0123456789abcdef])", "");
        return string;
    }

    /**
     * 读取txt文件的内容<br>
     * System.getProperty("user.dir")获取运行目录<br>
     *
     * @param path     想要读取的文件对象
     * @param encoding 文本编码
     * @return 返回文件内容
     */
    public static String readFile(String path, String encoding) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), encoding)); //构造一个BufferedReader类来读取文件
            String s;
            while ((s = br.readLine()) != null) { //使用readLine方法，一次读一行
                if (sb.toString().equals("")) {
                    sb.append(s);
                } else {
                    sb.append(System.lineSeparator()).append(s);
                }
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            return sb.toString();
        }
    }

    @NotNull
    public static String getMsg(Message message) {
        ArrayList<MessageItem> list = message.getBody();
        StringBuilder msg = new StringBuilder();
        for (MessageItem s : list) {
            if (s.toPath().equals("NoImpl")) {
                continue;
            }
            msg.append(s.toPath());
        }
        return msg.toString();
    }

}
