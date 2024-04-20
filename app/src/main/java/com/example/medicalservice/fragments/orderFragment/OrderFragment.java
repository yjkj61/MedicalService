package com.example.medicalservice.fragments.orderFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.activity.propertyFiveBlessingsActivity.JudgeView;
import com.example.medicalservice.baseFile.BaseFragment;
import com.example.medicalservice.bean.Code200;
import com.example.medicalservice.bean.OrderBean;
import com.example.medicalservice.bean.ProperTypeBean;
import com.example.medicalservice.dataBaseBean.UserBean;
import com.example.medicalservice.databinding.FragmentOrderBinding;
import com.example.medicalservice.interfaceCallback.OnClickByType;
import com.example.medicalservice.recycleAdapter.OrderAdapter;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.OkHttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends BaseFragment<FragmentOrderBinding> {

    private String mParam1;

    private String mParam2;
    OrderAdapter orderAdapter;
    private List<PropertyOrderBean> propertyOrderBeans = new ArrayList<>();

    public OrderFragment() {
        // Required empty public constructor
    }


    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putString("params1", param1);
        args.putString("params2", param2);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString("params1");
            mParam2 = getArguments().getString("params2");
        }
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {
        super.initData();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        viewBinding.orderList.setLayoutManager(linearLayoutManager);

         orderAdapter = new OrderAdapter(propertyOrderBeans);


        viewBinding.orderList.setAdapter(orderAdapter);

        try {
            getOrderList();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        orderAdapter.setOnItemClickListener((position, type) -> {
            if (type.equals("取消预约")) {
                try {
                    deleteDialog(propertyOrderBeans.get(position).getBookNum());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            if(type.equals("去评价")){
                go(JudgeView.class,propertyOrderBeans.get(position).getBookNum());
            }
        });
    }

    private void getOrderList() throws JSONException {

        propertyOrderBeans.clear();

        UserBean userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);

        if (userBean == null) return;


        JSONObject jsonString = new JSONObject();

        jsonString.put("ownerUserId", userBean.getOwnerId());

        jsonString.put("serviceStatus", mParam2);

        Log.d("TAG", "getOrderList: " + jsonString.toString());

        OkHttpUtil.getInstance().doPost(API.selectBookListByOwnerUserIdAndStatus, jsonString.toString(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    JsonArray jsonArray = JsonParser.parseString(response.body().string()).getAsJsonArray();
                    Gson gson = new Gson();
                    for (JsonElement jsonElement : jsonArray) {
                        PropertyOrderBean properTypeBean = gson.fromJson(jsonElement, PropertyOrderBean.class);
                        propertyOrderBeans.add(properTypeBean);
                    }

                    activity.runOnUiThread(() -> orderAdapter.notifyDataSetChanged());
                }

            }
        });
    }

    private void deleteDialog(String bookNum) throws JSONException {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Dialog_style);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_delete, null);

        builder.setView(view);
        TextView left_btn, right_btn, text;
        left_btn = view.findViewById(R.id.yes);
        right_btn = view.findViewById(R.id.no);
        text = view.findViewById(R.id.tip_text);

        left_btn.setText("是，取消预约");
        left_btn.setText("否，我在想想");


        text.setText("是否取消预约");

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("bookNum", bookNum);
        jsonObject.put("serviceStatus", 4);

        left_btn.setOnClickListener(v1 -> OkHttpUtil.getInstance().doPost(API.updateBookByBookNum, jsonObject.toString(), new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        if (response.body() != null) {
                            Code200 code200 = new Gson().fromJson(response.body().string(), Code200.class);

                            if (code200.getCode() == 200) {

                                try {
                                    getOrderList();
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                })
        );

        right_btn.setOnClickListener(v1 -> alertDialog.dismiss());


    }

    public class PropertyOrderBean {

        private String createBy;
        private String createTime;
        private String updateBy;
        private String updateTime;
        private String remark;
        private int bookId;
        private String bookNum;
        private int ownerUserId;
        private int ownerId;
        private String ownerName;
        private String ownerSex;
        private int ownerAge;
        private String ownerPhone;
        private String agentUserId;
        private String agentId;
        private String agentName;
        private String agentPhone;
        private String workerUserId;
        private String workerId;
        private String workerName;
        private String workerPhone;
        private int sTypeId;
        private String projectName;
        private String ownerAddress;
        private String serviceBookStartTime;
        private String serviceBookEndTime;
        private String sTypeLogo;
        private String firstElsePropertyValue;
        private String secondElsePropertyValue;
        private String thirdElsePropertyValue;
        private String bookRemark;
        private String bookStartPic;
        private String bookEndPic;
        private int serviceStatus;
        private String bookTime;
        private String servicerMoney;
        private String bookMoney;
        private String bookStartTime;
        private String bookFinishTime;
        private String payWay;
        private int payStatus;
        private String bookSatisfaction;
        private String bookAdvice;

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getBookId() {
            return bookId;
        }

        public String getsTypeLogo() {
            return sTypeLogo;
        }

        public void setsTypeLogo(String sTypeLogo) {
            this.sTypeLogo = sTypeLogo;
        }

        public void setBookId(int bookId) {
            this.bookId = bookId;
        }

        public String getBookNum() {
            return bookNum;
        }

        public void setBookNum(String bookNum) {
            this.bookNum = bookNum;
        }

        public int getOwnerUserId() {
            return ownerUserId;
        }

        public void setOwnerUserId(int ownerUserId) {
            this.ownerUserId = ownerUserId;
        }

        public int getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(int ownerId) {
            this.ownerId = ownerId;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public String getOwnerSex() {
            return ownerSex;
        }

        public void setOwnerSex(String ownerSex) {
            this.ownerSex = ownerSex;
        }

        public int getOwnerAge() {
            return ownerAge;
        }

        public void setOwnerAge(int ownerAge) {
            this.ownerAge = ownerAge;
        }

        public String getOwnerPhone() {
            return ownerPhone;
        }

        public void setOwnerPhone(String ownerPhone) {
            this.ownerPhone = ownerPhone;
        }

        public String getAgentUserId() {
            return agentUserId;
        }

        public void setAgentUserId(String agentUserId) {
            this.agentUserId = agentUserId;
        }

        public String getAgentId() {
            return agentId;
        }

        public void setAgentId(String agentId) {
            this.agentId = agentId;
        }

        public String getAgentName() {
            return agentName;
        }

        public void setAgentName(String agentName) {
            this.agentName = agentName;
        }

        public String getAgentPhone() {
            return agentPhone;
        }

        public void setAgentPhone(String agentPhone) {
            this.agentPhone = agentPhone;
        }

        public String getWorkerUserId() {
            return workerUserId;
        }

        public void setWorkerUserId(String workerUserId) {
            this.workerUserId = workerUserId;
        }

        public String getWorkerId() {
            return workerId;
        }

        public void setWorkerId(String workerId) {
            this.workerId = workerId;
        }

        public String getWorkerName() {
            return workerName;
        }

        public void setWorkerName(String workerName) {
            this.workerName = workerName;
        }

        public String getWorkerPhone() {
            return workerPhone;
        }

        public void setWorkerPhone(String workerPhone) {
            this.workerPhone = workerPhone;
        }

        public int getSTypeId() {
            return sTypeId;
        }

        public void setSTypeId(int sTypeId) {
            this.sTypeId = sTypeId;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getOwnerAddress() {
            return ownerAddress;
        }

        public void setOwnerAddress(String ownerAddress) {
            this.ownerAddress = ownerAddress;
        }

        public String getServiceBookStartTime() {
            return serviceBookStartTime;
        }

        public void setServiceBookStartTime(String serviceBookStartTime) {
            this.serviceBookStartTime = serviceBookStartTime;
        }

        public String getServiceBookEndTime() {
            return serviceBookEndTime;
        }

        public void setServiceBookEndTime(String serviceBookEndTime) {
            this.serviceBookEndTime = serviceBookEndTime;
        }

        public String getFirstElsePropertyValue() {
            return firstElsePropertyValue;
        }

        public void setFirstElsePropertyValue(String firstElsePropertyValue) {
            this.firstElsePropertyValue = firstElsePropertyValue;
        }

        public String getSecondElsePropertyValue() {
            return secondElsePropertyValue;
        }

        public void setSecondElsePropertyValue(String secondElsePropertyValue) {
            this.secondElsePropertyValue = secondElsePropertyValue;
        }

        public String getThirdElsePropertyValue() {
            return thirdElsePropertyValue;
        }

        public void setThirdElsePropertyValue(String thirdElsePropertyValue) {
            this.thirdElsePropertyValue = thirdElsePropertyValue;
        }

        public String getBookRemark() {
            return bookRemark;
        }

        public void setBookRemark(String bookRemark) {
            this.bookRemark = bookRemark;
        }

        public String getBookStartPic() {
            return bookStartPic;
        }

        public void setBookStartPic(String bookStartPic) {
            this.bookStartPic = bookStartPic;
        }

        public String getBookEndPic() {
            return bookEndPic;
        }

        public void setBookEndPic(String bookEndPic) {
            this.bookEndPic = bookEndPic;
        }

        public int getServiceStatus() {
            return serviceStatus;
        }

        public void setServiceStatus(int serviceStatus) {
            this.serviceStatus = serviceStatus;
        }

        public String getBookTime() {
            return bookTime;
        }

        public void setBookTime(String bookTime) {
            this.bookTime = bookTime;
        }

        public String getServicerMoney() {
            return servicerMoney;
        }

        public void setServicerMoney(String servicerMoney) {
            this.servicerMoney = servicerMoney;
        }

        public String getBookMoney() {
            return bookMoney;
        }

        public void setBookMoney(String bookMoney) {
            this.bookMoney = bookMoney;
        }

        public String getBookStartTime() {
            return bookStartTime;
        }

        public void setBookStartTime(String bookStartTime) {
            this.bookStartTime = bookStartTime;
        }

        public String getBookFinishTime() {
            return bookFinishTime;
        }

        public void setBookFinishTime(String bookFinishTime) {
            this.bookFinishTime = bookFinishTime;
        }

        public String getPayWay() {
            return payWay;
        }

        public void setPayWay(String payWay) {
            this.payWay = payWay;
        }

        public int getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(int payStatus) {
            this.payStatus = payStatus;
        }

        public String getBookSatisfaction() {
            return bookSatisfaction;
        }

        public void setBookSatisfaction(String bookSatisfaction) {
            this.bookSatisfaction = bookSatisfaction;
        }

        public String getBookAdvice() {
            return bookAdvice;
        }

        public void setBookAdvice(String bookAdvice) {
            this.bookAdvice = bookAdvice;
        }
    }
}