package com.example.medicalservice.fragments.healthPhysicalExaminationFragments;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.medicalservice.MyApplication;
import com.example.medicalservice.activity.healthcareChildActivity.useMadChildView.AddMadActivity;
import com.example.medicalservice.activity.healthcareChildActivity.useMadChildView.UseMadType;
import com.example.medicalservice.activity.healthcareChildActivity.useMadChildView.addMedicineChildView.FrequencyAdministration;
import com.example.medicalservice.activity.healthcareChildActivity.useMadChildView.addMedicineChildView.MedicineConfig;
import com.example.medicalservice.baseFile.BaseFragment;
import com.example.medicalservice.databinding.FragmentNeedSpaceAreaBinding;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.DataEdit;
import com.example.medicalservice.tools.OkHttpUtil;
import com.example.medicalservice.tools.TimeTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class FragmentNeedSpaceArea extends BaseFragment<FragmentNeedSpaceAreaBinding> {

    private JSONObject medInfo;

    public FragmentNeedSpaceArea() {
        // Required empty public constructor
    }


    public static FragmentNeedSpaceArea newInstance(String params) {
        FragmentNeedSpaceArea fragment = new FragmentNeedSpaceArea();
        Bundle args = new Bundle();
        args.putString("params", params);

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void initView() {
        super.initView();
        try {
            medInfo = new JSONObject(getArguments().getString("params"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        viewBinding.nextStep.setOnClickListener(v -> {
            try {
                setMedData();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setMedData() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        if(DataEdit.getInstance().getRowsDTO()!=null){
            jsonObject.put("id", DataEdit.getInstance().getRowsDTO().getId());
        }
        jsonObject.put("userId", MyApplication.getInstance().getUserId());
        jsonObject.put("medicinalName", medInfo.getString("name"));
        jsonObject.put("medicinalType", medInfo.getString("type"));
        jsonObject.put("medicinalNumber", medInfo.getString("number"));
        jsonObject.put("medicinalUnit", medInfo.getString("unit"));
        jsonObject.put("status", "0");
        jsonObject.put("remindType", "2");
        jsonObject.put("img",medInfo.getString("img"));
        jsonObject.put("beginTime", TimeTool.Day(0,"YYYY-MM-dd"));
        jsonObject.put("remindTime", "");

        Log.d("TAG", "setData: "+jsonObject.toString());

        OkHttpUtil.getInstance().doPost(API.medicinalRemind, jsonObject.toString(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if(response.body()!=null){
                    try {
                        JSONObject data = new JSONObject(response.body().string());
                        if(data.getInt("code") ==200){
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    FrequencyAdministration.mActivity.finish();
                                    MedicineConfig.mActivity.finish();
                                    UseMadType.mActivity.finish();
                                    AddMadActivity.mActivity.finish();
                                    showToast("创建成功");
                                }
                            });
                        }else {

                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });
    }


}