package com.example.medicalservice;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.room.Room;

import com.clj.fastble.BleManager;
import com.example.medicalservice.tools.MyMigration;
import com.tencent.bugly.crashreport.CrashReport;

public class MyApplication extends Application {

    private static MyApplication instance;

    private String user;

    public AppDatabase db;

    public String token;


    private String userName = "请登录";

    private String userHeader;

    private String userId;

    private String phoneNumber;

    private String ownerRemainMoney;

    private String markId = "";
    private String markName = "";
    private String rFoodCanteenId = "";

    private String rFoodCanteenName = "";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "table_name")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();


        BleManager.getInstance().init(this);

        BleManager.getInstance()
                .enableLog(true)
                .setReConnectCount(1, 5000)
                .setOperateTimeout(5000);

        BleManager.getInstance().isSupportBle();
        BleManager.getInstance().isBlueEnable();
        BleManager.getInstance().enableBluetooth();
        CrashReport.initCrashReport(this, "48c201e4ed", true, null);
    }

    public static MyApplication getInstance() {
        if (instance == null) {
            throw new NullPointerException("please inherit BaseApplication or call setApplication.");
        }

        return instance;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToken() {
        return token;
        //return "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyX2lkIjoxLCJ1c2VyX2tleSI6ImZmNDFjZGM0LWJiNzgtNDEyMy1iZGMwLThhM2EyZjhhODBmNCIsInVzZXJuYW1lIjoiYWRtaW4ifQ.mPl-QMmUGaZ3Nj3Ifi7MnVX9dIADoy4vmt6sRuYVkBEV8qnj7nwRlAIupdrzqsPMFKAKeMlzSRd29flLDai1OQ";
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHeader() {
        return userHeader;
    }

    public void setUserHeader(String userHeader) {
        this.userHeader = userHeader;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOwnerRemainMoney() {
        return ownerRemainMoney;
    }

    public void setOwnerRemainMoney(String ownerRemainMoney) {
        this.ownerRemainMoney = ownerRemainMoney;
    }

    public String getMarkId() {
        return markId;
    }

    public void setMarkId(String markId) {
        this.markId = markId;
    }

    public String getMarkName() {
        return markName;
    }

    public void setMarkName(String markName) {
        this.markName = markName;
    }

    public String getrFoodCanteenId() {
        return rFoodCanteenId;
    }

    public void setrFoodCanteenId(String rFoodCanteenId) {
        this.rFoodCanteenId = rFoodCanteenId;
    }

    public String getrFoodCanteenName() {
        return rFoodCanteenName;
    }

    public void setrFoodCanteenName(String rFoodCanteenName) {
        this.rFoodCanteenName = rFoodCanteenName;
    }
}
