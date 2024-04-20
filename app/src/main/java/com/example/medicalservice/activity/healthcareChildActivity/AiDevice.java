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
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.AIDeviceBean;
import com.example.medicalservice.bean.BluetoothBean;
import com.example.medicalservice.bean.DeviceBean;
import com.example.medicalservice.bean.TestBean;
import com.example.medicalservice.dataBaseBean.UserBean;
import com.example.medicalservice.databinding.ActivityAiDeviceBinding;
import com.example.medicalservice.recycleAdapter.AiBoxDeviceAdapter;
import com.example.medicalservice.recycleAdapter.DialogSelectAdapter;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.OkHttpUtil;
import com.example.medicalservice.tools.SpUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AiDevice extends BaseActivity<ActivityAiDeviceBinding> {

    private static final int REQUEST_ENABLE_BT = 100;


    String type = "xinlv";

    private String lastXueYang = "0";
    private String lastXueTang = "0";
    private String lastDanGuChun = "0";
    private String lastNiaoSuan = "0";

    private boolean dialogBoole = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        super.initView();

        viewBinding.back.setOnClickListener(view -> finish());

    }

    @Override
    public void initData() {
        super.initData();


        getDevice();
        List<DeviceBean> deviceBeans = new ArrayList<>();

        deviceBeans.add(new DeviceBean("脉搏波血压计", "脉搏波血压计", R.drawable.xueya, "xueya"));
        deviceBeans.add(new DeviceBean("血脂四项仪", "血脂四项仪", R.drawable.xuezhi, "xuezhi"));

        deviceBeans.add(new DeviceBean("血糖/尿酸/胆固醇分析仪", "血糖/尿酸/胆固醇分析仪", R.drawable.xuetang, "xuetang"));

        deviceBeans.add(new DeviceBean("红外体温计", "红外体温计", R.drawable.tiwen, "tiwen"));

        deviceBeans.add(new DeviceBean("血氧仪", "血氧仪", R.drawable.xueyang, "xueyang"));


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        viewBinding.boxDeviceList.setLayoutManager(linearLayoutManager);

        AiBoxDeviceAdapter aiBoxDeviceAdapter = new AiBoxDeviceAdapter(deviceBeans);


        viewBinding.boxDeviceList.setAdapter(aiBoxDeviceAdapter);

        aiBoxDeviceAdapter.setOnItemClickListener(position -> {
            viewBinding.bottomSelectBox.setVisibility(View.GONE);
            type = deviceBeans.get(position).getType();
            bleXueYaConnect();
        });

//        viewBinding.xinlv.setOnClickListener(v -> {
//            type = "xinlv";
//            viewBinding.bottomSelectBox.setVisibility(View.GONE);
//            bleXueYaConnect();
//        });
//
//        viewBinding.xueyang.setOnClickListener(v -> {
//            type = "xueyang";
//            viewBinding.bottomSelectBox.setVisibility(View.GONE);
//            bleXueYaConnect();
//        });
//
//        viewBinding.xuetang.setOnClickListener(v -> {
//            type = "xuetang";
//            viewBinding.bottomSelectBox.setVisibility(View.GONE);
//            bleXueYaConnect();
//        });
//
//        viewBinding.xueya.setOnClickListener(v -> {
//            type = "xueya";
//            viewBinding.bottomSelectBox.setVisibility(View.GONE);
//            bleXueYaConnect();
//
//        });
//
//        viewBinding.tiwen.setOnClickListener(v -> {
//            type = "tiwen";
//            viewBinding.bottomSelectBox.setVisibility(View.GONE);
//            bleXueYaConnect();
//
//        });
//
//        viewBinding.xuezhi.setOnClickListener(v -> {
//            type = "xuezhi";
//            viewBinding.bottomSelectBox.setVisibility(View.GONE);
//            bleXueYaConnect();
//        });


    }


    private void getDevice() {

        OkHttpUtil.getInstance().doGet(API.hardwareList, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    AIDeviceBean aiDeviceBean = new Gson().fromJson(response.body().string(), AIDeviceBean.class);


                    if(aiDeviceBean.getCode() != 200){
                        activity.runOnUiThread(() -> showToast("请求失败"));
                        return;
                    }

                    List<String> list = new ArrayList<>();

                    list.add("智能随诊箱");
                    list.add("智能睡眠垫");
                    list.add("智能呼叫板");

                    for (int i = 0; i < list.size(); i++) {
                        AIDeviceBean.RowsDTO rowsDTO = new AIDeviceBean.RowsDTO();
                        rowsDTO.setHUserId(0);
                        rowsDTO.setHHardwareName(list.get(i));
                        switch (i) {
                            case 0:
                                rowsDTO.setHHardwareDetail("您身边的健康助手");
                                break;
                            case 1:
                                rowsDTO.setHHardwareDetail("睡眠管家伴您好梦");
                                break;
                            case 2:
                                rowsDTO.setHHardwareDetail("多种需求一键呼叫");
                                break;
                        }
                        aiDeviceBean.getRows().add(rowsDTO);
                    }

                    runOnUiThread(() -> {
                        MsAdapter msAdapter = new MsAdapter<AIDeviceBean.RowsDTO>(aiDeviceBean.getRows(), R.layout.ai_device_grid_item) {

                            @Override
                            public void bindView(ViewHolder holder, AIDeviceBean.RowsDTO obj) {

                                LinearLayout layout = holder.getView(R.id.layout);
                                ImageView imageView = holder.getView(R.id.image);

                                TextView name = holder.getView(R.id.name);
                                TextView context = holder.getView(R.id.context);


                                TextView add = holder.getView(R.id.add);
                                TextView edit = holder.getView(R.id.edit);
                                TextView delete = holder.getView(R.id.delete);

                                if (obj.getHUserId() == 0) {
                                    add.setVisibility(View.VISIBLE);
                                    edit.setVisibility(View.GONE);
                                    delete.setVisibility(View.GONE);
                                } else {
                                    add.setVisibility(View.GONE);
                                    edit.setVisibility(View.VISIBLE);
                                    delete.setVisibility(View.VISIBLE);
                                }


                                add.setOnClickListener(v -> {

                                    UserBean userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);


                                    AIDeviceBean.RowsDTO rowsDTO = new AIDeviceBean.RowsDTO();

                                    if(userBean == null){
                                        showToast("请先登录");
                                        return;
                                    }

                                    rowsDTO.setHUserId(userBean.getUserId());
                                    rowsDTO.setHHardwareSn("213123132");
                                    rowsDTO.setHHardwarePic(obj.getHHardwareName());
                                    rowsDTO.setHHardwareName(obj.getHHardwareName());
                                    rowsDTO.setHHardwareDetail(obj.getHHardwareDetail());
                                    rowsDTO.setHHardwareType(String.valueOf(aiDeviceBean.getRows().indexOf(obj)));


                                    OkHttpUtil.getInstance().doPost(API.hardware, new Gson().toJson(rowsDTO), new Callback() {
                                        @Override
                                        public void onFailure(@NonNull Call call, @NonNull IOException e) {

                                        }

                                        @Override
                                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                                            runOnUiThread(AiDevice.this::getDevice);
                                        }
                                    });
                                });

                                delete.setOnClickListener(v -> {


                                    deleteDialog(obj.getHHardwareId());


                                });
                                name.setText(obj.getHHardwareName());
                                context.setText(obj.getHHardwareDetail());
                                switch (obj.getHHardwareName()) {
                                    case "智能随诊箱":
                                        imageView.setImageResource(R.drawable.ai_box);
                                        layout.setBackgroundResource(R.drawable.ai_device_item_back3);
                                        break;
                                    case "智能睡眠垫":
                                        imageView.setImageResource(R.drawable.ai_beg);
                                        layout.setBackgroundResource(R.drawable.ai_device_item_back2);
                                        break;
                                    case "智能呼叫板":
                                        imageView.setImageResource(R.drawable.ai_ban);
                                        layout.setBackgroundResource(R.drawable.ai_device_item_back);
                                        break;
                                }


//                                holder.getItemView().setOnClickListener(v -> {
//                                    switch (obj) {
//                                        case "智能随诊箱":
////                            dialogBoole = false;
////                            viewBinding.bottomSelectBox.setVisibility(View.VISIBLE);
//                                            break;
//                                        case "智能睡眠垫":
//
//                                            break;
//                                        case "智能呼叫板":
//
//                                            break;
//                                    }
//                                });

                            }
                        };

                        viewBinding.aiDevices.setAdapter(msAdapter);
                    });

                }

            }
        });
    }

    private void deleteDialog(int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Dialog_style);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_delete,null);

        builder.setView(view);
        TextView left_btn, right_btn, text;
        left_btn = view.findViewById(R.id.yes);
        right_btn = view.findViewById(R.id.no);
        text = view.findViewById(R.id.tip_text);


        text.setText("是否删除该设备");

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


        left_btn.setOnClickListener(v1 -> OkHttpUtil.getInstance().doDelete(API.hardwareList(id), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {

                runOnUiThread(AiDevice.this::getDevice);
            }
        }));

        right_btn.setOnClickListener(v1 -> alertDialog.dismiss());


    }

    private void bleXueYaConnect() {

        BleScanRuleConfig scanRuleConfig;

        if (type.equals("xueya") || type.equals("xinlv")) {
            scanRuleConfig = new BleScanRuleConfig.Builder()
                    .setDeviceName(true, "BP")
                    .setAutoConnect(true)
                    .setScanTimeOut(10000)
                    .build();
        } else if (type.equals("tiwen")) {
            scanRuleConfig = new BleScanRuleConfig.Builder()
                    .setDeviceName(true, "AOJ-2")
                    .setAutoConnect(true)
                    .setScanTimeOut(10000)
                    .build();
        } else if (type.equals("xueyang")) {
            scanRuleConfig = new BleScanRuleConfig.Builder()
                    .setDeviceName(true, "AOJ-70")
                    .setAutoConnect(true)
                    .setScanTimeOut(5000)
                    .build();
        } else if (type.equals("xuetang") || type.equals("danguchun") || type.equals("niaosuan")) {
            scanRuleConfig = new BleScanRuleConfig.Builder()
                    .setDeviceName(true, "BeneCheck-")
                    .setAutoConnect(true)
                    .setScanTimeOut(5000)
                    .build();
        } else if (type.equals("xuezhi")) {
            scanRuleConfig = new BleScanRuleConfig.Builder()
                    .setDeviceName(true, "PW-01")
                    .setAutoConnect(true)
                    .setScanTimeOut(5000)
                    .build();
        } else {
            scanRuleConfig = new BleScanRuleConfig.Builder()
                    .setAutoConnect(true)
                    .setScanTimeOut(5000)
                    .build();
        }
        BleManager.getInstance().initScanRule(scanRuleConfig);

        BleManager.getInstance().scan(new BleScanCallback() {
            @Override
            public void onScanStarted(boolean success) {
                System.out.println("开始扫描");
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
//                firstDialog.show();
//                content.setText("正在连接设备中，请稍后！");
//                setParam();
//                mTts.startSpeaking("正在连接设备中，请稍后！", mTtsListener);
            }

            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {
                System.out.println("连接失败" + exception);
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

                String uuid_service = "";
                String uuid_characteristic_write = "0000FFF2-0000-1000-8000-00805F9B34FB";
                String uuid_characteristic_notify = "";
                // || type.equals("xinlv")
                if (type.equals("xueya")) {
                    uuid_service = "0000FFF0-0000-1000-8000-00805F9B34FB";
                    uuid_characteristic_notify = "0000FFF1-0000-1000-8000-00805F9B34FB";
                } else if (type.equals("tiwen")) {
                    uuid_service = "0000FFE0-0000-1000-8000-00805F9B34FB";
                    uuid_characteristic_notify = "0000FFE1-0000-1000-8000-00805F9B34FB";
                } else if (type.equals("xueyang")) {
                    uuid_service = "0000FFE0-0000-1000-8000-00805F9B34FB";
                    uuid_characteristic_notify = "0000FFE1-0000-1000-8000-00805F9B34FB";
                } else if (type.equals("xuetang")) {
                    uuid_service = "00001000-0000-1000-8000-00805F9B34FB";
                    uuid_characteristic_notify = "00001002-0000-1000-8000-00805F9B34FB";
                } else if (type.equals("danguchun")) {
                    uuid_service = "00001000-0000-1000-8000-00805F9B34FB";
                    uuid_characteristic_notify = "00001002-0000-1000-8000-00805F9B34FB";
                } else if (type.equals("niaosuan")) {
                    uuid_service = "00001000-0000-1000-8000-00805F9B34FB";
                    uuid_characteristic_notify = "00001002-0000-1000-8000-00805F9B34FB";
                } else if (type.equals("xuezhi")) {
                    uuid_service = "0000FFF0-0000-1000-8000-00805F9B34FB";
                    uuid_characteristic_notify = "0000FFF6-0000-1000-8000-00805F9B34FB";

                }
                String begin_value = "CC80020301020002";
                byte[] data = hexStringToBytes(begin_value);
                System.out.println("data is : " + data.toString());

                String finalUuid_service = uuid_service;
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
                                //setParam();
                                if (type.equals("xueya") || type.equals("xinlv")) {

                                }

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
                                    if (type.equals("xueya")) {
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
                                            //addXueYa(value_real_gaoya + "/" + value_real_diya, value_real_xinlv + "");
//                                            if (shuju == 0) {
//                                                shuju = 1;
//                                                if (value_real_gaoya < 90 || value_real_diya < 60) {
//
//                                                    tong("测血压", "risk","不好","红色");
//                                                } else if (value_real_gaoya < 120 || value_real_diya < 80) {
//                                                    tong("测血压", "normal","好","绿色");
//                                                } else if (value_real_gaoya < 130 || value_real_diya < 85) {
//                                                    tong("测血压", "normal","好","绿色");
//                                                } else if (value_real_gaoya < 140 || value_real_diya < 90) {
//                                                    tong("测血压", "abnormal","还行","黄色");
//                                                } else if (value_real_gaoya < 160 || value_real_diya < 100) {
//                                                    tong("测血压", "abnormal","还行","黄色");
//
//                                                } else if (value_real_gaoya < 180 || value_real_diya < 110) {
//                                                    tong("测血压", "risk","不好","红色");
//                                                } else {
//                                                    tong("测血压", "risk","不好","红色");
//                                                }
//                                            }


                                        }
                                    } else if (type.equals("tiwen")) {
                                        List<String> list1 = Arrays.asList(value.split(" "));
                                        String value_wendu = list1.get(4) + list1.get(5);
                                        System.out.println(value_wendu);
                                        Integer s1 = Integer.parseInt(value_wendu, 16);
                                        String value_real_wendu = s1.toString().substring(0, 2) + "." + s1.toString().substring(2);
                                        System.out.println("notify vlaue : " + "温度:" + value_real_wendu);
                                        //addUserBodyTiWen(value_real_wendu);

                                        float d = Float.parseFloat(value_real_wendu);

                                        uploadTestData(String.valueOf(d));
//                                        Integer s1s = Integer.parseInt(value_real_wendu, 16);
//                                        if (d < 36) {
////                                            suggest.setText("您的体温已经超标，有极为严重问题，请立即就医或拨打120电话！（优先排除血压计是否损坏或是否正确安装）");
//                                            tong("测体温", "risk","不好","红色");
//                                        } else if (d < 37.3) {
////                                            suggest.setText("您的体温正常！不错哦！请继续保持！（优先排除设备是否损坏或是否正确操作）");
//                                            tong("测体温", "normal","好","绿色");
//                                        } else if (d < 38.6) {
////                                            suggest.setText("您的体温已经超标，请多加关注，建议立即就医！（优先排除血压计是否损坏或是否正确安装）");
//                                            tong("测体温", "abnormal","还行","黄色");
//                                        } else {
////                                            suggest.setText("您的体温已经超标，有极为严重问题，请立即就医或拨打120电话！（优先排除血压计是否损坏或是否正确安装）");
//                                            tong("测体温", "risk","不好","红色");
//                                        }

                                    } else if (type.equals("xueyang")) {

                                        List<String> list1 = Arrays.asList(value.split(" "));
                                        String value_xueyang = list1.get(1);
                                        System.out.println(value_xueyang);
                                        Integer value_real_xueyang = Integer.parseInt(value_xueyang, 16);
                                        String value_xinlv = list1.get(2);
                                        System.out.println(value_xinlv);
                                        Integer value_real_xinlv = Integer.parseInt(value_xinlv, 16);
                                        System.out.println("notify vlaue : " + "血氧:" + value_real_xueyang + " 脉搏(心率):" + value_real_xinlv);
                                        //lastXueYang = value_real_xinlv > 100 ? lastXueYang : value_real_xinlv + "";

//                                        if (shuju == 0) {
//                                            shuju = 1;
//                                            if (value_real_xueyang < 95) {
//                                                tong("测血氧", "risk","不好","红色");
////                                                addUserBodyXueYang(lastXueYang);
////                                            img5.setBackgroundResource(R.drawable.clwz);
////                                            suggest.setText("您的血氧已超标！风险较大！请立即就医！（优先排除设备是否损坏或是否正确操作）");
//                                            } else {
//                                                tong("测血氧", "normal","好","绿色");
////                                                addUserBodyXueYang(lastXueYang);
//
////                                            img4.setBackgroundResource(R.drawable.clwz);
////                                            suggest.setText("您的血氧非常完美，太棒了！请继续保持！（优先排除设备是否损坏或是否正确操作）");
//                                            }
//                                        }


                                    } else if (type.equals("xuetang")) {
                                        List<String> list1 = Arrays.asList(value.split(" "));
                                        String value_str = list1.get(18) + list1.get(17);
                                        System.out.println(value_str);
                                        double xuetang_vlaue_1 = Integer.parseInt(value_str, 16);
                                        System.out.println("xuetang_vlaue_1 is " + xuetang_vlaue_1);
                                        lastXueTang = String.format("%.2f", xuetang_vlaue_1 / 18f);


//                                        if (shuju == 0) {
//                                            shuju = 1;
//                                            if (xuetang_vlaue_1 < 2.8) {
//                                                tong("测血糖", "risk","不好","红色");
//                                                addUserBodyXueTang(lastXueTang);
////                                                suggest.setText("您的血糖有极为严重问题，请立即就医或拨打120电话！（优先排除设备是否损坏或是否正确操作）");
//                                            } else if (xuetang_vlaue_1 < 6.1) {
//                                                tong("测血糖", "normal","好","绿色");
//                                                addUserBodyXueTang(lastXueTang);
////                                                suggest.setText("您的血糖非常完美，太棒了！请继续保持！（优先排除设备是否损坏或是否正确操作）");
//                                            } else if (xuetang_vlaue_1 < 7) {
//                                                tong("测血糖", "abnormal","还行","黄色");
//                                                addUserBodyXueTang(lastXueTang);
////                                                suggest.setText("您的血糖有潜在风险，需要多加关注！优化饮食结构和生活习惯！（优先排除设备是否损坏或是否正确操作）");
//                                            } else if (xuetang_vlaue_1 < 8.5) {
//                                                tong("测血糖", "abnormal","还行","黄色");
//                                                addUserBodyXueTang(lastXueTang);
////                                                suggest.setText("您的血糖有一定问题，需要多加关注！建议就医并做进一步检查！（优先排除设备是否损坏或是否正确操作）");
//                                            } else if (xuetang_vlaue_1 < 11.1) {
//                                                tong("测血糖", "risk","不好","红色");
//                                                addUserBodyXueTang(lastXueTang);
////                                                suggest.setText("您的血糖有严重问题，请立即就医或拨打120电话！（优先排除血压计是否损坏或是否正确安装）");
//                                            } else {
//                                                tong("测血糖", "risk","不好","红色");
//                                                addUserBodyXueTang(lastXueTang);
////                                                suggest.setText("您的血糖有极为严重问题，请立即就医或拨打120电话！（优先排除血压计是否损坏或是否正确安装）");
//                                            }
//
//                                        }

                                    } else if (type.equals("danguchun")) {
                                        List<String> list1 = Arrays.asList(value.split(" "));
                                        String value_str = list1.get(18) + list1.get(17);
                                        System.out.println(value_str);
                                        Integer value_real = Integer.parseInt(value_str, 16);
                                        System.out.println("danguchun vlaue is : " + value_real / 38.66);
                                        double danguchun = value_real / 38.66;
                                        lastDanGuChun = String.format("%.2f", danguchun);
//                                        if (shuju == 0) {
//                                            shuju = 1;
//                                            if (danguchun < 5.17) {
//                                                tong("测胆固醇", "normal","好","绿色");
//                                                addUserBodyDanGuChun(lastDanGuChun);
////                                            img4.setBackgroundResource(R.drawable.clwz);
////                                            suggest.setText("您的胆固醇非常完美，太棒了！请继续保持！（优先排除设备是否损坏或是否正确操作）");
//                                            } else {
//                                                tong("测胆固醇", "risk","不好","红色");
//                                                addUserBodyDanGuChun(lastDanGuChun);
////                                            img5.setBackgroundResource(R.drawable.clwz);
////                                            suggest.setText("您的胆固醇已超标，建议立即就医，并做进一步检查！建议优化饮食结构和生活习惯！（优先排除设备是否损坏或是否正确操作）");
//                                            }
//                                        }

                                    } else if (type.equals("niaosuan")) {
                                        List<String> list1 = Arrays.asList(value.split(" "));
                                        String value_str = list1.get(18) + list1.get(17);
                                        System.out.println(value_str);
                                        double niaosuan_vlaue_1 = Integer.parseInt(value_str, 16);
                                        System.out.println("niaosuan_vlaue_1 is " + niaosuan_vlaue_1);
                                        lastNiaoSuan = String.format("%.2f", niaosuan_vlaue_1 * 0.1 / 16.81f * 1000);
//                                        if (shuju == 0) {
//                                            shuju = 1;
//                                            if (SharedPreferencesUtil.getString(BaseData.sex, getApplicationContext()).equals("男")) {
//                                                if (niaosuan_vlaue_1 < 416) {
//                                                    tong("测尿酸", "normal","好","绿色");
//                                                    addUserBodyNiaoSuan(lastNiaoSuan);
////                                                img4.setBackgroundResource(R.drawable.clwz);
////                                                suggest.setText("您的尿酸非常完美，太棒了！请继续保持！（优先排除设备是否损坏或是否正确操作）");
//                                                } else {
//                                                    tong("测尿酸", "risk","不好","红色");
//                                                    addUserBodyNiaoSuan(lastNiaoSuan);
////                                                img5.setBackgroundResource(R.drawable.clwz);
////                                                suggest.setText("您的尿酸已超标，建议立即就医，并做进一步检查！建议优化饮食结构和生活习惯！（优先排除设备是否损坏或是否正确操作）");
//                                                }
//                                            } else {
//                                                if (niaosuan_vlaue_1 < 357) {
//                                                    tong("测尿酸", "normal","好","绿色");
//                                                    addUserBodyNiaoSuan(lastNiaoSuan);
////                                                img4.setBackgroundResource(R.drawable.clwz);
////                                                suggest.setText("您的尿酸非常完美，太棒了！请继续保持！（优先排除设备是否损坏或是否正确操作）");
//                                                } else {
//                                                    tong("测尿酸", "risk","不好","红色");
//                                                    addUserBodyNiaoSuan(lastNiaoSuan);
//
////                                                img5.setBackgroundResource(R.drawable.clwz);
////                                                suggest.setText("您的尿酸已超标，建议立即就医，并做进一步检查！建议优化饮食结构和生活习惯！（优先排除设备是否损坏或是否正确操作）");
//                                                }
//                                            }
//                                        }

                                    } else if (type.equals("xuezhi")) {

                                        // String value = formatHexString(data, true);

                                        List<String> list1 = Arrays.asList(value.split(" "));
                                        if (value.length() > 25) {
                                            String value_zongdan = list1.get(5) + list1.get(6);
                                            Integer zongdan = Integer.parseInt(value_zongdan, 16);
                                            float zongdan_value = zongdan.floatValue() / 100;
                                            System.out.println("总胆TC:" + zongdan_value);
//                                                if (zongdan_value > 5.17) {
//                                                    tong("测血脂四项/总胆固醇", "risk","不好","红色");
////                                            suggest.setText(suggest.getText().toString() + "您的总胆固醇已超标，建议立即就医！\n");
//                                                } else {
//                                                    tong("测血脂四项/总胆固醇", "normal","好","绿色");
//
////                                            suggest.setText(suggest.getText().toString() + "您的总胆固醇非常完美，太棒了！请继续保持！\n");
//                                                }

                                            String value_ganyou = list1.get(7) + list1.get(8);
                                            Integer ganyou = Integer.parseInt(value_ganyou, 16);
                                            float ganyou_value = ganyou.floatValue() / 100;
                                            System.out.println("甘油TG:" + ganyou_value);
//                                                if (ganyou_value < 1.7) {
//                                                    tong("测血脂四项/甘油三酯", "normal","好","绿色");
////                                            suggest.setText(suggest.getText().toString() + "您的甘油三酯非常完美，太棒了！请继续保持！\n");
//                                                } else {
//                                                    tong("测血脂四项/甘油三酯", "risk","不好","红色");
////                                            suggest.setText(suggest.getText().toString() + "您的甘油三酯已超标，建议立即就医！\n");
//                                                }

                                            String value_gaomi = list1.get(9) + list1.get(10);
                                            Integer gaomi = Integer.parseInt(value_gaomi, 16);
                                            float gaomi_value = gaomi.floatValue() / 100;
                                            System.out.println("高密HDL:" + gaomi_value);
//                                                if (SharedPreferencesUtil.getString(BaseData.sex, getApplicationContext()).equals("男")) {
//                                                    if (gaomi_value < 0.96) {
//                                                        tong("测血脂四项/高密度脂蛋白", "risk","不好","红色");
////                                                suggest.setText(suggest.getText().toString() + "您的高密度脂蛋白已超标，建议立即就医！\n");
//                                                    } else if (gaomi_value < 1.16) {
//                                                        tong("测血脂四项/高密度脂蛋白", "abnormal","还行","黄色");
////                                                suggest.setText(suggest.getText().toString() + "您的高密度脂蛋白正常，不错哦！请继续保持！\n");
//                                                    } else {
//                                                        tong("测血脂四项/高密度脂蛋白", "normal","好","绿色");
////                                                suggest.setText(suggest.getText().toString() + "您的高密度脂蛋白非常完美，太棒了！请继续保持！\n");
//                                                    }
//                                                } else {
//                                                    if (gaomi_value < 0.96) {
//                                                        tong("测血脂四项/高密度脂蛋白", "risk","不好","红色");
////                                                suggest.setText(suggest.getText().toString() + "您的高密度脂蛋白已超标，建议立即就医！\n");
//                                                    } else if (gaomi_value < 1.56) {
//                                                        tong("测血脂四项/高密度脂蛋白", "abnormal","还行","黄色");
////                                                suggest.setText(suggest.getText().toString() + "您的高密度脂蛋白正常，不错哦！请继续保持！\n");
//                                                    } else {
//                                                        tong("测血脂四项/高密度脂蛋白", "normal","好","绿色");
//
////                                                suggest.setText(suggest.getText().toString() + "您的高密度脂蛋白非常完美，太棒了！请继续保持！\n");
//                                                    }
//                                                }

                                            String value_dimi = list1.get(11) + list1.get(12);
                                            Integer dimi = Integer.parseInt(value_dimi, 16);
                                            float dimi_value = dimi.floatValue() / 100;
                                            System.out.println("低密LDL:" + dimi_value);
//                                                if (dimi_value < 2.1) {
//                                                    tong("测血脂四项/低密度脂蛋白", "normal","好","绿色");
////                                            suggest.setText(suggest.getText().toString() + "您的低密度脂蛋白非常完美，太棒了！请继续保持！\n");
//                                                } else if (dimi_value < 3.1) {
//                                                    tong("测血脂四项/低密度脂蛋白", "anormal","还行","黄色");
////                                            suggest.setText(suggest.getText().toString() + "您的低密度脂蛋白正常，不错哦！请继续保持！\n");
//                                                } else {
//                                                    tong("测血脂四项/低密度脂蛋白", "risk","不好","红色");
////                                            suggest.setText(suggest.getText().toString() + "您的低密度脂蛋白已超标，建议立即就医！\n");
//                                                }
//
//                                                addUserBodyFour(zongdan_value + "", ganyou_value + "", gaomi_value + "", dimi_value + "");
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
//                connect_type.setText("断开连接");
//                connect_type.setBackgroundResource(R.drawable.gray_bg);
                // 连接中断，isActiveDisConnected表示是否是主动调用了断开连接方法
                if (type.equals("xueyang") && !lastXueYang.equals("0")) {
                    //addUserBodyXueYang(lastXueYang);
                } else if (type.equals("xuetang") && !lastXueTang.equals("0")) {
//                    addUserBodyXueTang(lastXueTang);
                } else if (type.equals("danguchun") && !lastDanGuChun.equals("0")) {
//                    addUserBodyDanGuChun(lastDanGuChun);
                } else if (type.equals("niaosuan") && !lastNiaoSuan.equals("0")) {
//                    addUserBodyNiaoSuan(lastNiaoSuan);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BleManager.getInstance().destroy();
    }

    private void uploadTestData(String value) {
        Long userId = 123123123123L;

        TestBean testBean = new TestBean();
        testBean.setTemp(value);
        testBean.setUserId(userId);

        Log.d("TAG", "uploadTestData: " + new Gson().toJson(testBean));

        OkHttpUtil.getInstance().doPost(API.hardwareHost, new Gson().toJson(testBean), new Callback() {
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