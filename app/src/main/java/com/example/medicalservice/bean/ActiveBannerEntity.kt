package com.example.medicalservice.bean

data class ActiveBannerEntity(
    var code: Int,
    var msg: String,
    var rows: List<ActiveRow>,
    var total: Int
)

data class ActiveRow(
    var activityImage: Any,
    var activitySignCount: Int,
    var activitySignNotCount: Int,
    var activityTime: String,
    var activityType: String,
    var adPosition: String,
    var content: String,
    var createBy: Any,
    var createTime: Any,
    var endTime: String,
    var id: Int,
    var indexOne: String,
    var isLimited: String,
    var limitCount: Int,
    var location: String,
    var markerId: Int,
    var markerName: Any,
    var publishTime: String,
    var readCount: Int,
    var readsNumber: Any,
    var remark: Any,
    var signupCount: Int,
    var signupTime: String,
    var status: String,
    var title: String,
    var updateBy: Any,
    var updateTime: Any,
    var userId: Any,
    var userType: String
)