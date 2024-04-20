package com.example.medicalservice.fragments.goodDetailChildView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseFragment;
import com.example.medicalservice.databinding.FragmentGoodJudgeBinding;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.OkHttpUtil;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import per.wsj.library.AndRatingBar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoodJudgeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoodJudgeFragment extends BaseFragment<FragmentGoodJudgeBinding> {


    String params;

    private GoodJudgeFragment() {
        // Required empty public constructor
    }

    private AdapterJudge adapterJudge;

    public static GoodJudgeFragment newInstance(String params) {
        GoodJudgeFragment fragment = new GoodJudgeFragment();
        Bundle args = new Bundle();
        args.putString("params", params);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            params = getArguments().getString("params");
        }
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData()  {
        super.initData();

        adapterJudge = new AdapterJudge();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        viewBinding.judgeList.setLayoutManager(linearLayoutManager);
        viewBinding.judgeList.setAdapter(adapterJudge);

        Log.d("TAG", "initData: "+API.commentsFirstList + "sGoodsId=" + params);

        OkHttpUtil.getInstance().doGet(API.commentsFirstList + "sGoodsId=" + params, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if(response.body()!=null){
                    JudgeListBean judgeListBean = new Gson().fromJson(response.body().string(),JudgeListBean.class);

                    if(judgeListBean.getCode() == 200){
                        activity.runOnUiThread(() -> adapterJudge.setData(judgeListBean.getRows()));

                    }
                }

            }
        });
    }

    class AdapterJudge extends RecyclerView.Adapter<AdapterJudge.ViewHolder> {


        private List<JudgeListBean.RowsDTO> rowsDTOS = new ArrayList<>();

        public void setData(List<JudgeListBean.RowsDTO> rowsDTOS){
            this.rowsDTOS = rowsDTOS;
            notifyDataSetChanged();
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.judeg_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            JudgeListBean.RowsDTO rowsDTO = rowsDTOS.get(position);

            holder.name.setText(rowsDTO.getFirstCommentatorName());

            holder.ratingBar.setRating(Float.parseFloat(rowsDTO.getComprehensiveScore()));

            holder.ratingBar.setOnRatingChangeListener((ratingBar, rating, fromUser) -> holder.ratingBar.setRating(Float.parseFloat(rowsDTO.getComprehensiveScore())));

            if(rowsDTO.getCommentsImages() != null){
                GlideUtils.load(holder.itemView.getContext(),rowsDTO.getCommentsImages(),holder.header,R.drawable.header,90);

            }else {
                holder.header.setImageResource(R.drawable.header);
            }

            holder.context.setText(rowsDTO.commentsText);

            holder.time.setText(rowsDTO.getCreateTime());

        }

        @Override
        public int getItemCount() {
            Log.d("TAG", "getItemCount: "+rowsDTOS.size());
            return rowsDTOS.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private ImageView header;
            private TextView name, context, time;
            private AndRatingBar ratingBar;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                header = itemView.findViewById(R.id.header);
                name = itemView.findViewById(R.id.name);
                context = itemView.findViewById(R.id.context);
                time = itemView.findViewById(R.id.time);
                ratingBar = itemView.findViewById(R.id.rating);
            }
        }
    }

    class JudgeListBean{

        private int total;
        private List<RowsDTO> rows;
        private int code;
        private String msg;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<RowsDTO> getRows() {
            return rows;
        }

        public void setRows(List<RowsDTO> rows) {
            this.rows = rows;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public  class RowsDTO {
            private String createBy;
            private String createTime;
            private String updateBy;
            private String updateTime;
            private String remark;
            private String sCommentsFirstId;
            private String orderId;
            private String firstCommentatorId;
            private String firstCommentatorName;
            private String sGoodsBusinessId;
            private String sGoodsId;
            private String comprehensiveScore;
            private String goodsScore;
            private String serviceScore;
            private String logisticsScore;
            private String commentsVideo;
            private String commentsImages;
            private String commentsText;
            private String isShow;
            private String isReply;
            private String createTimeList;
            private String createTimeBegin;
            private String createTimeEnd;
            private String goodsName;
            private String goodsCoverImages;
            private String commentResponse;

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

            public String getSCommentsFirstId() {
                return sCommentsFirstId;
            }

            public void setSCommentsFirstId(String sCommentsFirstId) {
                this.sCommentsFirstId = sCommentsFirstId;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getFirstCommentatorId() {
                return firstCommentatorId;
            }

            public void setFirstCommentatorId(String firstCommentatorId) {
                this.firstCommentatorId = firstCommentatorId;
            }

            public String getFirstCommentatorName() {
                return firstCommentatorName;
            }

            public void setFirstCommentatorName(String firstCommentatorName) {
                this.firstCommentatorName = firstCommentatorName;
            }

            public String getSGoodsBusinessId() {
                return sGoodsBusinessId;
            }

            public void setSGoodsBusinessId(String sGoodsBusinessId) {
                this.sGoodsBusinessId = sGoodsBusinessId;
            }

            public String getSGoodsId() {
                return sGoodsId;
            }

            public void setSGoodsId(String sGoodsId) {
                this.sGoodsId = sGoodsId;
            }

            public String getComprehensiveScore() {
                return comprehensiveScore;
            }

            public void setComprehensiveScore(String comprehensiveScore) {
                this.comprehensiveScore = comprehensiveScore;
            }

            public String getGoodsScore() {
                return goodsScore;
            }

            public void setGoodsScore(String goodsScore) {
                this.goodsScore = goodsScore;
            }

            public String getServiceScore() {
                return serviceScore;
            }

            public void setServiceScore(String serviceScore) {
                this.serviceScore = serviceScore;
            }

            public String getLogisticsScore() {
                return logisticsScore;
            }

            public void setLogisticsScore(String logisticsScore) {
                this.logisticsScore = logisticsScore;
            }

            public String getCommentsVideo() {
                return commentsVideo;
            }

            public void setCommentsVideo(String commentsVideo) {
                this.commentsVideo = commentsVideo;
            }

            public String getCommentsImages() {
                return commentsImages;
            }

            public void setCommentsImages(String commentsImages) {
                this.commentsImages = commentsImages;
            }

            public String getCommentsText() {
                return commentsText;
            }

            public void setCommentsText(String commentsText) {
                this.commentsText = commentsText;
            }

            public String getIsShow() {
                return isShow;
            }

            public void setIsShow(String isShow) {
                this.isShow = isShow;
            }

            public String getIsReply() {
                return isReply;
            }

            public void setIsReply(String isReply) {
                this.isReply = isReply;
            }

            public String getCreateTimeList() {
                return createTimeList;
            }

            public void setCreateTimeList(String createTimeList) {
                this.createTimeList = createTimeList;
            }

            public String getCreateTimeBegin() {
                return createTimeBegin;
            }

            public void setCreateTimeBegin(String createTimeBegin) {
                this.createTimeBegin = createTimeBegin;
            }

            public String getCreateTimeEnd() {
                return createTimeEnd;
            }

            public void setCreateTimeEnd(String createTimeEnd) {
                this.createTimeEnd = createTimeEnd;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsCoverImages() {
                return goodsCoverImages;
            }

            public void setGoodsCoverImages(String goodsCoverImages) {
                this.goodsCoverImages = goodsCoverImages;
            }

            public String getCommentResponse() {
                return commentResponse;
            }

            public void setCommentResponse(String commentResponse) {
                this.commentResponse = commentResponse;
            }
        }
    }
}