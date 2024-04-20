package com.example.medicalservice.bean;

import java.util.List;

public class ShopTypeBean {


    private String msg;
    private int code;
    private List<DataDTO> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        private String createBy;
        private String createTime;
        private String updateBy;
        private String updateTime;
        private String remark;
        private int sPageConfigurationId;
        private int sGoodsTypeId;
        private String goodsTypeName;
        private String sGoodsTypeIdSecondary;
        private String goodsTypeNameSecondary;
        private List<String> goodsTypeNameSecondaryList;
        private int sortNumber;

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

        public int getSPageConfigurationId() {
            return sPageConfigurationId;
        }

        public void setSPageConfigurationId(int sPageConfigurationId) {
            this.sPageConfigurationId = sPageConfigurationId;
        }

        public int getSGoodsTypeId() {
            return sGoodsTypeId;
        }

        public void setSGoodsTypeId(int sGoodsTypeId) {
            this.sGoodsTypeId = sGoodsTypeId;
        }

        public String getGoodsTypeName() {
            return goodsTypeName;
        }

        public void setGoodsTypeName(String goodsTypeName) {
            this.goodsTypeName = goodsTypeName;
        }

        public String getSGoodsTypeIdSecondary() {
            return sGoodsTypeIdSecondary;
        }

        public void setSGoodsTypeIdSecondary(String sGoodsTypeIdSecondary) {
            this.sGoodsTypeIdSecondary = sGoodsTypeIdSecondary;
        }

        public String getGoodsTypeNameSecondary() {
            return goodsTypeNameSecondary;
        }

        public void setGoodsTypeNameSecondary(String goodsTypeNameSecondary) {
            this.goodsTypeNameSecondary = goodsTypeNameSecondary;
        }

        public List<String> getGoodsTypeNameSecondaryList() {
            return goodsTypeNameSecondaryList;
        }

        public void setGoodsTypeNameSecondaryList(List<String> goodsTypeNameSecondaryList) {
            this.goodsTypeNameSecondaryList = goodsTypeNameSecondaryList;
        }

        public int getSortNumber() {
            return sortNumber;
        }

        public void setSortNumber(int sortNumber) {
            this.sortNumber = sortNumber;
        }
    }
}
