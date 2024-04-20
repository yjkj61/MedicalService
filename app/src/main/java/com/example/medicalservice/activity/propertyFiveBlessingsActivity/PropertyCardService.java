package com.example.medicalservice.activity.propertyFiveBlessingsActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.ProperTypeBean;
import com.example.medicalservice.bean.TestBean;
import com.example.medicalservice.databinding.ActivityPropertyCardServiceBinding;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.OkHttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PropertyCardService extends BaseActivity<ActivityPropertyCardServiceBinding> {

    private String type;

    List<ProperTypeBean> properTypeBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        super.initView();
        type = getIntent().getStringExtra("msg");
        viewBinding.back.setOnClickListener(v -> finish());

        Log.d("TAG", "initView: " + type);


//        if (type.equals("card1")) {
//            beans.add(new Bean(R.mipmap.housekeeping, getString(R.string.housekeeping)));
//            beans.add(new Bean(R.mipmap.laundry_washing, getString(R.string.laundry_washing)));
//            beans.add(new Bean(R.mipmap.appliance_cleaning, getString(R.string.appliance_cleaning)));
//            beans.add(new Bean(R.mipmap.appliance_repair, getString(R.string.appliance_repair)));
//            beans.add(new Bean(R.mipmap.home_repairs, getString(R.string.home_repairs)));
//            beans.add(new Bean(R.mipmap.hydroelectric_installation, getString(R.string.hydroelectric_installation)));
//            beans.add(new Bean(R.mipmap.water_electricity_repairs, getString(R.string.water_electricity_repairs)));
//            beans.add(new Bean(R.mipmap.pipeline_dredging, getString(R.string.pipeline_dredging)));
//        }
//
//        if (type.equals("card2")) {
//            beans.add(new Bean(R.mipmap.housekeeping, "助浴服务"));
//            beans.add(new Bean(R.mipmap.door_haircut, getString(R.string.door_haircut)));
//            beans.add(new Bean(R.mipmap.massage_treatments, getString(R.string.massage_treatments)));
//            beans.add(new Bean(R.mipmap.life_delivery, getString(R.string.life_delivery)));
//            beans.add(new Bean(R.mipmap.outside_activity, getString(R.string.outside_activity)));
//            beans.add(new Bean(R.mipmap.psychological_counseling, getString(R.string.psychological_counseling)));
//            beans.add(new Bean(R.mipmap.door_care, getString(R.string.door_care)));
//
//        }
//
//        if (type.equals("card3")) {
//            beans.add(new Bean(R.mipmap.travel_with_accompaniment, getString(R.string.travel_with_accompaniment)));
//            beans.add(new Bean(R.mipmap.outside_activity, "叫车出行"));
//            beans.add(new Bean(R.mipmap.express_errands, getString(R.string.express_errands)));
//            beans.add(new Bean(R.mipmap.accompany_the_doctor_to_purchase_medicines, getString(R.string.accompany_the_doctor_to_purchase_medicines)));
//        }


        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fTypeId",type);

            OkHttpUtil.getInstance().doPost(API.selectAllSecondTypesOwnerFirstTypeByFTypeId, jsonObject.toString(), new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {


                    if(response.body()!=null){

                        JsonArray jsonArray = JsonParser.parseString(response.body().string()).getAsJsonArray();
                        Gson gson = new Gson();
                        for (JsonElement jsonElement : jsonArray) {
                            ProperTypeBean properTypeBean = gson.fromJson(jsonElement, ProperTypeBean.class);
                            properTypeBeans.add(properTypeBean);
                        }

                        runOnUiThread(() -> {
                            MsAdapter<ProperTypeBean> msAdapter = new MsAdapter<ProperTypeBean>(properTypeBeans, R.layout.property_card_service_item) {

                                @Override
                                public void bindView(ViewHolder holder, ProperTypeBean obj) {

                                    TextView name = holder.getView(R.id.name);
                                    ImageView image = holder.getView(R.id.image);

                                    name.setText(obj.getSTypeName());

                                    GlideUtils.load(holder.getItemView().getContext(),obj.getsTypeLogo(),image);
                                    //image.setImageResource(obj.getImage());

                                    holder.getItemView().setOnClickListener(v -> {
                                        if (obj.getFTypeName().equals("叫车出行")) {
                                            go(CardServiceDetailMap.class);
                                        } else {
                                            go(CardServiceDetail.class,obj.getSTypeName(), String.valueOf(obj.getSTypeId()));
                                        }
                                    });

                                }
                            };

                            viewBinding.list.setAdapter(msAdapter);
                        });


                    }
                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }





    }

}