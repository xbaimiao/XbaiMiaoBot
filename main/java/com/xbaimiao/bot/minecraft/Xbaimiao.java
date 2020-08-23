package com.xbaimiao.bot.minecraft;

import com.icecreamqaq.yuq.message.At;
import com.icecreamqaq.yuq.message.Message;
import com.icecreamqaq.yuq.message.MessageItem;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

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

    /**
     * 从文本中制作列表<br>
     *
     * @param split  分隔符
     * @param string 需要分割的文本
     * @return 分割开的列表
     */
    public static List<String> StringorList(String split, String string) {
        String[] strs = string.split(split);
        return Arrays.asList(strs);
    }

    /**
     * 请求的url链接  返回的是json字符串<br>
     * 使用方法 String string = Void.getURLContent("http://.....");<br>
     *
     * @param urlStr 请求地址
     * @return 网页源码
     */
    public static String getURLContent(String urlStr) {
        //请求的url
        URL url;
        //请求的输入流
        BufferedReader in = null;
        //输入流的缓冲
        StringBuilder sb = new StringBuilder();
        try {
            url = new URL(urlStr);
            in = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            String str;
            //一行一行进行读入
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close(); //关闭流
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static String toUnicode(String str) {
        String tmp;
        StringBuffer sb = new StringBuffer(1000);
        char c;
        int i, j;
        sb.setLength(0);
        for (i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            sb.append("\\u");
            j = (c >>> 8); //取出高8位?
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1)
                sb.append("0");
            sb.append(tmp);
            j = (c & 0xFF); //取出低8位?
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1)
                sb.append("0");
            sb.append(tmp);
        }
        return new String(sb);
    }

    public static String getTime(String format) {
        Long timeStamp = System.currentTimeMillis();  //获取当前时间戳
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.parseLong(String.valueOf(timeStamp))));

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

    public static String ChangeUTFToISO(String serverurlUTF) {
        StringBuilder resultStr = new StringBuilder();
        for (int i = 0; i < serverurlUTF.length(); i++) {
            char c = serverurlUTF.charAt(i);
            if (c <= 255) {
                resultStr.append(c);
            } else {
                byte[] b;
                try {
                    b = String.valueOf(c).getBytes(StandardCharsets.UTF_8);
                } catch (Exception ex) {
                    b = new byte[0];
                }
                for (int value : b) {
                    int k = value;
                    if (k < 0)
                        k += 256;
                    resultStr.append("%").append(Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return resultStr.toString();
    }

}
