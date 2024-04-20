package com.example.medicalservice.fragments.orderFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.medicalservice.baseFile.BaseFragment;
import com.example.medicalservice.bean.OrderBeanList;
import com.example.medicalservice.databinding.FragmentOrderShopBinding;
import com.example.medicalservice.recycleAdapter.ShopOrderAdapter;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.OkHttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class OrderFragmentShop extends BaseFragment<FragmentOrderShopBinding> {

    List<OrderBeanList.RowsDTO.SOrderPoListDTO> sOrderPoListDTOS = new ArrayList<>();
    private String mParam1;

    private static final String ARG_PARAM1 = "param1";

    ShopOrderAdapter orderAdapter;


    public OrderFragmentShop() {
        // Required empty public constructor
    }

    public static OrderFragmentShop newInstance(String param1) {
        OrderFragmentShop fragment = new OrderFragmentShop();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public void initView() {
        super.initView();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        viewBinding.orderList.setLayoutManager(linearLayoutManager);
        orderAdapter = new ShopOrderAdapter(sOrderPoListDTOS);
        viewBinding.orderList.setAdapter(orderAdapter);

        orderAdapter.setOnItemClickListener(position -> getOrderList());
        //getOrderList();
    }

    @Override
    public void onResume() {
        super.onResume();
        getOrderList();
    }

    private void getOrderList() {
        sOrderPoListDTOS.clear();
        OkHttpUtil.getInstance().doGet(API.orderList(mParam1), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() != null) {
                    OrderBeanList orderBeanList = new Gson().fromJson(response.body().string(), OrderBeanList.class);

                    if (orderBeanList.getCode() == 200) {
                        orderBeanList.getRows().forEach(item -> {
                            sOrderPoListDTOS.addAll(item.getSOrderPoList());
                        });


                        activity.runOnUiThread(() -> orderAdapter.notifyDataSetChanged());
                    } else {
                        activity.runOnUiThread(() -> showToast("请求失败,请联系管理员"));

                    }


                }

            }
        });
    }
}