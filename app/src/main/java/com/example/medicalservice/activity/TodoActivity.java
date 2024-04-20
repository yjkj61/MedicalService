package com.example.medicalservice.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.CalendarBean;
import com.example.medicalservice.databinding.ActivityTodoBinding;
import com.example.medicalservice.tools.TimeTool;

import java.util.ArrayList;
import java.util.List;

public class TodoActivity extends BaseActivity<ActivityTodoBinding> {

    private List<CalendarBean> calendarBeanList = new ArrayList<>();
    private List<Integer> weekList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData() {
        super.initData();

        Log.d("TAG", "农历" + TimeTool.getMonthDays() + "一号星期" + TimeTool.getMonthFirstDayWeek(1));

        int residueDay = TimeTool.getMonthFirstDayWeek(1) - 1;

        for (int i = 0; i < residueDay; i++) {
            calendarBeanList.add(new CalendarBean());
        }

        for (int i = 1; i <= TimeTool.getMonthDays(); i++) {
            CalendarBean calendarBean = new CalendarBean();
            calendarBean.setWeek(TimeTool.getMonthFirstDayWeek(i));
            calendarBean.setCalendar(i);
            calendarBeanList.add(calendarBean);
        }

        for (int i = 0; i < 7; i++) {
            weekList.add(i + 1);
        }


        viewBinding.weekList.setNumColumns(7);
        MsAdapter weekAdapter = new MsAdapter<Integer>(weekList, R.layout.week_item) {
            @Override
            public void bindView(ViewHolder holder, Integer obj) {
                TextView week = holder.getView(R.id.week);
                TextView weekEng = holder.getView(R.id.weekEng);

                switch (obj) {
                    case 1:
                        week.setText("周一");
                        weekEng.setText("Mon");
                        break;
                    case 2:
                        week.setText("周二");
                        weekEng.setText("Tue");
                        break;
                    case 3:
                        week.setText("周三");
                        weekEng.setText("Wed");
                        break;
                    case 4:
                        week.setText("周四");
                        weekEng.setText("Thu");
                        break;
                    case 5:
                        week.setText("周五");
                        weekEng.setText("Fri");
                        break;
                    case 6:
                        week.setText("周六");
                        weekEng.setText("Sta");
                        break;
                    case 7:
                        week.setText("周日");
                        weekEng.setText("Sun");
                        break;
                }

            }
        };

        viewBinding.weekList.setAdapter(weekAdapter);
        weekAdapter.notifyDataSetChanged();


        MsAdapter msAdapter = new MsAdapter<CalendarBean>(calendarBeanList, R.layout.calendar_item) {

            @SuppressLint("SetTextI18n")
            @Override
            public void bindView(ViewHolder holder, CalendarBean obj) {
                TextView calendarText = holder.getView(R.id.calendarText);
                TextView translateText = holder.getView(R.id.translateText);


                if (obj.getWeek() != 0 && obj.getCalendar() != 0) {

                    calendarText.setText(obj.getCalendar() + "");
                    if(obj.getCalendar()==TimeTool.getDay()){
                        calendarText.setBackgroundResource(R.drawable.day_circle_back);
                    }
                    translateText.setText(TimeTool.getTranslate(obj.getCalendar()));

                }
            }
        };

        viewBinding.CalendarView.setNumColumns(7);
        viewBinding.CalendarView.setAdapter(msAdapter);
        msAdapter.notifyDataSetChanged();

        if(TimeTool.getMonth()+1 > 10){
            viewBinding.dayTime.setText(TimeTool.getMonth()+"\n"+TimeTool.getDay());
        }else {
            viewBinding.dayTime.setText("0"+TimeTool.getMonth()+"\n"+TimeTool.getDay());
        }

        viewBinding.year.setText(""+TimeTool.getYear());
        viewBinding.month.setText(""+TimeTool.getMonth());

    }
}