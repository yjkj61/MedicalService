package com.example.medicalservice.activity.cateringServicesActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.CanteenBean;
import com.example.medicalservice.databinding.ActivityCateringSelectViewBinding;
import com.example.medicalservice.recycleAdapter.CarMenuAdapter;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.OkHttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CateringSelectView extends BaseActivity<ActivityCateringSelectViewBinding> {



    @Override
    public void initView() {
        super.initView();
        viewBinding.back.setOnClickListener(v -> finish());
    }

    @Override
    public void initData() {
        super.initData();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        viewBinding.cateringList.setLayoutManager(linearLayoutManager);

        OkHttpUtil.getInstance().doGet(API.canteenList, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    CanteenBean canteenBean = new Gson().fromJson(response.body().string(), CanteenBean.class);

                  if(canteenBean.getCode() == 200){

                      runOnUiThread(() -> {
                          AdapterCantering adapterCantering = new AdapterCantering(canteenBean.getRows());
                          viewBinding.cateringList.setAdapter(adapterCantering);
                      });
                  }
                }
            }
        });
    }


    class AdapterCantering extends RecyclerView.Adapter<AdapterCantering.ViewHolder>{

        private List<CanteenBean.RowsDTO> rowsDTOS;

        public AdapterCantering(List<CanteenBean.RowsDTO> rowsDTOS) {
            this.rowsDTOS = rowsDTOS;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_type_list_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

            holder.name.setText(rowsDTOS.get(position).getRFoodCanteenName());

            holder.name.setOnClickListener(v -> {
                MyApplication.getInstance().setMarkName(rowsDTOS.get(position).getMarkerName());
                MyApplication.getInstance().setMarkId(String.valueOf(rowsDTOS.get(position).getMarkerId()));
                MyApplication.getInstance().setrFoodCanteenId(String.valueOf(rowsDTOS.get(position).getRFoodCanteenId()));
                MyApplication.getInstance().setrFoodCanteenName(rowsDTOS.get(position).getRFoodCanteenName());
                activity.finish();
            });
        }

        @Override
        public int getItemCount() {
            return rowsDTOS.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            private TextView name;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.name);
            }
        }
    }
}