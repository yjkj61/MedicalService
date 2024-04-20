package com.example.medicalservice.bean;

import java.io.Serializable;

public class JudegeBean implements Serializable {

    private String orderId;
    private String firstCommentatorId;

    private String firstCommentatorName;

    private String sGoodsBusinessId;

    private String sGoodsId;

    private String comprehensiveScore;

    private String goodsScore;

    private String commentsVideo = "";

    private String commentsImages = "";
    private String serviceScore;

    private String logisticsScore;

    private String commentsText;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getFirstCommentatorId() {
        return firstCommentatorId;
    }

    public void setFirstCommentatorId(String firstCommentatorId) {
        this.firstCommentatorId = firstCommentatorId;
    }

    public String getFirstCommentatorName() {
        return firstCommentatorName;
    }

    public void setFirstCommentatorName(String firstCommentatorName) {
        this.firstCommentatorName = firstCommentatorName;
    }

    public String getsGoodsBusinessId() {
        return sGoodsBusinessId;
    }

    public void setsGoodsBusinessId(String sGoodsBusinessId) {
        this.sGoodsBusinessId = sGoodsBusinessId;
    }

    public String getsGoodsId() {
        return sGoodsId;
    }

    public void setsGoodsId(String sGoodsId) {
        this.sGoodsId = sGoodsId;
    }

    public String getComprehensiveScore() {
        return comprehensiveScore;
    }

    public void setComprehensiveScore(String comprehensiveScore) {
        this.comprehensiveScore = comprehensiveScore;
    }

    public String getGoodsScore() {
        return goodsScore;
    }

    public void setGoodsScore(String goodsScore) {
        this.goodsScore = goodsScore;
    }

    public String getCommentsVideo() {
        return commentsVideo;
    }

    public void setCommentsVideo(String commentsVideo) {
        this.commentsVideo = commentsVideo;
    }

    public String getCommentsImages() {
        return commentsImages;
    }

    public void setCommentsImages(String commentsImages) {
        this.commentsImages = commentsImages;
    }

    public String getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(String serviceScore) {
        this.serviceScore = serviceScore;
    }

    public String getLogisticsScore() {
        return logisticsScore;
    }

    public void setLogisticsScore(String logisticsScore) {
        this.logisticsScore = logisticsScore;
    }

    public String getCommentsText() {
        return commentsText;
    }

    public void setCommentsText(String commentsText) {
        this.commentsText = commentsText;
    }
}
