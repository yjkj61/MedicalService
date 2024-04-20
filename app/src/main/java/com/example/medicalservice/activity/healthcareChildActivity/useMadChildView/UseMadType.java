package com.example.medicalservice.activity.healthcareChildActivity.useMadChildView;

import androidx.annotation.NonNull;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.R;
import com.example.medicalservice.activity.healthcareChildActivity.useMadChildView.addMedicineChildView.MedicineConfig;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.MedicinalTypeBean;
import com.example.medicalservice.bean.UseMedHistoryBean;
import com.example.medicalservice.databinding.ActivityUseMadTypeBinding;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//选取用药类型
public class UseMadType extends BaseActivity<ActivityUseMadTypeBinding> {

    //private String[] types = {};

    private List<TypeBean> typeBeans = new ArrayList<>();

    String medName = "";
    String medType = "";

    String url = "";

    public static UseMadType mActivity;

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
        if(rowsDTO!=null){
            medType = rowsDTO.getMedicinalType();
        }

        medName = getIntent().getStringExtra("msg");
        url = getIntent().getStringExtra("msg2");

        viewBinding.back.setOnClickListener(v -> finish());

        viewBinding.nextStep.setOnClickListener(v -> {

            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("name", medName);
                jsonObject.put("type", medType);
                jsonObject.put("img",url);

                go(MedicineConfig.class, jsonObject.toString());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        });
    }

    @Override
    public void initData() {
        super.initData();
        getMedTypeList();


    }

    private void getMedTypeList() {
        OkHttpUtil.getInstance().doGet(API.medicinalTypeList, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() != null) {
                    MedicinalTypeBean medicinalTypeBean = new Gson().fromJson(response.body().string(), MedicinalTypeBean.class);
                    for (MedicinalTypeBean.RowsDTO type : medicinalTypeBean.getRows()) {

                        typeBeans.add(new TypeBean(type.getMedicinalType(), medicinalTypeBean.getRows().indexOf(type) == 0));
                        if(medicinalTypeBean.getRows().indexOf(type) == 0){
                            if(DataEdit.getInstance().getRowsDTO()==null){
                                medType = type.getMedicinalType();
                            }

                        }
                    }



                    runOnUiThread(() -> viewBinding.configList.post(() -> {
                       // int width = viewBinding.configList.getMeasuredWidth() / 3 - 60;

                        MsAdapter msAdapter = new MsAdapter<TypeBean>(typeBeans, R.layout.med_config_item) {

                            @Override
                            public void bindView(ViewHolder holder, TypeBean obj) {
                                MaterialCardView box = holder.getView(R.id.box);

                                TextView text = holder.getView(R.id.text);
                                text.setText(obj.name);


                                if (obj.status) select(box);

                                if (!obj.status) unSelect(box);

                                box.setOnClickListener(v -> {

                                    typeBeans.forEach(item -> item.status = false);

                                    obj.setStatus(true);
                                    medType = obj.name;


                                    notifyDataSetChanged();
                                });

                            }
                        };

                        viewBinding.configList.setAdapter(msAdapter);
                    }));
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }
    }
}