package com.example.medicalservice.recycleAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.R;
import com.example.medicalservice.bean.BannerBean;
import com.example.medicalservice.tools.GlideUtils;
import com.youth.banner.util.BannerUtils;

import java.util.List;

public class BannerAdapter extends com.youth.banner.adapter.BannerAdapter<BannerBean, BannerAdapter.ViewHolder> {
    public BannerAdapter(List<BannerBean> datas) {
        super(datas);
    }

    @Override
    public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindView(ViewHolder holder, BannerBean data, int position, int size) {
        GlideUtils.load(holder.itemView.getContext(),data.getImageLeft(),holder.imageLeft);
        GlideUtils.load(holder.itemView.getContext(),data.getImageTop(),holder.imageTop);

        GlideUtils.load(holder.itemView.getContext(),data.getImageBottom(),holder.imageBottom);


    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageLeft,imageTop,imageBottom;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageLeft = itemView.findViewById(R.id.imageLeft);
            imageTop = itemView.findViewById(R.id.imageTop);
            imageBottom = itemView.findViewById(R.id.imageBottom);
        }
    }
}
