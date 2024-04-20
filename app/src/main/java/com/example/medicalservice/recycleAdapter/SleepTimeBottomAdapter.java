package com.example.medicalservice.recycleAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.R;

import java.util.ArrayList;
import java.util.List;

public class SleepTimeBottomAdapter extends RecyclerView.Adapter<SleepTimeBottomAdapter.ViewHolder> {


    private List<String> list;
    private int width;

    public SleepTimeBottomAdapter(List<String> list, int width) {
        this.list = list;
        this.width = width;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sleep_time_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.time.setText(list.get(position));
        holder.time.setLayoutParams(new LinearLayout.LayoutParams(width,30));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
        }
    }
}
