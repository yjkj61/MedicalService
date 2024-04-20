package com.example.medicalservice.activity.healthcareChildActivity.useMadChildView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.databinding.ActivityInfodetailBinding;

public class InfoDetailActivity extends BaseActivity<ActivityInfodetailBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infodetail);
    }
}