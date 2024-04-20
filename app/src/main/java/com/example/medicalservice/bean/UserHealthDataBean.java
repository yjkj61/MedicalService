package com.example.medicalservice.bean;

public class UserHealthDataBean {

    private String type;
    private double progress;

    private String textValue;


    public UserHealthDataBean(String type, double progress, String textValue) {
        this.type = type;
        this.progress = progress;
        this.textValue = textValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }
}
