package com.example.medicalservice.activity.healthcareChildActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.medicalservice.R;
import com.example.medicalservice.activity.healthcareChildActivity.physicalExamination.AddPhysicalExamination;
import com.example.medicalservice.activity.healthcareChildActivity.physicalExamination.SelectPhysicalExamination;
import com.example.medicalservice.activity.healthcareChildActivity.useMadChildView.AddMadActivity;
import com.example.medicalservice.activity.healthcareChildActivity.useMadChildView.UseMadType;
import com.example.medicalservice.activity.healthcareChildActivity.useMadChildView.addMedicineChildView.FrequencyAdministration;
import com.example.medicalservice.activity.healthcareChildActivity.useMadChildView.addMedicineChildView.MedicineConfig;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.databinding.ActivityTipTimeCommentBinding;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.DataEdit;
import com.example.medicalservice.tools.OkHttpUtil;
import com.example.medicalservice.tools.StringUtils;

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

public class TipTimeComment extends BaseActivity<ActivityTipTimeCommentBinding> {

    private String requestData;

    private List<String> timeList;

    private Adapter adapter;

    private String type;

    @Override
    public void initView() {
        super.initView();
        timeList = new ArrayList<>();

        if (DataEdit.getInstance().getRowsDTO() != null) {
            timeList.addAll(DataEdit.getInstance().getRowsDTO().getUseMedicinalTime());
        }

        if(DataEdit.getInstance().getDataDTO() != null){
            if(DataEdit.getInstance().getDataDTO().getPhysicalTimeList() != null) timeList.addAll(DataEdit.getInstance().getDataDTO().getPhysicalTimeList());
        }

        requestData = getIntent().getStringExtra("msg");
        type = getIntent().getStringExtra("msg2");

        viewBinding.titleText.setText(type.equals("med")?"选择用药时间":"选择体检时间");

        adapter = new Adapter(timeList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        viewBinding.timeList.setLayoutManager(linearLayoutManager);

        viewBinding.timeList.setAdapter(adapter);
    }

    @Override
    public void initData() {
        super.initData();

        viewBinding.addTime.setOnClickListener(v -> {
            Calendar startCal = Calendar.getInstance();
            Calendar sal = Calendar.getInstance();

            startCal.set(1998, 0, 1, 0, 0, 0);
            TimePickerView pvTime = new TimePickerBuilder(activity, (date, v1) -> {
                timeList.add(StringUtils.forDataTimeTime(date));
                adapter.notifyDataSetChanged();
            })
                    .setDate(sal).setRangDate(startCal, StringUtils.strForCalendar("2029-12-31 23:59:59"))//起始终止年月日设定// 如果不设置的话，默认是系统时间*/
                    .setType(new boolean[]{false, false, false, true, true, false})
                    .setCancelText("取消")
                    .setSubmitText("确定")

//                        .setSubmitColor(getResources().getColor(R.color.white))
//                        .setCancelColor(getResources().getColor(R.color.white))
//                        .setTitleBgColor(getResources().getColor(R.color.colorAccent))
//                        .setBgColor(getResources().getColor(R.color.bankuai))//滚轮背景颜色 Night mode
//                        .setDividerColor(getResources().getColor(R.color.bg2T55))//设置分割线的颜色
//                        .setTextColorCenter(getResources().getColor(R.color.black))//设置选中项的颜色
//                        .setTextColorOut(getResources().getColor(R.color.gray2))//设置没有被选中项的颜色
                    .build();
            pvTime.show();
        });



        viewBinding.nextStep.setOnClickListener(v -> {

            if (type.equals("med")) {
                if (DataEdit.getInstance().getRowsDTO() == null) {
                    setMedNewRequest();
                } else {
                    setMedEditRequest();
                }

            } else {
                if (DataEdit.getInstance().getDataDTO() == null) {
                    setPhysicalRequest();
                } else {
                    setPhysicalEditRequest();
                }

            }
        });
    }

    private void setMedNewRequest() {
        viewBinding.loading.setVisibility(View.VISIBLE);
        OkHttpUtil.getInstance().doPost(API.medicinalRemind, requestData, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getInt("code") == 200) {
                            String hMedicinalRemindId = jsonObject.getString("data");
                            JSONObject timeObject = new JSONObject();
                            JSONArray jsonArray = new JSONArray();
                            for (int i = 0; i < timeList.size(); i++) {
                                jsonArray.put(timeList.get(i) + ":00");
                            }
                            timeObject.put("hMedicinalRemindId", hMedicinalRemindId);
                            timeObject.put("useMedicinalTimeList", jsonArray);
                            OkHttpUtil.getInstance().doPost(API.medicinalRemindTimeAndroidAdd, timeObject.toString(), new Callback() {
                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                                }

                                @Override
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {


                                    if (response.body() != null) {
                                        try {
                                            if (new JSONObject(response.body().string()).getInt("code") == 200) {
                                                runOnUiThread(() -> {
                                                    viewBinding.loading.setVisibility(View.GONE);
                                                    FrequencyAdministration.mActivity.finish();
                                                    MedicineConfig.mActivity.finish();
                                                    UseMadType.mActivity.finish();
                                                    AddMadActivity.mActivity.finish();

                                                    finish();
                                                });

                                            }
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }
                            });
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                }
            }
        });
    }

    private void setMedEditRequest() {
        viewBinding.loading.setVisibility(View.VISIBLE);
        OkHttpUtil.getInstance().doPut(API.medicinalRemind, requestData, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getInt("code") == 200) {

                            JSONObject timeObject = new JSONObject();
                            JSONArray jsonArray = new JSONArray();
                            for (int i = 0; i < timeList.size(); i++) {
                                jsonArray.put(timeList.get(i) + ":00");
                            }
                            timeObject.put("hMedicinalRemindId", DataEdit.getInstance().getRowsDTO().getId());
                            timeObject.put("useMedicinalTimeList", jsonArray);

                            OkHttpUtil.getInstance().doPut(API.medicinalRemindTimeAndroidEdit, timeObject.toString(), new Callback() {
                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                                }

                                @Override
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {


                                    if (response.body() != null) {
                                        try {
                                            if (new JSONObject(response.body().string()).getInt("code") == 200) {
                                                runOnUiThread(() -> {
                                                    viewBinding.loading.setVisibility(View.GONE);
                                                    DataEdit.getInstance().setRowsDTO(null);
                                                    FrequencyAdministration.mActivity.finish();
                                                    MedicineConfig.mActivity.finish();
                                                    UseMadType.mActivity.finish();
                                                    AddMadActivity.mActivity.finish();

                                                    finish();
                                                });

                                            } else {
                                                runOnUiThread(() -> {
                                                    showToast("请求失败");
                                                    viewBinding.loading.setVisibility(View.GONE);
                                                });
                                            }
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }
                            });
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                }
            }
        });
    }

    private void setPhysicalRequest() {
        viewBinding.loading.setVisibility(View.VISIBLE);
        OkHttpUtil.getInstance().doPost(API.physicalRemind, requestData, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getInt("code") == 200) {
                            String hPhysicalRemindId = jsonObject.getString("data");
                            JSONObject timeObject = new JSONObject();
                            JSONArray jsonArray = new JSONArray();
                            for (int i = 0; i < timeList.size(); i++) {
                                jsonArray.put(timeList.get(i) + ":00");
                            }
                            timeObject.put("hPhysicalRemindId", hPhysicalRemindId);

                            timeObject.put("physicalTimeList", jsonArray);
                            OkHttpUtil.getInstance().doPost(API.physicalRemindTime, timeObject.toString(), new Callback() {
                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                                }

                                @Override
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {


                                    if (response.body() != null) {
                                        try {
                                            if (new JSONObject(response.body().string()).getInt("code") == 200) {
                                                runOnUiThread(() -> {
                                                    viewBinding.loading.setVisibility(View.GONE);
                                                    SelectPhysicalExamination.mActivity.finish();
                                                    AddPhysicalExamination.mActivity.finish();

                                                    finish();
                                                });

                                            }
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }
                            });
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                }
            }
        });
    }

    private void setPhysicalEditRequest() {
        viewBinding.loading.setVisibility(View.VISIBLE);
        OkHttpUtil.getInstance().doPut(API.physicalRemind, requestData, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getInt("code") == 200) {


                            JSONObject timeObject = new JSONObject();
                            JSONArray jsonArray = new JSONArray();
                            for (int i = 0; i < timeList.size(); i++) {
                                jsonArray.put(timeList.get(i) + ":00");
                            }
                            timeObject.put("hPhysicalRemindId", DataEdit.getInstance().getDataDTO().getId());

                            timeObject.put("physicalTimeList", jsonArray);
                            OkHttpUtil.getInstance().doPut(API.physicalRemindTimeAndroidEdit, timeObject.toString(), new Callback() {
                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                                }

                                @Override
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {


                                    if (response.body() != null) {
                                        try {
                                            if (new JSONObject(response.body().string()).getInt("code") == 200) {
                                                runOnUiThread(() -> {
                                                    viewBinding.loading.setVisibility(View.GONE);
                                                    DataEdit.getInstance().setDataDTO(null);
                                                    SelectPhysicalExamination.mActivity.finish();
                                                    AddPhysicalExamination.mActivity.finish();

                                                    finish();
                                                });

                                            }
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }
                            });
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                }
            }
        });


    }

    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        List<String> timeList;

        public Adapter(List<String> timeList) {
            this.timeList = timeList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ttip_time_comment_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.time.setText(timeList.get(position));

            holder.delete.setOnClickListener(v -> {
                timeList.remove(position);
                notifyDataSetChanged();
            });
        }

        @Override
        public int getItemCount() {
            return timeList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView time;
            private ImageView delete;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                time = itemView.findViewById(R.id.time);
                delete = itemView.findViewById(R.id.delete);
            }
        }
    }

}