package com.example.medicalservice.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.LoginBean;
import com.example.medicalservice.bean.ProperTypeBean;
import com.example.medicalservice.dataBaseBean.UserBean;
import com.example.medicalservice.databinding.ActivityRegisterBinding;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.BitmapUtil;
import com.example.medicalservice.tools.ContentUriUtil;
import com.example.medicalservice.tools.FileUtil;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.MarkBean;
import com.example.medicalservice.tools.OkHttpUtil;
import com.example.medicalservice.tools.SpUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {

    private ActivityResultLauncher<Intent> intentActivityResultLauncher;
    private String avatar = "";
    private String markId = "";
    private String markName = "";

    private List<MarkBean> markBeans = new ArrayList<>();

    private TimeCount timeCount = new TimeCount(60000, 1000);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        super.initView();

        viewBinding.back.setOnClickListener(view -> finish());

        viewBinding.header.setOnClickListener(v -> {
            if (getPermissions()) {

                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intentActivityResultLauncher.launch(intent);


            } else {
                String[] strings = new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this, strings, 1);
            }
        });
        getMarkList();
    }

    private void getMarkList() {
        OkHttpUtil.getInstance().doGet(API.selectSysMarkerNameList, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if(response.body()!=null){
                    JsonArray jsonArray = JsonParser.parseString(response.body().string()).getAsJsonArray();
                    Gson gson = new Gson();
                    for (JsonElement jsonElement : jsonArray) {
                        MarkBean markBean = gson.fromJson(jsonElement, MarkBean.class);
                        markBeans.add(markBean);
                    }

                    runOnUiThread(() -> intiMarkList());

                }
            }
        });
    }



    private void intiMarkList(){


        List<String> nameList = new ArrayList<>();

        for (MarkBean markBean:
              markBeans) {
            nameList.add(markBean.getMarkerName());
        }


        final ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nameList);

        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //添加数据
        viewBinding.markName.setAdapter(ad);

        viewBinding.markName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                markName = markBeans.get(position).getMarkerName();
                markId = String.valueOf(markBeans.get(position).getMarkerId());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                markName = "";
                markId = "";

            }
        });
    }
    @Override
    public void initData() {
        super.initData();

        intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getData() != null && result.getResultCode() == Activity.RESULT_OK) {
                Uri uri = result.getData().getData();
                if (uri != null) {

                    String path = ContentUriUtil.getPath(getApplicationContext(), uri);

                    String type = getFileType(path);
                    File file = new File(BitmapUtil.compressImage(path));
                    if (type.equals("jpg") || type.equals("png")) {
                        OkHttpUtil.getInstance().upImageFunc(file, type, new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                                Log.d("TAG", "onFailure: " + e);

                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                                if (response.body() != null) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.body().string());
                                        if (jsonObject.getInt("code") == 200) {
                                            avatar = jsonObject.getJSONObject("data").getString("url");
                                            runOnUiThread(() -> GlideUtils.load(activity, avatar, viewBinding.header));
                                        }
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }

                                }

                            }
                        }); //uploadImage(path);
                    } else {
                        showToast("您的图片格式不正确");
                    }

                }
            }
        });

        viewBinding.login.setOnClickListener(v -> {

            if (viewBinding.userName.getText().toString().equals("") || viewBinding.password.getText().toString().equals("") || viewBinding.verifyCode.getText().toString().equals("")) {
                showToast("请填写完整");

                return;
            }

            if (avatar.isEmpty()) {
                showToast("请上传头像");
                return;
            }

            if(markName.isEmpty()||markId.isEmpty()){
                showToast("请选择运营商");
                return;
            }


            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("phoneNumber", viewBinding.phoneNumber.getText().toString());
                jsonObject.put("username", viewBinding.userName.getText().toString());
                jsonObject.put("password", viewBinding.password.getText().toString());
                jsonObject.put("avatar", avatar);
                jsonObject.put("verifyCode", viewBinding.verifyCode.getText().toString());
                jsonObject.put("markId",markId);
                jsonObject.put("markName",markName);

                Log.d(API.addUser, jsonObject.toString());

                viewBinding.login.setEnabled(false);
                OkHttpUtil.getInstance().doPost(API.addUser, jsonObject.toString(), new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        if (response.body() != null) {
                            try {
                                String data = response.body().string();
                                Log.d("TAG", "onResponse: "+data);
                                JSONObject json = new JSONObject(data);
                                if (json.getInt("code") == 200) {
                                    JSONObject login = new JSONObject();
                                    login.put("username", viewBinding.userName.getText().toString());
                                    login.put("password", viewBinding.password.getText().toString());

                                    runOnUiThread(() -> {
                                        showToast("注册成功");
                                        viewBinding.upload.setVisibility(View.VISIBLE);
                                    });

                                    new Thread(() -> {
                                        try {
                                            Thread.sleep(2000);
                                            OkHttpUtil.getInstance().doPost(API.login, login.toString(), new Callback() {
                                                @Override
                                                public void onFailure(@NonNull Call call1, @NonNull IOException e) {

                                                }

                                                @Override
                                                public void onResponse(@NonNull Call call1, @NonNull Response response1) throws IOException {

                                                    if (response1.body() != null) {
                                                        Message message = new Message();
                                                        message.obj = response1.body().string();
                                                        handler.sendMessage(message);
                                                    }
                                                }
                                            });
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }

                                    }).start();
                                }else {
                                    runOnUiThread(() -> {
                                        try {
                                            viewBinding.login.setEnabled(true);
                                            showToast(json.getString("msg"));
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }


        });

        viewBinding.seenCode.setOnClickListener(v -> {
            if (viewBinding.phoneNumber.getText().toString().isEmpty()) {
                showToast("请填写手机号");
                return;
            }
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("phoneNumber", viewBinding.phoneNumber.getText().toString());
                OkHttpUtil.getInstance().doPost(API.seedMsg, jsonObject.toString(), new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        if (response.body() != null) {
                            try {
                                JSONObject jsonObject1 = new JSONObject(response.body().string());
                                if (jsonObject1.getInt("code") == 200) {
                                    timeCount.start();
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }

                    }
                });

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

    }

    //创建一个倒计时功能类
    private class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //每次间隔时间的回调，millisUntilFinished剩余的时间，单位是毫秒
        @Override
        public void onTick(long millisUntilFinished) {
            viewBinding.seenCode.setClickable(false);
            viewBinding.seenCode.setEnabled(false);
            viewBinding.seenCode.setText("重新发送" + "(" + millisUntilFinished / 1000 + "s" + ")");
        }

        //倒计时结束时的回调
        @Override
        public void onFinish() {
            viewBinding.seenCode.setClickable(true);
            viewBinding.seenCode.setEnabled(true);

            viewBinding.seenCode.setText("获取验证码");
        }
    }


    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            String json = msg.obj.toString();

            viewBinding.upload.setVisibility(View.GONE);
            viewBinding.login.setEnabled(true);
            LoginBean loginBean = new Gson().fromJson(json, LoginBean.class);

            if ( loginBean.getCode() == 200) {
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
                                    UserInfoInType.mActivity.finish();
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

    public static String getFileType(String name) {
        File file = new File(name);
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return suffix;
    }

    private boolean getPermissions() {


        return ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                String[] strings = {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
                ActivityCompat.requestPermissions(this, strings, 1);


            } else {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intentActivityResultLauncher.launch(intent);
            }

        }


    }
}