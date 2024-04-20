package com.example.medicalservice.activity.msgmind

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.medicalservice.R
import com.example.medicalservice.baseFile.BaseActivity
import com.example.medicalservice.bean.MessageMindEntity.RowsDTO
import com.example.medicalservice.databinding.ActivityMsgMindDetailBinding

/**
 * 公告通知详情
 */
class MsgMindDetailActivity : BaseActivity<ActivityMsgMindDetailBinding>() {



    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        this.intent.extras?.let {
            val row = it.getSerializable(MsgRemindActivity.MSG_DETAIL_ROW) as RowsDTO
            viewBinding.apply {
                titleText.text = row.title
                publishTimeText.text = "发布时间:${row.publishTime}"
                publishAuthorText.text = "发布人:${row.createBy ?: ""}"
                contentText.text = row.content.replace("</p><p>","").replace("<p>","").replace("</p>","")
            }
        }
    }
}