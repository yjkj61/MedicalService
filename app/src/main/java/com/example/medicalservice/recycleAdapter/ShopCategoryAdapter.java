package com.example.medicalservice.recycleAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.R;
import com.example.medicalservice.activity.ShopActivity;
import com.example.medicalservice.bean.ShopTypeBean;
import com.example.medicalservice.interfaceCallback.OnClickListener;

import java.util.ArrayList;
import java.util.List;

public class ShopCategoryAdapter extends RecyclerView.Adapter<ShopCategoryAdapter.ViewHolder> {


    private List<ShopTypeBean.DataDTO> shopCategoryBeans;

    public ShopCategoryAdapter(List<ShopTypeBean.DataDTO> shopCategoryBeans) {
        this.shopCategoryBeans = shopCategoryBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_category_item, parent, false);

        return new ViewHolder(view);

    }

    private OnClickListener onClickListener;

    public void setOnItemClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShopTypeBean.DataDTO shopCategoryBean = shopCategoryBeans.get(position);

        String text = "";

        for (int i = 0; i < shopCategoryBean.getGoodsTypeNameSecondaryList().size(); i++) {
            if (i < 3) {
                text = text + shopCategoryBean.getGoodsTypeNameSecondaryList().get(i) + " ";
            }
        }


        holder.content.setText(text);


        holder.category.setText(shopCategoryBean.getGoodsTypeName());

        holder.itemView.setOnClickListener(v -> onClickListener.OnItemClick(position));


    }

    @Override
    public int getItemCount() {
        return shopCategoryBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView category, content;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.category);
            content = itemView.findViewById(R.id.content);
        }
    }
}
