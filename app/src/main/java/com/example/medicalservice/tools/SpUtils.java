package com.example.medicalservice.tools;


import android.content.Context;
import android.content.SharedPreferences;

public class SpUtils {

    private static final String NAME = "config";
    private static SharedPreferences sp;

    /***
     *String
     */
    public static void putString(Context context, String key, String value) {
        sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key, String defaultValue) {
        sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    /***
     *int
     */
    public static void putInt(Context context, String key, int value) {
        sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }

    public static int getInt(Context context, String key, int defaultValue) {
        sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    /***
     *Boolean
     */

    public static void putBoolean(Context context, String key, Boolean value) {
        sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static Boolean getBoolean(Context context, String key, Boolean defaultValue) {
        sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    /***
     *删除单个
     */
    public static void deleteSingle(Context context, String key) {
        sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }
    /***
     *删除全部
     */
    public static void deleteAll(Context context) {
        sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }

}














