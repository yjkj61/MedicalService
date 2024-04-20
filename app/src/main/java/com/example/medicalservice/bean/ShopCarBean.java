package com.example.medicalservice.bean;

import java.util.List;

public class ShopCarBean {


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
        private int sGoodsBusinessId;
        private String businessName;

        private boolean check = false;

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }

        private List<SShoppingCartVoListDTO> sShoppingCartVoList;

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

        public int getSGoodsBusinessId() {
            return sGoodsBusinessId;
        }

        public void setSGoodsBusinessId(int sGoodsBusinessId) {
            this.sGoodsBusinessId = sGoodsBusinessId;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public List<SShoppingCartVoListDTO> getSShoppingCartVoList() {
            return sShoppingCartVoList;
        }

        public void setSShoppingCartVoList(List<SShoppingCartVoListDTO> sShoppingCartVoList) {
            this.sShoppingCartVoList = sShoppingCartVoList;
        }

        public static class SShoppingCartVoListDTO {
            private String createBy;
            private String createTime;
            private String updateBy;
            private String updateTime;
            private String remark;
            private String sShoppingCartId;
            private int userId;
            private int sGoodsBusinessId;
            private String businessName;
            private String sGoodsId;
            private String goodsName;
            private List<String> goodsLabelList;
            private String goodsLabelId;
            private String isShow;
            private int sGoodsSpecificationsId;
            private String sGoodsSpecificationsName;
            private int price;
            private int number;
            private String specificationsImages;
            private String goodsRemind;

            private boolean check = false;

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

            public String getSShoppingCartId() {
                return sShoppingCartId;
            }

            public void setSShoppingCartId(String sShoppingCartId) {
                this.sShoppingCartId = sShoppingCartId;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getSGoodsBusinessId() {
                return sGoodsBusinessId;
            }

            public void setSGoodsBusinessId(int sGoodsBusinessId) {
                this.sGoodsBusinessId = sGoodsBusinessId;
            }

            public String getBusinessName() {
                return businessName;
            }

            public void setBusinessName(String businessName) {
                this.businessName = businessName;
            }

            public String getSGoodsId() {
                return sGoodsId;
            }

            public void setSGoodsId(String sGoodsId) {
                this.sGoodsId = sGoodsId;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public List<String> getGoodsLabelList() {
                return goodsLabelList;
            }

            public void setGoodsLabelList(List<String> goodsLabelList) {
                this.goodsLabelList = goodsLabelList;
            }

            public String getGoodsLabelId() {
                return goodsLabelId;
            }

            public void setGoodsLabelId(String goodsLabelId) {
                this.goodsLabelId = goodsLabelId;
            }

            public String getIsShow() {
                return isShow;
            }

            public void setIsShow(String isShow) {
                this.isShow = isShow;
            }

            public int getSGoodsSpecificationsId() {
                return sGoodsSpecificationsId;
            }

            public void setSGoodsSpecificationsId(int sGoodsSpecificationsId) {
                this.sGoodsSpecificationsId = sGoodsSpecificationsId;
            }

            public String getSGoodsSpecificationsName() {
                return sGoodsSpecificationsName;
            }

            public void setSGoodsSpecificationsName(String sGoodsSpecificationsName) {
                this.sGoodsSpecificationsName = sGoodsSpecificationsName;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public String getSpecificationsImages() {
                return specificationsImages;
            }

            public void setSpecificationsImages(String specificationsImages) {
                this.specificationsImages = specificationsImages;
            }

            public String getGoodsRemind() {
                return goodsRemind;
            }

            public void setGoodsRemind(String goodsRemind) {
                this.goodsRemind = goodsRemind;
            }

            public boolean isCheck() {
                return check;
            }

            public void setCheck(boolean check) {
                this.check = check;
            }
        }
    }
}
