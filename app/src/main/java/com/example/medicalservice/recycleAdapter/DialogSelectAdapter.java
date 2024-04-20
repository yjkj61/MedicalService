package com.example.medicalservice.recycleAdapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.medicalservice.R;
import com.example.medicalservice.bean.BluetoothBean;


import java.util.List;

public class DialogSelectAdapter extends BaseQuickAdapter<BluetoothBean, DialogSelectAdapter.ViewHolder> {


    public DialogSelectAdapter(int layoutResId, @Nullable List<BluetoothBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(@NonNull ViewHolder viewHolder, BluetoothBean bluetoothBean) {
        viewHolder.text.setText(bluetoothBean.getName());
        viewHolder.number.setText(bluetoothBean.getNumber()+"");
        if (bluetoothBean.getNumber() < -45){
            Glide.with(getContext()).load(R.drawable.wifi_four).into(viewHolder.wifi);
        }else if (bluetoothBean.getNumber() < -30){
            Glide.with(getContext()).load(R.drawable.wifi_three).into(viewHolder.wifi);
        }else if (bluetoothBean.getNumber() < -15){
            Glide.with(getContext()).load(R.drawable.wifi_two).into(viewHolder.wifi);
        }else {
            Glide.with(getContext()).load(R.drawable.wifi_one).into(viewHolder.wifi);
        }
    }

    static class ViewHolder extends BaseViewHolder {

        private TextView text,number;
        private ImageView wifi;

        public ViewHolder(@NonNull View view) {
            super(view);
            text = view.findViewById(R.id.text);
            number = view.findViewById(R.id.number);
            wifi = view.findViewById(R.id.wifi);
        }
    }
}
