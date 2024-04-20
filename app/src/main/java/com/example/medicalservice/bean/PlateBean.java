package com.example.medicalservice.bean;

import java.util.List;

public class PlateBean {

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
        private int sPlateId;
        private String plateType;
        private int sGoodsTypeId;
        private String plateTitle;
        private String plateSubtitle;
        private String sGoodsId;
        private List<GoodBean.RowsDTO> goodsList;
        private String goodsName;
        private String minPrice;
        private String maxPrice;
        private String rankingIsShow;
        private List<GoodBean.RowsDTO> goodsRankingList;

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

        public int getSPlateId() {
            return sPlateId;
        }

        public void setSPlateId(int sPlateId) {
            this.sPlateId = sPlateId;
        }

        public String getPlateType() {
            return plateType;
        }

        public void setPlateType(String plateType) {
            this.plateType = plateType;
        }

        public int getSGoodsTypeId() {
            return sGoodsTypeId;
        }

        public void setSGoodsTypeId(int sGoodsTypeId) {
            this.sGoodsTypeId = sGoodsTypeId;
        }

        public String getPlateTitle() {
            return plateTitle;
        }

        public void setPlateTitle(String plateTitle) {
            this.plateTitle = plateTitle;
        }

        public String getPlateSubtitle() {
            return plateSubtitle;
        }

        public void setPlateSubtitle(String plateSubtitle) {
            this.plateSubtitle = plateSubtitle;
        }

        public String getSGoodsId() {
            return sGoodsId;
        }

        public void setSGoodsId(String sGoodsId) {
            this.sGoodsId = sGoodsId;
        }

        public List<GoodBean.RowsDTO> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodBean.RowsDTO> goodsList) {
            this.goodsList = goodsList;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
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

        public String getRankingIsShow() {
            return rankingIsShow;
        }

        public void setRankingIsShow(String rankingIsShow) {
            this.rankingIsShow = rankingIsShow;
        }

        public List<GoodBean.RowsDTO> getGoodsRankingList() {
            return goodsRankingList;
        }

        public void setGoodsRankingList(List<GoodBean.RowsDTO> goodsRankingList) {
            this.goodsRankingList = goodsRankingList;
        }




    }
}
