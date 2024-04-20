package com.example.medicalservice.bean;

import java.util.List;

public class BloodSugarListBean {


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
        private int hBloodSugarId;
        private String userId;
        private int ownerId;
        private String ownerName;
        private String ownerSex;
        private String ownerAge;
        private String ownerRoomNum;
        private String hBloodSugarValue;
        private String hBloodSugarResultAnalysis;
        private String hBloodSugarCondition;
        private String hReferenceOpinions;
        private String hBloodSugarTime;
        private String hBloodSugarDataResource;

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

        public int getHBloodSugarId() {
            return hBloodSugarId;
        }

        public void setHBloodSugarId(int hBloodSugarId) {
            this.hBloodSugarId = hBloodSugarId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

        public String getOwnerRoomNum() {
            return ownerRoomNum;
        }

        public void setOwnerRoomNum(String ownerRoomNum) {
            this.ownerRoomNum = ownerRoomNum;
        }

        public String getHBloodSugarValue() {
            return hBloodSugarValue;
        }

        public void setHBloodSugarValue(String hBloodSugarValue) {
            this.hBloodSugarValue = hBloodSugarValue;
        }

        public String getHBloodSugarResultAnalysis() {
            return hBloodSugarResultAnalysis;
        }

        public void setHBloodSugarResultAnalysis(String hBloodSugarResultAnalysis) {
            this.hBloodSugarResultAnalysis = hBloodSugarResultAnalysis;
        }

        public String getHBloodSugarCondition() {
            return hBloodSugarCondition;
        }

        public void setHBloodSugarCondition(String hBloodSugarCondition) {
            this.hBloodSugarCondition = hBloodSugarCondition;
        }

        public String getHReferenceOpinions() {
            return hReferenceOpinions;
        }

        public void setHReferenceOpinions(String hReferenceOpinions) {
            this.hReferenceOpinions = hReferenceOpinions;
        }

        public String getHBloodSugarTime() {
            return hBloodSugarTime;
        }

        public void setHBloodSugarTime(String hBloodSugarTime) {
            this.hBloodSugarTime = hBloodSugarTime;
        }

        public String getHBloodSugarDataResource() {
            return hBloodSugarDataResource;
        }

        public void setHBloodSugarDataResource(String hBloodSugarDataResource) {
            this.hBloodSugarDataResource = hBloodSugarDataResource;
        }
    }
}
