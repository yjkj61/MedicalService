package com.example.medicalservice.bean

data class PostHealthRecordsBean(
    var msg: String? = null,
    val code : Int = 0,
    var data : RecordsData ? = null
)

data class RecordsData(
    var id: String = "",
    var userId: String = "",
    var ownerId: String = "",
    var ownerName: String = "",
    var ownerSex: String? = null,
    var ownerAge: String = "",
    var ownerBedNum: String = "",
    var personalHistory: String = "",
    var familyHistory: String? = null,
    var behaviorCustom: String? = null,
    var allergyHistory: String? = null,
    var takeMedicine: String? = null,
    var createTime: String? = null,
    var createBy: String? = null,
    var updateTime: String? = null,
    var updateBy: String? = null,
)
