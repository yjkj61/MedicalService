package com.example.medicalservice.activity.shopChildView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.GoodBean;
import com.example.medicalservice.databinding.ActivityLimitedTimeShopBinding;
import com.example.medicalservice.fragments.MallGroupBuying;
import com.example.medicalservice.recycleAdapter.GoodsListAdapter;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.OkHttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LimitedTimeShop extends BaseActivity<ActivityLimitedTimeShopBinding> {

    @Override
    public void initView() {
        super.initView();
        viewBinding.back.setOnClickListener(v -> finish());
    }

    @Override
    public void initData() {
        super.initData();

        OkHttpUtil.getInstance().doGet(API.limitedTime, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() != null) {
                    String json = response.body().string();

                    if (json.isEmpty()) return;
                    MallGroupBuying.LimitGood goodBean = new Gson().fromJson(json, MallGroupBuying.LimitGood.class);
                    if (goodBean.getCode() == 200) {
                        activity.runOnUiThread(() -> initGoodList(goodBean.getData()));

                    } else {
                        activity.runOnUiThread(() -> showToast("请求失败,请联系管理员"));
                    }
                }

            }
        });
    }


    private void initGoodList(List<GoodBean.RowsDTO> rowsDTOS) {


        MsAdapter<GoodBean.RowsDTO> msAdapter = new MsAdapter<GoodBean.RowsDTO>(rowsDTOS, R.layout.goods_item) {

            @SuppressLint("SetTextI18n")
            @Override
            public void bindView(ViewHolder holder, GoodBean.RowsDTO goodBean) {
                ImageView goodImage = holder.getView(R.id.good_image);
                TextView goodName = holder.getView(R.id.name);
                TextView orgPrice = holder.getView(R.id.org_price);
                TextView price = holder.getView(R.id.price);

                LinearLayout box = holder.getView(R.id.box);
                box.setBackgroundColor(Color.parseColor("#FFF8F7FC"));


                goodName.setText(goodBean.getGoodsName());

                GlideUtils.load(activity, goodBean.getGoodsCoverImages(), goodImage, R.drawable.good_test, 20);
                orgPrice.setText("" + goodBean.getMinPrice());
                price.setText("" + goodBean.getMinPrice());

                orgPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);


                DisplayMetrics outMetrics = new DisplayMetrics();
                activity.getWindowManager().getDefaultDisplay().getRealMetrics(outMetrics);
                float widthPixel = outMetrics.widthPixels;
                float width = widthPixel / 5 - 60 - 50;


                goodName.setLayoutParams(new LinearLayout.LayoutParams((int) width, 44));
                goodImage.setLayoutParams(new LinearLayout.LayoutParams((int) width, (int) width));

                goodImage.setOnClickListener(v -> go(GoodDetailView.class, goodBean.getSGoodsId()));

            }
        };

        viewBinding.goodList.setAdapter(msAdapter);


    }
}