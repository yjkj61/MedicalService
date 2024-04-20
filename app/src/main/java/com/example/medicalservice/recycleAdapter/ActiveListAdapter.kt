package com.example.medicalservice.recycleAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalservice.R
import com.example.medicalservice.bean.MessageMindEntity
import com.example.medicalservice.bean.Row

private typealias OnActiveListItemClickCallback = (Row)->Unit

class ActiveListAdapter : RecyclerView.Adapter<ActiveListAdapter.ActiveListVH>() {

    var callback : OnActiveListItemClickCallback? = null

    private var rows = mutableListOf<Row>()

    fun setData(rows: MutableList<Row>):ActiveListAdapter{
        this.rows = rows
        notifyDataSetChanged()
        return this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveListVH {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.msg_mind_list_item_layout, parent, false)

        return ActiveListVH(view)
    }

    override fun getItemCount(): Int {
        return rows.size
    }

    override fun onBindViewHolder(holder: ActiveListVH, position: Int) {
        if(rows.isEmpty()){
            return
        }
        rows[position].apply {
            holder.titleText.text = title
            holder.dateTextView.text = publishTime
            holder.itemView.setOnClickListener {
                callback?.invoke(this)
            }
        }

    }

    fun OnItemClickCallback(callback: OnActiveListItemClickCallback){
        this.callback = callback
    }

    inner class ActiveListVH(itemView : View) : RecyclerView.ViewHolder(itemView){
        var titleText : TextView
        var dateTextView : TextView

        init {
            titleText = itemView.findViewById(R.id.msg_title_text)
            dateTextView = itemView.findViewById(R.id.msg_mind_date)
        }
    }

}