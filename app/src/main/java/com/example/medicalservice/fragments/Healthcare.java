package com.example.medicalservice.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.activity.AddUserActivity;
import com.example.medicalservice.activity.ChatView;
import com.example.medicalservice.activity.WebView;
import com.example.medicalservice.activity.healthcareChildActivity.AiChat;
import com.example.medicalservice.activity.healthcareChildActivity.AiVisitActivity;
import com.example.medicalservice.activity.healthcareChildActivity.BloodFat;
import com.example.medicalservice.activity.healthcareChildActivity.BloodOxygen;
import com.example.medicalservice.activity.healthcareChildActivity.BloodPressure;
import com.example.medicalservice.activity.healthcareChildActivity.UseMedTip;
import com.example.medicalservice.activity.healthcareChildActivity.useMadChildView.UseMadType;
import com.example.medicalservice.activity.healthcareChildActivity.AiDevice;
import com.example.medicalservice.activity.healthcareChildActivity.HealthRecords;
import com.example.medicalservice.baseFile.BaseFragment;
import com.example.medicalservice.bean.HealthInfoBean;
import com.example.medicalservice.bean.HomeCommentBean;
import com.example.medicalservice.bean.LastPhysicalExamination;
import com.example.medicalservice.bean.NewWeatherBean;
import com.example.medicalservice.bean.SleepInfoBean;
import com.example.medicalservice.bean.UseMedTipListBean;
import com.example.medicalservice.bean.UserHealthDataBean;
import com.example.medicalservice.dataBaseBean.UserBean;
import com.example.medicalservice.databinding.FragmentHealthcareBinding;
import com.example.medicalservice.recycleAdapter.HealthHomeTipAdapter;
import com.example.medicalservice.recycleAdapter.UseMedTipGridRecAdapter;
import com.example.medicalservice.recycleAdapter.UserHealthDataAdapter;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.OkHttpUtil;
import com.example.medicalservice.tools.TimeTool;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Healthcare#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Healthcare extends BaseFragment<FragmentHealthcareBinding> {

    UserHealthDataAdapter userHealthDataAdapter;

    private Healthcare() {
        // Required empty public constructor
    }

    public static Healthcare newInstance() {
        Healthcare fragment = new Healthcare();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        super.initView();


        viewBinding.voiceWeidgt.input.setOnClickListener(v -> go(ChatView.class));

        viewBinding.voiceWeidgt.reboot.setOnClickListener(v -> go(ChatView.class));

        viewBinding.switchUser.setOnClickListener(v -> go(AddUserActivity.class));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        viewBinding.userHealthDataList.setLayoutManager(linearLayoutManager);

        viewBinding.womanAi.setOnClickListener(v -> go(WebView.class, "https://robot-lib-achieve.zuoshouyisheng.com/?app_id=5a7d6767bea7c06caf241b78"));

        viewBinding.manAi.setOnClickListener(v -> go(WebView.class, "https://robot-lib-achieve.zuoshouyisheng.com/?app_id=586232fc0bdf3f6784d211bb"));

        //人工问诊
        viewBinding.linearRgwz.setOnClickListener(v -> go(WebView.class, "https://robot-lib-achieve.zuoshouyisheng.com/?app_id=586232fc0bdf3f6784d211bb"));

        viewBinding.useTipBox.setOnClickListener(v -> go(UseMedTip.class));
        viewBinding.healthRecords.setOnClickListener(view -> go(HealthRecords.class));

        viewBinding.aiDevice.setOnClickListener(view -> go(AiDevice.class));


        viewBinding.bloodPressure.setOnClickListener(v -> go(BloodPressure.class, "血压"));
        viewBinding.bloodOxygen.setOnClickListener(v -> go(BloodOxygen.class, "血氧"));
        viewBinding.bloodSugar.setOnClickListener(v -> go(BloodOxygen.class, "血糖"));
        viewBinding.temperature.setOnClickListener(v -> go(BloodOxygen.class, "体温"));
        viewBinding.pulse.setOnClickListener(v -> go(BloodOxygen.class, "脉搏"));
        viewBinding.uricAcid.setOnClickListener(v -> go(BloodOxygen.class, "尿酸"));
        viewBinding.cholesterol.setOnClickListener(v -> go(BloodOxygen.class, "胆固醇"));
        viewBinding.bloodFat.setOnClickListener(v -> go(BloodFat.class));

        getSleepInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        setUserInfo();
        initTip();
        initDevice();
        initHeathData();
        getHealthInfo();
    }

    private void setUserInfo() {


        viewBinding.userName.setText(MyApplication.getInstance().getUserName());

        GlideUtils.load(activity, MyApplication.getInstance().getUserHeader(), viewBinding.header, R.drawable.header, 90);

        //viewBinding.userHeader
    }

    List<UserHealthDataBean> userHealthDataBeans = new ArrayList<>();

    @Override
    public void initData() {
        super.initData();

        userHealthDataBeans.add(new UserHealthDataBean(getResources().getString(R.string.to_day_sleep), 0.05, "0"));

        userHealthDataBeans.add(new UserHealthDataBean(getResources().getString(R.string.week_sleep), 0.05, "0"));
//        userHealthDataBeans.add(new UserHealthDataBean(getResources().getString(R.string.to_day_walk), 0.05, "0"));
//
//        userHealthDataBeans.add(new UserHealthDataBean(getResources().getString(R.string.week_walk), 0.05, "0"));

        userHealthDataAdapter = new UserHealthDataAdapter(userHealthDataBeans, activity);
        viewBinding.userHealthDataList.setAdapter(userHealthDataAdapter);


    }

    //获取定位和天气
    private void getSleepInfo() {
        OkHttpUtil.getInstance().doGet(API.sleep_info, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() != null) {
                    SleepInfoBean bean = new Gson().fromJson(response.body().string(), SleepInfoBean.class);
                    if (bean.getCode() == 200){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                userHealthDataBeans.get(0).setProgress(Integer.parseInt(bean.getData().getTodaySleep()));
                                userHealthDataBeans.get(0).setTextValue(bean.getData().getTodaySleep());
                                userHealthDataBeans.get(1).setProgress(Integer.parseInt(bean.getData().getWeekSleep()));
                                userHealthDataBeans.get(1).setTextValue(bean.getData().getWeekSleep());
                                userHealthDataAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        });
    }

    private void initHeathData() {
        UserBean userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);

        if (userBean != null) {
            Log.i("initHeathData", userBean.getOwnerId());
            OkHttpUtil.getInstance().doGet(API.lastPhysicalExamination + userBean.getOwnerId(), new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                    if (response.body() != null) {


                        LastPhysicalExamination lastPhysicalExamination = new Gson().fromJson(response.body().string(), LastPhysicalExamination.class);

                        if (lastPhysicalExamination != null && lastPhysicalExamination.getCode() == 200) {
                            activity.runOnUiThread(() -> {


                                viewBinding.tension.setText(lastPhysicalExamination.getData().getHHypotension() + "/" + lastPhysicalExamination.getData().getHHypertension());


                                viewBinding.bloodOxygenSaturation.setText(lastPhysicalExamination.getData().getHBloodOxygenSaturation());

                                viewBinding.bloodSugarValue.setText(lastPhysicalExamination.getData().getHBloodSugarValue());

                                viewBinding.temperatureValue.setText(lastPhysicalExamination.getData().getHTemperatureValue());

                                viewBinding.uricAcidValue.setText(lastPhysicalExamination.getData().getHUricAcidValue());

                                viewBinding.cholesterolValue.setText(lastPhysicalExamination.getData().getHCholesterolContent());

                                viewBinding.hPulseRate.setText(lastPhysicalExamination.getData().getHPulseRate());

                            });
                        }

                    }
                }
            });

        }
    }

    private void initTip() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        viewBinding.tipList.setLayoutManager(linearLayoutManager);

        List<HomeCommentBean> homeCommentBeans = new ArrayList<>();

        OkHttpUtil.getInstance().doGet(API.medicinalRemindList(TimeTool.Day(0, "YYYY/MM/dd")), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                activity.runOnUiThread(() -> showToast("请求失败"));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    UseMedTipListBean useMedTipListBean = new Gson().fromJson(response.body().string(), UseMedTipListBean.class);

                    if (useMedTipListBean.getCode() == 200) {

                        for (int i = 0; i < useMedTipListBean.getData().size(); i++) {
                            UseMedTipListBean.DataDTO dataDTO = useMedTipListBean.getData().get(i);

                            for (int j = 0; j < dataDTO.getHMedicinalRemindVoList().size(); j++) {
                                UseMedTipListBean.DataDTO.HMedicinalRemindVo vo = dataDTO.getHMedicinalRemindVoList().get(j);

                                activity.runOnUiThread(() -> viewBinding.tipTime.setText(vo.getUseMedicinalTime()));

                                homeCommentBeans.add(new HomeCommentBean(vo.getMedicinalName(), String.valueOf(vo.getMedicinalNumber()), vo.getMedicinalUnit()));

                            }

                            if (homeCommentBeans.size() > 0) {
                                break;
                            }
                        }

                        activity.runOnUiThread(() -> {
                            HealthHomeTipAdapter healthHomeTipAdapter = new HealthHomeTipAdapter(homeCommentBeans);
                            viewBinding.tipList.setAdapter(healthHomeTipAdapter);

                        });

                    } else {
                        activity.runOnUiThread(() -> showToast("请求失败,请联系管理员"));
                    }
                }

            }
        });


    }

    private void initDevice() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        viewBinding.devieList.setLayoutManager(linearLayoutManager);

        List<HomeCommentBean> homeCommentBeans = new ArrayList<>();


//        homeCommentBeans.add(new HomeCommentBean("智能手环", "已连接", ""));
//        homeCommentBeans.add(new HomeCommentBean("智能床垫", "已连接", ""));
//        homeCommentBeans.add(new HomeCommentBean("智能工具箱", "已连接", ""));


        HealthHomeTipAdapter healthHomeTipAdapter = new HealthHomeTipAdapter(homeCommentBeans);
        viewBinding.devieList.setAdapter(healthHomeTipAdapter);

    }

    //获取健康状态信息
    private void getHealthInfo() {
        UserBean userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);
        if (userBean != null) {
            OkHttpUtil.getInstance().doGet(API.health_info + userBean.getOwnerId(), new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.body() != null) {
                        HealthInfoBean bean = new Gson().fromJson(response.body().string(), HealthInfoBean.class);
                        if (bean.getCode() == 200) {
                            activity.runOnUiThread(() -> {
                                viewBinding.tvHealthState.setText(bean.getData().getPhysiologicalState());
                            });
                        }
                    }
                }
            });
        }
    }


}