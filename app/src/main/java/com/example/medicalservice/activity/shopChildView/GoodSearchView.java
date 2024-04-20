package com.example.medicalservice.activity.shopChildView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.GoodBean;
import com.example.medicalservice.bean.ShopCarBean;
import com.example.medicalservice.databinding.ActivityGoodSearchViewBinding;
import com.example.medicalservice.recycleAdapter.ChartsAdapter;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.OkHttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GoodSearchView extends BaseActivity<ActivityGoodSearchViewBinding> {

    private MsAdapter msAdapter;

    private String sGoodsBusinessId = "";

    private String activeId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        super.initView();


        viewBinding.back.setOnClickListener(v -> finish());

        viewBinding.shopcar.setOnClickListener(v -> go(ShoppingCart.class));
    }

    @Override
    public void initData() {
        super.initData();

        if(getIntent().getStringExtra("msg") != null && getIntent().getStringExtra("msg2") == null){
            viewBinding.goodName.setText(getIntent().getStringExtra("msg"));
            if(Objects.equals(getIntent().getStringExtra("msg"), "限时抢购")){
                viewBinding.topEndImage.setVisibility(View.GONE);
            }
        }

        if (getIntent().getStringExtra("msg") != null&&getIntent().getStringExtra("msg2") != null) {

            if(Objects.equals(getIntent().getStringExtra("msg2"), "store")){
                sGoodsBusinessId = getIntent().getStringExtra("msg");
            }

            if(Objects.equals(getIntent().getStringExtra("msg2"), "activity")){
                activeId = getIntent().getStringExtra("msg");
            }

        }

        getIntent().getStringExtra("msg");
        getShopCarNumber();
        initSortList();

        searchGoodList("", "", "");

        viewBinding.searchBtn.setOnClickListener(v -> searchGoodList("", ""));

        viewBinding.minPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchGoodList(s.toString(), viewBinding.maxPrice.getText().toString(), "");
            }
        });

        viewBinding.maxPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchGoodList(viewBinding.minPrice.getText().toString(), s.toString(), "");

            }
        });
    }

    private void initSortList() {
        List<SortBean> sortBeans = new ArrayList<>();

        sortBeans.add(new SortBean(0, getString(R.string.comprehensive), "", 0));
        sortBeans.add(new SortBean(1, getString(R.string.sales_volume), "salesVolumeSorting", 0));
        sortBeans.add(new SortBean(1, getString(R.string.new_goods), "newGoodsSorting", 0));
        sortBeans.add(new SortBean(1, getString(R.string.price), "priceSorting", 0));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        viewBinding.sortList.setLayoutManager(linearLayoutManager);

        viewBinding.sortList.setAdapter(new SortAdapter(sortBeans));

    }


    private void getShopCarNumber() {
        OkHttpUtil.getInstance().doGet(API.shoppingCartList, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() != null) {
                    ShopCarBean sOrderPoListDTOS = new Gson().fromJson(response.body().string(), ShopCarBean.class);

                    if (sOrderPoListDTOS.getCode() == 200) {
                        runOnUiThread(() -> {
                            if (sOrderPoListDTOS.getRows().size() > 0) {
                                viewBinding.numberBox.setVisibility(View.VISIBLE);
                                viewBinding.shopCarNumber.setText("" + sOrderPoListDTOS.getRows().size());
                            }
                        });
                    } else {
                        runOnUiThread(() -> showToast("请求失败,请联系管理员"));
                    }


                }


            }
        });
    }

    private void searchGoodList(String sortType, String type) {
        OkHttpUtil.getInstance().doGet(API.goodsList(viewBinding.goodName.getText().toString(), sortType, type)+"&sGoodsBusinessId="+sGoodsBusinessId+"&activeId="+activeId, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() != null) {
                    GoodBean goodBean = new Gson().fromJson(response.body().string(), GoodBean.class);
                    if (goodBean.getCode() == 200) {
                        runOnUiThread(() -> initGoodList(goodBean.getRows()));

                    } else {
                        runOnUiThread(() -> showToast("请求失败"));
                    }
                }

            }
        });
    }

    private void searchGoodList(String minPrice, String maxPrice, String priceSorting) {

        OkHttpUtil.getInstance().doGet(API.goodsList(viewBinding.goodName.getText().toString(), minPrice, maxPrice, priceSorting)+"&sGoodsBusinessId="+sGoodsBusinessId+"&activeId="+activeId, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    GoodBean goodBean = new Gson().fromJson(response.body().string(), GoodBean.class);
                    if (goodBean.getCode() == 200) {
                        runOnUiThread(() -> initGoodList(goodBean.getRows()));

                    } else {
                        runOnUiThread(() -> showToast("请求失败,请联系管理员"));
                    }
                }


            }
        });
    }

    private void initGoodList(List<GoodBean.RowsDTO> rowsDTOS) {


        msAdapter = new MsAdapter<GoodBean.RowsDTO>(rowsDTOS, R.layout.goods_item) {

            @SuppressLint("SetTextI18n")
            @Override
            public void bindView(ViewHolder holder, GoodBean.RowsDTO goodBean) {
                ImageView goodImage = holder.getView(R.id.good_image);
                TextView goodName = holder.getView(R.id.name);
                TextView orgPrice = holder.getView(R.id.org_price);
                TextView price = holder.getView(R.id.price);

                LinearLayout box = holder.getView(R.id.box);
                box.setBackgroundColor(Color.parseColor("#FFF8F7FC"));


                goodName.setText(goodBean.getGoodsName());

                GlideUtils.load(activity, goodBean.getGoodsCoverImages(), goodImage, R.drawable.good_test, 20);
                orgPrice.setText("" + goodBean.getMinPrice());
                price.setText("" + goodBean.getMinPrice());

                orgPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);


                DisplayMetrics outMetrics = new DisplayMetrics();
                activity.getWindowManager().getDefaultDisplay().getRealMetrics(outMetrics);
                float widthPixel = outMetrics.widthPixels;
                float width = widthPixel / 5 - 60 - 50;


                goodName.setLayoutParams(new LinearLayout.LayoutParams((int) width, 44));
                goodImage.setLayoutParams(new LinearLayout.LayoutParams((int) width, (int) width));

                goodImage.setOnClickListener(v -> go(GoodDetailView.class, goodBean.getSGoodsId()));

            }
        };

        viewBinding.goodList.setAdapter(msAdapter);


    }


    class SortAdapter extends RecyclerView.Adapter<SortAdapter.ViewHolder> {

        List<SortBean> sortBeans;

        public SortAdapter(List<SortBean> sortBeans) {
            this.sortBeans = sortBeans;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sort_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            SortBean sortBean = sortBeans.get(position);
            holder.text.setText(sortBean.getText());

            if (position == 0) {
                holder.icon.setVisibility(View.GONE);
            }
            if (sortBean.type == 0) {
                holder.box.setBackgroundColor(Color.parseColor("#FF91C2FF"));
                holder.text.setTextColor(Color.WHITE);
                holder.icon.setImageResource(R.mipmap.desc);

                if (sortBean.getSort() == 0) {
                    holder.icon.setImageResource(R.drawable.arrow_no_desc);
                } else {
                    holder.icon.setImageResource(R.mipmap.desc);
                }
            } else {
                holder.box.setBackgroundColor(Color.WHITE);
                holder.text.setTextColor(Color.parseColor("#FF898989"));
                holder.icon.setImageResource(R.mipmap.desc_un);
            }


            holder.itemView.setOnClickListener(v -> {
                changeStatus(position);


                if (sortBean.getSort() == 0) {
                    sortBean.setSort(1);
                } else {
                    sortBean.setSort(0);
                }

                notifyDataSetChanged();


                Log.d("TAG", "onBindViewHolder: " + sortBean.getSort());


                searchGoodList(sortBean.getColumn(), sortBean.getSort() + "");


            });
        }

        private void changeStatus(int position) {

            for (SortBean s : sortBeans) {
                s.setType(1);
            }

            sortBeans.get(position).setType(0);

            notifyDataSetChanged();

        }

        @Override
        public int getItemCount() {
            return sortBeans.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView text;
            ImageView icon;

            LinearLayout box;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                text = itemView.findViewById(R.id.text);
                icon = itemView.findViewById(R.id.icon);
                box = itemView.findViewById(R.id.box);
            }
        }
    }

    private void rotate(ImageView view, int startDegrees, int endDegrees) {
//        Animation animation = new RotateAnimation(startDegrees, endDegrees);
//        animation.setDuration(500);
//        animation.setRepeatCount(0);//动画的反复次数
//        animation.setFillAfter(true);//设置为true，动画转化结束后被应用
//        view.startAnimation(animation);//開始动画

        RotateAnimation xz = new RotateAnimation(startDegrees, endDegrees, Animation.RELATIVE_TO_SELF, 0.5f
                , Animation.RELATIVE_TO_SELF, 0.5f);//起始位置，旋转到，x轴，y轴
//Animation.RELATIVE_TO_SELF是居中
        xz.setDuration(200);//速度
        xz.setRepeatCount(1);//次数，-1和Animation.INFINITE是无限次
        view.startAnimation(xz);


    }

    class SortBean {
        private int type;
        private String text;

        private String column;

        private int sort;

        public SortBean(int type, String text, String column, int sort) {
            this.type = type;
            this.text = text;
            this.column = column;
            this.sort = sort;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }
}