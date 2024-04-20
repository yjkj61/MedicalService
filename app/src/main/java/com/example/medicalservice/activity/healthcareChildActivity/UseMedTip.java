package com.example.medicalservice.activity.healthcareChildActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.R;
import com.example.medicalservice.activity.healthcareChildActivity.useMadChildView.AddInfoActivity;
import com.example.medicalservice.activity.healthcareChildActivity.useMadChildView.AddMadActivity;
import com.example.medicalservice.activity.healthcareChildActivity.useMadChildView.AllMadActivity;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.UseMedTipListBean;
import com.example.medicalservice.databinding.ActivityUseMedTipBinding;
import com.example.medicalservice.recycleAdapter.UseMedTipGridRecAdapter;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.DataEdit;
import com.example.medicalservice.tools.OkHttpUtil;
import com.example.medicalservice.tools.StringUtils;
import com.example.medicalservice.tools.TimeTool;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UseMedTip extends BaseActivity<ActivityUseMedTipBinding> {

    @Override
    public void initView() {
        super.initView();

        viewBinding.time.setText(TimeTool.Day(0,"YYYY/MM/dd"));

        viewBinding.back.setOnClickListener(v -> finish());

        viewBinding.lookAll.setOnClickListener(v -> go(AllMadActivity.class));

        viewBinding.addNew.setOnClickListener(v -> go(AddMadActivity.class));

        viewBinding.addInfo.setOnClickListener(v -> go(AddInfoActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        DataEdit.getInstance().setRowsDTO(null);
        initList();
    }

    @Override
    public void initData() {
        super.initData();

        viewBinding.time.setOnClickListener(v -> {

            Calendar startCal = Calendar.getInstance();
            Calendar sal = Calendar.getInstance();

            startCal.set(1998, 0, 1, 0, 0, 0);
            TimePickerView pvTime = new TimePickerBuilder(activity, (date, v1) -> {
                viewBinding.time.setText(StringUtils.forDataTimeYMD2(date));

                initList();
            })
                    .setDate(sal).setRangDate(startCal, StringUtils.strForCalendar("2029-12-31 23:59:59"))//起始终止年月日设定// 如果不设置的话，默认是系统时间*/
                    .setType(new boolean[]{true, true, true, false, false, false})
                    .setCancelText("取消")
                    .setSubmitText("确定")

                    .build();
            pvTime.show();
        });

    }

    private void initList() {

        OkHttpUtil.getInstance().doGet(API.medicinalRemindList(viewBinding.time.getText().toString()), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> showToast("请求失败"));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if(response.body()!=null){
                    UseMedTipListBean useMedTipListBean = new Gson().fromJson(response.body().string(),UseMedTipListBean.class);

                    if(useMedTipListBean.getCode() == 200){

                        runOnUiThread(() -> {
                            MsAdapter msAdapter = new MsAdapter<UseMedTipListBean.DataDTO>(useMedTipListBean.getData(), R.layout.use_med_tip_grid_item) {

                                @Override
                                public void bindView(ViewHolder holder, UseMedTipListBean.DataDTO obj) {

                                    RecyclerView recyclerView = holder.getView(R.id.list);

                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.getItemView().getContext());
                                    recyclerView.setLayoutManager(linearLayoutManager);

                                    recyclerView.setAdapter(new UseMedTipGridRecAdapter(obj.getHMedicinalRemindVoList()));

                                }
                            };

                            viewBinding.list.setAdapter(msAdapter);
                        });

                    }else {
                        runOnUiThread(() -> showToast("请求失败，请联系管理员"));
                    }
                }

            }
        });





    }
}