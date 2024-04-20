package com.example.medicalservice.activity.cateringServicesActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.AddressCatering;
import com.example.medicalservice.bean.AddressListBean;
import com.example.medicalservice.dataBaseBean.UserBean;
import com.example.medicalservice.databinding.ActivityAddressMangerBinding;
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

public class AddressMangerCatering extends BaseActivity<ActivityAddressMangerBinding> {

    private List<AddressCatering.RowsDTO> addressBeans = new ArrayList<>();

    private AddressAdapter adapter;
    private boolean mangerStatus = false;

    private String controlType;

    private UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void initData() {
        super.initData();

        userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);
        controlType = getIntent().getStringExtra("msg");

        if(controlType.equals("select")){
            viewBinding.title.setText("点击选择发货地址");
        }


        viewBinding.back.setOnClickListener(v -> finish());

        viewBinding.manger.setOnClickListener(v -> {
            mangerStatus = !mangerStatus;
            adapter.notifyDataSetChanged();
        });

        viewBinding.add.setOnClickListener(v -> {
            addDialog("", "", "", "", 0,0);

        });

        getAddressList();
    }

    private void getAddressList() {
        addressBeans.clear();
        OkHttpUtil.getInstance().doGet(API.addressList+"?ownerId="+userBean.getOwnerId(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                activity.runOnUiThread(() -> showToast("请求失败"));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    AddressCatering addressListBean = new Gson().fromJson(response.body().string(), AddressCatering.class);
                    if (addressListBean.getCode() != 200) {
                        activity.runOnUiThread(() -> showToast("请求失败"));
                        return;
                    }

                    addressBeans = addressListBean.getRows();

                    runOnUiThread(() -> {
                        adapter = new AddressAdapter();
                        viewBinding.addressList.setAdapter(adapter);
                    });


                }


            }
        });
    }

    @Override
    public void initView() {
        super.initView();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        viewBinding.addressList.setLayoutManager(linearLayoutManager);
    }


    private void addDialog(String contact, String phoneNumber, String areaName, String addressDetail, int defaultValue,int id ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Dialog_style);
        View view = LayoutInflater.from(activity).inflate(R.layout.address_add_dialog, null);

        builder.setView(view);
        TextView left_btn, right_btn;
        EditText name, phone, area, address;
        SwitchCompat isDefault;
        left_btn = view.findViewById(R.id.yes);
        right_btn = view.findViewById(R.id.no);
        name = view.findViewById(R.id.name);
        phone = view.findViewById(R.id.phone);
        area = view.findViewById(R.id.area);
        address = view.findViewById(R.id.address);
        isDefault = view.findViewById(R.id.isDefault);

        if (!contact.isEmpty()) {
            name.setText(contact);
            phone.setText(phoneNumber);
            area.setText(areaName);
            address.setText(addressDetail);
            isDefault.setChecked(defaultValue == 1);
        }


        AlertDialog alertDialog = builder.create();
        alertDialog.show();


        left_btn.setOnClickListener(v1 -> {
            alertDialog.dismiss();
        });

        right_btn.setOnClickListener(v1 -> {
            if (name.getText().toString().isEmpty() ||
                    phone.getText().toString().isEmpty() ||
                    area.getText().toString().isEmpty() ||
                    address.getText().toString().isEmpty()
            ) {
                showToast("请填写完整");
                return;
            }

            AddressCatering.RowsDTO rowsDTO = new AddressCatering.RowsDTO();
            rowsDTO.setUserId(Integer.parseInt(MyApplication.getInstance().getUserId()));
            rowsDTO.setOwnerId(userBean.getOwnerId());
            rowsDTO.setRFoodRecipientName(name.getText().toString());
            rowsDTO.setRFoodLocation(area.getText().toString());
            rowsDTO.setRFoodPhoneNumber(phone.getText().toString());
            rowsDTO.setRFoodDetailedAddress(address.getText().toString());
            rowsDTO.setRFoodIsDefault(isDefault.isChecked() ? 1 : 0);

            if (!contact.isEmpty()) {
                rowsDTO.setId(id);
                OkHttpUtil.getInstance().doPut(API.address, new Gson().toJson(rowsDTO), new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        alertDialog.dismiss();

                        getAddressList();
                    }
                });
                return;
            }
            OkHttpUtil.getInstance().doPost(API.address, new Gson().toJson(rowsDTO), new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    alertDialog.dismiss();

                    getAddressList();
                }
            });
        });


    }

    class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

            AddressCatering.RowsDTO addressBean = addressBeans.get(position);

            holder.firstName.setText(addressBean.getRFoodRecipientName().substring(0, 1));
            if (addressBean.getRFoodIsDefault() == 1) {
                holder.default_status.setVisibility(View.VISIBLE);
                holder.firstName.setText("默");
            } else {
                holder.default_status.setVisibility(View.GONE);
            }

            if (mangerStatus) {
                holder.edit.setImageResource(R.drawable.baseline_delete_24);

                holder.edit.setOnClickListener(v -> OkHttpUtil.getInstance().doDelete(API.address + "/" + addressBean.getId(), new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        runOnUiThread(() -> {
                            addressBeans.remove(position);
                            notifyDataSetChanged();
                        });

                    }
                }));
            } else {
                holder.edit.setImageResource(R.drawable.edit);
                holder.edit.setOnClickListener(v -> addDialog(addressBean.getRFoodRecipientName(),addressBean.getRFoodPhoneNumber(),addressBean.getRFoodLocation(),addressBean.getRFoodDetailedAddress(),addressBean.getRFoodIsDefault(),addressBean.getId()));
            }


            if(controlType.equals("select")){
                holder.address.setOnClickListener(v -> {
                    GetOrderForShop.getInstance().setAddressId(String.valueOf(addressBean.getId()));
                    GetOrderForShop.getInstance().setAddress(addressBean.getRFoodLocation() + addressBean.getRFoodDetailedAddress());
                    GetOrderForShop.getInstance().setPhoneNumber(addressBean.getRFoodPhoneNumber());
                    GetOrderForShop.getInstance().setName(addressBean.getRFoodRecipientName());
                    finish();
                });
            }

            holder.address.setText(addressBean.getRFoodLocation() + addressBean.getRFoodDetailedAddress() + "  （" + addressBean.getRFoodRecipientName() + "）  " + addressBean.getRFoodPhoneNumber());
        }

        @Override
        public int getItemCount() {
            return addressBeans.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView firstName, address, default_status;
            ImageView edit;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                firstName = itemView.findViewById(R.id.first_name);
                address = itemView.findViewById(R.id.address);
                default_status = itemView.findViewById(R.id.default_address);
                edit = itemView.findViewById(R.id.edit);
            }
        }
    }



}