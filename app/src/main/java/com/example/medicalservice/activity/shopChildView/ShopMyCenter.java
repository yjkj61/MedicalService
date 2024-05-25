package com.example.medicalservice.activity.shopChildView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.LevelInfoBean;
import com.example.medicalservice.dataBaseBean.UserBean;
import com.example.medicalservice.databinding.ActivityShopMyCenterBinding;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.OkHttpUtil;
import com.example.medicalservice.tools.ToastUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShopMyCenter extends BaseActivity<ActivityShopMyCenterBinding> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        super.initView();

        viewBinding.allOrder.setOnClickListener(v -> go(OrderViewShop.class, "0"));
        viewBinding.allOrderIcon.setOnClickListener(v -> go(OrderViewShop.class, "0"));

        viewBinding.back.setOnClickListener(v -> finish());

        UserBean userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);


        GlideUtils.load(this, MyApplication.getInstance().getUserHeader(), viewBinding.header, R.drawable.header,90);

        viewBinding.nameAge.setText(MyApplication.getInstance().getUserName());


        initOrderControl();
        initGrid();
        getLevelList();
    }

    private void initOrderControl() {
        List<String> orderControls = new ArrayList<>();
        orderControls.add(getString(R.string.to_be_shipped));
        orderControls.add(getString(R.string.goods_to_be_received));
        orderControls.add(getString(R.string.to_be_evaluated));
        orderControls.add(getString(R.string.after_sale));

        MsAdapter msAdapter = new MsAdapter<String>(orderControls, R.layout.shop_my_center_grid_item) {

            @Override
            public void bindView(ViewHolder holder, String obj) {

                TextView text = holder.getView(R.id.text);
                ImageView icon = holder.getView(R.id.icon);
                text.setText(obj);
                switch (obj) {
                    case "待发货":
                    case "Shipped":
                        text.setTextColor(Color.parseColor("#FF0192EA"));
                        icon.setImageResource(R.mipmap.wait_send);
                        holder.getItemView().setOnClickListener(v -> go(OrderViewShop.class, "1"));

                        break;
                    case "待收货":
                    case "Received":
                        text.setTextColor(Color.parseColor("#FF4033A7"));
                        icon.setImageResource(R.mipmap.wait_get);
                        holder.getItemView().setOnClickListener(v -> go(OrderViewShop.class, "2"));
                        break;
                    case "待评价":
                    case "Evaluated":
                        text.setTextColor(Color.parseColor("#FFE74A80"));
                        icon.setImageResource(R.mipmap.wait_talk);
                        holder.getItemView().setOnClickListener(v -> go(OrderViewShop.class, "3"));
                        break;
                    case "退款/售后":
                    case "AfterSale":
                        text.setTextColor(Color.parseColor("#FFF065C9"));
                        icon.setImageResource(R.mipmap.after_sale);
                        break;
                }

            }
        };

        viewBinding.orderControl.setAdapter(msAdapter);
    }

    private void initGrid() {
        List<String> orderControls = new ArrayList<>();
        orderControls.add(getString(R.string.shopping_cart));
        orderControls.add(getString(R.string.my_collection));
        orderControls.add(getString(R.string.address));
        orderControls.add(getString(R.string.red_envelope_card_voucher));

        MsAdapter msAdapter = new MsAdapter<String>(orderControls, R.layout.shop_center_bottom_item) {

            @Override
            public void bindView(ViewHolder holder, String obj) {

                TextView text = holder.getView(R.id.text);
                ImageView icon = holder.getView(R.id.icon);
                text.setText(obj);
                switch (obj) {
                    case "购物车":
                    case "Cart":
                        icon.setImageResource(R.mipmap.center_car_icon);
                        holder.getItemView().setOnClickListener(v -> go(ShoppingCart.class));
                        break;
                    case "我的收藏":
                    case "MyCollection":
                        icon.setImageResource(R.mipmap.center_collect);
                        holder.getItemView().setOnClickListener(v -> go(MyFav.class));
                        break;
                    case "收货地址":
                    case "Address":
                        icon.setImageResource(R.mipmap.center_address_icon);
                        holder.getItemView().setOnClickListener(v -> go(AddressManger.class,"manager"));
                        break;
                    case "红包卡券":
                    case "CardVoucher":
                        icon.setImageResource(R.mipmap.center_card_icon);
                        holder.getItemView().setOnClickListener(v -> ToastUtil.showToast("敬请期待", activity));
                        //holder.getItemView().setOnClickListener(v -> go(CouponView.class));
                        break;
                }

            }
        };

        viewBinding.gridView.setAdapter(msAdapter);
    }

    public void getUserJiFen(){
        OkHttpUtil.getInstance().doGet(API.UserJiFen, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String code = jsonObject.getString("code");
                    String msg = jsonObject.getString("msg");
                    if (!"200".equals(code)){
                        showToast(msg);
                        return;
                    }
                    int jifen = jsonObject.getInt("data");
                    for (int i = 0; i < list_level.size();i++){
                        if (jifen >= list_level.get(i).getGrowthValueBegin() && list_level.get(i).getGrowthValueEnd() >= jifen){
                            int finalI = i;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    viewBinding.level.setText("LV" + list_level.get(finalI).getLevel());
                                }
                            });
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewBinding.tvJifen.setText("(" + jifen + "成长值" + ")");
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    List<LevelInfoBean.DataDTO> list_level = new ArrayList<>();

    public void getLevelList(){
        OkHttpUtil.getInstance().doGet(API.LevelList, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                LevelInfoBean bean = new Gson().fromJson(response.body().string(), LevelInfoBean.class);
                if (bean.getCode() == 200){
                    list_level.clear();
                    list_level.addAll(bean.getData());
                    getUserJiFen();
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            getUserJiFen();
//                        }
//                    });
                }
            }
        });
    }

}