package com.example.medicalservice.bean;

import java.util.List;

public class FoodListBean {


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

    public  class RowsDTO {
        private String createBy;
        private String createTime;
        private String updateBy;
        private String updateTime;
        private String remark;
        private int rFoodId;
        private String userId;
        private String rFoodName;
        private int rFoodTypeId;
        private String rFoodType;
        private double rFoodPrice;
        private double rFoodPackingCharge;
        private String rFoodSpicyTaste;
        private String rFoodCanteenId;
        private String rFoodTaste;
        private String rFoodFlavor;
        private int rFoodIsorder;
        private String rFoodNutritionDescription;
        private String rFoodIngredientsDescription;
        private String rFoodTabooPopulation;
        private String rFoodPic;
        private int rFoodStatus;
        private int rFoodOrder;

        private int rFoodNewStatus;

        private boolean select = false;

        private int number = 0;

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

        public int getRFoodId() {
            return rFoodId;
        }

        public double getrFoodPackingCharge() {
            return rFoodPackingCharge;
        }

        public void setrFoodPackingCharge(double rFoodPackingCharge) {
            this.rFoodPackingCharge = rFoodPackingCharge;
        }

        public void setRFoodId(int rFoodId) {
            this.rFoodId = rFoodId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getRFoodName() {
            return rFoodName;
        }

        public void setRFoodName(String rFoodName) {
            this.rFoodName = rFoodName;
        }

        public int getRFoodTypeId() {
            return rFoodTypeId;
        }

        public void setRFoodTypeId(int rFoodTypeId) {
            this.rFoodTypeId = rFoodTypeId;
        }

        public int getrFoodNewStatus() {
            return rFoodNewStatus;
        }

        public void setrFoodNewStatus(int rFoodNewStatus) {
            this.rFoodNewStatus = rFoodNewStatus;
        }

        public String getRFoodType() {
            return rFoodType;
        }

        public void setRFoodType(String rFoodType) {
            this.rFoodType = rFoodType;
        }

        public double getRFoodPrice() {
            return rFoodPrice;
        }

        public void setRFoodPrice(double rFoodPrice) {
            this.rFoodPrice = rFoodPrice;
        }

        public String getRFoodSpicyTaste() {
            return rFoodSpicyTaste;
        }

        public void setRFoodSpicyTaste(String rFoodSpicyTaste) {
            this.rFoodSpicyTaste = rFoodSpicyTaste;
        }

        public String getRFoodTaste() {
            return rFoodTaste;
        }

        public void setRFoodTaste(String rFoodTaste) {
            this.rFoodTaste = rFoodTaste;
        }

        public String getRFoodFlavor() {
            return rFoodFlavor;
        }

        public void setRFoodFlavor(String rFoodFlavor) {
            this.rFoodFlavor = rFoodFlavor;
        }

        public int getRFoodIsorder() {
            return rFoodIsorder;
        }

        public void setRFoodIsorder(int rFoodIsorder) {
            this.rFoodIsorder = rFoodIsorder;
        }

        public String getRFoodNutritionDescription() {
            return rFoodNutritionDescription;
        }

        public void setRFoodNutritionDescription(String rFoodNutritionDescription) {
            this.rFoodNutritionDescription = rFoodNutritionDescription;
        }

        public String getRFoodIngredientsDescription() {
            return rFoodIngredientsDescription;
        }

        public void setRFoodIngredientsDescription(String rFoodIngredientsDescription) {
            this.rFoodIngredientsDescription = rFoodIngredientsDescription;
        }

        public String getRFoodTabooPopulation() {
            return rFoodTabooPopulation;
        }

        public void setRFoodTabooPopulation(String rFoodTabooPopulation) {
            this.rFoodTabooPopulation = rFoodTabooPopulation;
        }

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public String getRFoodPic() {
            return rFoodPic;
        }

        public void setRFoodPic(String rFoodPic) {
            this.rFoodPic = rFoodPic;
        }

        public int getRFoodStatus() {
            return rFoodStatus;
        }

        public void setRFoodStatus(int rFoodStatus) {
            this.rFoodStatus = rFoodStatus;
        }

        public int getRFoodOrder() {
            return rFoodOrder;
        }

        public void setRFoodOrder(int rFoodOrder) {
            this.rFoodOrder = rFoodOrder;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getrFoodCanteenId() {
            return rFoodCanteenId;
        }

        public void setrFoodCanteenId(String rFoodCanteenId) {
            this.rFoodCanteenId = rFoodCanteenId;
        }
    }
}
