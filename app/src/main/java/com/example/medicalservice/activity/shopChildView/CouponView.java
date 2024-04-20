package com.example.medicalservice.activity.shopChildView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.ShopOrderBean;
import com.example.medicalservice.databinding.ActivityCouponViewBinding;
import com.example.medicalservice.recycleAdapter.CouponAdapter;
import com.example.medicalservice.recycleAdapter.ShopOrderAdapter;

import java.util.ArrayList;
import java.util.List;

public class CouponView extends BaseActivity<ActivityCouponViewBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void initView() {
        super.initView();

        viewBinding.back.setOnClickListener(v -> finish());
//        viewBinding.card.post(() -> viewBinding.coupon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, viewBinding.card.getHeight())));

        initCoupon();
    }

    private void initCoupon() {
        List<ShopOrderBean> orderBeanList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            ShopOrderBean orderBean = new ShopOrderBean();
            orderBean.setOrderNumber(String.valueOf(i));
            orderBeanList.add(orderBean);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        viewBinding.couponList.setLayoutManager(linearLayoutManager);

        CouponAdapter orderAdapter = new CouponAdapter(orderBeanList);

        viewBinding.couponList.setAdapter(orderAdapter);
    }
}