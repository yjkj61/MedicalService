package com.example.medicalservice.activity.propertyFiveBlessingsActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.Code200;
import com.example.medicalservice.databinding.ActivityCardServiceDetailBinding;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.OkHttpUtil;
import com.example.medicalservice.tools.StringUtils;
import com.google.gson.Gson;
import com.weigan.loopview.LoopView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CardServiceDetail extends BaseActivity<ActivityCardServiceDetailBinding> {

    private String type;

    private String sTypeId;
    int primaryColor;
    int themeColor;

    private MenuBean menuBean;

    private List<String> selectOptions = new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
        getUserMoneyInfo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public void initView() {
        super.initView();

        viewBinding.back.setOnClickListener(v -> finish());
        type = getIntent().getStringExtra("msg");
        sTypeId = getIntent().getStringExtra("msg2");
        try {
            viewConfig();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        viewBinding.money.setText("余额" + MyApplication.getInstance().getOwnerRemainMoney());


        viewBinding.timeSelect.setOnClickListener(v -> {
            Calendar startCal = Calendar.getInstance();
            Calendar sal = Calendar.getInstance();

            startCal.set(1998, 0, 1, 0, 0, 0);
            TimePickerView pvTime = new TimePickerBuilder(activity, (date, v1) -> {
                viewBinding.timeSelect.setText(StringUtils.forDataTimeYMD_HM(date));
            }).setDate(sal).setRangDate(startCal, StringUtils.strForCalendar("2029-12-31 23:59:59"))//起始终止年月日设定// 如果不设置的话，默认是系统时间*/
                    .setType(new boolean[]{true, true, true, true, true, false}).setCancelText("取消").setSubmitText("确定").build();
            pvTime.show();
        });

        viewBinding.submitBox.setOnClickListener(v -> {
            try {
                //commitOrder();
                submitOrder();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private void firstSelectOptionDataGet(String type, TextView textView) {

        List<String> firstSelectOption = new ArrayList<>();

        selectOptions.clear();

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sTypeId", sTypeId);
            jsonObject.put("firstDownProperty", type);

            Log.d("TAG", "firstSelectOptionDataGet: " + jsonObject.toString());
            OkHttpUtil.getInstance().doPost(API.selectTypeDownBySTypeIdAndFirstDownProperty, jsonObject.toString(), new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                    if (response.body() != null) {

                        try {
                            JSONArray jsonArray = new JSONArray(response.body().string());

                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.d("TAG", "onResponse: " + jsonArray.getString(i));
                                firstSelectOption.add(jsonArray.getString(i));
                            }


                            runOnUiThread(() -> addDialog(firstSelectOption, textView));

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }

                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


    }

    private void secondSelectOptionDataGet(String type, TextView textView) {
        List<String> selectOption = new ArrayList<>();

        selectOptions.clear();

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sTypeId", sTypeId);
            jsonObject.put("secondDownProperty", type);

            Log.d("TAG", "firstSelectOptionDataGet: " + jsonObject.toString());
            OkHttpUtil.getInstance().doPost(API.selectTypeDownBySTypeIdAndSecondDownProperty, jsonObject.toString(), new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                    if (response.body() != null) {

                        try {
                            JSONArray jsonArray = new JSONArray(response.body().string());

                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.d("TAG", "onResponse: " + jsonArray.getString(i));
                                selectOption.add(jsonArray.getString(i));
                            }


                            runOnUiThread(() -> addDialog(selectOption, textView));

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }

                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }


    private void thirdSelectOptionDataGet(String type, TextView textView) {
        List<String> selectOption = new ArrayList<>();

        selectOptions.clear();

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sTypeId", sTypeId);
            jsonObject.put("thirdDownProperty", type);

            OkHttpUtil.getInstance().doPost(API.selectTypeDownBySTypeIdAndThirdDownProperty, jsonObject.toString(), new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                    if (response.body() != null) {

                        try {
                            JSONArray jsonArray = new JSONArray(response.body().string());

                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.d("TAG", "onResponse: " + jsonArray.getString(i));
                                selectOption.add(jsonArray.getString(i));
                            }


                            runOnUiThread(() -> addDialog(selectOption, textView));

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }

                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }


    private void addDialog(List<String> items, TextView textView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Dialog_style);
        View view = LayoutInflater.from(activity).inflate(R.layout.select_option_dialog, null);

        builder.setView(view);
        TextView left_btn, right_btn;
        LoopView loopView;
        left_btn = view.findViewById(R.id.yes);
        right_btn = view.findViewById(R.id.no);
        loopView = view.findViewById(R.id.loopView);

        loopView.setItems(items);


        AlertDialog alertDialog = builder.create();
        alertDialog.show();


        left_btn.setOnClickListener(v1 -> {
            alertDialog.dismiss();
        });

        right_btn.setOnClickListener(v1 -> {
            textView.setText(items.get(loopView.getSelectedItem()));
            try {
                buildOrderMoney();
                //getServiceInfo();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            alertDialog.dismiss();

        });


    }

    private void buildOrderMoney() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        viewBinding.littleTitle.setText(viewBinding.firstValue.getText().toString());
        viewBinding.serviceInfo.setText(menuBean.getSecondElseProperty() + "：" + viewBinding.secondValue.getText().toString() + " " + menuBean.getThirdElseProperty() + viewBinding.fourSelectValue.getText().toString());
        jsonObject.put("firstPropertyValue", viewBinding.littleTitle.getText().toString());
        jsonObject.put("secondPropertyValue", viewBinding.secondValue.getText().toString());
        jsonObject.put("thirdPropertyValue", viewBinding.fourSelectValue.getText().toString());
        OkHttpUtil.getInstance().doPost(API.selectBookMoneyByDifferentValue, jsonObject.toString(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody body = response.body();
                if(body != null){
                    String orderMoney = body.string();
                    runOnUiThread(() -> viewBinding.orderMoney.setText(orderMoney));

                }
            }
        });
    }
    private void commitOrder(String money) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bookMoney", money);
        OkHttpUtil.getInstance().doPost(API.submitApi, jsonObject.toString(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody body = response.body();
                if(body != null){
                    Code200 code200 = new Gson().fromJson(body.string(), Code200.class);
                    if(code200.getCode() == 200){
                        runOnUiThread(()->{
                            showToast("下单成功");
                            go(OrderView.class);
                        });

                    }
                }
            }
        });
    }

    private void submitOrder() throws JSONException {
        JSONObject jsonObject = new JSONObject();


        if (viewBinding.timeSelect.getText().toString().equals("点击选择时间")) {
            showToast("请选择时间");
            return;
        }

        jsonObject.put("bookStartTime", viewBinding.timeSelect.getText().toString() + ":00");
        jsonObject.put("sTypeId", sTypeId);


        switch (type) {
            case "家政保洁":
            case "心理咨询":
            case "上门护理":
                if (viewBinding.firstValue.getText().toString().equals("请选择")) {
                    showToast("请选择服务类型");
                    return;
                }

                jsonObject.put("firstElsePropertyValue", viewBinding.firstValue.getText().toString());
                jsonObject.put("secondElsePropertyValue", viewBinding.secondValue.getText().toString().equals("请选择") ? "" : viewBinding.secondValue.getText().toString());
                jsonObject.put("thirdElsePropertyValue", viewBinding.fourSelectValue.getText().toString().equals("请选择") ? "" : viewBinding.fourSelectValue.getText().toString());

                break;

            case "衣物清洗":


                if (viewBinding.firstValue.getText().toString().equals("请选择") || viewBinding.secondValue.getText().toString().equals("请选择")) {
                    showToast("选择衣物类型和总件数");
                    return;
                }

                jsonObject.put("firstElsePropertyValue", viewBinding.firstValue.getText().toString());
                jsonObject.put("secondElsePropertyValue", viewBinding.secondValue.getText().toString());
                jsonObject.put("thirdElsePropertyValue", viewBinding.fourSelectValue.getText().toString().equals("请选择") ? "" : viewBinding.fourSelectValue.getText().toString());

                break;

            case "家电清洗":
            case "家电维修":

                if (viewBinding.firstValue.getText().toString().equals("请选择")) {
                    showToast("选择家电类型");
                    return;
                }

                jsonObject.put("firstElsePropertyValue", viewBinding.firstValue.getText().toString());
                jsonObject.put("secondElsePropertyValue", viewBinding.secondValue.getText().toString().equals("请选择") ? "" : viewBinding.secondValue.getText().toString());
                jsonObject.put("thirdElsePropertyValue", viewBinding.fourSelectValue.getText().toString().equals("请选择") ? "" : viewBinding.fourSelectValue.getText().toString());

                break;

            case "房屋维修":


                if (viewBinding.secondValue.getText().toString().equals("请选择")) {
                    showToast("选择维修类型");
                    return;
                }

                jsonObject.put("firstElsePropertyValue", viewBinding.secondValue.getText().toString());
                jsonObject.put("secondElsePropertyValue", viewBinding.fourSelectValue.getText().toString().equals("请选择") ? "" : viewBinding.fourSelectValue.getText().toString());
                jsonObject.put("thirdElsePropertyValue", "");

                break;

            case "水电安装":
                if (viewBinding.secondValue.getText().toString().equals("请选择") || viewBinding.fourSelectValue.getText().toString().equals("请选择")) {
                    showToast("选择服务项目和购买途径");
                    return;
                }

                jsonObject.put("firstElsePropertyValue", viewBinding.secondValue.getText().toString());
                jsonObject.put("secondElsePropertyValue", viewBinding.fourSelectValue.getText().toString());
                jsonObject.put("thirdElsePropertyValue", "");
                break;

            case "水电维修":


                if (viewBinding.secondValue.getText().toString().equals("请选择")) {
                    showToast("选择水电情况");
                    return;
                }

                jsonObject.put("firstElsePropertyValue", viewBinding.secondValue.getText().toString());
                jsonObject.put("secondElsePropertyValue", "");
                jsonObject.put("thirdElsePropertyValue", "");
                break;
            case "管道疏通":


                if (viewBinding.secondValue.getText().toString().equals("请选择")) {
                    showToast("具体管道位置");
                    return;
                }

                jsonObject.put("firstElsePropertyValue", viewBinding.secondValue.getText().toString());
                jsonObject.put("secondElsePropertyValue", "");
                jsonObject.put("thirdElsePropertyValue", "");
                break;
            case "助浴服务":

                if (viewBinding.fourSelectValue.getText().toString().equals("请选择")) {
                    showToast("请选择人数");
                    return;
                }

                jsonObject.put("firstElsePropertyValue", viewBinding.fourSelectValue.getText().toString());
                jsonObject.put("secondElsePropertyValue", "");
                jsonObject.put("thirdElsePropertyValue", "");
                break;

            case "上门理发":
            case "按摩理疗":
            case "陪伴出行":

                if (viewBinding.secondValue.getText().toString().equals("请选择")) {
                    showToast("选择服务类型");
                    return;
                }

                jsonObject.put("firstElsePropertyValue", viewBinding.secondValue.getText().toString());
                jsonObject.put("secondElsePropertyValue", viewBinding.fourSelectValue.getText().toString().equals("请选择") ? "" : viewBinding.fourSelectValue.getText().toString());
                jsonObject.put("thirdElsePropertyValue", "");

                break;


            case "生活配送":

                if (viewBinding.secondValue.getText().toString().equals("请选择") || viewBinding.secondValue.getText().toString().equals("请选择")) {
                    showToast("选择配送类型和配送数量");
                    return;
                }

                jsonObject.put("firstElsePropertyValue", viewBinding.secondValue.getText().toString());
                jsonObject.put("secondElsePropertyValue", viewBinding.fourSelectValue.getText().toString());

                jsonObject.put("thirdElsePropertyValue", "");
                break;

            case "户外活动":

                if (viewBinding.secondValue.getText().toString().equals("请选择")) {
                    showToast("选择服务项目");
                    return;
                }

                jsonObject.put("firstElsePropertyValue", viewBinding.secondValue.getText().toString());
                jsonObject.put("secondElsePropertyValue", viewBinding.fourSelectValue.getText().toString().equals("请选择") ? "" : viewBinding.fourSelectValue.getText().toString());
                jsonObject.put("thirdElsePropertyValue", "");

                break;

            case "快递跑腿":
                if (viewBinding.secondValue.getText().toString().equals("请选择")) {
                    showToast("选择服务类型");
                    return;
                }

                jsonObject.put("firstElsePropertyValue", viewBinding.secondValue.getText().toString());
                jsonObject.put("secondElsePropertyValue", "");
                jsonObject.put("thirdElsePropertyValue", "");
                break;

            case "陪医购药":
                if (viewBinding.secondValue.getText().toString().equals("请选择") || viewBinding.fourSelectValue.getText().toString().equals("请选择")) {
                    showToast("选择服务类型和人数");
                    return;
                }
                jsonObject.put("firstElsePropertyValue", viewBinding.secondValue.getText().toString());
                jsonObject.put("secondElsePropertyValue", viewBinding.fourSelectValue.getText().toString());
                jsonObject.put("thirdElsePropertyValue", "");
                break;
        }


        Log.d(type, sTypeId + jsonObject.toString());

        OkHttpUtil.getInstance().doPost(API.book, jsonObject.toString(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {

                    Code200 code200 = new Gson().fromJson(response.body().string(), Code200.class);

                    if (code200.getCode() == 200) {
                        runOnUiThread(() -> {
                            try {
                                commitOrder(viewBinding.orderMoney.getText().toString());
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
//                            showToast("下单成功");
//                            go(OrderView.class);
                        });
                    } else {
                        runOnUiThread(() -> showToast("请求失败"));
                    }


                }

            }
        });
    }

    /**
     * 查询用户余额
     */
    private void getUserMoneyInfo(){
        updateMoney(new Handler(msg -> {
            viewBinding.money.setText("余额："+msg.obj.toString());
            return false;
        }));
    }


    private void getServiceInfo() throws JSONException {


        if (menuBean == null) return;

        JSONObject jsonObject = new JSONObject();

        if (type.equals("快递跑腿")) {
            viewBinding.littleTitle.setText(viewBinding.secondValue.getText().toString());
            jsonObject.put("firstPropertyValue", viewBinding.littleTitle.getText().toString());
            jsonObject.put("secondPropertyValue", "");
            jsonObject.put("thirdPropertyValue", "");
            OkHttpUtil.getInstance().doPost(API.selectBookMoneyByDifferentValue, jsonObject.toString(), new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                    String data = response.body().string();
                    if (data.isEmpty()) {
                        runOnUiThread(() -> viewBinding.fourSelectValue.setText("面议"));
                    } else {
                        runOnUiThread(() -> viewBinding.fourSelectValue.setText(data));
                    }

                }
            });
            return;
        }
        switch (type) {
            case "家政保洁":
                break;
            case "衣物清洗":
            case "家电清洗":
            case "家电维修":
                viewBinding.littleTitle.setText(viewBinding.firstValue.getText().toString());
                viewBinding.serviceInfo.setText(menuBean.getSecondElseProperty() + "：" + viewBinding.secondValue.getText().toString() + " " + menuBean.getThirdElseProperty() + viewBinding.fourSelectValue.getText().toString());

                jsonObject.put("firstPropertyValue", viewBinding.littleTitle.getText().toString());
                jsonObject.put("secondPropertyValue", viewBinding.secondValue.getText().toString());
                jsonObject.put("thirdPropertyValue", viewBinding.fourSelectValue.getText().toString());
                break;
            case "房屋维修":
            case "水电安装":
            case "上门理发":
            case "按摩理疗":
            case "户外活动":
            case "生活配送":
            case "陪伴出行":
            case "陪医购药":
                viewBinding.littleTitle.setText(viewBinding.secondValue.getText().toString());
                viewBinding.serviceInfo.setText(menuBean.getSecondElseProperty() + "：" + viewBinding.fourSelectValue.getText().toString());


                jsonObject.put("firstPropertyValue", viewBinding.littleTitle.getText().toString());
                jsonObject.put("secondPropertyValue", viewBinding.fourSelectValue.getText().toString());
                jsonObject.put("thirdPropertyValue", "");
                break;


            case "水电维修":
            case "管道疏通":
                viewBinding.littleTitle.setText(viewBinding.secondValue.getText().toString());
                jsonObject.put("firstPropertyValue", viewBinding.littleTitle.getText().toString());
                jsonObject.put("secondPropertyValue", "");
                jsonObject.put("thirdPropertyValue", "");
                break;
            case "助浴服务":
                viewBinding.littleTitle.setText(viewBinding.fourSelectValue.getText().toString());
                jsonObject.put("firstPropertyValue", viewBinding.littleTitle.getText().toString());
                jsonObject.put("secondPropertyValue", "");
                jsonObject.put("thirdPropertyValue", "");
                break;

            case "心理咨询":
            case "上门护理":
                viewBinding.littleTitle.setText(viewBinding.firstValue.getText().toString());
                viewBinding.serviceInfo.setText(menuBean.getSecondElseProperty() + "：" + viewBinding.secondValue.getText().toString() + " " + menuBean.getThirdElseProperty() + viewBinding.fourSelectValue.getText().toString());
                jsonObject.put("firstPropertyValue", viewBinding.littleTitle.getText().toString());
                jsonObject.put("secondPropertyValue", viewBinding.secondValue.getText().toString());
                jsonObject.put("thirdPropertyValue", "");
                break;


        }

        Log.d("TAG", "getServiceInfo: " + jsonObject.toString());

        OkHttpUtil.getInstance().doPost(API.selectBookMoneyByDifferentValue, jsonObject.toString(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                String data = response.body().string();
                if (data.isEmpty()) {
                    runOnUiThread(() -> {
                        viewBinding.orderMoney.setText("面议");
                    });
                } else {
                    runOnUiThread(() -> viewBinding.orderMoney.setText(data));
                }

            }
        });


    }

    private void viewConfig() throws JSONException {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("sTypeId", sTypeId);

        OkHttpUtil.getInstance().doPost(API.selectItemBySTypeId, jsonObject.toString(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {


                    menuBean = new Gson().fromJson(response.body().string(), MenuBean.class);

                    if (menuBean == null) {
                        runOnUiThread(() -> showToast("返回数据为空"));
                        return;
                    }
                    runOnUiThread(() -> {
                        switch (type) {
                            case "家政保洁":
                                primaryColor = Color.parseColor("#FF9994F3");
                                themeColor = Color.parseColor("#FFF3F2F7");
                                viewBinding.backupBox.setVisibility(View.GONE);
                                viewBinding.secondSelectText.setText(menuBean.getFirstElseProperty());
                                viewBinding.thirdSelectText.setText(menuBean.getSecondElseProperty());
                                viewBinding.fourSelectText.setText(menuBean.getThirdElseProperty());
                                viewBinding.fourNeedText.setVisibility(View.GONE);

                                viewBinding.firstValue.setOnClickListener(v -> firstSelectOptionDataGet(menuBean.getFirstElseProperty(), viewBinding.firstValue));
                                viewBinding.secondValue.setOnClickListener(v -> secondSelectOptionDataGet(menuBean.getSecondElseProperty(), viewBinding.secondValue));
                                viewBinding.fourSelectValue.setOnClickListener(v -> thirdSelectOptionDataGet(menuBean.getThirdElseProperty(), viewBinding.fourSelectValue));

                                break;

                            case "衣物清洗":
                                primaryColor = Color.parseColor("#FF65A2EE");
                                themeColor = Color.parseColor("#FFEEF5FF");
                                viewBinding.secondSelectText.setText(menuBean.getFirstElseProperty());
                                viewBinding.thirdSelectText.setText(menuBean.getSecondElseProperty());
                                viewBinding.fourSelectText.setText(menuBean.getThirdElseProperty());
                                viewBinding.firstValue.setOnClickListener(v -> firstSelectOptionDataGet(menuBean.getFirstElseProperty(), viewBinding.firstValue));
                                viewBinding.secondValue.setOnClickListener(v -> secondSelectOptionDataGet(menuBean.getSecondElseProperty(), viewBinding.secondValue));
                                viewBinding.fourSelectValue.setOnClickListener(v -> thirdSelectOptionDataGet(menuBean.getThirdElseProperty(), viewBinding.fourSelectValue));
                                break;

                            case "家电清洗":
                                primaryColor = Color.parseColor("#FFEFAF6E");
                                themeColor = Color.parseColor("#FFF7F3F0");
                                viewBinding.secondSelectText.setText(menuBean.getFirstElseProperty());
                                viewBinding.thirdSelectText.setText(menuBean.getSecondElseProperty());
                                viewBinding.fourSelectText.setText(menuBean.getThirdElseProperty());


                                viewBinding.firstValue.setOnClickListener(v -> firstSelectOptionDataGet(menuBean.getFirstElseProperty(), viewBinding.firstValue));
                                viewBinding.secondValue.setOnClickListener(v -> secondSelectOptionDataGet(menuBean.getSecondElseProperty(), viewBinding.secondValue));
                                viewBinding.fourSelectValue.setOnClickListener(v -> thirdSelectOptionDataGet(menuBean.getThirdElseProperty(), viewBinding.fourSelectValue));

                                break;

                            case "家电维修":
                                primaryColor = Color.parseColor("#FF5AC6E0");
                                themeColor = Color.parseColor("#FFCCECED");
                                viewBinding.secondSelectText.setText(menuBean.getFirstElseProperty());
                                viewBinding.thirdSelectText.setText(menuBean.getSecondElseProperty());
                                viewBinding.fourSelectText.setText(menuBean.getThirdElseProperty());
                                viewBinding.backupBox.setVisibility(View.GONE);

                                viewBinding.firstValue.setOnClickListener(v -> firstSelectOptionDataGet(menuBean.getFirstElseProperty(), viewBinding.firstValue));
                                viewBinding.secondValue.setOnClickListener(v -> secondSelectOptionDataGet(menuBean.getSecondElseProperty(), viewBinding.secondValue));
                                viewBinding.fourSelectValue.setOnClickListener(v -> thirdSelectOptionDataGet(menuBean.getThirdElseProperty(), viewBinding.fourSelectValue));

                                break;

                            case "房屋维修":
                                primaryColor = Color.parseColor("#FF80D3B3");
                                themeColor = Color.parseColor("#FFE2F2DD");
                                //viewBinding.doubleTimeBox.setVisibility(View.VISIBLE);
                                viewBinding.firstValueBox.setVisibility(View.GONE);
                                viewBinding.thirdSelectText.setText(menuBean.getFirstElseProperty());
                                viewBinding.fourSelectText.setText(menuBean.getSecondElseProperty());
                                viewBinding.thirdNeedText.setVisibility(View.VISIBLE);
                                viewBinding.fourNeedText.setVisibility(View.GONE);


                                viewBinding.secondValue.setOnClickListener(v -> firstSelectOptionDataGet(menuBean.getFirstElseProperty(), viewBinding.secondValue));
                                viewBinding.fourSelectValue.setOnClickListener(v -> secondSelectOptionDataGet(menuBean.getSecondElseProperty(), viewBinding.fourSelectValue));

                                break;

                            case "水电安装":
                                primaryColor = Color.parseColor("#FFF4A59D");
                                themeColor = Color.parseColor("#FFFFE5E4");
                                viewBinding.firstValueBox.setVisibility(View.GONE);
                                viewBinding.thirdSelectText.setText(menuBean.getFirstElseProperty());
                                viewBinding.fourSelectText.setText(menuBean.getSecondElseProperty());
                                viewBinding.fourNeedText.setVisibility(View.VISIBLE);

                                viewBinding.secondValue.setOnClickListener(v -> firstSelectOptionDataGet(menuBean.getFirstElseProperty(), viewBinding.secondValue));
                                viewBinding.fourSelectValue.setOnClickListener(v -> secondSelectOptionDataGet(menuBean.getSecondElseProperty(), viewBinding.secondValue));
                                break;

                            case "水电维修":
                                primaryColor = Color.parseColor("#FFA17EE3");
                                themeColor = Color.parseColor("#FFE0D1F7");
                                viewBinding.firstValueBox.setVisibility(View.GONE);
                                viewBinding.bottomRightBox.setVisibility(View.GONE);
                                viewBinding.thirdSelectText.setText(menuBean.getFirstElseProperty());
                                viewBinding.thirdNeedText.setVisibility(View.VISIBLE);
                                viewBinding.spacer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));

                                viewBinding.secondValue.setOnClickListener(v -> firstSelectOptionDataGet(menuBean.getFirstElseProperty(), viewBinding.secondValue));
                                break;
                            case "管道疏通":
                                primaryColor = Color.parseColor("#FFF5BB8D");
                                themeColor = Color.parseColor("#FFFFE3D4");
                                viewBinding.firstValueBox.setVisibility(View.GONE);
                                viewBinding.bottomRightBox.setVisibility(View.GONE);
                                viewBinding.thirdSelectText.setText(menuBean.getFirstElseProperty());
                                viewBinding.thirdNeedText.setVisibility(View.VISIBLE);
                                viewBinding.spacer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
                                viewBinding.secondValue.setOnClickListener(v -> firstSelectOptionDataGet(menuBean.getFirstElseProperty(), viewBinding.secondValue));


                                break;
                            case "助浴服务":
                                primaryColor = Color.parseColor("#FF65A2EE");
                                themeColor = Color.parseColor("#FFEEF5FF");
                                viewBinding.firstValueBox.setVisibility(View.GONE);
                                viewBinding.bottomLeftBox.setVisibility(View.GONE);
                                viewBinding.thirdNeedText.setVisibility(View.GONE);
                                viewBinding.fourNeedText.setVisibility(View.GONE);
                                viewBinding.fourSelectText.setText(menuBean.getFirstElseProperty());
                                viewBinding.spacer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
//                                    List<String> people = new ArrayList<>();
//                                    for (int i = 0; i < 10; i++) {
//                                        people.add(i + 1 + "人");
//                                    }
                                viewBinding.fourSelectValue.setOnClickListener(v -> firstSelectOptionDataGet(menuBean.getFirstElseProperty(), viewBinding.secondValue));
                                break;

                            case "上门理发":
                                primaryColor = Color.parseColor("#FFFF9699");
                                themeColor = Color.parseColor("#FFFFE5E4");
                                viewBinding.firstValueBox.setVisibility(View.GONE);
                                viewBinding.thirdSelectText.setText(menuBean.getFirstElseProperty());
                                viewBinding.thirdNeedText.setVisibility(View.VISIBLE);
                                viewBinding.fourSelectText.setText(menuBean.getSecondElseProperty());
                                viewBinding.fourNeedText.setVisibility(View.GONE);

                                viewBinding.secondValue.setOnClickListener(v -> firstSelectOptionDataGet(menuBean.getFirstElseProperty(), viewBinding.secondValue));
                                viewBinding.fourSelectValue.setOnClickListener(v -> secondSelectOptionDataGet(menuBean.getSecondElseProperty(), viewBinding.secondValue));
                                break;

                            case "按摩理疗":
                                primaryColor = Color.parseColor("#FF5AC6E0");
                                themeColor = Color.parseColor("#FFCCECED");
                                viewBinding.firstValueBox.setVisibility(View.GONE);
                                viewBinding.thirdSelectText.setText(menuBean.getFirstElseProperty());
                                viewBinding.thirdNeedText.setVisibility(View.VISIBLE);
                                viewBinding.fourSelectText.setText(menuBean.getSecondElseProperty());
                                viewBinding.fourNeedText.setVisibility(View.GONE);
                                viewBinding.secondValue.setOnClickListener(v -> firstSelectOptionDataGet(menuBean.getFirstElseProperty(), viewBinding.secondValue));
                                viewBinding.fourSelectValue.setOnClickListener(v -> secondSelectOptionDataGet(menuBean.getSecondElseProperty(), viewBinding.secondValue));
                                break;

                            case "生活配送":
                                primaryColor = Color.parseColor("#FF9994F3");
                                themeColor = Color.parseColor("#FFF3F2F7");
                                viewBinding.firstValueBox.setVisibility(View.GONE);
                                viewBinding.thirdSelectText.setText(menuBean.getFirstElseProperty());
                                viewBinding.thirdNeedText.setVisibility(View.VISIBLE);
                                viewBinding.fourSelectText.setText(menuBean.getSecondElseProperty());
                                viewBinding.fourNeedText.setVisibility(View.GONE);

                                viewBinding.secondValue.setOnClickListener(v -> firstSelectOptionDataGet(menuBean.getFirstElseProperty(), viewBinding.secondValue));
                                viewBinding.fourSelectValue.setOnClickListener(v -> secondSelectOptionDataGet(menuBean.getSecondElseProperty(), viewBinding.secondValue));
                                break;

                            case "心理咨询":
                                primaryColor = Color.parseColor("#FFED9BC2");
                                themeColor = Color.parseColor("#FFFFF0F5");
                                viewBinding.secondSelectText.setText(menuBean.getFirstElseProperty());
                                viewBinding.thirdSelectText.setText(menuBean.getSecondElseProperty());
                                viewBinding.thirdNeedText.setVisibility(View.GONE);
                                viewBinding.fourSelectText.setText(menuBean.getThirdElseProperty());
                                viewBinding.fourNeedText.setVisibility(View.GONE);

                                viewBinding.firstValue.setOnClickListener(v -> firstSelectOptionDataGet(menuBean.getFirstElseProperty(), viewBinding.firstValue));
                                viewBinding.secondValue.setOnClickListener(v -> secondSelectOptionDataGet(menuBean.getSecondElseProperty(), viewBinding.secondValue));
                                viewBinding.fourSelectValue.setOnClickListener(v -> thirdSelectOptionDataGet(menuBean.getThirdElseProperty(), viewBinding.fourSelectValue));
                                break;

                            case "上门护理":
                                primaryColor = Color.parseColor("#FFA17EE3");
                                themeColor = Color.parseColor("#FFE0D1F7");
                                viewBinding.secondSelectText.setText(menuBean.getFirstElseProperty());
                                viewBinding.thirdSelectText.setText(menuBean.getSecondElseProperty());
                                viewBinding.thirdNeedText.setVisibility(View.GONE);
                                viewBinding.fourSelectText.setText(menuBean.getThirdElseProperty());
                                viewBinding.fourNeedText.setVisibility(View.GONE);

                                viewBinding.firstValue.setOnClickListener(v -> firstSelectOptionDataGet(menuBean.getFirstElseProperty(), viewBinding.firstValue));
                                viewBinding.secondValue.setOnClickListener(v -> secondSelectOptionDataGet(menuBean.getSecondElseProperty(), viewBinding.secondValue));
                                viewBinding.fourSelectValue.setOnClickListener(v -> thirdSelectOptionDataGet(menuBean.getThirdElseProperty(), viewBinding.fourSelectValue));
                                break;

                            case "户外活动":
                                primaryColor = Color.parseColor("#FFF4A59D");
                                themeColor = Color.parseColor("#FFFFE5E4");
                                viewBinding.firstValueBox.setVisibility(View.GONE);
                                viewBinding.thirdSelectText.setText(menuBean.getFirstElseProperty());
                                viewBinding.fourSelectText.setText(menuBean.getSecondElseProperty());
                                viewBinding.fourNeedText.setVisibility(View.GONE);

                                viewBinding.secondValue.setOnClickListener(v -> firstSelectOptionDataGet(menuBean.getFirstElseProperty(), viewBinding.secondValue));
                                viewBinding.fourSelectValue.setOnClickListener(v -> secondSelectOptionDataGet(menuBean.getSecondElseProperty(), viewBinding.secondValue));
                                break;

                            case "陪伴出行":
                                primaryColor = Color.parseColor("#FF80D3B3");
                                themeColor = Color.parseColor("#FFF2F7F0");
                                viewBinding.firstValueBox.setVisibility(View.GONE);
                                viewBinding.thirdSelectText.setText(menuBean.getFirstElseProperty());
                                viewBinding.thirdNeedText.setVisibility(View.GONE);
                                viewBinding.fourSelectText.setText(menuBean.getSecondElseProperty());
                                viewBinding.fourNeedText.setVisibility(View.GONE);

                                viewBinding.secondValue.setOnClickListener(v -> firstSelectOptionDataGet(menuBean.getFirstElseProperty(), viewBinding.secondValue));
                                viewBinding.fourSelectValue.setOnClickListener(v -> secondSelectOptionDataGet(menuBean.getSecondElseProperty(), viewBinding.secondValue));
                                break;
                            case "快递跑腿":
                                primaryColor = Color.parseColor("#FF5AC6E0");
                                themeColor = Color.parseColor("#FFCCECED");
                                viewBinding.firstValueBox.setVisibility(View.GONE);
                                viewBinding.thirdSelectText.setText(menuBean.getFirstElseProperty());
                                viewBinding.thirdNeedText.setVisibility(View.VISIBLE);
                                viewBinding.fourSelectText.setText("预计费用");
                                viewBinding.fourNeedText.setVisibility(View.GONE);
                                viewBinding.fourSelectValue.setText("");
                                viewBinding.fourSelectValue.setTextSize(18f);
                                viewBinding.fourSelectIcon.setVisibility(View.GONE);
                                viewBinding.serviceCashBox.setVisibility(View.GONE);

                                viewBinding.serviceCashBox.setVisibility(View.GONE);
                                viewBinding.bottomImageBox.setVisibility(View.VISIBLE);

                                viewBinding.secondValue.setOnClickListener(v -> firstSelectOptionDataGet(menuBean.getFirstElseProperty(), viewBinding.secondValue));
                                break;

                            case "陪医购药":
                                primaryColor = Color.parseColor("#FFED9BC2");
                                themeColor = Color.parseColor("#FFFFF0F5");
                                viewBinding.firstValueBox.setVisibility(View.GONE);
                                viewBinding.thirdSelectText.setText(menuBean.getFirstElseProperty());
                                viewBinding.thirdNeedText.setVisibility(View.VISIBLE);
                                viewBinding.fourNeedText.setVisibility(View.GONE);
                                viewBinding.fourSelectText.setText(menuBean.getSecondElseProperty());
                                viewBinding.serviceCashBox.setVisibility(View.GONE);
                                viewBinding.bottomImageBox.setVisibility(View.VISIBLE);

                                viewBinding.secondValue.setOnClickListener(v -> firstSelectOptionDataGet(menuBean.getFirstElseProperty(), viewBinding.secondValue));
                                viewBinding.fourSelectValue.setOnClickListener(v -> secondSelectOptionDataGet(menuBean.getSecondElseProperty(), viewBinding.secondValue));


                                break;
                        }

                        viewBinding.serviceType.setText(type);
                        viewBinding.serviceType.setTextColor(primaryColor);
                        viewBinding.contentBox.setCardBackgroundColor(themeColor);
                        viewBinding.submitBox.setCardBackgroundColor(primaryColor);
                        viewBinding.bottomProgress.firstStep.setCardBackgroundColor(primaryColor);
                        viewBinding.bottomProgress.secondStep.setCardBackgroundColor(primaryColor);
                        viewBinding.bottomProgress.thirdStep.setCardBackgroundColor(primaryColor);
                        viewBinding.bottomProgress.fourStep.setCardBackgroundColor(primaryColor);
                        viewBinding.bottomProgress.fiveStep.setCardBackgroundColor(primaryColor);
                        viewBinding.bottomProgress.sixStep.setCardBackgroundColor(primaryColor);
                    });

                }

            }
        });


    }

    class MenuBean {

        private Object createBy;
        private Object createTime;
        private Object updateBy;
        private Object updateTime;
        private String remark;
        private int id;
        private int sTypeId;
        private String serviceBookStartTime;
        private String serviceBookEndTime;
        private String firstElseProperty;
        private String secondElseProperty;
        private String thirdElseProperty;
        private String bookMoney;

        public Object getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Object createBy) {
            this.createBy = createBy;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
            this.updateBy = updateBy;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSTypeId() {
            return sTypeId;
        }

        public void setSTypeId(int sTypeId) {
            this.sTypeId = sTypeId;
        }

        public String getServiceBookStartTime() {
            return serviceBookStartTime;
        }

        public void setServiceBookStartTime(String serviceBookStartTime) {
            this.serviceBookStartTime = serviceBookStartTime;
        }

        public String getServiceBookEndTime() {
            return serviceBookEndTime;
        }

        public void setServiceBookEndTime(String serviceBookEndTime) {
            this.serviceBookEndTime = serviceBookEndTime;
        }

        public String getFirstElseProperty() {
            return firstElseProperty;
        }

        public void setFirstElseProperty(String firstElseProperty) {
            this.firstElseProperty = firstElseProperty;
        }

        public String getSecondElseProperty() {
            return secondElseProperty;
        }

        public void setSecondElseProperty(String secondElseProperty) {
            this.secondElseProperty = secondElseProperty;
        }

        public String getThirdElseProperty() {
            return thirdElseProperty;
        }

        public void setThirdElseProperty(String thirdElseProperty) {
            this.thirdElseProperty = thirdElseProperty;
        }

        public String getBookMoney() {
            return bookMoney;
        }

        public void setBookMoney(String bookMoney) {
            this.bookMoney = bookMoney;
        }
    }
}