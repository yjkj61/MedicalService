package com.example.medicalservice.activity

import android.os.Bundle
import android.text.Html
import com.example.medicalservice.R
import com.example.medicalservice.baseFile.BaseActivity
import com.example.medicalservice.bean.Row
import com.example.medicalservice.databinding.ActivityArticleDetialBinding

class ArticleDetailActivity : BaseActivity<ActivityArticleDetialBinding>() {
    override fun onResume() {
        super.onResume()
        hideStatusBar()
        intent.extras?.let {
            val row = it.getSerializable(ARTICLE_DETAIL_KEY) as Row
            viewBinding.apply {
                articleTitle.text = row.title
                publishAuthor.text = row.articleCreatorName
                publishTime.text = row.publishTime
                articleContent.text = Html.fromHtml(row.content);
//                articleContent.text = row.content.replace("<p>","").replace("</p>","").replace("<p></p>","")
            }
        }
    }

    companion object{
        const val ARTICLE_DETAIL_KEY = "article_detail"
    }
}