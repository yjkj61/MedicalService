package com.example.medicalservice.bean;

import java.util.List;

public class BloodFatBean {


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
        private int hTgChohdlldlId;
        private int userId;
        private int ownerId;
        private String ownerName;
        private String ownerSex;
        private String ownerAge;
        private String ownerBedNum;
        private String hTcValue;
        private String hTcResultAnalysis;
        private String hTriglycerideValue;
        private String hTriglycerideResultAnalysis;
        private String hHdlValue;
        private String hHdlResultAnalysis;
        private String hLdlValue;
        private String hLdlResultAnalysis;
        private String hSumOpinions;
        private String hTgChohdlldlTime;
        private String hTgChohdlldlDataResource;

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

        public int getHTgChohdlldlId() {
            return hTgChohdlldlId;
        }

        public void setHTgChohdlldlId(int hTgChohdlldlId) {
            this.hTgChohdlldlId = hTgChohdlldlId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
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

        public String getHTcValue() {
            return hTcValue;
        }

        public void setHTcValue(String hTcValue) {
            this.hTcValue = hTcValue;
        }

        public String getHTcResultAnalysis() {
            return hTcResultAnalysis;
        }

        public void setHTcResultAnalysis(String hTcResultAnalysis) {
            this.hTcResultAnalysis = hTcResultAnalysis;
        }

        public String getHTriglycerideValue() {
            return hTriglycerideValue;
        }

        public void setHTriglycerideValue(String hTriglycerideValue) {
            this.hTriglycerideValue = hTriglycerideValue;
        }

        public String getHTriglycerideResultAnalysis() {
            return hTriglycerideResultAnalysis;
        }

        public void setHTriglycerideResultAnalysis(String hTriglycerideResultAnalysis) {
            this.hTriglycerideResultAnalysis = hTriglycerideResultAnalysis;
        }

        public String getHHdlValue() {
            return hHdlValue;
        }

        public void setHHdlValue(String hHdlValue) {
            this.hHdlValue = hHdlValue;
        }

        public String getHHdlResultAnalysis() {
            return hHdlResultAnalysis;
        }

        public void setHHdlResultAnalysis(String hHdlResultAnalysis) {
            this.hHdlResultAnalysis = hHdlResultAnalysis;
        }

        public String getHLdlValue() {
            return hLdlValue;
        }

        public void setHLdlValue(String hLdlValue) {
            this.hLdlValue = hLdlValue;
        }

        public String getHLdlResultAnalysis() {
            return hLdlResultAnalysis;
        }

        public void setHLdlResultAnalysis(String hLdlResultAnalysis) {
            this.hLdlResultAnalysis = hLdlResultAnalysis;
        }

        public String getHSumOpinions() {
            return hSumOpinions;
        }

        public void setHSumOpinions(String hSumOpinions) {
            this.hSumOpinions = hSumOpinions;
        }

        public String getHTgChohdlldlTime() {
            return hTgChohdlldlTime;
        }

        public void setHTgChohdlldlTime(String hTgChohdlldlTime) {
            this.hTgChohdlldlTime = hTgChohdlldlTime;
        }

        public String getHTgChohdlldlDataResource() {
            return hTgChohdlldlDataResource;
        }

        public void setHTgChohdlldlDataResource(String hTgChohdlldlDataResource) {
            this.hTgChohdlldlDataResource = hTgChohdlldlDataResource;
        }
    }
}
