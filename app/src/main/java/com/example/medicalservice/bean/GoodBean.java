package com.example.medicalservice.bean;

import java.util.List;

public class GoodBean {
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
        private String sGoodsId;
        private String sGoodsBusinessId;
        private String goodsName;
        private String salesVolume;
        private String goodsCoverImages;
        private String price;
        private String minPrice;
        private String maxPrice;
        private String goodsTitle;
        private String unit;
        private String sGoodsTypeId;
        private String sGoodsTypeIdList;
        private String goodsTypeName;
        private String sBrandId;
        private String brandName;
        private String specificationsType;
        private String goodsVideo;
        private String goodsImages;
        private String goodsDetails;
        private String inventory;
        private String status;
        private String isShow;
        private String isShowTime;

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

        public String getSGoodsId() {
            return sGoodsId;
        }

        public void setSGoodsId(String sGoodsId) {
            this.sGoodsId = sGoodsId;
        }

        public String getSGoodsBusinessId() {
            return sGoodsBusinessId;
        }

        public void setSGoodsBusinessId(String sGoodsBusinessId) {
            this.sGoodsBusinessId = sGoodsBusinessId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getSalesVolume() {
            return salesVolume;
        }

        public void setSalesVolume(String salesVolume) {
            this.salesVolume = salesVolume;
        }

        public String getGoodsCoverImages() {
            return goodsCoverImages;
        }

        public void setGoodsCoverImages(String goodsCoverImages) {
            this.goodsCoverImages = goodsCoverImages;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(String minPrice) {
            this.minPrice = minPrice;
        }

        public String getMaxPrice() {
            return maxPrice;
        }

        public void setMaxPrice(String maxPrice) {
            this.maxPrice = maxPrice;
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

        public String getSGoodsTypeId() {
            return sGoodsTypeId;
        }

        public void setSGoodsTypeId(String sGoodsTypeId) {
            this.sGoodsTypeId = sGoodsTypeId;
        }

        public String getSGoodsTypeIdList() {
            return sGoodsTypeIdList;
        }

        public void setSGoodsTypeIdList(String sGoodsTypeIdList) {
            this.sGoodsTypeIdList = sGoodsTypeIdList;
        }

        public String getGoodsTypeName() {
            return goodsTypeName;
        }

        public void setGoodsTypeName(String goodsTypeName) {
            this.goodsTypeName = goodsTypeName;
        }

        public String getSBrandId() {
            return sBrandId;
        }

        public void setSBrandId(String sBrandId) {
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
    }

//    private List<RowsDTO> rows;
//    private int code;
//    private String msg;
//
//    public int getTotal() {
//        return total;
//    }
//
//    public void setTotal(int total) {
//        this.total = total;
//    }
//
//    public List<RowsDTO> getRows() {
//        return rows;
//    }
//
//    public void setRows(List<RowsDTO> rows) {
//        this.rows = rows;
//    }
//
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public static class RowsDTO {
//        private String createBy;
//        private String createTime;
//        private String updateBy;
//        private String updateTime;
//        private String remark;
//        private int sBrandId;
//        private String brandName;
//        private String brandLogo;
//
//        public String getCreateBy() {
//            return createBy;
//        }
//
//        public void setCreateBy(String createBy) {
//            this.createBy = createBy;
//        }
//
//        public String getCreateTime() {
//            return createTime;
//        }
//
//        public void setCreateTime(String createTime) {
//            this.createTime = createTime;
//        }
//
//        public String getUpdateBy() {
//            return updateBy;
//        }
//
//        public void setUpdateBy(String updateBy) {
//            this.updateBy = updateBy;
//        }
//
//        public String getUpdateTime() {
//            return updateTime;
//        }
//
//        public void setUpdateTime(String updateTime) {
//            this.updateTime = updateTime;
//        }
//
//        public String getRemark() {
//            return remark;
//        }
//
//        public void setRemark(String remark) {
//            this.remark = remark;
//        }
//
//        public int getSBrandId() {
//            return sBrandId;
//        }
//
//        public void setSBrandId(int sBrandId) {
//            this.sBrandId = sBrandId;
//        }
//
//        public String getBrandName() {
//            return brandName;
//        }
//
//        public void setBrandName(String brandName) {
//            this.brandName = brandName;
//        }
//
//        public String getBrandLogo() {
//            return brandLogo;
//        }
//
//        public void setBrandLogo(String brandLogo) {
//            this.brandLogo = brandLogo;
//        }
//    }
}
