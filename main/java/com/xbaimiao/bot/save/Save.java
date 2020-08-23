package com.xbaimiao.bot.save;

import com.icecreamqaq.yuq.message.*;
import org.yaml.snakeyaml.Yaml;

import javax.inject.Inject;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class Save {

    public static final String dataFile = System.getProperty("user.dir") + File.separator + "Data" + File.separator + "MessageObject.yml";
    public static final Yaml yml = new Yaml();
    public static final File file = new File(dataFile);
    public static final File fileParent = file.getParentFile();
    @Inject
    private MessageItemFactory mif;

    public static void saveMessage(String messageId, Message message) {
        ArrayList<MessageItem> list = message.getBody();
        StringBuilder msg = new StringBuilder();

        for (MessageItem s : list) {
            if (s.toPath().equals("NoImpl")) continue;
            if (s.toPath().startsWith("At_")) {
                msg.append(s.toPath()).append("|");
                continue;
            }
            if (s instanceof Face) {
                msg.append("face_").append(((Face) s).getFaceId()).append("|");
                continue;
            }
            if (s instanceof Image) {
                msg.append("image_").append(((Image) s).getUrl()).append("|");
                continue;
            }
            if (s.toPath().startsWith("ping_")) {
                msg.append(s).append("|");
            }
            msg.append(s.toPath()).append("|");
        }
        String mSg = msg.substring(0, msg.length() - 1);
        HashMap<String, String> map = loadDataFile();
        map.put(messageId, mSg);
        try {
            yml.dump(map, new FileWriter(dataFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean removeMessage(String messageId) {
        HashMap<String, String> map = loadDataFile();
        if (map.get(messageId) == null) {
            return false;
        }
        map.remove(messageId);
        try {
            yml.dump(map, new FileWriter(dataFile));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static HashMap<String, String> loadDataFile() {
        try {
            if (!fileParent.exists()) {
                fileParent.mkdirs();
            }
            if (!file.exists()) {
                if (file.createNewFile()) {
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
                    bw.write("例子: 这是个例子");
                    bw.close();
                }
            }
            return (HashMap<String, String>) yml.load(new FileInputStream(file));
        } catch (IOException e) {
            return new HashMap<>();
        }
    }
}
