package com.example.medicalservice.recycleAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.activity.shopChildView.LogisticsView;
import com.example.medicalservice.activity.shopChildView.SubmitOrderView;
import com.example.medicalservice.bean.Code200;
import com.example.medicalservice.bean.JudegeBean;
import com.example.medicalservice.bean.OrderBean;
import com.example.medicalservice.bean.OrderBeanList;
import com.example.medicalservice.bean.ShopOrderBean;
import com.example.medicalservice.interfaceCallback.OnClickListener;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.OkHttpUtil;
import com.example.medicalservice.tools.ToastUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShopOrderAdapter extends RecyclerView.Adapter<ShopOrderAdapter.ViewHolder> {

    private List<OrderBeanList.RowsDTO.SOrderPoListDTO> orderBeanList;

    public ShopOrderAdapter(List<OrderBeanList.RowsDTO.SOrderPoListDTO> orderBeanList) {
        this.orderBeanList = orderBeanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_order_item, parent, false);

        return new ViewHolder(view);
    }

    private OnClickListener onClickListener;

    public void setOnItemClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderBeanList.RowsDTO.SOrderPoListDTO sOrderPoListDTO = orderBeanList.get(position);
        holder.store_name.setText(sOrderPoListDTO.getBusinessName());
        holder.good_name.setText(sOrderPoListDTO.getGoodsName());
        holder.good_info.setText("型号:" + sOrderPoListDTO.getSGoodsSpecificationsName());
        holder.price.setText(sOrderPoListDTO.getGoodsPrice());
        holder.count.setText("x" + sOrderPoListDTO.getGoodsNumber());
        holder.total_price.setText(sOrderPoListDTO.getOrderPrice());
        holder.final_price.setText(sOrderPoListDTO.getOrderPrice());
        holder.address.setText(sOrderPoListDTO.getAddress());
        holder.time.setText(sOrderPoListDTO.getFinishOrderTime());

        switch (sOrderPoListDTO.getStatus()) {
            case "0":
                holder.status.setText("待支付");
                holder.look.setVisibility(View.GONE);
                holder.submit.setVisibility(View.GONE);
                break;
            case "1":
                holder.status.setText("待退款");
                holder.submit.setVisibility(View.GONE);

            case "2":
                holder.status.setText("已退款");
                holder.look.setVisibility(View.GONE);
                holder.submit.setVisibility(View.GONE);
            case "3":
                holder.status.setText("待发货");
                holder.look.setVisibility(View.GONE);
                holder.submit.setVisibility(View.GONE);
                break;
            case "4":
                holder.status.setText("已发货");
                holder.submit.setVisibility(View.GONE);
                break;

            case "5":
                holder.status.setText("待收货");
                holder.submit.setText("确认收货");
                holder.submit.setOnClickListener(v -> {
                    sOrderPoListDTO.setStatus("6");
                    deleteDialog(sOrderPoListDTO, holder.itemView.getContext());

                });
                break;
            case "6":
                holder.status.setText("待评价");
                holder.submit.setText("去评价");

                holder.submit.setOnClickListener(v -> {

                    Intent intent = new Intent(holder.itemView.getContext(), SubmitOrderView.class);
                    JudegeBean judegeBean = new JudegeBean();
                    judegeBean.setOrderId(sOrderPoListDTO.getSOrderId());
                    judegeBean.setFirstCommentatorId(MyApplication.getInstance().getUserId());
                    judegeBean.setFirstCommentatorName(MyApplication.getInstance().getUserName());
                    judegeBean.setsGoodsBusinessId(sOrderPoListDTO.getSGoodsBusinessId());
                    judegeBean.setsGoodsId(sOrderPoListDTO.getSGoodsId());
                    intent.putExtra("judegeBean", judegeBean);
                    intent.putExtra("type", "JudegeFragment");
                    holder.itemView.getContext().startActivity(intent);
                });
                break;
            case "7":
                holder.status.setText("已取消");
                holder.look.setVisibility(View.GONE);
                holder.submit.setVisibility(View.GONE);
                break;
            case "8":
                holder.status.setText("已完成");
                holder.submit.setVisibility(View.GONE);
                break;
            case "9":
                holder.status.setText("售后中");
                holder.look.setVisibility(View.GONE);
                holder.submit.setVisibility(View.GONE);
                break;

        }


        holder.look.setOnClickListener(v -> {

            String com = "";
            String num = "";
            for (OrderBeanList.RowsDTO.SOrderPoListDTO.SOrderSendGoodsDTO orderSeed :
                    sOrderPoListDTO.getsOrderSendGoodsList()) {
                if (sOrderPoListDTO.getSOrderId().equals(orderSeed.getsOrderId()) && sOrderPoListDTO.getSGoodsId().equals(orderSeed.getsGoodsId())) {
                    com = orderSeed.getLogisticsCompany();
                    num = orderSeed.getTrackingNumber();
                }
            }

            if (!com.isEmpty() && !num.isEmpty()) {
                Intent intent = new Intent(holder.itemView.getContext(), LogisticsView.class);
                intent.putExtra("com", com);
                intent.putExtra("num", num);

                holder.itemView.getContext().startActivity(intent);
            } else {
                ToastUtil.showToast("无物流信息", holder.itemView.getContext());
            }


        });

        GlideUtils.load(holder.itemView.getContext(), sOrderPoListDTO.getGoodsCoverImages(), holder.good_image, R.drawable.good_test, 1);
    }

    private void deleteDialog(OrderBeanList.RowsDTO.SOrderPoListDTO bean, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Dialog_style);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_delete, null);

        builder.setView(view);
        TextView left_btn, right_btn, text;
        left_btn = view.findViewById(R.id.yes);
        right_btn = view.findViewById(R.id.no);
        text = view.findViewById(R.id.tip_text);

        left_btn.setText("是的，确认");
        right_btn.setText("否，再想想");

        text.setText("是否确认收货");

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


        left_btn.setOnClickListener(v1 -> {
            OkHttpUtil.getInstance().doPut(API.orderEdit, new Gson().toJson(bean), new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                    if (response.body() != null) {

                        Code200 code200 = new Gson().fromJson(response.body().string(), Code200.class);
                        if (code200 != null && code200.getCode() == 200) {
                            onClickListener.OnItemClick(0);

                        }


                    }
                }
            });
            alertDialog.dismiss();
        });

        right_btn.setOnClickListener(v1 -> alertDialog.dismiss());


    }

    @Override
    public int getItemCount() {
        return orderBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView store_name, good_name, good_info, price, count, total_price, final_price, address, time, status, look, submit;
        private ImageView good_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            status = itemView.findViewById(R.id.status);
            store_name = itemView.findViewById(R.id.store_name);
            good_name = itemView.findViewById(R.id.good_name);
            good_info = itemView.findViewById(R.id.good_info);
            price = itemView.findViewById(R.id.price);
            count = itemView.findViewById(R.id.count);
            total_price = itemView.findViewById(R.id.total_price);
            final_price = itemView.findViewById(R.id.final_price);
            address = itemView.findViewById(R.id.address);
            time = itemView.findViewById(R.id.time);
            good_image = itemView.findViewById(R.id.good_image);
            look = itemView.findViewById(R.id.look);
            submit = itemView.findViewById(R.id.submit);

        }
    }
}
