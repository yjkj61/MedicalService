package com.example.medicalservice;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.medicalservice.activity.ChatView;
import com.example.medicalservice.activity.TodoActivity;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.GoodBean;
import com.example.medicalservice.bean.LoginBean;
import com.example.medicalservice.dataBaseBean.UserBean;
import com.example.medicalservice.databinding.ActivityMainBinding;
import com.example.medicalservice.fragments.CateringServices;
import com.example.medicalservice.fragments.HomeFragment;
import com.example.medicalservice.fragments.Healthcare;
import com.example.medicalservice.fragments.LeisureEntertainment;
import com.example.medicalservice.fragments.MallGroupBuying;
import com.example.medicalservice.fragments.PropertyFiveBlessings;
import com.example.medicalservice.fragments.Tourism;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.OkHttpUtil;
import com.example.medicalservice.tools.SpUtils;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.Setting;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.VoiceWakeuper;
import com.iflytek.cloud.WakeuperListener;
import com.iflytek.cloud.WakeuperResult;
import com.iflytek.cloud.util.ResourceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private final ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<Integer> selectedIconRes = new ArrayList<>();         //tab选中图标集合
    private ArrayList<Integer> unselectedIconRes = new ArrayList<>();       //tab未选中图标集合
    private ArrayList<String> titleRes = new ArrayList<>();                 //tab标题集合
    private List<CustomTabEntity> data = new ArrayList<>();
    private final List<String> mPermissionList = new ArrayList<>();
    private final int mRequestCode = 100;//权限请求码

    private VoiceWakeuper mIvw;
    private String keep_alive = "1";
    private String ivwNetMode = "1";

    private final String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=2b11d2f4");

        Setting.setLogLevel(Setting.LOG_LEVEL.low);

        wakeUp();
    }

    private void wakeUp() {
        mIvw = VoiceWakeuper.createWakeuper(this, null);

        mIvw.setParameter(SpeechConstant.IVW_SST, "wakeup");
        mIvw.setParameter(SpeechConstant.KEEP_ALIVE, keep_alive);
        mIvw.setParameter(SpeechConstant.IVW_RES_PATH, getResource());
        mIvw.setParameter(SpeechConstant.IVW_NET_MODE, ivwNetMode);
        mIvw.startListening(new WakeuperListener() {
            @Override
            public void onBeginOfSpeech() {

                Log.d("mIvw", "onBeginOfSpeech: ");
            }

            @Override
            public void onResult(WakeuperResult wakeuperResult) {
                Log.d("mIvw", "onResult: " + wakeuperResult.getResultString());

                if (ChatView.mActivity != null) {
                    ChatView.mActivity.finish();
                }
                go(ChatView.class, "open");
            }

            @Override
            public void onError(SpeechError speechError) {
                Log.d("mIvw", "onError: " + speechError.getErrorCode());
            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

            }

            @Override
            public void onVolumeChanged(int i) {

            }
        });
    }

    private String getResource() {
        final String resPath = ResourceUtil.generateResourcePath(this, ResourceUtil.RESOURCE_TYPE.assets, "ivw/2b11d2f4" + ".jet");
        Log.d("TAG", "resPath: " + resPath);
        return resPath;
    }

    @Override
    public void initView() {
        super.initView();
        viewBinding.viewPage.setNoScroll(true);
        //首页
        fragments.add(HomeFragment.newInstance("", ""));
        //健康医疗
        fragments.add(Healthcare.newInstance());
        //物业服务
        fragments.add(PropertyFiveBlessings.newInstance());
        //旅游
        fragments.add(Tourism.newInstance());
        //餐饮服务
        fragments.add(CateringServices.newInstance());
        //商城团购
        fragments.add(MallGroupBuying.newInstance());
        //休闲娱乐
        fragments.add(LeisureEntertainment.newInstance());

        selectedIconRes.add(R.mipmap.icon_home_unselect);
        selectedIconRes.add(R.mipmap.icon_healthcare_unselect);
        selectedIconRes.add(R.mipmap.icon_property_five_blessings_unselect);
        selectedIconRes.add(R.mipmap.icon_tourism_unselect);
        selectedIconRes.add(R.mipmap.icon_catering_services_unselect);
        selectedIconRes.add(R.mipmap.icon_mall_group_buying_unselect);
        selectedIconRes.add(R.mipmap.icon_leisure_entertainment_unselect);

        unselectedIconRes.add(R.mipmap.icon_home_unselect);
        unselectedIconRes.add(R.mipmap.icon_healthcare_unselect);
        unselectedIconRes.add(R.mipmap.icon_property_five_blessings_unselect);
        unselectedIconRes.add(R.mipmap.icon_tourism_unselect);
        unselectedIconRes.add(R.mipmap.icon_catering_services_unselect);
        unselectedIconRes.add(R.mipmap.icon_mall_group_buying_unselect);
        unselectedIconRes.add(R.mipmap.icon_leisure_entertainment_unselect);


        titleRes.add(getString(R.string.main_home_string));
        titleRes.add(getString(R.string.main_healthcare_string));
        titleRes.add(getString(R.string.main_property_five_blessings_string));
        titleRes.add(getString(R.string.main_tourism_string));
        titleRes.add(getString(R.string.main_catering_services_string));
        titleRes.add(getString(R.string.main_mall_group_buying_string));
        titleRes.add(getString(R.string.main_leisure_entertainment_string));

        //go(Weather.class);

        for (int i = 0; i < titleRes.size(); i++) {
            final int index = i;
            data.add(new CustomTabEntity() {
                @Override
                public String getTabTitle() {
                    return titleRes.get(index);
                }

                @Override
                public int getTabSelectedIcon() {
                    return selectedIconRes.get(index);
                }

                @Override
                public int getTabUnselectedIcon() {
                    return unselectedIconRes.get(index);
                }
            });
        }

        MainActivityPageAdapter myPagerAdapter = new MainActivityPageAdapter(getSupportFragmentManager(), fragments);
        viewBinding.tabLayout.setTabData((ArrayList<CustomTabEntity>) data);
        viewBinding.viewPage.setAdapter(myPagerAdapter);
        viewBinding.viewPage.setCurrentItem(0);
        viewBinding.viewPage.setOffscreenPageLimit(1);
        myPagerAdapter.notifyDataSetChanged();

        for (int i = 0; i < viewBinding.tabLayout.getTabCount(); i++) {
            if (0 == i) {
                viewBinding.tabLayout.getTitleView(0).setTextSize(10f);
                viewBinding.tabLayout.getTitleView(0).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

                int width = 80;
                int height = 80;
                viewBinding.tabLayout.getIconView(0).setLayoutParams(new LinearLayout.LayoutParams(width, height));

                viewBinding.tabLayout.getTitleView(0).setTranslationY(0);
                viewBinding.tabLayout.getIconView(0).setTranslationY(0);

            } else {

                int width = 50;
                int height = 50;
                viewBinding.tabLayout.getIconView(i).setLayoutParams(new LinearLayout.LayoutParams(width, height));
                viewBinding.tabLayout.getTitleView(i).setTextSize(8f);
                viewBinding.tabLayout.getTitleView(i).setTypeface(Typeface.DEFAULT);

                viewBinding.tabLayout.getTitleView(i).setTranslationY(20);
                viewBinding.tabLayout.getIconView(i).setTranslationY(20);
            }
        }

        initPermission();

        MyApplication.getInstance().setToken(SpUtils.getString(activity, "token", ""));


        new Thread(() -> {

            UserBean userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);

            if (userBean != null) {
                Log.d("TAG", "user-iofo" + userBean.getUserId() + ":" + userBean.getUserName());

                MyApplication.getInstance().setUserName(userBean.getNickName());
                MyApplication.getInstance().setUserHeader(userBean.getAvatar());
                MyApplication.getInstance().setUserId(String.valueOf(userBean.getUserId()));
                MyApplication.getInstance().setPhoneNumber(userBean.getPhonenumber());

                refreshToken(userBean);
            }

        }).start();

    }

    //进入app刷新token
    private void refreshToken(UserBean userBean) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("username", userBean.getUserName());
            jsonObject.put("password", userBean.getPassword());
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
                        if (loginBean.getData() != null){
                            updateToken(loginBean.getData().getAccess_token(), userBean.getNickName(), userBean.getAvatar(), String.valueOf(userBean.getUserId()), userBean.getPhonenumber());
                        }
                    } else {
                        runOnUiThread(() -> showToast("认证刷新失败"));
                    }

                }
            }
        });
    }


    @Override
    public void initData() {
        super.initData();

        viewBinding.tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                //显示相应的item界面
                viewBinding.viewPage.setCurrentItem(position);

                for (int i = 0; i < viewBinding.tabLayout.getTabCount(); i++) {
                    if (position == i) {

                        viewBinding.tabLayout.getTitleView(position).setTextSize(10f);
                        viewBinding.tabLayout.getTitleView(position).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

                        int width = 80;
                        int height = 80;
                        viewBinding.tabLayout.getIconView(position).setLayoutParams(new LinearLayout.LayoutParams(width, height));
                        viewBinding.tabLayout.getTitleView(position).setTranslationY(0);
                        viewBinding.tabLayout.getIconView(position).setTranslationY(0);


                    } else {

                        viewBinding.tabLayout.getTitleView(i).setTextSize(9f);
                        viewBinding.tabLayout.getTitleView(i).setTypeface(Typeface.DEFAULT);
                        int width = 50;
                        int height = 50;
                        viewBinding.tabLayout.getIconView(i).setLayoutParams(new LinearLayout.LayoutParams(width, height));
                        viewBinding.tabLayout.getTitleView(i).setTranslationY(20);
                        viewBinding.tabLayout.getIconView(i).setTranslationY(20);

                    }
                }

            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        viewBinding.viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                //设置相应选中图标和颜色
                viewBinding.tabLayout.setCurrentTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    private void animator(View view, float value) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "translationY", 100)
        );
        animatorSet.start();

    }

    private void initPermission() {
        mPermissionList.clear();//清空已经允许的没有通过的权限
        //逐个判断是否还有未通过的权限
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permission);//添加还未授予的权限到mPermissionList中
            }
        }
        //申请权限
        if (mPermissionList.size() > 0) {//有权限没有通过，需要申请
            ActivityCompat.requestPermissions(this, permissions, mRequestCode);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mRequestCode == requestCode) {
            for (int grantResult : grantResults) {
                if (grantResult == -1) {
                    break;
                }
            }
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 销毁合成对象
        mIvw = VoiceWakeuper.getWakeuper();
        if (mIvw != null) {
            mIvw.destroy();
        }
    }
}