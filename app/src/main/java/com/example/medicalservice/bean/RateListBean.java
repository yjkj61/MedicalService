package com.example.medicalservice.bean;

import java.util.List;

public class RateListBean {

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
        private int hHeartRateId;
        private int ownerId;
        private String ownerName;
        private String ownerSex;
        private String ownerAge;
        private String ownerBedNum;
        private String hHeartRateValue;
        private String hHeartRateResultAnalysis;
        private String hHeartRateTime;
        private String hReferenceOpinions;
        private String hHeartRateDataResource;

        public int getHHeartRateId() {
            return hHeartRateId;
        }

        public void setHHeartRateId(int hHeartRateId) {
            this.hHeartRateId = hHeartRateId;
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

        public String getHHeartRateValue() {
            return hHeartRateValue;
        }

        public void setHHeartRateValue(String hHeartRateValue) {
            this.hHeartRateValue = hHeartRateValue;
        }

        public String getHHeartRateResultAnalysis() {
            return hHeartRateResultAnalysis;
        }

        public void setHHeartRateResultAnalysis(String hHeartRateResultAnalysis) {
            this.hHeartRateResultAnalysis = hHeartRateResultAnalysis;
        }

        public String getHHeartRateTime() {
            return hHeartRateTime;
        }

        public void setHHeartRateTime(String hHeartRateTime) {
            this.hHeartRateTime = hHeartRateTime;
        }

        public String getHReferenceOpinions() {
            return hReferenceOpinions;
        }

        public void setHReferenceOpinions(String hReferenceOpinions) {
            this.hReferenceOpinions = hReferenceOpinions;
        }

        public String getHHeartRateDataResource() {
            return hHeartRateDataResource;
        }

        public void setHHeartRateDataResource(String hHeartRateDataResource) {
            this.hHeartRateDataResource = hHeartRateDataResource;
        }
    }
}
