package com.example.medicalservice.recycleAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.R;
import com.example.medicalservice.bean.SleepBean;

import java.util.List;

public class SleepLayoutChartAdapter extends RecyclerView.Adapter<SleepLayoutChartAdapter.ViewHolder> {

    private List<SleepBean> sleepBeanList;
    private int totalTime;

    private int totalNumber;

    public SleepLayoutChartAdapter(List<SleepBean> sleepBeanList, int totalTime, int totalNumber) {
        this.sleepBeanList = sleepBeanList;

        this.totalTime = totalTime;
        this.totalNumber = totalNumber;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sleep_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        int oneHour = totalNumber/totalTime;

        SleepBean sleepBean = sleepBeanList.get(position);
        switch (sleepBean.getType()){
            case "清醒时间":
                holder.layout.setBackgroundResource(R.drawable.sleep_first);
                holder.layout.setTranslationY(50);
                break;
            case "快速动眼睡眠":
                holder.layout.setBackgroundResource(R.drawable.sleep_second);
                holder.layout.setTranslationY(140);
                break;
            case "深度睡眠":
                holder.layout.setBackgroundResource(R.drawable.sleep_third);
                holder.layout.setTranslationY(230);

                break;
        }

        holder.layout.setLayoutParams(new LinearLayout.LayoutParams(oneHour*sleepBean.getTime(),30));

    }

    @Override
    public int getItemCount() {
        return sleepBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.item);
        }
    }
}
