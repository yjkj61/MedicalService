package com.example.medicalservice.activity.healthcareChildActivity;

import static com.clj.fastble.utils.HexUtil.formatHexString;
import static com.clj.fastble.utils.HexUtil.hexStringToBytes;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.BleScanRuleConfig;
import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.activity.LoginView;
import com.example.medicalservice.activity.healthcareChildActivity.bloodDataChildView.BloodHistory;
import com.example.medicalservice.activity.healthcareChildActivity.bloodDataChildView.HealthAdvice;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.BloodPressureListBean;
import com.example.medicalservice.bean.BluetoothBean;
import com.example.medicalservice.dataBaseBean.UserBean;
import com.example.medicalservice.databinding.ActivityBloodOxygenBinding;
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

//血压
public class BloodPressure extends BaseActivity<ActivityBloodOxygenBinding> {

    private boolean dialogBoole = false;
    private boolean start = false;

    private final List<BloodPressureListBean.RowsDTO> testBeans = new ArrayList<>();

    private final ArrayList<String> xLabel = new ArrayList<>();

    private final List<List<Float>> yLabels = new ArrayList<>();

    private final List<Float> yLabel = new ArrayList<>();
    private final List<Float> yLabel2 = new ArrayList<>();

    private ChartView chartView;

    private BloodOxygenHistoryAdapter bloodOxygenHistoryAdapter;

    private String device_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        super.initView();
        viewBinding.back.setOnClickListener(v -> finish());
        viewBinding.allHistory.setOnClickListener(v -> go(BloodHistory.class));
        viewBinding.advice.setOnClickListener(v -> go(HealthAdvice.class));

        viewBinding.connect.setOnClickListener(v -> {
            if (viewBinding.connect.getText().equals("连接设备")) {
                connectDevice();
            }
        });


    }


    @Override
    public void initData() {
        super.initData();
        chartView = new ChartView();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        viewBinding.listHistory.setLayoutManager(linearLayoutManager);


        viewBinding.progress1.setStepMax(100);
        viewBinding.progress2.setStepMax(100);


        bloodOxygenHistoryAdapter = new BloodOxygenHistoryAdapter(testBeans);
        bloodOxygenHistoryAdapter.setShowType("xueya");
        viewBinding.listHistory.setAdapter(bloodOxygenHistoryAdapter);

        viewBinding.allHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go(BloodHistory.class,"xueya",API.pressureList);
            }
        });

        getPressureList();
        connectDevice();
    }

    private void getPressureList() {
        testBeans.clear();

        UserBean userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);

        if(userBean == null){
            go(LoginView.class);
            showToast("请先登录");
            finish();
            return;
        }

        OkHttpUtil.getInstance().doGet(API.pressureList+"?ownerId="+userBean.getOwnerId(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() != null) {
                    BloodPressureListBean bean = new Gson().fromJson(response.body().string(), BloodPressureListBean.class);
                    testBeans.addAll(bean.getRows());
                    runOnUiThread(() -> {

                        testBeans.forEach(item -> {
                            yLabel.add(Float.valueOf(item.getHHypertension()));
                            yLabel2.add(Float.valueOf(item.getHHypotension()));

                            viewBinding.progress1.setCurrentStep(Float.parseFloat(item.getHHypertension()), "");
                            viewBinding.progress2.setCurrentStep(Float.parseFloat(item.getHHypotension()), "");
                        });
                        yLabels.add(yLabel);
                        yLabels.add(yLabel2);


                        chartView.setLineChart(viewBinding.line, xLabel, yLabels);
                        bloodOxygenHistoryAdapter.notifyDataSetChanged();
                    });


                }
            }
        });
    }

    private void connectDevice() {
        BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
                .setDeviceName(true, "BP")
                .setAutoConnect(true)
                .setScanTimeOut(10000)
                .build();

        BleManager.getInstance().initScanRule(scanRuleConfig);


        BleManager.getInstance().scan(new BleScanCallback() {
            @Override
            public void onScanStarted(boolean success) {
//                showToast("开始测量" + device_name);
                System.out.println("开始测量" + device_name);
                viewBinding.connect.setText("正在扫描");
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
                System.out.println("开始连接");
                viewBinding.connect.setText("正在连接");

            }

            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {
                System.out.println("连接失败" + exception);
                viewBinding.connect.setText("连接设备");

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

                String uuid_service = "0000FFF0-0000-1000-8000-00805F9B34FB";
                String uuid_characteristic_write = "0000FFF2-0000-1000-8000-00805F9B34FB";
                String uuid_characteristic_notify = "0000FFF1-0000-1000-8000-00805F9B34FB";
                String begin_value = "CC80020301020002";
                byte[] data = hexStringToBytes(begin_value);

                BleManager.getInstance().notify(
                        bleDevice,
                        uuid_service,
                        uuid_characteristic_notify,
                        new BleNotifyCallback() {
                            @Override
                            public void onNotifySuccess() {
                                // 打开通知操作成功
                                System.out.println("onNotifySuccess");
                                //firstDialog.dismiss();
                                Toast.makeText(activity, "连接成功！", Toast.LENGTH_SHORT).show();
                                viewBinding.connect.setText("连接成功");

                                viewBinding.start.setOnClickListener(view -> {
                                    start = !start;
                                    showToast("开始测量" + device_name);
                                    viewBinding.start.setText(start ? "正在测量" : "开始测量");
                                    BleManager.getInstance().write(
                                            bleDevice,
                                            uuid_service,
                                            uuid_characteristic_write,
                                            data,
                                            new BleWriteCallback() {
                                                @Override
                                                public void onWriteSuccess(int current, int total, byte[] justWrite) {
                                                    String value = formatHexString(justWrite, true);
                                                    System.out.println("value is : " + value);
                                                    System.out.println("onWriteSuccess");
                                                }

                                                @Override
                                                public void onWriteFailure(BleException exception) {
                                                    System.out.println("onWriteFailure");
                                                }
                                            });
                                });


                            }


                            @Override
                            public void onNotifyFailure(BleException exception) {
                                // 打开通知操作失败
                                System.out.println("onNotifyFailure");
                            }

                            @Override
                            public void onCharacteristicChanged(byte[] data) {
                                // 打开通知后，设备发过来的数据将在这里出现
                                try {

                                    System.out.println("onnotifyCharacteristicChanged");
                                    String value = formatHexString(data, true);
                                    System.out.println("value is : " + value);

                                    List<String> list1 = Arrays.asList(value.split(" "));
                                    if (list1.get(5).equals("06")) {
                                        String value_gaoya = list1.get(14);
                                        System.out.println(value_gaoya);
                                        Integer value_real_gaoya = Integer.parseInt(value_gaoya, 16);
                                        String value_diya = list1.get(16);
                                        System.out.println(value_diya);
                                        Integer value_real_diya = Integer.parseInt(value_diya, 16);
                                        String value_xinlv = list1.get(18);
                                        System.out.println(value_xinlv);
                                        Integer value_real_xinlv = Integer.parseInt(value_xinlv, 16);
                                        System.out.println("notify vlaue : " + "收缩压(高压):" + value_real_gaoya + " 舒张压(低压):" + value_real_diya + " 脉搏(心率):" + value_real_xinlv);

                                        if (start) {


                                            if (testBeans.size() == 0) {


                                                BloodPressureListBean.RowsDTO testBean = new BloodPressureListBean.RowsDTO();
                                                testBean.setHHypotension(String.valueOf(value_real_diya));
                                                testBean.setHHypertension(String.valueOf(value_real_gaoya));
                                                testBean.setHBloodPressureTime(StringUtils.forDataTimeTime2(new Date()));

                                                testBeans.add(testBean);
                                                xLabel.add(StringUtils.forDataTimeTime(new Date()));
                                                yLabel.add(Float.valueOf(value_real_gaoya));
                                                yLabel2.add(Float.valueOf(value_real_diya));
                                                viewBinding.progress1.setCurrentStep(value_real_diya, "舒张压");
                                                viewBinding.progress2.setCurrentStep(value_real_gaoya, "收缩压");

                                                if (value_real_gaoya < 90 || value_real_diya < 60) {
                                                    viewBinding.status.setText("血压：不太理想");
                                                } else if (value_real_gaoya < 120 || value_real_diya < 80) {
                                                    viewBinding.status.setText("血压：理想血压");
                                                } else if (value_real_gaoya < 130 || value_real_diya < 85) {
                                                    viewBinding.status.setText("血压：理想血压");
                                                } else if (value_real_gaoya < 140 || value_real_diya < 90) {
                                                    viewBinding.status.setText("血压：及格血压");
                                                } else if (value_real_gaoya < 160 || value_real_diya < 100) {
                                                    viewBinding.status.setText("血压：及格血压");
                                                } else if (value_real_gaoya < 180 || value_real_diya < 110) {
                                                    viewBinding.status.setText("血压：不太理想");
                                                } else {
                                                    viewBinding.status.setText("血压：不太理想");
                                                }

                                                yLabels.clear();

                                                yLabels.add(yLabel);
                                                yLabels.add(yLabel2);
                                                chartView.setLineChart(viewBinding.line, xLabel, yLabels);
                                                uploadTestData(String.valueOf(value_real_diya), String.valueOf(value_real_gaoya));

                                            } else {


                                                BloodPressureListBean.RowsDTO testBean = testBeans.get(testBeans.size() - 1);
                                                if (!testBean.getHHypotension().equals(String.valueOf(value_real_diya))) {
                                                    BloodPressureListBean.RowsDTO testBean1 = new BloodPressureListBean.RowsDTO();
                                                    testBean1.setHHypotension(String.valueOf(value_real_diya));
                                                    testBean1.setHHypertension(String.valueOf(value_real_gaoya));
                                                    testBean1.setHBloodPressureTime(StringUtils.forDataTimeTime2(new Date()));
                                                    testBeans.add(testBean1);

                                                    xLabel.add(StringUtils.forDataTimeTime(new Date()));
                                                    yLabel.add(Float.valueOf(value_real_gaoya));
                                                    yLabel2.add(Float.valueOf(value_real_diya));
                                                    viewBinding.progress1.setCurrentStep(value_real_diya, "舒张压");
                                                    viewBinding.progress2.setCurrentStep(value_real_gaoya, "收缩压");

                                                    if (value_real_gaoya < 90 || value_real_diya < 60) {
                                                        viewBinding.status.setText("血压：不太理想");
                                                    } else if (value_real_gaoya < 120 || value_real_diya < 80) {
                                                        viewBinding.status.setText("血压：理想血压");
                                                    } else if (value_real_gaoya < 130 || value_real_diya < 85) {
                                                        viewBinding.status.setText("血压：理想血压");
                                                    } else if (value_real_gaoya < 140 || value_real_diya < 90) {
                                                        viewBinding.status.setText("血压：及格血压");
                                                    } else if (value_real_gaoya < 160 || value_real_diya < 100) {
                                                        viewBinding.status.setText("血压：及格血压");
                                                    } else if (value_real_gaoya < 180 || value_real_diya < 110) {
                                                        viewBinding.status.setText("血压：不太理想");
                                                    } else {
                                                        viewBinding.status.setText("血压：不太理想");
                                                    }

                                                    if (testBeans.size() > 8) {
                                                        testBeans.remove(0);
                                                        xLabel.remove(0);
                                                        yLabel.remove(0);
                                                        yLabel2.remove(0);
                                                    }

                                                    yLabels.clear();

                                                    yLabels.add(yLabel);
                                                    yLabels.add(yLabel2);
                                                    chartView.setLineChart(viewBinding.line, xLabel, yLabels);
                                                    uploadTestData(String.valueOf(value_real_diya), String.valueOf(value_real_gaoya));

                                                }
                                            }

                                            bloodOxygenHistoryAdapter.notifyDataSetChanged();

                                        }


                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
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

    private void uploadTestData(String systolicPressure, String diastolicPressure) {
        UserBean userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);

        BloodPressureListBean.RowsDTO rowsDTO = new BloodPressureListBean.RowsDTO();
        rowsDTO.setOwnerId(userBean.getUserId());
        rowsDTO.setOwnerName(userBean.getUserName());
        rowsDTO.setOwnerAge(userBean.getOwnerAge());
        rowsDTO.setOwnerSex(userBean.getOwnerSex());
        rowsDTO.setOwnerBedNum(String.valueOf(userBean.getOwnerBedNum()));
        rowsDTO.setHHypotension(systolicPressure);
        rowsDTO.setHHypertension(diastolicPressure);
        rowsDTO.setHBloodPressureTime(StringUtils.forDataTimeY_M_D(new Date()));
        rowsDTO.setHHypertensionAnalysis(viewBinding.start.getText().toString());
        rowsDTO.setHHypotensionAnalysis(viewBinding.start.getText().toString());

        OkHttpUtil.getInstance().doPost(API.pressure, new Gson().toJson(rowsDTO), new Callback() {
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