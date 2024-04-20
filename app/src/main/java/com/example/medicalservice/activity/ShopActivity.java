package com.example.medicalservice.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.activity.shopChildView.GoodDetailView;
import com.example.medicalservice.activity.shopChildView.GoodSearchView;
import com.example.medicalservice.activity.shopChildView.ShopMyCenter;
import com.example.medicalservice.activity.shopChildView.ShoppingCart;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.ActiveBean;
import com.example.medicalservice.bean.GoodBean;
import com.example.medicalservice.bean.PlateBean;
import com.example.medicalservice.bean.ShopCarBean;
import com.example.medicalservice.bean.ShopTypeBean;
import com.example.medicalservice.databinding.ActivityShopBinding;
import com.example.medicalservice.diyView.ChildRecyclerview;
import com.example.medicalservice.recycleAdapter.ChartsAdapter;
import com.example.medicalservice.recycleAdapter.GoodsListAdapter;
import com.example.medicalservice.recycleAdapter.ShopCategoryAdapter;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.OkHttpUtil;
import com.example.medicalservice.tools.StringUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShopActivity extends BaseActivity<ActivityShopBinding> {

    private List<ShopTypeBean.DataDTO> shopCategoryBeans = new ArrayList<>();

    private CategoryFatherAdapter categoryAdapter;
    private ShopCategoryAdapter shopCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getShopCarNumber();
    }

    @Override
    public void initView() {
        super.initView();

        viewBinding.shopCar.setOnClickListener(v -> go(ShoppingCart.class));
        viewBinding.back.setOnClickListener(v -> finish());

        viewBinding.searchBox.setOnClickListener(v -> go(GoodSearchView.class));
        //viewBinding.seeAll.setOnClickListener(v -> go(GoodSearchView.class));

        viewBinding.myCenter.setOnClickListener(v -> go(ShopMyCenter.class));

        viewBinding.username.setText(MyApplication.getInstance().getUserName());
        GlideUtils.load(activity, MyApplication.getInstance().getUserHeader(), viewBinding.header, R.drawable.nologin_header, 90);
    }

    @Override
    public void initData() {
        super.initData();

        initCategory();

        initGood();
        initCharts();
        initActivity();
    }

    private void initCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        viewBinding.categoryList.setLayoutManager(linearLayoutManager);
        shopCategoryAdapter = new ShopCategoryAdapter(shopCategoryBeans);
        viewBinding.categoryList.setAdapter(shopCategoryAdapter);
        shopCategoryBeans.clear();
        getGoodTypeList();

        shopCategoryAdapter.setOnItemClickListener(position -> {

            if (viewBinding.showDetailCategory.getVisibility() == View.VISIBLE) {
                viewBinding.showDetailCategory.setVisibility(View.GONE);
                categoryAdapter.setShow(false, position);

            } else {
                viewBinding.showDetailCategory.setVisibility(View.VISIBLE);

                categoryAdapter.setShow(true, position);

            }
        });
    }

    private void getGoodTypeList() {
        OkHttpUtil.getInstance().doGet(API.shopTypeList, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {


                    ShopTypeBean shopTypeBean = new Gson().fromJson(response.body().string(), ShopTypeBean.class);

                    if (shopTypeBean.getCode() == 200) {
                        runOnUiThread(() -> {
                            shopCategoryBeans.addAll(shopTypeBean.getData());
                            shopCategoryAdapter.notifyDataSetChanged();


                            categoryAdapter = new CategoryFatherAdapter(shopTypeBean.getData());
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
                            viewBinding.showDetailCategory.setLayoutManager(linearLayoutManager);
                            viewBinding.showDetailCategory.setAdapter(categoryAdapter);
                        });
                    } else {
                        activity.runOnUiThread(() -> showToast("请求失败,请联系管理员"));
                    }


                }

            }
        });
    }

    private void initGood() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        viewBinding.middleBox.setLayoutManager(linearLayoutManager);


        OkHttpUtil.getInstance().doGet(API.plateAndroidList, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> showToast("请求失败"));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    PlateBean goodBean = new Gson().fromJson(response.body().string(), PlateBean.class);

                    if (goodBean.getCode() == 200) {
                        runOnUiThread(() -> {
                            PlateTypeFirst plateTypeFirst = new PlateTypeFirst(goodBean.getData());

                            viewBinding.middleBox.setAdapter(plateTypeFirst);
                        });


                    } else {
                        runOnUiThread(() -> showToast("请求失败,请联系管理员"));
                    }

                }


            }
        });

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
                                viewBinding.shopCarNumber.setVisibility(View.VISIBLE);
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

    private void initCharts() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        viewBinding.plateList.setLayoutManager(linearLayoutManager);
        viewBinding.plateList.setNestedScrollingEnabled(false);

        OkHttpUtil.getInstance().doGet(API.plateAndroidList2, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> showToast("请求失败"));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    PlateBean goodBean = new Gson().fromJson(response.body().string(), PlateBean.class);

                    if (goodBean.getCode() == 200) {

                        runOnUiThread(() -> viewBinding.plateList.setAdapter(new PlateAdapter(goodBean.getData())));

                    } else {
                        runOnUiThread(() -> showToast("请求失败,请联系管理员"));
                    }
                }

            }
        });


    }

    private void initActivity() {
        OkHttpUtil.getInstance().doGet(API.active, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    ActiveBean activeBean = new Gson().fromJson(response.body().string(), ActiveBean.class);
                    if (activeBean.getCode() == 200) {

                        if (activeBean.getRows().size() == 3) {
                            ActiveBean.RowsDTO rowsDTO1 = activeBean.getRows().get(0);
                            ActiveBean.RowsDTO rowsDTO2 = activeBean.getRows().get(1);
                            ActiveBean.RowsDTO rowsDTO3 = activeBean.getRows().get(2);

                            runOnUiThread(() -> {
                                GlideUtils.load(activity, rowsDTO1.getImg(), viewBinding.activityLeftImage, R.mipmap.mall_group_buying_header_image, 50);

                                viewBinding.activityTopTitle.setText(rowsDTO2.getTitle());
                                viewBinding.activityTopSubtitle.setText(rowsDTO2.getSubTitle());

                                GlideUtils.load(activity, rowsDTO2.getImg(), viewBinding.activityTopImage, R.mipmap.health_onitoring_center, 1);

                                viewBinding.activityBottomTitle.setText(rowsDTO3.getTitle());
                                viewBinding.activityBottomSubtitle.setText(rowsDTO3.getSubTitle());
                                GlideUtils.load(activity, rowsDTO3.getImg(), viewBinding.activityBottomImage, R.mipmap.health_onitoring_center, 1);


                                viewBinding.activityLeftImage.setOnClickListener(v -> go(GoodSearchView.class, String.valueOf(rowsDTO1.getId()), "activity"));
                                viewBinding.activityTop.setOnClickListener(v -> go(GoodSearchView.class, String.valueOf(rowsDTO2.getId()), "activity"));

                                viewBinding.activityBottom.setOnClickListener(v -> go(GoodSearchView.class, String.valueOf(rowsDTO3.getId()), "activity"));

                            });


                        } else {
                            runOnUiThread(() -> showToast("数据出错"));
                        }
                    } else {
                        runOnUiThread(() -> showToast("请求失败"));
                    }
                }
            }
        });
    }

    class PlateTypeFirst extends RecyclerView.Adapter<PlateTypeFirst.ViewHolder> {


        private List<PlateBean.DataDTO> dataDTOList;

        public PlateTypeFirst(List<PlateBean.DataDTO> dataDTOList) {
            this.dataDTOList = dataDTOList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plate_type_first, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            PlateBean.DataDTO dataDTO = dataDTOList.get(position);

            holder.name.setText(dataDTO.getPlateTitle());
            holder.subtitle.setText(dataDTO.getPlateSubtitle());

            holder.see_all.setOnClickListener(v -> go(GoodSearchView.class, dataDTO.getPlateTitle()));

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.goodList.setLayoutManager(linearLayoutManager);

            holder.goodList.setAdapter(new GoodsListAdapter(dataDTO.getGoodsList(), activity));


        }

        @Override
        public int getItemCount() {
            return dataDTOList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private RecyclerView goodList;
            private TextView see_all, name, subtitle;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                goodList = itemView.findViewById(R.id.good_list);
                see_all = itemView.findViewById(R.id.see_all);
                name = itemView.findViewById(R.id.name);
                subtitle = itemView.findViewById(R.id.subtitle);
            }
        }
    }


    class CategoryFatherAdapter extends RecyclerView.Adapter<CategoryFatherAdapter.ViewHolder> {

        private List<ShopTypeBean.DataDTO> shopCategoryBeans;
        private boolean show = false;

        private int fatherPosition = 0;

        public void setShow(boolean show, int fatherPosition) {
            this.show = show;
            this.fatherPosition = fatherPosition;
            notifyDataSetChanged();
        }

        public CategoryFatherAdapter(List<ShopTypeBean.DataDTO> shopCategoryBeans) {
            this.shopCategoryBeans = shopCategoryBeans;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_detail_grid_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ShopTypeBean.DataDTO rowsDTO = shopCategoryBeans.get(position);

            if (fatherPosition == position) {
                holder.box.setVisibility(show ? View.VISIBLE : View.GONE);
            } else {
                holder.box.setVisibility(View.GONE);
            }


            List<List<String>> lists = getSubList(3, rowsDTO.getGoodsTypeNameSecondaryList());
            CategoryChildAdapter categoryChildAdapter = new CategoryChildAdapter(lists);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.itemView.getContext());

            holder.fatherList.setLayoutManager(linearLayoutManager);
            holder.fatherList.setAdapter(categoryChildAdapter);

            // holder.text.setText(StringUtils.join((ArrayList<String>) rowsDTO.getGoodsTypeNameSecondaryList(),"  "));

        }

        public List<List<String>> getSubList(int length, List<String> list) {
            int size = list.size();
            int temp = size / length + 1;
            boolean result = size % length == 0;
            List<List<String>> subList = new ArrayList<>();
            for (int i = 0; i < temp; i++) {
                if (i == temp - 1) {
                    if (result) {
                        break;
                    }
                    subList.add(list.subList(length * i, size));
                } else {
                    subList.add(list.subList(length * i, length * (i + 1)));
                }
            }
            return subList;
        }

        @Override
        public int getItemCount() {
            return shopCategoryBeans.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            //            private TextView text;
            private LinearLayout box;
            //
            private RecyclerView fatherList;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                // text = itemView.findViewById(R.id.text);
                box = itemView.findViewById(R.id.box);
                fatherList = itemView.findViewById(R.id.father_list);
            }
        }
    }

    class CategoryChildAdapter extends RecyclerView.Adapter<CategoryChildAdapter.ViewHolder> {
        private List<List<String>> lists;

        public CategoryChildAdapter(List<List<String>> lists) {
            this.lists = lists;
        }

        @NonNull
        @Override
        public CategoryChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_detail_grid_item, parent, false);

            return new CategoryChildAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            CategoryChildtextAdapter categoryChildtextAdapter = new CategoryChildtextAdapter(lists.get(position));

            holder.recyclerView.setLayoutManager(linearLayoutManager);
            holder.recyclerView.setAdapter(categoryChildtextAdapter);
        }

        @Override
        public int getItemCount() {
            return lists.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {


            RecyclerView recyclerView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                recyclerView = itemView.findViewById(R.id.father_list);
            }
        }
    }

    class CategoryChildtextAdapter extends RecyclerView.Adapter<CategoryChildtextAdapter.ViewHolder> {
        private List<String> lists;

        public CategoryChildtextAdapter(List<String> lists) {
            this.lists = lists;
        }

        @NonNull
        @Override
        public CategoryChildtextAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_text_item, parent, false);

            return new CategoryChildtextAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.text.setText(lists.get(position));

            holder.text.setOnClickListener(v -> {
                categoryAdapter.setShow(false, position);
                categoryAdapter.notifyDataSetChanged();
                Intent intent = new Intent(holder.itemView.getContext(), GoodSearchView.class);
                intent.putExtra("msg", lists.get(position));
                holder.itemView.getContext().startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return lists.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {


            private TextView text;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                text = itemView.findViewById(R.id.text);
            }
        }
    }


    class PlateAdapter extends RecyclerView.Adapter<PlateAdapter.ViewHolder> {


        private List<PlateBean.DataDTO> dataDTOList;

        public PlateAdapter(List<PlateBean.DataDTO> dataDTOList) {
            this.dataDTOList = dataDTOList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plate_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            PlateBean.DataDTO dataDTO = dataDTOList.get(position);

            holder.name.setText(dataDTO.getPlateTitle());
            holder.subtitle.setText(dataDTO.getPlateSubtitle());

            if (Integer.parseInt(dataDTO.getRankingIsShow()) == 0) {
                holder.rankBox.setVisibility(View.VISIBLE);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.itemView.getContext());
                holder.theCharts.setLayoutManager(linearLayoutManager);

                ChartsAdapter chartsAdapter = new ChartsAdapter(dataDTO.getGoodsRankingList());
                holder.theCharts.setAdapter(chartsAdapter);

                holder.theCharts.setNestedScrollingEnabled(false);
            }

            for (int i = 0; i < dataDTO.getGoodsList().size(); i++) {
                switch (i) {
                    case 0:
                        GlideUtils.load(holder.itemView.getContext(), dataDTO.getGoodsList().get(i).getGoodsCoverImages(), holder.image1, R.drawable.good_test, 20);
                        holder.image1.setOnClickListener(v -> {

                            if (dataDTO.getGoodsList().get(0) != null && dataDTO.getGoodsList().get(0).getSGoodsId() != null) {
                                go(GoodDetailView.class, dataDTO.getGoodsList().get(0).getSGoodsId());

                            }
                        });

                        break;
                    case 1:
                        GlideUtils.load(holder.itemView.getContext(), dataDTO.getGoodsList().get(i).getGoodsCoverImages(), holder.image2, R.drawable.good_test, 20);
                        holder.image2.setOnClickListener(v -> {

                            if (dataDTO.getGoodsList().get(1) != null && dataDTO.getGoodsList().get(1).getSGoodsId() != null) {
                                go(GoodDetailView.class, dataDTO.getGoodsList().get(1).getSGoodsId());

                            }
                        });
                        break;
                    case 2:
                        GlideUtils.load(holder.itemView.getContext(), dataDTO.getGoodsList().get(i).getGoodsCoverImages(), holder.image3, R.drawable.good_test, 20);
                        holder.image3.setOnClickListener(v -> {

                            if (dataDTO.getGoodsList().get(2) != null && dataDTO.getGoodsList().get(2).getSGoodsId() != null) {
                                go(GoodDetailView.class, dataDTO.getGoodsList().get(2).getSGoodsId());

                            }
                        });
                        break;
                    case 3:
                        GlideUtils.load(holder.itemView.getContext(), dataDTO.getGoodsList().get(i).getGoodsCoverImages(), holder.image4, R.drawable.good_test, 20);
                        holder.image4.setOnClickListener(v -> {

                            if (dataDTO.getGoodsList().get(3) != null && dataDTO.getGoodsList().get(3).getSGoodsId() != null) {
                                go(GoodDetailView.class, dataDTO.getGoodsList().get(3).getSGoodsId());

                            }
                        });
                        break;
                    case 4:
                        GlideUtils.load(holder.itemView.getContext(), dataDTO.getGoodsList().get(i).getGoodsCoverImages(), holder.image5, R.drawable.good_test, 20);
                        holder.image5.setOnClickListener(v -> {

                            if (dataDTO.getGoodsList().get(4) != null && dataDTO.getGoodsList().get(4).getSGoodsId() != null) {
                                go(GoodDetailView.class, dataDTO.getGoodsList().get(4).getSGoodsId());

                            }
                        });

                        break;
                }
            }


        }

        @Override
        public int getItemCount() {
            return dataDTOList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView name, subtitle;
            private LinearLayout rankBox;
            private ChildRecyclerview theCharts;
            private ImageView image1, image2, image3, image4, image5;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.name);

                theCharts = itemView.findViewById(R.id.the_charts);

                rankBox = itemView.findViewById(R.id.rank_box);
                subtitle = itemView.findViewById(R.id.subtitle);

                image1 = itemView.findViewById(R.id.image1);
                image2 = itemView.findViewById(R.id.image2);

                image3 = itemView.findViewById(R.id.image3);

                image4 = itemView.findViewById(R.id.image4);

                image5 = itemView.findViewById(R.id.image5);

            }
        }
    }

}