package com.example.medicalservice.recycleAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.R;
import com.example.medicalservice.bean.ShopOrderBean;

import java.util.List;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder> {

    private List<ShopOrderBean> orderBeanList;

    public CouponAdapter(List<ShopOrderBean> orderBeanList) {
        this.orderBeanList = orderBeanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coupon_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return orderBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
