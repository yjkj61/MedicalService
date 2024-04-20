package com.example.medicalservice.activity.shopChildView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.AddressListBean;
import com.example.medicalservice.bean.BuyGoodBean;
import com.example.medicalservice.bean.GoodDetailBean;
import com.example.medicalservice.bean.ShopCarBean;
import com.example.medicalservice.fragments.submitOrderFragment.SureOrderInfoFragment;
import com.example.medicalservice.interfaceCallback.OnClickListener;
import com.example.medicalservice.recycleAdapter.ShopCarOrderAdapter;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.Count;
import com.example.medicalservice.tools.GetOrderForShop;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.OkHttpUtil;

import androidx.annotation.NonNull;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.databinding.ActivityShoppingCartBinding;

import com.example.medicalservice.R;
import com.example.medicalservice.tools.StringUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShoppingCart extends BaseActivity<ActivityShoppingCartBinding> {

    //管理状态
    private boolean manager = false;
    private final List<ShopCarBean.RowsDTO> rowsDTOS = new ArrayList<>();

    private final List<ShopCarBean.RowsDTO.SShoppingCartVoListDTO> sShoppingCartVoListDTOS = new ArrayList<>();

    private ShopCarOrderAdapter orderAdapter;

    private boolean selectAll = false;

    private String contact;
    private String address;
    private String phoneNumber;
    private String addressId;

    private BuyGoodBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewBinding.manger.setOnClickListener(v -> {
            manager = !manager;

            viewBinding.payInfoBox.setVisibility(manager ? View.GONE : View.VISIBLE);

            viewBinding.bottomBtn1.setBackgroundResource(manager ? R.drawable.shop_car_button_back : R.drawable.shop_car_button_back2);

            viewBinding.bottomBtn2.setVisibility(manager ? View.VISIBLE : View.GONE);
            viewBinding.bottomBtn1Text.setText(manager ? "清理失效" : "去结算");
            viewBinding.bottomBtn2Text2.setText("立即删除");

            viewBinding.mangerText.setText(manager ? "退出" : "管理");

            viewBinding.manger.setBackgroundResource(manager ? R.drawable.my_fav_item_fav_num2 : R.drawable.my_fav_item_fav_num);

            viewBinding.mangerText.setTextColor(manager ? Color.parseColor("#ffffff") : Color.parseColor("#ff5656c1"));
        });

        viewBinding.bottomBtn1.setOnClickListener(v -> {
            if (manager) {
                viewBinding.passDateBox.setVisibility(View.VISIBLE);
                viewBinding.bottomBtn2Text2.setText("一键清理");
            }
        });

        viewBinding.bottomBtn2.setOnClickListener(v -> {
            if (manager && viewBinding.passDateBox.getVisibility() == View.GONE) {
                //删除购物车功能
                String ids = orderAdapter.shopCarIdList.stream().collect(Collectors.joining(",", "{", "}"));

                OkHttpUtil.getInstance().doDelete(API.shoppingDelete(ids), new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                    }
                });

            }

            if (manager && viewBinding.passDateBox.getVisibility() == View.VISIBLE) {
                //清理失效功能

                //String ids = orderAdapter.shopCarIdList.stream().collect(Collectors.joining(",","{","}"));

            }
        });

        viewBinding.close.setOnClickListener(v -> {
            viewBinding.passDateBox.setVisibility(View.GONE);
            viewBinding.bottomBtn2Text2.setText("立即删除");
        });

        viewBinding.bottomBtn2Text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewBinding.bottomBtn2Text2.getText().toString().equals("立即删除")) {

                    String url = API.shoppingCart + "/" + StringUtils.join((ArrayList<String>) orderAdapter.shopCarIdList, ",");

                    Log.d("TAG", "onClick: "+orderAdapter.shopCarIdList.size());

                    if(orderAdapter.shopCarIdList.size()==0){
                        showToast("未选中商品");
                        return;
                    }


                    OkHttpUtil.getInstance().doDelete(url, new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                            if (response.body() != null) {
                                String data = response.body().string();

                                Log.d("TAG", "onResponse: " + data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                showToast("删除成功");
                                                getGoodList();
                                            }
                                        });
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                            }

                        }
                    });
                }

            }
        });

        viewBinding.bottomBtn1Text.setOnClickListener(v -> {
            if (viewBinding.bottomBtn1Text.getText().toString().contains("去结算")) {
                List<BuyGoodBean> beans = new ArrayList<>();
                List<SureOrderInfoFragment.OrderGood> orderGoods = new ArrayList<>();

                for (ShopCarBean.RowsDTO shopCarBean :
                        rowsDTOS) {

                    for (ShopCarBean.RowsDTO.SShoppingCartVoListDTO sShoppingCartVoListDTO :
                            shopCarBean.getSShoppingCartVoList()) {

                        if (sShoppingCartVoListDTO.isCheck()) {
                            bean = new BuyGoodBean();
                            bean.setSGoodsSpecificationsId(String.valueOf(sShoppingCartVoListDTO.getSGoodsSpecificationsId()));
                            bean.setGoodsPrice(String.valueOf(sShoppingCartVoListDTO.getPrice()));

                            bean.setSGoodsBusinessId(String.valueOf(shopCarBean.getSGoodsBusinessId()));
                            bean.setSGoodsId(String.valueOf(sShoppingCartVoListDTO.getSGoodsId()));
                            bean.setGoodsNumber(String.valueOf(sShoppingCartVoListDTO.getNumber()));
                            bean.setSGoodsSpecificationsName(sShoppingCartVoListDTO.getSGoodsSpecificationsName());
                            bean.setOrderPrice(String.valueOf(Count.mul(sShoppingCartVoListDTO.getPrice(), sShoppingCartVoListDTO.getNumber())));
                            bean.setAddress(address);
                            bean.setContact(contact);
                            bean.setPhone(phoneNumber);
                            bean.setPayWay("0");
                            bean.setOrderSource("0");
                            beans.add(bean);


                            SureOrderInfoFragment.OrderGood orderGood = new SureOrderInfoFragment.OrderGood();
                            orderGood.setName(sShoppingCartVoListDTO.getGoodsName());
                            orderGood.setNumber(sShoppingCartVoListDTO.getNumber());
                            orderGood.setMoney(Double.parseDouble(bean.getGoodsPrice()));
                            orderGoods.add(orderGood);


                        }
                    }
                }


                if(beans.size()>0){
                    Intent intent = new Intent(activity, SubmitOrderView.class);
                    GetOrderForShop.getInstance().setAddressId(addressId);
                    GetOrderForShop.getInstance().setBuyGoodBeans(beans);

                    GetOrderForShop.getInstance().setGoods(orderGoods);

                    startActivity(intent);
                }else {
                    showToast("未选中任何商品");
                }


            }
        });


    }

    @Override
    public void initView() {
        super.initView();

        addressData();

        viewBinding.back.setOnClickListener(v -> finish());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        viewBinding.list.setLayoutManager(linearLayoutManager);


        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(activity);
        viewBinding.passDateList.setLayoutManager(linearLayoutManager1);


        viewBinding.selectAll.setOnClickListener(v -> {

            if (selectAll) {
                viewBinding.goodCheck.setImageResource(R.drawable.check);
                orderAdapter.shopCarIdList.clear();
                rowsDTOS.forEach(item -> {
                    item.setCheck(false);
                    item.getSShoppingCartVoList().forEach(child -> {
                        child.setCheck(false);
                    });
                });
                viewBinding.money.setText("0");
            } else {
                viewBinding.goodCheck.setImageResource(R.drawable.checked);
                AtomicReference<Double> money = new AtomicReference<>((double) 0);
                rowsDTOS.forEach(item -> {
                    item.setCheck(true);
                    item.getSShoppingCartVoList().forEach(child -> {
                        child.setCheck(true);
                        orderAdapter.shopCarIdList.add(child.getSShoppingCartId());
                        money.updateAndGet(v1 -> new Double(v1 + child.getPrice()));
                    });

                });
                viewBinding.money.setText(money.get() + "");
            }
            selectAll = !selectAll;

            orderAdapter.notifyDataSetChanged();
        });


    }

    @Override
    public void initData() {
        super.initData();

        orderAdapter = new ShopCarOrderAdapter(rowsDTOS,activity);
        viewBinding.list.setAdapter(orderAdapter);
        orderAdapter.setOnItemClickListener(new OnClickListener() {
            @Override
            public void OnItemClick(int position) {

                if (manager) {
                    return;
                }

                Log.d("TAG", "OnItemClick: " + position);

                int number = 0;
                AtomicReference<Double> money = new AtomicReference<>((double) 0);

                for (ShopCarBean.RowsDTO shopCarBean :
                        rowsDTOS) {

                    for (ShopCarBean.RowsDTO.SShoppingCartVoListDTO sShoppingCartVoListDTO :
                            shopCarBean.getSShoppingCartVoList()) {

                        if (sShoppingCartVoListDTO.isCheck()) {
                            number += 1;
                            money.updateAndGet(v1 -> v1 + Count.mul(sShoppingCartVoListDTO.getPrice(), sShoppingCartVoListDTO.getNumber()));
                        }
                    }
                }

                viewBinding.money.setText(money.get() + "");
                viewBinding.bottomBtn1Text.setText("去结算(" + number + ")");
            }
        });

        getGoodList();


    }

    private void addressData() {
        OkHttpUtil.getInstance().doGet(API.areaList, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                activity.runOnUiThread(() -> showToast("请求失败"));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    AddressListBean addressListBean = new Gson().fromJson(response.body().string(), AddressListBean.class);
                    if (addressListBean.getCode() != 200) {
                        activity.runOnUiThread(() -> showToast("请求失败"));
                        return;
                    }
                    addressListBean.getRows().forEach(item -> {

                        if (!GetOrderForShop.getInstance().getAddressId().isEmpty()) {
                            if (GetOrderForShop.getInstance().getAddressId().equals(String.valueOf(item.getSAreaId()))) {
                                address = item.getArea() + item.getAddress();
                                contact = item.getContact();
                                phoneNumber = item.getPhone();
                                addressId = String.valueOf(item.getSAreaId());

                            }

                        } else if (item.getIsDefaultAddress() == 1) {
                            address = item.getArea() + item.getAddress();
                            contact = item.getContact();
                            phoneNumber = item.getPhone();

                            addressId = String.valueOf(item.getSAreaId());

                        }
                    });


                }


            }
        });
    }

    private void getGoodList() {
        rowsDTOS.clear();
        OkHttpUtil.getInstance().doGet(API.shoppingCartList, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() != null) {
                    ShopCarBean sOrderPoListDTOS = new Gson().fromJson(response.body().string(), ShopCarBean.class);

                    if (sOrderPoListDTOS.getCode() == 200) {

                        rowsDTOS.addAll(sOrderPoListDTOS.getRows());

                        runOnUiThread(() -> orderAdapter.notifyDataSetChanged());
                    } else {
                        runOnUiThread(() -> showToast("请求失败,请联系管理员"));
                    }


                }


            }
        });
    }

    private void getPassDateGood() {
        sShoppingCartVoListDTOS.clear();
        OkHttpUtil.getInstance().doGet(API.shoppingCartList("1"), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() != null) {
                    ShopCarBean sOrderPoListDTOS = new Gson().fromJson(response.body().string(), ShopCarBean.class);

                    if (sOrderPoListDTOS.getCode() == 200) {
                        sOrderPoListDTOS.getRows().forEach(item -> sShoppingCartVoListDTOS.addAll(item.getSShoppingCartVoList()));

                        runOnUiThread(() -> {
                            AdapterGoodsPassDate orderAdapter = new AdapterGoodsPassDate(sShoppingCartVoListDTOS);
                            viewBinding.passDateList.setAdapter(orderAdapter);
                        });
                    } else {
                        runOnUiThread(() -> showToast("请求失败,请联系管理员"));
                    }

                }

            }
        });
    }


    static class AdapterGoodsPassDate extends RecyclerView.Adapter<AdapterGoodsPassDate.ViewHolder> {

        private final List<ShopCarBean.RowsDTO.SShoppingCartVoListDTO> sShoppingCartVoListDTOS;

        public AdapterGoodsPassDate(List<ShopCarBean.RowsDTO.SShoppingCartVoListDTO> sShoppingCartVoListDTOS) {
            this.sShoppingCartVoListDTOS = sShoppingCartVoListDTOS;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pass_date_list_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ShopCarBean.RowsDTO.SShoppingCartVoListDTO sShoppingCartVoListDTO = sShoppingCartVoListDTOS.get(position);
            holder.price.setText(sShoppingCartVoListDTO.getPrice());
            GlideUtils.load(holder.itemView.getContext(), sShoppingCartVoListDTO.getSpecificationsImages(), holder.goods_image, R.drawable.good_test, 20);
            holder.good_name.setText(sShoppingCartVoListDTO.getGoodsName());
            holder.rule.setText(sShoppingCartVoListDTO.getSGoodsSpecificationsName());

        }

        @Override
        public int getItemCount() {
            return sShoppingCartVoListDTOS.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            private final ImageView check;
            private final ImageView goods_image;
            private final TextView good_name;
            private final TextView rule;
            private final TextView price;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                check = itemView.findViewById(R.id.check);
                goods_image = itemView.findViewById(R.id.goods_image);
                good_name = itemView.findViewById(R.id.good_name);
                rule = itemView.findViewById(R.id.rule);
                price = itemView.findViewById(R.id.price);


            }
        }
    }
}