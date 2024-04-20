package com.example.medicalservice.bean;

import java.util.List;

public class EatOrderListBean {


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
        private int id;
        private String rOrderId;
        private String markerId;
        private String userId;
        private int ownerId;
        private String ownerName;
        private String ownerPic;
        private String ownerSex;
        private String rOrderRemark;
        private int rFoodCanteenId;
        private String rFoodCanteenName;
        private int rFoodId;
        private String rFoodName;
        private int rOrderCount;
        private String rFoodPackingCharge;
        private String rOrderPrice;
        private String rOrderList;
        private String orderInfoList;
        private String rOrderRoute;
        private String rOrderPaymentWay;
        private String rOrderTime;
        private String rOrderCompletionTime;
        private String rOrderAddressId;
        private String rOrderAddress;
        private String ownerPhone;
        private String rOrderCourierId;
        private String rOrderCourier;
        private int rOrderStatus;
        private String rOrderType;
        private String rFoodEvaluate;
        private String userType;

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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getROrderId() {
            return rOrderId;
        }

        public String getMarkerId() {
            return markerId;
        }

        public void setMarkerId(String markerId) {
            this.markerId = markerId;
        }

        public void setROrderId(String rOrderId) {
            this.rOrderId = rOrderId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(int ownerId) {
            this.ownerId = ownerId;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public String getOwnerPic() {
            return ownerPic;
        }

        public void setOwnerPic(String ownerPic) {
            this.ownerPic = ownerPic;
        }

        public String getOwnerSex() {
            return ownerSex;
        }

        public void setOwnerSex(String ownerSex) {
            this.ownerSex = ownerSex;
        }

        public String getROrderRemark() {
            return rOrderRemark;
        }

        public void setROrderRemark(String rOrderRemark) {
            this.rOrderRemark = rOrderRemark;
        }

        public int getRFoodCanteenId() {
            return rFoodCanteenId;
        }

        public void setRFoodCanteenId(int rFoodCanteenId) {
            this.rFoodCanteenId = rFoodCanteenId;
        }

        public String getRFoodCanteenName() {
            return rFoodCanteenName;
        }

        public void setRFoodCanteenName(String rFoodCanteenName) {
            this.rFoodCanteenName = rFoodCanteenName;
        }

        public int getRFoodId() {
            return rFoodId;
        }

        public void setRFoodId(int rFoodId) {
            this.rFoodId = rFoodId;
        }

        public String getRFoodName() {
            return rFoodName;
        }

        public void setRFoodName(String rFoodName) {
            this.rFoodName = rFoodName;
        }

        public int getROrderCount() {
            return rOrderCount;
        }

        public void setROrderCount(int rOrderCount) {
            this.rOrderCount = rOrderCount;
        }

        public String getRFoodPackingCharge() {
            return rFoodPackingCharge;
        }

        public void setRFoodPackingCharge(String rFoodPackingCharge) {
            this.rFoodPackingCharge = rFoodPackingCharge;
        }

        public String getROrderPrice() {
            return rOrderPrice;
        }

        public void setROrderPrice(String rOrderPrice) {
            this.rOrderPrice = rOrderPrice;
        }

        public String getROrderList() {
            return rOrderList;
        }

        public void setROrderList(String rOrderList) {
            this.rOrderList = rOrderList;
        }

        public String getOrderInfoList() {
            return orderInfoList;
        }

        public void setOrderInfoList(String orderInfoList) {
            this.orderInfoList = orderInfoList;
        }

        public String getROrderRoute() {
            return rOrderRoute;
        }

        public void setROrderRoute(String rOrderRoute) {
            this.rOrderRoute = rOrderRoute;
        }

        public String getROrderPaymentWay() {
            return rOrderPaymentWay;
        }

        public void setROrderPaymentWay(String rOrderPaymentWay) {
            this.rOrderPaymentWay = rOrderPaymentWay;
        }

        public String getROrderTime() {
            return rOrderTime;
        }

        public void setROrderTime(String rOrderTime) {
            this.rOrderTime = rOrderTime;
        }

        public String getROrderCompletionTime() {
            return rOrderCompletionTime;
        }

        public void setROrderCompletionTime(String rOrderCompletionTime) {
            this.rOrderCompletionTime = rOrderCompletionTime;
        }

        public String getROrderAddressId() {
            return rOrderAddressId;
        }

        public void setROrderAddressId(String rOrderAddressId) {
            this.rOrderAddressId = rOrderAddressId;
        }

        public String getROrderAddress() {
            return rOrderAddress;
        }

        public void setROrderAddress(String rOrderAddress) {
            this.rOrderAddress = rOrderAddress;
        }

        public String getOwnerPhone() {
            return ownerPhone;
        }

        public void setOwnerPhone(String ownerPhone) {
            this.ownerPhone = ownerPhone;
        }

        public String getROrderCourierId() {
            return rOrderCourierId;
        }

        public void setROrderCourierId(String rOrderCourierId) {
            this.rOrderCourierId = rOrderCourierId;
        }

        public String getROrderCourier() {
            return rOrderCourier;
        }

        public void setROrderCourier(String rOrderCourier) {
            this.rOrderCourier = rOrderCourier;
        }

        public int getROrderStatus() {
            return rOrderStatus;
        }

        public void setROrderStatus(int rOrderStatus) {
            this.rOrderStatus = rOrderStatus;
        }

        public String getROrderType() {
            return rOrderType;
        }

        public void setROrderType(String rOrderType) {
            this.rOrderType = rOrderType;
        }

        public String getRFoodEvaluate() {
            return rFoodEvaluate;
        }

        public void setRFoodEvaluate(String rFoodEvaluate) {
            this.rFoodEvaluate = rFoodEvaluate;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }
    }
}
