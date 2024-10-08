package com.example.medicalservice.bean

data class HomeAdBannerEntity(
    var code: Int,
    var msg: String,
    var rows: List<ADRow>,
    var total: Int
)

data class ADRow(
    var adContent: Any,
    var adContentText: Any,
    var adData: String,
    var adFormat: Any,
    var adGif: Any,
    var adImage: Any,
    var adName: String,
    var adPosition: String,
    var adVideo: Any,
    var advertiserUid: Int,
    var billingType: String,
    var coverImage: String,
    var createBy: Any,
    var createTime: Any,
    var endTime: String,
    var firstImpression: Any,
    var geographicTargeting: Any,
    var hyperlink: Any,
    var id: Int,
    var jumpType: Any,
    var markerId: Long,
    var markerName: Any,
    var progress: Int,
    var putInWeight: Int,
    var redirectType: Any,
    var remark: Any,
    var salesVolume: Int,
    var salesVolumeSold: Int,
    var salesVolumeUnsold: Int,
    var startTime: String,
    var status: Int,
    var updateBy: Any,
    var updateTime: Any,
    var userId: Any,
    var userType: String
)