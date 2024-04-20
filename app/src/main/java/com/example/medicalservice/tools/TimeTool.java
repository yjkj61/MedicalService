package com.example.medicalservice.tools;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.ChineseCalendar;
import android.icu.util.TimeZone;
import android.icu.util.ULocale;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.Date;
import java.util.Locale;

public class TimeTool {

    public static int getMonthDays() {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        Month currentMonth = currentDate.getMonth();
        YearMonth yearMonth = YearMonth.of(currentYear, currentMonth);
        return yearMonth.lengthOfMonth();
    }

    public static int getMonthFirstDayWeek(int dayOfMonth) {
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();

        LocalDate firstDayOfMonth = LocalDate.of(year, month, dayOfMonth);
        DayOfWeek dayOfWeek = firstDayOfMonth.getDayOfWeek();
        return dayOfWeek.getValue();

        //System.out.println("当前月份的1号是星期: " + dayOfWeek);
    }

    public static int getMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;


    }

    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);


    }

    public static int getDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static String getTranslate(int day) {

        Calendar calendar = Calendar.getInstance();
        LunarCalender lunarCalender = new LunarCalender();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;

        String lunarAnimal = lunarCalender.animalsYear(year);
        String lunarString = lunarCalender.getLunarString(year, month, day);
        return lunarString.substring(3, lunarString.length());
    }


    // 获取具体日期
    public static String Day(int day, String type) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day);
        Date date1 = calendar.getTime();
        DateFormat df = new android.icu.text.SimpleDateFormat(type);
        return df.format(date1);
    }

    public static String StringData() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        switch (mWay) {
            case "1":
                mWay = "天";
                break;
            case "2":
                mWay = "一";
                break;
            case "3":
                mWay = "二";
                break;
            case "4":
                mWay = "三";
                break;
            case "5":
                mWay = "四";
                break;
            case "6":
                mWay = "五";
                break;
            case "7":
                mWay = "六";
                break;
        }
        return "星期"+mWay;
    }


    // 获取具体月份
    public static String Month(int day, String type) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, day);
        Date date1 = calendar.getTime();
        DateFormat df = new SimpleDateFormat(type);
        return df.format(date1);
    }

}
