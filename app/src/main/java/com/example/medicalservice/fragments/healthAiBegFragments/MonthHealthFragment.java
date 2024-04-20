package com.example.medicalservice.fragments.healthAiBegFragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseFragment;
import com.example.medicalservice.databinding.FragmentMonthHealthBinding;
import com.example.medicalservice.diyView.ChartView;
import com.example.medicalservice.diyView.QQStepView;
import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonthHealthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthHealthFragment extends BaseFragment<FragmentMonthHealthBinding> {


    private static final String ARG_PARAM1 = "param1";
    private String mParam1;

    public MonthHealthFragment() {

    }


    public static MonthHealthFragment newInstance(String params) {
        MonthHealthFragment fragment = new MonthHealthFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, params);
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
    public void initData() {
        super.initData();


        setBarData();
        viewBinding.stepView.setStepMax(100);

        //属性动画
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 50);
        //先快后慢的效果
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(animation -> {
            float animatedValue = (float) animation.getAnimatedValue();
            viewBinding.stepView.setCurrentStep((int) animatedValue, "day");
        });
        viewBinding.stepView.postDelayed(valueAnimator::start, 1000);//这里延时5s便于人眼观察

        List<GridBean> gridBeans = new ArrayList<>();

        gridBeans.add(new GridBean(23, "day", "本月打鼾", 30));
        gridBeans.add(new GridBean(98, " 分", "本月睡眠效率", 100));
        gridBeans.add(new GridBean(18, "day", "夜间离床", 30));
        gridBeans.add(new GridBean(20, "day", "本月平均体动", 30));


        MsAdapter msAdapter = new MsAdapter<GridBean>(gridBeans, R.layout.circle_progress_item) {

            @Override
            public void bindView(ViewHolder holder, GridBean obj) {
                QQStepView qqStepView = holder.getView(R.id.step_view);
                TextView type = holder.getView(R.id.type);

                TextView bottom_text = holder.getView(R.id.bottom_text);

                qqStepView.setStepMax(obj.getMax());
                qqStepView.setCurrentStep((int) obj.getToData(), "");
                type.setText(obj.getType());
                bottom_text.setText(obj.getBottomText());
            }
        };
        viewBinding.circleGrid.setAdapter(msAdapter);
    }

    private void setBarData() {
        List<SleepTime> sleepTimes = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            SleepTime sleepTime = new SleepTime();
            List<Float> childBens = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                if (j == 1) {
                    childBens.add(0f);
                } else {
                    childBens.add((float) (4 + j));
                }
            }
            sleepTime.setDatTime("08-14");
            sleepTime.setChildBens(childBens);

            sleepTimes.add(sleepTime);
        }

        Log.d("json", new Gson().toJson(sleepTimes));

        ChartView chartView = new ChartView();
        chartView.setBarChart(viewBinding.barChart, sleepTimes);

    }

    public static class GridBean {
        private float toData;
        private String bottomText;

        private String type;

        private int max;

        public GridBean(float toData, String bottomText, String type, int max) {
            this.toData = toData;
            this.bottomText = bottomText;
            this.type = type;
            this.max = max;
        }

        public float getToData() {
            return toData;
        }

        public void setToData(int toData) {
            this.toData = toData;
        }

        public String getBottomText() {
            return bottomText;
        }

        public void setBottomText(String bottomText) {
            this.bottomText = bottomText;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }
    }

    public static class SleepTime {


        private String datTime;

        private List<Float> childBens;

        public String getDatTime() {
            return datTime;
        }

        public void setDatTime(String datTime) {
            this.datTime = datTime;
        }


        public List<Float> getChildBens() {
            return childBens;
        }

        public void setChildBens(List<Float> childBens) {
            this.childBens = childBens;
        }
    }
}