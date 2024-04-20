package com.example.medicalservice.activity.shopChildView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.R;
import com.example.medicalservice.activity.ShopActivity;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.ShopTypeBean;
import com.example.medicalservice.databinding.ActivityLogisticsViewBinding;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.OkHttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LogisticsView extends BaseActivity<ActivityLogisticsViewBinding> {


    @Override
    public void initData() {
        super.initData();

        String com = getIntent().getStringExtra("com");
        String num = getIntent().getStringExtra("num");

        viewBinding.back.setOnClickListener(v -> finish());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        viewBinding.logisticsList.setLayoutManager(linearLayoutManager);

        LogistAdapter logistAdapter = new LogistAdapter();
        viewBinding.logisticsList.setAdapter(logistAdapter);

        viewBinding.info.setText("订单号：" + num);
        viewBinding.noData.setVisibility(View.GONE);
        OkHttpUtil.getInstance()
                .doGet(API.queryTrack(com, num), new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        if (response.body() != null) {
                            LogistBean logistBean = new Gson().fromJson(response.body().string(), LogistBean.class);

                            if (logistBean.getCode() == 200) {

                                runOnUiThread(() -> {
                                    if (logistBean.getData().getData() == null) {
                                        viewBinding.noData.setVisibility(View.VISIBLE);
                                        return;
                                    }
                                    //if(logistBean.getData().getData().size()==0)
                                    logistAdapter.setLogistAdapter(logistBean.getData().getData());
                                });
                            } else {
                                runOnUiThread(() -> showToast("未查询到物流信息"));
                            }
                        }


                    }
                });


    }

    class LogistAdapter extends RecyclerView.Adapter<LogistAdapter.ViewHolder> {

        private List<LogistBean.DataDTO.Logists> logists = new ArrayList<>();

        public void setLogistAdapter(List<LogistBean.DataDTO.Logists> logists) {
            this.logists = logists;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public LogistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.logist_item, parent, false);

            return new LogistAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull LogistAdapter.ViewHolder holder, int position) {
            LogistBean.DataDTO.Logists logist = logists.get(position);
            if (logist.getContext().contains("已签收")) {

                holder.status_text.setText("已签收");
                holder.status_point.setCardBackgroundColor(Color.parseColor("#FF6600"));
                holder.time.setTextColor(Color.parseColor("#FF6600"));
                holder.status_text.setTextColor(Color.parseColor("#FF6600"));
                holder.divider.setBackgroundColor(Color.parseColor("#FF6600"));
            } else {
                holder.status_text.setText("运输中");
                holder.status_point.setCardBackgroundColor(Color.GRAY);
                holder.time.setTextColor(Color.GRAY);
                holder.status_text.setTextColor(Color.BLACK);
                holder.divider.setBackgroundColor(Color.GRAY);
            }

            holder.time.setText(logist.getTime());
            holder.context.setText(logist.getContext());


        }


        @Override
        public int getItemCount() {
            return logists.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView status_text, time, context;
            private CardView status_point;
            private View divider;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                status_text = itemView.findViewById(R.id.status_text);

                time = itemView.findViewById(R.id.time);
                context = itemView.findViewById(R.id.context);
                status_point = itemView.findViewById(R.id.status_point);
                divider = itemView.findViewById(R.id.divider);

            }
        }
    }

    class LogistBean {

        private String msg;
        private int code;
        private DataDTO data;

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

        public DataDTO getData() {
            return data;
        }

        public void setData(DataDTO data) {
            this.data = data;
        }

        public class DataDTO {
            private String message;
            private String nu;
            private String ischeck;
            private String com;
            private String status;
            private List<DataDTO.Logists> data;
            private String state;
            private String condition;
            private Object routeInfo;
            private Object returnCode;
            private boolean result;

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getNu() {
                return nu;
            }

            public void setNu(String nu) {
                this.nu = nu;
            }

            public String getIscheck() {
                return ischeck;
            }

            public void setIscheck(String ischeck) {
                this.ischeck = ischeck;
            }

            public String getCom() {
                return com;
            }

            public void setCom(String com) {
                this.com = com;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public List<DataDTO.Logists> getData() {
                return data;
            }

            public void setData(List<DataDTO.Logists> data) {
                this.data = data;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getCondition() {
                return condition;
            }

            public void setCondition(String condition) {
                this.condition = condition;
            }

            public Object getRouteInfo() {
                return routeInfo;
            }

            public void setRouteInfo(Object routeInfo) {
                this.routeInfo = routeInfo;
            }

            public Object getReturnCode() {
                return returnCode;
            }

            public void setReturnCode(Object returnCode) {
                this.returnCode = returnCode;
            }

            public boolean isResult() {
                return result;
            }

            public void setResult(boolean result) {
                this.result = result;
            }

            public class Logists {
                private String time;
                private String context;
                private String ftime;
                private Object areaCode;
                private Object areaName;
                private Object status;
                private Object areaCenter;
                private Object areaPinYin;
                private Object statusCode;

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public String getContext() {
                    return context;
                }

                public void setContext(String context) {
                    this.context = context;
                }

                public String getFtime() {
                    return ftime;
                }

                public void setFtime(String ftime) {
                    this.ftime = ftime;
                }

                public Object getAreaCode() {
                    return areaCode;
                }

                public void setAreaCode(Object areaCode) {
                    this.areaCode = areaCode;
                }

                public Object getAreaName() {
                    return areaName;
                }

                public void setAreaName(Object areaName) {
                    this.areaName = areaName;
                }

                public Object getStatus() {
                    return status;
                }

                public void setStatus(Object status) {
                    this.status = status;
                }

                public Object getAreaCenter() {
                    return areaCenter;
                }

                public void setAreaCenter(Object areaCenter) {
                    this.areaCenter = areaCenter;
                }

                public Object getAreaPinYin() {
                    return areaPinYin;
                }

                public void setAreaPinYin(Object areaPinYin) {
                    this.areaPinYin = areaPinYin;
                }

                public Object getStatusCode() {
                    return statusCode;
                }

                public void setStatusCode(Object statusCode) {
                    this.statusCode = statusCode;
                }
            }
        }
    }
}