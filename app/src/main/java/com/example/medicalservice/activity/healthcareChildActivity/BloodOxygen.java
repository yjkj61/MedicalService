package com.example.medicalservice.activity.healthcareChildActivity;

import static com.clj.fastble.utils.HexUtil.formatHexString;
import static com.clj.fastble.utils.HexUtil.hexStringToBytes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.BleScanRuleConfig;
import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.activity.LoginView;
import com.example.medicalservice.activity.healthcareChildActivity.bloodDataChildView.BloodHistory;
import com.example.medicalservice.activity.healthcareChildActivity.bloodDataChildView.HealthAdvice;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.AcidListBean;
import com.example.medicalservice.bean.BloodPressureListBean;
import com.example.medicalservice.bean.BloodSugarListBean;
import com.example.medicalservice.bean.BluetoothBean;
import com.example.medicalservice.bean.CholesterolListBean;
import com.example.medicalservice.bean.OxygenListBean;
import com.example.medicalservice.bean.PostOxygenBean;
import com.example.medicalservice.bean.RateListBean;
import com.example.medicalservice.bean.TemperatureListBean;
import com.example.medicalservice.dataBaseBean.UserBean;
import com.example.medicalservice.databinding.ActivityBloodPressureBinding;
import com.example.medicalservice.diyView.ChartView;
import com.example.medicalservice.recycleAdapter.BloodOxygenHistoryAdapter;
import com.example.medicalservice.recycleAdapter.DialogSelectAdapter;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.OkHttpUtil;
import com.example.medicalservice.tools.SpUtils;
import com.example.medicalservice.tools.StringUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BloodOxygen extends BaseActivity<ActivityBloodPressureBinding> {

    private boolean dialogBoole = false;

    private boolean start = false;

    private List<OxygenListBean.RowsDTO> OxygenRowsDTOS = new ArrayList<>();

    private List<BloodSugarListBean.RowsDTO> BloodSugarRowsDTOS = new ArrayList<>();

    private List<CholesterolListBean.RowsDTO> CholesterolRowsDTOS = new ArrayList<>();

    private List<AcidListBean.RowsDTO> AcidRowsDTOS = new ArrayList<>();
    private List<TemperatureListBean.RowsDTO> TemperatureRowsDTOS = new ArrayList<>();

    private List<RateListBean.RowsDTO> RateRowsDTOS = new ArrayList<>();


    private ArrayList<String> xLabel = new ArrayList<>();

    private List<List<Float>> yLabels = new ArrayList<>();

    private List<Float> yLabel = new ArrayList<>();

    private ChartView chartView;

    private BloodOxygenHistoryAdapter bloodOxygenHistoryAdapter;

    private String type;

    private UserBean userBean;

    private String device_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        super.initView();

        userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);


        type = getIntent().getStringExtra("msg");

        viewBinding.type.setText(type);


        viewBinding.back.setOnClickListener(v -> finish());

        viewBinding.allHistory.setOnClickListener(v -> go(BloodHistory.class));
        viewBinding.advice.setOnClickListener(v -> go(HealthAdvice.class));

        viewBinding.start.setOnClickListener(v -> {
            start = !start;
            showToast("开始测量" + device_name);
            viewBinding.start.setText(start ? getString(R.string.measuring) : getString(R.string.start_measure));

        });
        chartView = new ChartView();

        viewBinding.progress.setStepMax(100);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        viewBinding.listHistory.setLayoutManager(linearLayoutManager);


        if(userBean == null){
            go(LoginView.class);
            showToast("请先登录");
            finish();
            return;
        }

        switch (type) {
            case "血氧":
                getOxygenListData();
                break;
            case "血糖":
                getSugarListData();
                break;
            case "胆固醇":
                getCholesterolListData();
                break;
            case "尿酸":
                getAcidListData();
                break;
            case "体温":
                getTemperatureListData();
                break;
            case "脉搏":
                getRateListData();
                break;


        }

        connectDevice();
    }

    private void getOxygenListData() {
        OxygenRowsDTOS.clear();
        bloodOxygenHistoryAdapter = new BloodOxygenHistoryAdapter(OxygenRowsDTOS);
        bloodOxygenHistoryAdapter.setShowType("xueyang");
        viewBinding.allHistory.setOnClickListener(v -> go(BloodHistory.class,"xueyang",API.oxygenList));
        viewBinding.listHistory.setAdapter(bloodOxygenHistoryAdapter);
        OkHttpUtil.getInstance().doGet(API.oxygenList+"?ownerId="+userBean.getOwnerId(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    OxygenListBean oxygenListBean = new Gson().fromJson(response.body().string(), OxygenListBean.class);

                    if (oxygenListBean.getCode() != 200) {
                        activity.runOnUiThread(() -> showToast("请求失败"));
                        return;
                    }
                    OxygenRowsDTOS.addAll(oxygenListBean.getRows());
                    runOnUiThread(() -> {
                        OxygenRowsDTOS.forEach(item -> {

                            xLabel.add(item.getHBloodOxygenTime());
                            yLabel.add(Float.valueOf(item.getHRespiratoryRate()));

                            viewBinding.progress.setCurrentStep(Integer.parseInt(item.getHRespiratoryRate()), "SaO2");

                            if (Integer.parseInt(item.getHRespiratoryRate()) > 95) {
                                viewBinding.statusText.setText("上次测量血氧浓度：正常血氧");
                            }

                            if (Integer.parseInt(item.getHRespiratoryRate()) < 95) {
                                viewBinding.statusText.setText("上次测量血氧浓度：血氧过低");
                            }



                        });





                        yLabels.add(yLabel);
                        chartView.setLineChart(viewBinding.line, xLabel, yLabels);

                        bloodOxygenHistoryAdapter.notifyDataSetChanged();
                    });
                }


            }
        });
    }

    private void getSugarListData() {
        BloodSugarRowsDTOS.clear();
        bloodOxygenHistoryAdapter = new BloodOxygenHistoryAdapter(BloodSugarRowsDTOS);
        bloodOxygenHistoryAdapter.setShowType("xuetang");
        viewBinding.allHistory.setOnClickListener(v -> go(BloodHistory.class,"xuetang",API.sugarList));
        viewBinding.listHistory.setAdapter(bloodOxygenHistoryAdapter);

        OkHttpUtil.getInstance().doGet(API.sugarList+"?ownerId="+userBean.getOwnerId(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    BloodSugarListBean oxygenListBean = new Gson().fromJson(response.body().string(), BloodSugarListBean.class);

                    if (oxygenListBean.getCode() != 200) {
                        activity.runOnUiThread(() -> showToast("请求失败"));
                        return;
                    }
                    BloodSugarRowsDTOS.addAll(oxygenListBean.getRows());
                    runOnUiThread(() -> {
                        BloodSugarRowsDTOS.forEach(item -> {

                            xLabel.add(item.getHBloodSugarTime());
                            yLabel.add(Float.valueOf(item.getHBloodSugarValue()));
                            viewBinding.progress.setCurrentStep(Float.parseFloat(item.getHBloodSugarValue()), "mmol/L");

                        });


                        yLabels.add(yLabel);
                        chartView.setLineChart(viewBinding.line, xLabel, yLabels);

                        bloodOxygenHistoryAdapter.notifyDataSetChanged();
                    });
                }


            }
        });
    }

    private void getCholesterolListData() {
        CholesterolRowsDTOS.clear();
        bloodOxygenHistoryAdapter = new BloodOxygenHistoryAdapter(CholesterolRowsDTOS);
        bloodOxygenHistoryAdapter.setShowType("cholesterol");
        viewBinding.allHistory.setOnClickListener(v -> go(BloodHistory.class,"cholesterol",API.cholesterolList));
        viewBinding.listHistory.setAdapter(bloodOxygenHistoryAdapter);
        OkHttpUtil.getInstance().doGet(API.cholesterolList+"?ownerId="+userBean.getOwnerId(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    CholesterolListBean cholesterolListBean = new Gson().fromJson(response.body().string(), CholesterolListBean.class);
                    if (cholesterolListBean.getCode() != 200) {
                        activity.runOnUiThread(() -> showToast("请求失败"));
                        return;
                    }

                    CholesterolRowsDTOS.addAll(cholesterolListBean.getRows());
                    runOnUiThread(() -> {
                        CholesterolRowsDTOS.forEach(item -> {

                            xLabel.add(item.getHCholesterolTime());
                            yLabel.add(Float.valueOf(item.getHCholesterolContent()));
                            viewBinding.progress.setCurrentStep(Float.parseFloat(item.getHCholesterolContent()), "mmol/L");

                        });


                        yLabels.add(yLabel);
                        chartView.setLineChart(viewBinding.line, xLabel, yLabels);

                        bloodOxygenHistoryAdapter.notifyDataSetChanged();
                    });
                }


            }
        });
    }

    private void getAcidListData() {
        AcidRowsDTOS.clear();
        bloodOxygenHistoryAdapter = new BloodOxygenHistoryAdapter(AcidRowsDTOS);
        bloodOxygenHistoryAdapter.setShowType("acid");
        viewBinding.allHistory.setOnClickListener(v -> go(BloodHistory.class,"acid",API.acidList));
        viewBinding.listHistory.setAdapter(bloodOxygenHistoryAdapter);
        OkHttpUtil.getInstance().doGet(API.acidList+"?ownerId="+userBean.getOwnerId(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    AcidListBean acidListBean = new Gson().fromJson(response.body().string(), AcidListBean.class);
                    if (acidListBean.getCode() != 200) {
                        activity.runOnUiThread(() -> showToast("请求失败"));
                        return;
                    }
                    AcidRowsDTOS.addAll(acidListBean.getRows());
                    runOnUiThread(() -> {
                        AcidRowsDTOS.forEach(item -> {

                            xLabel.add(item.getHUricAcidTime());
                            yLabel.add(Float.valueOf(item.getHUricAcidValue()));
                            viewBinding.progress.setCurrentStep(Float.parseFloat(item.getHUricAcidValue()), "μmol/L");

                        });


                        yLabels.add(yLabel);
                        chartView.setLineChart(viewBinding.line, xLabel, yLabels);

                        bloodOxygenHistoryAdapter.notifyDataSetChanged();
                    });
                }


            }
        });
    }

    private void getTemperatureListData() {
        TemperatureRowsDTOS.clear();
        viewBinding.progress.setStepMax(50);
        bloodOxygenHistoryAdapter = new BloodOxygenHistoryAdapter(TemperatureRowsDTOS);
        bloodOxygenHistoryAdapter.setShowType("temperature");
        viewBinding.allHistory.setOnClickListener(v -> go(BloodHistory.class,"temperature",API.temperatureList));

        viewBinding.listHistory.setAdapter(bloodOxygenHistoryAdapter);
        OkHttpUtil.getInstance().doGet(API.temperatureList+"?ownerId="+userBean.getOwnerId(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    TemperatureListBean cholesterolListBean = new Gson().fromJson(response.body().string(), TemperatureListBean.class);
                    if (cholesterolListBean.getCode() != 200) {
                        activity.runOnUiThread(() -> showToast("请求失败"));
                        return;
                    }
                    TemperatureRowsDTOS.addAll(cholesterolListBean.getRows());
                    runOnUiThread(() -> {
                        TemperatureRowsDTOS.forEach(item -> {

                            xLabel.add(item.getHTemperatureRateTime());
                            yLabel.add(Float.valueOf(item.getHTemperatureValue()));
                            viewBinding.progress.setCurrentStep(Float.parseFloat(item.getHTemperatureValue()), "°C");

                        });


                        yLabels.add(yLabel);
                        chartView.setLineChart(viewBinding.line, xLabel, yLabels);

                        bloodOxygenHistoryAdapter.notifyDataSetChanged();
                    });
                }


            }
        });
    }

    private void getRateListData(){
        RateRowsDTOS.clear();

        bloodOxygenHistoryAdapter = new BloodOxygenHistoryAdapter(RateRowsDTOS);
        bloodOxygenHistoryAdapter.setShowType("rate");
        viewBinding.allHistory.setOnClickListener(v -> go(BloodHistory.class,"rate",API.rateList));

        viewBinding.listHistory.setAdapter(bloodOxygenHistoryAdapter);
        OkHttpUtil.getInstance().doGet(API.rateList+"?ownerId="+userBean.getOwnerId(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    RateListBean cholesterolListBean = new Gson().fromJson(response.body().string(), RateListBean.class);
                    if (cholesterolListBean.getCode() != 200) {
                        activity.runOnUiThread(() -> showToast("请求失败"));
                        return;
                    }
                    RateRowsDTOS.addAll(cholesterolListBean.getRows());
                    runOnUiThread(() -> {
                        RateRowsDTOS.forEach(item -> {

                            xLabel.add(item.getHHeartRateTime());
                            yLabel.add(Float.valueOf(item.getHHeartRateValue()));
                            viewBinding.progress.setCurrentStep(Float.parseFloat(item.getHHeartRateValue()), "次");

                        });

                        yLabels.add(yLabel);
                        chartView.setLineChart(viewBinding.line, xLabel, yLabels);

                        bloodOxygenHistoryAdapter.notifyDataSetChanged();
                    });
                }


            }
        });
    }

    //仪器连接
    private void connectDevice() {

        BleScanRuleConfig scanRuleConfig = null;

        switch (type) {
            case "血氧":
                scanRuleConfig = new BleScanRuleConfig.Builder()
                        .setDeviceName(true, "AOJ-70")
                        .setAutoConnect(true)
                        .setScanTimeOut(10000)
                        .build();
                break;
            case "血糖":
            case "胆固醇":
            case "尿酸":
                scanRuleConfig = new BleScanRuleConfig.Builder()
                        .setDeviceName(true, "BeneCheck-")
                        .setAutoConnect(true)
                        .setScanTimeOut(5000)
                        .build();
                break;
            case "体温":
                scanRuleConfig = new BleScanRuleConfig.Builder()
                        .setDeviceName(true, "AOJ-2")
                        .setAutoConnect(true)
                        .setScanTimeOut(10000)
                        .build();
                break;
        }

        if (scanRuleConfig == null) return;

        BleManager.getInstance().initScanRule(scanRuleConfig);


        BleManager.getInstance().scan(new BleScanCallback() {
            @Override
            public void onScanStarted(boolean success) {
                showToast("开始扫描");
                viewBinding.connect.setText(R.string.scanning);
            }

            @Override
            public void onLeScan(BleDevice bleDevice) {

            }

            @Override
            public void onScanning(BleDevice bleDevice) {
                System.out.println("onScanning name: " + bleDevice.getName());
            }

            @Override
            public void onScanFinished(List<BleDevice> scanResultList) {
                System.out.println("onScanFinished name ");
                System.out.println("onScanFinished name: " + scanResultList + " size is " + scanResultList.size());
                if (scanResultList.size() != 0) {

                    if (!dialogBoole) {

                        if (scanResultList.size() == 1) {
                            dialogBoole = true;
                            bleConnect(scanResultList.get(0));
                            return;
                        }

                        View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_select, null);
                        RecyclerView recycler = dialogView.findViewById(R.id.recycler);
                        ImageView cancel = dialogView.findViewById(R.id.cancel);

                        AlertDialog dialog = new AlertDialog.Builder(activity, R.style.Dialog_style)
                                .setView(dialogView)
                                .create();

                        List<BluetoothBean> list = new ArrayList<>();
                        scanResultList.forEach(bleDevice -> {
                            list.add(new BluetoothBean(bleDevice.getName(), bleDevice.getRssi()));
                        });

                        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
                        recycler.setLayoutManager(layoutManager);
                        DialogSelectAdapter dialogSelectAdapter = new DialogSelectAdapter(R.layout.item_dialog_select, list);
                        recycler.setAdapter(dialogSelectAdapter);
                        dialogSelectAdapter.addChildClickViewIds(R.id.text);
                        dialogSelectAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                            dialog.dismiss();
                            dialogBoole = true;
                            bleConnect(scanResultList.get(position));
                        });
                        cancel.setOnClickListener(view -> dialog.dismiss());
                        dialog.show();

                    }

                }

            }

        });

    }


    private void bleConnect(BleDevice mBleDevice) {

        BleManager.getInstance().connect(mBleDevice, new BleGattCallback() {
            @Override
            public void onStartConnect() {
                System.out.println(getString(R.string.start_connect));
                viewBinding.connect.setText(R.string.connecting);

            }

            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {
                System.out.println("连接失败" + exception);
                viewBinding.connect.setText(R.string.connect);

                BleManager.getInstance().cancelScan();
                bleConnect(mBleDevice);
            }

            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                // 连接成功，BleDevice即为所连接的BLE设备
                System.out.println("devices is : " + bleDevice.getName() + " , mac is : " + bleDevice.getMac()
                        + " , scan data is : " + bleDevice.getScanRecord() + " , rssi is : " + bleDevice.getRssi());
                device_name = bleDevice.getName();
                if (bleDevice.getName().contains("BP")) {
                    SpUtils.putString(activity, "BP_lanya_devices", bleDevice.getName());
                } else if (bleDevice.getName().contains("AOJ-2")) {
                    SpUtils.putString(activity, "AOJ_2_lanya_devices", bleDevice.getName());
                } else if (bleDevice.getName().contains("AOJ-70")) {
                    SpUtils.putString(activity, "AOJ_70_lanya_devices", bleDevice.getName());
                } else if (bleDevice.getName().contains("BeneCheck-")) {
                    SpUtils.putString(activity, "BeneChecklanya_devices", bleDevice.getName());
                }

                if (bleDevice != null) {
                    BleManager.getInstance().cancelScan();
                }
                System.out.println("connect status : " + status);

                gatt = BleManager.getInstance().getBluetoothGatt(bleDevice);
                System.out.println("now connect status : " + status + " , gatt get services is : " + gatt.getServices());
                List<BluetoothGattService> serviceList = gatt.getServices();
                for (BluetoothGattService service : serviceList) {
                    UUID uuid_service = service.getUuid();

                    List<BluetoothGattCharacteristic> characteristicList = service.getCharacteristics();
                    for (BluetoothGattCharacteristic characteristic : characteristicList) {
                        UUID uuid_chara = characteristic.getUuid();

                        System.out.println("gatt infos is : " + uuid_service + " , " + uuid_chara);
                    }
                }

                String uuid_service = "0000FFE0-0000-1000-8000-00805F9B34FB";
                String uuid_characteristic_write = "0000FFF2-0000-1000-8000-00805F9B34FB";
                String uuid_characteristic_notify = "0000FFE1-0000-1000-8000-00805F9B34FB";

                if (type.equals("血压")) {
                    uuid_service = "0000FFF0-0000-1000-8000-00805F9B34FB";
                    uuid_characteristic_notify = "0000FFF1-0000-1000-8000-00805F9B34FB";
                } else if (type.equals("体温")) {
                    uuid_service = "0000FFE0-0000-1000-8000-00805F9B34FB";
                    uuid_characteristic_notify = "0000FFE1-0000-1000-8000-00805F9B34FB";
                } else if (type.equals("血氧")) {
                    uuid_service = "0000FFE0-0000-1000-8000-00805F9B34FB";
                    uuid_characteristic_notify = "0000FFE1-0000-1000-8000-00805F9B34FB";
                } else if (type.equals("血糖")) {
                    uuid_service = "00001000-0000-1000-8000-00805F9B34FB";
                    uuid_characteristic_notify = "00001002-0000-1000-8000-00805F9B34FB";
                } else if (type.equals("胆固醇")) {
                    uuid_service = "00001000-0000-1000-8000-00805F9B34FB";
                    uuid_characteristic_notify = "00001002-0000-1000-8000-00805F9B34FB";
                } else if (type.equals("尿酸")) {
                    uuid_service = "00001000-0000-1000-8000-00805F9B34FB";
                    uuid_characteristic_notify = "00001002-0000-1000-8000-00805F9B34FB";
                } else if (type.equals("血脂")) {
                    uuid_service = "0000FFF0-0000-1000-8000-00805F9B34FB";
                    uuid_characteristic_notify = "0000FFF6-0000-1000-8000-00805F9B34FB";

                }

                String begin_value = "CC80020301020002";
                byte[] data = hexStringToBytes(begin_value);
                System.out.println("data is : " + data.toString());
                BleManager.getInstance().notify(
                        bleDevice,
                        uuid_service,
                        uuid_characteristic_notify,
                        new BleNotifyCallback() {
                            @Override
                            public void onNotifySuccess() {
                                // 打开通知操作成功
                                System.out.println("onNotifySuccess");
                                Toast.makeText(activity, "连接成功！", Toast.LENGTH_SHORT).show();
                                viewBinding.connect.setText(R.string.connect_success);
                            }


                            @Override
                            public void onNotifyFailure(BleException exception) {
                                // 打开通知操作失败
                                System.out.println("onNotifyFailure");
                            }

                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onCharacteristicChanged(byte[] data) {
                                String value = formatHexString(data, true);
                                if (type.equals("血氧")||type.equals("脉搏")) {
                                    List<String> list1 = Arrays.asList(value.split(" "));
                                    String value_xueyang = list1.get(1);
                                    String value_xinlv = list1.get(2);
                                    Integer value_real_xueyang = Integer.parseInt(value_xueyang, 16);
                                    Integer value_real_xinlv = Integer.parseInt(value_xinlv, 16);


                                    if (start&&type.equals("血氧")) {
                                        if (value_real_xueyang < 70) return;
                                        if (OxygenRowsDTOS.size() == 0) {
                                            OxygenListBean.RowsDTO rowsDTO = new OxygenListBean.RowsDTO();
                                            rowsDTO.setHRespiratoryRate(String.valueOf(value_real_xueyang));
                                            rowsDTO.setHBloodOxygenTime(StringUtils.forDataTimeTime2(new Date()));

                                            OxygenRowsDTOS.add(rowsDTO);
                                            xLabel.add(StringUtils.forDataTimeTime(new Date()));
                                            yLabel.add(Float.valueOf(value_real_xueyang));
                                            viewBinding.progress.setCurrentStep(value_real_xueyang, "SaO2");

                                            if (value_real_xueyang < 95) {
                                                viewBinding.statusText.setText("您的血氧已超标！风险较大！请立即就医！");
                                            } else {
                                                viewBinding.statusText.setText("您的血氧非常完美，太棒了！请继续保持");
                                            }

                                            bloodOxygenHistoryAdapter.notifyDataSetChanged();

                                            bloodOxygenDataPush(String.valueOf(value_real_xueyang), viewBinding.statusText.getText().toString());

                                        } else {
                                            OxygenListBean.RowsDTO testBean = OxygenRowsDTOS.get(OxygenRowsDTOS.size() - 1);
                                            if (!testBean.getHRespiratoryRate().equals(String.valueOf(value_real_xueyang))) {
                                                OxygenListBean.RowsDTO rowsDTO1 = new OxygenListBean.RowsDTO();
                                                rowsDTO1.setHRespiratoryRate(String.valueOf(value_real_xueyang));
                                                rowsDTO1.setHBloodOxygenTime(StringUtils.forDataTimeTime2(new Date()));
                                                OxygenRowsDTOS.add(rowsDTO1);
                                                xLabel.add(StringUtils.forDataTimeTime(new Date()));
                                                yLabel.add(Float.valueOf(value_real_xueyang));

                                                viewBinding.progress.setCurrentStep(value_real_xueyang, "SaO2");


                                                if (value_real_xueyang < 95) {
                                                    viewBinding.statusText.setText("您的血氧已超标！风险较大！请立即就医！");
                                                } else {
                                                    viewBinding.statusText.setText("您的血氧非常完美，太棒了！请继续保持");
                                                }

                                                if (OxygenRowsDTOS.size() > 8) {
                                                    OxygenRowsDTOS.remove(0);
                                                    xLabel.remove(0);
                                                    yLabel.remove(0);
                                                }

                                                yLabels.clear();

                                                yLabels.add(yLabel);
                                                chartView.setLineChart(viewBinding.line, xLabel, yLabels);

                                                bloodOxygenHistoryAdapter.notifyDataSetChanged();

                                                bloodOxygenDataPush(String.valueOf(value_real_xueyang), viewBinding.statusText.getText().toString());
                                            }
                                        }

                                    }

                                    if (start&&type.equals("脉搏")) {
                                        if (RateRowsDTOS.size() == 0) {
                                            RateListBean.RowsDTO rowsDTO = new RateListBean.RowsDTO();
                                            rowsDTO.setHHeartRateValue(String.valueOf(value_real_xinlv));
                                            rowsDTO.setHHeartRateTime(StringUtils.forDataTimeTime2(new Date()));

                                            RateRowsDTOS.add(rowsDTO);
                                            xLabel.add(StringUtils.forDataTimeTime(new Date()));
                                            yLabel.add(Float.valueOf(value_real_xinlv));
                                            viewBinding.progress.setCurrentStep(value_real_xinlv, "SaO2");

                                            if (value_real_xinlv>120) {
                                                viewBinding.statusText.setText("您的心率过高！");
                                            } else {
                                                viewBinding.statusText.setText("您的心率正常");
                                            }

                                            bloodOxygenHistoryAdapter.notifyDataSetChanged();

                                            rateDataPush(String.valueOf(value_real_xinlv), viewBinding.statusText.getText().toString());

                                        } else {
                                            RateListBean.RowsDTO testBean = RateRowsDTOS.get(RateRowsDTOS.size() - 1);
                                            if (!testBean.getHHeartRateValue().equals(String.valueOf(value_real_xinlv))) {
                                                RateListBean.RowsDTO rowsDTO1 = new RateListBean.RowsDTO();
                                                rowsDTO1.setHHeartRateValue(String.valueOf(value_real_xinlv));
                                                rowsDTO1.setHHeartRateTime(StringUtils.forDataTimeTime2(new Date()));
                                                RateRowsDTOS.add(rowsDTO1);
                                                xLabel.add(StringUtils.forDataTimeTime(new Date()));
                                                yLabel.add(Float.valueOf(value_real_xinlv));

                                                viewBinding.progress.setCurrentStep(value_real_xinlv, "SaO2");

                                                if (value_real_xinlv>120) {
                                                    viewBinding.statusText.setText("您的心率过高！");
                                                } else {
                                                    viewBinding.statusText.setText("您的心率正常");
                                                }

                                                if (RateRowsDTOS.size() > 8) {
                                                    RateRowsDTOS.remove(0);
                                                    xLabel.remove(0);
                                                    yLabel.remove(0);
                                                }

                                                yLabels.clear();

                                                yLabels.add(yLabel);
                                                chartView.setLineChart(viewBinding.line, xLabel, yLabels);

                                                bloodOxygenHistoryAdapter.notifyDataSetChanged();

                                                rateDataPush(String.valueOf(value_real_xinlv), viewBinding.statusText.getText().toString());
                                            }
                                        }
                                    }


                                }

                                if (type.equals("血糖")) {
                                    List<String> list1 = Arrays.asList(value.split(" "));
                                    String value_str = list1.get(18) + list1.get(17);
                                    double xuetang_vlaue_1 = Integer.parseInt(value_str, 16);
                                    String lastXueTang = String.format("%.2f", xuetang_vlaue_1 / 18f);
                                    if (start) {
                                        if (xuetang_vlaue_1 < 2.8) {
                                            viewBinding.statusText.setText("您的血糖有极为严重问题,请立即就医或拨打120电话");

                                        } else if (xuetang_vlaue_1 < 6.1) {
                                            viewBinding.statusText.setText("您的血糖非常完美，太棒了！请继续保持！");
                                        } else if (xuetang_vlaue_1 < 7) {
                                            viewBinding.statusText.setText("您的血糖有潜在风险，需要多加关注！");
                                        } else if (xuetang_vlaue_1 < 8.5) {
                                            viewBinding.statusText.setText("您的血糖有一定问题，需要多加关注！");
                                        } else if (xuetang_vlaue_1 < 11.1) {
                                            viewBinding.statusText.setText("您的血糖有严重问题，请立即就医或拨打120电话！");
                                        } else {
                                            viewBinding.statusText.setText("您的血糖有严重问题，请立即就医或拨打120电话！");
                                        }


                                        if (BloodSugarRowsDTOS.size() == 0) {
                                            BloodSugarListBean.RowsDTO rowsDTO = new BloodSugarListBean.RowsDTO();
                                            rowsDTO.setHBloodSugarValue(lastXueTang);
                                            rowsDTO.setHBloodSugarTime(StringUtils.forDataTimeTime2(new Date()));

                                            BloodSugarRowsDTOS.add(rowsDTO);
                                            xLabel.add(StringUtils.forDataTimeTime(new Date()));
                                            yLabel.add(Float.valueOf(lastXueTang));
                                            viewBinding.progress.setCurrentStep(Float.parseFloat(lastXueTang), "mmol/L");

                                            bloodOxygenHistoryAdapter.notifyDataSetChanged();

                                            bloodSugarDataPush(lastXueTang, viewBinding.statusText.getText().toString());

                                        } else {
                                            BloodSugarListBean.RowsDTO testBean = BloodSugarRowsDTOS.get(BloodSugarRowsDTOS.size() - 1);
                                            if (!testBean.getHBloodSugarValue().equals(lastXueTang)) {
                                                BloodSugarListBean.RowsDTO rowsDTO1 = new BloodSugarListBean.RowsDTO();
                                                rowsDTO1.setHBloodSugarValue(lastXueTang);
                                                rowsDTO1.setHBloodSugarTime(StringUtils.forDataTimeTime2(new Date()));
                                                BloodSugarRowsDTOS.add(rowsDTO1);
                                                xLabel.add(StringUtils.forDataTimeTime(new Date()));
                                                yLabel.add(Float.valueOf(lastXueTang));

                                                viewBinding.progress.setCurrentStep(Float.parseFloat(lastXueTang), "mmol/L");


                                                if (BloodSugarRowsDTOS.size() > 8) {
                                                    BloodSugarRowsDTOS.remove(0);
                                                    xLabel.remove(0);
                                                    yLabel.remove(0);
                                                }

                                                yLabels.clear();

                                                yLabels.add(yLabel);
                                                chartView.setLineChart(viewBinding.line, xLabel, yLabels);

                                                bloodOxygenHistoryAdapter.notifyDataSetChanged();

                                                bloodSugarDataPush(lastXueTang, viewBinding.statusText.getText().toString());
                                            }
                                        }

                                    }
                                }

                                if (type.equals("胆固醇")) {
                                    List<String> list1 = Arrays.asList(value.split(" "));
                                    String value_str = list1.get(18) + list1.get(17);
                                    Integer value_real = Integer.parseInt(value_str, 16);
                                    double danguchun = value_real / 38.66;
                                    String lastDanGuChun = String.format("%.2f", danguchun);

                                    if (start) {
                                        if (danguchun < 5.17) {
                                            viewBinding.statusText.setText("您的胆固醇非常完美，太棒了！请继续保持！");
                                        } else {
                                            viewBinding.statusText.setText("您的胆固醇已超标，建议立即就医！");
                                        }


                                        if (CholesterolRowsDTOS.size() == 0) {
                                            CholesterolListBean.RowsDTO rowsDTO = new CholesterolListBean.RowsDTO();
                                            rowsDTO.setHCholesterolContent(lastDanGuChun);
                                            rowsDTO.setHCholesterolTime(StringUtils.forDataTimeTime2(new Date()));

                                            CholesterolRowsDTOS.add(rowsDTO);
                                            xLabel.add(StringUtils.forDataTimeTime(new Date()));
                                            yLabel.add(Float.valueOf(lastDanGuChun));
                                            viewBinding.progress.setCurrentStep(Float.parseFloat(lastDanGuChun), "mmol/L");

                                            bloodOxygenHistoryAdapter.notifyDataSetChanged();

                                            cholesterolDataPush(lastDanGuChun, viewBinding.statusText.getText().toString());

                                        } else {
                                            CholesterolListBean.RowsDTO testBean = CholesterolRowsDTOS.get(CholesterolRowsDTOS.size() - 1);
                                            if (!testBean.getHCholesterolContent().equals(lastDanGuChun)) {
                                                CholesterolListBean.RowsDTO rowsDTO1 = new CholesterolListBean.RowsDTO();
                                                rowsDTO1.setHCholesterolContent(lastDanGuChun);
                                                rowsDTO1.setHCholesterolTime(StringUtils.forDataTimeTime2(new Date()));
                                                CholesterolRowsDTOS.add(rowsDTO1);
                                                xLabel.add(StringUtils.forDataTimeTime(new Date()));
                                                yLabel.add(Float.valueOf(lastDanGuChun));

                                                viewBinding.progress.setCurrentStep(Float.parseFloat(lastDanGuChun), "mmol/L");


                                                if (CholesterolRowsDTOS.size() > 8) {
                                                    CholesterolRowsDTOS.remove(0);
                                                    xLabel.remove(0);
                                                    yLabel.remove(0);
                                                }

                                                yLabels.clear();

                                                yLabels.add(yLabel);
                                                chartView.setLineChart(viewBinding.line, xLabel, yLabels);

                                                bloodOxygenHistoryAdapter.notifyDataSetChanged();

                                                cholesterolDataPush(lastDanGuChun, viewBinding.statusText.getText().toString());
                                            }
                                        }
                                    }
                                }

                                if (type.equals("尿酸")) {
                                    List<String> list1 = Arrays.asList(value.split(" "));
                                    String value_str = list1.get(18) + list1.get(17);
                                    double niaosuan_vlaue_1 = Integer.parseInt(value_str, 16);

                                    String lastNiaoSuan = String.format("%.2f", niaosuan_vlaue_1 * 0.1 / 16.81f * 1000);


                                    if (start) {
                                        if (niaosuan_vlaue_1 < 416) {
                                            viewBinding.statusText.setText("您的尿酸非常完美，太棒了");
                                        } else {
                                            viewBinding.statusText.setText("您的尿酸已超标，建议立即就医");
                                        }

                                        if (AcidRowsDTOS.size() == 0) {
                                            AcidListBean.RowsDTO rowsDTO = new AcidListBean.RowsDTO();
                                            rowsDTO.setHUricAcidValue(lastNiaoSuan);
                                            rowsDTO.setHUricAcidTime(StringUtils.forDataTimeTime2(new Date()));

                                            AcidRowsDTOS.add(rowsDTO);
                                            xLabel.add(StringUtils.forDataTimeTime(new Date()));
                                            yLabel.add(Float.valueOf(lastNiaoSuan));
                                            viewBinding.progress.setCurrentStep(Float.parseFloat(lastNiaoSuan), "μmol/L");

                                            bloodOxygenHistoryAdapter.notifyDataSetChanged();

                                            acidDataPush(lastNiaoSuan, viewBinding.statusText.getText().toString());

                                        } else {
                                            AcidListBean.RowsDTO testBean = AcidRowsDTOS.get(AcidRowsDTOS.size() - 1);
                                            if (!testBean.getHUricAcidValue().equals(lastNiaoSuan)) {
                                                AcidListBean.RowsDTO rowsDTO1 = new AcidListBean.RowsDTO();
                                                rowsDTO1.setHUricAcidValue(lastNiaoSuan);
                                                rowsDTO1.setHUricAcidTime(StringUtils.forDataTimeTime2(new Date()));
                                                AcidRowsDTOS.add(rowsDTO1);
                                                xLabel.add(StringUtils.forDataTimeTime(new Date()));
                                                yLabel.add(Float.valueOf(lastNiaoSuan));

                                                viewBinding.progress.setCurrentStep(Float.parseFloat(lastNiaoSuan), "μmol/L");


                                                if (AcidRowsDTOS.size() > 8) {
                                                    AcidRowsDTOS.remove(0);
                                                    xLabel.remove(0);
                                                    yLabel.remove(0);
                                                }

                                                yLabels.clear();

                                                yLabels.add(yLabel);
                                                chartView.setLineChart(viewBinding.line, xLabel, yLabels);

                                                bloodOxygenHistoryAdapter.notifyDataSetChanged();

                                                acidDataPush(lastNiaoSuan, viewBinding.statusText.getText().toString());
                                            }
                                        }
                                    }

                                }

                                if (type.equals("体温")) {
                                    List<String> list1 = Arrays.asList(value.split(" "));
                                    String value_wendu = list1.get(4) + list1.get(5);

                                    Integer s1 = Integer.parseInt(value_wendu, 16);
                                    String value_real_wendu = s1.toString().substring(0, 2) + "." + s1.toString().substring(2);


                                    float wendu = Float.parseFloat(value_real_wendu);

                                    if (start) {
                                        if (wendu < 37.6) {
                                            viewBinding.statusText.setText("您的体温正常！");
                                        } else {
                                            viewBinding.statusText.setText("您的体温过高，建议就医！");
                                        }


                                        if (TemperatureRowsDTOS.size() == 0) {
                                            TemperatureListBean.RowsDTO rowsDTO = new TemperatureListBean.RowsDTO();
                                            rowsDTO.setHTemperatureValue(value_real_wendu);
                                            rowsDTO.setHTemperatureRateTime(StringUtils.forDataTimeTime2(new Date()));

                                            TemperatureRowsDTOS.add(rowsDTO);
                                            xLabel.add(StringUtils.forDataTimeTime(new Date()));
                                            yLabel.add(Float.valueOf(value_real_wendu));
                                            viewBinding.progress.setCurrentStep(Float.parseFloat(value_real_wendu), "°C");

                                            bloodOxygenHistoryAdapter.notifyDataSetChanged();

                                            temperatureDataPush(value_real_wendu, viewBinding.statusText.getText().toString());

                                        } else {
                                            TemperatureListBean.RowsDTO testBean = TemperatureRowsDTOS.get(TemperatureRowsDTOS.size() - 1);
                                            if (!testBean.getHTemperatureValue().equals(value_real_wendu)) {
                                                TemperatureListBean.RowsDTO rowsDTO1 = new TemperatureListBean.RowsDTO();
                                                rowsDTO1.setHTemperatureValue(value_real_wendu);
                                                rowsDTO1.setHTemperatureRateTime(StringUtils.forDataTimeTime2(new Date()));
                                                TemperatureRowsDTOS.add(rowsDTO1);
                                                xLabel.add(StringUtils.forDataTimeTime(new Date()));
                                                yLabel.add(Float.valueOf(value_real_wendu));

                                                viewBinding.progress.setCurrentStep(Float.parseFloat(value_real_wendu), "°C");


                                                if (TemperatureRowsDTOS.size() > 8) {
                                                    TemperatureRowsDTOS.remove(0);
                                                    xLabel.remove(0);
                                                    yLabel.remove(0);
                                                }

                                                yLabels.clear();

                                                yLabels.add(yLabel);
                                                chartView.setLineChart(viewBinding.line, xLabel, yLabels);

                                                bloodOxygenHistoryAdapter.notifyDataSetChanged();

                                                temperatureDataPush(value_real_wendu, viewBinding.statusText.getText().toString());
                                            }
                                        }
                                    }

                                }

                            }


                        });

            }


            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
                System.out.println("连接中断");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BleManager.getInstance().disconnectAllDevice();
        BleManager.getInstance().destroy();
    }

    //血氧数据上传
    private void bloodOxygenDataPush(String value, String tips) {

        UserBean userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);

        if (userBean == null) {
            showToast("请先登录");
            return;
        }
        PostOxygenBean postOxygenBean = new PostOxygenBean();
        postOxygenBean.setOwnerId(userBean.getUserId());
        postOxygenBean.setOwnerName(userBean.getUserName());
        postOxygenBean.setOwnerAge(userBean.getOwnerAge());
        postOxygenBean.setOwnerSex(userBean.getOwnerSex());
        postOxygenBean.setOwnerBedNum(userBean.getOwnerBedNum());
        postOxygenBean.setHBloodOxygenResultAnalysis(tips);
        postOxygenBean.setHRespiratoryRate(value);
        postOxygenBean.setHBloodOxygenTime(StringUtils.forDataTimeY_M_D(new Date()));

        OkHttpUtil.getInstance().doPost(API.oxygen, new Gson().toJson(postOxygenBean), new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                String backData = response.body() != null ? response.body().string() : "null";

                Log.d("TAG", "onResponse: " + backData);

            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                Log.d("TAG", "onFailure: " + e);

            }
        });
    }

    // 血糖数据上传
    private void bloodSugarDataPush(String value, String tips) {

        UserBean userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);
        if (userBean == null) {
            showToast("请先登录");
            return;
        }

        BloodSugarListBean.RowsDTO rowsDTO = new BloodSugarListBean.RowsDTO();
        rowsDTO.setOwnerId(userBean.getUserId());
        rowsDTO.setOwnerName(userBean.getUserName());
        rowsDTO.setOwnerAge(userBean.getOwnerAge());
        rowsDTO.setOwnerSex(userBean.getOwnerSex());
        rowsDTO.setOwnerRoomNum(userBean.getOwnerRoomNum());
        rowsDTO.setHBloodSugarValue(value);
        rowsDTO.setHBloodSugarResultAnalysis(tips);
        rowsDTO.setHBloodSugarTime(StringUtils.forDataTimeY_M_D(new Date()));

        OkHttpUtil.getInstance().doPost(API.sugar, new Gson().toJson(rowsDTO), new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                String backData = response.body() != null ? response.body().string() : "null";

                Log.d("TAG", "onResponse: " + backData);

            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                Log.d("TAG", "onFailure: " + e);

            }
        });
    }

    //胆固醇数据上传
    private void cholesterolDataPush(String value, String tips) {

        UserBean userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);
        if (userBean == null) {
            showToast("请先登录");
            return;
        }

        CholesterolListBean.RowsDTO rowsDTO = new CholesterolListBean.RowsDTO();
        rowsDTO.setOwnerId(userBean.getUserId());
        rowsDTO.setOwnerName(userBean.getUserName());
        rowsDTO.setOwnerAge(userBean.getOwnerAge());
        rowsDTO.setOwnerSex(userBean.getOwnerSex());
        rowsDTO.setOwnerBedNum(userBean.getOwnerBedNum());
        rowsDTO.setHCholesterolContent(value);
        rowsDTO.setHCholesterolResultAnalysis(tips);


        OkHttpUtil.getInstance().doPost(API.cholesterol, new Gson().toJson(rowsDTO), new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                String backData = response.body() != null ? response.body().string() : "null";

                Log.d("TAG", "onResponse: " + backData);

            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                Log.d("TAG", "onFailure: " + e);

            }
        });
    }

    //尿酸数据上传
    private void acidDataPush(String value, String tips) {

        UserBean userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);
        if (userBean == null) {
            showToast("请先登录");
            return;
        }

        AcidListBean.RowsDTO rowsDTO = new AcidListBean.RowsDTO();
        rowsDTO.setOwnerId(userBean.getUserId());
        rowsDTO.setOwnerName(userBean.getUserName());
        rowsDTO.setOwnerAge(userBean.getOwnerAge());
        rowsDTO.setOwnerSex(userBean.getOwnerSex());
        rowsDTO.setOwnerBedNum(String.valueOf(userBean.getOwnerBedNum()));
        rowsDTO.setHUricAcidValue(value);
        rowsDTO.setHUricAcidResultAnalysis(tips);


        OkHttpUtil.getInstance().doPost(API.acid, new Gson().toJson(rowsDTO), new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                String backData = response.body() != null ? response.body().string() : "null";

                Log.d("TAG", "onResponse: " + backData);

            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                Log.d("TAG", "onFailure: " + e);

            }
        });
    }

    //体温数据上传
    private void temperatureDataPush(String value, String tips) {

        UserBean userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);
        if (userBean == null) {
            showToast("请先登录");
            return;
        }

        TemperatureListBean.RowsDTO rowsDTO = new TemperatureListBean.RowsDTO();
        rowsDTO.setOwnerId(userBean.getUserId());
        rowsDTO.setOwnerName(userBean.getUserName());
        rowsDTO.setOwnerAge(userBean.getOwnerAge());
        rowsDTO.setOwnerSex(userBean.getOwnerSex());
        rowsDTO.setOwnerBedNum(userBean.getOwnerBedNum());
        rowsDTO.setHTemperatureValue(value);
        rowsDTO.setHTemperatureResultAnalysis(tips);


        OkHttpUtil.getInstance().doPost(API.temperature, new Gson().toJson(rowsDTO), new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                String backData = response.body() != null ? response.body().string() : "null";

                Log.d("TAG", "onResponse: " + backData);

            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                Log.d("TAG", "onFailure: " + e);

            }
        });
    }

    private void rateDataPush(String value, String tips) {
        UserBean userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);
        if (userBean == null) {
            showToast("请先登录");
            return;
        }

        RateListBean.RowsDTO rowsDTO = new RateListBean.RowsDTO();
        rowsDTO.setOwnerId(userBean.getUserId());
        rowsDTO.setOwnerName(userBean.getUserName());
        rowsDTO.setOwnerAge(userBean.getOwnerAge());
        rowsDTO.setOwnerSex(userBean.getOwnerSex());
        rowsDTO.setOwnerBedNum(userBean.getOwnerBedNum());
        rowsDTO.setHHeartRateValue(value);
        rowsDTO.setHHeartRateResultAnalysis(tips);


        OkHttpUtil.getInstance().doPost(API.rate, new Gson().toJson(rowsDTO), new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                String backData = response.body() != null ? response.body().string() : "null";

                Log.d("TAG", "onResponse: " + backData);

            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                Log.d("TAG", "onFailure: " + e);

            }
        });
    }

}