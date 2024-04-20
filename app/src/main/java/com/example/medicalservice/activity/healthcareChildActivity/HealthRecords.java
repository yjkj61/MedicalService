package com.example.medicalservice.activity.healthcareChildActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.activity.AddUserActivity;
import com.example.medicalservice.activity.healthcareChildActivity.physicalExamination.AddPhysicalExamination;
import com.example.medicalservice.activity.healthcareChildActivity.physicalExamination.PhysicalExaminationTip;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.PostHealthRecordsBean;
import com.example.medicalservice.dataBaseBean.UserBean;
import com.example.medicalservice.databinding.ActivityHealthRecordsBinding;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.CommonExtKt;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.OkHttpUtil;
import com.example.medicalservice.tools.StringUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HealthRecords extends BaseActivity<ActivityHealthRecordsBinding> {

    List<HealthRecordBean> healthRecords = new ArrayList<>();

    private MsAdapter<HealthRecordBean> fatherMsAdapter;

    private RecordsBean recordsBean;

    private UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        super.initView();

        userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);

        viewBinding.username.setText(MyApplication.getInstance().getUserName());
        GlideUtils.load(activity, MyApplication.getInstance().getUserHeader(), viewBinding.header, R.drawable.header, 90);

        viewBinding.back.setOnClickListener(view -> finish());

        viewBinding.switchUser.setOnClickListener(v -> go(AddUserActivity.class));

        viewBinding.imageView.setOnClickListener(v -> go(ImagingReport.class));

    }

    @Override
    public void initData() {
        super.initData();

        fatherMsAdapter = new MsAdapter<HealthRecordBean>(healthRecords, R.layout.health_records_grid_item) {

            @Override
            public void bindView(ViewHolder holder, HealthRecordBean obj) {


                TextView name = holder.getView(R.id.name);
                CardView edit = holder.getView(R.id.edit);
                TextView edit_text = holder.getView(R.id.edit_text);
                CardView detail = holder.getView(R.id.detail);
                GridView gridView = holder.getView(R.id.gridList);


                MsAdapter<String> msAdapter = new MsAdapter<String>(obj.childList, R.layout.health_records_grid_child_item) {

                    @Override
                    public void bindView(ViewHolder holder1, String text) {

                        TextView childName = holder1.getView(R.id.child_name);
                        ImageView add = holder1.getView(R.id.add);
                        ImageView delete = holder1.getView(R.id.delete);

                        childName.setText(text);

                        if (text.equals("add")) {
                            add.setVisibility(View.VISIBLE);
                            childName.setVisibility(View.GONE);
                        } else {
                            childName.setVisibility(View.VISIBLE);
                            add.setVisibility(View.GONE);
                        }

                        if (obj.isStatus()) {
                            if (!text.equals("add")) delete.setVisibility(View.VISIBLE);
                            add.setVisibility(View.GONE);

                        } else {
                            delete.setVisibility(View.GONE);
                            if (text.equals("add")) add.setVisibility(View.VISIBLE);
                        }

                        if (text.equals("add")) {
                            delete.setVisibility(View.GONE);
                        }

                        delete.setOnClickListener(v -> {
                            obj.childList.remove(text);
                            this.notifyDataSetChanged();
                        });

                        add.setOnClickListener(v -> {
                            if (obj.getFatherName().equals("体检项目")) {
                                go(AddPhysicalExamination.class);
                            } else {
                                addDialog(obj, this);
                            }
                        });


                    }
                };

                gridView.setAdapter(msAdapter);

                name.setText(obj.getFatherName());

                if (obj.getFatherName().equals("体检项目")) {
                    detail.setVisibility(View.VISIBLE);
                    edit.setVisibility(View.GONE);
                } else {
                    detail.setVisibility(View.GONE);
                    edit.setVisibility(View.VISIBLE);
                }

                edit.setOnClickListener(v -> {
                    if (obj.isStatus()) {

                        editData(obj, msAdapter);
                        //changeData(obj,msAdapter);

                    }
                    obj.setStatus(!obj.isStatus());


                    notifyDataSetChanged();
                    msAdapter.notifyDataSetChanged();
                });

                edit_text.setText(obj.isStatus() ? "完成" : "编辑");

                detail.setOnClickListener(v -> {

                    if (obj.getFatherName().equals("体检项目")) {
                        go(PhysicalExaminationTip.class);
                    }
                });


            }
        };

        viewBinding.gridList.setAdapter(fatherMsAdapter);
        getRecordInfo();

    }

    private void getRecordInfo(){
        OkHttpUtil.getInstance().doGet(API.recordsAndroidInfo, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {

                    recordsBean = new Gson().fromJson(response.body().string(), RecordsBean.class);


                    if (recordsBean.getCode() == 200 && recordsBean.getData() != null) {
                        List<String> personalHistory;
                        List<String> familyHistory;
                        List<String> behaviorCustom;
                        List<String> takeMedicine;
                        List<String> allergyHistory;
                        if (recordsBean.getData().getPersonalHistory() != null && !recordsBean.getData().getPersonalHistory().equals("")) {
                            personalHistory = new ArrayList<>(Arrays.asList(recordsBean.getData().getPersonalHistory().split(",")));
                        } else {
                            personalHistory = new ArrayList<>();
                        }

                        if (recordsBean.getData().getFamilyHistory() != null && !recordsBean.getData().getFamilyHistory().equals("")) {
                            familyHistory = new ArrayList<>(Arrays.asList(recordsBean.getData().getFamilyHistory().split(",")));
                        } else {
                            familyHistory = new ArrayList<>();
                        }

                        if (recordsBean.getData().getBehaviorCustom() != null && !recordsBean.getData().getBehaviorCustom().equals("")) {
                            behaviorCustom = new ArrayList<>(Arrays.asList(recordsBean.getData().getBehaviorCustom().split(",")));
                        } else {
                            behaviorCustom = new ArrayList<>();
                        }
                        if (recordsBean.getData().getTakeMedicine() != null && !recordsBean.getData().getTakeMedicine().equals("")) {
                            takeMedicine = new ArrayList<>(Arrays.asList(recordsBean.getData().getTakeMedicine().split(",")));
                        } else {
                            takeMedicine = new ArrayList<>();
                        }

                        if (recordsBean.getData().getAllergyHistory() != null && !recordsBean.getData().getAllergyHistory().equals("")) {
                            allergyHistory = new ArrayList<>(Arrays.asList(recordsBean.getData().getAllergyHistory().split(",")));
                        } else {
                            allergyHistory = new ArrayList<>();
                        }

                        personalHistory.add("add");
                        familyHistory.add("add");
                        behaviorCustom.add("add");
                        takeMedicine.add("add");
                        allergyHistory.add("add");

                        if(healthRecords.isEmpty()){
                            healthRecords.add(new HealthRecordBean("个人病史", false, personalHistory));
                            healthRecords.add(new HealthRecordBean("家族病史", false, familyHistory));
                            healthRecords.add(new HealthRecordBean("服用药物", false, takeMedicine));
                            healthRecords.add(new HealthRecordBean("行为习惯", false, behaviorCustom));
                            healthRecords.add(new HealthRecordBean("过敏史", false, allergyHistory));
                        }



                        runOnUiThread(() -> fatherMsAdapter.notifyDataSetChanged());


                        getDataList();
                    }


                }

            }
        });
    }

    private void addDialog(HealthRecordBean healthRecordBean, MsAdapter<String> msAdapter) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Dialog_style);
        View view = LayoutInflater.from(activity).inflate(R.layout.add_dialog, null);

        builder.setView(view);
        TextView left_btn, right_btn;
        EditText name;
        left_btn = view.findViewById(R.id.yes);
        right_btn = view.findViewById(R.id.no);
        name = view.findViewById(R.id.name);


        AlertDialog alertDialog = builder.create();
        alertDialog.show();


        left_btn.setOnClickListener(v1 -> alertDialog.dismiss());

        right_btn.setOnClickListener(v1 -> {
            if (name.getText().toString().isEmpty()
            ) {
                showToast("请填写");
                return;
            }

            healthRecordBean.childList.add(healthRecordBean.childList.size() - 1, name.getText().toString());


            alertDialog.dismiss();
            editData(healthRecordBean, msAdapter);
        });


    }


    private void editData(HealthRecordBean healthRecordBean, MsAdapter<String> msAdapter) {

        //healthRecordBean.childList.remove("add");

        ArrayList<String> finalList = new ArrayList<>();

        healthRecordBean.childList.forEach(item -> {
            if (!item.equals("add")) {
                finalList.add(item);
            }

        });

        switch (healthRecordBean.getFatherName()){
            case "个人病史":{
                if(recordsBean.getData().getPersonalHistory() == null || recordsBean.getData().getPersonalHistory().isEmpty()){
                    Log.d("healthRecordBean","个人为空，调用插入");
                    recordsBean.getData().setPersonalHistory(CommonExtKt.join2String(finalList));
                    recordsBean.getData().setOwnerId(Integer.parseInt(userBean.getOwnerId()));
                    recordsBean.getData().setUserId(userBean.getUserId());
                    JSONObject jsonObject = new JSONObject();
                    try {
                        //jsonObject.put("id",recordsBean.getData().getId() +"");
                        jsonObject.put("userId",recordsBean.getData().getUserId() +"");
                        jsonObject.put("familyHistory",recordsBean.getData().familyHistory +"");
                        jsonObject.put("behaviorCustom",recordsBean.getData().behaviorCustom +"");
                        jsonObject.put("takeMedicine",recordsBean.getData().takeMedicine +"");
                        jsonObject.put("allergyHistory",recordsBean.getData().allergyHistory +"");
                        jsonObject.put("personalHistory",CommonExtKt.join2String(finalList));
                    }catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    insertRecords(msAdapter,jsonObject.toString());
                }else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("id",recordsBean.getData().getId() +"");
                        jsonObject.put("familyHistory",recordsBean.getData().familyHistory +"");
                        jsonObject.put("behaviorCustom",recordsBean.getData().behaviorCustom +"");
                        jsonObject.put("takeMedicine",recordsBean.getData().takeMedicine +"");
                        jsonObject.put("allergyHistory",recordsBean.getData().allergyHistory +"");
                        jsonObject.put("personalHistory",CommonExtKt.join2String(finalList));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    changeRecord(msAdapter,jsonObject.toString());
                }
                break;
            }
            case "家族病史":{
                if(recordsBean.getData().getFamilyHistory() == null || recordsBean.getData().getFamilyHistory().isEmpty()){
                    recordsBean.getData().setFamilyHistory(CommonExtKt.join2String(finalList));
                    recordsBean.getData().setOwnerId(Integer.parseInt(userBean.getOwnerId()));
                    recordsBean.getData().setUserId(userBean.getUserId());
                    JSONObject jsonObject = new JSONObject();
                    try {
                        //jsonObject.put("id",recordsBean.getData().id +"");
                        jsonObject.put("userId",recordsBean.getData().getUserId() +"");
                        jsonObject.put("personalHistory",recordsBean.getData().personalHistory +"");
                        jsonObject.put("behaviorCustom",recordsBean.getData().behaviorCustom +"");
                        jsonObject.put("takeMedicine",recordsBean.getData().takeMedicine +"");
                        jsonObject.put("allergyHistory",recordsBean.getData().allergyHistory +"");
                        jsonObject.put("familyHistory",CommonExtKt.join2String(finalList));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    insertRecords(msAdapter, jsonObject.toString());
                    //insertRecords(msAdapter, jsonObject.toString());
                }else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("id",recordsBean.getData().getId() +"");
                        jsonObject.put("personalHistory",recordsBean.getData().personalHistory +"");
                        jsonObject.put("behaviorCustom",recordsBean.getData().behaviorCustom +"");
                        jsonObject.put("takeMedicine",recordsBean.getData().takeMedicine +"");
                        jsonObject.put("allergyHistory",recordsBean.getData().allergyHistory +"");
                        jsonObject.put("familyHistory",CommonExtKt.join2String(finalList));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    changeRecord(msAdapter, jsonObject.toString());
                }
                break;
            }
            case "服用药物":{
                if(recordsBean.getData().getTakeMedicine() == null || recordsBean.getData().getTakeMedicine().isEmpty()){
                    recordsBean.getData().setTakeMedicine(CommonExtKt.join2String(finalList));
                    recordsBean.getData().setOwnerId(Integer.parseInt(userBean.getOwnerId()));
                    recordsBean.getData().setUserId(userBean.getUserId());
                    JSONObject jsonObject = new JSONObject();
                    try {
                        //jsonObject.put("id",recordsBean.getData().id +"");
                        jsonObject.put("userId",recordsBean.getData().getUserId() +"");
                        jsonObject.put("personalHistory",recordsBean.getData().personalHistory +"");
                        jsonObject.put("familyHistory",recordsBean.getData().familyHistory +"");
                        jsonObject.put("takeMedicine",CommonExtKt.join2String(finalList));
                        jsonObject.put("allergyHistory",recordsBean.getData().allergyHistory +"");
                        jsonObject.put("behaviorCustom",recordsBean.getData().behaviorCustom + "");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    //String json =  new Gson().toJson(recordsBean);
                    insertRecords(msAdapter, jsonObject.toString());
                }else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("id",recordsBean.getData().getId() +"");
                        jsonObject.put("personalHistory",recordsBean.getData().personalHistory +"");
                        jsonObject.put("familyHistory",recordsBean.getData().familyHistory +"");
                        jsonObject.put("takeMedicine",CommonExtKt.join2String(finalList));
                        jsonObject.put("allergyHistory",recordsBean.getData().allergyHistory +"");
                        jsonObject.put("behaviorCustom",recordsBean.getData().behaviorCustom + "");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    changeRecord(msAdapter, jsonObject.toString());

                }
                break;
            }
            case "行为习惯":{
                if(recordsBean.getData().getBehaviorCustom() == null || recordsBean.getData().getBehaviorCustom().isEmpty()){
                    recordsBean.getData().setBehaviorCustom(CommonExtKt.join2String(finalList));
                    recordsBean.getData().setOwnerId(Integer.parseInt(userBean.getOwnerId()));
                    recordsBean.getData().setUserId(userBean.getUserId());
                    JSONObject jsonObject = new JSONObject();
                    try {
                        //jsonObject.put("id",recordsBean.getData().id +"");
                        jsonObject.put("userId",recordsBean.getData().getUserId() +"");
                        jsonObject.put("familyHistory",recordsBean.getData().familyHistory +"");
                        jsonObject.put("personalHistory",recordsBean.getData().personalHistory +"");
                        jsonObject.put("behaviorCustom",CommonExtKt.join2String(finalList));
                    jsonObject.put("takeMedicine",recordsBean.getData().takeMedicine +"");
                        jsonObject.put("allergyHistory",recordsBean.getData().allergyHistory +"");
                        jsonObject.put("takeMedicine",recordsBean.getData().takeMedicine+"");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    insertRecords(msAdapter, jsonObject.toString());
                }else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        //jsonObject.put("id",recordsBean.getData().id +"");
                        jsonObject.put("id",recordsBean.getData().getId() +"");
                        jsonObject.put("familyHistory",recordsBean.getData().familyHistory +"");
                        jsonObject.put("personalHistory",recordsBean.getData().personalHistory +"");
                        jsonObject.put("behaviorCustom",CommonExtKt.join2String(finalList));
                        jsonObject.put("allergyHistory",recordsBean.getData().allergyHistory +"");
                        jsonObject.put("takeMedicine",recordsBean.getData().takeMedicine+"");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                   changeRecord(msAdapter, jsonObject.toString());
                }
                break;
            }
            case "过敏史":{
                if(recordsBean.getData().getAllergyHistory() == null ||  recordsBean.getData().getAllergyHistory().isEmpty()){
                    recordsBean.getData().setAllergyHistory(CommonExtKt.join2String(finalList));
                    recordsBean.getData().setOwnerId(Integer.parseInt(userBean.getOwnerId()));
                    recordsBean.getData().setUserId(userBean.getUserId());
                    JSONObject jsonObject = new JSONObject();
                    try {
                        //jsonObject.put("id",recordsBean.getData().id +"");
                        jsonObject.put("userId",recordsBean.getData().getUserId() +"");
                        jsonObject.put("familyHistory",recordsBean.getData().familyHistory +"");
                        jsonObject.put("personalHistory",recordsBean.getData().personalHistory +"");
                        jsonObject.put("behaviorCustom",recordsBean.getData().behaviorCustom +"");
                        jsonObject.put("takeMedicine",recordsBean.getData().takeMedicine +"");
                        jsonObject.put("allergyHistory",CommonExtKt.join2String(finalList));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    insertRecords(msAdapter, jsonObject.toString());
                }else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        //jsonObject.put("id",recordsBean.getData().id +"");
                        jsonObject.put("id",recordsBean.getData().getId() +"");
                        jsonObject.put("familyHistory",recordsBean.getData().familyHistory +"");
                        jsonObject.put("personalHistory",recordsBean.getData().personalHistory +"");
                        jsonObject.put("behaviorCustom",recordsBean.getData().behaviorCustom +"");
                        jsonObject.put("takeMedicine",recordsBean.getData().takeMedicine +"");
                        jsonObject.put("allergyHistory",CommonExtKt.join2String(finalList));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    changeRecord(msAdapter, jsonObject.toString());

                }
                break;
            }
        }
//        if (healthRecordBean.getFatherName().equals("个人病史"))
//            recordsBean.getData().setPersonalHistory(StringUtils.join(finalList, ","));
//
//        if (healthRecordBean.getFatherName().equals("家族病史"))
//            recordsBean.getData().setFamilyHistory(StringUtils.join(finalList, ","));
//        if (healthRecordBean.getFatherName().equals("服用药物"))
//            recordsBean.getData().setTakeMedicine(StringUtils.join(finalList, ","));
//        if (healthRecordBean.getFatherName().equals("行为习惯"))
//            recordsBean.getData().setBehaviorCustom(StringUtils.join(finalList, ","));
//        if (healthRecordBean.getFatherName().equals("过敏史"))
//            recordsBean.getData().setAllergyHistory(StringUtils.join(finalList, ","));

//        if(recordsBean.getData().getId() == 0){
//            recordsBean.getData().setOwnerId(Integer.parseInt(userBean.getOwnerId()));
//            recordsBean.getData().setUserId(userBean.getUserId());
//
//            return;
//        }

    }

    //新增
    private void insertRecords(MsAdapter<String> msAdapter, String s){
        //new Gson().toJson(recordsBean.getData())
        //新增
        OkHttpUtil.getInstance().doPost(API.records, s, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                getRecordInfo();
                runOnUiThread(msAdapter::notifyDataSetChanged);

            }
        });
    }

    private void changeRecord(MsAdapter<String> msAdapter, String json){
        //new Gson().toJson(recordsBean)
        //修改
        OkHttpUtil.getInstance().doPut(API.records, json, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                getRecordInfo();
                runOnUiThread(msAdapter::notifyDataSetChanged);

            }
        });
    }

    private void getDataList() {
        OkHttpUtil.getInstance().doGet(API.physicalRemindList, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    PhysicalRemindBean physicalRemindBean = new Gson().fromJson(response.body().string(), PhysicalRemindBean.class);

                    if (physicalRemindBean.getCode() == 200) {
                        List<String> second = new ArrayList<>();

                        physicalRemindBean.getRows().forEach(item -> second.add(item.getPhysicalName()));

                        second.add("add");

                        if(healthRecords.size() != 6){
                            healthRecords.add(new HealthRecordBean("体检项目", false, second));
                        }


                        runOnUiThread(() -> fatherMsAdapter.notifyDataSetChanged());
                    }
                }
            }
        });
    }

    static class HealthRecordBean {
        private String fatherName;

        private boolean status;

        private List<String> childList;

        public HealthRecordBean(String fatherName, boolean status, List<String> childList) {
            this.fatherName = fatherName;
            this.status = status;
            this.childList = childList;
        }

        public String getFatherName() {
            return fatherName;
        }

        public void setFatherName(String fatherName) {
            this.fatherName = fatherName;
        }

        public List<String> getChildList() {
            return childList;
        }

        public void setChildList(List<String> childList) {
            this.childList = childList;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }
    }


    public class PhysicalRemindBean {

        private int total;
        private List<RowsDTO> rows;
        private int code;
        private String msg;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<RowsDTO> getRows() {
            return rows;
        }

        public void setRows(List<RowsDTO> rows) {
            this.rows = rows;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public class RowsDTO {
            private String createBy;
            private String createTime;
            private String updateBy;
            private String updateTime;
            private String remark;
            private String id;
            private String userId;
            private String physicalName;
            private String remindType;
            private String status;
            private String beginTime;
            private String remindTime;

            public String getCreateBy() {
                return createBy;
            }

            public void setCreateBy(String createBy) {
                this.createBy = createBy;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getUpdateBy() {
                return updateBy;
            }

            public void setUpdateBy(String updateBy) {
                this.updateBy = updateBy;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getPhysicalName() {
                return physicalName;
            }

            public void setPhysicalName(String physicalName) {
                this.physicalName = physicalName;
            }

            public String getRemindType() {
                return remindType;
            }

            public void setRemindType(String remindType) {
                this.remindType = remindType;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getBeginTime() {
                return beginTime;
            }

            public void setBeginTime(String beginTime) {
                this.beginTime = beginTime;
            }

            public String getRemindTime() {
                return remindTime;
            }

            public void setRemindTime(String remindTime) {
                this.remindTime = remindTime;
            }
        }
    }


    public static class RecordsBean {

        private String msg;
        private int code;
        private DataDTO data;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public DataDTO getData() {
            return data;
        }

        public void setData(DataDTO data) {
            this.data = data;
        }

        static class DataDTO {
            private int id;
            private int userId;
            private int ownerId;
            private String ownerName;
            private String ownerSex;
            private String ownerAge;
            private String ownerBedNum;
            private String personalHistory;
            private String familyHistory;
            private String behaviorCustom;
            private String takeMedicine;
            private String allergyHistory;


            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getOwnerId() {
                return ownerId;
            }

            public void setOwnerId(int ownerId) {
                this.ownerId = ownerId;
            }

            public String getOwnerName() {
                return ownerName;
            }

            public void setOwnerName(String ownerName) {
                this.ownerName = ownerName;
            }

            public String getOwnerSex() {
                return ownerSex;
            }

            public void setOwnerSex(String ownerSex) {
                this.ownerSex = ownerSex;
            }

            public String getOwnerAge() {
                return ownerAge;
            }

            public void setOwnerAge(String ownerAge) {
                this.ownerAge = ownerAge;
            }

            public String getOwnerBedNum() {
                return ownerBedNum;
            }

            public void setOwnerBedNum(String ownerBedNum) {
                this.ownerBedNum = ownerBedNum;
            }

            public String getPersonalHistory() {
                return personalHistory;
            }

            public void setPersonalHistory(String personalHistory) {
                this.personalHistory = personalHistory;
            }

            public String getFamilyHistory() {
                return familyHistory;
            }

            public void setFamilyHistory(String familyHistory) {
                this.familyHistory = familyHistory;
            }

            public String getBehaviorCustom() {
                return behaviorCustom;
            }

            public void setBehaviorCustom(String behaviorCustom) {
                this.behaviorCustom = behaviorCustom;
            }

            public String getTakeMedicine() {
                return takeMedicine;
            }

            public void setTakeMedicine(String takeMedicine) {
                this.takeMedicine = takeMedicine;
            }

            public String getAllergyHistory() {
                return allergyHistory;
            }

            public void setAllergyHistory(String allergyHistory) {
                this.allergyHistory = allergyHistory;
            }
        }
    }

}