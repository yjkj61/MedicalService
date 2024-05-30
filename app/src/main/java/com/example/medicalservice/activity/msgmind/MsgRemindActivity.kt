package com.example.medicalservice.activity.msgmind

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicalservice.baseFile.BaseActivity
import com.example.medicalservice.bean.MessageMindEntity

import com.example.medicalservice.recycleAdapter.MsgMindListAdapter
import com.example.medicalservice.tools.API
import com.example.medicalservice.tools.OkHttpUtil
import com.example.medicalservice.tools.clickNoRepeat2
import com.example.medicalservice.tools.registerTextWatcher
import com.example.medicalservice.tools.toast
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class MsgRemindActivity : BaseActivity<com.example.medicalservice.databinding.ActivityMsgRemindBinding>() {

    private val adapter by lazy {
        MsgMindListAdapter()
    }

    override fun onResume() {
        super.onResume()
        viewBinding.searchEdit.addTextChangedListener(textWatcher)
        requestSearch()
        viewBinding.igBack.setOnClickListener(View.OnClickListener { finish() })
    }

    private val textWatcher = registerTextWatcher{
        onTextChanged { s, start, before, count ->
            if(count == 0){
                requestSearch()
            }
        }
        afterTextChanged {
            if(it.toString().isNotEmpty()){
                clickNoRepeat2 {
                    requestSearch(title = it.toString())
                }

            }


        }
    }

    fun launchSearch(view : View){
        val keyWord = viewBinding.searchEdit.text.toString()
        if(keyWord.isEmpty()){
            toast("请输入关键字")
            return
        }
        requestSearch(title = keyWord)
    }


    private fun requestSearch(id : String = "",title : String = ""){
        val jsonObject = JSONObject()
        jsonObject.put("id", id)
        jsonObject.put("title", title)
        OkHttpUtil.getInstance().doPost(API.requestMsgMindList,jsonObject.toString(),object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    response.body?.let {
                        kotlin.runCatching {
                            val messageMindEntity = Gson().fromJson(it.string(), MessageMindEntity::class.java)
                            if(messageMindEntity.code == 200 && messageMindEntity.rows.isNotEmpty()){
                                updateMsgMindList(messageMindEntity)
                            }else{
                                //toast("暂无数据")
                            }
                        }

                    }
                }

            }

        })
    }

    private fun updateMsgMindList(messageMindEntity: MessageMindEntity) {
        val rows = messageMindEntity.rows
        viewBinding.msgMindRv.layoutManager = LinearLayoutManager(this)
        viewBinding.msgMindRv.adapter = adapter
        adapter.setData(rows)
        adapter.OnItemClickCallback {
            val intent = Intent(this,MsgMindDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable(MSG_DETAIL_ROW,it)
            intent.putExtras(bundle)
            startActivity(intent)
        }

    }

    companion object{
        const val MSG_DETAIL_ROW = "msg_detail"
    }
}