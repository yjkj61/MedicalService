package com.example.medicalservice.activity.healthcareChildActivity;

import static com.clj.fastble.utils.HexUtil.formatHexString;
import static com.clj.fastble.utils.HexUtil.hexStringToBytes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.BleScanRuleConfig;
import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.activity.LoginView;
import com.example.medicalservice.activity.healthcareChildActivity.bloodDataChildView.BloodHistory;
import com.example.medicalservice.activity.healthcareChildActivity.bloodDataChildView.HealthAdvice;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.BloodFatBean;
import com.example.medicalservice.bean.BluetoothBean;
import com.example.medicalservice.bean.LastPhysicalExamination;
import com.example.medicalservice.bean.TestBean;
import com.example.medicalservice.dataBaseBean.UserBean;
import com.example.medicalservice.databinding.ActivityBloodFatBinding;
import com.example.medicalservice.diyView.ChartView;
import com.example.medicalservice.diyView.QQStepView;
import com.example.medicalservice.fragments.healthAiBegFragments.MonthHealthFragment;
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

public class BloodFat extends BaseActivity<ActivityBloodFatBinding> {

    private boolean dialogBoole = false;

    private MsAdapter msAdapter;

    private boolean start = false;

    private List<BloodFatBean.RowsDTO> bloodFatBeans = new ArrayList<>();

    private ArrayList<String> xLabel = new ArrayList<>();

    private List<List<Float>> yLabels = new ArrayList<>();

    private List<Float> yLabel = new ArrayList<>();
    private List<Float> yLabel2 = new ArrayList<>();
    private List<Float> yLabel3 = new ArrayList<>();

    private List<Float> yLabel4 = new ArrayList<>();

    private List<MonthHealthFragment.GridBean> gridBeans;

    private ChartView chartView;

    private BloodOxygenHistoryAdapter bloodOxygenHistoryAdapter;


    private UserBean userBean = null;

    private String type = "totalCholesterol";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        super.initView();
        userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);


        select(viewBinding.totalCholesterol);
        unSelect(viewBinding.glycerol);
        unSelect(viewBinding.lowDensityLipoprotein);
        unSelect(viewBinding.highDensityLipoprotein);


        viewBinding.back.setOnClickListener(v -> finish());
        viewBinding.allHistory.setOnClickListener(v -> go(BloodHistory.class));
        viewBinding.advice.setOnClickListener(v -> go(HealthAdvice.class));

        viewBinding.start.setOnClickListener(v -> {
            start = !start;
            viewBinding.start.setText(start ? "正在测量" : "开始测量");

        });


        gridBeans = new ArrayList<>();


        chartView = new ChartView();


        bloodOxygenHistoryAdapter = new BloodOxygenHistoryAdapter(bloodFatBeans);
        bloodOxygenHistoryAdapter.setShowType("totalCholesterol");

        viewBinding.allHistory.setOnClickListener(v -> go(BloodHistory.class, type, API.chohdlldlList));


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        viewBinding.listData.setLayoutManager(linearLayoutManager);

        viewBinding.listData.setAdapter(bloodOxygenHistoryAdapter);


        viewBinding.totalCholesterol.setOnClickListener(v -> {
            select(viewBinding.totalCholesterol);
            unSelect(viewBinding.glycerol);
            unSelect(viewBinding.lowDensityLipoprotein);
            unSelect(viewBinding.highDensityLipoprotein);
            type = "totalCholesterol";
            bloodOxygenHistoryAdapter.setShowType("totalCholesterol");
            bloodOxygenHistoryAdapter.notifyDataSetChanged();

        });
        viewBinding.glycerol.setOnClickListener(v -> {
            unSelect(viewBinding.totalCholesterol);
            select(viewBinding.glycerol);
            unSelect(viewBinding.lowDensityLipoprotein);
            unSelect(viewBinding.highDensityLipoprotein);
            bloodOxygenHistoryAdapter.setShowType("glycerol");
            type = "glycerol";
            bloodOxygenHistoryAdapter.notifyDataSetChanged();

        });

        viewBinding.lowDensityLipoprotein.setOnClickListener(v -> {
            unSelect(viewBinding.totalCholesterol);
            unSelect(viewBinding.glycerol);
            select(viewBinding.lowDensityLipoprotein);
            unSelect(viewBinding.highDensityLipoprotein);
            type = "lowDensityLipoprotein";
            bloodOxygenHistoryAdapter.setShowType("lowDensityLipoprotein");
            bloodOxygenHistoryAdapter.notifyDataSetChanged();


        });


        viewBinding.highDensityLipoprotein.setOnClickListener(v -> {
            unSelect(viewBinding.totalCholesterol);
            unSelect(viewBinding.glycerol);
            unSelect(viewBinding.lowDensityLipoprotein);
            select(viewBinding.highDensityLipoprotein);
            type = "highDensityLipoprotein";
            bloodOxygenHistoryAdapter.setShowType("highDensityLipoprotein");
            bloodOxygenHistoryAdapter.notifyDataSetChanged();


        });

        getLastPhysicalExamination();
        connectDevice();
    }

    private void getLastPhysicalExamination() {

        if (userBean == null) {
            go(LoginView.class);
            finish();
            showToast("请登录");
            return;
        }
        OkHttpUtil.getInstance().doGet(API.lastPhysicalExamination + userBean.getOwnerId(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {


                    LastPhysicalExamination lastPhysicalExamination = new Gson().fromJson(response.body().string(), LastPhysicalExamination.class);

                    if (lastPhysicalExamination.getData().getHTcValue() == null || lastPhysicalExamination.getData().getHTcValue().isEmpty()) {
                        lastPhysicalExamination.getData().setHTcValue("0");
                    }
                    if (lastPhysicalExamination.getData().getHTriglycerideValue() == null || lastPhysicalExamination.getData().getHTriglycerideValue().isEmpty()) {
                        lastPhysicalExamination.getData().setHTriglycerideValue("0");
                    }
                    if (lastPhysicalExamination.getData().getHHdlValue() == null || lastPhysicalExamination.getData().getHHdlValue().isEmpty()) {
                        lastPhysicalExamination.getData().setHHdlValue("0");
                    }
                    if (lastPhysicalExamination.getData().getHLdlValue() == null || lastPhysicalExamination.getData().getHLdlValue().isEmpty()) {
                        lastPhysicalExamination.getData().setHLdlValue("0");
                    }


                    float zongdan_value = Float.parseFloat(lastPhysicalExamination.getData().getHTcValue());
                    viewBinding.suggest1.setText(zongdan_value > 5.17 ? "总胆固醇超标" : "总胆固醇完美");

                    float ganyou_value = Float.parseFloat(lastPhysicalExamination.getData().getHTriglycerideValue());
                    viewBinding.suggest2.setText(ganyou_value < 1.7 ? "甘油三酯完美" : "甘油三酯超标");


                    float gaomi_value = Float.parseFloat(lastPhysicalExamination.getData().getHHdlValue());
                    if (gaomi_value < 0.96) {
                        viewBinding.suggest3.setText("高密度脂蛋白超标");
                    } else if (gaomi_value < 1.56) {
                        viewBinding.suggest3.setText("高密度脂蛋白正常");
                    } else {
                        viewBinding.suggest3.setText("高密度脂蛋白完美");

                    }


                    float dimi_value = Float.parseFloat(lastPhysicalExamination.getData().getHLdlValue());
                    if (dimi_value < 2.1) {
                        viewBinding.suggest4.setText("低密度脂蛋白完美");
                    } else if (dimi_value < 3.1) {
                        viewBinding.suggest4.setText("低密度脂蛋白正常");
                    } else {
                        viewBinding.suggest4.setText("低密度脂蛋白超标");

                    }


                    gridBeans.add(new MonthHealthFragment.GridBean(Float.parseFloat(lastPhysicalExamination.getData().getHTcValue()), "day", "总胆固醇\nmmol/L", 10));
                    gridBeans.add(new MonthHealthFragment.GridBean(Float.parseFloat(lastPhysicalExamination.getData().getHTriglycerideValue()), " 分", "甘油三酯\nmmol/L", 10));
                    gridBeans.add(new MonthHealthFragment.GridBean(Float.parseFloat(lastPhysicalExamination.getData().getHHdlValue()), "day", "高密度脂蛋白\nmmol/L", 10));
                    gridBeans.add(new MonthHealthFragment.GridBean(Float.parseFloat(lastPhysicalExamination.getData().getHLdlValue()), "day", "低密度脂蛋白\nmmol/L", 10));
                    if (lastPhysicalExamination.getCode() == 200) {
                        runOnUiThread(() -> {

                            msAdapter = new MsAdapter<MonthHealthFragment.GridBean>(gridBeans, R.layout.circle_progress_item) {

                                @Override
                                public void bindView(ViewHolder holder, MonthHealthFragment.GridBean obj) {
                                    QQStepView qqStepView = holder.getView(R.id.step_view);
                                    TextView type = holder.getView(R.id.type);

                                    TextView bottom_text = holder.getView(R.id.bottom_text);

                                    qqStepView.setStepMax(obj.getMax());
                                    qqStepView.setCurrentStep(obj.getToData(), "");
                                    type.setText(obj.getType());
                                    type.setTranslationY(-40);

                                    type.setTextSize(8);
                                    bottom_text.setText(obj.getBottomText());

                                    bottom_text.setVisibility(View.GONE);
                                }
                            };
                            viewBinding.circleGrid.setAdapter(msAdapter);


                        });
                    }

                }
            }
        });


        OkHttpUtil.getInstance().doGet(API.chohdlldlList + "?ownerId=" + userBean.getOwnerId(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {


                if (response.body() != null) {
                    BloodFatBean bloodFatBean = new Gson().fromJson(response.body().string(), BloodFatBean.class);
                    if (bloodFatBean.getCode() == 200) {

                        runOnUiThread(() -> {
                            bloodFatBeans.addAll(bloodFatBean.getRows());
                            bloodOxygenHistoryAdapter.notifyDataSetChanged();

                            for (BloodFatBean.RowsDTO rowsDTO : bloodFatBean.getRows()
                            ) {
                                yLabel.add(Float.valueOf(rowsDTO.getHTcValue()));
                                yLabel2.add(Float.valueOf(rowsDTO.getHTriglycerideValue()));
                                yLabel3.add(Float.valueOf(rowsDTO.getHHdlValue()));
                                yLabel4.add(Float.valueOf(rowsDTO.getHLdlValue()));
                            }
                            yLabels.add(yLabel);
                            yLabels.add(yLabel2);
                            yLabels.add(yLabel3);
                            yLabels.add(yLabel4);

                            chartView.setLineChart(viewBinding.lineChart, xLabel, yLabels);
                        });
                    }

                }
            }
        });
    }

    @Override
    public void initData() {
        super.initData();


    }


    private void connectDevice() {
        BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
                .setDeviceName(true, "PW-01")
                .setAutoConnect(true)
                .setScanTimeOut(10000)
                .build();

        BleManager.getInstance().initScanRule(scanRuleConfig);


        BleManager.getInstance().scan(new BleScanCallback() {
            @Override
            public void onScanStarted(boolean success) {
                System.out.println("开始扫描");
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

//                        if (scanResultList.size() == 1) {
//                            dialogBoole = false;
//                            bleConnect(scanResultList.get(0));
//                            return;
//                        }

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
                String uuid_characteristic_notify = "0000FFF6-0000-1000-8000-00805F9B34FB";


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
                                viewBinding.connect.setText("连接成功");
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
                                    String value_str = formatHexString(data, true);
                                    System.out.println("value is : " + value_str);
                                    List<String> list1 = Arrays.asList(value_str.split(" "));
                                    if (value_str.length() > 25) {

                                        xLabel.add(StringUtils.forDataTimeTime(new Date()));

                                        BloodFatBean.RowsDTO rowsDTO = new BloodFatBean.RowsDTO();

                                        String value_zongdan = list1.get(5) + list1.get(6);
                                        Integer zongdan = Integer.parseInt(value_zongdan, 16);
                                        float zongdan_value = zongdan.floatValue() / 100;
                                        System.out.println("总胆TC:" + zongdan_value);

                                        if (zongdan_value > 5.17) {
                                            rowsDTO.setHTcResultAnalysis("您的总胆固醇已超标");
                                        } else {
                                            rowsDTO.setHTcResultAnalysis("您的总胆固醇非常完美");
                                        }

                                        //gridBeans.add(new MonthHealthFragment.GridBean((int) zongdan_value, "day", "总胆固醇\nmmol/L", 10));

                                        rowsDTO.setHTcValue(String.valueOf(zongdan_value));
                                        yLabel.add(zongdan_value);


                                        String value_ganyou = list1.get(7) + list1.get(8);
                                        Integer ganyou = Integer.parseInt(value_ganyou, 16);
                                        float ganyou_value = ganyou.floatValue() / 100;
                                        System.out.println("甘油TG:" + ganyou_value);
                                        rowsDTO.setHTriglycerideValue(String.valueOf(ganyou_value));
                                        if (ganyou_value < 1.7) {
                                            rowsDTO.setHTriglycerideResultAnalysis("您的甘油三酯非常完美");
                                        } else {
                                            rowsDTO.setHTriglycerideResultAnalysis("您的甘油三酯已超标");
                                        }
                                        // gridBeans.add(new MonthHealthFragment.GridBean((int) ganyou_value, " 分", "甘油三酯\nmmol/L", 10));

                                        yLabel2.add(ganyou_value);


                                        String value_gaomi = list1.get(9) + list1.get(10);
                                        Integer gaomi = Integer.parseInt(value_gaomi, 16);
                                        float gaomi_value = gaomi.floatValue() / 100;
                                        System.out.println("高密HDL:" + gaomi_value);

                                        rowsDTO.setHHdlValue(String.valueOf(gaomi_value));

                                        if (userBean.getOwnerSex().equals("1")) {
                                            if (gaomi_value < 0.96) {
                                                rowsDTO.setHHdlResultAnalysis("您的高密度脂蛋白已超标");
                                            } else if (gaomi_value < 1.16) {
                                                rowsDTO.setHHdlResultAnalysis("您的高密度脂蛋白正常");
                                            } else {
                                                rowsDTO.setHHdlResultAnalysis("您的高密度脂蛋白非常完美");
                                            }
                                        } else {
                                            if (gaomi_value < 0.96) {
                                                rowsDTO.setHHdlResultAnalysis("您的高密度脂蛋白已超标");
                                            } else if (gaomi_value < 1.56) {
                                                rowsDTO.setHHdlResultAnalysis("您的高密度脂蛋白正常");
                                            } else {
                                                rowsDTO.setHHdlResultAnalysis("您的高密度脂蛋白非常完美");
                                            }
                                        }
                                        //gridBeans.add(new MonthHealthFragment.GridBean((int) gaomi_value, "day", "高密度脂蛋白\nmmol/L", 10));

                                        yLabel3.add(gaomi_value);


                                        String value_dimi = list1.get(11) + list1.get(12);
                                        Integer dimi = Integer.parseInt(value_dimi, 16);
                                        float dimi_value = dimi.floatValue() / 100;
                                        System.out.println("低密LDL:" + dimi_value);

                                        rowsDTO.setHLdlValue(String.valueOf(dimi_value));
                                        // gridBeans.add(new MonthHealthFragment.GridBean((int) dimi_value, "day", "低密度脂蛋白\nmmol/L", 10));

                                        bloodFatBeans.add(rowsDTO);
                                        if (dimi_value < 2.1) {
                                            rowsDTO.setHLdlResultAnalysis("您的低密度脂蛋白非常完美");
                                        } else if (dimi_value < 3.1) {
                                            rowsDTO.setHLdlResultAnalysis("您的低密度脂蛋白正常");
                                        } else {
                                            rowsDTO.setHLdlResultAnalysis("您的低密度脂蛋白已超标");
                                        }

                                        yLabel4.add(dimi_value);


                                        if (yLabel.size() > 8) {
                                            xLabel.remove(0);
                                            yLabel.remove(0);
                                            yLabel2.remove(0);
                                            yLabel3.remove(0);
                                            yLabel4.remove(0);
                                        }

                                        chartView.setLineChart(viewBinding.lineChart, xLabel, yLabels);

                                        rowsDTO.setHTgChohdlldlTime(StringUtils.forDataTimeTime(new Date()));

                                        uploadTestData(rowsDTO);


                                        //msAdapter.notifyDataSetChanged();
                                        bloodOxygenHistoryAdapter.notifyDataSetChanged();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }


                        });

            }


            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice
                    bleDevice, BluetoothGatt gatt, int status) {
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

    private void uploadTestData(BloodFatBean.RowsDTO rowsDTO) {

        rowsDTO.setUserId(userBean.getUserId());
        rowsDTO.setOwnerAge(userBean.getOwnerAge());
        rowsDTO.setOwnerId(Integer.parseInt(userBean.getOwnerId()));
        rowsDTO.setOwnerSex(userBean.getOwnerSex());
        rowsDTO.setOwnerBedNum(userBean.getOwnerBedNum());


        OkHttpUtil.getInstance().doPost(API.chohdlldl, new Gson().toJson(rowsDTO), new Callback() {
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

    private void select(TextView cardView) {
        cardView.setBackgroundColor(Color.parseColor("#DED6FC"));
    }

    private void unSelect(TextView cardView) {
        cardView.setBackgroundColor(Color.parseColor("#F6F2FC"));
    }
}