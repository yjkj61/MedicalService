package com.example.medicalservice.recycleAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.bean.EatOrderListBean;
import com.example.medicalservice.dataBaseBean.UserBean;
import com.example.medicalservice.interfaceCallback.OnClickListener;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.OkHttpUtil;
import com.example.medicalservice.tools.ToastUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class EatOrderListAdapter extends RecyclerView.Adapter<EatOrderListAdapter.ViewHolder> {

    private List<EatOrderListBean.RowsDTO> rowsDTOS;

    public EatOrderListAdapter(List<EatOrderListBean.RowsDTO> rowsDTOS) {
        this.rowsDTOS = rowsDTOS;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eat_order_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EatOrderListBean.RowsDTO rowsDTO = rowsDTOS.get(position);

        holder.storeName.setText(rowsDTO.getRFoodCanteenName());
        holder.orderTime.setText("下单时间：" + rowsDTO.getROrderTime());
        holder.getTime.setText("送达时间：" + rowsDTO.getROrderCompletionTime() == null ? "未送达":rowsDTO.getROrderCompletionTime());
        holder.money.setText(rowsDTO.getROrderPrice());
        holder.afterSale.setVisibility(View.GONE);
        holder.judge.setVisibility(View.GONE);


        switch (rowsDTO.getROrderStatus()) {
            case 0:
                holder.status.setText("配餐中");
                break;
            case 1:
                holder.status.setText("待收货");
                break;
            case 2:
                holder.afterSale.setVisibility(View.VISIBLE);
                holder.judge.setVisibility(View.VISIBLE);
                holder.status.setText("待评价");
                //售后
                holder.afterSale.setOnClickListener(v -> {
                    addDialog("请填写退款原因",holder.itemView.getContext(),rowsDTO);
                });

                //评价
                holder.judge.setOnClickListener(v -> {
                    addDialog("请填写您的评价",holder.itemView.getContext(),rowsDTO);
                });
                break;
            case 3:
                holder.status.setText("已完成");
                break;
            case 4:
                holder.status.setText("售后中");
                break;
            case 5:
                holder.status.setText("已退款");
                break;
            case 6:
                holder.status.setText("已退单");
                break;
        }

        if (rowsDTO.getROrderList() != null) {
            List<ROrder> rOrders = new ArrayList<>();

            try {
                JSONArray jsonArray = new JSONArray(rowsDTO.getROrderList());
                for (int i = 0; i < jsonArray.length(); i++) {
                    ROrder rOrder = new ROrder();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    rOrder.setrOrderCount(jsonObject.getInt("rOrderCount"));
                    rOrder.setrFoodId(jsonObject.getString("rFoodId"));
                    rOrder.setrFoodName(jsonObject.getString("rFoodName"));
                    rOrder.setImg(jsonObject.getString("rFoodPic"));
                    rOrders.add(rOrder);
                }

                MsAdapter msAdapter = new MsAdapter<ROrder>(rOrders, R.layout.eat_order_child_item) {
                    @Override
                    public void bindView(ViewHolder holder, ROrder obj) {

                        ImageView image = holder.getView(R.id.image);
                        TextView name = holder.getView(R.id.name);

                        GlideUtils.load(holder.getItemView().getContext(), obj.getImg(), image);
                        name.setText(obj.getrFoodName());
                    }
                };

                holder.menu_list.setAdapter(msAdapter);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

    }
    private OnClickListener onClickListener;

    public void setOnItemClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private void addDialog(String hint,Context context, EatOrderListBean.RowsDTO rowsDTO) {

        UserBean userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Dialog_style);
        View view = LayoutInflater.from(context).inflate(R.layout.add_dialog, null);

        builder.setView(view);
        TextView left_btn, right_btn;
        EditText name;
        left_btn = view.findViewById(R.id.yes);
        right_btn = view.findViewById(R.id.no);
        name = view.findViewById(R.id.name);

        name.setHint(hint);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        left_btn.setOnClickListener(v1 -> alertDialog.dismiss());

        right_btn.setOnClickListener(v1 -> {
            if (name.getText().toString().isEmpty()
            ) {
                ToastUtil.showToast(hint,context);
                return;
            }

            if(hint.equals("请填写您的评价")){

                OrderEvaluation orderEvaluation = new OrderEvaluation();
                orderEvaluation.setROrderId(rowsDTO.getROrderId());
                orderEvaluation.setRFoodEvaluate(name.getText().toString());
                orderEvaluation.setOwnerId(userBean.getOwnerId());
                orderEvaluation.setMarkerId(rowsDTO.getMarkerId());
                orderEvaluation.setRFoodCanteenId(String.valueOf(rowsDTO.getRFoodCanteenId()));
                OkHttpUtil.getInstance().doPost(API.orderEvaluation, new Gson().toJson(orderEvaluation), new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        onClickListener.OnItemClick(0);
                    }
                });
            }

            if(hint.equals("请填写退款原因")){

                OrderRefundRefund orderRefundRefund = new OrderRefundRefund();
                orderRefundRefund.setROrderRefundReason(name.getText().toString());
                orderRefundRefund.setMarkerId(rowsDTO.getMarkerId());
                orderRefundRefund.setROrderId(rowsDTO.getROrderId());
                orderRefundRefund.setOwnerId(userBean.getOwnerId());
                orderRefundRefund.setRFoodCanteenId(String.valueOf(rowsDTO.getRFoodCanteenId()));

                OkHttpUtil.getInstance().doPost(API.orderRefundRefund, new Gson().toJson(orderRefundRefund), new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        onClickListener.OnItemClick(0);
                    }
                });
            }



            alertDialog.dismiss();

        });


    }


    @Override
    public int getItemCount() {
        return rowsDTOS.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView storeName, orderTime, getTime, money, status,judge,afterSale;

        private GridView menu_list;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            storeName = itemView.findViewById(R.id.store_name);
            orderTime = itemView.findViewById(R.id.order_time);
            getTime = itemView.findViewById(R.id.get_time);
            money = itemView.findViewById(R.id.money);
            status = itemView.findViewById(R.id.status);
            menu_list = itemView.findViewById(R.id.menu_list);

            judge = itemView.findViewById(R.id.judge);
            afterSale = itemView.findViewById(R.id.afterSale);


        }
    }

    class ROrder {
        String rFoodId;
        String rFoodName;
        int rOrderCount;

        String img;

        public String getrFoodId() {
            return rFoodId;
        }

        public void setrFoodId(String rFoodId) {
            this.rFoodId = rFoodId;
        }

        public String getrFoodName() {
            return rFoodName;
        }

        public void setrFoodName(String rFoodName) {
            this.rFoodName = rFoodName;
        }

        public int getrOrderCount() {
            return rOrderCount;
        }

        public void setrOrderCount(int rOrderCount) {
            this.rOrderCount = rOrderCount;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }

    class OrderRefundRefund{

        private String rOrderRefundReason;
        private String rOrderId;
        private String ownerId;
        private String markerId;
        private String rFoodCanteenId;

        public String getROrderRefundReason() {
            return rOrderRefundReason;
        }

        public void setROrderRefundReason(String rOrderRefundReason) {
            this.rOrderRefundReason = rOrderRefundReason;
        }

        public String getROrderId() {
            return rOrderId;
        }

        public void setROrderId(String rOrderId) {
            this.rOrderId = rOrderId;
        }

        public String getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }

        public String getMarkerId() {
            return markerId;
        }

        public void setMarkerId(String markerId) {
            this.markerId = markerId;
        }

        public String getRFoodCanteenId() {
            return rFoodCanteenId;
        }

        public void setRFoodCanteenId(String rFoodCanteenId) {
            this.rFoodCanteenId = rFoodCanteenId;
        }
    }

    class OrderEvaluation{

        private String rFoodEvaluate;
        private String rOrderId;
        private String ownerId;
        private String markerId;
        private String rFoodCanteenId;

        public String getRFoodEvaluate() {
            return rFoodEvaluate;
        }

        public void setRFoodEvaluate(String rFoodEvaluate) {
            this.rFoodEvaluate = rFoodEvaluate;
        }

        public String getROrderId() {
            return rOrderId;
        }

        public void setROrderId(String rOrderId) {
            this.rOrderId = rOrderId;
        }

        public String getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }

        public String getMarkerId() {
            return markerId;
        }

        public void setMarkerId(String markerId) {
            this.markerId = markerId;
        }

        public String getRFoodCanteenId() {
            return rFoodCanteenId;
        }

        public void setRFoodCanteenId(String rFoodCanteenId) {
            this.rFoodCanteenId = rFoodCanteenId;
        }
    }
}
