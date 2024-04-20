package com.example.medicalservice.bean;

import java.util.List;

public class CholesterolListBean {


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
        private int hCholesterolId;
        private int ownerId;
        private String ownerName;
        private String ownerSex;
        private String ownerAge;
        private String ownerBedNum;
        private String hCholesterolContent;
        private String hCholesterolResultAnalysis;
        private String hCholesterolTime;
        private String hReferenceOpinions;
        private String hCholesterolDataResource;

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

        public int getHCholesterolId() {
            return hCholesterolId;
        }

        public void setHCholesterolId(int hCholesterolId) {
            this.hCholesterolId = hCholesterolId;
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

        public String getHCholesterolContent() {
            return hCholesterolContent;
        }

        public void setHCholesterolContent(String hCholesterolContent) {
            this.hCholesterolContent = hCholesterolContent;
        }

        public String getHCholesterolResultAnalysis() {
            return hCholesterolResultAnalysis;
        }

        public void setHCholesterolResultAnalysis(String hCholesterolResultAnalysis) {
            this.hCholesterolResultAnalysis = hCholesterolResultAnalysis;
        }

        public String getHCholesterolTime() {
            return hCholesterolTime;
        }

        public void setHCholesterolTime(String hCholesterolTime) {
            this.hCholesterolTime = hCholesterolTime;
        }

        public String getHReferenceOpinions() {
            return hReferenceOpinions;
        }

        public void setHReferenceOpinions(String hReferenceOpinions) {
            this.hReferenceOpinions = hReferenceOpinions;
        }

        public String getHCholesterolDataResource() {
            return hCholesterolDataResource;
        }

        public void setHCholesterolDataResource(String hCholesterolDataResource) {
            this.hCholesterolDataResource = hCholesterolDataResource;
        }
    }
}
