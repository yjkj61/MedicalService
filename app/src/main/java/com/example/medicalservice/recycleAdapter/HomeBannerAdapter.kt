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

private typealias OnItemClickCallback = (it : Row) ->Unit

class HomeBannerAdapter(val rows : List<Row>) : BannerAdapter<Row,HomeBannerAdapter.BannerVH>(rows) {

    private var onItemClickCallback : OnItemClickCallback? = null

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerVH {
        val view: View = LayoutInflater.from(parent?.context).inflate(R.layout.home_banner_item, parent, false)
        return BannerVH(view)
    }

    override fun onBindView(holder: BannerVH?, data: Row?, position: Int, size: Int) {
        GlideUtils.load(holder?.itemView?.context,rows[position].articleImage , holder?.imageCover)
        holder?.bannerTitle?.text = rows[position].title
        holder?.itemView?.setOnClickListener {
            onItemClickCallback?.invoke(rows[position]) ?: Unit
        }
    }

    fun OnItemClickCallback(callback: OnItemClickCallback){
        onItemClickCallback = callback
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