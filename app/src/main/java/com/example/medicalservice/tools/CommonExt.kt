package com.example.medicalservice.tools

import android.util.Log
import com.example.medicalservice.MyApplication
import com.example.medicalservice.recycleAdapter.MedicalHistoryAdapter

fun toast(msg : String){
    ToastUtil.showToast(msg,MyApplication.getInstance().applicationContext)
}


fun String.log(tag : String = "Okhttp"){
    Log.d(tag, this)
}

/**
 * 防止快速点击
 */
var mLastTime = 0L
fun clickNoRepeat2(interval: Long = 500 , onClick: () -> Unit){
    val currentTime = System.currentTimeMillis()
    if(mLastTime != 0L && (currentTime - mLastTime < interval)){
        return
    }
    mLastTime = currentTime
    onClick()
}

fun String?.ifNull(block : () -> String):String{
    return this ?: block()
}


fun Int.toMedicalStr():String{
   return when(this){
        0->{
            "高血压"
        }
        1->{
            "糖尿病"
        }
        2->{
            "高血脂"
        }
        3->{
            "冠心病"
        }
        4->{
            "脑卒中"
        }
        5->{
            "慢性肺病"
        }
        6->{
            "癌症"
        }
        7->{
            "阿兹海默症"
        }
       else->{
           ""
       }
    }
}

fun List<String>.join2String():String{
    val s = this.joinToString(",")
    return s.ifBlank {
        ""
    }
}