package com.example.medicalservice.baseFile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.example.medicalservice.MyApplication;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.OkHttpUtil;
import com.example.medicalservice.tools.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BaseFragment<T extends ViewBinding> extends Fragment {
    protected T viewBinding;

    protected Context mContext;
    protected Activity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        Class cls = (Class) type.getActualTypeArguments()[0];
        try {
            Method inflate = cls.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            viewBinding = (T) inflate.invoke(null, inflater, container, false);
        }  catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return viewBinding.getRoot();
    }
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.activity=getActivity();
        initView();
        initData();
    }


    public void initView(){

    }

    public void initData() {

    }

    public void updateMoney(Handler handler){
        OkHttpUtil.getInstance().doGet(API.userInfo(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                try {
                    if (response.body() != null) {
                        JSONObject jsonObject2 = new JSONObject(response.body().string());
                        if (jsonObject2.getInt("code") == 200) {
                            MyApplication.getInstance().setOwnerRemainMoney(jsonObject2.getJSONObject("data").getString("ownerRemainMoney"));
                            Message message = new Message();
                            message.obj = MyApplication.getInstance().getOwnerRemainMoney();
                            handler.sendMessage(message);
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void go(Class clazz) {
        Intent intent = new Intent(activity, clazz);
        startActivity(intent);
    }
    public void go(Class clazz,String msg) {
        Intent intent = new Intent(activity, clazz);
        intent.putExtra("msg",msg);
        startActivity(intent);
    }


    public void go(Class clazz,String msg,String msg2) {
        Intent intent = new Intent(activity, clazz);
        intent.putExtra("msg",msg);
        intent.putExtra("msg2",msg2);
        startActivity(intent);
    }

    public void showToast(String msg){
        ToastUtil.showToast(msg,activity);
    }

}
