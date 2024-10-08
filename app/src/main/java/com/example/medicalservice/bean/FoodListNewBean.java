package com.example.medicalservice.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @description 新的菜单列表
 * @author: Lenovo
 * @date: 2024/5/21
 */
public class FoodListNewBean {
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
        @SerializedName("rFoodId")
        private Integer rFoodId;
        @SerializedName("rFoodName")
        private String rFoodName;
        @SerializedName("rFoodTypeId")
        private Integer rFoodTypeId;
        @SerializedName("rFoodType")
        private String rFoodType;
        @SerializedName("rFoodPrice")
        private Double rFoodPrice;
        @SerializedName("rFoodPackingCharge")
        private Double rFoodPackingCharge;
        @SerializedName("rFoodSpicyTasteId")
        private Integer rFoodSpicyTasteId;
        @SerializedName("rFoodSpicyTaste")
        private String rFoodSpicyTaste;
        @SerializedName("rFoodTasteId")
        private Integer rFoodTasteId;
        @SerializedName("number")
        private Integer number = 0;
        @SerializedName("rFoodTaste")
        private String rFoodTaste;
        @SerializedName("rFoodFlavorId")
        private Integer rFoodFlavorId;
        @SerializedName("rFoodFlavor")
        private String rFoodFlavor;
        @SerializedName("rFoodIsorder")
        private Integer rFoodIsorder;
        @SerializedName("rFoodNewStatus")
        private Integer rFoodNewStatus;
        @SerializedName("rFoodNutritionDescription")
        private String rFoodNutritionDescription;
        @SerializedName("rFoodIngredientsDescription")
        private String rFoodIngredientsDescription;
        @SerializedName("rFoodTabooPopulationId")
        private String rFoodTabooPopulationId;
        @SerializedName("rFoodTabooPopulation")
        private String rFoodTabooPopulation;
        @SerializedName("rFoodTabooPopulationList")
        private Object rFoodTabooPopulationList;
        @SerializedName("rFoodPic")
        private String rFoodPic;
        @SerializedName("rFoodStatus")
        private Integer rFoodStatus;
        @SerializedName("rFoodOrder")
        private Integer rFoodOrder;
        @SerializedName("userId")
        private Long userId;
        @SerializedName("userType")
        private String userType;
        @SerializedName("markerId")
        private Long markerId;
        @SerializedName("markerName")
        private Object markerName;
        @SerializedName("rFoodCanteenId")
        private Integer rFoodCanteenId;
        @SerializedName("rFoodCanteenName")
        private String rFoodCanteenName;
        @SerializedName("rFoodCommunityOrPrivate")
        private Integer rFoodCommunityOrPrivate;

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

        public Integer getRFoodId() {
            return rFoodId;
        }

        public void setRFoodId(Integer rFoodId) {
            this.rFoodId = rFoodId;
        }

        public String getRFoodName() {
            return rFoodName;
        }

        public void setRFoodName(String rFoodName) {
            this.rFoodName = rFoodName;
        }

        public Integer getRFoodTypeId() {
            return rFoodTypeId;
        }

        public void setRFoodTypeId(Integer rFoodTypeId) {
            this.rFoodTypeId = rFoodTypeId;
        }

        public String getRFoodType() {
            return rFoodType;
        }

        public void setRFoodType(String rFoodType) {
            this.rFoodType = rFoodType;
        }

        public Double getRFoodPrice() {
            return rFoodPrice;
        }

        public void setRFoodPrice(Double rFoodPrice) {
            this.rFoodPrice = rFoodPrice;
        }

        public Double getRFoodPackingCharge() {
            return rFoodPackingCharge;
        }

        public void setRFoodPackingCharge(Double rFoodPackingCharge) {
            this.rFoodPackingCharge = rFoodPackingCharge;
        }

        public Integer getRFoodSpicyTasteId() {
            return rFoodSpicyTasteId;
        }

        public void setRFoodSpicyTasteId(Integer rFoodSpicyTasteId) {
            this.rFoodSpicyTasteId = rFoodSpicyTasteId;
        }

        public String getRFoodSpicyTaste() {
            return rFoodSpicyTaste;
        }

        public void setRFoodSpicyTaste(String rFoodSpicyTaste) {
            this.rFoodSpicyTaste = rFoodSpicyTaste;
        }

        public Integer getRFoodTasteId() {
            return rFoodTasteId;
        }

        public void setRFoodTasteId(Integer rFoodTasteId) {
            this.rFoodTasteId = rFoodTasteId;
        }

        public String getRFoodTaste() {
            return rFoodTaste;
        }

        public void setRFoodTaste(String rFoodTaste) {
            this.rFoodTaste = rFoodTaste;
        }

        public Integer getRFoodFlavorId() {
            return rFoodFlavorId;
        }

        public void setRFoodFlavorId(Integer rFoodFlavorId) {
            this.rFoodFlavorId = rFoodFlavorId;
        }

        public String getRFoodFlavor() {
            return rFoodFlavor;
        }

        public void setRFoodFlavor(String rFoodFlavor) {
            this.rFoodFlavor = rFoodFlavor;
        }

        public Integer getRFoodIsorder() {
            return rFoodIsorder;
        }

        public void setRFoodIsorder(Integer rFoodIsorder) {
            this.rFoodIsorder = rFoodIsorder;
        }

        public Integer getRFoodNewStatus() {
            return rFoodNewStatus;
        }

        public void setRFoodNewStatus(Integer rFoodNewStatus) {
            this.rFoodNewStatus = rFoodNewStatus;
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

        public String getRFoodTabooPopulationId() {
            return rFoodTabooPopulationId;
        }

        public void setRFoodTabooPopulationId(String rFoodTabooPopulationId) {
            this.rFoodTabooPopulationId = rFoodTabooPopulationId;
        }

        public String getRFoodTabooPopulation() {
            return rFoodTabooPopulation;
        }

        public void setRFoodTabooPopulation(String rFoodTabooPopulation) {
            this.rFoodTabooPopulation = rFoodTabooPopulation;
        }

        public Object getRFoodTabooPopulationList() {
            return rFoodTabooPopulationList;
        }

        public void setRFoodTabooPopulationList(Object rFoodTabooPopulationList) {
            this.rFoodTabooPopulationList = rFoodTabooPopulationList;
        }

        public String getRFoodPic() {
            return rFoodPic;
        }

        public void setRFoodPic(String rFoodPic) {
            this.rFoodPic = rFoodPic;
        }

        public Integer getRFoodStatus() {
            return rFoodStatus;
        }

        public void setRFoodStatus(Integer rFoodStatus) {
            this.rFoodStatus = rFoodStatus;
        }

        public Integer getRFoodOrder() {
            return rFoodOrder;
        }

        public void setRFoodOrder(Integer rFoodOrder) {
            this.rFoodOrder = rFoodOrder;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public Long getMarkerId() {
            return markerId;
        }

        public void setMarkerId(Long markerId) {
            this.markerId = markerId;
        }

        public Object getMarkerName() {
            return markerName;
        }

        public void setMarkerName(Object markerName) {
            this.markerName = markerName;
        }

        public Integer getRFoodCanteenId() {
            return rFoodCanteenId;
        }

        public void setRFoodCanteenId(Integer rFoodCanteenId) {
            this.rFoodCanteenId = rFoodCanteenId;
        }

        public String getRFoodCanteenName() {
            return rFoodCanteenName;
        }

        public void setRFoodCanteenName(String rFoodCanteenName) {
            this.rFoodCanteenName = rFoodCanteenName;
        }

        public Integer getRFoodCommunityOrPrivate() {
            return rFoodCommunityOrPrivate;
        }

        public void setRFoodCommunityOrPrivate(Integer rFoodCommunityOrPrivate) {
            this.rFoodCommunityOrPrivate = rFoodCommunityOrPrivate;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }
    }
}
