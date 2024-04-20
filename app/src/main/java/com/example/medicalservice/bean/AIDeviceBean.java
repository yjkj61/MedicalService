package com.example.medicalservice.bean;

import java.util.List;

public class AIDeviceBean {


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
        private int hHardwareId;
        private int hUserId;
        private String hHardwareSn;
        private String hHardwarePic;
        private String hHardwareType;
        private String hHardwareName;
        private String hHardwareTopic;
        private String hHardwareDetail;

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

        public int getHHardwareId() {
            return hHardwareId;
        }

        public void setHHardwareId(int hHardwareId) {
            this.hHardwareId = hHardwareId;
        }

        public int getHUserId() {
            return hUserId;
        }

        public void setHUserId(int hUserId) {
            this.hUserId = hUserId;
        }

        public String getHHardwareSn() {
            return hHardwareSn;
        }

        public void setHHardwareSn(String hHardwareSn) {
            this.hHardwareSn = hHardwareSn;
        }

        public String getHHardwarePic() {
            return hHardwarePic;
        }

        public void setHHardwarePic(String hHardwarePic) {
            this.hHardwarePic = hHardwarePic;
        }

        public String getHHardwareType() {
            return hHardwareType;
        }

        public void setHHardwareType(String hHardwareType) {
            this.hHardwareType = hHardwareType;
        }

        public String getHHardwareName() {
            return hHardwareName;
        }

        public void setHHardwareName(String hHardwareName) {
            this.hHardwareName = hHardwareName;
        }

        public String getHHardwareTopic() {
            return hHardwareTopic;
        }

        public void setHHardwareTopic(String hHardwareTopic) {
            this.hHardwareTopic = hHardwareTopic;
        }

        public String getHHardwareDetail() {
            return hHardwareDetail;
        }

        public void setHHardwareDetail(String hHardwareDetail) {
            this.hHardwareDetail = hHardwareDetail;
        }
    }
}
