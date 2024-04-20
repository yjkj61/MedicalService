package com.example.medicalservice.activity.healthcareChildActivity.useMadChildView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.UseMedHistoryBean;
import com.example.medicalservice.databinding.ActivityAllMadBinding;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.DataEdit;
import com.example.medicalservice.tools.OkHttpUtil;
import com.example.medicalservice.tools.StringUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AllMadActivity extends BaseActivity<ActivityAllMadBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        super.initView();
        DataEdit.getInstance().setRowsDTO(null);
        viewBinding.back.setOnClickListener(v -> finish());
        viewBinding.addMad.setOnClickListener(v -> {
            DataEdit.getInstance().setRowsDTO(null);
            go(AddMadActivity.class);

        });

        getAllHistory();
    }

    private void getAllHistory() {
        OkHttpUtil.getInstance().doGet(API.medicinalRemindAndroidListByUserId, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    UseMedHistoryBean useMedHistoryBean = new Gson().fromJson(response.body().string(), UseMedHistoryBean.class);


                    if (useMedHistoryBean.getCode() == 200) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MsAdapter msAdapter = new MsAdapter<UseMedHistoryBean.DataDTO>(useMedHistoryBean.getData(), R.layout.mad_grid_iem) {
                                    @Override
                                    public void bindView(ViewHolder holder, UseMedHistoryBean.DataDTO rowsDTO) {
                                        CardView edit_plan = holder.getView(R.id.edit_plan);
                                        CardView cancel_tip = holder.getView(R.id.cancel_tip);
                                        TextView cancel_text = holder.getView(R.id.cancel_text);

                                        LinearLayout box = holder.getView(R.id.box);
                                        CardView delete_tip = holder.getView(R.id.delete_tip);

                                        ImageView image = holder.getView(R.id.image);

                                        TextView name = holder.getView(R.id.name);
                                        TextView medicinalNumber = holder.getView(R.id.medicinalNumber);
                                        TextView tip_next_time = holder.getView(R.id.tip_next_time);
                                        TextView frequency_administration = holder.getView(R.id.frequency_administration);
                                        TextView time_administration = holder.getView(R.id.time_administration);

                                        List<String> useMedicinalTimes = new ArrayList<>();
                                        if (rowsDTO.getUseMedicinalTime() != null) {
                                            for (int i = 0; i < rowsDTO.getUseMedicinalTime().size(); i++) {
                                                useMedicinalTimes.add(rowsDTO.getUseMedicinalTime().get(i).substring(0, 5));
                                            }
                                            rowsDTO.setUseMedicinalTime(useMedicinalTimes);

                                        }

                                        if (rowsDTO.getUseMedicinalTime() != null) {
                                            time_administration.setText(StringUtils.join((ArrayList<String>) rowsDTO.getUseMedicinalTime(), ","));

                                        }


                                        box.setBackgroundResource(rowsDTO.getStatus().equals("0") ? R.drawable.back1 : R.drawable.back3);

                                        cancel_text.setText(rowsDTO.getStatus().equals("0") ? "取消提醒" : "开启提醒");

                                        GlideUtils.load(activity, rowsDTO.getImg(), image);

                                        name.setText(rowsDTO.getMedicinalName());
                                        medicinalNumber.setText(rowsDTO.getMedicinalNumber() + rowsDTO.getMedicinalUnit() + "/次");


                                        switch (rowsDTO.getRemindType()) {
                                            case "0":

                                                frequency_administration.setText("每" + rowsDTO.getRemindTime() + "天");

                                                if (rowsDTO.getRemindTime().equals("1")) {
                                                    frequency_administration.setText("每天");
                                                }

                                                if (rowsDTO.getRemindTime().equals("2")) {
                                                    frequency_administration.setText("隔一天");
                                                }
                                                tip_next_time.setText(rowsDTO.getStatus().equals("0") ? rowsDTO.getNextRemindTime() : "已取消");

                                                break;
                                            case "1":

                                                String[] times = rowsDTO.getRemindTime().split(",");

                                                StringBuilder showTime = new StringBuilder();
                                                for (String time :
                                                        times) {
                                                    showTime.append("每周").append(time);
                                                }
                                                frequency_administration.setText(showTime.toString());
                                                tip_next_time.setText(rowsDTO.getStatus().equals("0") ? rowsDTO.getNextRemindTime() : "已取消");
                                                break;

                                            case "2":
                                                frequency_administration.setText("按需");
                                                tip_next_time.setText(rowsDTO.getStatus().equals("0") ? "按需" : "已取消");
                                        }


                                        edit_plan.setOnClickListener(v -> {
                                            Intent intent = new Intent(activity, AddMadActivity.class);
                                            DataEdit.getInstance().setRowsDTO(rowsDTO);
                                            startActivity(intent);
                                        });

                                        cancel_tip.setOnClickListener(v -> {
                                            rowsDTO.setStatus(rowsDTO.getStatus().equals("0") ? "1" : "0");
                                            OkHttpUtil.getInstance().doPut(API.medicinalRemind, new Gson().toJson(rowsDTO), new Callback() {
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
                                                                    notifyDataSetChanged();
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

                                        });

                                        delete_tip.setOnClickListener(v -> {
                                            deleteDialog(String.valueOf(rowsDTO.getId()));
                                        });

                                    }
                                };

                                viewBinding.allHistory.setAdapter(msAdapter);
                            }
                        });
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


        left_btn.setOnClickListener(v1 -> OkHttpUtil.getInstance().doDelete(API.medicinalRemindAndroidRemove(id), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                Log.d("TAG", API.medicinalRemindAndroidRemove(id)+response.body().string());

                runOnUiThread(() -> {
                    alertDialog.dismiss();
                    getAllHistory();
                });

            }
        }));

        right_btn.setOnClickListener(v1 -> alertDialog.dismiss());


    }

}