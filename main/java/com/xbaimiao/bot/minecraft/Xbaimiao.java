package com.xbaimiao.bot.minecraft;

import com.icecreamqaq.yuq.message.At;
import com.icecreamqaq.yuq.message.Message;
import com.icecreamqaq.yuq.message.MessageItem;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class Xbaimiao {

    private static Properties config;

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
    public static String readfile(String path, String encoding) {
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

    public static void writerfile(String path, String encoding, String content) {
        try {
            File file = new File(path);
            File ParentFile = file.getParentFile();
            if (!ParentFile.exists()) {
                ParentFile.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), encoding));
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
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

    @NotNull

    public static void saveconfig(String path) throws IOException {
        FileOutputStream oFile = new FileOutputStream(new File(path));
        config.store(oFile, null);
        oFile.close();
    }

    public static void writeConfig(String path, String key, String value) throws IOException {
        config = new Properties();
        File file = new File(path);
        File ParentFile = file.getParentFile();
        if (!ParentFile.exists()) {
            ParentFile.mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        FileInputStream in = new FileInputStream(file);
        config.load(in);
        config.put(key, value);
        saveconfig(path);
        in.close();
    }

    public static String readconfig(String path, String key) throws IOException {
        config = new Properties();
        File file = new File(path);
        if (!file.exists()) {
            return "该文件不存在";
        }
        config.load(new FileInputStream(file));
        String string = (String) config.get(key);
        if (string == null) {
            return "读取配置项为空";
        }

        return string;
    }

}
