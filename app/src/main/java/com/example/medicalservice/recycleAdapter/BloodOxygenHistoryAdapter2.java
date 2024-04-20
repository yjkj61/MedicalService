package com.example.medicalservice.recycleAdapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.R;
import com.example.medicalservice.bean.AcidListBean;
import com.example.medicalservice.bean.BloodFatBean;
import com.example.medicalservice.bean.BloodPressureListBean;
import com.example.medicalservice.bean.BloodSugarListBean;
import com.example.medicalservice.bean.CholesterolListBean;
import com.example.medicalservice.bean.OxygenListBean;
import com.example.medicalservice.bean.RateListBean;
import com.example.medicalservice.bean.TemperatureListBean;

import java.util.List;

public class BloodOxygenHistoryAdapter2<T> extends RecyclerView.Adapter<BloodOxygenHistoryAdapter2.ViewHolder> {


    private List<T> testBeans;

    private String type = "";

    public BloodOxygenHistoryAdapter2(List<T> testBeans) {
        this.testBeans = testBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blood_ox_history_item2, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.count.setText(position+1+"");

        if (type.equals("xueyang")) {
            OxygenListBean.RowsDTO testBean = (OxygenListBean.RowsDTO) testBeans.get(position);
            holder.date.setText(testBean.getHBloodOxygenTime());

            holder.value.setText(testBean.getHRespiratoryRate());


            if (Integer.parseInt(testBean.getHRespiratoryRate()) > 95) {
                holder.status.setBackgroundResource(R.drawable.good_back);
                holder.status.setText("理想血氧");
            }

            if (Integer.parseInt(testBean.getHRespiratoryRate()) < 95) {
                holder.status.setBackgroundResource(R.drawable.bad_back);
                holder.status.setText("低血氧");
            }
        }

        if (type.equals("xuetang")) {
            BloodSugarListBean.RowsDTO testBean = (BloodSugarListBean.RowsDTO) testBeans.get(position);
            holder.date.setText(testBean.getHBloodSugarTime());
            float xuetang_vlaue_1 = Float.parseFloat(testBean.getHBloodSugarValue());
            holder.value.setText(testBean.getHBloodSugarValue());


            if (xuetang_vlaue_1 < 2.8) {
                holder.status.setBackgroundResource(R.drawable.bad_back);
                holder.status.setText("低血糖");
            } else if (xuetang_vlaue_1 < 6.1) {
                holder.status.setBackgroundResource(R.drawable.good_back);
                holder.status.setText("理想血糖");


            } else if (xuetang_vlaue_1 < 7) {
                holder.status.setBackgroundResource(R.drawable.middle_back);
                holder.status.setText("正常血糖");
            } else if (xuetang_vlaue_1 < 8.5) {
                holder.status.setBackgroundResource(R.drawable.middle_back);
                holder.status.setText("正常血糖");
            } else if (xuetang_vlaue_1 < 11.1) {
                holder.status.setBackgroundResource(R.drawable.bad_back);
                holder.status.setText("低血糖");
            } else {
                holder.status.setBackgroundResource(R.drawable.bad_back);
                holder.status.setText("低血糖");
            }

        }

        if (type.equals("xueya")) {


            BloodPressureListBean.RowsDTO testBean = (BloodPressureListBean.RowsDTO) testBeans.get(position);
            holder.date.setText(testBean.getHBloodPressureTime());
            holder.value.setText(testBean.getHHypertension() + "/" + testBean.getHHypotension() + "mmHg");

            float value_real_gaoya = Float.parseFloat(testBean.getHHypertension());
            float value_real_diya = Float.parseFloat(testBean.getHHypotension());

            if (value_real_gaoya < 90 || value_real_diya < 60) {
                holder.status.setBackgroundResource(R.drawable.bad_back);
                holder.status.setText("低血压");
            } else if (value_real_gaoya < 120 || value_real_diya < 80) {
                holder.status.setBackgroundResource(R.drawable.good_back);
                holder.status.setText("理想血压");
            } else if (value_real_gaoya < 130 || value_real_diya < 85) {
                holder.status.setBackgroundResource(R.drawable.good_back);
                holder.status.setText("理想血压");

            } else if (value_real_gaoya < 140 || value_real_diya < 90) {
                holder.status.setBackgroundResource(R.drawable.middle_back);
                holder.status.setText("正常血压");

            } else if (value_real_gaoya < 160 || value_real_diya < 100) {
                holder.status.setBackgroundResource(R.drawable.middle_back);
                holder.status.setText("正常血压");
            } else if (value_real_gaoya < 180 || value_real_diya < 110) {
                holder.status.setBackgroundResource(R.drawable.bad_back);
                holder.status.setText("高血压");
            } else {
                holder.status.setBackgroundResource(R.drawable.bad_back);
                holder.status.setText("高血压");
            }

        }



        if (type.equals("cholesterol")) {

            CholesterolListBean.RowsDTO testBean = (CholesterolListBean.RowsDTO) testBeans.get(position);
            holder.date.setText(testBean.getHCholesterolTime());


            holder.value.setText(testBean.getHCholesterolContent());

            float zongdan_value = Float.parseFloat(testBean.getHCholesterolContent());
            if (zongdan_value > 5.17) {
                holder.status.setBackgroundResource(R.drawable.bad_back);
                holder.status.setText("高胆固醇");
            } else {
                holder.status.setBackgroundResource(R.drawable.good_back);
                holder.status.setText("理想胆固醇");
            }
        }

        if (type.equals("temperature")) {
            TemperatureListBean.RowsDTO rowsDTO = (TemperatureListBean.RowsDTO) testBeans.get(position);
            holder.date.setText(rowsDTO.getHTemperatureRateTime());
            holder.value.setText(rowsDTO.getHTemperatureValue());
            float value = Float.parseFloat(rowsDTO.getHTemperatureValue());

            if (value > 37.4) {
                holder.status.setBackgroundResource(R.drawable.bad_back);
                holder.status.setText("体温高");
            } else {
                holder.status.setBackgroundResource(R.drawable.good_back);
                holder.status.setText("体温正常");
            }
        }

        if (type.equals("acid")) {
            AcidListBean.RowsDTO rowsDTO = (AcidListBean.RowsDTO) testBeans.get(position);
            holder.date.setText(rowsDTO.getHUricAcidTime());
            holder.value.setText(rowsDTO.getHUricAcidValue());

            if(rowsDTO.getHUricAcidValue()== null){
                rowsDTO.setHUricAcidValue("0");
            }

            float value = Float.parseFloat(rowsDTO.getHUricAcidValue());


            if (value < 416) {
                holder.status.setBackgroundResource(R.drawable.good_back);
                holder.status.setText("理想尿酸");
            } else {
                holder.status.setBackgroundResource(R.drawable.bad_back);
                holder.status.setText("尿酸异常");
            }
        }



        if(type.equals("rate")){
            RateListBean.RowsDTO cholesterolListBean = (RateListBean.RowsDTO) testBeans.get(position);
            holder.date.setText(cholesterolListBean.getHHeartRateTime());
            holder.value.setText(cholesterolListBean.getHHeartRateValue());


            if (Integer.parseInt(cholesterolListBean.getHHeartRateValue()) >120) {
                holder.status.setBackgroundResource(R.drawable.bad_back);
                holder.status.setText("心率过高");
            } else {
                holder.status.setBackgroundResource(R.drawable.good_back);
                holder.status.setText("心率正常");
            }
        }


        if (type.equals("totalCholesterol")) {

            BloodFatBean.RowsDTO bloodFatBean = (BloodFatBean.RowsDTO) testBeans.get(position);
            holder.date.setText(bloodFatBean.getHTgChohdlldlTime());


            holder.value.setText(bloodFatBean.getHTcValue());

            float zongdan_value = Float.parseFloat(bloodFatBean.getHTcValue());
            if (zongdan_value > 5.17) {
                holder.status.setBackgroundResource(R.drawable.bad_back);
                holder.status.setText("总胆固醇超标");

            } else {
                holder.status.setBackgroundResource(R.drawable.good_back);
                holder.status.setText("总胆固醇完美");

            }
        }

        if(type.equals("glycerol")){
            BloodFatBean.RowsDTO bloodFatBean = (BloodFatBean.RowsDTO) testBeans.get(position);
            holder.date.setText(bloodFatBean.getHTgChohdlldlTime());
            holder.value.setText(bloodFatBean.getHTriglycerideValue());

            float ganyou_value = Float.parseFloat(bloodFatBean.getHTriglycerideValue());

            if (ganyou_value < 1.7) {
                holder.status.setBackgroundResource(R.drawable.good_back);
                holder.status.setText("甘油三酯完美");

            } else {
                holder.status.setBackgroundResource(R.drawable.bad_back);
                holder.status.setText("甘油三酯超标");

            }

        }

        if (type.equals("highDensityLipoprotein")) {
            BloodFatBean.RowsDTO bloodFatBean = (BloodFatBean.RowsDTO) testBeans.get(position);
            holder.date.setText(bloodFatBean.getHTgChohdlldlTime());
            holder.value.setText(bloodFatBean.getHHdlValue());

            float gaomi_value = Float.parseFloat(bloodFatBean.getHHdlValue());

            if (gaomi_value < 0.96) {
                holder.status.setBackgroundResource(R.drawable.bad_back);
                holder.status.setText("高密度脂蛋白超标");

            } else if (gaomi_value < 1.56) {
                holder.status.setBackgroundResource(R.drawable.middle_back);
                holder.status.setText("高密度脂蛋白正常");

            } else {
                holder.status.setBackgroundResource(R.drawable.good_back);
                holder.status.setText("高密度脂蛋白完美");

            }

        }

        if(type.equals("lowDensityLipoprotein")){
            BloodFatBean.RowsDTO bloodFatBean = (BloodFatBean.RowsDTO) testBeans.get(position);
            holder.date.setText(bloodFatBean.getHTgChohdlldlTime());

            holder.value.setText(bloodFatBean.getHLdlValue());

            float dimi_value = Float.parseFloat(bloodFatBean.getHLdlValue());
            if (dimi_value < 2.1) {
                holder.status.setBackgroundResource(R.drawable.good_back);
                holder.status.setText("低密度脂蛋白完美");

            } else if (dimi_value < 3.1) {
                holder.status.setBackgroundResource(R.drawable.middle_back);
                holder.status.setText("低密度脂蛋白正常");

            } else {
                holder.status.setBackgroundResource(R.drawable.bad_back);
                holder.status.setText("低密度脂蛋白超标");

            }
        }


    }


    public void setShowType(String type) {
        this.type = type;
    }

    @Override
    public int getItemCount() {
        return testBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView count, date, value;
        TextView status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            count = itemView.findViewById(R.id.count);
            date = itemView.findViewById(R.id.date);
            value = itemView.findViewById(R.id.value);
            status = itemView.findViewById(R.id.status);

        }
    }

}
