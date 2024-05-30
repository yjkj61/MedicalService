package com.example.medicalservice.fragments.submitOrderFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medicalservice.R;
import com.example.medicalservice.activity.shopChildView.SubmitOrderView;
import com.example.medicalservice.baseFile.BaseFragment;
import com.example.medicalservice.bean.BuyGoodBean;
import com.example.medicalservice.bean.GoodDetailBean;
import com.example.medicalservice.databinding.FragmentSureOrderInfoBinding;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.GetOrderForShop;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.OkHttpUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SureOrderInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SureOrderInfoFragment extends BaseFragment<FragmentSureOrderInfoBinding> {

    List<BuyGoodBean> buyGoodBeans;

    public static SureOrderInfoFragment newInstance() {
        SureOrderInfoFragment fragment = new SureOrderInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        List<OrderGood> goods = GetOrderForShop.getInstance().getGoods();

        buyGoodBeans = GetOrderForShop.getInstance().getBuyGoodBeans();

        AdapterOrderInfo adapterOrderInfo = new AdapterOrderInfo(goods);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        viewBinding.list.setLayoutManager(linearLayoutManager);
        viewBinding.list.setAdapter(adapterOrderInfo);

        viewBinding.submitBox.setOnClickListener(v -> {
            submit();
        });

        viewBinding.lastStep.setOnClickListener(v -> SubmitOrderView.mActivity.tabSwitch(0));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    private void submit() {
        OkHttpUtil.getInstance().doPost(API.order, new Gson().toJson(buyGoodBeans), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                activity.runOnUiThread(() -> showToast("请求失败"));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {

                if (response.body() != null) {

                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getInt("code") == 200) {
                            showToast("下单成功");
                            //SubmitOrderView.mActivity.finish();
//                            activity.runOnUiThread(() -> SubmitOrderView.mActivity.tabSwitch(2));
                        } else {
                            activity.runOnUiThread(() -> showToast("请求失败，请联系管理员"));
                        }

                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    class AdapterOrderInfo extends RecyclerView.Adapter<AdapterOrderInfo.ViewHolder> {


        private List<OrderGood> orderGoods;

        public AdapterOrderInfo(List<OrderGood> orderGoods) {
            this.orderGoods = orderGoods;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sure_order_info_item, parent, false);

            return new ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            OrderGood orderGood = orderGoods.get(position);

            holder.goodName.setText(orderGood.getName());
            holder.number.setText(orderGood.getNumber()+"");
            holder.rule.setText(orderGood.getRule());
            holder.money.setText(orderGood.getMoney() * orderGood.number + "");
            GlideUtils.load(getContext(),orderGood.goodsCoverImages,holder.imageGoods);
            holder.reduce.setOnClickListener(v -> {
                if (orderGood.getNumber() == 1) {
                    return;
                }
                orderGood.setNumber(orderGood.getNumber() - 1);

                notifyItemChanged(position);
            });
            holder.plus.setOnClickListener(v -> {

                orderGood.setNumber(orderGood.getNumber() + 1);

                notifyItemChanged(position);
            });
        }

        @Override
        public int getItemCount() {
            return orderGoods.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView goodName, number, rule, money;
            private ImageView reduce, plus, imageGoods;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                goodName = itemView.findViewById(R.id.name);
                number = itemView.findViewById(R.id.people_number);
                rule = itemView.findViewById(R.id.rule);
                money = itemView.findViewById(R.id.money);
                reduce = itemView.findViewById(R.id.reduce);
                plus = itemView.findViewById(R.id.plus);
                imageGoods = itemView.findViewById(R.id.imageGoods);
            }
        }
    }


   public static class OrderGood {
        String name;
        String rule;
        String goodsCoverImages;

        int number;

        double money;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRule() {
            return rule;
        }

        public void setRule(String rule) {
            this.rule = rule;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

       public String getGoodsCoverImages() {
           return goodsCoverImages;
       }

       public void setGoodsCoverImages(String goodsCoverImages) {
           this.goodsCoverImages = goodsCoverImages;
       }
   }
}