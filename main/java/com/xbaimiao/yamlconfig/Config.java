package com.xbaimiao.yamlconfig;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Set;

public class Config {

    private final File file;
    private final HashMap<String, String> yaml = new HashMap<>();
    private boolean empty;

    public Config(File file) {
        this.file = file;
        try {
            if (file.exists()) {
                reload();
                if (yaml.isEmpty()) {
                    empty = true;
                    return;
                }
                empty = false;
                return;
            }
            if (!file.getParentFile().exists()) {
                if (!file.getParentFile().mkdirs()) {
                    return;
                }
            }
            if (!file.exists()) {
                if (file.createNewFile()) {
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
                    bw.write("#初始文件");
                    bw.close();
                }
            }
            empty = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isEmpty() {
        return empty;
    }

    /**
     * 从文件中重新加载到配置文件
     */
    public void reload() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String s;
            while ((s = br.readLine()) != null) {
                if (s.contains(": \"")) {
                    String[] args = s.split(": \"");
                    if (args.length < 2) {
                        continue;
                    }
                    if (args[1].endsWith("\"")) {
                        yaml.put(args[0], args[1].substring(0, args[1].length() - 1).replace("\"", "'"));
                        continue;
                    }
                    yaml.put(args[0], args[1].replace("\"", "'"));
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取目标key值的 String 值
     *
     * @param key 键值
     * @return String 对象
     */
    public String getString(String key) {
        return yaml.getOrDefault(key.replace("'", "\""), null);
    }

    /**
     * 获取目标key值的 int 值
     *
     * @param key 键值
     * @return int 对象
     */
    public int getInt(String key) {
        return Integer.parseInt(yaml.get(key));
    }

    /**
     * 获取目标key值的 long 值
     *
     * @param key 键值
     * @return long 对象
     */
    public long getLong(String key) {
        return Long.parseLong(yaml.get(key));
    }

    /**
     * 获取目标key值的 boolean 值
     *
     * @param key 键值
     * @return boolean 对象
     */
    public boolean getBoolean(String key) {
        return yaml.get(key).equals("true");
    }

    /**
     * 移除一个key值
     *
     * @param key 键值
     */
    public void remove(String key) {
        yaml.remove(key);
    }

    /**
     * 删除配置文件(包括文件)
     */
    public void delete() {
        if (file.delete()) {
            yaml.clear();
        }
    }

    /**
     * 将目标key值设置为提供的 String 值
     *
     * @param key   键值
     * @param vault String 值
     */
    public void set(String key, String vault) {
        yaml.put(key, vault);
    }

    /**
     * 将目标key值设置为提供的 Integer 值
     *
     * @param key   键值
     * @param vault Integer 值
     */
    public void set(String key, Integer vault) {
        yaml.put(key, vault.toString());
    }

    /**
     * 将目标key值设置为提供的 Long 值
     *
     * @param key   键值
     * @param vault Long 值
     */
    public void set(String key, Long vault) {
        yaml.put(key, vault.toString());
    }

    /**
     * 将目标key值设置为提供的 Boolean 值
     *
     * @param key   键值
     * @param vault Boolean 值
     */
    public void set(String key, Boolean vault) {
        yaml.put(key, vault.toString());
    }

    /**
     * 保存配置文件到文件
     *
     * @return 是否保存成功
     */
    public boolean save() {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (String key : yaml.keySet()) {
                sb.append(key).append(": \"").append(yaml.get(key)).append("\"\r\n");
            }
            bw.write(sb.toString());
            bw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取全部的key
     *
     * @return 全部的key
     */
    public Set<String> getKeys() {
        return yaml.keySet();
    }

    @Override
    public String toString() {
        return "Config{" +
                "file=" + file +
                ", yaml=" + yaml +
                '}';
    }

}
