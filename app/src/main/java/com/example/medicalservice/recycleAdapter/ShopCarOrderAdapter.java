package com.example.medicalservice.recycleAdapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.R;
import com.example.medicalservice.activity.shopChildView.GoodDetailView;
import com.example.medicalservice.activity.shopChildView.SpecificationReSelect;
import com.example.medicalservice.bean.ShopCarBean;
import com.example.medicalservice.interfaceCallback.OnClickListener;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.OkHttpUtil;
import com.example.medicalservice.tools.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShopCarOrderAdapter extends RecyclerView.Adapter<ShopCarOrderAdapter.ViewHolder> {

    private List<ShopCarBean.RowsDTO> shopCar;

    public List<String> shopCarIdList = new ArrayList<>();

    private Activity activity;

    public ShopCarOrderAdapter(List<ShopCarBean.RowsDTO> shopCar,Activity activity) {
        this.shopCar = shopCar;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_car_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShopCarBean.RowsDTO rowsDTO = shopCar.get(position);
        holder.store_name.setText(rowsDTO.getBusinessName());


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.itemView.getContext());
        holder.childListView.setLayoutManager(linearLayoutManager);

        ChildViewAdapter childViewAdapter = new ChildViewAdapter(this);
        childViewAdapter.setData(rowsDTO.getSShoppingCartVoList());


        if (isAllSelect(rowsDTO.getSShoppingCartVoList())) {
            holder.store_check.setImageResource(R.drawable.checked);
        } else {
            holder.store_check.setImageResource(R.drawable.check);
        }


        holder.childListView.setAdapter(childViewAdapter);


        holder.store_check.setOnClickListener(v -> {
            if (rowsDTO.isCheck()) {
                rowsDTO.getSShoppingCartVoList().forEach(item -> {
                    shopCarIdList.remove(item);
                    item.setCheck(false);
                });
                rowsDTO.setCheck(false);
                holder.store_check.setImageResource(R.drawable.check);
            } else {
                rowsDTO.setCheck(true);
                holder.store_check.setImageResource(R.drawable.checked);

                rowsDTO.getSShoppingCartVoList().forEach(item -> {
                    shopCarIdList.add(item.getSShoppingCartId());
                    item.setCheck(true);
                });
            }

            onClickListener.OnItemClick(position);
            childViewAdapter.notifyDataSetChanged();
        });


    }

    private OnClickListener onClickListener;

    public void setOnItemClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private boolean isAllSelect(List<ShopCarBean.RowsDTO.SShoppingCartVoListDTO> sShoppingCartVoListDTOS) {

        for (ShopCarBean.RowsDTO.SShoppingCartVoListDTO bean :
                sShoppingCartVoListDTOS) {
            if (!bean.isCheck()) {
                return false;
            }
        }
        return true;
    }


    @Override
    public int getItemCount() {
        return shopCar.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView store_name;
        private RecyclerView childListView;

        private ImageView store_check;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            store_name = itemView.findViewById(R.id.store_name);
            store_check = itemView.findViewById(R.id.store_check);
            childListView = itemView.findViewById(R.id.child_list_view);

        }
    }


    class ChildViewAdapter extends RecyclerView.Adapter<ChildViewAdapter.ViewHolder> {

        private ShopCarOrderAdapter shopCarOrderAdapter;

        public ChildViewAdapter(ShopCarOrderAdapter shopCarOrderAdapter) {
            this.shopCarOrderAdapter = shopCarOrderAdapter;
        }

        private List<ShopCarBean.RowsDTO.SShoppingCartVoListDTO> sShoppingCartVoListDTOS;

        public void setData(List<ShopCarBean.RowsDTO.SShoppingCartVoListDTO> sShoppingCartVoListDTOS) {
            this.sShoppingCartVoListDTOS = sShoppingCartVoListDTOS;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_car_child_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ShopCarBean.RowsDTO.SShoppingCartVoListDTO rowsDTO = sShoppingCartVoListDTOS.get(position);

            holder.good_name.setText(rowsDTO.getGoodsName());
            holder.good_info.setText(rowsDTO.getSGoodsSpecificationsName());

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(activity, GoodDetailView.class);
                intent.putExtra("msg",rowsDTO.getSGoodsId());
                activity.startActivity(intent);
            });

            if (rowsDTO.getGoodsRemind() != null) {
                if (rowsDTO.getGoodsRemind().equals("回规格发生变化，请重新选择")) {
                    holder.good_info.setText(rowsDTO.getGoodsRemind());
                    holder.good_info.setOnClickListener(v -> {
                        String url = API.shoppingCart + "/" + rowsDTO.getSShoppingCartId();
                        Intent intent = new Intent(holder.itemView.getContext(), SpecificationReSelect.class);
                        intent.putExtra("msg", rowsDTO.getSGoodsId());
                        intent.putExtra("url", url);
                        holder.itemView.getContext().startActivity(intent);
                    });
                }
            }


            holder.price.setText(rowsDTO.getPrice() + "");
            holder.number.setText(rowsDTO.getNumber() + "");

            holder.reduce.setOnClickListener(v -> {
                if (rowsDTO.getNumber() == 1) return;
                rowsDTO.setNumber(rowsDTO.getNumber() - 1);
                notifyItemChanged(position);
                shopCarOrderAdapter.onClickListener.OnItemClick(position);
                try {
                    editNumber(rowsDTO.getSShoppingCartId(), rowsDTO.getNumber());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            });

            holder.add.setOnClickListener(v -> {
                rowsDTO.setNumber(rowsDTO.getNumber() + 1);
                notifyItemChanged(position);
                shopCarOrderAdapter.onClickListener.OnItemClick(position);
                try {
                    editNumber(rowsDTO.getSShoppingCartId(), rowsDTO.getNumber());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            });
            GlideUtils.load(holder.itemView.getContext(), rowsDTO.getSpecificationsImages(), holder.good_image, R.drawable.good_test, 20);


            if (rowsDTO.isCheck()) {
                holder.good_check.setImageResource(R.drawable.checked);
            } else {
                holder.good_check.setImageResource(R.drawable.check);
            }
            holder.good_check.setOnClickListener(v -> {

                if (rowsDTO.isCheck()) {
                    rowsDTO.setCheck(false);
                    holder.good_check.setImageResource(R.drawable.check);
                    shopCarIdList.remove(rowsDTO.getSShoppingCartId());
                } else {
                    rowsDTO.setCheck(true);
                    holder.good_check.setImageResource(R.drawable.checked);
                    shopCarIdList.add(rowsDTO.getSShoppingCartId());

                }

                shopCarOrderAdapter.onClickListener.OnItemClick(position);
                notifyDataSetChanged();
                shopCarOrderAdapter.notifyDataSetChanged();
            });
        }

        private void editNumber(String id ,int number) throws JSONException {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sShoppingCartId",id);
            jsonObject.put("number",number);

            OkHttpUtil.getInstance().doPut(API.shoppingCart, jsonObject.toString(), new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                }
            });
        }

        @Override
        public int getItemCount() {
            return sShoppingCartVoListDTOS.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView good_image;
            private TextView good_name, good_info, price, number;
            private ImageView good_check;
            private ImageView add, reduce;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                good_name = itemView.findViewById(R.id.good_name);
                good_info = itemView.findViewById(R.id.good_info);
                price = itemView.findViewById(R.id.price);
                number = itemView.findViewById(R.id.number);
                good_image = itemView.findViewById(R.id.good_image);
                good_check = itemView.findViewById(R.id.good_check);

                add = itemView.findViewById(R.id.add);
                reduce = itemView.findViewById(R.id.reduce);
            }
        }
    }
}
