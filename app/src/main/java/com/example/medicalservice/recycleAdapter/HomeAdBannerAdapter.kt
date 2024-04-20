package com.example.medicalservice.recycleAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalservice.R
import com.example.medicalservice.bean.ADRow
import com.example.medicalservice.bean.Row
import com.example.medicalservice.tools.GlideUtils
import com.youth.banner.adapter.BannerAdapter

private typealias OnHomeADBannerItemClickCallback = (it : ADRow) -> Unit

class HomeAdBannerAdapter(val rows : List<ADRow>) : BannerAdapter<ADRow,HomeAdBannerAdapter.BannerVH>(rows) {

    private var onHomeADBannerItemClickCallback : OnHomeADBannerItemClickCallback? = null

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerVH {
        val view: View = LayoutInflater.from(parent?.context).inflate(R.layout.home_banner_item, parent, false)
        return BannerVH(view)
    }

    override fun onBindView(holder: BannerVH?, data: ADRow?, position: Int, size: Int) {

        GlideUtils.load(holder?.itemView?.context,rows[position].coverImage , holder?.imageCover)
        holder?.bannerTitle?.text = rows[position].adName
        holder?.itemView?.setOnClickListener {
            onHomeADBannerItemClickCallback?.invoke(rows[position]) ?: Unit
        }
    }

    fun OnItemClickCallback(callback : OnHomeADBannerItemClickCallback){
        onHomeADBannerItemClickCallback = callback
    }

    inner class BannerVH(itemView : View) : RecyclerView.ViewHolder(itemView){
        var imageCover : ImageView
        var bannerTitle : TextView

        init {
            itemView.apply {
                imageCover = findViewById(R.id.banner_image)
                bannerTitle = findViewById(R.id.banner_title)
            }
        }
    }
}