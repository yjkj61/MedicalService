package com.example.medicalservice.recycleAdapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalservice.R
import com.example.medicalservice.bean.DataXX

private typealias OnRelationTextChangeCallback = (Int,String)->Unit
private typealias OnPhoneTextChangeCallback = (Int,String)->Unit
class PersonChargeAdapter : RecyclerView.Adapter<PersonChargeAdapter.ViewHolder>() {

    private var onRelationTextChangeCallback : OnRelationTextChangeCallback? = null
    private var onPhoneTextChangeCallback : OnPhoneTextChangeCallback? = null

    private var data = mutableListOf<PersonChargeBean>()

    fun setData(list : MutableList<PersonChargeBean>){
        data = list
        notifyDataSetChanged()
    }

    fun OnRelationTextChangeCallback(callback: OnRelationTextChangeCallback){
        onRelationTextChangeCallback = callback
    }

    fun OnPhoneTextChangeCallback(callback: OnPhoneTextChangeCallback){
        onPhoneTextChangeCallback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.person_charge_item_layout,null,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            title.text = "${data[position].title}："
            relationText.text = "${data[position].name}"
            phoneText.text = "${data[position].phone}"

            relationText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    onRelationTextChangeCallback?.invoke(position,s.toString())?:Unit
                }

            })

            phoneText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    onPhoneTextChangeCallback?.invoke(position,s.toString())?:Unit
                }

            })
        }
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
         var title : TextView
         var relationText : TextView
         var phoneText : TextView

        init {
            itemView.apply {
                title = findViewById(R.id.title_text)
                relationText = findViewById(R.id.relation_text)
                phoneText = findViewById(R.id.phone_text)
            }
        }
    }

    data class PersonChargeBean(
        var title : String = "",
        var name : String = "",
        var phone : String = "",
        var id : String = "",
        var sex : String = "",
        var eContact : String = ""
    )
}