package com.example.medicalservice.recycleAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalservice.R
import com.example.medicalservice.bean.Row
import com.example.medicalservice.tools.GlideUtils

private typealias OnArticleListItemClickCallback = (it : Row)->Unit

class ArticleListAdapter : RecyclerView.Adapter<ArticleListAdapter.ArticleListVH>() {

    private var callback : OnArticleListItemClickCallback? = null
    private var data = mutableListOf<Row>()

    fun setArticleData(data : List<Row>){
        this.data = data.toMutableList();
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleListVH {
        val view =  LayoutInflater.from(parent.context).inflate(
            R.layout.article_list_item_layout,
            parent,
            false
        )
        return ArticleListVH(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ArticleListVH, position: Int) {
        holder.apply {
            data[position].apply {
                titleText?.text = title
                contentText?.text = content.replace("<p>","").replace("</p>","").replace("<p></p>","")
                GlideUtils.load(holder.itemView.context,articleImage,imageCover)
                publishAuthor?.text = articleCreatorName
                publishTimeText?.text = publishTime

                itemView.setOnClickListener {
                    callback?.invoke(this) ?: Unit
                }
            }

        }
    }

    fun OnItemClickCallback(callback : OnArticleListItemClickCallback){
        this.callback = callback
    }

    inner class ArticleListVH(itemView : View) : RecyclerView.ViewHolder(itemView){
        var titleText : TextView? = null
        var contentText : TextView? = null
        var imageCover : ImageView? = null
        var publishAuthor : TextView? = null
        var publishTimeText : TextView? = null

        init {
            itemView.apply {
                titleText = findViewById(R.id.article_title)
                contentText = findViewById(R.id.article_content)
                imageCover = findViewById(R.id.image_cover)
                publishAuthor = findViewById(R.id.publish_author)
                publishTimeText = findViewById(R.id.publish_time)
            }
        }
    }

}