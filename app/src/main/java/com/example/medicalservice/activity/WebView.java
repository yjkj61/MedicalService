package com.example.medicalservice.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.databinding.ActivityWebViewBinding;
import com.example.medicalservice.tools.utils.AndroidBug5497Workaround;

public class WebView extends BaseActivity<ActivityWebViewBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        AndroidBug5497Workaround.assistActivity(this);
    }


    @Override
    public void initView() {
        super.initView();

        viewBinding.back.setOnClickListener(v -> finish());

        viewBinding.webView.setWebChromeClient(new WebChromeClient());
        viewBinding.webView.setWebViewClient(new WebViewClient());
        viewBinding.webView.loadUrl(getIntent().getStringExtra("msg"));
        WebSettings webSettings = viewBinding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);


        webSettings.setDisplayZoomControls(false);

        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

    }
}