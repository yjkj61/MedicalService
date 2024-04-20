package com.example.medicalservice.activity.active

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicalservice.baseFile.BaseActivity
import com.example.medicalservice.bean.HomeBannerEntity
import com.example.medicalservice.bean.Row
import com.example.medicalservice.recycleAdapter.ActiveListAdapter

import com.example.medicalservice.recycleAdapter.HomeBannerAdapter
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

class ActiveListActivity : BaseActivity<com.example.medicalservice.databinding.ActivityActiveListBinding>() {

    private val activeAdapter by lazy {
        ActiveListAdapter()
    }

    private var adPosition : String? = ""
    override fun onResume() {
        super.onResume()
        hideStatusBar()
        adPosition = intent.getStringExtra("msg")
        getListData()

        activeAdapter.OnItemClickCallback {
            val bundle = Bundle()
            bundle.putSerializable(ActiveDetailActivity.ACTIVE_DETAIL_KEY,it)
            go(ActiveDetailActivity::class.java,bundle)
        }

        viewBinding.searchEdit.addTextChangedListener(registerTextWatcher {
            onTextChanged { s, start, before, count ->
                if(count == 0){
                    getListData()
                }
            }

            afterTextChanged {
                if(it.toString().isNotEmpty()){
                    clickNoRepeat2 {
                        getListData(it.toString())
                    }

                }
            }
        })
    }

    private fun getListData(title : String = ""){
        val jsonObject = JSONObject()
        jsonObject.put("adPosition", adPosition)
        if(title.isNotEmpty()){
            jsonObject.put("title", title)
        }
        //活动banner
        OkHttpUtil.getInstance()
            .doPost(API.homeActivityBannerUrl, jsonObject.toString(), object : Callback {
                override fun onFailure(call: Call, e: IOException) {}

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    if (response.body != null) {
                        val (code, _, rows) = Gson().fromJson(
                            response.body!!.string(),
                            HomeBannerEntity::class.java
                        )
                        if (code == 200 && rows.isNotEmpty()) {
                            activity.runOnUiThread {
                                viewBinding.activeListRv.apply {
                                    layoutManager = LinearLayoutManager(this@ActiveListActivity)
                                    adapter = activeAdapter
                                    activeAdapter.setData(rows.toMutableList())
                                }

                            }
                        }
                    }
                }
            })
    }

    fun launchSearch(view: View){
        val s = viewBinding.searchEdit.text.toString()
        if(s.isNotEmpty()){
            getListData(s)
        }
    }

}