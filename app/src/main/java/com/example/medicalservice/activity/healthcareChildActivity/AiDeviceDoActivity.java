package com.example.medicalservice.activity.healthcareChildActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.HardwareDictBean;
import com.example.medicalservice.bean.OwnerHardwareListBean;
import com.example.medicalservice.databinding.ActivityHardwareDoBinding;
import com.example.medicalservice.interfaceCallback.DialogInterFace;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.OkHttpUtil;
import com.example.medicalservice.tools.ToastUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @description 操作智能设备
 * @author: Lenovo
 * @date: 2024/5/20
 */
public class AiDeviceDoActivity extends BaseActivity<ActivityHardwareDoBinding> {

    //0:新增，  1：修改
    private int type = 0;

    private int id;

    private long number;

    private int device_type;

    private int position_temp = 0;

    private String ownerid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        super.initView();

        type = getIntent().getIntExtra("type", 0);
        if (type == 1){
            id = getIntent().getIntExtra("id", 0);
            number = getIntent().getLongExtra("number", 0);
            device_type = getIntent().getIntExtra("device_type", 0);
            viewBinding.etCode.setText(number + "");
            viewBinding.tvHardware.setText(getIntent().getStringExtra("name"));
            viewBinding.tvTitle.setText("修改");
        }else if (type == 0){
            ownerid = getIntent().getStringExtra("ownerid");
            viewBinding.tvTitle.setText("新增");
        }

        viewBinding.tvBack.setOnClickListener(view -> finish());

        viewBinding.tvHardware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        viewBinding.tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(viewBinding.tvHardware.getText().toString())){
                    showToast("请选择智能设备");
                    return;
                }
                if ("".equals(viewBinding.etCode.getText().toString())){
                    showToast("请输入设备编号");
                    return;
                }
                number = Long.parseLong(viewBinding.etCode.getText().toString());
                if (type == 0){
                    HarswareAdd(ownerid, number, device_type);
                }else if (type == 1){
                    HarswareEdit(id, number, device_type);
                }
            }
        });

        getHarswareDict();
    }

    String[] list_device = null;

    private void showDialog(){
        if (list_device == null){
            showToast("未获取到智能设备列表");
            return;
        }
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("智能设备")
                .setSingleChoiceItems(list_device, position_temp, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewBinding.tvHardware.setText(list_device[which]);
                        device_type = Integer.parseInt(list_dict.get(which).getDictValue());
                        position_temp = which;
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    List<HardwareDictBean.DataDTO> list_dict = new ArrayList<>();

    //设备字典项
    private void getHarswareDict(){
        OkHttpUtil.getInstance().doGet(API.ownerHardwareDict, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                HardwareDictBean bean = new Gson().fromJson(response.body().string(), HardwareDictBean.class);
                if (bean.getCode() != 200) {
                    activity.runOnUiThread(() -> showToast("请求失败"));
                    return;
                }
                if (bean.getData() != null){
                    list_dict.clear();
                    list_dict.addAll(bean.getData());
                    list_device = new String[list_dict.size()];
                    for (int i = 0; i < list_dict.size(); i++){
                        list_device[i] = list_dict.get(i).getDictLabel();
                        if (type == 1){
                            if (getIntent().getStringExtra("name").equals(list_dict.get(i).getDictLabel())){
                                position_temp = i;
                            }
                        }
                    }
                }

            }
        });
    }

    //修改
    private void HarswareEdit(int id, long number, int type){
        OwnerHardwareListBean.DataDTO data = new OwnerHardwareListBean.DataDTO();
        data.setId(id);
        data.setSerialNumber(number);
        data.setSerialType(type);
        OkHttpUtil.getInstance().doPut(API.ownerHardwareDo, new Gson().toJson(data), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast("修改成功");
                    }
                });
                finish();
            }
        });
    }

    //新增
    private void HarswareAdd(String id, long num, int type){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("ownerId", id);
            jsonObject.put("serialNumber", num);
            jsonObject.put("serialType", type);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        OkHttpUtil.getInstance().doPost(API.ownerHardwareDo, jsonObject.toString(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast("新增成功");
                    }
                });
                finish();
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
    }

}
