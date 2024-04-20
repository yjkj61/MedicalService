package com.example.medicalservice.bean;

public class BannerBean {

    private String imageLeft;
    private String imageTop;
    private String imageBottom;

    public BannerBean(String imageLeft, String imageTop, String imageBottom) {
        this.imageLeft = imageLeft;
        this.imageTop = imageTop;
        this.imageBottom = imageBottom;
    }


    public String getImageLeft() {
        return imageLeft;
    }

    public void setImageLeft(String imageLeft) {
        this.imageLeft = imageLeft;
    }

    public String getImageTop() {
        return imageTop;
    }

    public void setImageTop(String imageTop) {
        this.imageTop = imageTop;
    }

    public String getImageBottom() {
        return imageBottom;
    }

    public void setImageBottom(String imageBottom) {
        this.imageBottom = imageBottom;
    }
}
