package com.example.medicalservice.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.LoginBean;
import com.example.medicalservice.dataBaseBean.UserBean;
import com.example.medicalservice.databinding.ActivityAddUserBinding;
import com.example.medicalservice.diyView.DialogUntil;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.OkHttpUtil;
import com.google.gson.Gson;
import com.lljjcoder.style.citypickerview.CityPickerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddUserActivity extends BaseActivity<ActivityAddUserBinding> {

    List<UserBean> userBeanList;


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

        userBeanList = new ArrayList<>();


        UserBean userBean = new UserBean();
        userBean.setUserId(0);
        userBean.setAvatar("add");
        userBean.setUserName(getString(R.string.add_user));
        userBeanList.add(userBean);

        MsAdapter msAdapter = new MsAdapter<UserBean>(userBeanList, R.layout.add_user_grid_item) {

            @Override
            public void bindView(ViewHolder holder, UserBean obj) {
                TextView name = holder.getView(R.id.name);
                TextView isYou = holder.getView(R.id.isYou);

                ImageView user_header = holder.getView(R.id.user_header);

                name.setText(obj.getUserName());

                Log.d("TAG", "bindView: " + userBean.getAvatar());

                if (!obj.getUserName().equals(getString(R.string.add_user)) && obj.getUserId() != 0) {
                    GlideUtils.load(getApplicationContext(), obj.getAvatar(), user_header, R.drawable.header, 90);
                }

                if (obj.isLoginStatus()) {
                    isYou.setVisibility(View.VISIBLE);
                } else {
                    isYou.setVisibility(View.GONE);
                }

                holder.getItemView().setOnClickListener(view -> {
                    if (obj.getUserName().equals(getString(R.string.add_user)) && obj.getUserId() == 0) {
                        go(LoginView.class);
                        finish();
                    } else {

                        if (!obj.isLoginStatus()) {
                            obj.setLoginStatus(true);
                            changeUserLoginStatus(obj, this);
                        }
                    }
                });

            }
        };


        viewBinding.addUserGrid.setAdapter(msAdapter);

        getAllUser(msAdapter);
    }


    private void getAllUser(MsAdapter msAdapter) {

        new Thread(() -> {
            userBeanList.addAll(0, MyApplication.getInstance().db.userDao().getAllUser());

            runOnUiThread(msAdapter::notifyDataSetChanged);

        }).start();

    }

    private void changeUserLoginStatus(UserBean loginUser, MsAdapter msAdapter) {

        new Thread(() -> {
            UserBean userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);

            if (userBean != null) {
                userBean.setLoginStatus(false);
                MyApplication.getInstance().db.userDao().updateUser(userBean);
            }


            MyApplication.getInstance().db.userDao().updateUser(loginUser);


            List<UserBean> userBeans = MyApplication.getInstance().db.userDao().getAllUser();

            userBeanList.clear();

            userBeanList.addAll(userBeans);


            UserBean newAdd = new UserBean();
            newAdd.setUserId(0);
            newAdd.setAvatar("add");
            newAdd.setUserName(getString(R.string.add_user));
            userBeanList.add(newAdd);

            runOnUiThread(msAdapter::notifyDataSetChanged);

            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("username", loginUser.getUserName());
                jsonObject.put("password", loginUser.getPassword());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


            OkHttpUtil.getInstance().doPost(API.login, jsonObject.toString(), new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.body() != null) {
                        LoginBean loginBean = new Gson().fromJson(response.body().string(), LoginBean.class);

                        if (loginBean != null && loginBean.getCode() == 200) {
                            updateToken(loginBean.getData().getAccess_token(), loginUser.getNickName(), loginUser.getAvatar(), String.valueOf(loginUser.getUserId()), loginUser.getPhonenumber());
                        }else {
                            runOnUiThread(() -> showToast("切换失败"));
                        }
                    }


                }
            });

        }).start();


    }
}