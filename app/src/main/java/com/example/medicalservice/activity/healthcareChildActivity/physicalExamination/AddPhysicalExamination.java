package com.example.medicalservice.activity.healthcareChildActivity.physicalExamination;

import android.os.Bundle;
import android.view.View;

import com.example.medicalservice.activity.healthcareChildActivity.useMadChildView.AddMadActivity;
import com.example.medicalservice.activity.healthcareChildActivity.useMadChildView.UseMadType;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.databinding.ActivityAddPhysicalExaminationBinding;
import com.example.medicalservice.tools.DataEdit;

import org.json.JSONException;
import org.json.JSONObject;

public class AddPhysicalExamination extends BaseActivity<ActivityAddPhysicalExaminationBinding> {

    public static AddPhysicalExamination mActivity;


    private PhysicalExaminationTip.PhysicalBean.DataDTO dataDTO;

    @Override
    public void initView() {
        super.initView();

        mActivity = this;

        if(DataEdit.getInstance().getDataDTO()!= null){
            dataDTO = DataEdit.getInstance().getDataDTO();
            viewBinding.name.setText(dataDTO.getPhysicalName());
        }



        viewBinding.back.setOnClickListener(view -> finish());


        viewBinding.next.setOnClickListener(v -> {
            if (viewBinding.name.getText().toString().equals("")) {
                showToast("请输入体检项目名称");
                return;
            }

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name",viewBinding.name.getText().toString());

                go(SelectPhysicalExamination.class,jsonObject.toString());
            }catch (JSONException e){
                e.printStackTrace();
            }

        });
    }
}