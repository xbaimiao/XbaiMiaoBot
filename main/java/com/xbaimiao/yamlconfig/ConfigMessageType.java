package com.xbaimiao.yamlconfig;

public enum ConfigMessageType {
    FACE("face_"),
    IMAGE("image_"),
    AT("At_"),
    NULL("NoImpl");

    String type;
    ConfigMessageType(String type){
        this.type = type;
    }
    public int getSize(){
        return type.length();
    }
    public String getType(){
        return type;
    }
}
