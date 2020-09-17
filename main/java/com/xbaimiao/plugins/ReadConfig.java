package com.xbaimiao.plugins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ReadConfig {

    private final HashMap<String, String> yaml = new HashMap<>();

    protected ReadConfig(InputStream inputStream) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
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

    public String getString(String key) {
        return yaml.get(key);
    }
}
