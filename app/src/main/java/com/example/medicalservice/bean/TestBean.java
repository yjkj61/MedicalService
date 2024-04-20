package com.example.medicalservice.bean;

public class TestBean {

    //用户名/或者ID，随你吧
    private Long userId;

    private String time;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    //体温
    private String temp;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    //收缩压(高压)
    private String systolicPressure;
    //舒张压(高压)
    private String diastolicPressure;
    //脉搏
    private String pulse;

    public String getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(String systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public String getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(String diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    //总胆TC
    private String totalCholesterol;
    //甘油三酯
    private String glycerol;
    //高密度脂蛋白
    private String highDensityLipoprotein;
    //低密度脂蛋白
    private String lowDensityLipoprotein;


    public String getTotalCholesterol() {
        return totalCholesterol;
    }

    public void setTotalCholesterol(String totalCholesterol) {
        this.totalCholesterol = totalCholesterol;
    }

    public String getGlycerol() {
        return glycerol;
    }

    public void setGlycerol(String glycerol) {
        this.glycerol = glycerol;
    }

    public String getHighDensityLipoprotein() {
        return highDensityLipoprotein;
    }

    public void setHighDensityLipoprotein(String highDensityLipoprotein) {
        this.highDensityLipoprotein = highDensityLipoprotein;
    }

    public String getLowDensityLipoprotein() {
        return lowDensityLipoprotein;
    }

    public void setLowDensityLipoprotein(String lowDensityLipoprotein) {
        this.lowDensityLipoprotein = lowDensityLipoprotein;
    }

    //血糖
    private String bloodSugar;
    //胆固醇
    private String cholesterol;

    //尿酸
    private String uricAcid;

    public String getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(String bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public String getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(String cholesterol) {
        this.cholesterol = cholesterol;
    }

    public String getUricAcid() {
        return uricAcid;
    }

    public void setUricAcid(String uricAcid) {
        this.uricAcid = uricAcid;
    }

    //血氧
    private String bloodOxygen;

    public String getBloodOxygen() {
        return bloodOxygen;
    }

    public void setBloodOxygen(String bloodOxygen) {
        this.bloodOxygen = bloodOxygen;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
