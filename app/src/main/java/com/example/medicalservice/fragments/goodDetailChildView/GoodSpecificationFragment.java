package com.example.medicalservice.fragments.goodDetailChildView;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseFragment;
import com.example.medicalservice.bean.SpecificationBean;
import com.example.medicalservice.databinding.FragmentGoodSpecificationBinding;
import com.example.medicalservice.recycleAdapter.CarMenuAdapter;
import com.example.medicalservice.recycleAdapter.SpecificationAdapter;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.OkHttpUtil;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoodSpecificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoodSpecificationFragment extends BaseFragment<FragmentGoodSpecificationBinding> {

    public String goodId = "";

    SpecificationAdapter adapter;

    List<SpecificationBean.DataDTO> list = new ArrayList<>();

    private GoodSpecificationFragment() {
        // Required empty public constructor
    }

    public static GoodSpecificationFragment newInstance(String params) {
        GoodSpecificationFragment fragment = new GoodSpecificationFragment();
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

        adapter = new SpecificationAdapter(list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        viewBinding.recyclerview.setLayoutManager(linearLayoutManager);

        viewBinding.recyclerview.setAdapter(adapter);

        getSpecification();
    }

    @Override
    public void initData()  {
        super.initData();
    }

    //获取规格和包装
    public void getSpecification(){
        OkHttpUtil.getInstance().doGet(API.Specifications + goodId, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                SpecificationBean bean = new Gson().fromJson(response.body().string(), SpecificationBean.class);
                if (bean != null && bean.getCode() == 200){
                    list.clear();
                    list.addAll(bean.getData());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }
}