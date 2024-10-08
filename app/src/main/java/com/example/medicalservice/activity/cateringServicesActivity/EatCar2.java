package com.example.medicalservice.activity.cateringServicesActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.activity.cateringServicesActivity.Order.OrderListActivity;
import com.example.medicalservice.activity.cateringServicesActivity.Order.PayOrderActivity;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.CarFoodListBean;
import com.example.medicalservice.bean.FoodListBean;
import com.example.medicalservice.bean.FoodListNewBean;
import com.example.medicalservice.databinding.ActivityEatCar2Binding;
import com.example.medicalservice.fragments.CateringServices;
import com.example.medicalservice.recycleAdapter.AiBegHeaderAdapter;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.Count;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.OkHttpUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class EatCar2 extends BaseActivity<ActivityEatCar2Binding> {

    private List<String> types = new ArrayList<>();

    private List<CarFoodListBean> foodList = new ArrayList<>();

    private String nowType = "当季新品";
    private MsAdapter msAdapter;


    private String markId = "";
    private String markName = "";

    List<MenuSlideBean> menuSlideBeans = new ArrayList<>();
    PagerSnapHelper pagerSnapHelperMouth;

    AdapterMenu adapterMenu;

    AdapterEatList adapterEatList;

    public static Activity mActivity;

    private String type = "0";
    Handler handler = new Handler(msg -> {

        String money = (String) msg.obj;
        viewBinding.ownerRemainMoney.setText("余额：" + money);
        return false;
    });

    @Override
    public void onResume() {
        super.onResume();
        viewBinding.canteenName.setText(MyApplication.getInstance().getrFoodCanteenName());
        updateMoney(handler);

        getNewMenuList();

//        try {
//            getMenuList();
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void initView() {
        super.initView();

        mActivity = this;

        type = getIntent().getStringExtra("msg");
//        markId = getIntent().getStringExtra("msg");
        markName = getIntent().getStringExtra("msg2");
        viewBinding.seeAll.setOnClickListener(v -> go(OrderListActivity.class));
        viewBinding.catering.setOnClickListener(v -> {
            foodList.clear();
            viewBinding.money.setText("");
            go(CateringSelectView.class);
        });

        pagerSnapHelperMouth = new PagerSnapHelper();
        pagerSnapHelperMouth.attachToRecyclerView(viewBinding.menuList);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        viewBinding.menuTypeList.setLayoutManager(linearLayoutManager);
        adapterMenu = new AdapterMenu(menuSlideBeans);
        viewBinding.menuTypeList.setAdapter(adapterMenu);


        LinearLayoutManager menuListLinear = new LinearLayoutManager(activity);
        viewBinding.menuList.setLayoutManager(menuListLinear);
        adapterEatList = new AdapterEatList(menuSlideBeans);
        viewBinding.menuList.setAdapter(adapterEatList);


        viewBinding.menuList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    for (MenuSlideBean bean : menuSlideBeans) {
                        bean.setSelect(false);
                    }
                    menuSlideBeans.get(Objects.requireNonNull(recyclerView.getLayoutManager()).getPosition(Objects.requireNonNull(pagerSnapHelperMouth.findSnapView(recyclerView.getLayoutManager())))).setSelect(true);
                    adapterMenu.notifyDataSetChanged();
                }


            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


            }
        });


        viewBinding.submit.setOnClickListener(v -> {

            if (foodList.size() > 0) {
                Intent intent = new Intent(this, PayOrderActivity.class);
                intent.putExtra("foodList", (Serializable) foodList);
                intent.putExtra("totalMoney", viewBinding.money.getText().toString());
                startActivity(intent);

                viewBinding.money.setText("");
            } else {
                showToast("请点菜");
            }


        });
    }

    @Override
    public void initData() {
        super.initData();
    }

    private void sumTotalMoney() {
        double money = 0;
        for (CarFoodListBean item : foodList) {
            money = Count.add(money, Count.mul(item.getNumber(), item.getSinglePrice()));
        }

        viewBinding.money.setText(money + "");
    }

    private void getNewMenuList(){
        types.clear();
        OkHttpUtil.getInstance().doGet(API.newFoodList() + "rFoodCanteenId=" + MyApplication.getInstance().getrFoodCanteenId()
                + "&rFoodCommunityOrPrivate=" + type, new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String json = Objects.requireNonNull(response.body()).string();

                FoodListNewBean foodListBean = new Gson().fromJson(json, FoodListNewBean.class);


                if (foodListBean.getCode() == 200) {
                    types.add("当季新品");
                    for (FoodListNewBean.DataDTO row : foodListBean.getData()) {
                        if (!types.contains(row.getRFoodType())) types.add(row.getRFoodType());
                    }


                    for (String type : types) {
                        MenuSlideBean menuSlideBean = new MenuSlideBean();
                        menuSlideBean.setName(type);
                        List<FoodListNewBean.DataDTO> allFoods = new ArrayList<>();

                        menuSlideBean.setSelect(type.equals("当季新品"));

                        for (FoodListNewBean.DataDTO row : foodListBean.getData()) {

                            if (type.equals("当季新品") && row.getRFoodNewStatus() == 0) {
                                allFoods.add(row);
                            }
                            if (row.getRFoodType().contains(type)) {
                                allFoods.add(row);
                            }
                        }
                        menuSlideBean.setFoodList(allFoods);
                        menuSlideBeans.add(menuSlideBean);

                    }
                    if (menuSlideBeans.size() == 0) return;

                    runOnUiThread(() -> {
                        adapterEatList.notifyDataSetChanged();
                        adapterMenu.notifyDataSetChanged();
                    });

                } else {
                    runOnUiThread(() -> showToast("请求失败,请联系管理员"));
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
        });
    }

    private void getMenuList() throws JSONException {
//        types.clear();
//        menuSlideBeans.clear();
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("rFoodCanteenId", MyApplication.getInstance().getrFoodCanteenId());
//        jsonObject.put("rFoodCommunityOrPrivate", "1");
//        OkHttpUtil.getInstance().doPost(API.foodList(), jsonObject.toString(), new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//
//                String json = Objects.requireNonNull(response.body()).string();
//
//                FoodListBean foodListBean = new Gson().fromJson(json, FoodListBean.class);
//
//
//                if (foodListBean.getCode() == 200) {
//                    types.add("当季新品");
//                    for (FoodListBean.RowsDTO row : foodListBean.getRows()) {
//                        if (!types.contains(row.getRFoodType())) types.add(row.getRFoodType());
//                    }
//
//
//                    for (String type : types) {
//                        MenuSlideBean menuSlideBean = new MenuSlideBean();
//                        menuSlideBean.setName(type);
//                        List<FoodListBean.RowsDTO> allFoods = new ArrayList<>();
//
//                        menuSlideBean.setSelect(type.equals("当季新品"));
//
//                        for (FoodListBean.RowsDTO row : foodListBean.getRows()) {
//
//                            if (type.equals("当季新品") && row.getrFoodNewStatus() == 0) {
//                                allFoods.add(row);
//                            }
//                            if (row.getRFoodType().contains(type)) {
//                                allFoods.add(row);
//                            }
//                        }
//                        menuSlideBean.setFoodList(allFoods);
//                        menuSlideBeans.add(menuSlideBean);
//
//                    }
//                    if (menuSlideBeans.size() == 0) return;
//
//                    runOnUiThread(() -> {
//                        adapterEatList.notifyDataSetChanged();
//                        adapterMenu.notifyDataSetChanged();
//                    });
//
//                } else {
//                    runOnUiThread(() -> showToast("请求失败,请联系管理员"));
//                }
//
//            }
//        });
    }


    public static boolean haveIt(List<CarFoodListBean> foodList, int targetValue) {
        for (CarFoodListBean s : foodList) {
            if (s.getId() == targetValue)
                return true;
        }
        return false;
    }

    class AdapterMenu extends RecyclerView.Adapter<AdapterMenu.ViewHolder> {


        private List<MenuSlideBean> typeList;

        public AdapterMenu(List<MenuSlideBean> foodList) {
            this.typeList = foodList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_type_list_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            MenuSlideBean menuSlideBean = typeList.get(position);
            holder.name.setText(menuSlideBean.getName());

            if (menuSlideBean.isSelect()) {
                holder.name.setBackgroundColor(Color.WHITE);
            } else {
                holder.name.setBackgroundColor(Color.parseColor("#FDF7EF"));
            }

            holder.name.setOnClickListener(v -> {

                for (MenuSlideBean bean : typeList) {
                    bean.setSelect(false);
                }
                menuSlideBean.setSelect(true);
                viewBinding.menuList.smoothScrollToPosition(position);
                notifyDataSetChanged();
                //msAdapter.notifyDataSetChanged();

            });
        }

        @Override
        public int getItemCount() {
            return typeList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView name;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.name);
            }
        }
    }


    class AdapterEatList extends RecyclerView.Adapter<AdapterEatList.ViewHolder> {

        private List<MenuSlideBean> typeList;

        public AdapterEatList(List<MenuSlideBean> typeList) {
            this.typeList = typeList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eat_recyclerview_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            msAdapter = new MsAdapter<FoodListNewBean.DataDTO>(typeList.get(position).getFoodList(), R.layout.car2_menu_item) {
                @SuppressLint("SetTextI18n")
                @Override
                public void bindView(ViewHolder holder, FoodListNewBean.DataDTO obj) {
                    TextView price, number, tv_name;
                    ImageView image, remove, add;
                    price = holder.getView(R.id.price);
                    number = holder.getView(R.id.number);
                    image = holder.getView(R.id.image);
                    remove = holder.getView(R.id.remove);
                    add = holder.getView(R.id.add);
                    tv_name = holder.getView(R.id.tv_name);

                    tv_name.setText(obj.getRFoodName());
                    GlideUtils.load(holder.getItemView().getContext(), obj.getRFoodPic(), image, R.drawable.good_test, 20);
                    price.setText(obj.getRFoodPrice() + "");
                    number.setText(obj.getNumber() + "");
                    remove.setOnClickListener(v -> {

                        if (obj.getRFoodIsorder() == 1) {
                            showToast("售罄");
                            return;
                        }

                        if (obj.getNumber() == 0) {
                            return;
                        }

                        for (CarFoodListBean bean : foodList) {

                            if (bean.getId() == obj.getRFoodId()) {
                                if (bean.getNumber() == 1) {
                                    foodList.remove(bean);
                                } else {
                                    bean.setNumber(bean.getNumber() - 1);
                                }

                                break;
                            }
                        }


                        obj.setNumber(obj.getNumber() - 1);
                        sumTotalMoney();
                        notifyDataSetChanged();
                    });
                    add.setOnClickListener(v -> {

                        if (obj.getRFoodIsorder() == 1) {
                            showToast("售罄");
                            return;
                        }

                        obj.setNumber(obj.getNumber() + 1);
                        CarFoodListBean carFoodListBean = new CarFoodListBean();

                        if (foodList.size() == 0) {
                            carFoodListBean.setId(obj.getRFoodId());
                            carFoodListBean.setImageUrl(obj.getRFoodPic());
                            carFoodListBean.setName(obj.getRFoodName());
                            carFoodListBean.setSinglePrice(obj.getRFoodPrice());
                            carFoodListBean.setPrice(obj.getRFoodPrice());
                            carFoodListBean.setNumber(1);
                            carFoodListBean.setrFoodCanteenId(obj.getRFoodCanteenId() + "");
                            carFoodListBean.setrFoodPackingCharge(obj.getRFoodPackingCharge());
                            foodList.add(carFoodListBean);
                        } else {

                            if (haveIt(foodList, obj.getRFoodId())) {
                                for (int i = 0; i < foodList.size(); i++) {
                                    CarFoodListBean carFood = foodList.get(i);
                                    if (carFood.getId() == obj.getRFoodId()) {
                                        carFood.setNumber(carFood.getNumber() + 1);
                                        carFood.setPrice(carFood.getPrice() + carFood.getSinglePrice());
                                    }
                                }
                            } else {
                                carFoodListBean.setId(obj.getRFoodId());
                                carFoodListBean.setImageUrl(obj.getRFoodPic());
                                carFoodListBean.setName(obj.getRFoodName());
                                carFoodListBean.setSinglePrice(obj.getRFoodPrice());
                                carFoodListBean.setPrice(obj.getRFoodPrice());
                                carFoodListBean.setNumber(1);
                                carFoodListBean.setrFoodCanteenId(obj.getRFoodCanteenId() + "");
                                carFoodListBean.setrFoodPackingCharge(obj.getRFoodPackingCharge());
                                foodList.add(carFoodListBean);
                            }


                        }

                        sumTotalMoney();
                        notifyDataSetChanged();
                    });

//                    if (nowType.equals("当季新品")) {
//                        if (obj.getrFoodNewStatus() == 0) {
//                            holder.getItemView().setVisibility(View.VISIBLE);
//                        } else {
//                            holder.getItemView().setVisibility(View.GONE);
//
//                        }
//                    } else {
//                        if (nowType.equals(obj.getRFoodType())) {
//                            holder.getItemView().setVisibility(View.VISIBLE);
//
//                        } else {
//                            holder.getItemView().setVisibility(View.GONE);
//                        }
//                    }
                }
            };

            holder.grid_list.setAdapter(msAdapter);
        }

        @Override
        public int getItemCount() {
            return typeList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private GridView grid_list;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                grid_list = itemView.findViewById(R.id.grid_list);
            }
        }
    }

    class MenuSlideBean {
        String name;
        boolean select;

        private List<FoodListNewBean.DataDTO> foodList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public List<FoodListNewBean.DataDTO> getFoodList() {
            return foodList;
        }

        public void setFoodList(List<FoodListNewBean.DataDTO> foodList) {
            this.foodList = foodList;
        }
    }

}