package com.example.medicalservice.fragments.submitOrderFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseFragment;
import com.example.medicalservice.bean.JudegeBean;
import com.example.medicalservice.databinding.FragmentJudegeBinding;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.Count;
import com.example.medicalservice.tools.OkHttpUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import per.wsj.library.AndRatingBar;


public class JudegeFragment extends BaseFragment<FragmentJudegeBinding> {


    private List<JudegeBean> judegeBeans;

    public JudegeFragment() {
        // Required empty public constructor
    }

    public static JudegeFragment newInstance(List<JudegeBean> judegeBeans) {
        JudegeFragment fragment = new JudegeFragment();
        Bundle args = new Bundle();

        args.putSerializable("data", (Serializable) judegeBeans);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            judegeBeans = (List<JudegeBean>) getArguments().getSerializable("data");
        }
    }

    @Override
    public void initView() {
        super.initView();

        viewBinding.service.setOnRatingChangeListener((ratingBar, rating, fromUser) -> {
            for (JudegeBean judegeBean : judegeBeans
            ) {
                judegeBean.setLogisticsScore(String.valueOf(rating));
            }

        });

        viewBinding.ems.setOnRatingChangeListener((ratingBar, rating, fromUser) -> {
            for (JudegeBean judegeBean : judegeBeans
            ) {
                judegeBean.setServiceScore(String.valueOf(rating));
            }
        });

        viewBinding.goodScore.setOnRatingChangeListener((ratingBar, rating, fromUser) -> {
            for (JudegeBean judegeBean : judegeBeans
            ) {
                judegeBean.setGoodsScore(String.valueOf(rating));
            }
        });


        viewBinding.wuyetuwer.setOnClickListener(v -> activity.finish());

        viewBinding.submit.setOnClickListener(v -> {
            if (viewBinding.goodScore.getRating() == 0f) {
                showToast("请选择此产品评分");
                return;
            }
            if (viewBinding.service.getRating() == 0f) {
                showToast("请选择服务态度评分");
                return;
            }

            if (viewBinding.ems.getRating() == 0f) {
                showToast("请选择物流评分");
                return;
            }


            for (JudegeBean judegeBean : judegeBeans) {

                float serviceScore = viewBinding.service.getRating();
                float logisticsScore = viewBinding.ems.getRating();
                float goodScore = viewBinding.goodScore.getRating();

                float AllScore = serviceScore + logisticsScore + goodScore;

                judegeBean.setCommentsText(viewBinding.commentsText.getText().toString());

                judegeBean.setComprehensiveScore(String.valueOf(Count.division(AllScore, 3)));

                judegeBean.setCommentsImages(MyApplication.getInstance().getUserHeader());

                Log.d("TAG", "initView: "+new Gson().toJson(judegeBean));


                OkHttpUtil.getInstance().doPost(API.commentsFirst, new Gson().toJson(judegeBean), new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        if (response.body() != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                if (jsonObject.getInt("code") == 200) {
                                    activity.runOnUiThread(() -> {
                                        if (judegeBeans.indexOf(judegeBean) == judegeBeans.size() - 1) {
                                            activity.finish();
                                        }

                                    });

                                } else {
                                    activity.runOnUiThread(() -> showToast("请求失败"));
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                        }

                    }
                });
            }
        });
    }
}