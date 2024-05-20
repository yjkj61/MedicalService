package com.example.medicalservice.activity.shopChildView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.medicalservice.databinding.ActivityGooddetailViewBinding;
import com.example.medicalservice.fragments.goodDetailChildView.AfterSaleFragment;
import com.example.medicalservice.fragments.goodDetailChildView.GoodJudgeFragment;
import com.example.medicalservice.fragments.goodDetailChildView.GoodSpecificationFragment;
import com.example.medicalservice.fragments.goodDetailChildView.GoodsIntroduceFragment;
import com.example.medicalservice.fragments.orderFragment.OrderFragmentShop;
import com.example.medicalservice.fragments.submitOrderFragment.SureOrderInfoFragment;
import com.example.medicalservice.recycleAdapter.ShopCarOrderAdapter;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.Count;
import com.example.medicalservice.tools.GetOrderForShop;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.OkHttpUtil;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GoodDetailView extends BaseActivity<ActivityGooddetailViewBinding> {

    private String[] typeList = {"商品介绍", "规格与包装", "售后保障", "商品评价"};

    private ArrayList<Fragment> fragments = new ArrayList<>();

    private String goodId;

    private String nowSelectSpecification;
    private List<GoodDetailBean.DataDTO.SGoodsSpecificationsMoreListDTO> specificationList = new ArrayList<>();

    private List<SelectImageBean> sGoodsSpecificationsListDTOS = new ArrayList<>();

    private GoodDetailBean.DataDTO dataDTO;

    private int number = 0;

    private String currentItem = "0";

    private List<BuyGoodBean> buyGoodBeans = new ArrayList<>();

    private String contact;
    private String address;
    private String phoneNumber;
    private String addressId;
    private String url;

    private int inventory;

    private BuyGoodBean bean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onResume() {
        super.onResume();

        addressData();
    }

    @Override
    public void initView() {
        super.initView();

        //viewBinding.favStatus.setImageResource(R.mipmap.sure_get);
        bean = new BuyGoodBean();

        viewBinding.back.setOnClickListener(v -> finish());

        viewBinding.submit.setOnClickListener(v -> {
            Intent intent = new Intent(activity, SubmitOrderView.class);
            GetOrderForShop.getInstance().setAddressId(addressId);

            List<BuyGoodBean> beans = new ArrayList<>();
            beans.add(bean);

            List<GoodDetailBean.DataDTO> dataDTOS = new ArrayList<>();
            dataDTOS.add(dataDTO);
            GetOrderForShop.getInstance().setBuyGoodBeans(beans);

            List<SureOrderInfoFragment.OrderGood> orderGoods = new ArrayList<>();

            for (GoodDetailBean.DataDTO data : dataDTOS) {
                SureOrderInfoFragment.OrderGood orderGood = new SureOrderInfoFragment.OrderGood();
                orderGood.setName(data.getGoodsName());
                orderGood.setNumber(Integer.parseInt(buyGoodBeans.get(0).getGoodsNumber()));
                orderGood.setMoney(Double.parseDouble(buyGoodBeans.get(0).getGoodsPrice()));
                orderGood.setGoodsCoverImages(data.getGoodsCoverImages());
                orderGoods.add(orderGood);
            }

            GetOrderForShop.getInstance().setGoods(orderGoods);

            startActivity(intent);
        });


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

            for (BuyGoodBean buyGoodBean : buyGoodBeans) {
                buyGoodBean.setGoodsNumber(String.valueOf(number));
                buyGoodBean.setOrderPrice(String.valueOf(Count.mul(number, Double.parseDouble(bean.getGoodsPrice()))));

                viewBinding.finalPrice.setText("" + Count.mul(number, Double.parseDouble(bean.getGoodsPrice())));
            }

        });

        viewBinding.reduce.setOnClickListener(v -> {
            if (inventory == 0) {
                return;
            }
            if (number < 2) return;
            number -= 1;
            viewBinding.peopleNumber.setText(number + "");

            for (BuyGoodBean buyGoodBean : buyGoodBeans) {
                buyGoodBean.setGoodsNumber(String.valueOf(number));
                buyGoodBean.setOrderPrice(String.valueOf(Count.mul(number, Double.parseDouble(bean.getGoodsPrice()))));
                viewBinding.finalPrice.setText("" + Count.mul(number, Double.parseDouble(bean.getGoodsPrice())));
            }
        });

        viewBinding.imageBox.setLayoutParams(new LinearLayout.LayoutParams((int) width, LinearLayout.LayoutParams.WRAP_CONTENT));


        viewBinding.shopCar.setOnClickListener(v -> addGoodInShopCar());

        viewBinding.fav.setOnClickListener(v -> {

            UserBean userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);
            if (userBean == null) {
                showToast("请先登录");
                return;
            }
            JSONObject jsonObject = new JSONObject();
            try {

                if (dataDTO.getGoodsCollectStatus() != 0) {
                    deleteDialog(dataDTO.getGoodsCollectStatus());
                    return;
                }
                jsonObject.put("userId", userBean.getUserId());
                jsonObject.put("sGoodsId", goodId);

                OkHttpUtil.getInstance().doPost(API.collect, jsonObject.toString(), new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        if (response.body() != null) {
                            try {
                                JSONObject jsonObject1 = new JSONObject(response.body().string());
                                if (jsonObject1.getInt("code") == 200) {
                                    runOnUiThread(() -> {
                                        showToast("收藏成功");
                                        getGoodDetail();
                                    });
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }

                    }
                });
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        });

        viewBinding.addCar.setOnClickListener(v -> {
            addGoodInShopCar();
        });

        viewBinding.addressText.setOnClickListener(v -> go(AddressManger.class, "select"));

    }

    @Override
    public void initData() {
        super.initData();
        recommendingGoodsList();

        goodId = getIntent().getStringExtra("msg");

        Log.d("TAG", "goodId: "+getIntent().getStringExtra("msg"));

        buyGoodBeans.clear();
        getGoodDetail();
    }

    private void getGoodDetail(){
        specificationList.clear();
        sGoodsSpecificationsListDTOS.clear();
        fragments.clear();
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
                        }
                        Log.d("TAG", "onResponse: " + dataDTO.getGoodsCollectStatus());
                        if (dataDTO.getGoodsCollectStatus() != 0) {
                            runOnUiThread(() -> viewBinding.favStatus.setImageResource(R.mipmap.sure_get));
                        }

                        if (dataDTO.getGoodsVideo() != null) {
                            sGoodsSpecificationsListDTOS.add(new SelectImageBean(dataDTO.getGoodsVideo(), "video"));
                            url = dataDTO.getGoodsVideo();
                            runOnUiThread(() -> playVideo(dataDTO.getGoodsVideo()));
                        } else {
                            url = dataDTO.getGoodsCoverImages();
                        }
                        sGoodsSpecificationsListDTOS.add(new SelectImageBean(dataDTO.getGoodsCoverImages(), "image"));

                        dataDTO.getSGoodsSpecificationsList().forEach(item -> {
                            if (item.getSpecificationsImages() != null) {
                                sGoodsSpecificationsListDTOS.add(new SelectImageBean(item.getSpecificationsImages(), "image"));
                            }

                        });
                        runOnUiThread(() -> {
                            fragments.add(GoodsIntroduceFragment.newInstance(dataDTO.getGoodsDetailsImages()));
                            fragments.add(GoodSpecificationFragment.newInstance(goodId));
                            fragments.add(AfterSaleFragment.newInstance(goodId));
                            fragments.add(GoodJudgeFragment.newInstance(goodId));

                            if (dataDTO.getGoodsCollectStatus() != 0) {
                                viewBinding.favStatus.setImageResource(R.mipmap.sure_get);
                            }

                            viewBinding.viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                                @Override
                                public int getCount() {
                                    return fragments.size();
                                }

                                @NonNull
                                @Override
                                public Fragment getItem(int position) {
                                    return fragments.get(position);
                                }

                                @Override
                                public CharSequence getPageTitle(int position) {
                                    return typeList[position];
                                }
                            });
                            viewBinding.viewPager.setOffscreenPageLimit(4);

                            viewBinding.tl2.setViewPager(viewBinding.viewPager);

                            viewBinding.viewPager.setCurrentItem(Integer.parseInt(currentItem));

                            viewControl();

                            viewBinding.goodName.setText(dataDTO.getGoodsName());


                            for (GoodDetailBean.DataDTO.SGoodsSpecificationsListDTO sGoodsSpecificationsListDTO :
                                    dataDTO.getSGoodsSpecificationsList()) {

                                if (sGoodsSpecificationsListDTO.getInventory() > 0) {
                                    inventory = sGoodsSpecificationsListDTO.getInventory();

                                    viewBinding.price.setText(sGoodsSpecificationsListDTO.getPrice());
                                    viewBinding.finalPrice.setText(sGoodsSpecificationsListDTO.getPrice());

                                    viewBinding.weight.setText(sGoodsSpecificationsListDTO.getWeight());
                                    bean.setSGoodsSpecificationsId(String.valueOf(sGoodsSpecificationsListDTO.getSGoodsSpecificationsId()));
                                    bean.setGoodsPrice(sGoodsSpecificationsListDTO.getPrice());
                                    bean.setWeight(sGoodsSpecificationsListDTO.getWeight());
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


                            bean.setSGoodsBusinessId(String.valueOf(dataDTO.getSGoodsBusinessId()));
                            bean.setSGoodsId(String.valueOf(dataDTO.getSGoodsId()));
                            bean.setGoodsNumber(viewBinding.peopleNumber.getText().toString());
                            bean.setSGoodsSpecificationsName(nowSelectSpecification);
                            bean.setOrderPrice(viewBinding.finalPrice.getText().toString());
                            bean.setAddress(address);
                            bean.setContact(contact);
                            bean.setPhone(phoneNumber);
                            bean.setPayWay("0");
                            bean.setOrderSource("0");
                            buyGoodBeans.add(bean);
                        });

                    } else {
                        runOnUiThread(() -> showToast("请求失败,请联系管理员"));
                    }
                }
            }
        });
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
                                activity.runOnUiThread(() -> viewBinding.addressText.setText(item.getArea() + item.getAddress()));
                            }

                        } else if (item.getIsDefaultAddress() == 1) {
                            address = item.getArea() + item.getAddress();
                            contact = item.getContact();
                            phoneNumber = item.getPhone();

                            addressId = String.valueOf(item.getSAreaId());
                            activity.runOnUiThread(() -> viewBinding.addressText.setText(item.getArea() + item.getAddress()));
                        }
                    });


                }


            }
        });
    }

    private void viewControl() {

        viewBinding.tl2.getTitleView(0).setBackgroundColor(Color.rgb(233, 232, 248));

        viewBinding.tl2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewBinding.viewPager.setCurrentItem(position);
                for (int i = 0; i < typeList.length; i++) {

                    if (i == position) {
                        viewBinding.tl2.getTitleView(position).setBackgroundColor(Color.rgb(233, 232, 248));
                    } else {
                        viewBinding.tl2.getTitleView(i).setBackgroundColor(Color.rgb(255, 255, 255));
                    }
                }

            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        viewBinding.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewBinding.tl2.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    private void addGoodInShopCar() {
        UserBean userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);
        if (userBean == null) {
            showToast("请先登录");
            return;
        }


        AddShopCarBean addShopCarBean = new AddShopCarBean();
        addShopCarBean.setUserId(String.valueOf(userBean.getUserId()));
        addShopCarBean.setSGoodsId(goodId);
        addShopCarBean.setSGoodsBusinessId(String.valueOf(dataDTO.getSGoodsBusinessId()));
        addShopCarBean.setSGoodsSpecificationsId(bean.getSGoodsSpecificationsId());
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

    private void recommendingGoodsList() {
        viewBinding.recommendingGoodsList.setLayoutManager(new LinearLayoutManager(this));


        OkHttpUtil.getInstance().doGet(API.recommendingGoods, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    GoodBean goodBean = new Gson().fromJson(response.body().string(), GoodBean.class);
                    if (goodBean.getCode() == 200) {

                        runOnUiThread(() -> {
                            Recommending recommending = new Recommending(goodBean.getRows());

                            if (recommending.rowsDTOS.size() > 0) {
                                viewBinding.boxRecommend.setVisibility(View.VISIBLE);
                            }
                            viewBinding.recommendingGoodsList.setAdapter(recommending);
                        });
                    }
                }
            }
        });

    }

    private void playVideo(String url) {

        viewBinding.videoBox.setVisibility(View.VISIBLE);
        viewBinding.headerImage.setVisibility(View.GONE);

        MediaController mediaController = new MediaController(this);
        viewBinding.video.setMediaController(mediaController);
        mediaController.setVisibility(View.GONE);

        viewBinding.video.setVideoPath(url);
        viewBinding.video.requestFocus();
        viewBinding.video.start();

    }

    private void deleteDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Dialog_style);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_delete, null);

        builder.setView(view);
        TextView left_btn, right_btn, text;
        left_btn = view.findViewById(R.id.yes);
        right_btn = view.findViewById(R.id.no);
        text = view.findViewById(R.id.tip_text);


        text.setText("是否取消收藏");

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


        left_btn.setOnClickListener(v1 -> OkHttpUtil.getInstance().doDelete(API.collectCancel(id), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {


                runOnUiThread(() -> {
                    alertDialog.dismiss();
                    getGoodDetail();
                    viewBinding.favStatus.setImageResource(R.mipmap.cancel_get);
                });

            }
        }));

        right_btn.setOnClickListener(v1 -> alertDialog.dismiss());


    }

    class SpecificationAdapter extends RecyclerView.Adapter<SpecificationAdapter.ViewHolder> {


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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


    class SpecificationListChildAdapter extends RecyclerView.Adapter<SpecificationListChildAdapter.ViewHolder> {


        private List<GoodDetailBean.DataDTO.SGoodsSpecificationsMoreListDTO.SpecificationsValueListDTO> specificationsValueListDTOS;

        public SpecificationListChildAdapter(List<GoodDetailBean.DataDTO.SGoodsSpecificationsMoreListDTO.SpecificationsValueListDTO> specificationsValueListDTOS) {
            this.specificationsValueListDTOS = specificationsValueListDTOS;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.specification_child_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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


                        bean.setSGoodsSpecificationsId(String.valueOf(item.getSGoodsSpecificationsId()));
                        bean.setGoodsPrice(item.getPrice());
                        bean.setWeight(item.getWeight());

                        inventory = item.getInventory();

                    }
                }

                if (item.getSpecificationsValueTwo() != null) {
                    if (nowSelectSpecification.equals(item.getSpecificationsValueOne() + item.getSpecificationsValueTwo())) {
                        viewBinding.price.setText(item.getPrice());
                        viewBinding.finalPrice.setText(Float.valueOf(item.getPrice()) * Integer.valueOf(viewBinding.peopleNumber.getText().toString()) + "");
                        bean.setSGoodsSpecificationsId(String.valueOf(item.getSGoodsSpecificationsId()));
                        bean.setGoodsPrice(item.getPrice());
                        bean.setWeight(item.getWeight());
                        inventory = item.getInventory();

                    }
                }

                if (item.getSpecificationsValueThree() != null) {
                    if (nowSelectSpecification.equals(item.getSpecificationsValueOne() + item.getSpecificationsValueTwo() + item.getSpecificationsValueThree())) {
                        viewBinding.price.setText(item.getPrice());
                        viewBinding.finalPrice.setText(Float.valueOf(item.getPrice()) * Integer.valueOf(viewBinding.peopleNumber.getText().toString()) + "");
                        bean.setSGoodsSpecificationsId(String.valueOf(item.getSGoodsSpecificationsId()));
                        bean.setGoodsPrice(item.getPrice());
                        bean.setWeight(item.getWeight());
                        inventory = item.getInventory();


                    }
                }
            });

            if (inventory == 0) {
                showToast("库存不足");
                return;
            }
            bean.setSGoodsBusinessId(String.valueOf(dataDTO.getSGoodsBusinessId()));
            bean.setSGoodsId(String.valueOf(dataDTO.getSGoodsId()));
            bean.setGoodsNumber(viewBinding.peopleNumber.getText().toString());
            bean.setSGoodsSpecificationsName(nowSelectSpecification);
            bean.setOrderPrice(viewBinding.finalPrice.getText().toString());
            bean.setAddress(address);
            bean.setContact(contact);
            bean.setPhone(phoneNumber);
            bean.setPayWay("0");
            bean.setOrderSource("0");

            buyGoodBeans.add(bean);


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


    class SpecificationImageListAdapter extends RecyclerView.Adapter<SpecificationImageListAdapter.ViewHolder> {


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            SelectImageBean sGoodsSpecificationsListDTO = sGoodsSpecificationsListDTOS.get(position);
            GlideUtils.load(holder.itemView.getContext(), sGoodsSpecificationsListDTO.getUrl(), holder.image, sGoodsSpecificationsListDTO.getType().equals("image") ? R.drawable.good_test : R.drawable.video_test, 1);


            if (url.equals(sGoodsSpecificationsListDTO.getUrl())) {
                holder.isYou.setVisibility(View.VISIBLE);
            } else {
                holder.isYou.setVisibility(View.GONE);
            }

            holder.itemView.setOnClickListener(v -> {
                if (sGoodsSpecificationsListDTO.getType().equals("image")) {
                    viewBinding.headerImage.setVisibility(View.VISIBLE);
                    viewBinding.videoBox.setVisibility(View.GONE);
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

    class Recommending extends RecyclerView.Adapter<Recommending.ViewHolder> {


        List<GoodBean.RowsDTO> rowsDTOS;

        public Recommending(List<GoodBean.RowsDTO> rowsDTOS) {
            this.rowsDTOS = rowsDTOS;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item_for_100, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            GoodBean.RowsDTO rowsDTO = rowsDTOS.get(position);
            GlideUtils.load(holder.itemView.getContext(), rowsDTO.getGoodsImages(), holder.good_image);

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private ImageView good_image;

            public ViewHolder(@NonNull View itemView) {

                super(itemView);

                good_image = itemView.findViewById(R.id.good_image);
            }
        }
    }

    static class SelectImageBean {
        String url;
        String type;

        public SelectImageBean(String url, String type) {
            this.url = url;
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}