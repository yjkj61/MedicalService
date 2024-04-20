package com.example.medicalservice.bean;

import java.io.Serializable;
import java.util.List;

public class UseMedHistoryBean implements Serializable{


    private String msg;
    private int code;
    private List<DataDTO> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public  class DataDTO implements Serializable {
        private Object createBy;
        private String createTime;
        private Object updateBy;
        private String updateTime;
        private Object remark;
        private long id;
        private int userId;
        private String img;
        private String medicinalName;
        private String medicinalType;
        private int medicinalNumber;
        private String medicinalUnit;
        private String status;
        private String remindType;
        private String remindTime;
        private String nextRemindTime;
        private List<String> useMedicinalTime;
        private String beginTime;

        public Object getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Object createBy) {
            this.createBy = createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getMedicinalName() {
            return medicinalName;
        }

        public void setMedicinalName(String medicinalName) {
            this.medicinalName = medicinalName;
        }

        public String getMedicinalType() {
            return medicinalType;
        }

        public void setMedicinalType(String medicinalType) {
            this.medicinalType = medicinalType;
        }

        public int getMedicinalNumber() {
            return medicinalNumber;
        }

        public void setMedicinalNumber(int medicinalNumber) {
            this.medicinalNumber = medicinalNumber;
        }

        public String getMedicinalUnit() {
            return medicinalUnit;
        }

        public void setMedicinalUnit(String medicinalUnit) {
            this.medicinalUnit = medicinalUnit;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRemindType() {
            return remindType;
        }

        public void setRemindType(String remindType) {
            this.remindType = remindType;
        }

        public String getRemindTime() {
            return remindTime;
        }

        public void setRemindTime(String remindTime) {
            this.remindTime = remindTime;
        }

        public String getNextRemindTime() {
            return nextRemindTime;
        }

        public void setNextRemindTime(String nextRemindTime) {
            this.nextRemindTime = nextRemindTime;
        }

        public List<String> getUseMedicinalTime() {
            return useMedicinalTime;
        }

        public void setUseMedicinalTime(List<String> useMedicinalTime) {
            this.useMedicinalTime = useMedicinalTime;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }
    }
}
