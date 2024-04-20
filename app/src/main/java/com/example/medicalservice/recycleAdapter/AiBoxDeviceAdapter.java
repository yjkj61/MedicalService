package com.example.medicalservice.recycleAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.R;
import com.example.medicalservice.bean.DeviceBean;
import com.example.medicalservice.interfaceCallback.OnClickListener;

import java.util.List;

public class AiBoxDeviceAdapter extends RecyclerView.Adapter<AiBoxDeviceAdapter.ViewHolder> {

    List<DeviceBean> deviceBeans;

    public AiBoxDeviceAdapter(List<DeviceBean> deviceBeans) {
        this.deviceBeans = deviceBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ai_device_grid_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DeviceBean deviceBean = deviceBeans.get(position);
        holder.control_box.setVisibility(View.GONE);
        holder.name.setText(deviceBean.getName());
        holder.context.setText(deviceBean.getContent());
        holder.image.setImageResource(deviceBean.getImage());

        holder.itemView.setOnClickListener(v -> onClickListener.OnItemClick(position));
    }

    @Override
    public int getItemCount() {
        return deviceBeans.size();
    }

    private OnClickListener onClickListener;

    public void setOnItemClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        LinearLayout control_box;

        TextView name, context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            control_box = itemView.findViewById(R.id.control_box);

            name = itemView.findViewById(R.id.name);
            context = itemView.findViewById(R.id.context);

        }
    }
}
