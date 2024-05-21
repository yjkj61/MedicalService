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
        @SerializedName("rFoodBookId")
        private Integer rFoodBookId;
        @SerializedName("rFoodId")
        private String rFoodId;
        @SerializedName("rFoodName")
        private String rFoodName;
        @SerializedName("rFoodTypeId")
        private String rFoodTypeId;
        @SerializedName("rFoodType")
        private String rFoodType;
        @SerializedName("meals")
        private String meals;
        @SerializedName("weeks")
        private String weeks;
        @SerializedName("rFoodBookTime")
        private String rFoodBookTime;
        @SerializedName("rFoodNewStatus")
        private String rFoodNewStatus;
        @SerializedName("selectedFoods")
        private String selectedFoods;
        @SerializedName("rFoodCommunityOrPrivate")
        private Integer rFoodCommunityOrPrivate;
        @SerializedName("rFoodCanteenId")
        private Integer rFoodCanteenId;
        @SerializedName("rFoodCanteenName")
        private Object rFoodCanteenName;
        @SerializedName("userId")
        private Object userId;
        @SerializedName("userType")
        private String userType;
        @SerializedName("markerId")
        private Integer markerId;
        @SerializedName("markerName")
        private Object markerName;
        @SerializedName("rFoodPrice")
        private String rFoodPrice;
        @SerializedName("rFoodPic")
        private String rFoodPic;
        @SerializedName("years")
        private Object years;

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

        public Integer getRFoodBookId() {
            return rFoodBookId;
        }

        public void setRFoodBookId(Integer rFoodBookId) {
            this.rFoodBookId = rFoodBookId;
        }

        public String getRFoodId() {
            return rFoodId;
        }

        public void setRFoodId(String rFoodId) {
            this.rFoodId = rFoodId;
        }

        public String getRFoodName() {
            return rFoodName;
        }

        public void setRFoodName(String rFoodName) {
            this.rFoodName = rFoodName;
        }

        public String getRFoodTypeId() {
            return rFoodTypeId;
        }

        public void setRFoodTypeId(String rFoodTypeId) {
            this.rFoodTypeId = rFoodTypeId;
        }

        public String getRFoodType() {
            return rFoodType;
        }

        public void setRFoodType(String rFoodType) {
            this.rFoodType = rFoodType;
        }

        public String getMeals() {
            return meals;
        }

        public void setMeals(String meals) {
            this.meals = meals;
        }

        public String getWeeks() {
            return weeks;
        }

        public void setWeeks(String weeks) {
            this.weeks = weeks;
        }

        public String getRFoodBookTime() {
            return rFoodBookTime;
        }

        public void setRFoodBookTime(String rFoodBookTime) {
            this.rFoodBookTime = rFoodBookTime;
        }

        public String getRFoodNewStatus() {
            return rFoodNewStatus;
        }

        public void setRFoodNewStatus(String rFoodNewStatus) {
            this.rFoodNewStatus = rFoodNewStatus;
        }

        public String getSelectedFoods() {
            return selectedFoods;
        }

        public void setSelectedFoods(String selectedFoods) {
            this.selectedFoods = selectedFoods;
        }

        public Integer getRFoodCommunityOrPrivate() {
            return rFoodCommunityOrPrivate;
        }

        public void setRFoodCommunityOrPrivate(Integer rFoodCommunityOrPrivate) {
            this.rFoodCommunityOrPrivate = rFoodCommunityOrPrivate;
        }

        public Integer getRFoodCanteenId() {
            return rFoodCanteenId;
        }

        public void setRFoodCanteenId(Integer rFoodCanteenId) {
            this.rFoodCanteenId = rFoodCanteenId;
        }

        public Object getRFoodCanteenName() {
            return rFoodCanteenName;
        }

        public void setRFoodCanteenName(Object rFoodCanteenName) {
            this.rFoodCanteenName = rFoodCanteenName;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public Integer getMarkerId() {
            return markerId;
        }

        public void setMarkerId(Integer markerId) {
            this.markerId = markerId;
        }

        public Object getMarkerName() {
            return markerName;
        }

        public void setMarkerName(Object markerName) {
            this.markerName = markerName;
        }

        public String getRFoodPrice() {
            return rFoodPrice;
        }

        public void setRFoodPrice(String rFoodPrice) {
            this.rFoodPrice = rFoodPrice;
        }

        public String getRFoodPic() {
            return rFoodPic;
        }

        public void setRFoodPic(String rFoodPic) {
            this.rFoodPic = rFoodPic;
        }

        public Object getYears() {
            return years;
        }

        public void setYears(Object years) {
            this.years = years;
        }
    }
}
