package com.example.medicalservice.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.LoginBean;
import com.example.medicalservice.dataBaseBean.UserBean;
import com.example.medicalservice.databinding.ActivityLoginViewBinding;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.OkHttpUtil;
import com.example.medicalservice.tools.SpUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginView extends BaseActivity<ActivityLoginViewBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        super.initView();
        viewBinding.back.setOnClickListener(view -> finish());
        viewBinding.register.setOnClickListener(view -> {
            go(RegisterActivity.class);
            finish();
        });
    }

    @Override
    public void initData() {
        super.initData();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(intent);
            }
        }

        viewBinding.login.setOnClickListener(v -> {

            if (viewBinding.userName.getText().toString().equals("") || viewBinding.password.getText().toString().equals("")) {
                showToast(getString(R.string.login_tip1));

                return;
            }

            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("username", viewBinding.userName.getText().toString());
                jsonObject.put("password", viewBinding.password.getText().toString());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


            OkHttpUtil.getInstance().doPost(API.login, jsonObject.toString(), new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    runOnUiThread(() -> showToast(getString(R.string.login_tip2)));

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                    if (response.body() != null) {
                        Message message = new Message();
                        message.obj = response.body().string();
                        handler.sendMessage(message);


                    }
                }
            });

        });
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            String json = msg.obj.toString();


            LoginBean loginBean = new Gson().fromJson(json, LoginBean.class);

            if (loginBean != null && loginBean.getCode() == 200) {
                showToast(getString(R.string.login_tip3));
                String userName = viewBinding.userName.getText().toString();
                String password = viewBinding.password.getText().toString();
                SpUtils.putString(activity, "token", loginBean.getData().getAccess_token());
                MyApplication.getInstance().setToken(loginBean.getData().getAccess_token());

                OkHttpUtil.getInstance().doGet(API.userInfo(), new okhttp3.Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        UserBean userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);
                        if (userBean != null) {
                            userBean.setLoginStatus(false);
                            MyApplication.getInstance().db.userDao().updateUser(userBean);
                        }
                        try {
                            if (response.body() != null) {
                                String data = response.body().string();

                                Log.d(API.userInfo(), data);
                                JSONObject jsonObject2 = new JSONObject(data);
                                if (jsonObject2.getInt("code") == 200) {

                                    UserBean userData = new UserBean();
                                    userData.setLoginStatus(true);
                                    userData.setPassword(password);
                                    userData.setUserId(jsonObject2.getJSONObject("data").getInt("userId"));
                                    userData.setAvatar(jsonObject2.getJSONObject("data").getString("ownerPic"));
                                    userData.setNickName(jsonObject2.getJSONObject("data").getString("ownerName"));
                                    userData.setUserName(userName);
                                    userData.setOwnerAge(jsonObject2.getJSONObject("data").getString("ownerAge"));
                                    userData.setPhonenumber(jsonObject2.getJSONObject("data").getString("ownerPhone"));
                                    userData.setOwnerSex(jsonObject2.getJSONObject("data").getString("ownerSex"));
                                    userData.setOwnerId(jsonObject2.getJSONObject("data").getString("ownerId"));
                                    userData.setOwnerRoomNum(jsonObject2.getJSONObject("data").getString("ownerRoomNum"));


                                    updateToken(loginBean.getData().getAccess_token(), userData.getNickName(), userData.getAvatar(), String.valueOf(userData.getUserId()), userData.getPhonenumber());
                                    if (MyApplication.getInstance().db.userDao().getUserById(userData.getUserId()) == null) {
                                        MyApplication.getInstance().db.userDao().insert(userData);
                                    } else {
                                        MyApplication.getInstance().db.userDao().updateUser(userData);
                                    }
                                    go(AddUserActivity.class);
                                    finish();

                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            } else {
                showToast( "登录失败");
            }

        }
    };

}