package com.example.medicalservice.fragments.healthPhysicalExaminationFragments;

import android.graphics.Color;
import android.os.Bundle;

import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.activity.healthcareChildActivity.TipTimeComment;
import com.example.medicalservice.activity.healthcareChildActivity.physicalExamination.PhysicalExaminationTip;
import com.example.medicalservice.baseFile.BaseFragment;
import com.example.medicalservice.bean.UseMedHistoryBean;
import com.example.medicalservice.databinding.FragmentSpecialTimeBinding;
import com.example.medicalservice.tools.DataEdit;
import com.example.medicalservice.tools.StringUtils;
import com.example.medicalservice.tools.TimeTool;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class FragmentSpecialTime extends BaseFragment<FragmentSpecialTimeBinding> {

    private String[] weeks = {"一", "二", "三", "四", "五", "六", "日"};
    private JSONObject medInfo;

    private List<String> showTimes;
    private List<String> uploadTimes;

    private UseMedHistoryBean.DataDTO rowsDTO;

    private PhysicalExaminationTip.PhysicalBean.DataDTO dataDTO;

    public FragmentSpecialTime() {
        // Required empty public constructor
    }

    public static FragmentSpecialTime newInstance(String params,String params2) {
        FragmentSpecialTime fragment = new FragmentSpecialTime();
        Bundle args = new Bundle();
        args.putString("params", params);
        args.putString("params2",params2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rowsDTO = DataEdit.getInstance().getRowsDTO();
        dataDTO = DataEdit.getInstance().getDataDTO();
        try {

            medInfo = new JSONObject(getArguments().getString("params"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initView() {
        super.initView();

        viewBinding.time.setText(TimeTool.Day(0, "YYYY-MM-dd"));
        viewBinding.nextStep.setOnClickListener(v -> {
            try {
                if (getArguments().getString("params2").equals("med")){
                    setMedData();
                }else {
                    setPhysicalData();
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

        viewBinding.selectDate.setOnClickListener(v -> {

            Calendar startCal = Calendar.getInstance();
            Calendar sal = Calendar.getInstance();

            startCal.set(1998, 0, 1, 0, 0, 0);
            TimePickerView pvTime = new TimePickerBuilder(activity, (date, v1) -> {

                viewBinding.time.setText(StringUtils.forDataTimeYMD(date));


            })
                    .setDate(sal).setRangDate(startCal, StringUtils.strForCalendar("2029-12-31 23:59:59"))//起始终止年月日设定// 如果不设置的话，默认是系统时间*/
                    .setType(new boolean[]{true, true, true, false, false, false})
                    .setCancelText("取消")
                    .setSubmitText("确定")
//                        .setSubmitColor(getResources().getColor(R.color.white))
//                        .setCancelColor(getResources().getColor(R.color.white))
//                        .setTitleBgColor(getResources().getColor(R.color.colorAccent))
//                        .setBgColor(getResources().getColor(R.color.bankuai))//滚轮背景颜色 Night mode
//                        .setDividerColor(getResources().getColor(R.color.bg2T55))//设置分割线的颜色
//                        .setTextColorCenter(getResources().getColor(R.color.black))//设置选中项的颜色
//                        .setTextColorOut(getResources().getColor(R.color.gray2))//设置没有被选中项的颜色
                    .build();
            pvTime.show();
        });
    }

    @Override
    public void initData() {
        super.initData();

        List<TimeBean> timeBeans = new ArrayList<>();

        for (int i = 0; i < weeks.length; i++) {
            timeBeans.add(new TimeBean(weeks[i], false));
        }
        if (rowsDTO != null && getArguments().getString("params2").equals("med")&&rowsDTO.getRemindType().equals("1")) {
            viewBinding.time.setText(rowsDTO.getBeginTime().substring(0,10));

            String[] remindTime  =  rowsDTO.getRemindTime().split(",");
            showTimes = new ArrayList<>();
            for (String time:remindTime ) {
                showTimes.add("周" + weeks[Integer.parseInt(time)-1]);
                timeBeans.get(Integer.parseInt(time)-1).setStatus(true);
            }

            viewBinding.timeText.setText(StringUtils.join((ArrayList<String>) showTimes, "和"));

        }

        if (dataDTO != null && !getArguments().getString("params2").equals("med")&&dataDTO.getRemindType().equals("1")) {
            viewBinding.time.setText(dataDTO.getBeginTime().substring(0,10));

            String[] remindTime  =  dataDTO.getRemindTime().split(",");
            showTimes = new ArrayList<>();
            for (String time:remindTime ) {
                showTimes.add("周" + weeks[Integer.parseInt(time)-1]);
                timeBeans.get(Integer.parseInt(time)-1).setStatus(true);
            }

            viewBinding.timeText.setText(StringUtils.join((ArrayList<String>) showTimes, "和"));

        }


        MsAdapter msAdapter = new MsAdapter<TimeBean>(timeBeans, R.layout.time_speical_item) {

            @Override
            public void bindView(ViewHolder holder, TimeBean obj) {

                MaterialCardView box = holder.getView(R.id.box);
                TextView weekDay = holder.getView(R.id.week_day);

                weekDay.setText(obj.text);

                if(obj.isStatus()){
                    box.setCardBackgroundColor(Color.parseColor("#FFDED6FC"));
                }else {
                    box.setCardBackgroundColor(Color.parseColor("#FFF7F3FF"));
                }



                holder.getItemView().setOnClickListener(v -> {
                    int position = timeBeans.indexOf(obj);
                    timeBeans.get(position).setStatus(!timeBeans.get(position).isStatus());

                    if (timeBeans.get(position).isStatus()) {
                        box.setCardBackgroundColor(Color.parseColor("#FFDED6FC"));
                    } else {
                        box.setCardBackgroundColor(Color.parseColor("#FFF7F3FF"));

                    }
                    getTimeList(timeBeans);
                });

            }
        };

        viewBinding.weekList.setAdapter(msAdapter);


    }

    private void getTimeList(List<TimeBean> timeBeans) {

        showTimes = new ArrayList<>();
        uploadTimes = new ArrayList<>();

        for (TimeBean time : timeBeans) {
            if (time.isStatus()) {
                showTimes.add("周" + time.text);
                uploadTimes.add(String.valueOf(timeBeans.indexOf(time) + 1));
            }
        }

        viewBinding.timeText.setText(StringUtils.join((ArrayList<String>) showTimes, "和"));
    }

    private void setMedData() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        if(rowsDTO!=null){
            jsonObject.put("id", rowsDTO.getId());
        }
        jsonObject.put("userId", MyApplication.getInstance().getUserId());
        jsonObject.put("medicinalName", medInfo.getString("name"));
        jsonObject.put("medicinalType", medInfo.getString("type"));
        jsonObject.put("medicinalNumber", medInfo.getString("number"));
        jsonObject.put("medicinalUnit", medInfo.getString("unit"));
        jsonObject.put("status", "0");
        jsonObject.put("remindType", "1");
        jsonObject.put("img",medInfo.getString("img"));
        jsonObject.put("beginTime", viewBinding.time.getText().toString());
        jsonObject.put("remindTime", StringUtils.join((ArrayList<String>) uploadTimes, ","));

        go(TipTimeComment.class,jsonObject.toString(),"med");

    }

    private void setPhysicalData() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        if(dataDTO!=null){
            jsonObject.put("id", dataDTO.getId());
        }
        jsonObject.put("userId", MyApplication.getInstance().getUserId());
        jsonObject.put("physicalName", medInfo.getString("name"));
        jsonObject.put("status", "0");
        jsonObject.put("remindType", "1");
        jsonObject.put("beginTime", viewBinding.time.getText().toString());
        jsonObject.put("remindTime", StringUtils.join((ArrayList<String>) uploadTimes, ","));

        go(TipTimeComment.class,jsonObject.toString(),"Physical");

    }

    static class TimeBean {
        private String text;
        private boolean status;

        public TimeBean(String text, boolean status) {
            this.text = text;
            this.status = status;
        }

        public String getText() {
            return text;
        }

        public boolean isStatus() {
            return status;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }
    }
}