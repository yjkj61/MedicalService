package com.example.medicalservice.activity.cateringServicesActivity.Order.OrderFragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseFragment;
import com.example.medicalservice.bean.EatOrderListBean;
import com.example.medicalservice.dataBaseBean.UserBean;
import com.example.medicalservice.databinding.FragmentEatOrderBinding;
import com.example.medicalservice.interfaceCallback.OnClickListener;
import com.example.medicalservice.recycleAdapter.EatOrderListAdapter;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.OkHttpUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class EatOrderFragment extends BaseFragment<FragmentEatOrderBinding> {
    private static final String ARG_PARAM1 = "param1";
    private UserBean userBean = null;


    EatOrderListAdapter eatOrderListAdapter;
    private List<EatOrderListBean.RowsDTO> rowsDTOS = new ArrayList<>();
    private String mParam1;

    public static EatOrderFragment newInstance(String param1) {
        EatOrderFragment fragment = new EatOrderFragment();
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

        userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);
    }

    @Override
    public void initData()  {
        super.initData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        viewBinding.orderList.setLayoutManager(linearLayoutManager);
        eatOrderListAdapter = new EatOrderListAdapter(rowsDTOS);
        viewBinding.orderList.setAdapter(eatOrderListAdapter);

        eatOrderListAdapter.setOnItemClickListener(position -> getOrder(""));
        getOrder("");
    }

    public void getOrder(String rOrderId) {
        Log.d("TAG", "getOrder: " + rOrderId);
        rowsDTOS.clear();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ownerId", userBean.getOwnerId());
            //jsonObject.put("markerId", MyApplication.getInstance().getMarkId());
            jsonObject.put("rOrderStatus", mParam1);
            jsonObject.put("rOrderId", rOrderId);
            OkHttpUtil.getInstance().doPost(API.eatOrderList, jsonObject.toString(), new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                    if (response.body() != null) {
                        EatOrderListBean eatOrderListBean = new Gson().fromJson(response.body().string(), EatOrderListBean.class);
                        rowsDTOS.addAll(eatOrderListBean.getRows()) ;

                        activity.runOnUiThread(() -> {


                            eatOrderListAdapter.notifyDataSetChanged();


                        });
                    }


                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


}