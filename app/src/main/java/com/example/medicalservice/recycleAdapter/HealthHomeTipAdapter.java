package com.example.medicalservice.recycleAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.R;
import com.example.medicalservice.bean.HomeCommentBean;

import java.util.List;

public class HealthHomeTipAdapter extends RecyclerView.Adapter<HealthHomeTipAdapter.ViewHolder> {


    private List<HomeCommentBean> homeCommentBeans;

    public HealthHomeTipAdapter(List<HomeCommentBean> homeCommentBeans) {
        this.homeCommentBeans = homeCommentBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.health_home_tip_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeCommentBean homeCommentBean = homeCommentBeans.get(position);

        holder.name.setText(homeCommentBean.getName());
        holder.number.setText(homeCommentBean.getValue());
        holder.unit.setText(homeCommentBean.getOther());
    }

    @Override
    public int getItemCount() {
        return homeCommentBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, number, unit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            unit = itemView.findViewById(R.id.unit);
        }
    }
}
