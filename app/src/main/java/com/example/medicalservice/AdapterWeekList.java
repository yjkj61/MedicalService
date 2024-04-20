package com.example.medicalservice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.bean.CalendarBean;

import java.util.List;

public class AdapterWeekList extends RecyclerView.Adapter<AdapterWeekList.ViewHolder> {


    private List<Integer> weekList;
    public AdapterWeekList(List<Integer> weekList){
        this.weekList = weekList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.week_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        int week = weekList.get(position);

        switch (week){
            case 1:
                holder.week.setText("周一");
                holder.weekEng.setText("Mon");
                break;
            case 2:
                holder.week.setText("周二");
                holder.weekEng.setText("Tue");
                break;
            case 3:
                holder.week.setText("周三");
                holder.weekEng.setText("Wed");
                break;
            case 4:
                holder.week.setText("周四");
                holder.weekEng.setText("Thu");
                break;
            case 5:
                holder.week.setText("周五");
                holder.weekEng.setText("Fri");
                break;
            case 6:
                holder.week.setText("周六");
                holder.weekEng.setText("Sta");
                break;
            case 7:
                holder.week.setText("周日");
                holder.weekEng.setText("Sun");
                break;
        }


    }

    @Override
    public int getItemCount() {
        return weekList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView week;
        private TextView weekEng;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            week = itemView.findViewById(R.id.week);
            weekEng = itemView.findViewById(R.id.weekEng);
        }
    }
}
