package com.example.medicalservice.fragments.healthMyStepFragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseFragment;
import com.example.medicalservice.databinding.FragmentWeekAndMonthBinding;
import com.example.medicalservice.diyView.ChartView;
import com.example.medicalservice.tools.CircleProgress;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeekAndMonthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeekAndMonthFragment extends BaseFragment<FragmentWeekAndMonthBinding> {


    private static final String ARG_PARAM1 = "param1";


    // TODO: Rename and change types of parameters
    private String mParam1;

    public WeekAndMonthFragment() {

    }

    public static WeekAndMonthFragment newInstance(String param1) {
        WeekAndMonthFragment fragment = new WeekAndMonthFragment();
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

    @SuppressLint("SetTextI18n")
    @Override
    public void initView() {
        super.initView();

        viewBinding.most.setText("本" + mParam1 + "步数最多");
        viewBinding.less.setText("本" + mParam1 + "步数最少");
        viewBinding.avg.setText("本" + mParam1 + "平均步数");
        viewBinding.context.setText("本" + mParam1 + "步数\n达标天数");

        viewBinding.mostStep.setText("2000步");
        viewBinding.lessStep.setText("100步");
        viewBinding.avgStep.setText("1100步");
        viewBinding.adviceText.setText("本"+mParam1+"运动建议");
    }

    @Override
    public void initData() {
        super.initData();

        lineSet();


        CircleProgress.set(viewBinding.stepView,7,4,"day");

    }

    private void lineSet() {

        ArrayList<String> dateList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dateList.add("8-" + i + 1);
        }


        List<List<Float>> lists = new ArrayList<>();

        for (int i = 0; i < 1; i++) {

            List<Float> floats = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                floats.add((float) Math.random());
            }

            lists.add(floats);

        }


        ChartView chartView = new ChartView();
        chartView.setBarChart(viewBinding.barChart, dateList, lists);

    }
}