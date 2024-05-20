package com.example.medicalservice.fragments.goodDetailChildView;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseFragment;
import com.example.medicalservice.bean.AfterSalesBean;
import com.example.medicalservice.bean.SpecificationBean;
import com.example.medicalservice.databinding.FragmentAfterSaleBinding;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.OkHttpUtil;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AfterSaleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AfterSaleFragment extends BaseFragment<FragmentAfterSaleBinding> {

    public String goodId = "";

    private AfterSaleFragment() {
        // Required empty public constructor
    }

    public static AfterSaleFragment newInstance(String params) {
        AfterSaleFragment fragment = new AfterSaleFragment();
        Bundle args = new Bundle();
        args.putString("params", params);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            goodId = getArguments().getString("params");
        }
    }

    @Override
    public void initView() {
        super.initView();

        getAfterSales();
    }

    @Override
    public void initData() {
        super.initData();
    }

    //获取售后
    public void getAfterSales(){
        OkHttpUtil.getInstance().doGet(API.AfterSales + goodId, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                AfterSalesBean bean = new Gson().fromJson(response.body().string(), AfterSalesBean.class);
                if (bean != null && bean.getCode() == 200){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (bean.getData().getAfterSalesGuarantee() != null && !"".equals(bean.getData().getAfterSalesGuarantee())){
                                viewBinding.tvContent.setText(Html.fromHtml(bean.getData().getAfterSalesGuarantee()));
                            }
                        }
                    });
                }
            }
        });
    }
}