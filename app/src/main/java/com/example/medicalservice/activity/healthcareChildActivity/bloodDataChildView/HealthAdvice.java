package com.example.medicalservice.activity.healthcareChildActivity.bloodDataChildView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.databinding.ActivityHealthAdviceBinding;

import java.util.ArrayList;
import java.util.List;

public class HealthAdvice extends BaseActivity<ActivityHealthAdviceBinding> {

    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        super.initView();

        viewBinding.back.setOnClickListener(v -> finish());
    }

    @Override
    public void initData() {
        super.initData();

        list.add("饮\n食\n建\n议");
        list.add("行\n为\n建\n议");
        list.add("用\n药\n建\n议");
        list.add("就\n医\n建\n议");

        DisplayMetrics outMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(outMetrics);
        float widthPixel = (float) (outMetrics.widthPixels-100);


        MsAdapter msAdapter = new MsAdapter<String>(list,R.layout.advice_item) {
            @Override
            public void bindView(ViewHolder holder, String obj) {

               ImageView imageView =  holder.getView(R.id.background);
                TextView  textView =  holder.getView(R.id.text);
                textView.setText(obj);

                switch (obj){
                    case "饮\n食\n建\n议":
                        imageView.setImageResource(R.drawable.eat_advice);
                        break;
                    case "行\n为\n建\n议":
                        imageView.setImageResource(R.drawable.do_advice);
                        break;

                    case "用\n药\n建\n议":
                        imageView.setImageResource(R.drawable.use_med_advice);
                        break;
                    case "就\n医\n建\n议":
                        imageView.setImageResource(R.drawable.doctor_advice);
                        break;
                }
            }
        };
        viewBinding.advice.setAdapter(msAdapter);


    }

}