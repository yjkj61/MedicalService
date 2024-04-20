package com.example.medicalservice.bean;

import java.io.Serializable;
import java.util.List;

public class GoodDetailBean {


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

    public static class DataDTO implements Serializable {
        private String createBy;
        private String createTime;
        private String updateBy;
        private String updateTime;
        private String remark;
        private long sGoodsId;
        private int sGoodsBusinessId;
        private String goodsName;
        private String goodsTitle;
        private String unit;
        private int sGoodsTypeId;
        private List<Integer> sGoodsTypeIdList;
        private String goodsTypeName;
        private int sBrandId;
        private String brandName;
        private String specificationsType;
        private String goodsCoverImages;
        private String goodsVideo;
        private String goodsImages;
        private String goodsDetails;
        private String salesVolume;
        private String inventory;
        private String status;
        private String isShow;
        private String isShowTime;
        private String rejectReason;
        private String prohibitionSalesReason;
        private List<AttributeListDTO> attributeList;
        private List<SGoodsSpecificationsMoreListDTO> sGoodsSpecificationsMoreList;
        private List<SGoodsSpecificationsListDTO> sGoodsSpecificationsList;
        private String sGoodsLabelAssociationList;
        private List<Integer> labelList;
        private String labelNameList;

        private int goodsCollectStatus;

        private String goodsDetailsImages;

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

        public long getSGoodsId() {
            return sGoodsId;
        }

        public void setSGoodsId(long sGoodsId) {
            this.sGoodsId = sGoodsId;
        }

        public int getSGoodsBusinessId() {
            return sGoodsBusinessId;
        }

        public void setSGoodsBusinessId(int sGoodsBusinessId) {
            this.sGoodsBusinessId = sGoodsBusinessId;
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

        public int getSGoodsTypeId() {
            return sGoodsTypeId;
        }

        public void setSGoodsTypeId(int sGoodsTypeId) {
            this.sGoodsTypeId = sGoodsTypeId;
        }

        public List<Integer> getSGoodsTypeIdList() {
            return sGoodsTypeIdList;
        }

        public void setSGoodsTypeIdList(List<Integer> sGoodsTypeIdList) {
            this.sGoodsTypeIdList = sGoodsTypeIdList;
        }

        public String getGoodsTypeName() {
            return goodsTypeName;
        }

        public void setGoodsTypeName(String goodsTypeName) {
            this.goodsTypeName = goodsTypeName;
        }

        public int getSBrandId() {
            return sBrandId;
        }

        public void setSBrandId(int sBrandId) {
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

        public String getGoodsVideo() {
            return goodsVideo;
        }

        public void setGoodsVideo(String goodsVideo) {
            this.goodsVideo = goodsVideo;
        }

        public String getGoodsImages() {
            return goodsImages;
        }

        public void setGoodsImages(String goodsImages) {
            this.goodsImages = goodsImages;
        }

        public String getGoodsDetails() {
            return goodsDetails;
        }

        public void setGoodsDetails(String goodsDetails) {
            this.goodsDetails = goodsDetails;
        }

        public String getSalesVolume() {
            return salesVolume;
        }

        public void setSalesVolume(String salesVolume) {
            this.salesVolume = salesVolume;
        }

        public String getInventory() {
            return inventory;
        }

        public void setInventory(String inventory) {
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

        public String getRejectReason() {
            return rejectReason;
        }

        public void setRejectReason(String rejectReason) {
            this.rejectReason = rejectReason;
        }

        public String getProhibitionSalesReason() {
            return prohibitionSalesReason;
        }

        public void setProhibitionSalesReason(String prohibitionSalesReason) {
            this.prohibitionSalesReason = prohibitionSalesReason;
        }

        public List<AttributeListDTO> getAttributeList() {
            return attributeList;
        }

        public void setAttributeList(List<AttributeListDTO> attributeList) {
            this.attributeList = attributeList;
        }

        public List<SGoodsSpecificationsMoreListDTO> getSGoodsSpecificationsMoreList() {
            return sGoodsSpecificationsMoreList;
        }

        public void setSGoodsSpecificationsMoreList(List<SGoodsSpecificationsMoreListDTO> sGoodsSpecificationsMoreList) {
            this.sGoodsSpecificationsMoreList = sGoodsSpecificationsMoreList;
        }

        public List<SGoodsSpecificationsListDTO> getSGoodsSpecificationsList() {
            return sGoodsSpecificationsList;
        }

        public void setSGoodsSpecificationsList(List<SGoodsSpecificationsListDTO> sGoodsSpecificationsList) {
            this.sGoodsSpecificationsList = sGoodsSpecificationsList;
        }

        public String getSGoodsLabelAssociationList() {
            return sGoodsLabelAssociationList;
        }

        public void setSGoodsLabelAssociationList(String sGoodsLabelAssociationList) {
            this.sGoodsLabelAssociationList = sGoodsLabelAssociationList;
        }

        public List<Integer> getLabelList() {
            return labelList;
        }

        public void setLabelList(List<Integer> labelList) {
            this.labelList = labelList;
        }

        public String getLabelNameList() {
            return labelNameList;
        }

        public void setLabelNameList(String labelNameList) {
            this.labelNameList = labelNameList;
        }

        public int getGoodsCollectStatus() {
            return goodsCollectStatus;
        }

        public void setGoodsCollectStatus(int goodsCollectStatus) {
            this.goodsCollectStatus = goodsCollectStatus;
        }

        public static class AttributeListDTO {
            private String createBy;
            private String createTime;
            private String updateBy;
            private String updateTime;
            private String remark;
            private String sGoodsAttributeTypeId;
            private int sGoodsAttributeId;
            private int sGoodsTypeId;
            private String attributeName;
            private int attributeType;
            private String attributeValue;
            private int isEnable;
            private String sGoodsAttributeAssociationId;
            private List<SGoodsAttributeOptionListDTO> sGoodsAttributeOptionList;

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

            public String getSGoodsAttributeTypeId() {
                return sGoodsAttributeTypeId;
            }

            public void setSGoodsAttributeTypeId(String sGoodsAttributeTypeId) {
                this.sGoodsAttributeTypeId = sGoodsAttributeTypeId;
            }

            public int getSGoodsAttributeId() {
                return sGoodsAttributeId;
            }

            public void setSGoodsAttributeId(int sGoodsAttributeId) {
                this.sGoodsAttributeId = sGoodsAttributeId;
            }

            public int getSGoodsTypeId() {
                return sGoodsTypeId;
            }

            public void setSGoodsTypeId(int sGoodsTypeId) {
                this.sGoodsTypeId = sGoodsTypeId;
            }

            public String getAttributeName() {
                return attributeName;
            }

            public void setAttributeName(String attributeName) {
                this.attributeName = attributeName;
            }

            public int getAttributeType() {
                return attributeType;
            }

            public void setAttributeType(int attributeType) {
                this.attributeType = attributeType;
            }

            public String getAttributeValue() {
                return attributeValue;
            }

            public void setAttributeValue(String attributeValue) {
                this.attributeValue = attributeValue;
            }

            public int getIsEnable() {
                return isEnable;
            }

            public void setIsEnable(int isEnable) {
                this.isEnable = isEnable;
            }

            public String getSGoodsAttributeAssociationId() {
                return sGoodsAttributeAssociationId;
            }

            public void setSGoodsAttributeAssociationId(String sGoodsAttributeAssociationId) {
                this.sGoodsAttributeAssociationId = sGoodsAttributeAssociationId;
            }

            public List<SGoodsAttributeOptionListDTO> getSGoodsAttributeOptionList() {
                return sGoodsAttributeOptionList;
            }

            public void setSGoodsAttributeOptionList(List<SGoodsAttributeOptionListDTO> sGoodsAttributeOptionList) {
                this.sGoodsAttributeOptionList = sGoodsAttributeOptionList;
            }

            public static class SGoodsAttributeOptionListDTO {
                private String createBy;
                private String createTime;
                private String updateBy;
                private String updateTime;
                private String remark;
                private int sGoodsAttributeOptionId;
                private int sGoodsAttributeId;
                private String attributeValue;

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

                public int getSGoodsAttributeOptionId() {
                    return sGoodsAttributeOptionId;
                }

                public void setSGoodsAttributeOptionId(int sGoodsAttributeOptionId) {
                    this.sGoodsAttributeOptionId = sGoodsAttributeOptionId;
                }

                public int getSGoodsAttributeId() {
                    return sGoodsAttributeId;
                }

                public void setSGoodsAttributeId(int sGoodsAttributeId) {
                    this.sGoodsAttributeId = sGoodsAttributeId;
                }

                public String getAttributeValue() {
                    return attributeValue;
                }

                public void setAttributeValue(String attributeValue) {
                    this.attributeValue = attributeValue;
                }
            }
        }

        public static class SGoodsSpecificationsMoreListDTO {
            private String specificationsName;
            private List<SpecificationsValueListDTO> specificationsValueList;

            public String getSpecificationsName() {
                return specificationsName;
            }

            public void setSpecificationsName(String specificationsName) {
                this.specificationsName = specificationsName;
            }

            public List<SpecificationsValueListDTO> getSpecificationsValueList() {
                return specificationsValueList;
            }

            public void setSpecificationsValueList(List<SpecificationsValueListDTO> specificationsValueList) {
                this.specificationsValueList = specificationsValueList;
            }

            public static class SpecificationsValueListDTO {
                private String specificationsValue;

                private boolean select;
                public String getSpecificationsValue() {
                    return specificationsValue;
                }

                public void setSpecificationsValue(String specificationsValue) {
                    this.specificationsValue = specificationsValue;
                }

                public boolean isSelect() {
                    return select;
                }

                public void setSelect(boolean select) {
                    this.select = select;
                }
            }
        }

        public static class SGoodsSpecificationsListDTO {
            private String createBy;
            private String createTime;
            private String updateBy;
            private String updateTime;
            private String remark;
            private int sGoodsSpecificationsId;
            private long sGoodsId;
            private int sGoodsBusinessId;
            private String specificationsNameOne;
            private String specificationsNameTwo;
            private String specificationsNameThree;
            private String specificationsValueOne;
            private String specificationsValueTwo;
            private String specificationsValueThree;
            private String name;
            private String specificationsImages;
            private String price;
            private int inventory;
            private String weight;
            private String volume;

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

            public int getSGoodsSpecificationsId() {
                return sGoodsSpecificationsId;
            }

            public void setSGoodsSpecificationsId(int sGoodsSpecificationsId) {
                this.sGoodsSpecificationsId = sGoodsSpecificationsId;
            }

            public long getSGoodsId() {
                return sGoodsId;
            }

            public void setSGoodsId(long sGoodsId) {
                this.sGoodsId = sGoodsId;
            }

            public int getSGoodsBusinessId() {
                return sGoodsBusinessId;
            }

            public void setSGoodsBusinessId(int sGoodsBusinessId) {
                this.sGoodsBusinessId = sGoodsBusinessId;
            }

            public String getSpecificationsNameOne() {
                return specificationsNameOne;
            }

            public void setSpecificationsNameOne(String specificationsNameOne) {
                this.specificationsNameOne = specificationsNameOne;
            }

            public String getSpecificationsNameTwo() {
                return specificationsNameTwo;
            }

            public void setSpecificationsNameTwo(String specificationsNameTwo) {
                this.specificationsNameTwo = specificationsNameTwo;
            }

            public String getSpecificationsNameThree() {
                return specificationsNameThree;
            }

            public void setSpecificationsNameThree(String specificationsNameThree) {
                this.specificationsNameThree = specificationsNameThree;
            }

            public String getSpecificationsValueOne() {
                return specificationsValueOne;
            }

            public void setSpecificationsValueOne(String specificationsValueOne) {
                this.specificationsValueOne = specificationsValueOne;
            }

            public String getSpecificationsValueTwo() {
                return specificationsValueTwo;
            }

            public void setSpecificationsValueTwo(String specificationsValueTwo) {
                this.specificationsValueTwo = specificationsValueTwo;
            }

            public String getSpecificationsValueThree() {
                return specificationsValueThree;
            }

            public void setSpecificationsValueThree(String specificationsValueThree) {
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

            public int getInventory() {
                return inventory;
            }

            public void setInventory(int inventory) {
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

        public String getGoodsDetailsImages() {
            return goodsDetailsImages;
        }

        public void setGoodsDetailsImages(String goodsDetailsImages) {
            this.goodsDetailsImages = goodsDetailsImages;
        }
    }
}
