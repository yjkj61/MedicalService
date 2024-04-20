package com.example.medicalservice.tools.utils

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.util.Log
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

/**
 * 身份证号提取生日
 */
fun String.extractBirthDate():String{
    val birthDateString = this.substring(6, 14)
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val birthDate = LocalDate.parse(birthDateString, formatter)
    return birthDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}

@SuppressLint("SimpleDateFormat")
fun Date.getTimeFormat(format : String = "yyyy-MM-dd"):String{
    val format = SimpleDateFormat(format)
    return format.format(this)
}
