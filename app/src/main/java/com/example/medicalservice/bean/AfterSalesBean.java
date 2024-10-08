package com.example.medicalservice.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @description 售后bean类
 * @author: Lenovo
 * @date: 2024/5/18
 */
public class AfterSalesBean {

    @SerializedName("msg")
    private String msg;
    @SerializedName("code")
    private Integer code;
    @SerializedName("data")
    private DataDTO data;

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

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        @SerializedName("createBy")
        private Object createBy;
        @SerializedName("createTime")
        private String createTime;
        @SerializedName("updateBy")
        private Object updateBy;
        @SerializedName("updateTime")
        private String updateTime;
        @SerializedName("remark")
        private Object remark;
        @SerializedName("goodsCollectStatus")
        private Integer goodsCollectStatus;
        @SerializedName("goodsDetailsImages")
        private String goodsDetailsImages;
        @SerializedName("sGoodsId")
        private Long sGoodsId;
        @SerializedName("sGoodsBusinessId")
        private Integer sGoodsBusinessId;
        @SerializedName("businessName")
        private Object businessName;
        @SerializedName("shopName")
        private Object shopName;
        @SerializedName("goodsName")
        private String goodsName;
        @SerializedName("goodsTitle")
        private String goodsTitle;
        @SerializedName("unit")
        private String unit;
        @SerializedName("sGoodsTypeId")
        private Integer sGoodsTypeId;
        @SerializedName("sGoodsTypeIdList")
        private List<Integer> sGoodsTypeIdList;
        @SerializedName("goodsTypeName")
        private Object goodsTypeName;
        @SerializedName("sBrandId")
        private Integer sBrandId;
        @SerializedName("brandName")
        private String brandName;
        @SerializedName("specificationsType")
        private String specificationsType;
        @SerializedName("goodsCoverImages")
        private String goodsCoverImages;
        @SerializedName("goodsVideo")
        private Object goodsVideo;
        @SerializedName("goodsImages")
        private String goodsImages;
        @SerializedName("goodsDetails")
        private Object goodsDetails;
        @SerializedName("salesVolume")
        private String salesVolume;
        @SerializedName("inventory")
        private Object inventory;
        @SerializedName("status")
        private String status;
        @SerializedName("isShow")
        private String isShow;
        @SerializedName("isShowTime")
        private String isShowTime;
        @SerializedName("offTime")
        private Object offTime;
        @SerializedName("rejectReason")
        private Object rejectReason;
        @SerializedName("prohibitionSalesReason")
        private Object prohibitionSalesReason;
        @SerializedName("attributeList")
        private List<AttributeListDTO> attributeList;
        @SerializedName("sGoodsSpecificationsMoreList")
        private Object sGoodsSpecificationsMoreList;
        @SerializedName("sGoodsSpecificationsList")
        private List<SGoodsSpecificationsListDTO> sGoodsSpecificationsList;
        @SerializedName("sGoodsLabelAssociationList")
        private Object sGoodsLabelAssociationList;
        @SerializedName("labelList")
        private Object labelList;
        @SerializedName("labelNameList")
        private Object labelNameList;
        @SerializedName("regularTimeOrder")
        private Integer regularTimeOrder;
        @SerializedName("activeIdList")
        private Object activeIdList;
        @SerializedName("activeId")
        private String activeId;
        @SerializedName("afterSalesGuarantee")
        private String afterSalesGuarantee;

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

        public Integer getGoodsCollectStatus() {
            return goodsCollectStatus;
        }

        public void setGoodsCollectStatus(Integer goodsCollectStatus) {
            this.goodsCollectStatus = goodsCollectStatus;
        }

        public String getGoodsDetailsImages() {
            return goodsDetailsImages;
        }

        public void setGoodsDetailsImages(String goodsDetailsImages) {
            this.goodsDetailsImages = goodsDetailsImages;
        }

        public Long getSGoodsId() {
            return sGoodsId;
        }

        public void setSGoodsId(Long sGoodsId) {
            this.sGoodsId = sGoodsId;
        }

        public Integer getSGoodsBusinessId() {
            return sGoodsBusinessId;
        }

        public void setSGoodsBusinessId(Integer sGoodsBusinessId) {
            this.sGoodsBusinessId = sGoodsBusinessId;
        }

        public Object getBusinessName() {
            return businessName;
        }

        public void setBusinessName(Object businessName) {
            this.businessName = businessName;
        }

        public Object getShopName() {
            return shopName;
        }

        public void setShopName(Object shopName) {
            this.shopName = shopName;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsTitle() {
            return goodsTitle;
        }

        public void setGoodsTitle(String goodsTitle) {
            this.goodsTitle = goodsTitle;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public Integer getSGoodsTypeId() {
            return sGoodsTypeId;
        }

        public void setSGoodsTypeId(Integer sGoodsTypeId) {
            this.sGoodsTypeId = sGoodsTypeId;
        }

        public List<Integer> getSGoodsTypeIdList() {
            return sGoodsTypeIdList;
        }

        public void setSGoodsTypeIdList(List<Integer> sGoodsTypeIdList) {
            this.sGoodsTypeIdList = sGoodsTypeIdList;
        }

        public Object getGoodsTypeName() {
            return goodsTypeName;
        }

        public void setGoodsTypeName(Object goodsTypeName) {
            this.goodsTypeName = goodsTypeName;
        }

        public Integer getSBrandId() {
            return sBrandId;
        }

        public void setSBrandId(Integer sBrandId) {
            this.sBrandId = sBrandId;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getSpecificationsType() {
            return specificationsType;
        }

        public void setSpecificationsType(String specificationsType) {
            this.specificationsType = specificationsType;
        }

        public String getGoodsCoverImages() {
            return goodsCoverImages;
        }

        public void setGoodsCoverImages(String goodsCoverImages) {
            this.goodsCoverImages = goodsCoverImages;
        }

        public Object getGoodsVideo() {
            return goodsVideo;
        }

        public void setGoodsVideo(Object goodsVideo) {
            this.goodsVideo = goodsVideo;
        }

        public String getGoodsImages() {
            return goodsImages;
        }

        public void setGoodsImages(String goodsImages) {
            this.goodsImages = goodsImages;
        }

        public Object getGoodsDetails() {
            return goodsDetails;
        }

        public void setGoodsDetails(Object goodsDetails) {
            this.goodsDetails = goodsDetails;
        }

        public String getSalesVolume() {
            return salesVolume;
        }

        public void setSalesVolume(String salesVolume) {
            this.salesVolume = salesVolume;
        }

        public Object getInventory() {
            return inventory;
        }

        public void setInventory(Object inventory) {
            this.inventory = inventory;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIsShow() {
            return isShow;
        }

        public void setIsShow(String isShow) {
            this.isShow = isShow;
        }

        public String getIsShowTime() {
            return isShowTime;
        }

        public void setIsShowTime(String isShowTime) {
            this.isShowTime = isShowTime;
        }

        public Object getOffTime() {
            return offTime;
        }

        public void setOffTime(Object offTime) {
            this.offTime = offTime;
        }

        public Object getRejectReason() {
            return rejectReason;
        }

        public void setRejectReason(Object rejectReason) {
            this.rejectReason = rejectReason;
        }

        public Object getProhibitionSalesReason() {
            return prohibitionSalesReason;
        }

        public void setProhibitionSalesReason(Object prohibitionSalesReason) {
            this.prohibitionSalesReason = prohibitionSalesReason;
        }

        public List<AttributeListDTO> getAttributeList() {
            return attributeList;
        }

        public void setAttributeList(List<AttributeListDTO> attributeList) {
            this.attributeList = attributeList;
        }

        public Object getSGoodsSpecificationsMoreList() {
            return sGoodsSpecificationsMoreList;
        }

        public void setSGoodsSpecificationsMoreList(Object sGoodsSpecificationsMoreList) {
            this.sGoodsSpecificationsMoreList = sGoodsSpecificationsMoreList;
        }

        public List<SGoodsSpecificationsListDTO> getSGoodsSpecificationsList() {
            return sGoodsSpecificationsList;
        }

        public void setSGoodsSpecificationsList(List<SGoodsSpecificationsListDTO> sGoodsSpecificationsList) {
            this.sGoodsSpecificationsList = sGoodsSpecificationsList;
        }

        public Object getSGoodsLabelAssociationList() {
            return sGoodsLabelAssociationList;
        }

        public void setSGoodsLabelAssociationList(Object sGoodsLabelAssociationList) {
            this.sGoodsLabelAssociationList = sGoodsLabelAssociationList;
        }

        public Object getLabelList() {
            return labelList;
        }

        public void setLabelList(Object labelList) {
            this.labelList = labelList;
        }

        public Object getLabelNameList() {
            return labelNameList;
        }

        public void setLabelNameList(Object labelNameList) {
            this.labelNameList = labelNameList;
        }

        public Integer getRegularTimeOrder() {
            return regularTimeOrder;
        }

        public void setRegularTimeOrder(Integer regularTimeOrder) {
            this.regularTimeOrder = regularTimeOrder;
        }

        public Object getActiveIdList() {
            return activeIdList;
        }

        public void setActiveIdList(Object activeIdList) {
            this.activeIdList = activeIdList;
        }

        public String getActiveId() {
            return activeId;
        }

        public void setActiveId(String activeId) {
            this.activeId = activeId;
        }

        public String getAfterSalesGuarantee() {
            return afterSalesGuarantee;
        }

        public void setAfterSalesGuarantee(String afterSalesGuarantee) {
            this.afterSalesGuarantee = afterSalesGuarantee;
        }

        public static class AttributeListDTO {
            @SerializedName("createBy")
            private Object createBy;
            @SerializedName("createTime")
            private Object createTime;
            @SerializedName("updateBy")
            private Object updateBy;
            @SerializedName("updateTime")
            private Object updateTime;
            @SerializedName("remark")
            private Object remark;
            @SerializedName("sGoodsAttributeTypeId")
            private Object sGoodsAttributeTypeId;
            @SerializedName("sGoodsAttributeId")
            private Integer sGoodsAttributeId;
            @SerializedName("sGoodsTypeId")
            private Integer sGoodsTypeId;
            @SerializedName("attributeName")
            private String attributeName;
            @SerializedName("attributeType")
            private Integer attributeType;
            @SerializedName("attributeValue")
            private String attributeValue;
            @SerializedName("optionValue")
            private String optionValue;
            @SerializedName("isEnable")
            private Integer isEnable;
            @SerializedName("sGoodsAttributeAssociationId")
            private Object sGoodsAttributeAssociationId;
            @SerializedName("sGoodsAttributeOptionList")
            private Object sGoodsAttributeOptionList;

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

            public Object getSGoodsAttributeTypeId() {
                return sGoodsAttributeTypeId;
            }

            public void setSGoodsAttributeTypeId(Object sGoodsAttributeTypeId) {
                this.sGoodsAttributeTypeId = sGoodsAttributeTypeId;
            }

            public Integer getSGoodsAttributeId() {
                return sGoodsAttributeId;
            }

            public void setSGoodsAttributeId(Integer sGoodsAttributeId) {
                this.sGoodsAttributeId = sGoodsAttributeId;
            }

            public Integer getSGoodsTypeId() {
                return sGoodsTypeId;
            }

            public void setSGoodsTypeId(Integer sGoodsTypeId) {
                this.sGoodsTypeId = sGoodsTypeId;
            }

            public String getAttributeName() {
                return attributeName;
            }

            public void setAttributeName(String attributeName) {
                this.attributeName = attributeName;
            }

            public Integer getAttributeType() {
                return attributeType;
            }

            public void setAttributeType(Integer attributeType) {
                this.attributeType = attributeType;
            }

            public String getAttributeValue() {
                return attributeValue;
            }

            public void setAttributeValue(String attributeValue) {
                this.attributeValue = attributeValue;
            }

            public String getOptionValue() {
                return optionValue;
            }

            public void setOptionValue(String optionValue) {
                this.optionValue = optionValue;
            }

            public Integer getIsEnable() {
                return isEnable;
            }

            public void setIsEnable(Integer isEnable) {
                this.isEnable = isEnable;
            }

            public Object getSGoodsAttributeAssociationId() {
                return sGoodsAttributeAssociationId;
            }

            public void setSGoodsAttributeAssociationId(Object sGoodsAttributeAssociationId) {
                this.sGoodsAttributeAssociationId = sGoodsAttributeAssociationId;
            }

            public Object getSGoodsAttributeOptionList() {
                return sGoodsAttributeOptionList;
            }

            public void setSGoodsAttributeOptionList(Object sGoodsAttributeOptionList) {
                this.sGoodsAttributeOptionList = sGoodsAttributeOptionList;
            }
        }

        public static class SGoodsSpecificationsListDTO {
            @SerializedName("createBy")
            private Object createBy;
            @SerializedName("createTime")
            private Object createTime;
            @SerializedName("updateBy")
            private Object updateBy;
            @SerializedName("updateTime")
            private Object updateTime;
            @SerializedName("remark")
            private Object remark;
            @SerializedName("sGoodsSpecificationsId")
            private Integer sGoodsSpecificationsId;
            @SerializedName("sGoodsId")
            private Long sGoodsId;
            @SerializedName("sGoodsBusinessId")
            private Integer sGoodsBusinessId;
            @SerializedName("specificationsNameOne")
            private Object specificationsNameOne;
            @SerializedName("specificationsNameTwo")
            private Object specificationsNameTwo;
            @SerializedName("specificationsNameThree")
            private Object specificationsNameThree;
            @SerializedName("specificationsValueOne")
            private Object specificationsValueOne;
            @SerializedName("specificationsValueTwo")
            private Object specificationsValueTwo;
            @SerializedName("specificationsValueThree")
            private Object specificationsValueThree;
            @SerializedName("name")
            private String name;
            @SerializedName("specificationsImages")
            private String specificationsImages;
            @SerializedName("price")
            private String price;
            @SerializedName("inventory")
            private Integer inventory;
            @SerializedName("weight")
            private String weight;
            @SerializedName("volume")
            private String volume;

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

            public Integer getSGoodsSpecificationsId() {
                return sGoodsSpecificationsId;
            }

            public void setSGoodsSpecificationsId(Integer sGoodsSpecificationsId) {
                this.sGoodsSpecificationsId = sGoodsSpecificationsId;
            }

            public Long getSGoodsId() {
                return sGoodsId;
            }

            public void setSGoodsId(Long sGoodsId) {
                this.sGoodsId = sGoodsId;
            }

            public Integer getSGoodsBusinessId() {
                return sGoodsBusinessId;
            }

            public void setSGoodsBusinessId(Integer sGoodsBusinessId) {
                this.sGoodsBusinessId = sGoodsBusinessId;
            }

            public Object getSpecificationsNameOne() {
                return specificationsNameOne;
            }

            public void setSpecificationsNameOne(Object specificationsNameOne) {
                this.specificationsNameOne = specificationsNameOne;
            }

            public Object getSpecificationsNameTwo() {
                return specificationsNameTwo;
            }

            public void setSpecificationsNameTwo(Object specificationsNameTwo) {
                this.specificationsNameTwo = specificationsNameTwo;
            }

            public Object getSpecificationsNameThree() {
                return specificationsNameThree;
            }

            public void setSpecificationsNameThree(Object specificationsNameThree) {
                this.specificationsNameThree = specificationsNameThree;
            }

            public Object getSpecificationsValueOne() {
                return specificationsValueOne;
            }

            public void setSpecificationsValueOne(Object specificationsValueOne) {
                this.specificationsValueOne = specificationsValueOne;
            }

            public Object getSpecificationsValueTwo() {
                return specificationsValueTwo;
            }

            public void setSpecificationsValueTwo(Object specificationsValueTwo) {
                this.specificationsValueTwo = specificationsValueTwo;
            }

            public Object getSpecificationsValueThree() {
                return specificationsValueThree;
            }

            public void setSpecificationsValueThree(Object specificationsValueThree) {
                this.specificationsValueThree = specificationsValueThree;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSpecificationsImages() {
                return specificationsImages;
            }

            public void setSpecificationsImages(String specificationsImages) {
                this.specificationsImages = specificationsImages;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public Integer getInventory() {
                return inventory;
            }

            public void setInventory(Integer inventory) {
                this.inventory = inventory;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }

            public String getVolume() {
                return volume;
            }

            public void setVolume(String volume) {
                this.volume = volume;
            }
        }
    }
}
