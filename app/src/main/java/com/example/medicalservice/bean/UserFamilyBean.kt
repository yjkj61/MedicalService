package com.example.medicalservice.bean

data class UserFamilyBean(
    var code: Int,
    var `data`: List<DataXX>,
    var msg: String
)

data class DataXX(
    var createBy: Any,
    var createTime: Any,
    var familyMemberAddress: String,
    var familyMemberEconphone: String,
    var familyMemberEcontact: String,
    var familyMemberId: Int,
    var familyMemberName: String,
    var familyMemberPhone: String,
    var familyMemberRandom: String,
    var familyMemberRelation: String,
    var familyMemberSex: String,
    var familyMemberWorkplace: String,
    var markerId: Int,
    var markerName: Any,
    var ownerAddress: String,
    var ownerId: Int,
    var ownerName: String,
    var ownerPic: String,
    var ownerSex: String,
    var remark: Any,
    var updateBy: Any,
    var updateTime: Any,
    var userId: Any,
    var userType: Any
)