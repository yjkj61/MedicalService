package com.example.medicalservice.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @description 健康状态信息Bean
 * @author: Lenovo
 * @date: 2024/5/17
 */
public class HealthInfoBean {

    @SerializedName("msg")
    private String msg;
    @SerializedName("code")
    private Integer code;
    @SerializedName("data")
    private DataDTO data;

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

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        @SerializedName("createBy")
        private Object createBy;
        @SerializedName("createTime")
        private String createTime;
        @SerializedName("updateBy")
        private Object updateBy;
        @SerializedName("updateTime")
        private Object updateTime;
        @SerializedName("remark")
        private Object remark;
        @SerializedName("ownerId")
        private Integer ownerId;
        @SerializedName("ownerUsername")
        private Object ownerUsername;
        @SerializedName("ownerAccountName")
        private Object ownerAccountName;
        @SerializedName("ownerPassword")
        private Object ownerPassword;
        @SerializedName("ownerName")
        private String ownerName;
        @SerializedName("ownerPic")
        private String ownerPic;
        @SerializedName("ownerSex")
        private String ownerSex;
        @SerializedName("ownerAge")
        private Integer ownerAge;
        @SerializedName("ownerPhone")
        private String ownerPhone;
        @SerializedName("ownerCarId")
        private String ownerCarId;
        @SerializedName("ownerCarNumber")
        private String ownerCarNumber;
        @SerializedName("ownerCardNumber")
        private String ownerCardNumber;
        @SerializedName("ownerArea")
        private String ownerArea;
        @SerializedName("ownerAddress")
        private String ownerAddress;
        @SerializedName("ownerCommunity")
        private String ownerCommunity;
        @SerializedName("ownerBuilding")
        private String ownerBuilding;
        @SerializedName("ownerUnit")
        private String ownerUnit;
        @SerializedName("ownerFloor")
        private String ownerFloor;
        @SerializedName("ownerRoomNum")
        private String ownerRoomNum;
        @SerializedName("ownerBedNum")
        private String ownerBedNum;
        @SerializedName("ownerRemainMoney")
        private Integer ownerRemainMoney;
        @SerializedName("ownerAttribute")
        private Object ownerAttribute;
        @SerializedName("ownerBehavior")
        private Object ownerBehavior;
        @SerializedName("ownerStatus")
        private Integer ownerStatus;
        @SerializedName("ownerFamilyMemberId")
        private String ownerFamilyMemberId;
        @SerializedName("ownerFamilyMemberName")
        private String ownerFamilyMemberName;
        @SerializedName("ownerFile")
        private Object ownerFile;
        @SerializedName("ownerRole")
        private String ownerRole;
        @SerializedName("ownerRoleName")
        private Object ownerRoleName;
        @SerializedName("ownerVip")
        private Integer ownerVip;
        @SerializedName("ownerVipGrowthValue")
        private Object ownerVipGrowthValue;
        @SerializedName("ownerVipStartTime")
        private String ownerVipStartTime;
        @SerializedName("ownerVipEndTime")
        private String ownerVipEndTime;
        @SerializedName("ownerLiving")
        private String ownerLiving;
        @SerializedName("ownerIllness")
        private String ownerIllness;
        @SerializedName("ownerBirth")
        private String ownerBirth;
        @SerializedName("ownerXuexing")
        private Integer ownerXuexing;
        @SerializedName("ownerCanji")
        private String ownerCanji;
        @SerializedName("ownerGuomin")
        private String ownerGuomin;
        @SerializedName("ownerGuominOther")
        private Object ownerGuominOther;
        @SerializedName("ownerZongjiao")
        private Object ownerZongjiao;
        @SerializedName("ownerWenhua")
        private Object ownerWenhua;
        @SerializedName("ownerHunyin")
        private Object ownerHunyin;
        @SerializedName("ownerMianmao")
        private Object ownerMianmao;
        @SerializedName("ownerTuixiu")
        private Object ownerTuixiu;
        @SerializedName("ownerYuanzhiwu")
        private Object ownerYuanzhiwu;
        @SerializedName("ownerLaiyuan")
        private String ownerLaiyuan;
        @SerializedName("ownerYiliao")
        private String ownerYiliao;
        @SerializedName("ownerBeizhu")
        private Object ownerBeizhu;
        @SerializedName("ownerHeight")
        private Object ownerHeight;
        @SerializedName("ownerWeight")
        private Object ownerWeight;
        @SerializedName("ownerNursingId")
        private Object ownerNursingId;
        @SerializedName("ownerNursingName")
        private String ownerNursingName;
        @SerializedName("ownerNursingPhone")
        private String ownerNursingPhone;
        @SerializedName("ownerNursingIdTow")
        private Object ownerNursingIdTow;
        @SerializedName("ownerNursingPhoneTow")
        private Object ownerNursingPhoneTow;
        @SerializedName("ownerNursingNameTow")
        private Object ownerNursingNameTow;
        @SerializedName("ownerDoctorId")
        private Object ownerDoctorId;
        @SerializedName("ownerDoctorName")
        private Object ownerDoctorName;
        @SerializedName("ownerDoctorPhone")
        private Object ownerDoctorPhone;
        @SerializedName("ownerSelfAssess")
        private Object ownerSelfAssess;
        @SerializedName("ownerNurseAssess")
        private Object ownerNurseAssess;
        @SerializedName("markerId")
        private Long markerId;
        @SerializedName("markerName")
        private Object markerName;
        @SerializedName("userId")
        private Long userId;
        @SerializedName("userType")
        private String userType;
        @SerializedName("ownerEntryTime")
        private Object ownerEntryTime;
        @SerializedName("ownerMonthPrice")
        private Integer ownerMonthPrice;
        @SerializedName("ownerSupervisorDoctorId")
        private Object ownerSupervisorDoctorId;
        @SerializedName("ownerSupervisorDoctorName")
        private String ownerSupervisorDoctorName;
        @SerializedName("ownerSupervisorDoctorPhone")
        private String ownerSupervisorDoctorPhone;
        @SerializedName("ownerManagerId")
        private Object ownerManagerId;
        @SerializedName("ownerManagerName")
        private String ownerManagerName;
        @SerializedName("ownerManagerPhone")
        private String ownerManagerPhone;
        @SerializedName("registrationId")
        private Object registrationId;
        @SerializedName("ownerNurseId")
        private Object ownerNurseId;
        @SerializedName("ownerNurse")
        private String ownerNurse;
        @SerializedName("ownerNursePhone")
        private String ownerNursePhone;
        @SerializedName("ownerNurseIdTow")
        private Object ownerNurseIdTow;
        @SerializedName("ownerNurseTow")
        private Object ownerNurseTow;
        @SerializedName("ownerNursePhoneTow")
        private Object ownerNursePhoneTow;
        @SerializedName("minAge")
        private Object minAge;
        @SerializedName("minMoney")
        private Object minMoney;
        @SerializedName("province")
        private String province;
        @SerializedName("city")
        private String city;
        @SerializedName("area")
        private String area;
        @SerializedName("dangerType")
        private String dangerType;
        @SerializedName("physiologicalState")
        private String physiologicalState;

        public Object getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Object createBy) {
            this.createBy = createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
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

        public Integer getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(Integer ownerId) {
            this.ownerId = ownerId;
        }

        public Object getOwnerUsername() {
            return ownerUsername;
        }

        public void setOwnerUsername(Object ownerUsername) {
            this.ownerUsername = ownerUsername;
        }

        public Object getOwnerAccountName() {
            return ownerAccountName;
        }

        public void setOwnerAccountName(Object ownerAccountName) {
            this.ownerAccountName = ownerAccountName;
        }

        public Object getOwnerPassword() {
            return ownerPassword;
        }

        public void setOwnerPassword(Object ownerPassword) {
            this.ownerPassword = ownerPassword;
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

        public Integer getOwnerAge() {
            return ownerAge;
        }

        public void setOwnerAge(Integer ownerAge) {
            this.ownerAge = ownerAge;
        }

        public String getOwnerPhone() {
            return ownerPhone;
        }

        public void setOwnerPhone(String ownerPhone) {
            this.ownerPhone = ownerPhone;
        }

        public String getOwnerCarId() {
            return ownerCarId;
        }

        public void setOwnerCarId(String ownerCarId) {
            this.ownerCarId = ownerCarId;
        }

        public String getOwnerCarNumber() {
            return ownerCarNumber;
        }

        public void setOwnerCarNumber(String ownerCarNumber) {
            this.ownerCarNumber = ownerCarNumber;
        }

        public String getOwnerCardNumber() {
            return ownerCardNumber;
        }

        public void setOwnerCardNumber(String ownerCardNumber) {
            this.ownerCardNumber = ownerCardNumber;
        }

        public String getOwnerArea() {
            return ownerArea;
        }

        public void setOwnerArea(String ownerArea) {
            this.ownerArea = ownerArea;
        }

        public String getOwnerAddress() {
            return ownerAddress;
        }

        public void setOwnerAddress(String ownerAddress) {
            this.ownerAddress = ownerAddress;
        }

        public String getOwnerCommunity() {
            return ownerCommunity;
        }

        public void setOwnerCommunity(String ownerCommunity) {
            this.ownerCommunity = ownerCommunity;
        }

        public String getOwnerBuilding() {
            return ownerBuilding;
        }

        public void setOwnerBuilding(String ownerBuilding) {
            this.ownerBuilding = ownerBuilding;
        }

        public String getOwnerUnit() {
            return ownerUnit;
        }

        public void setOwnerUnit(String ownerUnit) {
            this.ownerUnit = ownerUnit;
        }

        public String getOwnerFloor() {
            return ownerFloor;
        }

        public void setOwnerFloor(String ownerFloor) {
            this.ownerFloor = ownerFloor;
        }

        public String getOwnerRoomNum() {
            return ownerRoomNum;
        }

        public void setOwnerRoomNum(String ownerRoomNum) {
            this.ownerRoomNum = ownerRoomNum;
        }

        public String getOwnerBedNum() {
            return ownerBedNum;
        }

        public void setOwnerBedNum(String ownerBedNum) {
            this.ownerBedNum = ownerBedNum;
        }

        public Integer getOwnerRemainMoney() {
            return ownerRemainMoney;
        }

        public void setOwnerRemainMoney(Integer ownerRemainMoney) {
            this.ownerRemainMoney = ownerRemainMoney;
        }

        public Object getOwnerAttribute() {
            return ownerAttribute;
        }

        public void setOwnerAttribute(Object ownerAttribute) {
            this.ownerAttribute = ownerAttribute;
        }

        public Object getOwnerBehavior() {
            return ownerBehavior;
        }

        public void setOwnerBehavior(Object ownerBehavior) {
            this.ownerBehavior = ownerBehavior;
        }

        public Integer getOwnerStatus() {
            return ownerStatus;
        }

        public void setOwnerStatus(Integer ownerStatus) {
            this.ownerStatus = ownerStatus;
        }

        public String getOwnerFamilyMemberId() {
            return ownerFamilyMemberId;
        }

        public void setOwnerFamilyMemberId(String ownerFamilyMemberId) {
            this.ownerFamilyMemberId = ownerFamilyMemberId;
        }

        public String getOwnerFamilyMemberName() {
            return ownerFamilyMemberName;
        }

        public void setOwnerFamilyMemberName(String ownerFamilyMemberName) {
            this.ownerFamilyMemberName = ownerFamilyMemberName;
        }

        public Object getOwnerFile() {
            return ownerFile;
        }

        public void setOwnerFile(Object ownerFile) {
            this.ownerFile = ownerFile;
        }

        public String getOwnerRole() {
            return ownerRole;
        }

        public void setOwnerRole(String ownerRole) {
            this.ownerRole = ownerRole;
        }

        public Object getOwnerRoleName() {
            return ownerRoleName;
        }

        public void setOwnerRoleName(Object ownerRoleName) {
            this.ownerRoleName = ownerRoleName;
        }

        public Integer getOwnerVip() {
            return ownerVip;
        }

        public void setOwnerVip(Integer ownerVip) {
            this.ownerVip = ownerVip;
        }

        public Object getOwnerVipGrowthValue() {
            return ownerVipGrowthValue;
        }

        public void setOwnerVipGrowthValue(Object ownerVipGrowthValue) {
            this.ownerVipGrowthValue = ownerVipGrowthValue;
        }

        public String getOwnerVipStartTime() {
            return ownerVipStartTime;
        }

        public void setOwnerVipStartTime(String ownerVipStartTime) {
            this.ownerVipStartTime = ownerVipStartTime;
        }

        public String getOwnerVipEndTime() {
            return ownerVipEndTime;
        }

        public void setOwnerVipEndTime(String ownerVipEndTime) {
            this.ownerVipEndTime = ownerVipEndTime;
        }

        public String getOwnerLiving() {
            return ownerLiving;
        }

        public void setOwnerLiving(String ownerLiving) {
            this.ownerLiving = ownerLiving;
        }

        public String getOwnerIllness() {
            return ownerIllness;
        }

        public void setOwnerIllness(String ownerIllness) {
            this.ownerIllness = ownerIllness;
        }

        public String getOwnerBirth() {
            return ownerBirth;
        }

        public void setOwnerBirth(String ownerBirth) {
            this.ownerBirth = ownerBirth;
        }

        public Integer getOwnerXuexing() {
            return ownerXuexing;
        }

        public void setOwnerXuexing(Integer ownerXuexing) {
            this.ownerXuexing = ownerXuexing;
        }

        public String getOwnerCanji() {
            return ownerCanji;
        }

        public void setOwnerCanji(String ownerCanji) {
            this.ownerCanji = ownerCanji;
        }

        public String getOwnerGuomin() {
            return ownerGuomin;
        }

        public void setOwnerGuomin(String ownerGuomin) {
            this.ownerGuomin = ownerGuomin;
        }

        public Object getOwnerGuominOther() {
            return ownerGuominOther;
        }

        public void setOwnerGuominOther(Object ownerGuominOther) {
            this.ownerGuominOther = ownerGuominOther;
        }

        public Object getOwnerZongjiao() {
            return ownerZongjiao;
        }

        public void setOwnerZongjiao(Object ownerZongjiao) {
            this.ownerZongjiao = ownerZongjiao;
        }

        public Object getOwnerWenhua() {
            return ownerWenhua;
        }

        public void setOwnerWenhua(Object ownerWenhua) {
            this.ownerWenhua = ownerWenhua;
        }

        public Object getOwnerHunyin() {
            return ownerHunyin;
        }

        public void setOwnerHunyin(Object ownerHunyin) {
            this.ownerHunyin = ownerHunyin;
        }

        public Object getOwnerMianmao() {
            return ownerMianmao;
        }

        public void setOwnerMianmao(Object ownerMianmao) {
            this.ownerMianmao = ownerMianmao;
        }

        public Object getOwnerTuixiu() {
            return ownerTuixiu;
        }

        public void setOwnerTuixiu(Object ownerTuixiu) {
            this.ownerTuixiu = ownerTuixiu;
        }

        public Object getOwnerYuanzhiwu() {
            return ownerYuanzhiwu;
        }

        public void setOwnerYuanzhiwu(Object ownerYuanzhiwu) {
            this.ownerYuanzhiwu = ownerYuanzhiwu;
        }

        public String getOwnerLaiyuan() {
            return ownerLaiyuan;
        }

        public void setOwnerLaiyuan(String ownerLaiyuan) {
            this.ownerLaiyuan = ownerLaiyuan;
        }

        public String getOwnerYiliao() {
            return ownerYiliao;
        }

        public void setOwnerYiliao(String ownerYiliao) {
            this.ownerYiliao = ownerYiliao;
        }

        public Object getOwnerBeizhu() {
            return ownerBeizhu;
        }

        public void setOwnerBeizhu(Object ownerBeizhu) {
            this.ownerBeizhu = ownerBeizhu;
        }

        public Object getOwnerHeight() {
            return ownerHeight;
        }

        public void setOwnerHeight(Object ownerHeight) {
            this.ownerHeight = ownerHeight;
        }

        public Object getOwnerWeight() {
            return ownerWeight;
        }

        public void setOwnerWeight(Object ownerWeight) {
            this.ownerWeight = ownerWeight;
        }

        public Object getOwnerNursingId() {
            return ownerNursingId;
        }

        public void setOwnerNursingId(Object ownerNursingId) {
            this.ownerNursingId = ownerNursingId;
        }

        public String getOwnerNursingName() {
            return ownerNursingName;
        }

        public void setOwnerNursingName(String ownerNursingName) {
            this.ownerNursingName = ownerNursingName;
        }

        public String getOwnerNursingPhone() {
            return ownerNursingPhone;
        }

        public void setOwnerNursingPhone(String ownerNursingPhone) {
            this.ownerNursingPhone = ownerNursingPhone;
        }

        public Object getOwnerNursingIdTow() {
            return ownerNursingIdTow;
        }

        public void setOwnerNursingIdTow(Object ownerNursingIdTow) {
            this.ownerNursingIdTow = ownerNursingIdTow;
        }

        public Object getOwnerNursingPhoneTow() {
            return ownerNursingPhoneTow;
        }

        public void setOwnerNursingPhoneTow(Object ownerNursingPhoneTow) {
            this.ownerNursingPhoneTow = ownerNursingPhoneTow;
        }

        public Object getOwnerNursingNameTow() {
            return ownerNursingNameTow;
        }

        public void setOwnerNursingNameTow(Object ownerNursingNameTow) {
            this.ownerNursingNameTow = ownerNursingNameTow;
        }

        public Object getOwnerDoctorId() {
            return ownerDoctorId;
        }

        public void setOwnerDoctorId(Object ownerDoctorId) {
            this.ownerDoctorId = ownerDoctorId;
        }

        public Object getOwnerDoctorName() {
            return ownerDoctorName;
        }

        public void setOwnerDoctorName(Object ownerDoctorName) {
            this.ownerDoctorName = ownerDoctorName;
        }

        public Object getOwnerDoctorPhone() {
            return ownerDoctorPhone;
        }

        public void setOwnerDoctorPhone(Object ownerDoctorPhone) {
            this.ownerDoctorPhone = ownerDoctorPhone;
        }

        public Object getOwnerSelfAssess() {
            return ownerSelfAssess;
        }

        public void setOwnerSelfAssess(Object ownerSelfAssess) {
            this.ownerSelfAssess = ownerSelfAssess;
        }

        public Object getOwnerNurseAssess() {
            return ownerNurseAssess;
        }

        public void setOwnerNurseAssess(Object ownerNurseAssess) {
            this.ownerNurseAssess = ownerNurseAssess;
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

        public Object getOwnerEntryTime() {
            return ownerEntryTime;
        }

        public void setOwnerEntryTime(Object ownerEntryTime) {
            this.ownerEntryTime = ownerEntryTime;
        }

        public Integer getOwnerMonthPrice() {
            return ownerMonthPrice;
        }

        public void setOwnerMonthPrice(Integer ownerMonthPrice) {
            this.ownerMonthPrice = ownerMonthPrice;
        }

        public Object getOwnerSupervisorDoctorId() {
            return ownerSupervisorDoctorId;
        }

        public void setOwnerSupervisorDoctorId(Object ownerSupervisorDoctorId) {
            this.ownerSupervisorDoctorId = ownerSupervisorDoctorId;
        }

        public String getOwnerSupervisorDoctorName() {
            return ownerSupervisorDoctorName;
        }

        public void setOwnerSupervisorDoctorName(String ownerSupervisorDoctorName) {
            this.ownerSupervisorDoctorName = ownerSupervisorDoctorName;
        }

        public String getOwnerSupervisorDoctorPhone() {
            return ownerSupervisorDoctorPhone;
        }

        public void setOwnerSupervisorDoctorPhone(String ownerSupervisorDoctorPhone) {
            this.ownerSupervisorDoctorPhone = ownerSupervisorDoctorPhone;
        }

        public Object getOwnerManagerId() {
            return ownerManagerId;
        }

        public void setOwnerManagerId(Object ownerManagerId) {
            this.ownerManagerId = ownerManagerId;
        }

        public String getOwnerManagerName() {
            return ownerManagerName;
        }

        public void setOwnerManagerName(String ownerManagerName) {
            this.ownerManagerName = ownerManagerName;
        }

        public String getOwnerManagerPhone() {
            return ownerManagerPhone;
        }

        public void setOwnerManagerPhone(String ownerManagerPhone) {
            this.ownerManagerPhone = ownerManagerPhone;
        }

        public Object getRegistrationId() {
            return registrationId;
        }

        public void setRegistrationId(Object registrationId) {
            this.registrationId = registrationId;
        }

        public Object getOwnerNurseId() {
            return ownerNurseId;
        }

        public void setOwnerNurseId(Object ownerNurseId) {
            this.ownerNurseId = ownerNurseId;
        }

        public String getOwnerNurse() {
            return ownerNurse;
        }

        public void setOwnerNurse(String ownerNurse) {
            this.ownerNurse = ownerNurse;
        }

        public String getOwnerNursePhone() {
            return ownerNursePhone;
        }

        public void setOwnerNursePhone(String ownerNursePhone) {
            this.ownerNursePhone = ownerNursePhone;
        }

        public Object getOwnerNurseIdTow() {
            return ownerNurseIdTow;
        }

        public void setOwnerNurseIdTow(Object ownerNurseIdTow) {
            this.ownerNurseIdTow = ownerNurseIdTow;
        }

        public Object getOwnerNurseTow() {
            return ownerNurseTow;
        }

        public void setOwnerNurseTow(Object ownerNurseTow) {
            this.ownerNurseTow = ownerNurseTow;
        }

        public Object getOwnerNursePhoneTow() {
            return ownerNursePhoneTow;
        }

        public void setOwnerNursePhoneTow(Object ownerNursePhoneTow) {
            this.ownerNursePhoneTow = ownerNursePhoneTow;
        }

        public Object getMinAge() {
            return minAge;
        }

        public void setMinAge(Object minAge) {
            this.minAge = minAge;
        }

        public Object getMinMoney() {
            return minMoney;
        }

        public void setMinMoney(Object minMoney) {
            this.minMoney = minMoney;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getDangerType() {
            return dangerType;
        }

        public void setDangerType(String dangerType) {
            this.dangerType = dangerType;
        }

        public String getPhysiologicalState() {
            return physiologicalState;
        }

        public void setPhysiologicalState(String physiologicalState) {
            this.physiologicalState = physiologicalState;
        }
    }
}
