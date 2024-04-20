package com.example.medicalservice.activity.shopChildView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.databinding.ActivityOrderViewShopBinding;
import com.example.medicalservice.fragments.orderFragment.OrderFragment;
import com.example.medicalservice.fragments.orderFragment.OrderFragmentShop;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

public class OrderViewShop extends BaseActivity<ActivityOrderViewShopBinding> {

    private String[] orderList = {"全部订单", "待发货", "待收货", "待评价"};

    private String currentItem = "0";

    private ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        super.initView();
        currentItem = getIntent().getStringExtra("msg");
        viewBinding.back.setOnClickListener(v -> finish());
    }

    @Override
    public void initData() {
        super.initData();

        fragments.add(OrderFragmentShop.newInstance(""));
        fragments.add(OrderFragmentShop.newInstance("3"));
        fragments.add(OrderFragmentShop.newInstance("5"));
        fragments.add(OrderFragmentShop.newInstance("6"));

        viewBinding.viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.size();
            }

            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return orderList[position];
            }
        });
        viewBinding.viewPager.setOffscreenPageLimit(4);

        viewBinding.tl2.setViewPager(viewBinding.viewPager);

        viewBinding.viewPager.setCurrentItem(Integer.parseInt(currentItem));

        viewControl();
    }

    private void viewControl() {
        viewBinding.tl2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewBinding.viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        viewBinding.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewBinding.tl2.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
