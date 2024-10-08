package com.example.medicalservice.activity.active

import android.text.Html
import com.example.medicalservice.MyApplication
import com.example.medicalservice.baseFile.BaseActivity
import com.example.medicalservice.bean.ApplyActiveEntity
import com.example.medicalservice.bean.Row
import com.example.medicalservice.databinding.ActivityActiveDetailBinding
import com.example.medicalservice.tools.API
import com.example.medicalservice.tools.OkHttpUtil
import com.example.medicalservice.tools.toast
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class ActiveDetailActivity : BaseActivity<ActivityActiveDetailBinding>() {

    override fun onResume() {
        super.onResume()
        hideStatusBar()
        intent.extras?.let {
            val row = it.getSerializable(ACTIVE_DETAIL_KEY) as Row
            row.apply {
                viewBinding.apply {
                    activeTitleText.text = title
                    publishTimeText.text = publishTime
                    publishAuthorText.text = articleCreatorName
                    contentText.text = Html.fromHtml(content)
                }
            }

            viewBinding.applyButton.setOnClickListener {
                val jsonObject = JSONObject()
                jsonObject.put("activityId",row.id)
                OkHttpUtil.getInstance().doPost(API.applyActiveUrl,jsonObject.toString(),
                    object : Callback {
                        override fun onFailure(call: Call, e: IOException) {

                        }

                        override fun onResponse(call: Call, response: Response) {
                            runOnUiThread {
                                response.body?.let {
                                    val activeEntity = Gson().fromJson(it.string(), ApplyActiveEntity::class.java)
                                    when(activeEntity.data){
                                        1 ->{
                                            toast("报名成功")
                                        }
                                        2 ->{
                                            toast("已报名不可重复报名")
                                        }
                                        -1 ->{
                                            toast("报名人数已达上限")
                                        }
                                        else ->{
                                            toast("报名失败请联系管理员")
                                        }
                                    }
                                }
                            }
                        }

                    })
            }
        }
    }




    companion object{
        const val ACTIVE_DETAIL_KEY = "active_detail"
    }

}