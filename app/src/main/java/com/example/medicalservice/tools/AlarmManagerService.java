package com.example.medicalservice.tools;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;

public class AlarmManagerService {
    public static AlarmManager alarmManager;
    private static String TAG = "AlarmManageService";
    public static void addAlarm(Context context, int requestCode, Bundle bundle, int minute){
        Intent intent = new Intent(context,RemindReceiver.class);
        intent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,requestCode,intent, PendingIntent.FLAG_IMMUTABLE);
        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.add(Calendar.SECOND,60*minute);
        //注册新提醒
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),minute* 60L,pendingIntent);
    }
}
