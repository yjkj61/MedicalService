package com.example.medicalservice.activity.cateringServicesActivity.Order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.medicalservice.R;
import com.example.medicalservice.activity.cateringServicesActivity.Order.OrderFragments.EatOrderFragment;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.databinding.ActivityOrderListBinding;
import com.example.medicalservice.fragments.orderFragment.OrderFragmentShop;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

public class OrderListActivity extends BaseActivity<ActivityOrderListBinding> {

    private String[] orderList = {"全部订单", "待送达", "待收货", "待评价", "退款/售后"};
    private ArrayList<Fragment> fragments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        super.initView();

        viewBinding.back.setOnClickListener(v -> finish());
    }

    @Override
    public void initData() {
        super.initData();


        EatOrderFragment fragment1 = EatOrderFragment.newInstance("");
        EatOrderFragment fragment2 = EatOrderFragment.newInstance("0");
        EatOrderFragment fragment3 = EatOrderFragment.newInstance("1");
        EatOrderFragment fragment4 = EatOrderFragment.newInstance("2");
        EatOrderFragment fragment5 = EatOrderFragment.newInstance("4");


        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        fragments.add(fragment5);


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
        viewBinding.viewPager.setOffscreenPageLimit(0);

        viewBinding.tl2.setViewPager(viewBinding.viewPager);

        viewBinding.viewPager.setCurrentItem(0);

        viewControl();


        viewBinding.rOrderInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                switch (viewBinding.viewPager.getCurrentItem()) {
                    case 0:
                        fragment1.getOrder(s.toString());
                        break;
                    case 1:
                        fragment2.getOrder(s.toString());
                        break;
                    case 2:
                        fragment3.getOrder(s.toString());
                        break;
                    case 3:
                        fragment4.getOrder(s.toString());
                        break;
                    case 4:
                        fragment5.getOrder(s.toString());
                        break;
                }


            }
        });
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