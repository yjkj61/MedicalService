package com.example.medicalservice.activity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.databinding.ActivityWeatherBinding;
import com.example.medicalservice.diyView.weather.AirLevel;
import com.example.medicalservice.diyView.weather.WeatherModel;
import com.example.medicalservice.diyView.weather.WeatherView;

import java.util.ArrayList;

public class Weather extends BaseActivity<ActivityWeatherBinding> {


    WeatherView weatherView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        super.initView();
        weatherView = viewBinding.weatherView;
        weatherView.setLineType(WeatherView.LINE_TYPE_CURVE);
        //设置线宽
        weatherView.setLineWidth(2f);
        try {
            weatherView.setColumnNumber(7);
        } catch (Exception e) {
            e.printStackTrace();
        }

        weatherView.setDayAndNightLineColor(Color.parseColor("#E4AE47"), Color.parseColor("#58ABFF"));

        //点击某一列
        weatherView.setOnWeatherItemClickListener((itemView, position, weatherModel) -> {
            //Toast.makeText(MainActivity.this, position+"", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void initData() {
        super.initData();

        weatherView.setList(generateData());
    }

    private ArrayList<WeatherModel> generateData() {
        ArrayList<WeatherModel> list = new ArrayList<>();
        WeatherModel model = new WeatherModel();
        model.setDate("12/07");
        model.setWeek("昨天");
        model.setDayWeather("大雪");
        model.setDayTemp(10);
        model.setNightTemp(5);
        model.setNightWeather("晴");
        model.setWindOrientation("西南风");
        model.setWindLevel("3级");
        model.setAirLevel(AirLevel.EXCELLENT);
        list.add(model);

        WeatherModel model1 = new WeatherModel();
        model1.setDate("12/08");
        model1.setWeek("今天");
        model1.setDayWeather("晴");
        model1.setDayTemp(8);
        model1.setNightTemp(5);
        model1.setNightWeather("晴");
        model1.setWindOrientation("西南风");
        model1.setWindLevel("3级");
        model1.setAirLevel(AirLevel.HIGH);
        list.add(model1);

        WeatherModel model2 = new WeatherModel();
        model2.setDate("12/09");
        model2.setWeek("明天");
        model2.setDayWeather("晴");
        model2.setDayTemp(9);
        model2.setNightTemp(8);
        model2.setNightWeather("晴");
        model2.setWindOrientation("东南风");
        model2.setWindLevel("3级");
        model2.setAirLevel(AirLevel.POISONOUS);
        list.add(model2);

        WeatherModel model3 = new WeatherModel();
        model3.setDate("12/10");
        model3.setWeek("周六");
        model3.setDayWeather("晴");
        model3.setDayTemp(12);
        model3.setNightTemp(9);
//        model3.setDayPic(R.drawable.w0);
//        model3.setNightPic(R.drawable.w1);
        model3.setNightWeather("晴");
        model3.setWindOrientation("东北风");
        model3.setWindLevel("3级");
        model3.setAirLevel(AirLevel.GOOD);
        list.add(model3);

        WeatherModel model4 = new WeatherModel();
        model4.setDate("12/11");
        model4.setWeek("周日");
        model4.setDayWeather("多云");
        model4.setDayTemp(13);
        model4.setNightTemp(7);
        model4.setNightWeather("多云");
        model4.setWindOrientation("东北风");
        model4.setWindLevel("3级");
        model4.setAirLevel(AirLevel.LIGHT);
        list.add(model4);

        WeatherModel model5 = new WeatherModel();
        model5.setDate("12/12");
        model5.setWeek("周一");
        model5.setDayWeather("多云");
        model5.setDayTemp(17);
        model5.setNightTemp(8);
        model5.setNightWeather("多云");
        model5.setWindOrientation("西南风");
        model5.setWindLevel("3级");
        model5.setAirLevel(AirLevel.LIGHT);
        list.add(model5);

        WeatherModel model6 = new WeatherModel();
        model6.setDate("12/13");
        model6.setWeek("周二");
        model6.setDayWeather("晴");
        model6.setDayTemp(13);
        model6.setNightTemp(6);
        model6.setNightWeather("晴");
        model6.setWindOrientation("西南风");
        model6.setWindLevel("3级");
        model6.setAirLevel(AirLevel.POISONOUS);
        list.add(model6);



        return list;
    }
}