package com.example.medicalservice.bean;

import java.util.List;

public class AddressCatering {
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
        private Object createBy;
        private Object createTime;
        private Object updateBy;
        private Object updateTime;
        private Object remark;
        private int id;
        private Object ownerId;
        private String rFoodRecipientName;
        private String rFoodPhoneNumber;
        private String rFoodLocation;
        private String rFoodDetailedAddress;
        private int rFoodIsDefault;
        private Object userId;
        private String userType;

        public Object getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Object createBy) {
            this.createBy = createBy;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
            this.updateBy = updateBy;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(Object ownerId) {
            this.ownerId = ownerId;
        }

        public String getRFoodRecipientName() {
            return rFoodRecipientName;
        }

        public void setRFoodRecipientName(String rFoodRecipientName) {
            this.rFoodRecipientName = rFoodRecipientName;
        }

        public String getRFoodPhoneNumber() {
            return rFoodPhoneNumber;
        }

        public void setRFoodPhoneNumber(String rFoodPhoneNumber) {
            this.rFoodPhoneNumber = rFoodPhoneNumber;
        }

        public String getRFoodLocation() {
            return rFoodLocation;
        }

        public void setRFoodLocation(String rFoodLocation) {
            this.rFoodLocation = rFoodLocation;
        }

        public String getRFoodDetailedAddress() {
            return rFoodDetailedAddress;
        }

        public void setRFoodDetailedAddress(String rFoodDetailedAddress) {
            this.rFoodDetailedAddress = rFoodDetailedAddress;
        }

        public int getRFoodIsDefault() {
            return rFoodIsDefault;
        }

        public void setRFoodIsDefault(int rFoodIsDefault) {
            this.rFoodIsDefault = rFoodIsDefault;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }
    }
}
