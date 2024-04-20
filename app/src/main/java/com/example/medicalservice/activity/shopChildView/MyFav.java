package com.example.medicalservice.activity.shopChildView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.R;
import com.example.medicalservice.activity.healthcareChildActivity.AiDevice;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.MyFavBean;
import com.example.medicalservice.databinding.ActivityMyFavBinding;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.OkHttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyFav extends BaseActivity<ActivityMyFavBinding> {

    private List<MyFavBean.RowsDTO> rowsDTOS = new ArrayList<>();
    private MsAdapter msAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        super.initView();

        viewBinding.back.setOnClickListener(v -> finish());


    }

    @Override
    public void initData() {
        super.initData();
        DisplayMetrics outMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(outMetrics);
        float widthPixel = outMetrics.widthPixels;
        float width = widthPixel / 2 - 100;


        msAdapter = new MsAdapter<MyFavBean.RowsDTO>(rowsDTOS, R.layout.my_fav_item) {

            @Override
            public void bindView(ViewHolder holder, MyFavBean.RowsDTO obj) {

                ImageView good_image = holder.getView(R.id.good_image);
                TextView good_name = holder.getView(R.id.good_name);
                TextView collectCount = holder.getView(R.id.collectCount);
                TextView money = holder.getView(R.id.money);
                LinearLayout cancel = holder.getView(R.id.cancel);
                LinearLayout detail = holder.getView(R.id.detail);

                cancel.setOnClickListener(v -> deleteDialog(obj.getSGoodsCollectId()));

                detail.setOnClickListener(v -> go(GoodDetailView.class, String.valueOf(obj.getSGoodsId())));


                GlideUtils.load(activity, obj.getGoodsCoverImages(), good_image, R.drawable.good_test, 20);

                good_name.setText(obj.getGoodsName());
                collectCount.setText(obj.getCollectCount() + "人收藏");
                money.setText(obj.getPrice() + "");

            }
        };
        viewBinding.gridList.setAdapter(msAdapter);

        getGoodList();

    }

    private void getGoodList() {
        rowsDTOS.clear();
        OkHttpUtil.getInstance().doGet(API.collectList, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    MyFavBean myFavBean = new Gson().fromJson(response.body().string(), MyFavBean.class);
                    if (myFavBean.getCode() == 200) {

                        rowsDTOS.addAll(myFavBean.getRows());
                        runOnUiThread(msAdapter::notifyDataSetChanged);

                    } else {
                        runOnUiThread(() -> showToast("请求失败,请联系管理员"));

                    }
                }

            }
        });
    }


    private void deleteDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Dialog_style);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_delete, null);

        builder.setView(view);
        TextView left_btn, right_btn, text;
        left_btn = view.findViewById(R.id.yes);
        right_btn = view.findViewById(R.id.no);
        text = view.findViewById(R.id.tip_text);


        text.setText("是否取消收藏");

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


        left_btn.setOnClickListener(v1 -> OkHttpUtil.getInstance().doDelete(API.collectCancel(id), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {


                runOnUiThread(() -> {
                    alertDialog.dismiss();
                    getGoodList();
                });

            }
        }));

        right_btn.setOnClickListener(v1 -> alertDialog.dismiss());


    }

}