package com.example.medicalservice.recycleAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.util.DisplayMetrics;
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
import com.example.medicalservice.activity.shopChildView.GoodDetailView;
import com.example.medicalservice.bean.GoodBean;
import com.example.medicalservice.tools.GlideUtils;

import java.util.List;

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.ViewHolder> {


    private List<GoodBean.RowsDTO> goodBeanList;
    private Activity activity;

    public GoodsListAdapter(List<GoodBean.RowsDTO> goodBeanList, Activity activity) {
        this.goodBeanList = goodBeanList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_item, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GoodBean.RowsDTO goodBean = goodBeanList.get(position);

        holder.goodName.setText(goodBean.getGoodsName());
        GlideUtils.load(holder.itemView.getContext(), goodBean.getGoodsCoverImages(), holder.goodImage, R.drawable.good_test, 20);
        holder.orgPrice.setText("" + goodBean.getMinPrice());
        holder.price.setText("" + goodBean.getMinPrice());

        holder.orgPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

        DisplayMetrics outMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(outMetrics);
        float widthPixel = outMetrics.widthPixels;
        float width = widthPixel / 6 - 60 - 50;


        holder.goodName.setLayoutParams(new LinearLayout.LayoutParams((int) width, 44));
        holder.goodImage.setLayoutParams(new LinearLayout.LayoutParams((int) width, (int) width));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(activity, GoodDetailView.class);
            intent.putExtra("msg", goodBean.getSGoodsId());

            Log.d("TAG", "getSGoodsId: "+goodBean.getSGoodsId());
            activity.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return goodBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView goodImage;
        private TextView goodName;
        private TextView orgPrice, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            goodImage = itemView.findViewById(R.id.good_image);
            goodName = itemView.findViewById(R.id.name);
            orgPrice = itemView.findViewById(R.id.org_price);
            price = itemView.findViewById(R.id.price);


            //goodImage.setLayoutParams(new LinearLayout.LayoutParams(goodImage.getWidth() - 200,goodImage.getWidth()-200));


        }
    }
}
