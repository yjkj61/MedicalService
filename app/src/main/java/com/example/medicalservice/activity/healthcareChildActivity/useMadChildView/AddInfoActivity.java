package com.example.medicalservice.activity.healthcareChildActivity.useMadChildView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.UseMedHistoryBean;
import com.example.medicalservice.databinding.ActivityAddInfoBinding;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.OkHttpUtil;
import com.example.medicalservice.tools.StringUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//添加用药记录
public class AddInfoActivity extends BaseActivity<ActivityAddInfoBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        super.initView();
        viewBinding.back.setOnClickListener(v -> finish());
        viewBinding.addMad.setOnClickListener(v -> go(AddMadActivity.class));
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
                        useMedHistoryBean.getData().removeIf(rowsDTO -> !rowsDTO.getRemindType().equals("2"));

                        runOnUiThread(() -> {
                            MsAdapter msAdapter = new MsAdapter<UseMedHistoryBean.DataDTO>(useMedHistoryBean.getData(), R.layout.need_med_item) {
                                @Override
                                public void bindView(ViewHolder holder, UseMedHistoryBean.DataDTO rowsDTO) {



                                    TextView name = holder.getView(R.id.name);

                                    TextView unit = holder.getView(R.id.unit);


                                    name.setText(rowsDTO.getMedicinalName());

                                    unit.setText(rowsDTO.getCreateTime().substring(11,16)+"用药"+rowsDTO.getMedicinalNumber()+rowsDTO.getMedicinalUnit());


                                    TextView btn = holder.getView(R.id.btn);

                                    if(rowsDTO.getStatus().equals("0")){
                                        btn.setBackgroundResource(R.drawable.need_med_item_use_back);
                                    }else {
                                        btn.setBackgroundResource(R.drawable.need_med_item_nouse_back);
                                    }

                                    btn.setOnClickListener(v -> {
                                        rowsDTO.setStatus(rowsDTO.getStatus().equals("0") ? "1" : "0");
                                        OkHttpUtil.getInstance().doPut(API.medicinalRemind, new Gson().toJson(rowsDTO), new Callback() {
                                            @Override
                                            public void onFailure(@NonNull Call call1, @NonNull IOException e) {

                                            }

                                            @Override
                                            public void onResponse(@NonNull Call call1, @NonNull Response response1) throws IOException {
                                                if (response1.body() != null) {
                                                    try {
                                                        JSONObject jsonObject = new JSONObject(response1.body().string());
                                                        if (jsonObject.getInt("code") == 200) {
                                                            runOnUiThread(() -> {
                                                                notifyDataSetChanged();
                                                                showToast("操作成功");
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


                                }
                            };

                            viewBinding.allHistory.setAdapter(msAdapter);
                        });
                    }
                }
            }
        });
    }

}