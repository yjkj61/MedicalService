package com.example.medicalservice.diyView;

import android.graphics.Color;

import com.example.medicalservice.R;
import com.example.medicalservice.fragments.healthAiBegFragments.MonthHealthFragment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class ChartView {

    public void setLineChart(LineChart mLineChar, ArrayList<String> xLabelValue, List<List<Float>> yLabelValues) {
        XAxis xAxis = mLineChar.getXAxis();
        xAxis.setGranularity(1f);  //最小轴步骤（间隔）为1
        xAxis.setDrawGridLines(true);//不显示网格线
        xAxis.setLabelCount(xLabelValue.size());
        xAxis.setAxisMaximum(xLabelValue.size());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //设置描述文本
        Description description = mLineChar.getDescription();
        description.setEnabled(false);


        //Y轴边框设置
        YAxis rightAxis = mLineChar.getAxisRight();
        YAxis leftAxis = mLineChar.getAxisLeft();


        leftAxis.setEnabled(true);
        rightAxis.setEnabled(false);
        rightAxis.setTextSize(0f);
        leftAxis.setAxisMinimum(0);

        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabelValue));


        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        for (int i = 0; i < yLabelValues.size(); i++) {
            ArrayList<Entry> values = new ArrayList<>();


            for (int j = 0; j < yLabelValues.get(i).size(); j++) {
                values.add(new Entry(j, yLabelValues.get(i).get(j)));

            }

            LineDataSet lineDataSet = new LineDataSet(values, "");
            lineDataSet.setValueTextSize(10f);//数据的字体大小
            lineDataSet.setDrawFilled(true);//设置图标填充
            lineDataSet.setDrawCircles(false);//数据用圆圈显示
            lineDataSet.setDrawValues(false);
            lineDataSet.setValueFormatter(new DefaultValueFormatter(2));


            switch (i) {
                case 0:
                    lineDataSet.setColor(Color.parseColor("#FFA87D5D"));
                    break;
                case 1:
                    lineDataSet.setColor(Color.parseColor("#FF5C96AD"));
                    break;
                case 2:
                    lineDataSet.setColor(Color.parseColor("#FF8AA85D"));
                    break;
                case 3:
                    lineDataSet.setColor(Color.parseColor("#FF5C60AD"));
                    break;
            }
            lineDataSet.setFillColor(Color.parseColor("#00FFFFFF"));
            lineDataSet.setFillAlpha(0);
            lineDataSet.setDrawHorizontalHighlightIndicator(false);//设置凸显样式水平图形显隐
            lineDataSet.setDrawVerticalHighlightIndicator(true);//设置凸显样式垂直图形显隐
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);//设置贝塞尔曲线
            //lineDataSet.setColor(Color.rgb(234, 172, 48));
            lineDataSet.setLineWidth(2f);//设置折线的宽度


            //添加数据集
            dataSets.add(lineDataSet);

        }


        //创建一个数据集的数据对象
        LineData data = new LineData(dataSets);

        //设置数据
        mLineChar.setData(data);

        mLineChar.setNoDataText("暂无数据");


        mLineChar.setAutoScaleMinMaxEnabled(false);
        mLineChar.setTouchEnabled(false);//设置为false的话，界面就像是一个图片
        mLineChar.setBorderWidth(1f);
        mLineChar.setScaleEnabled(false);  //可缩放
        mLineChar.setDrawBorders(false);//在折线图上添加边框
        mLineChar.setDragEnabled(true);//可拖拽
        mLineChar.setScaleEnabled(false);//缩放
        mLineChar.getLegend().setEnabled(false);

        mLineChar.animateX(0);
    }

    public void setBarChart(BarChart mBarChar, ArrayList<String> xLabelValue, List<List<Float>> yLabelValues) {
        XAxis xAxis = mBarChar.getXAxis();
        xAxis.setGranularity(1f);  //最小轴步骤（间隔）为1
        xAxis.setDrawGridLines(true);//不显示网格线
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(xLabelValue.size());
        xAxis.setAxisMaximum(xLabelValue.size());

        //设置描述文本
        Description description = mBarChar.getDescription();
        description.setEnabled(false);


        //Y轴边框设置
        YAxis rightAxis = mBarChar.getAxisRight();
        YAxis leftAxis = mBarChar.getAxisLeft();
        leftAxis.setAxisMinimum(0);


        leftAxis.setEnabled(true);
        rightAxis.setEnabled(false);
        rightAxis.setTextSize(0f);


        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabelValue));


        ArrayList<IBarDataSet> dataSets = new ArrayList<>();

        for (int i = 0; i < yLabelValues.size(); i++) {
            ArrayList<BarEntry> values = new ArrayList<>();


            for (int j = 0; j < yLabelValues.get(i).size(); j++) {
                values.add(new BarEntry(j, yLabelValues.get(i).get(j)));
            }

            BarDataSet barDateSet = new BarDataSet(values, "");
            barDateSet.setValueTextSize(10f);//数据的字体大小

            barDateSet.setValueFormatter(new DefaultValueFormatter(2));

            switch (i) {
                case 0:
                    barDateSet.setColor(Color.parseColor("#FFA4C6E4"));
                    break;
                case 1:
                    barDateSet.setColor(Color.parseColor("#FF5C96AD"));
                    break;
                case 2:
                    barDateSet.setColor(Color.parseColor("#FF8AA85D"));
                    break;
                case 3:
                    barDateSet.setColor(Color.parseColor("#FF5C60AD"));
                    break;
            }

            //添加数据集
            dataSets.add(barDateSet);

        }

        //创建一个数据集的数据对象
        BarData data = new BarData(dataSets);

        float groupSpace = 0.2f;
        float barSpace = 0f;
        float barWidth = 0.4f;

        data.setBarWidth(barWidth);

        //设置数据
        mBarChar.setData(data);

        mBarChar.setNoDataText("暂无数据");


        mBarChar.setAutoScaleMinMaxEnabled(false);
        mBarChar.setTouchEnabled(false);//设置为false的话，界面就像是一个图片
        mBarChar.setScaleEnabled(false);  //可缩放
        mBarChar.setDrawBorders(false);//在折线图上添加边框
        mBarChar.setDragEnabled(true);//可拖拽
        mBarChar.setScaleEnabled(false);//缩放
        mBarChar.getLegend().setEnabled(false);


        //mBarChar.groupBars(0f,groupSpace,barSpace);

        mBarChar.animateX(0);
    }

    public void setLineChartShowFill(LineChart mLineChar, ArrayList<String> xLabelValue, List<List<Float>> yLabelValues, String descriptionText) {
        XAxis xAxis = mLineChar.getXAxis();
        xAxis.setGranularity(1f);  //最小轴步骤（间隔）为1
        xAxis.setDrawGridLines(false);//不显示网格线

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //设置描述文本
        Description description = mLineChar.getDescription();
        description.setEnabled(false);
        description.setText(descriptionText);


        //Y轴边框设置
        YAxis rightAxis = mLineChar.getAxisRight();
        YAxis leftAxis = mLineChar.getAxisLeft();


        leftAxis.setEnabled(true);
        rightAxis.setEnabled(false);
        rightAxis.setTextSize(0f);


        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabelValue));


        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        for (int i = 0; i < yLabelValues.size(); i++) {
            ArrayList<Entry> values = new ArrayList<>();


            for (int j = 0; j < yLabelValues.get(i).size(); j++) {
                values.add(new Entry(j, yLabelValues.get(i).get(j)));
            }

            LineDataSet lineDataSet = new LineDataSet(values, "");
            lineDataSet.setValueTextSize(10f);//数据的字体大小
            lineDataSet.setDrawFilled(true);//设置图标填充
            lineDataSet.setDrawCircles(false);//数据用圆圈显示
            lineDataSet.setValueFormatter(new DefaultValueFormatter(2));

            lineDataSet.setDrawValues(false);

            switch (i) {
                case 0:
                    lineDataSet.setColor(Color.parseColor("#FF465F9E"));
                    break;
                case 1:
                    lineDataSet.setColor(Color.parseColor("#FF5C96AD"));
                    break;
                case 2:
                    lineDataSet.setColor(Color.parseColor("#FF8AA85D"));
                    break;
                case 3:
                    lineDataSet.setColor(Color.parseColor("#FF5C60AD"));
                    break;
            }
            lineDataSet.setFillColor(Color.parseColor("#FFA4C6E4"));
            lineDataSet.setDrawHorizontalHighlightIndicator(false);//设置凸显样式水平图形显隐
            lineDataSet.setDrawVerticalHighlightIndicator(true);//设置凸显样式垂直图形显隐
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);//设置贝塞尔曲线
            //lineDataSet.setColor(Color.rgb(234, 172, 48));
            lineDataSet.setLineWidth(2f);//设置折线的宽度


            //添加数据集
            dataSets.add(lineDataSet);

        }


        //创建一个数据集的数据对象
        LineData data = new LineData(dataSets);

        //设置数据
        mLineChar.setData(data);

        mLineChar.setNoDataText("暂无数据");


        mLineChar.setAutoScaleMinMaxEnabled(false);
        mLineChar.setTouchEnabled(false);//设置为false的话，界面就像是一个图片
        mLineChar.setBorderWidth(1f);
        mLineChar.setScaleEnabled(false);  //可缩放
        mLineChar.setDrawBorders(false);//在折线图上添加边框
        mLineChar.setDragEnabled(true);//可拖拽
        mLineChar.setScaleEnabled(false);//缩放
        mLineChar.getLegend().setEnabled(false);
        mLineChar.animateX(0);
    }

    public void setBarChart(BarChart barChart, List<MonthHealthFragment.SleepTime> sleepTimes) {
//数据赋值
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, new float[]{1, 1, 1}));
        entries.add(new BarEntry(1, new float[]{1, 0, 2}));
        entries.add(new BarEntry(2, new float[]{0.5f, 1, 0.5f}));
        entries.add(new BarEntry(3, new float[]{2, 0, 1}));
        entries.add(new BarEntry(4, new float[]{1, 1, 3}));
        entries.add(new BarEntry(5, new float[]{0.8f, 3, 1}));
        entries.add(new BarEntry(6, new float[]{2, 2, 2}));
//
        List<Integer> colors = new ArrayList<>();

//        for (int i = 0; i <sleepTimes.size(); i++) {
//            MonthHealthFragment.SleepTime item = sleepTimes.get(i);
//            float[] floats = new float[item.getChildBens().size()];
//
//            for (int j = 0; j < item.getChildBens().size(); j++) {
//                floats[j] = item.getChildBens().get(j);
//            }
//
//            entries.add(new BarEntry(i, floats));
//        }
//        //这里一个柱体有三个堆，每个堆设置一个颜色进行区分
        colors.add(Color.parseColor("#FFA4C6E4"));
        colors.add(Color.parseColor("#00FF8686"));
        colors.add(Color.parseColor("#FFA4C6E4"));

        BarDataSet dataSet = new BarDataSet(entries, "");
        //柱体颜色
        dataSet.setColors(colors);
        //不显示数值
        dataSet.setDrawValues(false);
        //数据集赋值给数据对象
        BarData data = new BarData(dataSet);
        barChart.setData(data);

//隐藏图例
        barChart.getLegend().setEnabled(false);
        //取消缩放、点击、高亮效果
        barChart.setScaleEnabled(false);
        barChart.setClickable(false);
        barChart.setHighlightPerDragEnabled(false);
        barChart.setHighlightPerTapEnabled(false);
        //设置描述位置
        Description description = barChart.getDescription();
        description.setText("小时");
        description.setPosition(60, 20);
        description.setYOffset(20);
        barChart.setDescription(description);
        //获取X轴
        XAxis xAxis = barChart.getXAxis();
        //设置X轴在下方（默认X轴在上方）
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //给X轴添加标签
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"一", "二", "三", "四", "五","六","七"}));
        //取消X轴网格线
        xAxis.setDrawGridLines(false);
        //获取左边Y轴
        YAxis leftYAxis = barChart.getAxisLeft();
        //取消Y轴网格线
        leftYAxis.setDrawGridLines(false);
        //不显示左边Y轴的标签
        leftYAxis.setDrawLabels(false);
        //获取右边Y轴
        YAxis rightYAxis = barChart.getAxisRight();
        //隐藏右边Y轴
        rightYAxis.setEnabled(false);
        //设置柱体宽带
        data.setBarWidth(0.3f);

    }
}
