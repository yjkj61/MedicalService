package com.example.medicalservice.bean;

import java.util.List;

public class FamilyBean {

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
        private Object createBy;
        private Object createTime;
        private Object updateBy;
        private Object updateTime;
        private Object remark;
        private String familyMemberId;
        private int ownerId;
        private String familyMemberName;
        private String familyMemberSex;
        private String familyMemberRelation;
        private String familyMemberPhone;
        private String familyMemberEcontact;
        private Object familyMemberEconphone;
        private String familyMemberRandom;
        private Object familyMemberAddress;
        private Object familyMemberWorkplace;
        private String ownerName;
        private String ownerSex;
        private String ownerAddress;
        private String ownerPic;

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

        public String getFamilyMemberId() {
            return familyMemberId;
        }

        public void setFamilyMemberId(String familyMemberId) {
            this.familyMemberId = familyMemberId;
        }

        public int getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(int ownerId) {
            this.ownerId = ownerId;
        }

        public String getFamilyMemberName() {
            return familyMemberName;
        }

        public void setFamilyMemberName(String familyMemberName) {
            this.familyMemberName = familyMemberName;
        }

        public String getFamilyMemberSex() {
            return familyMemberSex;
        }

        public void setFamilyMemberSex(String familyMemberSex) {
            this.familyMemberSex = familyMemberSex;
        }

        public String getFamilyMemberRelation() {
            return familyMemberRelation;
        }

        public void setFamilyMemberRelation(String familyMemberRelation) {
            this.familyMemberRelation = familyMemberRelation;
        }

        public String getFamilyMemberPhone() {
            return familyMemberPhone;
        }

        public void setFamilyMemberPhone(String familyMemberPhone) {
            this.familyMemberPhone = familyMemberPhone;
        }

        public String getFamilyMemberEcontact() {
            return familyMemberEcontact;
        }

        public void setFamilyMemberEcontact(String familyMemberEcontact) {
            this.familyMemberEcontact = familyMemberEcontact;
        }

        public Object getFamilyMemberEconphone() {
            return familyMemberEconphone;
        }

        public void setFamilyMemberEconphone(Object familyMemberEconphone) {
            this.familyMemberEconphone = familyMemberEconphone;
        }

        public String getFamilyMemberRandom() {
            return familyMemberRandom;
        }

        public void setFamilyMemberRandom(String familyMemberRandom) {
            this.familyMemberRandom = familyMemberRandom;
        }

        public Object getFamilyMemberAddress() {
            return familyMemberAddress;
        }

        public void setFamilyMemberAddress(Object familyMemberAddress) {
            this.familyMemberAddress = familyMemberAddress;
        }

        public Object getFamilyMemberWorkplace() {
            return familyMemberWorkplace;
        }

        public void setFamilyMemberWorkplace(Object familyMemberWorkplace) {
            this.familyMemberWorkplace = familyMemberWorkplace;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public String getOwnerSex() {
            return ownerSex;
        }

        public void setOwnerSex(String ownerSex) {
            this.ownerSex = ownerSex;
        }

        public String getOwnerAddress() {
            return ownerAddress;
        }

        public void setOwnerAddress(String ownerAddress) {
            this.ownerAddress = ownerAddress;
        }

        public String getOwnerPic() {
            return ownerPic;
        }

        public void setOwnerPic(String ownerPic) {
            this.ownerPic = ownerPic;
        }
    }
}