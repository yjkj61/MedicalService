package com.example.medicalservice.activity.healthcareChildActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.databinding.ActivityMyStepBinding;
import com.example.medicalservice.fragments.healthAiBegFragments.DayHealthFragment;
import com.example.medicalservice.fragments.healthAiBegFragments.MonthHealthFragment;
import com.example.medicalservice.fragments.healthMyStepFragments.DayFragment;
import com.example.medicalservice.fragments.healthMyStepFragments.WeekAndMonthFragment;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

public class MyStep extends BaseActivity<ActivityMyStepBinding> {

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

        fragments.add(DayFragment.newInstance());
        fragments.add(WeekAndMonthFragment.newInstance("周"));
        fragments.add(WeekAndMonthFragment.newInstance("月"));

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
                viewBinding.tl2.getTitleView(0).setLayoutParams(new RelativeLayout.LayoutParams(150, 60));
                viewBinding.tl2.getTitleView(0).setTranslationY(7);

            } else {
                viewBinding.tl2.getTitleView(i).setBackgroundResource(R.drawable.health_tab_unselect_back);
                viewBinding.tl2.getTitleView(i).setLayoutParams(new RelativeLayout.LayoutParams(150, 60));
                viewBinding.tl2.getTitleView(i).setTranslationY(7);

            }
        }
    }

    @Override
    public void initData() {
        super.initData();
        viewControl();

        viewBinding.totalAvgStep.setText("20000步");
        viewBinding.totalStep.setText("20001步");

    }

    private void viewControl() {
        viewBinding.tl2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

                viewBinding.viewPager.setCurrentItem(position);

                for (int i = 0; i < viewBinding.tl2.getTabCount(); i++) {
                    if (position == i) {

                        viewBinding.tl2.getTitleView(position).setBackgroundResource(R.drawable.health_tab_select_back);
                        viewBinding.tl2.getTitleView(0).setLayoutParams(new RelativeLayout.LayoutParams(150, 60));
                        viewBinding.tl2.getTitleView(0).setTranslationY(7);

                    } else {
                        viewBinding.tl2.getTitleView(i).setBackgroundResource(R.drawable.health_tab_unselect_back);
                        viewBinding.tl2.getTitleView(i).setLayoutParams(new RelativeLayout.LayoutParams(150, 60));
                        viewBinding.tl2.getTitleView(i).setTranslationY(7);

                    }
                }
            }

            @Override
            public void onTabReselect(int position) {
            }
        });


    }
}