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
        ConfigList.configs.add(this);
        try {
            if (file.exists()) {
                reload();
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

    public void reload() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String s;
            while ((s = br.readLine()) != null) {
                if (s.contains(": \"")) {
                    String[] args = s.split(": \"");
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

    public String getString(String key) {
        return yaml.getOrDefault(key.replace("'", "\""), null);
    }

    public int getInt(String key) {
        return Integer.parseInt(yaml.get(key));
    }

    public void remove(String key) {
        yaml.remove(key);
    }

    public void delete(){
        if (file.delete()){
            yaml.clear();
        }
    }

    public void set(String key, String vault) {
        yaml.put(key, vault);
    }

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
