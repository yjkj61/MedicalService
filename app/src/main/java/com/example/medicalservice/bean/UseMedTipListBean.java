package com.example.medicalservice.bean;

import java.util.List;

public class UseMedTipListBean {


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

    public static class DataDTO {
        private String createBy;
        private String createTime;
        private String updateBy;
        private String updateTime;
        private String remark;
        private String timeSlot;
        private List<HMedicinalRemindVo> hMedicinalRemindVoList;
        
        
        
       public static class HMedicinalRemindVo{

           private String createBy;
           private String createTime;
           private String updateBy;
           private String updateTime;
           private String remark;
           private String useMedicinalTime;
           private String medicinalName;
           private int medicinalNumber;
           private String medicinalUnit;

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

           public String getUseMedicinalTime() {
               return useMedicinalTime;
           }

           public void setUseMedicinalTime(String useMedicinalTime) {
               this.useMedicinalTime = useMedicinalTime;
           }

           public String getMedicinalName() {
               return medicinalName;
           }

           public void setMedicinalName(String medicinalName) {
               this.medicinalName = medicinalName;
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
       }

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

        public String getTimeSlot() {
            return timeSlot;
        }

        public void setTimeSlot(String timeSlot) {
            this.timeSlot = timeSlot;
        }

        public List<HMedicinalRemindVo> getHMedicinalRemindVoList() {
            return hMedicinalRemindVoList;
        }

        public void setHMedicinalRemindVoList(List<HMedicinalRemindVo> hMedicinalRemindVoList) {
            this.hMedicinalRemindVoList = hMedicinalRemindVoList;
        }
    }
}
