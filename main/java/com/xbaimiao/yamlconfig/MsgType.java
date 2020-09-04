package com.xbaimiao.yamlconfig;

public enum MsgType {
    FACE("face_"),
    IMAGE("image_"),
    AT("At_"),
    XML("xml_"),
    JSONEX("jsonex_"),
    NULL("NoImpl");

    String type;

    MsgType(String type) {
        this.type = type;
    }

    public int getSize() {
        return type.length();
    }

    public String getType() {
        return type;
    }
}
