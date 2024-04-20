package com.example.medicalservice.fragments.healthPhysicalExaminationFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;

import com.example.medicalservice.MyApplication;
import com.example.medicalservice.activity.healthcareChildActivity.TipTimeComment;
import com.example.medicalservice.activity.healthcareChildActivity.physicalExamination.PhysicalExaminationTip;
import com.example.medicalservice.baseFile.BaseFragment;
import com.example.medicalservice.bean.UseMedHistoryBean;
import com.example.medicalservice.databinding.FragmentNoChangeTimeSpaceAreaBinding;
import com.example.medicalservice.tools.DataEdit;
import com.example.medicalservice.tools.StringUtils;
import com.example.medicalservice.tools.TimeTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentNoChangeTimeSpaceArea#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentNoChangeTimeSpaceArea extends BaseFragment<FragmentNoChangeTimeSpaceAreaBinding> {


    private JSONObject medInfo;

    private int timeSelect = 0;

    private UseMedHistoryBean.DataDTO rowsDTO;

    private PhysicalExaminationTip.PhysicalBean.DataDTO dataDTO;

    public FragmentNoChangeTimeSpaceArea() {

    }


    public static FragmentNoChangeTimeSpaceArea newInstance(String params, String params2) {
        FragmentNoChangeTimeSpaceArea fragment = new FragmentNoChangeTimeSpaceArea();
        Bundle args = new Bundle();
        args.putString("params", params);
        args.putString("params2", params2);
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
                if (getArguments().getString("params2").equals("med")) {
                    setMedData();
                } else {
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

        List<String> times = new ArrayList<>();

        String unit = "天";


        if (!getArguments().getString("params2").equals("med")) {
            unit = getArguments().getString("params2");
        }

        for (int i = 0; i < 30; i++) {
            if (i == 1) {
                times.add("隔一" + unit);
            } else {
                times.add(i + 1 + unit);
            }
        }

        viewBinding.textTime.setText(times.get(0));

        viewBinding.loopView.setListener(index -> {
            timeSelect = index;
            viewBinding.textTime.setText(times.get(index));
        });


        viewBinding.loopView.setItems(times);

//编辑选择，用药
        if (rowsDTO != null && getArguments().getString("params2").equals("med") && rowsDTO.getRemindType().equals("0")) {
            viewBinding.time.setText(rowsDTO.getBeginTime().substring(0, 10));
            timeSelect = Integer.parseInt(rowsDTO.getRemindTime()) - 1;
            viewBinding.loopView.setInitPosition(timeSelect);
            viewBinding.textTime.setText(times.get(timeSelect));
        }

        if(dataDTO!=null && !getArguments().getString("params2").equals("med")){
            if(dataDTO.getRemindType().equals("0")||dataDTO.getRemindType().equals("2")){
                viewBinding.time.setText(dataDTO.getBeginTime().substring(0, 10));
                timeSelect = Integer.parseInt(dataDTO.getRemindTime()) - 1;
                viewBinding.loopView.setInitPosition(timeSelect);
                viewBinding.textTime.setText(times.get(timeSelect));

            }

        }

    }

    private void setMedData() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        if (rowsDTO != null) {
            jsonObject.put("id", rowsDTO.getId());
        }
        jsonObject.put("userId", MyApplication.getInstance().getUserId());
        jsonObject.put("medicinalName", medInfo.getString("name"));
        jsonObject.put("medicinalType", medInfo.getString("type"));
        jsonObject.put("medicinalNumber", medInfo.getString("number"));
        jsonObject.put("medicinalUnit", medInfo.getString("unit"));
        jsonObject.put("status", "0");
        jsonObject.put("remindType", "0");
        jsonObject.put("img", medInfo.getString("img"));
        jsonObject.put("beginTime", viewBinding.time.getText().toString());
        jsonObject.put("remindTime", timeSelect + 1);

        go(TipTimeComment.class, jsonObject.toString(), "med");
    }


    private void setPhysicalData() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        if(dataDTO != null){
            jsonObject.put("id",dataDTO.getId());
        }
        jsonObject.put("userId", MyApplication.getInstance().getUserId());
        jsonObject.put("physicalName", medInfo.getString("name"));
        jsonObject.put("status", "0");
        jsonObject.put("remindType", getArguments().getString("params2").equals("天") ? "0" : "2");
        jsonObject.put("beginTime", viewBinding.time.getText().toString());
        jsonObject.put("remindTime", timeSelect + 1);

        go(TipTimeComment.class, jsonObject.toString(), "Physical");
    }

}