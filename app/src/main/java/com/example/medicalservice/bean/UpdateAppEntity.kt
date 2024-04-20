package com.example.medicalservice.bean

import java.io.Serializable

data class UpdateAppEntity(
    var code: Int = 0,
    var `data`: Data,
    var msg: String
)

data class Data(
    var apkFile: String,
    var createBy: Any,
    var createTime: String,
    var id: Int,
    var msg: String,
    var releaseTime: String,
    var remark: Any,
    var status: String,
    var type: String,
    var updateBy: Any,
    var updateTime: Any,
    var version: String
): Serializable