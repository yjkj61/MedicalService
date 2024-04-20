package com.example.medicalservice.tools;

import android.content.Context;
import android.util.TypedValue;

import java.text.DecimalFormat;

/**
 * @ClassName ViewUtils
 * @Author LyouHiPeng
 * @创建日期 2021年01月24日 12:30
 * @Description 描述
 */
public class ViewUtils {

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);    }

    /**
     * px转换成dp
     */
    public static int pxZdp(Context context,float pxValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale+0.5f);
    }

    /**
     * sp转换成px
     */
    public static int spZpx(Context context,float spValue){
        float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue*fontScale+0.5f);
    }
    /**
     * px转换成sp
     */
    public static int pxZsp(Context context,float pxValue){
        float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue/fontScale+0.5f);
    }

    public static String decimalFloat(float number) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(number);
    }
}
