package com.example.medicalservice.bean

import java.io.Serializable

data class HomeBannerEntity(
    var code: Int,
    var msg: String,
    var rows: List<Row>,
    var total: Int
)

data class Row(
    var adPosition: String,
    var articleCreatorId: Any,
    var articleCreatorName: String,
    var articleImage: String,
    var articleStatus: Any,
    var content: String,
    var createBy: Any,
    var createTime: Any,
    var distributedCommunity: Any,
    var endTime: String,
    var id: Int,
    var indexOne: String,
    var markerId: Long,
    var markerName: Any,
    var ownerId: Any,
    var publishTime: String,
    var readCount: Int,
    var readsNumber: Any,
    var remark: Any,
    var status: String,
    var title: String,
    var updateBy: Any,
    var updateTime: Any,
    var userId: Any,
    var userType: String
):Serializable