package com.example.medicalservice.fragments;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.R;
import com.example.medicalservice.activity.ShopActivity;
import com.example.medicalservice.activity.shopChildView.GoodDetailView;
import com.example.medicalservice.activity.shopChildView.GoodSearchView;
import com.example.medicalservice.baseFile.BaseFragment;
import com.example.medicalservice.bean.GoodBean;
import com.example.medicalservice.bean.GoodDetailBean;
import com.example.medicalservice.databinding.FragmentMallGroupBuyingBinding;
import com.example.medicalservice.recycleAdapter.BannerAdapter;
import com.example.medicalservice.recycleAdapter.GoodsListAdapter;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.OkHttpUtil;
import com.google.gson.Gson;
import com.youth.banner.listener.OnPageChangeListener;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MallGroupBuying extends BaseFragment<FragmentMallGroupBuyingBinding> {


    // TODO: Rename parameter arguments, choose names that match


    public MallGroupBuying() {
        // Required empty public constructor
    }

    public static MallGroupBuying newInstance() {
        MallGroupBuying fragment = new MallGroupBuying();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        super.initView();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        viewBinding.goodList.setLayoutManager(linearLayoutManager);

        viewBinding.shop.setOnClickListener(v -> go(ShopActivity.class));

        viewBinding.shopTogether.setOnClickListener(v -> showToast("敬请期待"));

        viewBinding.seeAll.setOnClickListener(v -> go(GoodSearchView.class, "限时抢购"));

    }


    @Override
    public void onResume() {
        super.onResume();
        searchGoodList();
        banner();
    }

    private void banner() {
        OkHttpUtil.getInstance().doGet(API.rotationImage, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    RotationBean rotationBean = new Gson().fromJson(response.body().string(), RotationBean.class);

                    if (rotationBean.getCode() == 200) {

                        activity.runOnUiThread(() -> {
                            BannerRotationAdapter bannerRotationAdapter = new BannerRotationAdapter(rotationBean.getData());
                            viewBinding.banner.addBannerLifecycleObserver(getViewLifecycleOwner())
                                    .setAdapter(bannerRotationAdapter)
                                    .addOnPageChangeListener(new OnPageChangeListener() {
                                        @Override
                                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                        }

                                        @Override
                                        public void onPageSelected(int position) {

                                        }

                                        @Override
                                        public void onPageScrollStateChanged(int state) {

                                        }
                                    });
                        });
                    }
                }

            }
        });
    }

    private void searchGoodList() {
        OkHttpUtil.getInstance().doGet(API.limitedTime, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() != null) {
                    String json = response.body().string();

                    if (json.isEmpty()) return;
                    LimitGood goodBean = new Gson().fromJson(json, LimitGood.class);
                    if (goodBean.getCode() == 200) {

                        activity.runOnUiThread(() -> viewBinding.goodList.setAdapter(new GoodsListAdapter(goodBean.getData(), activity)));

                    } else {
                        activity.runOnUiThread(() -> showToast("请求失败,请联系管理员"));
                    }
                }

            }
        });
    }


    class BannerRotationAdapter extends com.youth.banner.adapter.BannerAdapter<RotationBean.DataDTO, BannerRotationAdapter.ViewHolder> {


        public BannerRotationAdapter(List<RotationBean.DataDTO> datas) {
            super(datas);
        }

        @Override
        public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_banner_full, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindView(ViewHolder holder, RotationBean.DataDTO data, int position, int size) {

            GlideUtils.load(activity, data.getRotationImage(), holder.imageView);


            holder.imageView.setOnClickListener(v -> {
                if (data.getRotationImageType().equals("0")) {
                    go(GoodDetailView.class, data.getForeignId());
                }

                if (data.getRotationImageType().equals("1")) {
                    go(GoodSearchView.class, data.getForeignId(), "store");
                }
            });

        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private final ImageView imageView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.image);
            }
        }
    }

    public class LimitGood {

        private String msg;
        private int code;
        private List<GoodBean.RowsDTO> data;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public List<GoodBean.RowsDTO> getData() {
            return data;
        }

        public void setData(List<GoodBean.RowsDTO> data) {
            this.data = data;
        }

    }

    public class RotationBean {

        private String msg;
        private int code;
        private List<DataDTO> data;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public List<DataDTO> getData() {
            return data;
        }

        public void setData(List<DataDTO> data) {
            this.data = data;
        }

        public class DataDTO {
            private String rotationImageType;
            private String foreignId;
            private String rotationImage;

            public String getRotationImageType() {
                return rotationImageType;
            }

            public void setRotationImageType(String rotationImageType) {
                this.rotationImageType = rotationImageType;
            }

            public String getForeignId() {
                return foreignId;
            }

            public void setForeignId(String foreignId) {
                this.foreignId = foreignId;
            }

            public String getRotationImage() {
                return rotationImage;
            }

            public void setRotationImage(String rotationImage) {
                this.rotationImage = rotationImage;
            }
        }
    }

}

