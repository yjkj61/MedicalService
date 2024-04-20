package com.example.medicalservice.bean;

import java.util.List;

public class CanteenBean {


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
        private int rFoodCanteenId;
        private String rFoodCanteenName;
        private String rFoodCanteenAddress;
        private String rFoodCanteenLatitudeLongitude;
        private String rFoodCanteenTime;
        private String rFoodCanteenContacts;
        private String rFoodCanteenPhone;
        private String rFoodCanteenRemark;
        private String userId;
        private String userType;
        private int markerId;
        private String markerName;

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

        public int getRFoodCanteenId() {
            return rFoodCanteenId;
        }

        public void setRFoodCanteenId(int rFoodCanteenId) {
            this.rFoodCanteenId = rFoodCanteenId;
        }

        public String getRFoodCanteenName() {
            return rFoodCanteenName;
        }

        public void setRFoodCanteenName(String rFoodCanteenName) {
            this.rFoodCanteenName = rFoodCanteenName;
        }

        public String getRFoodCanteenAddress() {
            return rFoodCanteenAddress;
        }

        public void setRFoodCanteenAddress(String rFoodCanteenAddress) {
            this.rFoodCanteenAddress = rFoodCanteenAddress;
        }

        public String getRFoodCanteenLatitudeLongitude() {
            return rFoodCanteenLatitudeLongitude;
        }

        public void setRFoodCanteenLatitudeLongitude(String rFoodCanteenLatitudeLongitude) {
            this.rFoodCanteenLatitudeLongitude = rFoodCanteenLatitudeLongitude;
        }

        public String getRFoodCanteenTime() {
            return rFoodCanteenTime;
        }

        public void setRFoodCanteenTime(String rFoodCanteenTime) {
            this.rFoodCanteenTime = rFoodCanteenTime;
        }

        public String getRFoodCanteenContacts() {
            return rFoodCanteenContacts;
        }

        public void setRFoodCanteenContacts(String rFoodCanteenContacts) {
            this.rFoodCanteenContacts = rFoodCanteenContacts;
        }

        public String getRFoodCanteenPhone() {
            return rFoodCanteenPhone;
        }

        public void setRFoodCanteenPhone(String rFoodCanteenPhone) {
            this.rFoodCanteenPhone = rFoodCanteenPhone;
        }

        public String getRFoodCanteenRemark() {
            return rFoodCanteenRemark;
        }

        public void setRFoodCanteenRemark(String rFoodCanteenRemark) {
            this.rFoodCanteenRemark = rFoodCanteenRemark;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public int getMarkerId() {
            return markerId;
        }

        public void setMarkerId(int markerId) {
            this.markerId = markerId;
        }

        public String getMarkerName() {
            return markerName;
        }

        public void setMarkerName(String markerName) {
            this.markerName = markerName;
        }
    }
}
