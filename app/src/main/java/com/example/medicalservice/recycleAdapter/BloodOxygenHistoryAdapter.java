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
import com.example.medicalservice.activity.healthcareChildActivity.BloodOxygen;
import com.example.medicalservice.bean.AcidListBean;
import com.example.medicalservice.bean.BloodFatBean;
import com.example.medicalservice.bean.BloodPressureListBean;
import com.example.medicalservice.bean.BloodSugarListBean;
import com.example.medicalservice.bean.CholesterolListBean;
import com.example.medicalservice.bean.OxygenListBean;
import com.example.medicalservice.bean.RateListBean;
import com.example.medicalservice.bean.TemperatureListBean;
import com.example.medicalservice.bean.TestBean;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;

import java.util.List;

public class BloodOxygenHistoryAdapter<T> extends RecyclerView.Adapter<BloodOxygenHistoryAdapter.ViewHolder> {


    private List<T> testBeans;

    private String type = "";

    public BloodOxygenHistoryAdapter(List<T> testBeans) {
        this.testBeans = testBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blood_ox_history_item, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



        if (type.equals("xueyang")) {
            OxygenListBean.RowsDTO testBean = (OxygenListBean.RowsDTO) testBeans.get(position);
            holder.date.setText(testBean.getHBloodOxygenTime());

            holder.value.setText(testBean.getHRespiratoryRate());

            holder.unit.setText("SaO2");

            if (Integer.parseInt(testBean.getHRespiratoryRate()) > 95) {
                holder.status.setCardBackgroundColor(Color.parseColor("#FFB8DCBC"));
            }

            if (Integer.parseInt(testBean.getHRespiratoryRate()) < 95) {
                holder.status.setCardBackgroundColor(Color.RED);
            }
        }

        if (type.equals("xuetang")) {
            BloodSugarListBean.RowsDTO testBean = (BloodSugarListBean.RowsDTO) testBeans.get(position);
            holder.date.setText(testBean.getHBloodSugarTime());
            float xuetang_vlaue_1 = Float.parseFloat(testBean.getHBloodSugarValue());
            holder.unit.setText("mmol/L");
            holder.value.setText(testBean.getHBloodSugarValue());


            if (xuetang_vlaue_1 < 2.8) {
                holder.status.setCardBackgroundColor(Color.RED);
            } else if (xuetang_vlaue_1 < 6.1) {
                holder.status.setCardBackgroundColor(Color.parseColor("#FFB8DCBC"));

            } else if (xuetang_vlaue_1 < 7) {
                holder.status.setCardBackgroundColor(Color.YELLOW);
            } else if (xuetang_vlaue_1 < 8.5) {
                holder.status.setCardBackgroundColor(Color.YELLOW);
            } else if (xuetang_vlaue_1 < 11.1) {
                holder.status.setCardBackgroundColor(Color.RED);
            } else {
                holder.status.setCardBackgroundColor(Color.RED);
            }

        }

        if (type.equals("xueya")) {


            BloodPressureListBean.RowsDTO testBean = (BloodPressureListBean.RowsDTO) testBeans.get(position);
            holder.date.setText(testBean.getHBloodPressureTime());
            holder.value.setText(testBean.getHHypertension() + "/" + testBean.getHHypotension() + "mmHg");
            holder.unit.setText("SaO2");

            float value_real_gaoya = Float.parseFloat(testBean.getHHypertension());
            float value_real_diya = Float.parseFloat(testBean.getHHypotension());

            if (value_real_gaoya < 90 || value_real_diya < 60) {
                holder.status.setCardBackgroundColor(Color.RED);
            } else if (value_real_gaoya < 120 || value_real_diya < 80) {
                holder.status.setCardBackgroundColor(Color.parseColor("#FFB8DCBC"));
            } else if (value_real_gaoya < 130 || value_real_diya < 85) {
                holder.status.setCardBackgroundColor(Color.parseColor("#FFB8DCBC"));
            } else if (value_real_gaoya < 140 || value_real_diya < 90) {
                holder.status.setCardBackgroundColor(Color.YELLOW);
            } else if (value_real_gaoya < 160 || value_real_diya < 100) {
                holder.status.setCardBackgroundColor(Color.YELLOW);
            } else if (value_real_gaoya < 180 || value_real_diya < 110) {
                holder.status.setCardBackgroundColor(Color.RED);
            } else {
                holder.status.setCardBackgroundColor(Color.RED);
            }

        }

        if (type.equals("temperature")) {
            TemperatureListBean.RowsDTO rowsDTO = (TemperatureListBean.RowsDTO) testBeans.get(position);
            holder.date.setText(rowsDTO.getHTemperatureRateTime());
            holder.unit.setText("°C");
            holder.value.setText(rowsDTO.getHTemperatureValue());
            float value = Float.parseFloat(rowsDTO.getHTemperatureValue());

            if (value > 37.4) {
                holder.status.setCardBackgroundColor(Color.RED);
            } else {
                holder.status.setCardBackgroundColor(Color.parseColor("#FFB8DCBC"));
            }
        }

        if (type.equals("acid")) {
            AcidListBean.RowsDTO rowsDTO = (AcidListBean.RowsDTO) testBeans.get(position);
            holder.date.setText(rowsDTO.getHUricAcidTime());
            holder.unit.setText("μmol/L");
            holder.value.setText(rowsDTO.getHUricAcidValue());

            float value = Float.parseFloat(rowsDTO.getHUricAcidValue());


            if (value < 416) {
                holder.status.setCardBackgroundColor(Color.parseColor("#FFB8DCBC"));
            } else {
                holder.status.setCardBackgroundColor(Color.RED);
            }
        }



        if(type.equals("rate")){
            RateListBean.RowsDTO cholesterolListBean = (RateListBean.RowsDTO) testBeans.get(position);
            holder.date.setText(cholesterolListBean.getHHeartRateTime());

            holder.value.setText(cholesterolListBean.getHHeartRateValue());

            holder.unit.setText("次/分钟");

            if (Integer.parseInt(cholesterolListBean.getHHeartRateValue()) >120) {
                holder.status.setCardBackgroundColor(Color.RED);
            } else {
                holder.status.setCardBackgroundColor(Color.parseColor("#FFB8DCBC"));
            }
        }

        if (type.equals("cholesterol")) {

            CholesterolListBean.RowsDTO testBean = (CholesterolListBean.RowsDTO) testBeans.get(position);
            holder.date.setText(testBean.getHCholesterolTime());
            holder.unit.setText("mmol/L");

            holder.value.setText(testBean.getHCholesterolContent());

            float zongdan_value = Float.parseFloat(testBean.getHCholesterolContent());
            if (zongdan_value > 5.17) {
                holder.status.setCardBackgroundColor(Color.RED);
            } else {
                holder.status.setCardBackgroundColor(Color.parseColor("#FFB8DCBC"));
            }
        }
        if (type.equals("totalCholesterol")) {

            BloodFatBean.RowsDTO bloodFatBean = (BloodFatBean.RowsDTO) testBeans.get(position);
            holder.date.setText(bloodFatBean.getHTgChohdlldlTime());
            holder.unit.setText("mmol/L");

            holder.value.setText(bloodFatBean.getHTcValue());

            float zongdan_value = Float.parseFloat(bloodFatBean.getHTcValue());
            if (zongdan_value > 5.17) {
                holder.status.setCardBackgroundColor(Color.RED);
            } else {
                holder.status.setCardBackgroundColor(Color.parseColor("#FFB8DCBC"));
            }
        }

        if(type.equals("glycerol")){
            BloodFatBean.RowsDTO bloodFatBean = (BloodFatBean.RowsDTO) testBeans.get(position);
            holder.date.setText(bloodFatBean.getHTgChohdlldlTime());
            holder.unit.setText("mmol/L");
            holder.value.setText(bloodFatBean.getHTriglycerideValue());

            float ganyou_value = Float.parseFloat(bloodFatBean.getHTriglycerideValue());

            if (ganyou_value < 1.7) {
                holder.status.setCardBackgroundColor(Color.parseColor("#FFB8DCBC"));
            } else {
                holder.status.setCardBackgroundColor(Color.RED);
            }

        }

        if (type.equals("highDensityLipoprotein")) {
            BloodFatBean.RowsDTO bloodFatBean = (BloodFatBean.RowsDTO) testBeans.get(position);
            holder.date.setText(bloodFatBean.getHTgChohdlldlTime());
            holder.unit.setText("mmol/L");
            holder.value.setText(bloodFatBean.getHHdlValue());

            float gaomi_value = Float.parseFloat(bloodFatBean.getHHdlValue());

            if (gaomi_value < 0.96) {
                holder.status.setCardBackgroundColor(Color.RED);
            } else if (gaomi_value < 1.56) {
                holder.status.setCardBackgroundColor(Color.YELLOW);
            } else {
                holder.status.setCardBackgroundColor(Color.parseColor("#FFB8DCBC"));
            }

        }

        if(type.equals("lowDensityLipoprotein")){
            BloodFatBean.RowsDTO bloodFatBean = (BloodFatBean.RowsDTO) testBeans.get(position);
            holder.date.setText(bloodFatBean.getHTgChohdlldlTime());
            holder.unit.setText("mmol/L");

            holder.value.setText(bloodFatBean.getHLdlValue());

            float dimi_value = Float.parseFloat(bloodFatBean.getHLdlValue());
            if (dimi_value < 2.1) {
                holder.status.setCardBackgroundColor(Color.parseColor("#FFB8DCBC"));

            } else if (dimi_value < 3.1) {
                holder.status.setCardBackgroundColor(Color.YELLOW);
            } else {
                holder.status.setCardBackgroundColor(Color.RED);
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

        TextView time, date, value, unit;
        CardView status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            value = itemView.findViewById(R.id.value);
            status = itemView.findViewById(R.id.status);
            unit = itemView.findViewById(R.id.unit);
        }
    }

}
