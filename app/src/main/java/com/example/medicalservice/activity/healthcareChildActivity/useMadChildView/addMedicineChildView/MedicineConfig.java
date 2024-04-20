package com.example.medicalservice.activity.healthcareChildActivity.useMadChildView.addMedicineChildView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.MedicinalUnit;
import com.example.medicalservice.bean.UseMedHistoryBean;
import com.example.medicalservice.databinding.ActivityMedicineConfigBinding;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.DataEdit;
import com.example.medicalservice.tools.OkHttpUtil;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MedicineConfig extends BaseActivity<ActivityMedicineConfigBinding> {


    private List<TypeBean> typeBeans = new ArrayList<>();

    private JSONObject medInfo;
    private String medUnit = "";

    public static MedicineConfig mActivity;
    private UseMedHistoryBean.DataDTO rowsDTO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        super.initView();
        mActivity = this;
        rowsDTO = DataEdit.getInstance().getRowsDTO();
        if (rowsDTO != null) {
            medUnit = rowsDTO.getMedicinalUnit();
            viewBinding.medNumber.setText(rowsDTO.getMedicinalNumber() + "");
        }
        try {
            medInfo = new JSONObject(Objects.requireNonNull(getIntent().getStringExtra("msg")));


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        viewBinding.back.setOnClickListener(v -> finish());


        viewBinding.nextStep.setOnClickListener(v -> {

            if (viewBinding.medNumber.getText().toString().equals("")) {
                showToast("请输入药物数量");
                return;
            }

            try {
                medInfo.put("number", viewBinding.medNumber.getText().toString());
                medInfo.put("unit", medUnit);
                go(FrequencyAdministration.class, medInfo.toString());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        });


    }


    @Override
    public void initData() {
        super.initData();
        getMedicinalUnitList();
    }


    private void getMedicinalUnitList() {
        OkHttpUtil.getInstance().doGet(API.medicinalUnitList, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() != null) {
                    MedicinalUnit medicinalTypeBean = new Gson().fromJson(response.body().string(), MedicinalUnit.class);
                    for (MedicinalUnit.RowsDTO type : medicinalTypeBean.getRows()) {

                        typeBeans.add(new TypeBean(type.getMedicinalUnit(), medicinalTypeBean.getRows().indexOf(type) == 0));

                        if (medicinalTypeBean.getRows().indexOf(type) == 0) {
                            if (DataEdit.getInstance().getRowsDTO() == null) {
                                medUnit = type.getMedicinalUnit();
                            }

                        }
                    }

                    runOnUiThread(() -> viewBinding.unit.post(() -> {


                        MsAdapter msAdapter = new MsAdapter<TypeBean>(typeBeans, R.layout.med_config_item) {

                            @Override
                            public void bindView(ViewHolder holder, TypeBean obj) {
                                MaterialCardView box = holder.getView(R.id.box);


                                TextView text = holder.getView(R.id.text);
                                text.setText(obj.name);


                                if (obj.status) select(box);

                                if (!obj.status) unSelect(box);

                                box.setOnClickListener(v -> {

                                    typeBeans.forEach(item -> {

                                        if (item.name.equals(obj.name)) {
                                            item.status = true;
                                            medUnit = obj.name;
                                        } else {
                                            item.status = false;
                                        }

                                    });
                                    notifyDataSetChanged();
                                });

                            }
                        };


                        viewBinding.unit.setAdapter(msAdapter);
                    })
                    );
                }
            }
        });

    }

    private void select(MaterialCardView cardView) {
        cardView.setCardBackgroundColor(Color.parseColor("#DED6FC"));
        cardView.setStrokeColor(Color.parseColor("#6C63E5"));
    }

    private void unSelect(MaterialCardView cardView) {
        cardView.setCardBackgroundColor(Color.parseColor("#F6F2FC"));
        cardView.setStrokeColor(Color.parseColor("#6C63E5"));
    }

    static class TypeBean {
        private String name;
        private boolean status = false;

        public TypeBean(String name, boolean status) {
            this.name = name;
            this.status = status;
        }
    }
}