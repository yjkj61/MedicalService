package com.example.medicalservice.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @description 规格和安装bean类
 * @author: Lenovo
 * @date: 2024/5/18
 */
public class SpecificationBean {

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
        @SerializedName("attributeParam")
        private String attributeParam;
        @SerializedName("attributeValueList")
        private List<String> attributeValueList;

        public String getAttributeParam() {
            return attributeParam;
        }

        public void setAttributeParam(String attributeParam) {
            this.attributeParam = attributeParam;
        }

        public List<String> getAttributeValueList() {
            return attributeValueList;
        }

        public void setAttributeValueList(List<String> attributeValueList) {
            this.attributeValueList = attributeValueList;
        }
    }
}
