package com.example.medicalservice.recycleAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalservice.R
import com.example.medicalservice.bean.MessageMindEntity.RowsDTO
import com.example.medicalservice.recycleAdapter.AiBegHeaderAdapter.viewHolder

private typealias OnMsgMindListItemClickCallback = (it : RowsDTO)->Unit

class MsgMindListAdapter : RecyclerView.Adapter<MsgMindListAdapter.MsgMindListVH>() {

    var callback : OnMsgMindListItemClickCallback? = null

    private var rows = mutableListOf<RowsDTO>()

    fun setData(rowsDTO: MutableList<RowsDTO>):MsgMindListAdapter{
        rows = rowsDTO
        notifyDataSetChanged()
        return this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgMindListVH {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.msg_mind_list_item_layout, parent, false)

        return MsgMindListVH(view)
    }

    override fun getItemCount(): Int {
        return rows.size
    }

    override fun onBindViewHolder(holder: MsgMindListVH, position: Int) {
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

    fun OnItemClickCallback(callback: OnMsgMindListItemClickCallback){
        this.callback = callback
    }

    inner class MsgMindListVH(itemView : View) : RecyclerView.ViewHolder(itemView){
        var titleText : TextView
        var dateTextView : TextView

        init {
            titleText = itemView.findViewById(R.id.msg_title_text)
            dateTextView = itemView.findViewById(R.id.msg_mind_date)
        }
    }

}