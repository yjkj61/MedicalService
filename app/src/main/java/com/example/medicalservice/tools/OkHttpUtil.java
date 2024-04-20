package com.example.medicalservice.tools;

import android.util.Log;

import com.example.medicalservice.MyApplication;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtil {
    private static OkHttpUtil okHttpUtil = null;
    private static OkHttpClient okHttpClient = null;

    public static OkHttpUtil getInstance() {
        if (okHttpUtil == null) {
            synchronized (OkHttpUtil.class) {
                if (okHttpUtil == null) {
                    okHttpUtil = new OkHttpUtil();
                }
            }
        }
        return okHttpUtil;
    }

    public static synchronized OkHttpClient getOkHttpClient() {

        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(getInstance().getLogInterceptor())
                    .build();
        }
        return okHttpClient;
    }

    /**
     * 获取日志拦截器
     */
    private Interceptor getLogInterceptor(){
        //http log 拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor("OkHttp");
        interceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        interceptor.setColorLevel(Level.INFO);
        return interceptor;
    }

    public void doGet(String url, Callback callback) {

        String token = MyApplication.getInstance().getToken();


        Log.d("Token", "token: "+token);

        Request reQuest = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + token)
                .get()
                .build();


        getOkHttpClient().newCall(reQuest).enqueue(callback);

    }

    public void doGet(String url,String requestBody, Callback callback) {

        String token = MyApplication.getInstance().getToken();
        Log.d("Okhttp", "token: "+token);

        Request reQuest = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + token)
                .get()
                .method("GET", okhttp3.RequestBody.create(null, requestBody))
                .build();


        getOkHttpClient().newCall(reQuest).enqueue(callback);

    }

    public void doDelete(String url, Callback callback) {

        String token = MyApplication.getInstance().getToken();

        Request reQuest = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + token)
                .delete()
                .build();


        getOkHttpClient().newCall(reQuest).enqueue(callback);

    }

    public void doPost(String url, String json, Callback callback) {

        MediaType JSON = MediaType.get("application/json");
        RequestBody body = RequestBody.create(json, JSON);

        String token = MyApplication.getInstance().getToken();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + token)
                .post(body)
                .build();


        getOkHttpClient().newCall(request).enqueue(callback);

    }


    public void doPut(String url, String json, Callback callback) {

        MediaType JSON = MediaType.get("application/json");
        RequestBody body = RequestBody.create(json, JSON);

        String token = MyApplication.getInstance().getToken();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + token)
                .put(body)
                .build();

        getOkHttpClient().newCall(request).enqueue(callback);

    }

    public void upImageFunc( File file, String imageType, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        // 要上传的文件

        MediaType mediaType = MediaType.parse(imageType);
        RequestBody fileBody = RequestBody.create(file, mediaType);
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM) // 表单类型(必填)
                .addFormDataPart("file", file.getName(), fileBody)
                .build();

        Request request = new Request.Builder()
                .url(API.uploadImage)
                .header("Content-Type", "multipart/form-data")
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
