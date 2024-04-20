package com.example.medicalservice.recycleAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalservice.R
import com.example.medicalservice.databinding.MedicalHistoryItemBinding

private typealias OnMedicalHistoryDelete = (Int)->Unit
private typealias OnMedicalHistoryEdit = ()->Unit
private typealias OnMedicalHistoryAdd = ()->Unit

class MedicalHistoryAdapter : RecyclerView.Adapter<MedicalHistoryAdapter.ViewHolder>() {

    private var onMedicalHistoryDeleteCallback : OnMedicalHistoryDelete? = null
    private var onMedicalHistoryEditCallback : OnMedicalHistoryEdit? = null
    private var onMedicalHistoryAddCallback : OnMedicalHistoryAdd? = null

    fun OnMedicalHistoryDelete(callback : OnMedicalHistoryDelete){
        onMedicalHistoryDeleteCallback = callback
    }

    fun OnMedicalHistoryEdit(callback : OnMedicalHistoryEdit){
        onMedicalHistoryEditCallback = callback
    }

    fun OnMedicalHistoryAdd(callback : OnMedicalHistoryAdd){
        onMedicalHistoryAddCallback = callback
    }

    private var data = mutableListOf<MedicalHisBean>()
    private var type  = "0"

    fun setData(list : MutableList<MedicalHisBean>,type : String){
        data = list
        this.type = type
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_portrait_item_layout,null,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.deleteButton.setOnClickListener {
            onMedicalHistoryDeleteCallback?.invoke(position) ?: Unit
        }

        if(type == "0"){
            when(position){
                data.lastIndex ->{
                    holder.medicalHistoryText.text = "添加"
                }
                data.lastIndex - 1 ->{
                    holder.medicalHistoryText.text = "编辑"
                }
                else ->{
                    holder.medicalHistoryText.text = data[position].value
                }
            }

            //holder.medicalHistoryText.text = data[position].value
            if(position != data.lastIndex  && position != data.lastIndex - 1 && data[position].deleteIsShow){
                holder.deleteButton.visibility = View.VISIBLE
            }else{
                holder.deleteButton.visibility = View.INVISIBLE
            }
            if(position == data.lastIndex - 1 ){
                holder.medicalHistoryText.setOnClickListener {
                    onMedicalHistoryEditCallback?.invoke() ?: Unit
                }
            }
            if(position == data.lastIndex ){
                holder.medicalHistoryText.setOnClickListener {
                    onMedicalHistoryAddCallback?.invoke() ?: Unit
                }
            }
        }else{
            val str =  when(data[position].value){
                "00"->{ "高血压" }
                "01"->{ "糖尿病" }
                "02"->{ "冠心病" }
                "03"->{ "慢性阻塞性肺病" }
                "04"->{ "恶性肿瘤" }
                "05"->{ "脑卒中" }
                "06"->{ "重性精神疾病" }
                "07"->{ "结核病" }
                "08"->{ "肝炎" }
                else->{
                    data[position].value
                }
            }
            when(position){
                data.lastIndex ->{
                    holder.medicalHistoryText.text = "添加"
                }
                data.lastIndex - 1 ->{
                    holder.medicalHistoryText.text = "编辑"
                }
                else ->{
                    holder.medicalHistoryText.text = str
                }
            }

            if(position != data.lastIndex && position != data.lastIndex - 1 && data[position].deleteIsShow){
                holder.deleteButton.visibility = View.VISIBLE
            }else{
                holder.deleteButton.visibility = View.INVISIBLE
            }
            if(position == data.lastIndex - 1 ){
                holder.medicalHistoryText.setOnClickListener {
                    onMedicalHistoryEditCallback?.invoke() ?: Unit
                }
            }
            if(position == data.lastIndex ){
                holder.medicalHistoryText.setOnClickListener {
                    onMedicalHistoryAddCallback?.invoke() ?: Unit
                }
            }
        }
    }

    inner class ViewHolder(itemView :View) : RecyclerView.ViewHolder(itemView){
        var medicalHistoryText : TextView
        var deleteButton : ImageButton

        init {
            medicalHistoryText = itemView.findViewById(R.id.user_habit_text)
            deleteButton = itemView.findViewById(R.id.delete_button)
            val layoutParams = medicalHistoryText.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.marginEnd = 20
            layoutParams.bottomMargin = 5
            medicalHistoryText.layoutParams = layoutParams
        }
    }

    data class MedicalHisBean(
        var value : String = "",
        var deleteIsShow : Boolean = false
    )

}

fun MedicalHistoryAdapter.MedicalHisBean.toStr():String{
    return this.value
}

fun MutableList<MedicalHistoryAdapter.MedicalHisBean>.toStrList():MutableList<String>{
    val toMutableList = this.toMutableList()
    toMutableList.removeAt(toMutableList.lastIndex)
    toMutableList.removeAt(toMutableList.lastIndex)
    return toMutableList.map {
        it.toStr()
    }.toMutableList()
}