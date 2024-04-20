package com.example.medicalservice.recycleAdapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.R;
import com.example.medicalservice.activity.ShopActivity;
import com.example.medicalservice.activity.shopChildView.GoodDetailView;
import com.example.medicalservice.bean.GoodBean;
import com.example.medicalservice.tools.GlideUtils;

import java.util.List;

public class ChartsAdapter extends RecyclerView.Adapter<ChartsAdapter.ViewHolder>{

    List<GoodBean.RowsDTO> shopChartsBeans;

    public ChartsAdapter(List<GoodBean.RowsDTO> shopChartsBeans) {
        this.shopChartsBeans = shopChartsBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_charts_item, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GoodBean.RowsDTO shopChartsBean = shopChartsBeans.get(position);

        holder.number.setText(position+1+".");
        holder.name.setText(shopChartsBean.getGoodsName());


        GlideUtils.load(holder.itemView.getContext(),shopChartsBean.getGoodsImages(), holder.image,R.drawable.good_test,20);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), GoodDetailView.class);
            intent.putExtra("msg", shopChartsBean.getSGoodsId());
            holder.itemView.getContext().startActivity(intent);
        });




    }

    @Override
    public int getItemCount() {
        return shopChartsBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,number;

        ImageView image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            image = itemView.findViewById(R.id.image);
        }
    }
}
