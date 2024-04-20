package com.example.medicalservice.bean;

import java.io.Serializable;

public class BuyGoodBean implements Serializable {


    private String sGoodsBusinessId;
    private String sGoodsId;
    private String sGoodsSpecificationsId;
    private String sGoodsSpecificationsName;
    private String goodsNumber;
    private String goodsPrice;
    private String orderPrice;
    private String address;
    private String contact;
    private String phone;
    private String payWay;
    private String orderSource;
    private String deliveryMethod = "";
    private String weight;

    public String getSGoodsBusinessId() {
        return sGoodsBusinessId;
    }

    public void setSGoodsBusinessId(String sGoodsBusinessId) {
        this.sGoodsBusinessId = sGoodsBusinessId;
    }

    public String getSGoodsId() {
        return sGoodsId;
    }

    public void setSGoodsId(String sGoodsId) {
        this.sGoodsId = sGoodsId;
    }

    public String getSGoodsSpecificationsId() {
        return sGoodsSpecificationsId;
    }

    public void setSGoodsSpecificationsId(String sGoodsSpecificationsId) {
        this.sGoodsSpecificationsId = sGoodsSpecificationsId;
    }

    public String getSGoodsSpecificationsName() {
        return sGoodsSpecificationsName;
    }

    public void setSGoodsSpecificationsName(String sGoodsSpecificationsName) {
        this.sGoodsSpecificationsName = sGoodsSpecificationsName;
    }

    public String getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
