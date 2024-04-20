package com.example.medicalservice.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class RemindReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ToastUtil.showToast("收到消息", context);



        //MediaPlayer.create(context, R.raw.remind).start();
        //Bundle bundle = intent.getExtras();
//        intent = new Intent(context, RemindActivity.class);
//        intent.putExtras(bundle);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
    }

}

