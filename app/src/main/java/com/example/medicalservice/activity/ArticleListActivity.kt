package com.example.medicalservice.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicalservice.baseFile.BaseActivity
import com.example.medicalservice.bean.HomeBannerEntity
import com.example.medicalservice.bean.Row
import com.example.medicalservice.databinding.ActivityArticleListBinding
import com.example.medicalservice.recycleAdapter.ArticleListAdapter
import com.example.medicalservice.recycleAdapter.HomeBannerAdapter
import com.example.medicalservice.tools.API
import com.example.medicalservice.tools.OkHttpUtil
import com.example.medicalservice.tools.clickNoRepeat2
import com.example.medicalservice.tools.registerTextWatcher
import com.example.medicalservice.tools.toast
import com.google.gson.Gson
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

/**
* @Author hxy
* Create at 2024/1/26
* @desc 资讯二级页面
*/
class ArticleListActivity : BaseActivity<ActivityArticleListBinding>() {

    private val articleAdapter by lazy {
        ArticleListAdapter()
    }

    private  var adPosition : String?=""

    override fun onResume() {
        super.onResume()
        hideStatusBar()
        adPosition =  intent.getStringExtra("msg")
        getListData()
        viewBinding.backButton.setOnClickListener {
            finish()
        }

        viewBinding.searchButton.setOnClickListener {
            val s = viewBinding.searchArticleEdit.text.toString()
            if(s.isBlank()){
                return@setOnClickListener
            }
            getListData(s)
        }

        viewBinding.searchArticleEdit.addTextChangedListener(registerTextWatcher {
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

        articleAdapter.OnItemClickCallback {
            val bundle = Bundle()
            bundle.putSerializable(ArticleDetailActivity.ARTICLE_DETAIL_KEY,it)
            go(ArticleDetailActivity::class.java,bundle)
        }
    }



    private fun getListData(title : String = ""){
        val jsonObject = JSONObject()
        jsonObject.put("adPosition", adPosition)
        if(title.isNotEmpty()){
            jsonObject.put("title", title)
        }
        //资讯列表
        OkHttpUtil.getInstance()
            .doPost(API.homeArticleBannerUrl, jsonObject.toString(), object : Callback {
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
                                viewBinding.articleRv.apply {
                                    layoutManager = LinearLayoutManager(this@ArticleListActivity)
                                    adapter = articleAdapter
                                    articleAdapter.setArticleData(rows)
                                }

                            }
                        }
                    }
                }
            })
    }

}