package com.example.medicalservice.tools;

import android.content.Context;
import android.widget.Toast;


/**
 * Created by LyouHP on 2019/4/23 21:49
 */
public class ToastUtil {

    private static Toast mToast;


    public static void showToast(String msg, Context context) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }
}

