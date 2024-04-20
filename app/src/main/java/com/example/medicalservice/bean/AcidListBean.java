package com.example.medicalservice.bean;

import java.util.List;

public class AcidListBean {


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
        private String hUricAcidId;
        private int ownerId;
        private String ownerName;
        private String ownerSex;
        private String ownerAge;
        private String ownerBedNum;
        private String hUricAcidValue;
        private String hUricAcidResultAnalysis;
        private String hUricAcidTime;
        private String hReferenceOpinions;
        private String hUricAcidDataResource;

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

        public String getHUricAcidId() {
            return hUricAcidId;
        }

        public void setHUricAcidId(String hUricAcidId) {
            this.hUricAcidId = hUricAcidId;
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

        public String getHUricAcidValue() {
            return hUricAcidValue;
        }

        public void setHUricAcidValue(String hUricAcidValue) {
            this.hUricAcidValue = hUricAcidValue;
        }

        public String getHUricAcidResultAnalysis() {
            return hUricAcidResultAnalysis;
        }

        public void setHUricAcidResultAnalysis(String hUricAcidResultAnalysis) {
            this.hUricAcidResultAnalysis = hUricAcidResultAnalysis;
        }

        public String getHUricAcidTime() {
            return hUricAcidTime;
        }

        public void setHUricAcidTime(String hUricAcidTime) {
            this.hUricAcidTime = hUricAcidTime;
        }

        public String getHReferenceOpinions() {
            return hReferenceOpinions;
        }

        public void setHReferenceOpinions(String hReferenceOpinions) {
            this.hReferenceOpinions = hReferenceOpinions;
        }

        public String getHUricAcidDataResource() {
            return hUricAcidDataResource;
        }

        public void setHUricAcidDataResource(String hUricAcidDataResource) {
            this.hUricAcidDataResource = hUricAcidDataResource;
        }
    }
}
