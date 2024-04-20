package com.example.medicalservice.tools;

import com.example.medicalservice.R;

public class WeatherIcon {

   public static int weatherIconGet(String weather) {

        switch (weather) {
            case "有风":
            case "平静":
            case "微风":
            case "和风":
            case "清风":
            case "强风/劲风":
            case "疾风":
            case "大风":
            case "烈风":
            case "风暴":
            case "狂爆风":
            case "飓风":
            case "热带风暴":
            case "龙卷风":
                return R.drawable.wind;

            case "少云":
            case "晴间多云":
            case "多云":
                return R.drawable.cloudy;

            case "雪":
            case "阵雪":
            case "小雪":
            case "中雪":
            case "大雪":
            case "暴雪":
            case "小雪-中雪":
            case "中雪-大雪":
            case "大雪-暴雪":
            case "冷":
                return R.drawable.snow;
            case "浮尘":
            case "扬沙":
            case "沙尘暴":
            case "强沙尘暴":
            case "雾":
            case "浓雾":
            case "强浓雾":
            case "轻雾":
            case "大雾":
            case "特强浓雾":
                return R.drawable.fog;

            case "晴":
            case "热":
                return R.drawable.sun;
            case "雨雪天气":
            case "雨夹雪":
            case "阵雨夹雪":
                return R.drawable.rain_snow;

            case "阵雨":
            case "雷阵雨":
            case "雷阵雨并伴有冰雹":
            case "小雨":
            case "中雨":
            case "大雨":
            case "暴雨":
            case "大暴雨":
            case "特大暴雨":
            case "强阵雨":
            case "强雷阵雨":
            case "极端降雨":
            case "毛毛雨/细雨":
            case "雨":
            case "小雨-中雨":
            case "中雨-大雨":
            case "大雨-暴雨":
            case "暴雨-大暴雨":
            case "大暴雨-特大暴雨":
            case "冻雨":
                return R.drawable.rain;

            case "阴":
            case "霾":
            case "中度霾":
            case "重度霾":
            case "严重霾":
            case "未知":
                return R.drawable.cloudy_day;


            default:
                return R.drawable.sun;
        }
    }
}
