package com.example.medicalservice.fragments.submitOrderFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.medicalservice.R;
import com.example.medicalservice.activity.shopChildView.AddressManger;
import com.example.medicalservice.activity.shopChildView.SubmitOrderView;
import com.example.medicalservice.baseFile.BaseFragment;
import com.example.medicalservice.bean.AddressListBean;
import com.example.medicalservice.bean.BuyGoodBean;
import com.example.medicalservice.databinding.FragmentSureAddressBinding;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.GetOrderForShop;
import com.example.medicalservice.tools.OkHttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class SureAddressFragment extends BaseFragment<FragmentSureAddressBinding> {

    private List<AddressListBean.RowsDTO> addressBeans = new ArrayList<>();

    AddressAdapter adapter;

    private String addressId = "";

    public SureAddressFragment() {
        // Required empty public constructor
    }


    public static SureAddressFragment newInstance(String addressId) {
        SureAddressFragment fragment = new SureAddressFragment();
        Bundle args = new Bundle();
        args.putString("addressId", addressId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            addressId = getArguments().getString("addressId");

            if (addressId == null) {
                addressId = "";
            }

            Log.d("TAG", "onCreate: " + addressId);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        getAddressList();
    }

    @Override
    public void initView() {
        super.initView();

        viewBinding.managerAddress.setOnClickListener(v -> go(AddressManger.class, "manager"));

        viewBinding.nextStep.setOnClickListener(v -> {

            if (addressBeans.size() == 0) {
                showToast("未选择任何地址");
                return;
            }


            boolean alreadySelect = false;
            for (int i = 0; i < addressBeans.size(); i++) {

                if (addressBeans.get(i).isSelect()) {
                    alreadySelect = addressBeans.get(i).isSelect();
                    break;
                }
            }

            if (alreadySelect) {
                SubmitOrderView.mActivity.tabSwitch(1);
            } else {
                showToast("未选择任何地址");

            }

        });
    }

    @Override
    public void initData() {
        super.initData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        viewBinding.addressList.setLayoutManager(linearLayoutManager);

    }

    private void getAddressList() {
        addressBeans.clear();
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

                    addressBeans = addressListBean.getRows();

                    activity.runOnUiThread(() -> {
                        adapter = new AddressAdapter();
                        viewBinding.addressList.setAdapter(adapter);
                    });


                }


            }
        });
    }

    class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

        @NonNull
        @Override
        public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

            AddressListBean.RowsDTO addressBean = addressBeans.get(position);

            holder.firstName.setText(addressBean.getContact().substring(0, 1));
            if (addressBean.getIsDefaultAddress() == 1) {
                holder.default_status.setVisibility(View.VISIBLE);
                holder.firstName.setText("默");
            } else {
                holder.default_status.setVisibility(View.GONE);
            }
            holder.status.setVisibility(View.VISIBLE);


            if (addressId.equals(String.valueOf(addressBean.getSAreaId()))) {
                addressBean.setSelect(true);
                holder.status.setImageResource(R.drawable.select_address);
                holder.addressBox.setBackgroundResource(R.drawable.my_fav_item_fav_num);

                ScaleAnimation scaleAnim = new ScaleAnimation(1f, 1.1f, 1f, 1.1f, 0.5f, 0.5f);
                scaleAnim.setDuration(200);
                scaleAnim.setFillAfter(true);
                holder.itemView.startAnimation(scaleAnim);

                List<BuyGoodBean> buyGoodBeans = GetOrderForShop.getInstance().getBuyGoodBeans();
                buyGoodBeans.forEach(item -> {
                    item.setAddress(addressBean.getArea() + addressBean.getAddress());
                    item.setContact(addressBean.getContact());
                    item.setPhone(addressBean.getPhone());

                });


                GetOrderForShop.getInstance().setBuyGoodBeans(buyGoodBeans);
            } else {
                addressBean.setSelect(false);
                holder.status.setImageResource(R.drawable.unselect_address);
                holder.addressBox.setBackgroundResource(R.drawable.nothing_back);
            }


            if (addressId.isEmpty() && position == 0) {
                holder.status.setImageResource(R.drawable.select_address);
                holder.addressBox.setBackgroundResource(R.drawable.my_fav_item_fav_num);
                addressBean.setSelect(true);
                ScaleAnimation scaleAnim = new ScaleAnimation(1f, 1.1f, 1f, 1.1f, 0.5f, 0.5f);
                scaleAnim.setDuration(200);
                scaleAnim.setFillAfter(true);
                holder.itemView.startAnimation(scaleAnim);

                List<BuyGoodBean> buyGoodBeans = GetOrderForShop.getInstance().getBuyGoodBeans();
                buyGoodBeans.forEach(item -> {
                    item.setAddress(addressBean.getArea() + addressBean.getAddress());
                    item.setContact(addressBean.getContact());
                    item.setPhone(addressBean.getPhone());

                });

                GetOrderForShop.getInstance().setBuyGoodBeans(buyGoodBeans);
            }

            holder.itemView.setOnClickListener(v -> {
                addressId = String.valueOf(addressBean.getSAreaId());

                notifyDataSetChanged();
            });

            holder.edit.setVisibility(View.GONE);

            holder.address.setText(addressBean.getArea() + addressBean.getAddress() + "  （" + addressBean.getContact() + "）  " + addressBean.getPhone());
        }

        @Override
        public int getItemCount() {
            return addressBeans.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView firstName, address, default_status;
            ImageView edit, status;

            LinearLayout addressBox;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                status = itemView.findViewById(R.id.status);
                firstName = itemView.findViewById(R.id.first_name);
                address = itemView.findViewById(R.id.address);
                default_status = itemView.findViewById(R.id.default_address);
                edit = itemView.findViewById(R.id.edit);

                addressBox = itemView.findViewById(R.id.address_box);
            }
        }
    }


}