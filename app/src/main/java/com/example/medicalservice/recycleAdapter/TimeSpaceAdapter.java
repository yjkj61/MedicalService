package com.example.medicalservice.recycleAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.R;

import java.util.List;

public class TimeSpaceAdapter extends RecyclerView.Adapter<TimeSpaceAdapter.ViewHolder> {


    private List<String> times;

    private int select = 0;


    public TimeSpaceAdapter(List<String> times) {
        this.times = times;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void changeSelect(int select) {
        this.select = select;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_space_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.timeSpace.setText(times.get(position));

        if (select == position) {

            holder.timeSpace.setTextSize(holder.itemView.getResources().getDimensionPixelSize(R.dimen.select_time) );
        } else {
            holder.timeSpace.setTextSize(holder.itemView.getResources().getDimensionPixelSize(R.dimen.no_select_time));
        }
    }

    @Override
    public int getItemCount() {
        return times.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView timeSpace;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            timeSpace = itemView.findViewById(R.id.time_space);
        }
    }
}
