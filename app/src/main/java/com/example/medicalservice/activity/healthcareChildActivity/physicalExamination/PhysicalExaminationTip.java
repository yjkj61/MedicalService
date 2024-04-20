package com.example.medicalservice.activity.healthcareChildActivity.physicalExamination;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.activity.healthcareChildActivity.HealthRecords;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.databinding.ActivitySingleTipBinding;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.DataEdit;
import com.example.medicalservice.tools.OkHttpUtil;
import com.example.medicalservice.tools.StringUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PhysicalExaminationTip extends BaseActivity<ActivitySingleTipBinding> {

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
    protected void onResume() {
        super.onResume();
        DataEdit.getInstance().setDataDTO(null);
        getDataList();
    }

    private void getDataList() {
        OkHttpUtil.getInstance().doGet(API.physicalRemindAndroidListByUserId, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    PhysicalBean physicalRemindBean = new Gson().fromJson(response.body().string(), PhysicalBean.class);

                    if (physicalRemindBean.getCode() == 200) {


                        runOnUiThread(() -> {
                            MsAdapter msAdapter = new MsAdapter<PhysicalBean.DataDTO>(physicalRemindBean.getData(), R.layout.single_tip_grid_item) {
                                @Override
                                public void bindView(ViewHolder holder, PhysicalBean.DataDTO rowsDTO) {

                                    TextView name = holder.getView(R.id.name);
                                    TextView physical_frequency = holder.getView(R.id.physical_frequency);
                                    TextView timeShow = holder.getView(R.id.times);
                                    TextView edit = holder.getView(R.id.edit);
                                    TextView cancel = holder.getView(R.id.cancel);

                                    TextView delete = holder.getView(R.id.delete);
                                    LinearLayout box = holder.getView(R.id.box);

                                    name.setText(rowsDTO.getPhysicalName());

                                    timeShow.setText(StringUtils.join((ArrayList<String>) rowsDTO.getPhysicalTimeList(), ","));
                                    cancel.setText(rowsDTO.getStatus().equals("0") ? "取消提醒" : "开启提醒");


                                    box.setBackgroundResource(rowsDTO.getStatus().equals("0")?R.drawable.back1:R.drawable.back3);

                                    switch (rowsDTO.getRemindType()) {
                                        case "0":

                                            physical_frequency.setText("每" + rowsDTO.getRemindTime() + "天");

                                            if (rowsDTO.getRemindTime().equals("1")) {
                                                physical_frequency.setText("每天");
                                            }

                                            if (rowsDTO.getRemindTime().equals("2")) {
                                                physical_frequency.setText("隔一天");
                                            }

                                            break;
                                        case "1":

                                            String[] times = rowsDTO.getRemindTime().split(",");

                                            StringBuilder showTime = new StringBuilder();
                                            for (String time :
                                                    times) {
                                                showTime.append("每周").append(time);
                                            }
                                            physical_frequency.setText(showTime.toString());
                                            break;

                                        case "2":
                                            physical_frequency.setText("按需");
                                    }

                                    edit.setOnClickListener(v -> {
                                        DataEdit.getInstance().setDataDTO(rowsDTO);
                                        go(AddPhysicalExamination.class);

                                    });

                                    cancel.setOnClickListener(v -> {
                                        rowsDTO.setStatus(rowsDTO.getStatus().equals("0") ? "1" : "0");
                                        editStatus(rowsDTO);
                                        notifyDataSetChanged();
                                    });

                                    delete.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            deleteDialog(String.valueOf(rowsDTO.getId()));

                                        }
                                    });

                                }
                            };

                            viewBinding.list.setAdapter(msAdapter);
                        });


                    }
                }
            }
        });
    }

    private void editStatus(PhysicalBean.DataDTO rowsDTO){
        OkHttpUtil.getInstance().doPut(API.physicalRemind, new Gson().toJson(rowsDTO), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getInt("code") == 200) {
                            runOnUiThread(() -> {
                                showToast("修改成功");
                            });
                        } else {
                            runOnUiThread(() -> showToast("请求失败"));
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private void deleteDialog(String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Dialog_style);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_delete, null);

        builder.setView(view);
        TextView left_btn, right_btn, text;
        left_btn = view.findViewById(R.id.yes);
        right_btn = view.findViewById(R.id.no);
        text = view.findViewById(R.id.tip_text);


        text.setText("是否删除");

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


        left_btn.setOnClickListener(v1 -> OkHttpUtil.getInstance().doDelete(API.physicalRemindAndroidRemove(id), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {


                runOnUiThread(() -> {
                    alertDialog.dismiss();
                    getDataList();
                });

            }
        }));

        right_btn.setOnClickListener(v1 -> alertDialog.dismiss());


    }

    public class PhysicalBean implements Serializable {

        private String msg;
        private int code;
        private List<DataDTO> data;

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

        public List<DataDTO> getData() {
            return data;
        }

        public void setData(List<DataDTO> data) {
            this.data = data;
        }

        public class DataDTO implements Serializable {
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
            private List<String> physicalTimeList;

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

            public List<String> getPhysicalTimeList() {
                return physicalTimeList;
            }

            public void setPhysicalTimeList(List<String> physicalTimeList) {
                this.physicalTimeList = physicalTimeList;
            }
        }
    }
}