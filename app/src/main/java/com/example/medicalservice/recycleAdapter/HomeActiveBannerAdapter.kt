package com.example.medicalservice.recycleAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalservice.R
import com.example.medicalservice.bean.ActiveRow
import com.example.medicalservice.bean.Row
import com.example.medicalservice.tools.GlideUtils
import com.youth.banner.adapter.BannerAdapter
private typealias OnActiveItemClickCallback = (ActiveRow)->Unit
class HomeActiveBannerAdapter(val rows : List<ActiveRow>) : BannerAdapter<ActiveRow, HomeActiveBannerAdapter.ActiveBannerVH>(rows) {

    private var onItemClickCallback : OnActiveItemClickCallback? = null

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ActiveBannerVH {
        val view: View = LayoutInflater.from(parent?.context)
            .inflate(R.layout.home_banner_item, parent, false)
        return ActiveBannerVH(view)
    }


    override fun onBindView(holder: ActiveBannerVH?, data: ActiveRow?, position: Int, size: Int) {
        GlideUtils.load(holder?.itemView?.context, data?.activityImage.toString(), holder?.imageCover)
        holder?.bannerTitle?.text = rows[position].title
        holder?.itemView?.setOnClickListener {
            onItemClickCallback?.invoke(rows[position]) ?: Unit
        }
    }

    fun OnItemClickCallback(callback: OnActiveItemClickCallback){
        onItemClickCallback = callback
    }

    inner class ActiveBannerVH(itemView : View) : RecyclerView.ViewHolder(itemView){
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