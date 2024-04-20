package com.example.medicalservice.bean;

public class DeviceBean {

    private String name;
    private String content;
    private int image;

    private String type;

    public DeviceBean(String name, String content, int image, String type) {
        this.name = name;
        this.content = content;
        this.image = image;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
