package com.example.medicalservice.recycleAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalservice.MyApplication
import com.example.medicalservice.R

class DeviceInfoAdapter :RecyclerView.Adapter<DeviceInfoAdapter.ViewHolder>() {
    private var data = mutableListOf<Any>()

    fun setData(list : MutableList<Any>){
        data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.device_info_item_layout,null,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.deviceName?.text = "智能呼叫板"
        setDeviceStatus(holder,1)
    }

    /**
     * @param status : 0 :有，在线   1：有，离线
     */
    @SuppressLint("ResourceAsColor")
    fun setDeviceStatus(holder: ViewHolder, status : Int){
        val colorForWhite = MyApplication.getInstance().resources.getColorStateList(R.color.white, null)
        val colorFor777777 = MyApplication.getInstance().resources.getColorStateList(R.color.color_777777, null)
        when(status){
            0->{

                holder.hasDevice?.setBackgroundResource(R.drawable.purple_bg_5dp_corner_shape)
                holder.hasDevice?.setTextColor(colorForWhite)
                holder.hasDevice?.text = "有"
                holder.deviceStatus?.setBackgroundResource(R.drawable.purple_bg_5dp_corner_shape)

                holder.deviceStatus?.setTextColor(colorForWhite)
                holder.deviceStatus?.text = "在线"
            }
            1->{
                holder.hasDevice?.setBackgroundResource(R.drawable.purple_bg_5dp_corner_shape)
                holder.hasDevice?.setTextColor(colorForWhite)
                holder.hasDevice?.text = "有"
                holder.deviceStatus?.setBackgroundResource(R.drawable.black_bg_5dp_corner_shape)
                holder.deviceStatus?.setTextColor(colorFor777777)
                holder.deviceStatus?.text = "离线"
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var deviceName : TextView? = null
        var hasDevice : TextView? = null
        var deviceStatus : TextView? = null

        init {
            itemView.apply {
                deviceName = findViewById(R.id.device_name)
                hasDevice = findViewById(R.id.has_device)
                deviceStatus = findViewById(R.id.device_status)
            }
        }

    }
}