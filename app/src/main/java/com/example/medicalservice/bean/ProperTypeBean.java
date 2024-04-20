package com.example.medicalservice.bean;

public class ProperTypeBean {


    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;
    private String remark;
    private int id;
    private int fTypeId;
    private String fTypeName;
    private String fTypeIntroduction;
    private int sTypeId;
    private String sTypeName;

    private String sTypeLogo;
    private int ImageSource;
    private int background;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFTypeId() {
        return fTypeId;
    }

    public void setFTypeId(int fTypeId) {
        this.fTypeId = fTypeId;
    }

    public String getFTypeName() {
        return fTypeName;
    }

    public void setFTypeName(String fTypeName) {
        this.fTypeName = fTypeName;
    }

    public String getFTypeIntroduction() {
        return fTypeIntroduction;
    }

    public void setFTypeIntroduction(String fTypeIntroduction) {
        this.fTypeIntroduction = fTypeIntroduction;
    }

    public int getSTypeId() {
        return sTypeId;
    }

    public void setSTypeId(int sTypeId) {
        this.sTypeId = sTypeId;
    }

    public String getSTypeName() {
        return sTypeName;
    }

    public void setSTypeName(String sTypeName) {
        this.sTypeName = sTypeName;
    }

    public int getImageSource() {
        return ImageSource;
    }

    public void setImageSource(int imageSource) {
        ImageSource = imageSource;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public String getsTypeLogo() {
        return sTypeLogo;
    }

    public void setsTypeLogo(String sTypeLogo) {
        this.sTypeLogo = sTypeLogo;
    }
}
