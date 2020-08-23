package com.xbaimiao.bot;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Config {

    private File file;

    public Config(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                if (file.createNewFile()) {
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
                    bw.write("#初始文件");
                    bw.close();
                }
            }
            this.file = file;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Config(File file) {
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                if (file.createNewFile()) {
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
                    bw.write("#初始文件");
                    bw.close();
                }
            }
            this.file = file;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<String> readFile(File path) {
        ArrayList<String> list = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            String s;
            while ((s = br.readLine()) != null) {
                list.add(s);
            }
            br.close();
            return list;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void set(String key, String value) {
        try {
            ArrayList<String> txt = readFile(this.file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            int txtSize = txt.size();
            boolean x = true;
            for (String s : txt) {
                if (s.startsWith("#")) {
                    sb.append(s).append("\n");
                    continue;
                }
                if (s.contains(":")) {
                    String[] args = s.split(": ");
                    if (args[0].equals(key)) {
                        sb.append(key).append(": ").append(value).append("\n");
                        x = false;
                        continue;
                    }
                    sb.append(s).append("\n");
                }
            }
            if (x) {
                sb.append(key).append(": ").append(value).append("\n");
            }
            bw.write(sb.substring(0, sb.toString().length() - 1));
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getString(String key) {
        ArrayList<String> txt = readFile(this.file);
        for (String s : txt) {
            if (s.startsWith("#")) continue;
            if (!s.contains(": ")) continue;
            String[] args = s.split(": ");
            if (args[0].equals(key)) {
                return args[1];
            }
        }
        return null;
    }

    public boolean isNull() {
        ArrayList<String> list = readFile(file);
        return list.size() == 0 || (list.get(0).contains("#初始文件") && list.size() == 1);
    }

}
