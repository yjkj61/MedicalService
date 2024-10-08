package com.example.medicalservice.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @description 睡眠数据
 * @author: Lenovo
 * @date: 2024/5/22
 */
public class SleepInfoBean {

    @SerializedName("msg")
    private String msg;
    @SerializedName("code")
    private Integer code;
    @SerializedName("data")
    private DataDTO data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        @SerializedName("todaySleep")
        private String todaySleep;
        @SerializedName("weekSleep")
        private String weekSleep;

        public String getTodaySleep() {
            return todaySleep;
        }

        public void setTodaySleep(String todaySleep) {
            this.todaySleep = todaySleep;
        }

        public String getWeekSleep() {
            return weekSleep;
        }

        public void setWeekSleep(String weekSleep) {
            this.weekSleep = weekSleep;
        }
    }
}
