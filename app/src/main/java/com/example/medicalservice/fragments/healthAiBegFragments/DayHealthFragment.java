package com.example.medicalservice.fragments.healthAiBegFragments;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseFragment;
import com.example.medicalservice.bean.SleepBean;
import com.example.medicalservice.databinding.FragmentDayHealthBinding;
import com.example.medicalservice.diyView.ChartView;
import com.example.medicalservice.recycleAdapter.AiBegHeaderAdapter;
import com.example.medicalservice.recycleAdapter.SleepLayoutChartAdapter;
import com.example.medicalservice.recycleAdapter.SleepTimeBottomAdapter;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DayHealthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DayHealthFragment extends BaseFragment<FragmentDayHealthBinding> {


    private RecyclerView recyclerView;

    public DayHealthFragment() {
        // Required empty public constructor
    }

    public static DayHealthFragment newInstance() {
        DayHealthFragment fragment = new DayHealthFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void initView() {
        super.initView();

        List<SleepBean> sleepBeanList = new ArrayList<>();

        sleepBeanList.add(new SleepBean("清醒时间", 1));

        sleepBeanList.add(new SleepBean("快速动眼睡眠", 2));

        sleepBeanList.add(new SleepBean("清醒时间", 1));

        sleepBeanList.add(new SleepBean("深度睡眠", 1));
        sleepBeanList.add(new SleepBean("快速动眼睡眠", 2));

        sleepBeanList.add(new SleepBean("清醒时间", 1));

        sleepBeanList.add(new SleepBean("深度睡眠", 1));


        viewBinding.sleepList.post(() -> {

            //Log.d("TAG", "FragmentDayHealthBinding: "+viewBinding.sleepList.getHeight());
            viewBinding.sleepList.getWidth(); //height可用

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            viewBinding.sleepList.setLayoutManager(linearLayoutManager);

            viewBinding.sleepList.setAdapter(new SleepLayoutChartAdapter(sleepBeanList, 9, viewBinding.sleepList.getWidth()));


            List<String> times = new ArrayList<>();

            times.add("22:00");
            times.add("01:00");
            times.add("04:00");
            times.add("07:00");
            LinearLayoutManager timeLayoutManager = new LinearLayoutManager(activity);
            timeLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            viewBinding.time.setLayoutManager(timeLayoutManager);
            viewBinding.time.setAdapter(new SleepTimeBottomAdapter(times, viewBinding.sleepList.getWidth() / times.size()));
        });

    }

    @Override
    public void initData() {
        super.initData();

        List<SleepStatus> sleepStatuses = new ArrayList<>();

        sleepStatuses.add(new SleepStatus("打鼾次数","2次",""));
        sleepStatuses.add(new SleepStatus("睡眠效率","100","合格"));
        sleepStatuses.add(new SleepStatus("体动次数","100次",""));

        sleepStatuses.add(new SleepStatus("离床次数","9次",""));



        MsAdapter msAdapter = new MsAdapter<SleepStatus>(sleepStatuses, R.layout.ai_beg_day_grid_item){

            @Override
            public void bindView(ViewHolder holder, SleepStatus obj) {

                TextView type = holder.getView(R.id.type);
                TextView data = holder.getView(R.id.data);
                TextView context = holder.getView(R.id.context);
                MaterialCardView cardView = holder.getView(R.id.box);

                type.setText(obj.getType());
                data.setText(obj.getData());
                context.setText(obj.getContext());

                switch (obj.getType()){
                    case "打鼾次数":
                        cardView.setCardBackgroundColor(Color.parseColor("#FFDCE3DD"));
                        break;
                    case "睡眠效率":
                        cardView.setCardBackgroundColor(Color.parseColor("#FFC2D8DC"));
                        break;
                    case "体动次数":
                        cardView.setCardBackgroundColor(Color.parseColor("#FFE3DCE2"));
                        break;
                    case "离床次数":
                        cardView.setCardBackgroundColor(Color.parseColor("#FFC2DCCF"));
                        break;
                }

            }
        };

        viewBinding.sleepType.setAdapter(msAdapter);

        initChart();
        initRec();
    }

    private void initChart(){
        ArrayList<String> dateList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dateList.add("");
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
        chartView.setLineChartShowFill(viewBinding.line1, dateList, lists,"呼吸频次图");
        chartView.setLineChartShowFill(viewBinding.line2, dateList, lists,"心率记录图");


    }

    private void initRec(){
        List<SleepStatus> sleepStatuses = new ArrayList<>();

        sleepStatuses.add(new SleepStatus("清醒时长  0小时45分钟","0.2","#FFDA96BE"));
        sleepStatuses.add(new SleepStatus("快速动眼睡眠时长  4小时45分钟","0.9","#FFACA4E4"));
        sleepStatuses.add(new SleepStatus("深度睡眠时长  3小时45分钟","0.3","#FF364FA5"));


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);

        viewBinding.rightRecyclerView.setLayoutManager(linearLayoutManager);

        viewBinding.rightRecyclerView.setAdapter(new AiBegHeaderAdapter(sleepStatuses));


    }

    public static class SleepStatus{
        private String type;
        private String data;
        private String context;

        public SleepStatus(String type, String data, String context) {
            this.type = type;
            this.data = data;
            this.context = context;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }
    }


}