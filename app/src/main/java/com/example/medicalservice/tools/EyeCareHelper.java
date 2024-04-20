package com.example.medicalservice.tools;

import com.example.medicalservice.MyApplication;

public class EyeCareHelper {

    private static String EYE_CARE_KEY = "eye_key_care";


    public static void saveEyeCare(Boolean b){
        SpUtils.putBoolean(MyApplication.getInstance(),EYE_CARE_KEY,b);
    }

    public static boolean getEyeCare(){
        return SpUtils.getBoolean(MyApplication.getInstance(),EYE_CARE_KEY,false);
    }
}
