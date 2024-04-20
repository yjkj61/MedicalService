package com.example.medicalservice.bean;

import java.util.List;

public class TemperatureListBean {

    private int total;
    private List<RowsDTO> rows;
    private int code;
    private String msg;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsDTO> getRows() {
        return rows;
    }

    public void setRows(List<RowsDTO> rows) {
        this.rows = rows;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class RowsDTO {
        private String createBy;
        private String createTime;
        private String updateBy;
        private String updateTime;
        private String remark;
        private int hTemperatureId;
        private int ownerId;
        private String ownerName;
        private String ownerSex;
        private String ownerAge;
        private String ownerBedNum;
        private String hTemperatureValue;
        private String hTemperatureResultAnalysis;
        private String hTemperatureRateTime;
        private String hReferenceOpinions;
        private String hTemperatureDataResource;

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

        public int getHTemperatureId() {
            return hTemperatureId;
        }

        public void setHTemperatureId(int hTemperatureId) {
            this.hTemperatureId = hTemperatureId;
        }

        public int getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(int ownerId) {
            this.ownerId = ownerId;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public String getOwnerSex() {
            return ownerSex;
        }

        public void setOwnerSex(String ownerSex) {
            this.ownerSex = ownerSex;
        }

        public String getOwnerAge() {
            return ownerAge;
        }

        public void setOwnerAge(String ownerAge) {
            this.ownerAge = ownerAge;
        }

        public String getOwnerBedNum() {
            return ownerBedNum;
        }

        public void setOwnerBedNum(String ownerBedNum) {
            this.ownerBedNum = ownerBedNum;
        }

        public String getHTemperatureValue() {
            return hTemperatureValue;
        }

        public void setHTemperatureValue(String hTemperatureValue) {
            this.hTemperatureValue = hTemperatureValue;
        }

        public String getHTemperatureResultAnalysis() {
            return hTemperatureResultAnalysis;
        }

        public void setHTemperatureResultAnalysis(String hTemperatureResultAnalysis) {
            this.hTemperatureResultAnalysis = hTemperatureResultAnalysis;
        }

        public String getHTemperatureRateTime() {
            return hTemperatureRateTime;
        }

        public void setHTemperatureRateTime(String hTemperatureRateTime) {
            this.hTemperatureRateTime = hTemperatureRateTime;
        }

        public String getHReferenceOpinions() {
            return hReferenceOpinions;
        }

        public void setHReferenceOpinions(String hReferenceOpinions) {
            this.hReferenceOpinions = hReferenceOpinions;
        }

        public String getHTemperatureDataResource() {
            return hTemperatureDataResource;
        }

        public void setHTemperatureDataResource(String hTemperatureDataResource) {
            this.hTemperatureDataResource = hTemperatureDataResource;
        }
    }
}
