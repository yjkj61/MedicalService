package com.example.medicalservice.activity.healthcareChildActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.databinding.ActivityAiChatBinding;

public class AiChat extends BaseActivity<ActivityAiChatBinding> {

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

    }
}