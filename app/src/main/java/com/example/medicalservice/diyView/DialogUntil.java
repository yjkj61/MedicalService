package com.example.medicalservice.diyView;


import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medicalservice.R;
import com.example.medicalservice.interfaceCallback.DialogInterFace;


public class DialogUntil {
    private Context context;
    private DialogInterFace callBackInterface;

    public DialogUntil(Context context) {
        this.context = context;
        callBackInterface = (DialogInterFace) context;
    }



    public AlertDialog buildDiaLogDelete(int view, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Dialog_style);
        View v = LayoutInflater.from(context).inflate(view, null);
        builder.setView(v);
        builder.setCancelable(false);

        TextView no, yes, tipText;
        tipText = v.findViewById(R.id.tip_text);
        no = v.findViewById(R.id.no);
        yes = v.findViewById(R.id.yes);
        tipText.setText(content);
        AlertDialog alertDialog = builder.create();

        no.setOnClickListener(v1 -> alertDialog.dismiss());
        yes.setOnClickListener(v12 -> callBackInterface.callBack("删除"));
        return alertDialog;
    }


    public AlertDialog buildDiaLogTip(int view, String content, String leftText, String rightText, String timeText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Dialog_style);
        View v = LayoutInflater.from(context).inflate(view, null);
        builder.setView(v);


        TextView left_btn, right_btn, text, time;

        left_btn = v.findViewById(R.id.yes);
        right_btn = v.findViewById(R.id.no);
        text = v.findViewById(R.id.tip_text);
        time = v.findViewById(R.id.time);

        left_btn.setText(leftText);
        right_btn.setText(rightText);
        text.setText(content);
        time.setText(timeText);

        left_btn.setOnClickListener(v1 -> callBackInterface.callBack(leftText));

        right_btn.setOnClickListener(v1 -> callBackInterface.callBack(rightText));

        return builder.create();
    }

}
