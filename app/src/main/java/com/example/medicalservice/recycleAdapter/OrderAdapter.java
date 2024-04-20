package com.example.medicalservice.recycleAdapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.R;
import com.example.medicalservice.bean.OrderBean;
import com.example.medicalservice.fragments.orderFragment.OrderFragment;
import com.example.medicalservice.interfaceCallback.OnClickByType;
import com.example.medicalservice.interfaceCallback.OnClickListener;
import com.example.medicalservice.tools.GlideUtils;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<OrderFragment.PropertyOrderBean> orderBeanList;

    public OrderAdapter(List<OrderFragment.PropertyOrderBean> orderBeanList) {
        this.orderBeanList = orderBeanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        OrderFragment.PropertyOrderBean propertyOrderBean = orderBeanList.get(position);

        GlideUtils.load(holder.itemView.getContext(),propertyOrderBean.getsTypeLogo(),holder.image_icon);

        holder.agent_name.setText("服务公司："+propertyOrderBean.getAgentName());
        holder.worker_name.setText("服务人员："+propertyOrderBean.getWorkerName());
        holder.name.setText(propertyOrderBean.getProjectName());

        holder.order_time.setText("下单服务时间："+propertyOrderBean.getBookTime());
        holder.service_book_start_time.setText("预计服务时间："+propertyOrderBean.getBookStartTime().substring(0,16));

        if(propertyOrderBean.getServiceStatus()==0){
            holder.time.setVisibility(View.VISIBLE);
            holder.time.setText(propertyOrderBean.getBookStartTime().substring(11,16)+"左右上门");
        }else {
            holder.time.setVisibility(View.GONE);
        }

        if(propertyOrderBean.getServiceStatus() == 0){
            holder.btn.setText("取消预约");

            holder.btn.setOnClickListener(v -> onClickListener.OnItemClick(position,"取消预约"));

        }
        if(propertyOrderBean.getServiceStatus() == 2){
            holder.btn.setText("去评价");
            holder.btn.setOnClickListener(v -> onClickListener.OnItemClick(position,"去评价"));

        }
        if(propertyOrderBean.getServiceStatus() == 3){
            holder.btn_box.setCardBackgroundColor(Color.rgb(243,199,193));
            holder.btn.setText("退款/售后");
            holder.btn.setOnClickListener(v -> onClickListener.OnItemClick(position,"退款/售后"));

        }
    }

    @Override
    public int getItemCount() {
        return orderBeanList.size();
    }


    private OnClickByType onClickListener;

    public void setOnItemClickListener(OnClickByType onClickListener) {
        this.onClickListener = onClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name,order_time,service_book_start_time,time,worker_name,agent_name;
        private ImageView image_icon;

        private TextView btn;

        private MaterialCardView btn_box;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            order_time = itemView.findViewById(R.id.order_time);
            service_book_start_time = itemView.findViewById(R.id.service_book_start_time);
            time = itemView.findViewById(R.id.time);
            worker_name = itemView.findViewById(R.id.worker_name);
            agent_name = itemView.findViewById(R.id.agent_name);

            image_icon = itemView.findViewById(R.id.image_icon);
            btn_box = itemView.findViewById(R.id.btn_box);
            btn = itemView.findViewById(R.id.btn);
        }
    }
}
