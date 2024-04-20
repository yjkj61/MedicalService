package com.example.medicalservice.bean;

import java.util.List;

public class BloodPressureListBean {


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
        private int hBloodPressureId;
        private String userId;
        private int ownerId;
        private String ownerName;
        private String ownerSex;
        private String ownerAge;
        private String ownerBedNum;
        private String hHypertension;
        private String hHypertensionAnalysis;
        private String hHypotension;
        private String hHypotensionAnalysis;
        private String hPulseRate;
        private String hPulseRateAnalysis;
        private String hReferenceOpinions;
        private String hBloodPressureTime;
        private String hBllodPressureDataResource;

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

        public int getHBloodPressureId() {
            return hBloodPressureId;
        }

        public void setHBloodPressureId(int hBloodPressureId) {
            this.hBloodPressureId = hBloodPressureId;
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

        public String getOwnerBedNum() {
            return ownerBedNum;
        }

        public void setOwnerBedNum(String ownerBedNum) {
            this.ownerBedNum = ownerBedNum;
        }

        public String getHHypertension() {
            return hHypertension;
        }

        public void setHHypertension(String hHypertension) {
            this.hHypertension = hHypertension;
        }

        public String getHHypertensionAnalysis() {
            return hHypertensionAnalysis;
        }

        public void setHHypertensionAnalysis(String hHypertensionAnalysis) {
            this.hHypertensionAnalysis = hHypertensionAnalysis;
        }

        public String getHHypotension() {
            return hHypotension;
        }

        public void setHHypotension(String hHypotension) {
            this.hHypotension = hHypotension;
        }

        public String getHHypotensionAnalysis() {
            return hHypotensionAnalysis;
        }

        public void setHHypotensionAnalysis(String hHypotensionAnalysis) {
            this.hHypotensionAnalysis = hHypotensionAnalysis;
        }

        public String getHPulseRate() {
            return hPulseRate;
        }

        public void setHPulseRate(String hPulseRate) {
            this.hPulseRate = hPulseRate;
        }

        public String getHPulseRateAnalysis() {
            return hPulseRateAnalysis;
        }

        public void setHPulseRateAnalysis(String hPulseRateAnalysis) {
            this.hPulseRateAnalysis = hPulseRateAnalysis;
        }

        public String getHReferenceOpinions() {
            return hReferenceOpinions;
        }

        public void setHReferenceOpinions(String hReferenceOpinions) {
            this.hReferenceOpinions = hReferenceOpinions;
        }

        public String getHBloodPressureTime() {
            return hBloodPressureTime;
        }

        public void setHBloodPressureTime(String hBloodPressureTime) {
            this.hBloodPressureTime = hBloodPressureTime;
        }

        public String getHBllodPressureDataResource() {
            return hBllodPressureDataResource;
        }

        public void setHBllodPressureDataResource(String hBllodPressureDataResource) {
            this.hBllodPressureDataResource = hBllodPressureDataResource;
        }
    }
}
