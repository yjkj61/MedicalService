package com.example.medicalservice.recycleAdapter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.R;
import com.example.medicalservice.bean.UseMedTipListBean;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class UseMedTipGridRecAdapter  extends RecyclerView.Adapter<UseMedTipGridRecAdapter.ViewHolder>{


    private List<UseMedTipListBean.DataDTO.HMedicinalRemindVo> list;

    public UseMedTipGridRecAdapter(List<UseMedTipListBean.DataDTO.HMedicinalRemindVo> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blood_ox_history_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        UseMedTipListBean.DataDTO.HMedicinalRemindVo remindVo = list.get(position);

        holder.data.setText(remindVo.getUseMedicinalTime());
        holder.name.setText(remindVo.getMedicinalName());
        holder.count.setText(remindVo.getMedicinalNumber()+"");
        holder.unit.setText(remindVo.getMedicinalUnit());
        holder.status.setVisibility(View.GONE);

        holder.data.setGravity(Gravity.CENTER);
        holder.name.setGravity(Gravity.CENTER);

        holder.count.setGravity(Gravity.CENTER);

        holder.unit.setGravity(Gravity.CENTER);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView data,name,count,unit;
        CardView status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            data = itemView.findViewById(R.id.date);
            name = itemView.findViewById(R.id.time);
            count = itemView.findViewById(R.id.value);
            unit = itemView.findViewById(R.id.unit);

            status = itemView.findViewById(R.id.status);
        }
    }
}
