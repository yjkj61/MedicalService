package com.example.medicalservice.activity.healthcareChildActivity.useMadChildView.addMedicineChildView;

import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import com.example.medicalservice.MainActivityPageAdapter;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.UseMedHistoryBean;
import com.example.medicalservice.databinding.ActivityFrequencyAdministrationBinding;
import com.example.medicalservice.fragments.healthPhysicalExaminationFragments.FragmentNeedSpaceArea;
import com.example.medicalservice.fragments.healthPhysicalExaminationFragments.FragmentNoChangeTimeSpaceArea;
import com.example.medicalservice.fragments.healthPhysicalExaminationFragments.FragmentSpecialTime;
import com.example.medicalservice.tools.DataEdit;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

//选择用药频率
public class FrequencyAdministration extends BaseActivity<ActivityFrequencyAdministrationBinding> {

    private ArrayList<Fragment> fragments = new ArrayList<>();

    public JSONObject medInfo;

    public static FrequencyAdministration mActivity;

    private UseMedHistoryBean.DataDTO rowsDTO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void initView() {
        super.initView();

        mActivity = this;

        viewBinding.back.setOnClickListener(v -> finish());

        try {
            medInfo = new JSONObject(Objects.requireNonNull(getIntent().getStringExtra("msg")));

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initData() {
        super.initData();

        selectFragment("0");



        viewBinding.back.setOnClickListener(v -> finish());


        fragments.add(FragmentNoChangeTimeSpaceArea.newInstance(getIntent().getStringExtra("msg"), "med"));
        fragments.add(FragmentSpecialTime.newInstance(getIntent().getStringExtra("msg"), "med"));

        fragments.add(FragmentNeedSpaceArea.newInstance(getIntent().getStringExtra("msg")));
        MainActivityPageAdapter myPagerAdapter = new MainActivityPageAdapter(getSupportFragmentManager(), fragments);
        viewBinding.viewPager.setNoScroll(true);
        viewBinding.viewPager.setCurrentItem(0);
        viewBinding.viewPager.setOffscreenPageLimit(2);

        viewBinding.viewPager.setAdapter(myPagerAdapter);


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


        rowsDTO = DataEdit.getInstance().getRowsDTO();

        if (rowsDTO != null) {
            viewBinding.viewPager.setCurrentItem(Integer.parseInt(rowsDTO.getRemindType()));
            selectFragment(rowsDTO.getRemindType());
        }
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