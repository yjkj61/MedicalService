package com.example.medicalservice.activity.healthcareChildActivity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.MedicalHistoryBean;
import com.example.medicalservice.databinding.ActivityMedicalHistoryBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MedicalHistory extends BaseActivity<ActivityMedicalHistoryBinding> {

    private String [] first = {"高血压","高血压","高血压","高血压","高血压","高血压","高血压","高血压","高血压","高血压"};
    private String [] second = {"胰腺炎","胰腺炎","胰腺炎","胰腺炎","胰腺炎","胰腺炎","胰腺炎","胰腺炎","胰腺炎","胰腺炎"};
    private String [] third = {"哮喘","哮喘","哮喘","哮喘","哮喘","哮喘","哮喘","哮喘","哮喘","哮喘"};

    private List<MedicalHistoryBean> medicalHistoryBeans = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void initData() {
        super.initData();
        String type = getIntent().getStringExtra("msg");

        if(Objects.equals(type, "first")){
            viewBinding.gridBox.setBackgroundResource(R.drawable.medical_history_back);
            for (String s : first) {
                MedicalHistoryBean medicalHistoryBean = new MedicalHistoryBean();
                medicalHistoryBean.setCheck(false);
                medicalHistoryBean.setName(s);
                medicalHistoryBeans.add(medicalHistoryBean);

            }

        }
        if(Objects.equals(type, "second")){
            viewBinding.gridBox.setBackgroundResource(R.drawable.medical_history_back2);

            for (String s : second) {
                MedicalHistoryBean medicalHistoryBean = new MedicalHistoryBean();
                medicalHistoryBean.setCheck(false);
                medicalHistoryBean.setName(s);
                medicalHistoryBeans.add(medicalHistoryBean);

            }
        }
        if(Objects.equals(type, "third")){
            viewBinding.gridBox.setBackgroundResource(R.drawable.medical_history_back3);

            for (String s : third) {
                MedicalHistoryBean medicalHistoryBean = new MedicalHistoryBean();
                medicalHistoryBean.setCheck(false);
                medicalHistoryBean.setName(s);
                medicalHistoryBeans.add(medicalHistoryBean);

            }
        }

        MsAdapter msAdapter = new MsAdapter<MedicalHistoryBean>(medicalHistoryBeans,R.layout.medical_history_item){

            @Override
            public void bindView(ViewHolder holder, MedicalHistoryBean obj) {

                TextView name = holder.getView(R.id.name);
                RadioButton radioButton = holder.getView(R.id.checkbox);

                name.setText(obj.getName());
                radioButton.setChecked(obj.getCheck());
            }
        };

        viewBinding.medicalGrid.setAdapter(msAdapter);
        msAdapter.notifyDataSetChanged();


        viewBinding.next.setOnClickListener(v -> {
            if(Objects.equals(type, "first")){
                go(MedicalHistory.class,"second");
            }

            if(Objects.equals(type, "second")){
                go(MedicalHistory.class,"third");
            }

            if(Objects.equals(type, "third")){

            }
        });

        viewBinding.last.setOnClickListener(v -> finish());

    }


}