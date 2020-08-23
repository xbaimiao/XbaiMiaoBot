package com.xbaimiao;

import com.IceCreamQAQ.Yu.loader.AppClassloader;
import com.icecreamqaq.yuq.mirai.YuQMiraiStart;

public class Start {

    public static void main(String[] args) {
        AppClassloader.registerTransformerList("com.IceCreamQAQ.Yu.web.WebClassTransformer");
        YuQMiraiStart.start(args);
    }

}
