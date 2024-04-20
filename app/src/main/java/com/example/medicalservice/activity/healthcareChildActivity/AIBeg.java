package com.example.medicalservice.activity.healthcareChildActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.databinding.ActivityAibegBinding;
import com.example.medicalservice.fragments.healthAiBegFragments.DayHealthFragment;
import com.example.medicalservice.fragments.healthAiBegFragments.MonthHealthFragment;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

public class AIBeg extends BaseActivity<ActivityAibegBinding> {

    private final String[] mTitles = {
            "日", "周", "月"
    };
    private ArrayList<Fragment> fragments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        super.initView();
        viewBinding.back.setOnClickListener(v -> finish());

        fragments.add(DayHealthFragment.newInstance());
        fragments.add(MonthHealthFragment.newInstance("周"));
        fragments.add(MonthHealthFragment.newInstance("月"));

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
                return mTitles[position];
            }
        });

        viewBinding.viewPager.setOffscreenPageLimit(4);

        viewBinding.tl2.setViewPager(viewBinding.viewPager);

        viewBinding.viewPager.setCurrentItem(0);
        viewBinding.viewPager.setNoScroll(true);

        for (int i = 0; i < viewBinding.tl2.getTabCount(); i++) {
            if (0 == i) {

                viewBinding.tl2.getTitleView(0).setBackgroundResource(R.drawable.health_tab_select_back);
                viewBinding.tl2.getTitleView(0).setLayoutParams(new RelativeLayout.LayoutParams(150,60));
                viewBinding.tl2.getTitleView(0).setTranslationY(7);

            } else {
                viewBinding.tl2.getTitleView(i).setBackgroundResource(R.drawable.health_tab_unselect_back);
                viewBinding.tl2.getTitleView(i).setLayoutParams(new RelativeLayout.LayoutParams(150,60));
                viewBinding.tl2.getTitleView(i).setTranslationY(7);

            }
        }

    }

    @Override
    public void initData() {
        super.initData();
        viewControl();


    }

    private void viewControl(){
        viewBinding.tl2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

                viewBinding.viewPager.setCurrentItem(position);

                for (int i = 0; i < viewBinding.tl2.getTabCount(); i++) {
                    if (position == i) {

                        viewBinding.tl2.getTitleView(position).setBackgroundResource(R.drawable.health_tab_select_back);
                        viewBinding.tl2.getTitleView(0).setLayoutParams(new RelativeLayout.LayoutParams(150,60));
                        viewBinding.tl2.getTitleView(0).setTranslationY(7);

                    } else {
                        viewBinding.tl2.getTitleView(i).setBackgroundResource(R.drawable.health_tab_unselect_back);
                        viewBinding.tl2.getTitleView(i).setLayoutParams(new RelativeLayout.LayoutParams(150,60));
                        viewBinding.tl2.getTitleView(i).setTranslationY(7);

                    }
                }
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

    }

    public static class SleepBean{
        private String name;
        private String data;

        public SleepBean(String name, String data) {
            this.name = name;
            this.data = data;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}