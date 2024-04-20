package com.example.medicalservice.activity.healthcareChildActivity.bloodDataChildView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.AcidListBean;
import com.example.medicalservice.bean.BloodFatBean;
import com.example.medicalservice.bean.BloodPressureListBean;
import com.example.medicalservice.bean.BloodSugarListBean;
import com.example.medicalservice.bean.CholesterolListBean;
import com.example.medicalservice.bean.OxygenListBean;
import com.example.medicalservice.bean.RateListBean;
import com.example.medicalservice.bean.TemperatureListBean;
import com.example.medicalservice.dataBaseBean.UserBean;
import com.example.medicalservice.databinding.ActivityBloodHistoryBinding;
import com.example.medicalservice.recycleAdapter.BloodOxygenHistoryAdapter2;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.OkHttpUtil;
import com.google.gson.Gson;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BloodHistory extends BaseActivity<ActivityBloodHistoryBinding> {

    private int pageNum = 1;

    private final List<BloodPressureListBean.RowsDTO> bloodPressureList = new ArrayList<>();

    private final List<OxygenListBean.RowsDTO> oxygenList = new ArrayList<>();

    private final List<BloodSugarListBean.RowsDTO> bloodSugarList = new ArrayList<>();

    private final List<CholesterolListBean.RowsDTO> cholesterolList = new ArrayList<>();

    private final List<AcidListBean.RowsDTO> acidList = new ArrayList<>();

    private final List<TemperatureListBean.RowsDTO> temperatureList = new ArrayList<>();

    private final List<RateListBean.RowsDTO> rateList = new ArrayList<>();

    private final List<BloodFatBean.RowsDTO> bloodFatList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        super.initView();

        viewBinding.back.setOnClickListener(v -> finish());


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        viewBinding.table.setLayoutManager(linearLayoutManager);


    }

    @Override
    public void initData() {
        super.initData();
        String type = getIntent().getStringExtra("msg");
        String api = getIntent().getStringExtra("msg2");

        Log.d("TAG", api);
        getList(type, api);

        viewBinding.refresh.setOnLoadMoreListener(refreshLayout -> {

            if (!viewBinding.refresh.isLoading()) {
                pageNum += 1;
                getList(type, api);
            }
        });
    }

    private void getList(String type, String api) {

        UserBean userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);
        OkHttpUtil.getInstance().doGet(api + "?ownerId=" + userBean.getOwnerId() + "&pageNum=" + pageNum + "&pageSize=10", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() != null) {

                    switch (type) {
                        case "xueya":
                            BloodPressureListBean bean = new Gson().fromJson(response.body().string(), BloodPressureListBean.class);
                            bloodPressureList.addAll(bean.getRows());

                            runOnUiThread(() -> {
                                BloodOxygenHistoryAdapter2 bloodOxygenHistoryAdapter2 = new BloodOxygenHistoryAdapter2(bloodPressureList);
                                bloodOxygenHistoryAdapter2.setShowType(type);
                                viewBinding.table.setAdapter(bloodOxygenHistoryAdapter2);
                            });


                            break;
                        case "xueyang":
                            OxygenListBean oxygenListBean = new Gson().fromJson(response.body().string(), OxygenListBean.class);
                            oxygenList.addAll(oxygenListBean.getRows());
                            runOnUiThread(() -> {
                                BloodOxygenHistoryAdapter2 bloodOxygenHistoryAdapter2 = new BloodOxygenHistoryAdapter2(oxygenList);
                                bloodOxygenHistoryAdapter2.setShowType(type);
                                viewBinding.table.setAdapter(bloodOxygenHistoryAdapter2);
                            });
                            break;
                        case "xuetang":
                            BloodSugarListBean bloodSugarListBean = new Gson().fromJson(response.body().string(), BloodSugarListBean.class);
                            bloodSugarList.addAll(bloodSugarListBean.getRows());
                            runOnUiThread(() -> {
                                BloodOxygenHistoryAdapter2 bloodOxygenHistoryAdapter2 = new BloodOxygenHistoryAdapter2(bloodSugarList);
                                bloodOxygenHistoryAdapter2.setShowType(type);
                                viewBinding.table.setAdapter(bloodOxygenHistoryAdapter2);
                            });
                            break;

                        case "cholesterol":
                            CholesterolListBean cholesterolListBean = new Gson().fromJson(response.body().string(), CholesterolListBean.class);
                            cholesterolList.addAll(cholesterolListBean.getRows());
                            runOnUiThread(() -> {
                                BloodOxygenHistoryAdapter2 bloodOxygenHistoryAdapter2 = new BloodOxygenHistoryAdapter2(cholesterolList);
                                bloodOxygenHistoryAdapter2.setShowType(type);
                                viewBinding.table.setAdapter(bloodOxygenHistoryAdapter2);
                            });
                            break;
                        case "acid":
                            AcidListBean acidListBean = new Gson().fromJson(response.body().string(), AcidListBean.class);
                            acidList.addAll(acidListBean.getRows());
                            runOnUiThread(() -> {
                                BloodOxygenHistoryAdapter2 bloodOxygenHistoryAdapter2 = new BloodOxygenHistoryAdapter2(acidList);
                                bloodOxygenHistoryAdapter2.setShowType(type);
                                viewBinding.table.setAdapter(bloodOxygenHistoryAdapter2);
                            });
                            break;
                        case "temperature":
                            TemperatureListBean temperatureListBean = new Gson().fromJson(response.body().string(), TemperatureListBean.class);
                            temperatureList.addAll(temperatureListBean.getRows());
                            runOnUiThread(() -> {
                                BloodOxygenHistoryAdapter2 bloodOxygenHistoryAdapter2 = new BloodOxygenHistoryAdapter2(temperatureList);
                                bloodOxygenHistoryAdapter2.setShowType(type);
                                viewBinding.table.setAdapter(bloodOxygenHistoryAdapter2);
                            });
                            break;
                        case "rate":
                            RateListBean rateListBean = new Gson().fromJson(response.body().string(), RateListBean.class);
                            rateList.addAll(rateListBean.getRows());
                            runOnUiThread(() -> {
                                BloodOxygenHistoryAdapter2 bloodOxygenHistoryAdapter2 = new BloodOxygenHistoryAdapter2(rateList);
                                bloodOxygenHistoryAdapter2.setShowType(type);
                                viewBinding.table.setAdapter(bloodOxygenHistoryAdapter2);
                            });
                            break;

                        case "totalCholesterol":
                        case "glycerol":
                        case "lowDensityLipoprotein":
                        case "highDensityLipoprotein":
                            BloodFatBean bloodFatBean = new Gson().fromJson(response.body().string(), BloodFatBean.class);
                            bloodFatList.addAll(bloodFatBean.getRows());
                            runOnUiThread(() -> {
                                BloodOxygenHistoryAdapter2 bloodOxygenHistoryAdapter2 = new BloodOxygenHistoryAdapter2(bloodFatList);
                                bloodOxygenHistoryAdapter2.setShowType(type);
                                viewBinding.table.setAdapter(bloodOxygenHistoryAdapter2);
                            });
                            break;
                    }


                }
            }
        });
    }
}