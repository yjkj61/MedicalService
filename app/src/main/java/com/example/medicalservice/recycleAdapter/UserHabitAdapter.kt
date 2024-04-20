package com.example.medicalservice.recycleAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalservice.R
import com.example.medicalservice.tools.toast

class UserHabitAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data  = mutableListOf<String>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: MutableList<String>){
        list.add("")
        data = list
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == data.lastIndex){
            0
        }else{
            1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view : View? = null
        var viewHolder : RecyclerView.ViewHolder? = null
        when(viewType){
            0->{
                view = LayoutInflater.from(parent.context).inflate(R.layout.user_portrait_last_item_layout,null,false)
                viewHolder = ViewHolder2(view)
            }
            else->{
                view = LayoutInflater.from(parent.context).inflate(R.layout.user_portrait_item_layout,null,false)
                viewHolder =  ViewHolder(view)
            }
        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolder ->{
                holder.textView?.text = data[position]
            }
            else ->{
                holder.itemView.setOnClickListener {
                    toast("点击了")
                }
            }
        }
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var textView : TextView? = null

        init {
            textView = itemView.findViewById(R.id.user_habit_text)
            val layoutParams = textView?.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.marginEnd = 20
            layoutParams.bottomMargin = 5
            textView?.layoutParams = layoutParams
        }
    }

    inner class ViewHolder2(itemView : View) : RecyclerView.ViewHolder(itemView){

    }

}