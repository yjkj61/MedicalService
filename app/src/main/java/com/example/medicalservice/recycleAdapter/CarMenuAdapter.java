package com.example.medicalservice.recycleAdapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.R;
import com.example.medicalservice.bean.CarFoodListBean;
import com.example.medicalservice.bean.FoodListBean;
import com.example.medicalservice.fragments.healthAiBegFragments.DayHealthFragment;
import com.example.medicalservice.interfaceCallback.OnClickByType;
import com.example.medicalservice.interfaceCallback.OnClickListener;
import com.example.medicalservice.tools.Count;
import com.example.medicalservice.tools.GlideUtils;
import com.google.android.material.card.MaterialCardView;

import java.text.DecimalFormat;
import java.util.List;

public class CarMenuAdapter extends RecyclerView.Adapter<CarMenuAdapter.ViewHolder> {

    List<CarFoodListBean> sleepStatuses;
    DecimalFormat df = new DecimalFormat("#.00");


    public CarMenuAdapter(List<CarFoodListBean> sleepStatuses) {
        this.sleepStatuses = sleepStatuses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CarFoodListBean carFoodListBean = sleepStatuses.get(position);

        GlideUtils.load(holder.itemView.getContext(), carFoodListBean.getImageUrl(), holder.imageView, R.drawable.good_test,20);

        holder.foodName.setText(carFoodListBean.getName());
        holder.number.setText(carFoodListBean.getNumber() + "");
        double price = carFoodListBean.getPrice();

        holder.price.setText(String.format("%.2f", price) + "");

        holder.add.setOnClickListener(v -> {


            onClickListener.OnItemClick(position,"add");

            //notifyItemChanged(position);
        });

        holder.reduce.setOnClickListener(v -> {
            onClickListener.OnItemClick(position,"reduce");
//            if (carFoodListBean.getNumber() == 1) {
//                onClickListener.OnItemClick(position,"reduce");
//                return;
//            }
//            carFoodListBean.setNumber(carFoodListBean.getNumber() - 1);
//            carFoodListBean.setPrice(Count.sub(carFoodListBean.getPrice(), carFoodListBean.getSinglePrice()));
//
//            notifyItemChanged(position);

        });


    }

    private OnClickByType onClickListener;

    public void setOnItemClickListener(OnClickByType onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        return sleepStatuses.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView, reduce, add;

        TextView foodName;

        TextView number, price;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            foodName = itemView.findViewById(R.id.foodName);
            number = itemView.findViewById(R.id.number);

            add = itemView.findViewById(R.id.add);
            reduce = itemView.findViewById(R.id.reduce);
            price = itemView.findViewById(R.id.price);

        }
    }
}
