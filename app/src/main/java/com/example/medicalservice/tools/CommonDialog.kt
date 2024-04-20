package com.example.medicalservice.tools

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.medicalservice.MyApplication
import com.example.medicalservice.R

/**
 * 弹出确认对话框
 * @param msg = 对话框提示信息
 * @param cancelBlock = 取消点击事件
 * @param confirmBlock = 确认点击事件
 */
fun Context.confirmDialog(cancelStr : String = "取消",confirmStr : String = "确认", msg : String = "",cancelOnTouchOutside : Boolean = false,cancelable : Boolean = false,cancelBlock : ()->Unit = {},confirmBlock : (Int)->Unit = {}):MaterialDialog{
    return MaterialDialog(this).show {
        var currentSelected = 0
        val data : MutableList<String> = mutableListOf("高血压","糖尿病","高血脂","冠心病","脑卒中","慢性肺病","癌症","阿兹海默症")
        val customView = customView(R.layout.add_medical_dialog_layout, scrollable = true, horizontalPadding = false, noVerticalPadding = true)
        val spinner = customView.findViewById<Spinner>(R.id.medical_add_spinner)
        val cancel = customView.findViewById<TextView>(R.id.cancel_btn)
        val confirm = customView.findViewById<TextView>(R.id.confirm_btn)
        cancelOnTouchOutside(cancelOnTouchOutside)
        cancelable(cancelable)
        cancel.text = cancelStr
        confirm.text = confirmStr
        cancel.setOnClickListener {
            cancelBlock.invoke()
            dismiss()
        }
        confirm.setOnClickListener {
            confirmBlock.invoke(currentSelected)
            dismiss()
        }
        var adapter = ArrayAdapter(MyApplication.getInstance(),R.layout.spinner_dropdown_item,data)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                currentSelected = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }
}




