package com.example.medicalservice.bean;

import java.util.List;

public class MyFavBean 
{
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
        private int sGoodsCollectId;
        private long sGoodsId;
        private String goodsName;
        private int price;
        private String goodsCoverImages;
        private int collectCount;

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

        public int getSGoodsCollectId() {
            return sGoodsCollectId;
        }

        public void setSGoodsCollectId(int sGoodsCollectId) {
            this.sGoodsCollectId = sGoodsCollectId;
        }

        public long getSGoodsId() {
            return sGoodsId;
        }

        public void setSGoodsId(long sGoodsId) {
            this.sGoodsId = sGoodsId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getGoodsCoverImages() {
            return goodsCoverImages;
        }

        public void setGoodsCoverImages(String goodsCoverImages) {
            this.goodsCoverImages = goodsCoverImages;
        }

        public int getCollectCount() {
            return collectCount;
        }

        public void setCollectCount(int collectCount) {
            this.collectCount = collectCount;
        }
    }
}
