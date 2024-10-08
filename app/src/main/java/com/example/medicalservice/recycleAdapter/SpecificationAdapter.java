package com.example.medicalservice.recycleAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.R;
import com.example.medicalservice.bean.CarFoodListBean;
import com.example.medicalservice.bean.SpecificationBean;
import com.example.medicalservice.interfaceCallback.OnClickByType;
import com.example.medicalservice.tools.GlideUtils;

import java.text.DecimalFormat;
import java.util.List;

public class SpecificationAdapter extends RecyclerView.Adapter<SpecificationAdapter.ViewHolder> {

    List<SpecificationBean.DataDTO> list;


    public SpecificationAdapter(List<SpecificationBean.DataDTO> mlist) {
        this.list = mlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_specification, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SpecificationBean.DataDTO bean = list.get(position);

        holder.tv_title.setText(bean.getAttributeParam());
        String content = "";
        for (String value: bean.getAttributeValueList()){
            if ("".equals(content)){
                content = value;
            }else{
                content = content + "," + value;
            }
        }
        holder.tv_content.setText(content);


    }

    private OnClickByType onClickListener;

    public void setOnItemClickListener(OnClickByType onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title, tv_content;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);

        }
    }
}
