package com.example.medicalservice.bean;

import java.util.List;

public class OrderBeanList {


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
        private long sOrderParentId;
        private String sOrderId;
        private int userId;
        private String sumOrderPrice;
        private List<SOrderPoListDTO> sOrderPoList;

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

        public long getSOrderParentId() {
            return sOrderParentId;
        }

        public void setSOrderParentId(long sOrderParentId) {
            this.sOrderParentId = sOrderParentId;
        }

        public String getSOrderId() {
            return sOrderId;
        }

        public void setSOrderId(String sOrderId) {
            this.sOrderId = sOrderId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getSumOrderPrice() {
            return sumOrderPrice;
        }

        public void setSumOrderPrice(String sumOrderPrice) {
            this.sumOrderPrice = sumOrderPrice;
        }

        public List<SOrderPoListDTO> getSOrderPoList() {
            return sOrderPoList;
        }

        public void setSOrderPoList(List<SOrderPoListDTO> sOrderPoList) {
            this.sOrderPoList = sOrderPoList;
        }

        public static class SOrderPoListDTO {
            private String createBy;
            private String createTime;
            private String updateBy;
            private String updateTime;
            private String remark;
            private String goodsCoverImages;
            private String businessName;
            private String sOrderId;
            private String sOrderParentId;
            private String userId;
            private String sGoodsBusinessId;
            private String sGoodsId;
            private String sGoodsSpecificationsId;
            private String sGoodsSpecificationsName;
            private int goodsNumber;
            private String goodsPrice;
            private String orderPrice;
            private String address;
            private String contact;
            private String phone;
            private String status;
            private String payWay;
            private String orderSource;
            private String buyerMessage;
            private String sellerMessage;
            private String sCommentsFirstId;
            private String deliveryMethod;
            private String logistics;
            private String createOrderTime;
            private String finishOrderTime;
            private String goodsName;
            private String sGoodsTypeId;
            private String specificationsValueOne;
            private String specificationsValueTwo;
            private String specificationsValueThree;
            private String name;
            private int goodsSendNumber;
            private int thisSendGoodsNumber;

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

            public String getGoodsCoverImages() {
                return goodsCoverImages;
            }




            public void setGoodsCoverImages(String goodsCoverImages) {
                this.goodsCoverImages = goodsCoverImages;
            }

            public String getBusinessName() {
                return businessName;
            }

            public void setBusinessName(String businessName) {
                this.businessName = businessName;
            }

            public String getSOrderId() {
                return sOrderId;
            }

            public void setSOrderId(String sOrderId) {
                this.sOrderId = sOrderId;
            }

            public String getSOrderParentId() {
                return sOrderParentId;
            }

            public void setSOrderParentId(String sOrderParentId) {
                this.sOrderParentId = sOrderParentId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getSGoodsBusinessId() {
                return sGoodsBusinessId;
            }

            public void setSGoodsBusinessId(String sGoodsBusinessId) {
                this.sGoodsBusinessId = sGoodsBusinessId;
            }

            public String getSGoodsId() {
                return sGoodsId;
            }

            public void setSGoodsId(String sGoodsId) {
                this.sGoodsId = sGoodsId;
            }

            public String getSGoodsSpecificationsId() {
                return sGoodsSpecificationsId;
            }

            public void setSGoodsSpecificationsId(String sGoodsSpecificationsId) {
                this.sGoodsSpecificationsId = sGoodsSpecificationsId;
            }

            public String getSGoodsSpecificationsName() {
                return sGoodsSpecificationsName;
            }

            public void setSGoodsSpecificationsName(String sGoodsSpecificationsName) {
                this.sGoodsSpecificationsName = sGoodsSpecificationsName;
            }

            public int getGoodsNumber() {
                return goodsNumber;
            }

            public void setGoodsNumber(int goodsNumber) {
                this.goodsNumber = goodsNumber;
            }

            public String getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(String goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public String getOrderPrice() {
                return orderPrice;
            }

            public void setOrderPrice(String orderPrice) {
                this.orderPrice = orderPrice;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getContact() {
                return contact;
            }

            public void setContact(String contact) {
                this.contact = contact;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getPayWay() {
                return payWay;
            }

            public void setPayWay(String payWay) {
                this.payWay = payWay;
            }

            public String getOrderSource() {
                return orderSource;
            }

            public void setOrderSource(String orderSource) {
                this.orderSource = orderSource;
            }

            public String getBuyerMessage() {
                return buyerMessage;
            }

            public void setBuyerMessage(String buyerMessage) {
                this.buyerMessage = buyerMessage;
            }

            public String getSellerMessage() {
                return sellerMessage;
            }

            public void setSellerMessage(String sellerMessage) {
                this.sellerMessage = sellerMessage;
            }

            public String getSCommentsFirstId() {
                return sCommentsFirstId;
            }

            public void setSCommentsFirstId(String sCommentsFirstId) {
                this.sCommentsFirstId = sCommentsFirstId;
            }

            public String getDeliveryMethod() {
                return deliveryMethod;
            }

            public void setDeliveryMethod(String deliveryMethod) {
                this.deliveryMethod = deliveryMethod;
            }

            public String getLogistics() {
                return logistics;
            }

            public void setLogistics(String logistics) {
                this.logistics = logistics;
            }

            public String getCreateOrderTime() {
                return createOrderTime;
            }

            public void setCreateOrderTime(String createOrderTime) {
                this.createOrderTime = createOrderTime;
            }

            public String getFinishOrderTime() {
                return finishOrderTime;
            }

            public void setFinishOrderTime(String finishOrderTime) {
                this.finishOrderTime = finishOrderTime;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getSGoodsTypeId() {
                return sGoodsTypeId;
            }

            public void setSGoodsTypeId(String sGoodsTypeId) {
                this.sGoodsTypeId = sGoodsTypeId;
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

            public int getGoodsSendNumber() {
                return goodsSendNumber;
            }

            public void setGoodsSendNumber(int goodsSendNumber) {
                this.goodsSendNumber = goodsSendNumber;
            }

            public int getThisSendGoodsNumber() {
                return thisSendGoodsNumber;
            }

            public void setThisSendGoodsNumber(int thisSendGoodsNumber) {
                this.thisSendGoodsNumber = thisSendGoodsNumber;
            }

            private List<SOrderSendGoodsDTO>sOrderSendGoodsList;

            public List<SOrderSendGoodsDTO> getsOrderSendGoodsList() {
                return sOrderSendGoodsList;
            }

            public void setsOrderSendGoodsList(List<SOrderSendGoodsDTO> sOrderSendGoodsList) {
                this.sOrderSendGoodsList = sOrderSendGoodsList;
            }

            public static class SOrderSendGoodsDTO{
                private String sOrderId;
                private String sGoodsId;

                private String logisticsCompany;
                private String trackingNumber;

                public String getsOrderId() {
                    return sOrderId;
                }

                public void setsOrderId(String sOrderId) {
                    this.sOrderId = sOrderId;
                }

                public String getsGoodsId() {
                    return sGoodsId;
                }

                public void setsGoodsId(String sGoodsId) {
                    this.sGoodsId = sGoodsId;
                }

                public String getLogisticsCompany() {
                    return logisticsCompany;
                }

                public void setLogisticsCompany(String logisticsCompany) {
                    this.logisticsCompany = logisticsCompany;
                }

                public String getTrackingNumber() {
                    return trackingNumber;
                }

                public void setTrackingNumber(String trackingNumber) {
                    this.trackingNumber = trackingNumber;
                }
            }
        }
    }
}
