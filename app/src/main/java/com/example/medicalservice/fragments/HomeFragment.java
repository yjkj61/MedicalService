package com.example.medicalservice.fragments;

import static android.content.Context.ACTIVITY_SERVICE;

import android.Manifest;
import android.app.ActivityManager;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.medicalservice.MainActivity;
import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.activity.AddUserActivity;
import com.example.medicalservice.activity.ArticleListActivity;
import com.example.medicalservice.activity.ChatView;
import com.example.medicalservice.activity.active.ActiveListActivity;
import com.example.medicalservice.activity.msgmind.MsgRemindActivity;
import com.example.medicalservice.activity.UserInfoInType;
import com.example.medicalservice.activity.userinfo.UserInfoActivity;
import com.example.medicalservice.baseFile.BaseFragment;
import com.example.medicalservice.bean.ADRow;
import com.example.medicalservice.bean.ActiveBannerEntity;
import com.example.medicalservice.bean.ActiveRow;
import com.example.medicalservice.bean.BannerBean;
import com.example.medicalservice.bean.BannerHomeBean;
import com.example.medicalservice.bean.Data;
import com.example.medicalservice.bean.HomeAdBannerEntity;
import com.example.medicalservice.bean.HomeBannerEntity;
import com.example.medicalservice.bean.HomeGridBean;
import com.example.medicalservice.bean.NewWeatherBean;
import com.example.medicalservice.bean.Row;
import com.example.medicalservice.bean.SleepInfoBean;
import com.example.medicalservice.bean.UpdateAppEntity;
import com.example.medicalservice.databinding.FragmentHomeBinding;
import com.example.medicalservice.recycleAdapter.BannerAdapter;
import com.example.medicalservice.recycleAdapter.HomeActiveBannerAdapter;
import com.example.medicalservice.recycleAdapter.HomeAdBannerAdapter;
import com.example.medicalservice.recycleAdapter.HomeBannerAdapter;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.EyeCareHelper;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.OkHttpUtil;
import com.example.medicalservice.tools.TimeTool;
import com.example.medicalservice.bean.WeatherBean;
import com.example.medicalservice.tools.WeatherIcon;
import com.google.gson.Gson;
import com.youth.banner.listener.OnPageChangeListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment<FragmentHomeBinding> {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final int LOCATION_CODE = 301;
    private LocationManager locationManager;
    private String locationProvider = null;


    private String mParam1;
    private String mParam2;

    Timer timer = null;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private void getLocation() {
        //1.获取位置管理器
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        //2.获取位置提供器，GPS或是NetWork
        List<String> providers = locationManager.getProviders(true);

        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
            Log.v("TAG", "定位方式GPS");
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
            Log.v("TAG", "定位方式Network");
        } else {
            showToast("没有可用的位置提供器");
            return;
        }

        //获取权限（如果没有开启权限，会弹出对话框，询问是否开启权限）
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            //请求权限
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_CODE);
        } else {
            //3.获取上次的位置，一般第一次运行，此值为null
            Location location = locationManager.getLastKnownLocation(locationProvider);
            if (location != null) {
                Log.v("TAG", "获取上次的位置-经纬度：" + location.getLongitude() + "   " + location.getLatitude());
                try {
                    getWeather(location.getLongitude() + "," + location.getLatitude());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            } else {
                //监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
                locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);
            }
        }
    }


    public LocationListener locationListener = new LocationListener() {
        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {
        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {
        }

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {

                Log.v("TAG", "监视地理位置变化-经纬度：" + location.getLongitude() + "   " + location.getLatitude());

                try {
                    getWeather(location.getLongitude() + "," + location.getLatitude());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    };


    @Override
    public void initView() {
        super.initView();

        viewBinding.headerInfo.setOnClickListener(v -> {
            String userName = viewBinding.userName.getText().toString();

            //go(userName.equals("请登录") ? AddUserActivity.class : UserInfoInType.class);
            go(userName.equals("请登录") ? AddUserActivity.class : UserInfoActivity.class);
        });


        viewBinding.ai.input.setOnClickListener(v -> go(ChatView.class));

        viewBinding.ai.reboot.setOnClickListener(v -> go(ChatView.class));


    }

    @Override
    public void onResume() {
        super.onResume();

        setUserInfo();
        getWeatherNew();
    }

    @Override
    public void initData()  {
        super.initData();

        if (timer == null) {
            timer = new Timer();
            timer.schedule(tTask, 0, 1000);
        }


        viewBinding.date.setText(TimeTool.Day(0, "MM月dd日"));
        //viewBinding.week.setText(TimeTool.StringData());

        getTime();
//        getLocation();
        initTools();
        initPlayTv();
        initApps();
        try {
            initBanner();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


    }


    private void getWeather(String location) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("location", location);
        OkHttpUtil.getInstance().doPost(API.weather, jsonObject.toString(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() != null) {

                    WeatherBean weatherBean = new Gson().fromJson(response.body().string(), WeatherBean.class);

                    if (weatherBean.getCode() == 200 && weatherBean.getData().getInfocode().equals("10000")) {
                        if (weatherBean.getData().getLives().size() > 0) {
                            WeatherBean.DataDTO.LivesDTO livesDTO = weatherBean.getData().getLives().get(0);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    viewBinding.wendu.setText(livesDTO.getTemperature() + "°");
                                    viewBinding.weatherInfo.setText("风向描述" + livesDTO.getWinddirection() + "\n" + "风力级别：" + livesDTO.getWindpower() + "\n" + "空气湿度:" + livesDTO.getHumidity());

                                    viewBinding.weatherIcon.setImageResource(WeatherIcon.weatherIconGet(livesDTO.getWeather()));
                                }
                            });
                        }
                    }

                }
            }
        });
    }

    private void getTime() {
        OkHttpUtil.getInstance().doGet(API.lunar, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        activity.runOnUiThread(() -> {
                            try {
                                viewBinding.week.setText(TimeTool.StringData() + "\n" + jsonObject.getJSONObject("data").getString("lunarDate"));
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    //获取定位和天气
    private void getWeatherNew() {
        OkHttpUtil.getInstance().doGet(API.weather_new, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() != null) {
                    NewWeatherBean bean = new Gson().fromJson(response.body().string(), NewWeatherBean.class);
                    if (bean.getCode() == 200){
                        if (bean.getData().getLives() != null){
                            if (bean.getData().getLives().size() != 0){
                                activity.runOnUiThread(() -> {
                                    viewBinding.wendu.setText(bean.getData().getLives().get(0).getTemperature() + "℃");
                                    viewBinding.weatherInfo.setText("城市：" + bean.getData().getLives().get(0).getCity() + "\r\n" + "湿度：" + bean.getData().getLives().get(0).getHumidity()
                                            + "\r\n" + "风向：" + bean.getData().getLives().get(0).getWinddirection() + "风");
                                });
                            }
                        }
                    }
                }
            }
        });
    }

    private void setUserInfo() {

        viewBinding.userName.setText(MyApplication.getInstance().getUserName());
        GlideUtils.load(activity, MyApplication.getInstance().getUserHeader(), viewBinding.userHeader, R.drawable.header, 90);

        Log.d("TAG", "setUserInfo: " + MyApplication.getInstance().getUserName());

    }


    private void initBanner() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("adPosition","0");
        //广告banner
        OkHttpUtil.getInstance().doPost(API.homeAdBannerUrl, jsonObject.toString(),new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {

                    HomeAdBannerEntity bannerHomeBean = new Gson().fromJson(response.body().string(), HomeAdBannerEntity.class);

                    if ( bannerHomeBean.getCode() == 200) {
                        List<ADRow> rows = bannerHomeBean.getRows();
                        activity.runOnUiThread(()->{
                            HomeAdBannerAdapter homeAdBannerAdapter = new HomeAdBannerAdapter(rows);
                            homeAdBannerAdapter.OnItemClickCallback(new Function1<ADRow, Unit>() {
                                @Override
                                public Unit invoke(ADRow adRow) {
                                    Intent intent =new  Intent();
                                    intent.setAction("android.intent.action.VIEW");
                                    intent.setData(Uri.parse(adRow.getAdData().replace("<p>","").replace("</p>","")));
                                    startActivity(intent);
                                    return null;
                                }
                            });
                            viewBinding.bannerHome.addBannerLifecycleObserver(getViewLifecycleOwner()).setAdapter(homeAdBannerAdapter);
                        });
                    }


//                    activity.runOnUiThread(() -> viewBinding.bannerHome.addBannerLifecycleObserver(getViewLifecycleOwner())
//                            .setAdapter(new BannerAdapter(bannerBeans))
//                            .addOnPageChangeListener(new OnPageChangeListener() {
//                                @Override
//                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                                }
//
//                                @Override
//                                public void onPageSelected(int position) {
//
//                                }
//
//                                @Override
//                                public void onPageScrollStateChanged(int state) {
//
//                                }
//                            }));

                }

            }
        });
        //资讯banner
        OkHttpUtil.getInstance().doPost(API.homeArticleBannerUrl, jsonObject.toString(),new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {

                    HomeBannerEntity bannerHomeBean = new Gson().fromJson(response.body().string(), HomeBannerEntity.class);

                    if ( bannerHomeBean.getCode() == 200 && bannerHomeBean.getRows().size() != 0) {
                        List<Row> rows = bannerHomeBean.getRows();
                        activity.runOnUiThread(()->{
                            HomeBannerAdapter homeBannerAdapter = new HomeBannerAdapter(rows);
                            homeBannerAdapter.OnItemClickCallback(new Function1<Row, Unit>() {
                                @Override
                                public Unit invoke(Row row) {
                                    go(ArticleListActivity.class,"0");
                                    return null;
                                }
                            });
                            viewBinding.bannerHome1.addBannerLifecycleObserver(getViewLifecycleOwner()).isAutoLoop(false).setAdapter(homeBannerAdapter);
                        });
                    }
                }

            }
        });
        //活动banner
        OkHttpUtil.getInstance().doPost(API.homeActivityBannerUrl, jsonObject.toString(),new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {

                    ActiveBannerEntity bannerHomeBean = new Gson().fromJson(response.body().string(), ActiveBannerEntity.class);

                    if ( bannerHomeBean.getCode() == 200 && bannerHomeBean.getRows().size() != 0) {
                        List<ActiveRow> rows = bannerHomeBean.getRows();
                        activity.runOnUiThread(()->{
                            HomeActiveBannerAdapter homeBannerAdapter = new HomeActiveBannerAdapter(rows);
                            homeBannerAdapter.OnItemClickCallback(new Function1<ActiveRow, Unit>() {
                                @Override
                                public Unit invoke(ActiveRow activeRow) {
                                    go(ActiveListActivity.class,"0");
                                    return null;
                                }
                            });
                            viewBinding.bannerHome2.addBannerLifecycleObserver(getViewLifecycleOwner()).isAutoLoop(false).setAdapter(homeBannerAdapter);
                        });
                    }

                }

            }
        });

    }

    private ActivityManager activityManager;
    private Handler seekBarHandler = new Handler();
    private void initTools() {

        List<HomeGridBean> homeGridBeans = new ArrayList<>();

        homeGridBeans.add(new HomeGridBean(getString(R.string.message), R.drawable.icon_tools_message));
        homeGridBeans.add(new HomeGridBean(getString(R.string.update), R.drawable.icon_tools_update));

        homeGridBeans.add(new HomeGridBean(getString(R.string.clear), R.drawable.icon_tools_speed));
        homeGridBeans.add(new HomeGridBean(getString(R.string.volume_control), R.drawable.icon_tools_voice));

        homeGridBeans.add(new HomeGridBean(getString(R.string.eye_protection_mode), R.drawable.icon_tool_eye));


        MsAdapter msAdapter = new MsAdapter<HomeGridBean>(homeGridBeans, R.layout.home_grid_tools_item) {


            @Override
            public void bindView(ViewHolder holder, HomeGridBean obj) {

                ImageView tools_icon = holder.getView(R.id.tools_icon);
                TextView tools_name = holder.getView(R.id.tools_name);

                tools_icon.setImageResource(obj.getDrawable());
                tools_name.setText(obj.getName());
                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (holder.getItemPosition()){
                            case 0:{
                                Intent intent = new Intent(requireContext(), MsgRemindActivity.class);
                                requireContext().startActivity(intent);
                                break;
                            }
                            case 1:{
                                try {
                                    checkUpdate();
                                } catch (JSONException | PackageManager.NameNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            }
                            case 2:{
                                speedUp();
                                break;
                            }
                            case 3:{
                                seekBarVolume();
                                break;
                            }
                            case 4:{
                                brightness();
                                break;
                            }
                            default:{
                                break;
                            }
                        }
                    }
                });

            }
        };
        viewBinding.tools.setAdapter(msAdapter);
    }

    private void checkUpdate() throws JSONException, PackageManager.NameNotFoundException {
        String type = "0";
        PackageInfo packageInfo = requireContext().getPackageManager().getPackageInfo(requireContext().getPackageName(), 0);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type",type);
        jsonObject.put("version","v"+packageInfo.versionName);
        OkHttpUtil.getInstance().doPost(API.updateAppUIrl, jsonObject.toString(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody body = response.body();
                if(body != null){
                    UpdateAppEntity updateAppEntity = new Gson().fromJson(body.string(), UpdateAppEntity.class);
                    if(updateAppEntity.getCode() == 200){
                        if(updateAppEntity.component2().getMsg().contains("当前为最新版本")){
                            showToast(updateAppEntity.getMsg());
                            return;
                        }
                        if(updateAppEntity.component2().getMsg().contains("新版本版本号为")){
                            UpdateAppDialogFragment dialogFragment = new UpdateAppDialogFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(UpdateAppDialogFragment.UPDATE_APP_ENTITY,updateAppEntity.component2());
                            dialogFragment.setArguments(bundle);
                            dialogFragment.show(getChildFragmentManager(),"update_dialog");
                        }
                    }
                }
            }
        });
    }

    /**
     * 切换护眼和正常模式
     */
    private void brightness(){
        MainActivity activity1 = (MainActivity)getActivity();
        if(EyeCareHelper.getEyeCare()){
            activity1.closeEye();
            EyeCareHelper.saveEyeCare(false);
        }else {
            activity1.openEye();
            EyeCareHelper.saveEyeCare(true);
        }

    }


    /**
     * 显示seekbar设置音量
     */
    private void seekBarVolume(){
        viewBinding.seekBarLayout.setVisibility(View.VISIBLE);
        viewBinding.seekBar.setProgress(getCurrentVolume());
        viewBinding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setVolume(progress);
                seekBarHandler.removeCallbacksAndMessages(null);
                seekBarHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideSeekBar();
                    }
                }, 3000);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBarHandler.removeCallbacksAndMessages(null);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideSeekBar();
                    }
                }, 3000);
            }
        });

        seekBarHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideSeekBar();
            }
        }, 3000);
    }
    private void hideSeekBar(){
        viewBinding.seekBarLayout.setVisibility(View.GONE);
    }
    private void speedUp(){
        if (activityManager == null){
            activityManager = (ActivityManager) requireContext().getSystemService(ACTIVITY_SERVICE);
        }
        long beforeMem = getAvailMemory();
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        if(runningAppProcesses != null && !runningAppProcesses.isEmpty()){
            for (ActivityManager.RunningAppProcessInfo runningApp : runningAppProcesses) {
                if (runningApp.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                    String[] pkgList = runningApp.pkgList;
                    for (String pkg : pkgList) {
                        activityManager.killBackgroundProcesses(pkg);
                    }
                }
            }
        }
        long afterMem = getAvailMemory();
        Toast.makeText(requireContext(), "为您节省了" + (afterMem - beforeMem) + "M内存", Toast.LENGTH_SHORT).show();
    }
    //获取当前可用内存
    private long getAvailMemory() {
        activityManager = (ActivityManager) requireContext().getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem / (1024 * 1024);
    }

    /**
     * 获取当前音量
     * @return
     */
    private int getCurrentVolume(){
        AudioManager audioManager =(AudioManager)requireContext().getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        return(int)(currentVolume*(100.f/ maxVolume));//将实际音量值转换为seekBar的进度值
    }

    /**
     * 设置音量
     * @param value
     */
    private void setVolume(int value){
        AudioManager audioManager =(AudioManager)requireContext().getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int newVolume = (int)(value * (maxVolume / 100.0f));
       audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,newVolume,0);
    }
    private void initPlayTv() {

        List<HomeGridBean> homeGridBeans = new ArrayList<>();

        homeGridBeans.add(new HomeGridBean("", R.drawable.icon_home_cctv));
        homeGridBeans.add(new HomeGridBean("", R.drawable.icon_home_aqy));

        homeGridBeans.add(new HomeGridBean("", R.drawable.icon_home_qqmusic));
        homeGridBeans.add(new HomeGridBean("", R.drawable.icon_home_douyin));

        DisplayMetrics outMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(outMetrics);
        float widthPixel = (float) (outMetrics.widthPixels * 0.8);

        float width = (float) widthPixel / 2 - 340;

        float height = (float) (width * 0.48);

        Log.d("TAG", "initPlayTv: " + width + "-----" + height);

        MsAdapter msAdapter = new MsAdapter<HomeGridBean>(homeGridBeans, R.layout.home_grid_play_item) {

            @Override
            public void bindView(ViewHolder holder, HomeGridBean obj) {

                ImageView tools_icon = holder.getView(R.id.image);
                tools_icon.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) height));

                tools_icon.setImageResource(obj.getDrawable());


            }
        };
        viewBinding.player.setAdapter(msAdapter);

        viewBinding.player.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri uri = null;
                switch (position){
                    case 0:
                        uri = Uri.parse("https://tv.cctv.com/live/");https://www.iqiyi.com/
                        break;
                    case 1:
                        uri = Uri.parse("https://www.iqiyi.com/");
                        break;
                    case 2:
                        uri = Uri.parse("https://y.qq.com/");
                        break;
                    case 3:
                        uri = Uri.parse("https://www.douyin.com/");
                        break;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    private void initApps() {

        List<HomeGridBean> homeGridBeans = new ArrayList<>();

        homeGridBeans.add(new HomeGridBean("", R.drawable.icon_app_wx));
        homeGridBeans.add(new HomeGridBean("", R.drawable.icon_app_camera));

        homeGridBeans.add(new HomeGridBean("", R.drawable.icon_app_photo));
        homeGridBeans.add(new HomeGridBean("", R.drawable.iconapp_radio));

        homeGridBeans.add(new HomeGridBean("", R.drawable.icon_app_fils));
        homeGridBeans.add(new HomeGridBean("", R.drawable.icon_app_apay));

        homeGridBeans.add(new HomeGridBean("", R.drawable.icon_app_jsq));

        homeGridBeans.add(new HomeGridBean("", R.drawable.icon_app_jsq));

        homeGridBeans.add(new HomeGridBean("", R.drawable.icon_app_map));

        homeGridBeans.add(new HomeGridBean("", R.drawable.icon_app_info));


        MsAdapter msAdapter = new MsAdapter<HomeGridBean>(homeGridBeans, R.layout.home_grid_app_item) {

            @Override
            public void bindView(ViewHolder holder, HomeGridBean obj) {

                ImageView app_icon = holder.getView(R.id.app_icon);
                app_icon.setImageResource(obj.getDrawable());

            }
        };
        viewBinding.apps.setAdapter(msAdapter);
    }

    final Handler handler = new Handler();
    //更新UI
    final Runnable run = () -> updateShowTime();
    //定时器
    TimerTask tTask = new TimerTask() {

        @Override
        public void run() {
            handler.post(run);
        }
    };

    private void updateShowTime() {

        viewBinding.time.setText(TimeTool.Day(0, "HH:mm"));
    }

}