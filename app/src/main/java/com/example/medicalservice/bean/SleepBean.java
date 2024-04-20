package com.example.medicalservice.bean;

public class SleepBean {

    private String type;
    private int time;

    public SleepBean(String type, int time) {
        this.type = type;
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
