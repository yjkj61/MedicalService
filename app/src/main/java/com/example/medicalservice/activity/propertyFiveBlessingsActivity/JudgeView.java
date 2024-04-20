package com.example.medicalservice.activity.propertyFiveBlessingsActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.Code200;
import com.example.medicalservice.databinding.ActivityJudgeViewBinding;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.OkHttpUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class JudgeView extends BaseActivity<ActivityJudgeViewBinding> {


    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {
        super.initData();

        String bookNum = getIntent().getStringExtra("msg");
        viewBinding.back.setOnClickListener(v -> finish());

        viewBinding.btnBox.setOnClickListener(v -> {
            if (viewBinding.advice.getText().toString().isEmpty()) {
                showToast("请填写评价");
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("bookNum", bookNum);
                jsonObject.put("serviceStatus", 4);

                jsonObject.put("bookAdvice", viewBinding.advice.getText().toString());


                OkHttpUtil.getInstance().doPost(API.updateBookByBookNum, jsonObject.toString(), new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        if (response.body() != null) {
                            Code200 code200 = new Gson().fromJson(response.body().string(), Code200.class);
                            if (code200.getCode() == 200) {
                                runOnUiThread(() -> {
                                    showToast("评价成功");
                                    finish();
                                });
                            }
                        }

                    }
                });

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });


    }
}