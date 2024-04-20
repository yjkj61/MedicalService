package com.example.medicalservice.bean;

public class HomeCommentBean {
    private String name;
    private String value;
    private String other;

    public HomeCommentBean(String name, String value, String other) {
        this.name = name;
        this.value = value;
        this.other = other;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
