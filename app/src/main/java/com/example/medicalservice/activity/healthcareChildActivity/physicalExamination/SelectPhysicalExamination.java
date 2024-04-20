package com.example.medicalservice.activity.healthcareChildActivity.physicalExamination;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.medicalservice.MainActivityPageAdapter;
import com.example.medicalservice.activity.healthcareChildActivity.useMadChildView.addMedicineChildView.FrequencyAdministration;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.databinding.ActivitySelectPhysicalExaminationBinding;
import com.example.medicalservice.fragments.healthPhysicalExaminationFragments.FragmentNeedSpaceArea;
import com.example.medicalservice.fragments.healthPhysicalExaminationFragments.FragmentNoChangeTimeSpaceArea;
import com.example.medicalservice.fragments.healthPhysicalExaminationFragments.FragmentSpecialTime;
import com.example.medicalservice.tools.DataEdit;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectPhysicalExamination extends BaseActivity<ActivitySelectPhysicalExaminationBinding> {


    ArrayList<Fragment> fragments = new ArrayList<>();

    public static SelectPhysicalExamination mActivity;

    private PhysicalExaminationTip.PhysicalBean.DataDTO dataDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        super.initView();
        mActivity = this;


        selectFragment("0");

        viewBinding.fragment1.setOnClickListener(view -> {
            selectFragment("0");
            viewBinding.viewPager.setCurrentItem(0);
        });
        viewBinding.fragment2.setOnClickListener(view -> {
            selectFragment("1");
            viewBinding.viewPager.setCurrentItem(1);
        });
        viewBinding.fragment3.setOnClickListener(view -> {
            selectFragment("2");
            viewBinding.viewPager.setCurrentItem(2);
        });


        viewBinding.back.setOnClickListener(v -> finish());


        dataDTO = DataEdit.getInstance().getDataDTO();

        if (dataDTO != null) {
            viewBinding.viewPager.setCurrentItem(Integer.parseInt(dataDTO.getRemindType()));
            selectFragment(dataDTO.getRemindType());
        }

    }

    @Override
    public void initData() {
        super.initData();


        fragments.add(FragmentNoChangeTimeSpaceArea.newInstance(getIntent().getStringExtra("msg"),"天"));
        fragments.add(FragmentSpecialTime.newInstance(getIntent().getStringExtra("msg"),"physical"));
        fragments.add(FragmentNoChangeTimeSpaceArea.newInstance(getIntent().getStringExtra("msg"),"年"));


        MainActivityPageAdapter myPagerAdapter = new MainActivityPageAdapter(getSupportFragmentManager(), fragments);
        viewBinding.viewPager.setNoScroll(true);
        viewBinding.viewPager.setCurrentItem(0);
        viewBinding.viewPager.setOffscreenPageLimit(2);
        viewBinding.viewPager.setAdapter(myPagerAdapter);


        viewBinding.fragment1.setOnClickListener(view -> {
            select(viewBinding.fragment1);
            unSelect(viewBinding.fragment2);
            unSelect(viewBinding.fragment3);
            viewBinding.viewPager.setCurrentItem(0);
        });
        viewBinding.fragment2.setOnClickListener(view -> {
            select(viewBinding.fragment2);
            unSelect(viewBinding.fragment1);
            unSelect(viewBinding.fragment3);
            viewBinding.viewPager.setCurrentItem(1);
        });
        viewBinding.fragment3.setOnClickListener(view -> {
            select(viewBinding.fragment3);
            unSelect(viewBinding.fragment1);
            unSelect(viewBinding.fragment2);
            viewBinding.viewPager.setCurrentItem(2);
        });

    }


    private void selectFragment(String type) {
        switch (type) {
            case "0":
                select(viewBinding.fragment1);
                unSelect(viewBinding.fragment2);
                unSelect(viewBinding.fragment3);
                break;
            case "1":
                select(viewBinding.fragment2);
                unSelect(viewBinding.fragment1);
                unSelect(viewBinding.fragment3);

                break;
            case "2":
                select(viewBinding.fragment3);
                unSelect(viewBinding.fragment1);
                unSelect(viewBinding.fragment2);
                break;
        }

    }

    private void select(MaterialCardView cardView) {
        cardView.setCardBackgroundColor(Color.parseColor("#DED6FC"));
        cardView.setStrokeColor(Color.parseColor("#6C63E5"));
    }

    private void unSelect(MaterialCardView cardView) {
        cardView.setCardBackgroundColor(Color.parseColor("#F6F2FC"));
        cardView.setStrokeColor(Color.parseColor("#6C63E5"));
    }
}