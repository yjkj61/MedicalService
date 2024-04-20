package com.example.medicalservice.activity.shopChildView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;

import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.AddShopCarBean;
import com.example.medicalservice.bean.AddressListBean;
import com.example.medicalservice.bean.BuyGoodBean;
import com.example.medicalservice.bean.Code200;
import com.example.medicalservice.bean.GoodBean;
import com.example.medicalservice.bean.GoodDetailBean;
import com.example.medicalservice.dataBaseBean.UserBean;
import com.example.medicalservice.databinding.ActivitySpecificationReSelectBinding;
import com.example.medicalservice.fragments.goodDetailChildView.AfterSaleFragment;
import com.example.medicalservice.fragments.goodDetailChildView.GoodJudgeFragment;
import com.example.medicalservice.fragments.goodDetailChildView.GoodSpecificationFragment;
import com.example.medicalservice.fragments.goodDetailChildView.GoodsIntroduceFragment;
import com.example.medicalservice.fragments.submitOrderFragment.SureOrderInfoFragment;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.GetOrderForShop;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.OkHttpUtil;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SpecificationReSelect extends BaseActivity<ActivitySpecificationReSelectBinding> {

    private String goodId;

    private String nowSelectSpecification;
    private List<GoodDetailBean.DataDTO.SGoodsSpecificationsMoreListDTO> specificationList = new ArrayList<>();

    private List<GoodDetailView.SelectImageBean> sGoodsSpecificationsListDTOS = new ArrayList<>();

    private GoodDetailBean.DataDTO dataDTO;

    private int number = 0;


    private List<BuyGoodBean> buyGoodBeans = new ArrayList<>();


    private String url;
    private String deleteShopUrl = "";

    private int inventory;

    private String sGoodsSpecificationsId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public void initView() {
        super.initView();


        viewBinding.back.setOnClickListener(v -> finish());
        DisplayMetrics outMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(outMetrics);
        float widthPixel = outMetrics.widthPixels;
        float width = (float) (widthPixel * 0.2);

        number = Integer.parseInt(viewBinding.peopleNumber.getText().toString());

        viewBinding.plus.setOnClickListener(v -> {

            if (inventory == 0) {
                return;
            }
            if (number == inventory) {
                return;
            }
            number += 1;
            viewBinding.peopleNumber.setText(number + "");

        });

        viewBinding.reduce.setOnClickListener(v -> {
            if (inventory == 0) {
                return;
            }
            if (number < 2) return;
            number -= 1;
            viewBinding.peopleNumber.setText(number + "");
        });

        viewBinding.imageBox.setLayoutParams(new LinearLayout.LayoutParams((int) width, LinearLayout.LayoutParams.WRAP_CONTENT));


        viewBinding.addCar.setOnClickListener(v -> {
            addGoodInShopCar();
        });

      
    }

    @Override
    public void initData() {
        super.initData();
        goodId = getIntent().getStringExtra("msg");
        deleteShopUrl = getIntent().getStringExtra("url");
        OkHttpUtil.getInstance().doGet(API.goodsDetail(goodId), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    GoodDetailBean goodDetailBean = new Gson().fromJson(response.body().string(), GoodDetailBean.class);
                    dataDTO = goodDetailBean.getData();
                    if (goodDetailBean.getCode() == 200) {
                        if (dataDTO.getSGoodsSpecificationsMoreList() != null) {
                            dataDTO.getSGoodsSpecificationsMoreList().forEach(item -> item.getSpecificationsValueList().forEach(child -> child.setSelect(item.getSpecificationsValueList().indexOf(child) == 0)));
                            specificationList.addAll(dataDTO.getSGoodsSpecificationsMoreList());

                            if (dataDTO.getGoodsVideo() != null) {
                                sGoodsSpecificationsListDTOS.add(new GoodDetailView.SelectImageBean(dataDTO.getGoodsVideo(), "video"));
                                url = dataDTO.getGoodsVideo();
                                runOnUiThread(() -> playVideo(dataDTO.getGoodsVideo()));
                            } else {
                                url = dataDTO.getGoodsCoverImages();
                            }
                            sGoodsSpecificationsListDTOS.add(new GoodDetailView.SelectImageBean(dataDTO.getGoodsCoverImages(), "image"));

                            dataDTO.getSGoodsSpecificationsList().forEach(item -> {
                                if (item.getSpecificationsImages() != null) {
                                    sGoodsSpecificationsListDTOS.add(new GoodDetailView.SelectImageBean(item.getSpecificationsImages(), "image"));
                                }

                            });


                        }
                        runOnUiThread(() -> {

                            viewBinding.goodName.setText(dataDTO.getGoodsName());


                            for (GoodDetailBean.DataDTO.SGoodsSpecificationsListDTO sGoodsSpecificationsListDTO :
                                    dataDTO.getSGoodsSpecificationsList()) {

                                if (sGoodsSpecificationsListDTO.getInventory() > 0) {
                                    inventory = sGoodsSpecificationsListDTO.getInventory();

                                    viewBinding.price.setText(sGoodsSpecificationsListDTO.getPrice());
                                    viewBinding.finalPrice.setText(sGoodsSpecificationsListDTO.getPrice());
                                    sGoodsSpecificationsId = String.valueOf(sGoodsSpecificationsListDTO.getSGoodsSpecificationsId());

                                    break;
                                }

                            }

                            viewBinding.salesVolume.setText(dataDTO.getSalesVolume());

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);

                            viewBinding.specificationsList.setLayoutManager(linearLayoutManager);

                            viewBinding.specificationsList.setAdapter(new SpecificationAdapter());

                            GlideUtils.load(activity, dataDTO.getGoodsCoverImages(), viewBinding.headerImage, R.drawable.good_test, 1);

                            if (sGoodsSpecificationsListDTOS.size() > 0) {
                                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(activity);
                                linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
                                viewBinding.ruleImages.setLayoutManager(linearLayoutManager1);
                                viewBinding.ruleImages.setAdapter(new SpecificationImageListAdapter());
                            }
                        });

                    } else {
                        runOnUiThread(() -> showToast("请求失败,请联系管理员"));
                    }
                }
            }
        });
    }






    private void addGoodInShopCar() {
        UserBean userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);
        if (userBean == null) {
            showToast("请先登录");
            return;
        }

        OkHttpUtil.getInstance().doDelete(deleteShopUrl, new Callback() {
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
                            AddShopCarBean addShopCarBean = new AddShopCarBean();
                            addShopCarBean.setUserId(String.valueOf(userBean.getUserId()));
                            addShopCarBean.setSGoodsId(goodId);
                            addShopCarBean.setSGoodsBusinessId(String.valueOf(dataDTO.getSGoodsBusinessId()));
                            addShopCarBean.setSGoodsSpecificationsId(sGoodsSpecificationsId);
                            addShopCarBean.setNumber(viewBinding.peopleNumber.getText().toString());

                            OkHttpUtil.getInstance().doPost(API.shoppingCart, new Gson().toJson(addShopCarBean), new Callback() {
                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                                }

                                @Override
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {


                                    if (response.body() != null) {
                                        Code200 code200 = new Gson().fromJson(response.body().string(), Code200.class);
                                        if (code200 != null && code200.getCode() == 200) {
                                            runOnUiThread(() -> {
                                                showToast("加入成功");
                                                go(ShoppingCart.class);

                                            });
                                        } else {
                                            runOnUiThread(() -> showToast("请求失败"));
                                        }
                                    }


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
    
    private void playVideo(String url) {

        viewBinding.video.setVisibility(View.VISIBLE);
        viewBinding.headerImage.setVisibility(View.GONE);

        MediaController mediaController = new MediaController(this);
        viewBinding.video.setMediaController(mediaController);
        mediaController.setVisibility(View.GONE);

        viewBinding.video.setVideoPath(url);
        viewBinding.video.requestFocus();
        viewBinding.video.start();

    }

    class SpecificationAdapter extends RecyclerView.Adapter<SpecificationAdapter.ViewHolder> {


        @NonNull
        @Override
        public SpecificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.specification_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SpecificationAdapter.ViewHolder holder, int position) {
            GoodDetailBean.DataDTO.SGoodsSpecificationsMoreListDTO sGoodsSpecificationsListDTO = specificationList.get(position);

            holder.name.setText(sGoodsSpecificationsListDTO.getSpecificationsName());

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.specificationList.setLayoutManager(linearLayoutManager);

            holder.specificationList.setAdapter(new SpecificationListChildAdapter(sGoodsSpecificationsListDTO.getSpecificationsValueList()));
        }


        @Override
        public int getItemCount() {
            return specificationList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView name;
            RecyclerView specificationList;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.name);
                specificationList = itemView.findViewById(R.id.specification_child_list);
            }
        }
    }


    class SpecificationListChildAdapter extends RecyclerView.Adapter< SpecificationListChildAdapter.ViewHolder> {


        private List<GoodDetailBean.DataDTO.SGoodsSpecificationsMoreListDTO.SpecificationsValueListDTO> specificationsValueListDTOS;

        public SpecificationListChildAdapter(List<GoodDetailBean.DataDTO.SGoodsSpecificationsMoreListDTO.SpecificationsValueListDTO> specificationsValueListDTOS) {
            this.specificationsValueListDTOS = specificationsValueListDTOS;
        }

        @NonNull
        @Override
        public  SpecificationListChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.specification_child_item, parent, false);

            return new  SpecificationListChildAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull  SpecificationListChildAdapter.ViewHolder holder, int position) {
            GoodDetailBean.DataDTO.SGoodsSpecificationsMoreListDTO.SpecificationsValueListDTO specificationsValueListDTO = specificationsValueListDTOS.get(position);
            holder.name.setText(specificationsValueListDTO.getSpecificationsValue());

            if (specificationsValueListDTO.isSelect()) {
                holder.name.setBackgroundResource(R.drawable.specification_back_select);
            } else {
                holder.name.setBackgroundResource(R.drawable.specification_back_unselect);

            }

            holder.itemView.setOnClickListener(v -> changeStatus(position));
        }

        @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
        void changeStatus(int position) {


            buyGoodBeans.clear();


            nowSelectSpecification = "";

            specificationList.forEach(item -> {
                item.getSpecificationsValueList().forEach(child -> {
                    if (child.isSelect()) {
                        nowSelectSpecification += child.getSpecificationsValue();
                    }
                });
            });

            Log.d("TAG", "changeStatus: " + nowSelectSpecification);


            dataDTO.getSGoodsSpecificationsList().forEach(item -> {
                if (item.getSpecificationsValueOne() != null) {
                    if (nowSelectSpecification.equals(item.getSpecificationsValueOne())) {
                        viewBinding.price.setText(item.getPrice());
                        viewBinding.finalPrice.setText(Float.valueOf(item.getPrice()) * Integer.valueOf(viewBinding.peopleNumber.getText().toString()) + "");

                        sGoodsSpecificationsId = String.valueOf(item.getSGoodsSpecificationsId());

                        inventory = item.getInventory();

                    }
                }

                if (item.getSpecificationsValueTwo() != null) {
                    if (nowSelectSpecification.equals(item.getSpecificationsValueOne() + item.getSpecificationsValueTwo())) {
                        viewBinding.price.setText(item.getPrice());
                        viewBinding.finalPrice.setText(Float.valueOf(item.getPrice()) * Integer.valueOf(viewBinding.peopleNumber.getText().toString()) + "");
                        sGoodsSpecificationsId = String.valueOf(item.getSGoodsSpecificationsId());

                        inventory = item.getInventory();

                    }
                }

                if (item.getSpecificationsValueThree() != null) {
                    if (nowSelectSpecification.equals(item.getSpecificationsValueOne() + item.getSpecificationsValueTwo() + item.getSpecificationsValueThree())) {
                        viewBinding.price.setText(item.getPrice());
                        viewBinding.finalPrice.setText(Float.valueOf(item.getPrice()) * Integer.valueOf(viewBinding.peopleNumber.getText().toString()) + "");
                        sGoodsSpecificationsId = String.valueOf(item.getSGoodsSpecificationsId());


                        inventory = item.getInventory();


                    }
                }
            });

            if (inventory == 0) {
                showToast("库存不足");
                return;
            }

            specificationsValueListDTOS.forEach(item -> {
                item.setSelect(false);

            });
            specificationsValueListDTOS.get(position).setSelect(true);

            notifyDataSetChanged();

        }

        @Override
        public int getItemCount() {
            return specificationsValueListDTOS.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView name;

            LinearLayout nameBox;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.name);
                nameBox = itemView.findViewById(R.id.name_box);
            }
        }
    }


    class SpecificationImageListAdapter extends RecyclerView.Adapter< SpecificationImageListAdapter.ViewHolder> {


        @NonNull
        @Override
        public  SpecificationImageListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);

            return new  SpecificationImageListAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull  SpecificationImageListAdapter.ViewHolder holder, int position) {

             GoodDetailView.SelectImageBean sGoodsSpecificationsListDTO = sGoodsSpecificationsListDTOS.get(position);
            GlideUtils.load(holder.itemView.getContext(), sGoodsSpecificationsListDTO.getUrl(), holder.image, sGoodsSpecificationsListDTO.getType().equals("image") ? R.drawable.good_test : R.drawable.video_test, 1);


            if (url.equals(sGoodsSpecificationsListDTO.getUrl())) {
                holder.isYou.setVisibility(View.VISIBLE);
            } else {
                holder.isYou.setVisibility(View.GONE);
            }

            holder.itemView.setOnClickListener(v -> {
                if (sGoodsSpecificationsListDTO.getType().equals("image")) {
                    viewBinding.headerImage.setVisibility(View.VISIBLE);
                    viewBinding.video.setVisibility(View.GONE);
                    GlideUtils.load(activity, sGoodsSpecificationsListDTO.getUrl(), viewBinding.headerImage, R.drawable.good_test, 1);
                } else {
                    playVideo(sGoodsSpecificationsListDTO.getUrl());
                }

                url = sGoodsSpecificationsListDTO.getUrl();

                notifyDataSetChanged();
            });


        }

        @Override
        public int getItemCount() {
            return sGoodsSpecificationsListDTOS.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private ImageView image;

            private TextView isYou;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
                isYou = itemView.findViewById(R.id.isYou);
            }
        }
    }
}