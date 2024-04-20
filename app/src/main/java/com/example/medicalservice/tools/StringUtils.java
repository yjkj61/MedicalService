package com.example.medicalservice.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebSettings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint({"SimpleDateFormat", "DefaultLocale"})
public class StringUtils {

    public static final String EMPTY = "";
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    private static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_DATETIME_PATTERN2 = "yyyy-MM-dd HH:mm";
    /**
     * 用于生成文件
     */
    private static final String DEFAULT_FILE_PATTERN = "yyyy-MM-dd-HH-mm-ss";
    private static final double KB = 1024.0;
    private static final double MB = 1048576.0;
    private static final double GB = 1073741824.0;
    public static final SimpleDateFormat DATE_FORMAT_PART = new SimpleDateFormat(
            "HH:mm");

    private static final long MINUTE = 60;
    private static final long HOUR = 60 * 60;
    public static final long DAY = 24 * HOUR;
    private static final long WEEK = 7 * DAY;
    private static final long MONTH = 30 * DAY;
    public static final long YEAR = 365 * DAY;
    public static final long MIN_DURATION = 5 * MINUTE;


    public static long getTodayZero() {
        Date date = new Date();
        long l = 24 * 60 * 60 * 1000; //每天的毫秒数
        //date.getTime()是现在的毫秒数，它 减去 当天零点到现在的毫秒数（ 现在的毫秒数%一天总的毫秒数，取余。），理论上等于零点的毫秒数，不过这个毫秒数是UTC+0时区的。
        //减8个小时的毫秒值是为了解决时区的问题。
        return (date.getTime() - (date.getTime() % l) - 8 * 60 * 60 * 1000);
    }

    public static String format2ChatTime(long msec) {
        return format2ChatTime(msec, false);
    }

    public static String format2ChatTime(long msec, boolean alwaysShowTime) {
        long todayZero = getTodayZero();
        long duration = (todayZero - msec) / 1000;
        Date curDate = new Date();
        Date date = new Date(msec);
        boolean isOneYear = curDate.getYear() == date.getYear();

        String patternSuffix = alwaysShowTime ? " ah:mm" : "";

        if (duration <= 0) {
            SimpleDateFormat df = new SimpleDateFormat("ah:mm");
            return df.format(date);
        } else if (duration < DAY) {
            SimpleDateFormat df = new SimpleDateFormat("昨天 ah:mm");
            return df.format(date);
        } else {
            SimpleDateFormat df;
            if (isOneYear) {
                df = new SimpleDateFormat("M月d日" + patternSuffix);
            } else {
                df = new SimpleDateFormat("yyyy年M月d日" + patternSuffix);
            }
            return df.format(date);
        }
    }

    public static String getDateString(long seconds) {
        long curSecond = System.currentTimeMillis() / 1000;
        long duration = curSecond - seconds / 1000;
        if (duration <= 0) {
            return "刚刚";
        } else if (duration < MIN_DURATION) {
            return "刚刚";
        } else if (duration < HOUR) {
            return duration / MINUTE + "分钟前";
        } else if (duration < DAY) {
            return duration / HOUR + "小时前";
        } else if (duration < WEEK) {
            return duration / DAY + "天前";
        } else if (duration < MONTH) {
            return formatDate(new Date(seconds), "yyyy-MM-dd");
            // return duration / WEEK + "周前";
        } else if (duration < YEAR) {
            return formatDate(new Date(seconds), "yyyy-MM-dd");
            // return duration / MONTH + "月前";
        } else {
            return formatDate(new Date(seconds), "yyyy-MM-dd");
            // return duration / YEAR + "年前";
        }
    }

    public static String getDateString(String seconds) {
        if (seconds == null)
            return "";
        Long longSeconds = Long.valueOf(seconds);
        if (longSeconds == null)
            return "";
        return getDateString(longSeconds);
    }

    /**
     * 判断字符串是否为空或空串
     *
     * @param str 待判断的字符串
     * @return true：字符串为空或空串
     */
    public static boolean isNull(final String str) {
        if (null == str || "".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取字符串长度（半角算1、全角算2）
     *
     * @param str 字符串
     * @return 字符串长度
     */
    public static int getLength(final String str) {
        if (isNull(str)) {
            return 0;
        }
        int len = str.length();
        for (int i = 0; i < str.length(); i++) {
            if (isFullwidthCharacter(str.charAt(i))) {
                len = len + 1;
            }
        }
        return len;
    }

    /**
     * 获取字符串的全角字符数
     *
     * @param str 待计算的字符串
     * @return 字符串的全角字符数
     */
    public static int getFwCharNum(final String str) {
        if (isNull(str)) {
            return 0;
        }
        int num = 0;
        for (int i = 0; i < str.length(); i++) {
            if (isFullwidthCharacter(str.charAt(i))) {
                num++;
            }
        }
        return num;
    }

    /**
     * 判断字符是否为全角字符
     *
     * @param ch 待判断的字符
     * @return true：全角； false：半角
     */
    public static boolean isFullwidthCharacter(final char ch) {
        if (ch >= 32 && ch <= 127) {
            // 基本拉丁字母（即键盘上可见的，空格、数字、字母、符号）
            return false;
        } else if (ch >= 65377 && ch <= 65439) {
            // 日文半角片假名和符号
            return false;
        } else {
            return true;
        }
    }

    // 判断电话号码格式
    public static boolean isPhoneNumber(String number) {
        if (number == null)
            return false;
        if (number.length() == 11) {
            if (number.startsWith("1")) {
                return true;
            }
        }
        return false;
    }

    // 判断是否密码有效
    public static boolean isRegistPwdNumber(String string) {
/*        至少8个字符，至少1个大写字母，1个小写字母和1个数字,不能包含特殊字符（非数字字母）：
        ^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$*/
        /*^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$*/
        if (string == null)
            return false;
        String regEx1 = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(string);
        return m.matches();

    }

    /**
     * 将字符串转成MD5值
     *
     * @param string
     * @return
     */
    public static String stringToMD5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(
                    string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

    public static boolean isSameDay(long time1, long time2) {


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getDefault());


        Date date1 = new Date(time1);
        String date1Str = format.format(date1);

        Date date2 = new Date(time2);
        String date2Str = format.format(date2);

        if (date1Str != null && date2Str != null
                && date1Str.equalsIgnoreCase(date2Str)) {
            return true;
        }

        return false;
    }

    public static boolean isSameYear(long time1, long time2) {
        String date1 = formatDate(time1, "yyyy");
        String date2 = formatDate(time2, "yyyy");
        return date1.equalsIgnoreCase(date2);
    }

    public static String currentTimeString() {
        return DATE_FORMAT_PART.format(Calendar.getInstance().getTime());
    }

    public static char chatAt(String pinyin, int index) {
        if (pinyin != null && pinyin.length() > 0)
            return pinyin.charAt(index);
        return ' ';
    }

    /**
     * 获取字符串宽度
     */
    public static float GetTextWidth(String Sentence, float Size) {
        if (isEmpty(Sentence))
            return 0;
        TextPaint FontPaint = new TextPaint();
        FontPaint.setTextSize(Size);
        return FontPaint.measureText(Sentence.trim()) + (int) (Size * 0.1); // 留点余地
    }

    /**
     * 格式化日期字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            return "null";
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static String formatDate(long date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date(date));
    }


    private static int getDayofWeek(String dateTime) {

        Calendar cal = Calendar.getInstance();
        if (StringUtils.isEmpty(dateTime)) {
            cal.setTime(new Date(System.currentTimeMillis()));
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date;
            try {
                date = sdf.parse(dateTime);
            } catch (ParseException e) {
                date = null;
                e.printStackTrace();
            }
            if (date != null) {
                cal.setTime(new Date(date.getTime()));
            }
        }
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static String getWeek(String dateTime) {
        String week = "";
        switch (getDayofWeek(dateTime)) {
            case 1:
                week = "周日";
                break;
            case 2:
                week = "周一";
                break;
            case 3:
                week = "周二";
                break;
            case 4:
                week = "周三";
                break;
            case 5:
                week = "周四";
                break;
            case 6:
                week = "周五";
                break;
            case 7:
                week = "周六";
                break;
        }
        return week;
    }


    /**
     * 获取当前时间 格式为yyyy-MM-dd 例如2011-07-08
     *
     * @return
     */
    public static String getDate() {
        return formatDate(new Date(), DEFAULT_DATE_PATTERN);
    }

    public static Calendar strForCalendar(String strTime) {

        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_PATTERN);
        Date date = null;
        try {
            date = sdf.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }

    public static Calendar strForCalendar(String type, String strTime) {

        SimpleDateFormat sdf = new SimpleDateFormat(type);
        Date date = null;
        try {
            date = sdf.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }


    /**
     * 生成一个文件名，不含后缀
     */
    public static String createFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FILE_PATTERN);
        return format.format(date);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getDateTime() {
        return formatDate(new Date(), DEFAULT_DATETIME_PATTERN);
    }
    public static String getDateTime2() {
        return formatDate(new Date(), DEFAULT_DATETIME_PATTERN2);
    }

    public static String getDateTimeHm() {
        return formatDate(new Date(), "HH:mm");
    }

    /**
     * 格式化日期字符串
     *
     * @param date
     * @return 例如2011-3-24
     */
    public static String formatDate(Date date) {
        return formatDate(date, DEFAULT_DATE_PATTERN);
    }

    public static String formatDate(long date) {
        return formatDate(new Date(date), DEFAULT_DATE_PATTERN);
    }

    /**
     * 格式化日期时间字符串
     *
     * @param date
     * @return 例如2011-11-30 16:06:54
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, DEFAULT_DATETIME_PATTERN);
    }

    public static String formatDateTime(long date) {
        return formatDate(new Date(date), DEFAULT_DATETIME_PATTERN);
    }

    public static String formatDateTimeNoSecond(Date date) {
        return formatDate(date, DEFAULT_DATETIME_PATTERN2);
    }

    public static String formatDateTimeNoSecond(long date) {
        return formatDate(new Date(date), DEFAULT_DATETIME_PATTERN2);
    }

    public static String forDataTimeYMD(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    public static String forDataTimeYMDHM(Date date) {
        return formatDate(date, "yyyy年MM月dd日\nHH:mm");
    }

    public static String forDataTimeYMD2(Date date) {
        return formatDate(date, "yyyy/MM/dd");
    }

    public static String forDataTimeYMD_HM(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm");
    }

    public static String forDataTimeYM(Date date) {
        return formatDate(date, "yyyy年MM月");
    }

    public static String forDataTimeY_M(Date date) {
        return formatDate(date, "yyyy-MM");
    }

    public static String forDataTimeY_M_D(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    public static String forDataTimeTime(Date date) {
        return formatDate(date, "HH:mm");
    }

    public static String forDataTimeTime2(Date date) {
        return formatDate(date, "yyyy/MM/dd-HH:mm");
    }


    /**
     * 格林威时间转换
     *
     * @param gmt
     * @return
     */
    public static String formatGMTDate(String gmt) {
        TimeZone timeZoneLondon = TimeZone.getTimeZone(gmt);
        return formatDate(Calendar.getInstance(timeZoneLondon)
                .getTimeInMillis());
    }

    /**
     * 拼接数组
     *
     * @param array
     * @param separator
     * @return
     */
    public static String join(final ArrayList<String> array,
                              final String separator) {
        StringBuffer result = new StringBuffer();
        if (array != null && array.size() > 0) {
            for (String str : array) {
                result.append(str);
                result.append(separator);
            }
            result.delete(result.length() - 1, result.length());
        }
        return result.toString();
    }

    public static String join(final Iterator<String> iter,
                              final String separator) {
        StringBuffer result = new StringBuffer();
        if (iter != null) {
            while (iter.hasNext()) {
                String key = iter.next();
                result.append(key);
                result.append(separator);
            }
            if (result.length() > 0)
                result.delete(result.length() - 1, result.length());
        }
        return result.toString();
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0 || str.equalsIgnoreCase("null");
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * @param str
     * @return
     */
    public static String trim(String str) {
        return str == null ? EMPTY : str.trim();
    }

    /**
     * 转换时间显示
     *
     * @param time 毫秒
     * @return
     */
    public static String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes,
                seconds) : String.format("%02d:%02d", minutes, seconds);
    }

    public static boolean isBlank(String s) {
        return TextUtils.isEmpty(s);
    }

    /**
     * 根据秒速获取时间格式
     */
    public static String gennerTime(int totalSeconds) {
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * 转换文件大小
     *
     * @param size
     * @return
     */
    public static String generateFileSize(long size) {
        if (size < 0)
            size = 0;
        String fileSize;
        if (size < KB)
            fileSize = size + "B";
        else if (size < MB)
            fileSize = String.format("%.1f", size / KB) + "KB";
        else if (size < GB)
            fileSize = String.format("%.1f", size / MB) + "MB";
        else
            fileSize = String.format("%.1f", size / GB) + "GB";

        return fileSize;
    }

    /**
     * 查找字符串，找到返回，没找到返回空
     */
//    public static String findString(String search, String start, String end) {
//        int start_len = start.length();
//        int start_pos = StringUtil.isEmpty(start) ? 0 : search.indexOf(start);
//        if (start_pos > -1) {
//            int end_pos = StringUtil.isEmpty(end) ? -1 : search.indexOf(end,
//                    start_pos + start_len);
//            if (end_pos > -1)
//                return search.substring(start_pos + start.length(), end_pos);
//        }
//        return "";
//    }

    /**
     * 截取字符串
     *
     * @param search       待搜索的字符串
     * @param start        起始字符串 例如：<title>
     * @param end          结束字符串 例如：</title>
     * @param defaultValue
     * @return
     */
    public static String substring(String search, String start, String end,
                                   String defaultValue) {
        int start_len = start.length();
        int start_pos = StringUtils.isEmpty(start) ? 0 : search.indexOf(start);
        if (start_pos > -1) {
            int end_pos = StringUtils.isEmpty(end) ? -1 : search.indexOf(end,
                    start_pos + start_len);
            if (end_pos > -1)
                return search.substring(start_pos + start.length(), end_pos);
            else
                return search.substring(start_pos + start.length());
        }
        return defaultValue;
    }

    /**
     * 截取字符串
     *
     * @param search 待搜索的字符串
     * @param start  起始字符串 例如：<title>
     * @param end    结束字符串 例如：</title>
     * @return
     */
    public static String substring(String search, String start, String end) {
        return substring(search, start, end, "");
    }

    /**
     * 拼接字符串
     *
     * @param strs
     * @return
     */
    public static String concat(String... strs) {
        StringBuffer result = new StringBuffer();
        if (strs != null) {
            for (String str : strs) {
                if (str != null)
                    result.append(str);
            }
        }
        return result.toString();
    }

    /**
     * Helper function for making null strings safe for comparisons, etc.
     *
     * @return (s = = null) ? "" : s;
     */
    public static String makeSafe(String s) {
        return (s == null) ? "" : s;
    }

    public static boolean isIdCard(String text) {
        String regx = "[0-9]{17}x";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(reg1) || text.matches(regex);
    }

    public static long revertDateToTimeStamp(String pattern, String dateStr) {
        SimpleDateFormat revert = new SimpleDateFormat(pattern);
        try {
            Date date = revert.parse(dateStr);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return System.currentTimeMillis();
        }
    }

    public static long revertDateToTimeStamp(String dateStr) {
        return revertDateToTimeStamp("yyyy-MM-dd HH:mm:ss", dateStr);
    }

    public static boolean isEmail(String email) {
        if (null == email || "".equals(email))
            return false;
        // Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p = Pattern
                .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");// 复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static String convertCharset(String s) {
        if (s != null) {
            try {
                int length = s.length();
                byte[] buffer = new byte[length];
                // 0x81 to Unicode 0x0081, 0x8d to 0x008d, 0x8f to 0x008f, 0x90
                // to 0x0090, and 0x9d to 0x009d.
                for (int i = 0; i < length; ++i) {
                    char c = s.charAt(i);
                    if (c == 0x0081) {
                        buffer[i] = (byte) 0x81;
                    } else if (c == 0x008d) {
                        buffer[i] = (byte) 0x8d;
                    } else if (c == 0x008f) {
                        buffer[i] = (byte) 0x8f;
                    } else if (c == 0x0090) {
                        buffer[i] = (byte) 0x90;
                    } else if (c == 0x009d) {
                        buffer[i] = (byte) 0x9d;
                    } else {
                        buffer[i] = Character.toString(c).getBytes("CP1252")[0];
                    }
                }
                String result = new String(buffer, "UTF-8");
                return result;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getFromAssets(Context context, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context
                    .getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 中文乱码
     * <p/>
     * 暂时解决大部分的中文乱码 但是还有部分的乱码无法解决 .
     * <p/>
     * 如果您有好的解决方式 请联系我
     * 我会很乐意向您请教 谢谢您
     *
     * @return
     */
    public static String recode(String str) {
        String formart = "";

        try {
            boolean ISO = Charset.forName("ISO-8859-1").newEncoder()
                    .canEncode(str);
            if (ISO) {
                formart = new String(str.getBytes("ISO-8859-1"), "GB2312");
            } else {
                formart = str;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return formart;
    }


    public static void writeToFile(Context context, String data, String filePath) {
        try {
            File file = new File(filePath);
            file.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    public static String readFromFile(Context context, String filePath) {

        String ret = "";

        try {
            InputStream inputStream = new FileInputStream(new File(filePath));

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }


    public static String combineStrings(Object... strings) {
        StringBuilder sb = new StringBuilder();
        for (Object string : strings) {
            sb.append(string);
        }
        return sb.toString();
    }


    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth)
                    age--;
            } else {
                age--;
            }
        }
        return age;
    }


    public static String getShortNumber(long number) {
        String formated;
        if (number <= 9999) {
            formated = number + "";
        } else if (number > 9999 && number < 9999999) {
            formated = String.format("%.1f万", number / 10000.f);
        } else {
            formated = String.format("%.1f千万", number / 10000000.f);
        }
        return formated;
    }

    public static boolean isNumer(String str) {
        Pattern pattern = Pattern.compile("^(\\-|\\+)?\\d+(\\.\\d+)?$");//这个是对的
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static String subRangeString(String body, String str1, String str2) {
        while (true) {
            int index1 = body.indexOf(str1);
            if (index1 != -1) {
                int index2 = body.indexOf(str2, index1);
                if (index2 != -1) {
                    String str3 = body.substring(0, index1) + body.substring(index2 + str2.length(), body.length());
                    body = str3;
                } else {
                    return body;
                }
            } else {
                return body;
            }
        }
    }

    public static boolean textIsNull(String str) {

        if (null == str) {
            return true;
        } else if ("".equals(str)) {
            return true;
        } else return "null".equals(str);
    }

    public static <T>boolean textIsNull(T str) {
       return str==null;
    }


    public static String mToString(List list) {

        return (list + "").replace("[", "").replace("]", "").replace(" ", "");
//        return list.toString().replaceAll("(?:\\[|null|\\]| +)", "");
    }


    public static String getUserAgent(Context context) {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(context);
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /** * 返回正确的UserAgent * @return */
    public static String getUserAgent() {
        String userAgent = "";
        StringBuffer sb = new StringBuffer();
        userAgent = System.getProperty("http.agent");//Dalvik/2.1.0 (Linux; U; Android 6.0.1; vivo X9L Build/MMB29M)

        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }

//        Log.v("lhp","User-Agent: "+ sb.toString());
        return sb.toString();
    }

    public static String changeColor(int id, Context context){
        StringBuffer stringBuffer = new StringBuffer();
        int color = context.getResources().getColor(id);

        stringBuffer.append("#");
//        stringBuffer.append(Integer.toHexString(Color.alpha(color)));
        stringBuffer.append(Integer.toHexString(Color.red(color)));
        stringBuffer.append(Integer.toHexString(Color.green(color)));
        stringBuffer.append(Integer.toHexString(Color.blue(color)));
        return stringBuffer.toString();
    }




    /**
     * 处理String类型,若为"" 或 null,最终返回"".否则str.trim()
     * @param str
     * @return
     */
    public static String processNull(String str) {
        if (StringUtils.textIsNull(str)) {
            return "";
        }else {
            return str.trim();
        }
    }


    /**
     *去除小数后面多余0
     */
    public static String mTripTrailingZeros(String args) {
        if (StringUtils.textIsNull(args)) {
            return "";
        }
        //若是String类型，也可以先转为BigDecimal
        BigDecimal value = new BigDecimal(args);
        //去除多余0
        BigDecimal noZeros = value.stripTrailingZeros();
        return noZeros.toPlainString();
    }

    /**
     * 保留小数点后面不为0的两位小数
     * @param args
     * @return
     */
    public static String mTripTrailingZeros(double args) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(args);
    }


}
