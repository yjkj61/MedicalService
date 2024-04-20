package com.example.medicalservice.activity.cateringServicesActivity.Order;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.activity.cateringServicesActivity.AddressMangerCatering;
import com.example.medicalservice.activity.cateringServicesActivity.EatCar1;
import com.example.medicalservice.activity.cateringServicesActivity.EatCar2;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.AddressCatering;
import com.example.medicalservice.bean.CarFoodListBean;
import com.example.medicalservice.bean.Code200;
import com.example.medicalservice.dataBaseBean.UserBean;
import com.example.medicalservice.databinding.ActivityPayOrderBinding;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.Count;
import com.example.medicalservice.tools.GetOrderForShop;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.OkHttpUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PayOrderActivity extends BaseActivity<ActivityPayOrderBinding> {


    private List<CarFoodListBean> foodList;

    private UserBean userBean;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();

        if (!GetOrderForShop.getInstance().getAddress().isEmpty()) {
            viewBinding.address.setText(GetOrderForShop.getInstance().getAddress());
        }

        if (!GetOrderForShop.getInstance().getName().isEmpty()) {
            viewBinding.name.setText("联系人：" + GetOrderForShop.getInstance().getName());
        }
        if (!GetOrderForShop.getInstance().getPhoneNumber().isEmpty()) {
            viewBinding.phone.setText("联系方式：" + GetOrderForShop.getInstance().getPhoneNumber());
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initView() {
        super.initView();

        userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);


        viewBinding.selectAddress.setOnClickListener(v -> go(AddressMangerCatering.class, "select"));


        foodList = (List<CarFoodListBean>) getIntent().getSerializableExtra("foodList");

        double packMoney = 0;
        for (CarFoodListBean bean : foodList
        ) {
            packMoney = Count.add(packMoney, Count.mul(bean.getNumber(), bean.getrFoodPackingCharge()));
        }

        viewBinding.packMoney.setText(String.valueOf(packMoney));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        viewBinding.list.setLayoutManager(linearLayoutManager);


        viewBinding.list.setAdapter(new Adapter(foodList));


        viewBinding.totalMoney.setText("¥" + Count.add(Double.parseDouble(getIntent().getStringExtra("totalMoney")), packMoney));

        viewBinding.submit.setOnClickListener(v -> {
            if(viewBinding.address.getText().toString().isEmpty()){
                showToast("请填写收货地址后，再下单！");
                return;
            }

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userid", MyApplication.getInstance().getUserId());
                jsonObject.put("ownerId", userBean.getOwnerId());
                jsonObject.put("rFoodCanteenId", foodList.size() > 0 ? foodList.get(0).getrFoodCanteenId() : "");
                jsonObject.put("rOrderAddressId", GetOrderForShop.getInstance().getAddressId());


                JSONArray jsonArray = new JSONArray();

                for (CarFoodListBean bean : foodList
                ) {
                    JSONObject orderInfoList = new JSONObject();
                    orderInfoList.put("rFoodId", bean.getId());
                    orderInfoList.put("rOrderCount", bean.getNumber());
                    jsonArray.put(orderInfoList);
                }

                jsonObject.put("orderInfoList", jsonArray);

                jsonObject.put("markerId", MyApplication.getInstance().getMarkId());
                jsonObject.put("markerName", MyApplication.getInstance().getMarkName());

                OkHttpUtil.getInstance().doPost(API.placeOrder, jsonObject.toString(), new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        runOnUiThread(() -> showToast("请求失败"));

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        if (response.body() != null) {

                            Code200 code200 = new Gson().fromJson(response.body().string(), Code200.class);

                            if (code200 != null && code200.getCode() == 200) {
                                runOnUiThread(() -> {
                                    showToast("下单成功");
                                    go(OrderListActivity.class);

                                    if (EatCar1.mActivity != null) {
                                        EatCar1.mActivity.finish();
                                    }
                                    if (EatCar2.mActivity != null) {
                                        EatCar2.mActivity.finish();
                                    }

                                    finish();
                                });
                            }else if(code200.getCode() == -1){
                                runOnUiThread(() -> showToast("您的余额不足，请先充值！"));
                            } else {
                                runOnUiThread(() -> showToast("请求失败，请联系管理员"));
                            }


                        }
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }


        });

        getAddressId();
    }

    private void getAddressId() {
        GetOrderForShop.getInstance().setAddress("");
        GetOrderForShop.getInstance().setPhoneNumber("");
        GetOrderForShop.getInstance().setName("");
        OkHttpUtil.getInstance().doGet(API.addressList + "?ownerId=" + userBean.getOwnerId(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                activity.runOnUiThread(() -> showToast("请求失败"));
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    AddressCatering addressListBean = new Gson().fromJson(response.body().string(), AddressCatering.class);
                    if (addressListBean.getCode() != 200) {
                        activity.runOnUiThread(() -> showToast("请求失败"));
                        return;
                    }

                    for (AddressCatering.RowsDTO bean : addressListBean.getRows()
                    ) {

                        if (bean.getRFoodIsDefault() == 0) {
                            GetOrderForShop.getInstance().setAddressId(String.valueOf(bean.getId()));
                            runOnUiThread(() -> {
                                viewBinding.address.setText(bean.getRFoodLocation() + bean.getRFoodDetailedAddress());

                                viewBinding.name.setText("联系人：" + bean.getRFoodRecipientName());
                                viewBinding.phone.setText("联系方式：" + bean.getRFoodPhoneNumber());
                            });
                        }
                    }

                    if (addressListBean.getRows().size() == 0) {

                        runOnUiThread(() -> {
                            viewBinding.name.setText("联系人：" + MyApplication.getInstance().getUserName());
                            viewBinding.phone.setText("联系方式：" + MyApplication.getInstance().getPhoneNumber());
                            viewBinding.address.setText("请点击箭头选择地址");
                        });

                    }


                }


            }
        });
    }

    static class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


        List<CarFoodListBean> foodList;

        public Adapter(List<CarFoodListBean> foodList) {
            this.foodList = foodList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pay_order_list_item, parent, false);

            return new ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            CarFoodListBean carFoodListBean = this.foodList.get(position);

            GlideUtils.load(holder.itemView.getContext(), carFoodListBean.getImageUrl(), holder.image, R.drawable.good_test, 20);
            holder.name.setText(carFoodListBean.getName());
            holder.number.setText("x" + carFoodListBean.getNumber());
            holder.money.setText(String.valueOf(carFoodListBean.getSinglePrice()));


        }

        @Override
        public int getItemCount() {
            return this.foodList.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            private final ImageView image;
            private final TextView name;
            private final TextView number;
            private final TextView money;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
                name = itemView.findViewById(R.id.name);
                number = itemView.findViewById(R.id.number);
                money = itemView.findViewById(R.id.money);
            }
        }
    }
}