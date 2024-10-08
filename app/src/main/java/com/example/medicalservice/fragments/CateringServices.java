package com.example.medicalservice.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.activity.cateringServicesActivity.CateringSelectView;
import com.example.medicalservice.activity.cateringServicesActivity.EatCar1;
import com.example.medicalservice.activity.cateringServicesActivity.EatCar2;
import com.example.medicalservice.activity.cateringServicesActivity.Order.OrderListActivity;
import com.example.medicalservice.baseFile.BaseFragment;
import com.example.medicalservice.bean.CanteenBean;
import com.example.medicalservice.bean.CaterRow;
import com.example.medicalservice.bean.CateringBannerEntity;
import com.example.medicalservice.bean.Row;
import com.example.medicalservice.dataBaseBean.UserBean;
import com.example.medicalservice.databinding.FragmentCateringServicesBinding;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.OkHttpUtil;
import com.example.medicalservice.tools.ToastUtil;
import com.google.gson.Gson;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class CateringServices extends BaseFragment<FragmentCateringServicesBinding> {

    public CateringServices() {
    }

    public static CateringServices newInstance() {
        CateringServices fragment = new CateringServices();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

        viewBinding.canteenName.setText(MyApplication.getInstance().getrFoodCanteenName());

        updateMoney(handler);
        initBanner();
    }

    @Override
    public void initView() {
        super.initView();

        viewBinding.see.setOnClickListener(v -> go(OrderListActivity.class));
        viewBinding.all.setOnClickListener(v -> go(OrderListActivity.class));

        viewBinding.btnLeft.setOnClickListener(v -> {

            if (!MyApplication.getInstance().getMarkId().isEmpty()) {
                go(EatCar1.class, "0");
            }else {
                showToast("无食堂供应");
            }


        });
        viewBinding.btnRight.setOnClickListener(v -> {
            if (!MyApplication.getInstance().getMarkId().isEmpty()) {
                go(EatCar2.class, "1");
            }else {
                showToast("无食堂供应");
            }

        });


        viewBinding.catering.setOnClickListener(v -> go(CateringSelectView.class));
        getCanteen();
    }

    Handler handler = new Handler(msg -> {

        String money = (String) msg.obj;
        viewBinding.ownerRemainMoney.setText("余额：" + money);
        return false;
    });

    private void getCanteen() {

        OkHttpUtil.getInstance().doGet(API.canteenList, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                activity.runOnUiThread(() -> viewBinding.canteenName.setText("暂无食堂供应"));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    CanteenBean canteenBean = new Gson().fromJson(response.body().string(), CanteenBean.class);

                    if(canteenBean.getCode() == 200){
                        if (canteenBean.getRows().size() > 0) {
                            activity.runOnUiThread(() -> viewBinding.canteenName.setText(canteenBean.getRows().get(0).getRFoodCanteenName()));


                            MyApplication.getInstance().setMarkId(String.valueOf(canteenBean.getRows().get(0).getMarkerId()));
                            MyApplication.getInstance().setMarkName(canteenBean.getRows().get(0).getMarkerName());
                            MyApplication.getInstance().setrFoodCanteenId(String.valueOf(canteenBean.getRows().get(0).getRFoodCanteenId()));
                            MyApplication.getInstance().setrFoodCanteenName(canteenBean.getRows().get(0).getRFoodCanteenName());
                        } else {
                            activity.runOnUiThread(() -> viewBinding.canteenName.setText("暂无食堂供应"));
                        }
                    }else {
                        activity.runOnUiThread(() -> viewBinding.canteenName.setText("暂无食堂供应"));
                    }

                }
            }
        });

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("markerId", "1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    private void initBanner(){
        List<UserBean> allUser = MyApplication.getInstance().db.userDao().getAllUser();
        Boolean isLogin = false;
        for (UserBean user:allUser) {
            if(user.isLoginStatus()){
                isLogin = true;
                break;
            }
        }
        if(allUser.size() == 0 || !isLogin){
            //用户未登录，加载默认轮播图
            viewBinding.defaultBannerImage.setVisibility(View.VISIBLE);
            viewBinding.realBanner.setVisibility(View.GONE);
            return;
        }
        viewBinding.defaultBannerImage.setVisibility(View.GONE);
        viewBinding.realBanner.setVisibility(View.VISIBLE);
        OkHttpUtil.getInstance().doGet(API.cateringBannerUrl, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody body = response.body();
                if(body != null){
                    CateringBannerEntity bannerEntity = new Gson().fromJson(body.string(), CateringBannerEntity.class);
                    List<CaterRow> rows = bannerEntity.component3();
                    if(rows.isEmpty()){
                        return;
                    }
                    String[] split = rows.get(0).getImage().split(",");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewBinding.realBanner.isAutoLoop(true).setAdapter(new BannerImageAdapter<String>(Arrays.asList(split)) {
                                @Override
                                public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                                    GlideUtils.load(getContext(),data,holder.imageView);
                                }
                            });
                        }
                    });
                }
            }
        });
    }
}