package com.example.medicalservice.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @description 字典项
 * @author: Lenovo
 * @date: 2024/5/20
 */
public class HardwareDictBean {


    @SerializedName("msg")
    private String msg;
    @SerializedName("code")
    private Integer code;
    @SerializedName("data")
    private List<DataDTO> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        @SerializedName("dictCode")
        private Integer dictCode;
        @SerializedName("dictType")
        private Integer dictType;
        @SerializedName("dictLabel")
        private String dictLabel;
        @SerializedName("dictValue")
        private String dictValue;
        @SerializedName("status")
        private String status;
        @SerializedName("default")
        private Boolean defaultX;

        public Integer getDictCode() {
            return dictCode;
        }

        public void setDictCode(Integer dictCode) {
            this.dictCode = dictCode;
        }

        public Integer getDictType() {
            return dictType;
        }

        public void setDictType(Integer dictType) {
            this.dictType = dictType;
        }

        public String getDictLabel() {
            return dictLabel;
        }

        public void setDictLabel(String dictLabel) {
            this.dictLabel = dictLabel;
        }

        public String getDictValue() {
            return dictValue;
        }

        public void setDictValue(String dictValue) {
            this.dictValue = dictValue;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Boolean getDefaultX() {
            return defaultX;
        }

        public void setDefaultX(Boolean defaultX) {
            this.defaultX = defaultX;
        }
    }
}
