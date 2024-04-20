package com.example.medicalservice.bean;

public class LastPhysicalExamination {


    private String msg;
    private int code;
    private DataDTO data;

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

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        private String createBy;
        private String createTime;
        private String updateBy;
        private String updateTime;
        private String remark;
        private String id;
        private String hHypertension;
        private String hHypotension;
        private String hBloodOxygenSaturation;
        private String hBloodSugarValue;
        private String hTemperatureValue;
        private String hPulseRate;
        private String hUricAcidValue;
        private String hCholesterolContent;
        private String hTcValue;
        private String hTriglycerideValue;
        private String hHdlValue;
        private String hLdlValue;

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHHypertension() {
            return hHypertension;
        }

        public void setHHypertension(String hHypertension) {
            this.hHypertension = hHypertension;
        }

        public String getHHypotension() {
            return hHypotension;
        }

        public void setHHypotension(String hHypotension) {
            this.hHypotension = hHypotension;
        }

        public String getHBloodOxygenSaturation() {
            return hBloodOxygenSaturation;
        }

        public void setHBloodOxygenSaturation(String hBloodOxygenSaturation) {
            this.hBloodOxygenSaturation = hBloodOxygenSaturation;
        }

        public String getHBloodSugarValue() {
            return hBloodSugarValue;
        }

        public void setHBloodSugarValue(String hBloodSugarValue) {
            this.hBloodSugarValue = hBloodSugarValue;
        }

        public String getHTemperatureValue() {
            return hTemperatureValue;
        }

        public void setHTemperatureValue(String hTemperatureValue) {
            this.hTemperatureValue = hTemperatureValue;
        }

        public String getHPulseRate() {
            return hPulseRate;
        }

        public void setHPulseRate(String hPulseRate) {
            this.hPulseRate = hPulseRate;
        }

        public String getHUricAcidValue() {
            return hUricAcidValue;
        }

        public void setHUricAcidValue(String hUricAcidValue) {
            this.hUricAcidValue = hUricAcidValue;
        }

        public String getHCholesterolContent() {
            return hCholesterolContent;
        }

        public void setHCholesterolContent(String hCholesterolContent) {
            this.hCholesterolContent = hCholesterolContent;
        }

        public String getHTcValue() {
            return hTcValue;
        }

        public void setHTcValue(String hTcValue) {
            this.hTcValue = hTcValue;
        }

        public String getHTriglycerideValue() {
            return hTriglycerideValue;
        }

        public void setHTriglycerideValue(String hTriglycerideValue) {
            this.hTriglycerideValue = hTriglycerideValue;
        }

        public String getHHdlValue() {
            return hHdlValue;
        }

        public void setHHdlValue(String hHdlValue) {
            this.hHdlValue = hHdlValue;
        }

        public String getHLdlValue() {
            return hLdlValue;
        }

        public void setHLdlValue(String hLdlValue) {
            this.hLdlValue = hLdlValue;
        }
    }
}
