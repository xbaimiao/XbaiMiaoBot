package com.xbaimiao.yamlconfig;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class Config {

    File file;
    HashMap<String,String> yaml = new HashMap<>();

    public Config(File file){
        try {
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            if (!file.exists()){
                if (file.createNewFile()) {
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
                    bw.write("#初始文件");
                    bw.close();
                }
            }
            this.file = file;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void reload() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String s;
            while ((s = br.readLine()) != null) {
                if (s.contains(": ")){
                    String[] args = s.split(": ");
                    yaml.put(args[0],args[1]);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getString(String key){
        return yaml.get(key) == null ? "" : yaml.get(key);
    }

}
