package com.example.medicalservice.bean;

import java.util.List;

public class HealthReportBean {


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
        private int hReportId;
        private int hUserId;
        private String hReportName;
        private String hReportContent;
        private String hReportCreateTime;

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

        public int getHReportId() {
            return hReportId;
        }

        public void setHReportId(int hReportId) {
            this.hReportId = hReportId;
        }

        public int getHUserId() {
            return hUserId;
        }

        public void setHUserId(int hUserId) {
            this.hUserId = hUserId;
        }

        public String getHReportName() {
            return hReportName;
        }

        public void setHReportName(String hReportName) {
            this.hReportName = hReportName;
        }

        public String getHReportContent() {
            return hReportContent;
        }

        public void setHReportContent(String hReportContent) {
            this.hReportContent = hReportContent;
        }

        public String getHReportCreateTime() {
            return hReportCreateTime;
        }

        public void setHReportCreateTime(String hReportCreateTime) {
            this.hReportCreateTime = hReportCreateTime;
        }
    }
}
