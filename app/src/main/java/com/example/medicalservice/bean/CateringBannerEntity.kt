package com.example.medicalservice.bean

data class CateringBannerEntity(
    var code: Int,
    var msg: String,
    var rows: List<CaterRow>,
    var total: Int
)

data class CaterRow(
    var adPosition: String,
    var content: Any,
    var createBy: Any,
    var createTime: Any,
    var id: Int,
    var image: String,
    var imageRemake: Any,
    var markerId: Long,
    var markerName: Any,
    var remark: Any,
    var updateBy: Any,
    var updateTime: Any,
    var userId: Any,
    var userType: String
)