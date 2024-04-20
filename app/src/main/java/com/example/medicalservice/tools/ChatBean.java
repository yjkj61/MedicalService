package com.example.medicalservice.tools;

public class ChatBean {

    private String msg;
    private String time;
    private boolean self;
    private boolean seeding;


    public ChatBean(String msg, String time, boolean self, boolean seeding) {
        this.msg = msg;
        this.time = time;
        this.self = self;
        this.seeding = seeding;
    }

    public boolean isSeeding() {
        return seeding;
    }

    public void setSeeding(boolean seeding) {
        this.seeding = seeding;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSelf() {
        return self;
    }

    public void setSelf(boolean self) {
        this.self = self;
    }
}
