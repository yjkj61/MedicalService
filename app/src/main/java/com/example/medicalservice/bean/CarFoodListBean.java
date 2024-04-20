package com.example.medicalservice.bean;

import java.io.Serializable;
import java.util.List;

public class CarFoodListBean implements Serializable {

   private int id;

    private double price;

    private double singlePrice;

    private String imageUrl;

    private String name;

    private int number;

    private String rFoodCanteenId;

    private double rFoodPackingCharge;

    public double getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(double singlePrice) {
        this.singlePrice = singlePrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getrFoodCanteenId() {
        return rFoodCanteenId;
    }

    public void setrFoodCanteenId(String rFoodCanteenId) {
        this.rFoodCanteenId = rFoodCanteenId;
    }

    public double getrFoodPackingCharge() {
        return rFoodPackingCharge;
    }

    public void setrFoodPackingCharge(double rFoodPackingCharge) {
        this.rFoodPackingCharge = rFoodPackingCharge;
    }
}
