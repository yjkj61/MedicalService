package com.example.medicalservice.recycleAdapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.R;
import com.example.medicalservice.activity.healthcareChildActivity.AIBeg;
import com.example.medicalservice.activity.healthcareChildActivity.MyStep;
import com.example.medicalservice.bean.UserHealthDataBean;

import java.util.List;

public class UserHealthDataAdapter extends RecyclerView.Adapter<UserHealthDataAdapter.ViewHolder> {


    private List<UserHealthDataBean> userHealthDataBeans;
    private Activity activity;

    public UserHealthDataAdapter(List<UserHealthDataBean> userHealthDataBeans, Activity activity) {
        this.userHealthDataBeans = userHealthDataBeans;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_health_data_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        UserHealthDataBean userHealthDataBean = userHealthDataBeans.get(position);


        holder.type.setText(userHealthDataBean.getType());
        holder.text_value.setText(userHealthDataBean.getTextValue());


        holder.progress_total.post(() -> {
            int width = holder.progress_total.getMeasuredWidth();

            holder.progress_value.setLayoutParams(new LinearLayout.LayoutParams((int) (userHealthDataBean.getProgress() * width), holder.progress_value.getHeight()));
        });

        if (userHealthDataBean.getType().contains("睡眠")) {
            holder.imageType.setImageResource(R.drawable.icon_health_list_sleep);
        }

        if (userHealthDataBean.getType().contains("步数")) {
            holder.imageType.setImageResource(R.drawable.icon_health_list_walk);

        }

        holder.itemView.setOnClickListener(v -> {
            if (userHealthDataBean.getType().equals("当日睡眠") || userHealthDataBean.getType().equals("本周睡眠")) {
                Intent intent = new Intent(activity, AIBeg.class);
                activity.startActivity(intent);
            }


            if (userHealthDataBean.getType().equals("当日步数") || userHealthDataBean.getType().equals("本周步数")) {
                Intent intent = new Intent(activity, MyStep.class);
                activity.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return userHealthDataBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout progress_total, progress_value;
        private ImageView imageType;
        private TextView type, text_value;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageType = itemView.findViewById(R.id.imageType);
            progress_value = itemView.findViewById(R.id.progress_value);
            progress_total = itemView.findViewById(R.id.progress_total);
            type = itemView.findViewById(R.id.type);
            text_value = itemView.findViewById(R.id.text_value);
        }
    }
}
