package com.example.medicalservice.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.Code200;
import com.example.medicalservice.bean.FamilyBean;
import com.example.medicalservice.bean.LoginBean;
import com.example.medicalservice.bean.OwnerBean;
import com.example.medicalservice.bean.UserLevelBean;
import com.example.medicalservice.dataBaseBean.UserBean;
import com.example.medicalservice.databinding.ActivityUserInfoInTypeBinding;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.OkHttpUtil;
import com.example.medicalservice.tools.StringUtils;
import com.google.gson.Gson;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UserInfoInType extends BaseActivity<ActivityUserInfoInTypeBinding> {

    private String[] level = {"自理", "一级", "二级", "三级", "四级", "五级"};

    CityPickerView mPicker = new CityPickerView();

    private OwnerBean ownerBean;

    private UserBean userBean;

    private List<FamilyBean.DataDTO> familyBeans = new ArrayList<>();

    private FamilyAdapter familyAdapter;

    public static Activity mActivity;

    @Override
    public void initView() {
        super.initView();
        mPicker.init(this);
        mActivity = this;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        viewBinding.familyList.setLayoutManager(linearLayoutManager);
        familyAdapter = new FamilyAdapter();
        viewBinding.familyList.setAdapter(familyAdapter);

        UserBean userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);

        viewBinding.username.setText(MyApplication.getInstance().getUserName());
        viewBinding.phoneNumber.setText(MyApplication.getInstance().getPhoneNumber());
        if (viewBinding.man.getText().toString().equals(userBean.getOwnerSex())) {
            viewBinding.man.setBackgroundResource(R.drawable.user_sexy_back_select);
            viewBinding.woman.setBackgroundResource(R.drawable.user_sexy_back_unselect);
        }

        if (viewBinding.woman.getText().toString().equals(userBean.getOwnerSex())) {
            viewBinding.man.setBackgroundResource(R.drawable.user_sexy_back_unselect);

            viewBinding.woman.setBackgroundResource(R.drawable.user_sexy_back_select);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
        getFamilyList();
    }

    @Override
    public void initData() {
        super.initData();

        areaSelect();
        timeSelect();
        setAddress();
        namePhoneSet();
        WHSet();

        viewBinding.area.setOnClickListener(v -> mPicker.showCityPicker());

        viewBinding.submit.setOnClickListener(v -> {

            Log.d("TAG", "initData: " + new Gson().toJson(ownerBean.getData()));

            OkHttpUtil.getInstance().doPut(API.owner, new Gson().toJson(ownerBean.getData()), new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                    if (response.body() != null) {
                        Code200 code200 = new Gson().fromJson(response.body().string(), Code200.class);

                        if (code200.getCode() == 200) {
                            runOnUiThread(() -> showToast(code200.getMsg()));
                            MyApplication.getInstance().setUserName(viewBinding.name.getText().toString());
                            MyApplication.getInstance().setPhoneNumber(viewBinding.phoneNumber.getText().toString());
                        } else {
                            runOnUiThread(() -> showToast("请求失败"));
                        }
                    }

                }
            });

            Log.d("TAG", "initData: " + new Gson().toJson(familyBeans));

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (FamilyBean.DataDTO item :
                            familyBeans) {

                        try {
                            Thread.sleep(1000);


                            if (item.getFamilyMemberId() != null) {

                                OkHttpUtil.getInstance().doPut(API.member, new Gson().toJson(item), new Callback() {
                                    @Override
                                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                                    }

                                    @Override
                                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                                        Log.d("TAG", "fix: " + response.body().string());

                                    }
                                });
                            } else {

                                if (item.getFamilyMemberName().isEmpty() || item.getFamilyMemberPhone().isEmpty()) {
                                    continue;
                                }

                                Log.d("TAG", "initData: " + new Gson().toJson(item));
                                OkHttpUtil.getInstance().doPost(API.member, new Gson().toJson(item), new Callback() {
                                    @Override
                                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                                    }

                                    @Override
                                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                        Log.d("TAG", "add: " + response.body().string());
                                    }
                                });
                            }

                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
            }).start();


        });

        viewBinding.switchUser.setOnClickListener(v -> go(AddUserActivity.class));

        viewBinding.delete.setOnClickListener(v -> deleteDialog());

        viewBinding.man.setOnClickListener(v -> {
            if (ownerBean != null) {
                ownerBean.getData().setOwnerSex("0");
                viewBinding.man.setBackgroundResource(R.drawable.user_sexy_back_select);
                viewBinding.woman.setBackgroundResource(R.drawable.user_sexy_back_unselect);
            }
        });

        viewBinding.woman.setOnClickListener(v -> {
            if (ownerBean != null) {
                ownerBean.getData().setOwnerSex("1");
                viewBinding.man.setBackgroundResource(R.drawable.user_sexy_back_unselect);
                viewBinding.woman.setBackgroundResource(R.drawable.user_sexy_back_select);
            }
        });
    }

    private void getFamilyList() {

        if (userBean == null) {
            return;
        }

        Log.d("TAG", "getFamilyList: " + API.family + "?ownerId=" + userBean.getOwnerId());
        OkHttpUtil.getInstance().doGet(API.family + "?ownerId=" + userBean.getOwnerId(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    FamilyBean familyBean = new Gson().fromJson(response.body().string(), FamilyBean.class);

                    if (familyBean.getCode() == 200) {
                        familyBeans = familyBean.getData();


                        if (familyBeans.size() == 0) {
                            for (int i = 0; i < 2; i++) {
                                FamilyBean.DataDTO rowsDTO = new FamilyBean.DataDTO();
                                rowsDTO.setFamilyMemberName("");
                                rowsDTO.setFamilyMemberPhone("");
                                rowsDTO.setOwnerId(Integer.parseInt(userBean.getOwnerId()));
                                rowsDTO.setFamilyMemberRandom(generateUniqueString());
                                familyBeans.add(rowsDTO);
                            }
                        }


                        if (familyBeans.size() == 1) {
                            FamilyBean.DataDTO rowsDTO = new FamilyBean.DataDTO();
                            rowsDTO.setFamilyMemberName("");
                            rowsDTO.setFamilyMemberPhone("");
                            rowsDTO.setOwnerId(Integer.parseInt(userBean.getOwnerId()));
                            rowsDTO.setFamilyMemberRandom(generateUniqueString());
                            familyBeans.add(rowsDTO);
                        }

                        Log.d("TAG", "onResponse: " + new Gson().toJson(familyBeans));

                        runOnUiThread(() -> familyAdapter.notifyDataSetChanged());
                    }

                }

            }
        });
    }

    private String generateUniqueString() {
        long timestamp = new Date().getTime();
        long randomNum = (long) Math.floor(1000 + Math.random() * 9000); // 生成四位随机数
        return "JS" + timestamp + randomNum;
    }


    private void namePhoneSet() {
        viewBinding.username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                ownerBean.getData().setOwnerName(s.toString());
                viewBinding.name.setText(s.toString());


            }
        });

        viewBinding.phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ownerBean.getData().setOwnerPhone(s.toString());
            }
        });


        viewBinding.ownerNursingPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ownerBean.getData().setOwnerNursingPhone(s.toString());
            }
        });
        viewBinding.ownerNursingName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ownerBean.getData().setOwnerNursingName(s.toString());
            }
        });

        viewBinding.ownerDoctorPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ownerBean.getData().setOwnerDoctorPhone(s.toString());
            }
        });
        viewBinding.ownerDoctorName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ownerBean.getData().setOwnerDoctorName(s.toString());
            }
        });
    }

    private void areaSelect() {
        CityConfig cityConfig = new CityConfig.Builder().build();
        mPicker.setConfig(cityConfig);

//监听选择点击事件及返回结果
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {


                viewBinding.area.setText(province.getName() + " " + city.getName() + " " + district.getName());

                ownerBean.getData().setOwnerArea(viewBinding.area.getText().toString());

            }

            @Override
            public void onCancel() {

            }
        });
    }

    private void setAddress() {
        viewBinding.ownerAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ownerBean.getData().setOwnerAddress(s.toString());
            }
        });
    }

    private void timeSelect() {
        viewBinding.birthday.setOnClickListener(v -> {

            Calendar startCal = Calendar.getInstance();
            Calendar sal = Calendar.getInstance();

            startCal.set(1998, 0, 1, 0, 0, 0);
            TimePickerView pvTime = new TimePickerBuilder(activity, (date, v1) -> {
                viewBinding.birthday.setText(StringUtils.forDataTimeYMD(date));

                ownerBean.getData().setOwnerBirth(viewBinding.birthday.getText().toString());
            })
                    .setDate(sal).setRangDate(startCal, StringUtils.strForCalendar("2029-12-31 23:59:59"))//起始终止年月日设定// 如果不设置的话，默认是系统时间*/
                    .setType(new boolean[]{true, true, true, false, false, false})
                    .setCancelText("取消")
                    .setSubmitText("确定")
                    .build();
            pvTime.show();
        });
    }

    private void WHSet() {
        viewBinding.weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ownerBean.getData().setOwnerWeight(s.toString());
            }
        });
        viewBinding.hight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ownerBean.getData().setOwnerHeight(s.toString());
            }
        });
    }

    private int getColor(String level) {

        switch (level) {
            case "自理":
                return Color.parseColor("#DFECE7");
            case "一级":
                return Color.parseColor("#DEECDE");
            case "二级":
                return Color.parseColor("#E7EDDD");
            case "三级":
                return Color.parseColor("#EDEBDD");
            case "四级":
                return Color.parseColor("#E8DDD8");
            case "五级":
                return Color.parseColor("#B7AFAC");

        }
        return Color.parseColor("#FFFFFF");
    }

    private void getUserInfo() {
        userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true);
        Log.d("TAG", "getUserInfo: " + API.owner(userBean.getOwnerId()));
        if (userBean != null) {
            OkHttpUtil.getInstance().doGet(API.owner(userBean.getOwnerId()), new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                    if (response.body() != null) {
                        String body = response.body().string();

                        ownerBean = new Gson().fromJson(body, OwnerBean.class);
                        if (ownerBean.getCode() == 200&&ownerBean.getData()!=null) {
                            runOnUiThread(() -> {
                                viewBinding.name.setText(ownerBean.getData().getOwnerName());
                                viewBinding.username.setText(ownerBean.getData().getOwnerName());
                                GlideUtils.load(activity, MyApplication.getInstance().getUserHeader(), viewBinding.userheader, R.drawable.header, 90);


                                if (ownerBean.getData().getOwnerSex() != null) {
                                    if (ownerBean.getData().getOwnerSex().equals("0")) {
                                        viewBinding.man.setBackgroundResource(R.drawable.user_sexy_back_select);
                                        viewBinding.woman.setBackgroundResource(R.drawable.user_sexy_back_unselect);
                                    } else {
                                        viewBinding.man.setBackgroundResource(R.drawable.user_sexy_back_unselect);

                                        viewBinding.woman.setBackgroundResource(R.drawable.user_sexy_back_select);
                                    }
                                }


                                viewBinding.hight.setText(ownerBean.getData().getOwnerHeight());
                                viewBinding.weight.setText(ownerBean.getData().getOwnerWeight());


                                String ownerBirth = ownerBean.getData().getOwnerBirth();

                                viewBinding.birthday.setText(ownerBirth);

                                viewBinding.area.setText(ownerBean.getData().getOwnerArea().isEmpty() ? "点击选择地区" :ownerBean.getData().getOwnerArea());

                                viewBinding.ownerAddress.setText( ownerBean.getData().getOwnerAddress());


                                List<UserLevelBean> userLevelBeans = new ArrayList<>();

                                for (String s : level) {
                                    UserLevelBean userLevelBean = new UserLevelBean();
                                    userLevelBean.setText(s);
                                    userLevelBean.setSelect(false);
                                    userLevelBeans.add(userLevelBean);
                                }
                                MsAdapter msAdapter = new MsAdapter<UserLevelBean>(userLevelBeans, R.layout.user_grid_item) {

                                    @Override
                                    public void bindView(ViewHolder holder, UserLevelBean obj) {
                                        TextView levelText = holder.getView(R.id.level);
                                        levelText.setText(obj.getText());

                                        if (ownerBean.getData().getOwnerSelfAssess() == userLevelBeans.indexOf(obj)) {
                                            levelText.setBackgroundColor(getColor(obj.getText()));
                                        } else {
                                            levelText.setBackgroundColor(Color.parseColor("#CACACA"));
                                        }

                                        holder.getItemView().setOnClickListener(v -> {
                                            ownerBean.getData().setOwnerSelfAssess(userLevelBeans.indexOf(obj));
                                            notifyDataSetChanged();
                                        });

                                    }
                                };

                                viewBinding.ownerDoctorName.setText(ownerBean.getData().getOwnerDoctorName());
                                viewBinding.ownerDoctorPhone.setText(ownerBean.getData().getOwnerDoctorPhone());


                                viewBinding.ownerNursingName.setText(ownerBean.getData().getOwnerNursingName());
                                viewBinding.ownerNursingPhone.setText(ownerBean.getData().getOwnerNursingPhone());

                                viewBinding.selfCareAssessmentLevel.setAdapter(msAdapter);

                            });
                        }else {

                            runOnUiThread(() -> showToast("请求失败"));
                        }


                    }

                }
            });
        }
    }

    private void deleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Dialog_style);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_delete, null);

        builder.setView(view);
        TextView left_btn, right_btn, text;
        left_btn = view.findViewById(R.id.yes);
        right_btn = view.findViewById(R.id.no);
        text = view.findViewById(R.id.tip_text);


        text.setText("是否删除用户");

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


        left_btn.setOnClickListener(v1 -> {
            if (userBean != null) {
                MyApplication.getInstance().db.userDao().delete(userBean);


                if (MyApplication.getInstance().db.userDao().getAllUser().size() > 0) {
                    UserBean nowLoginUser = MyApplication.getInstance().db.userDao().getAllUser().get(0);
                    nowLoginUser.setLoginStatus(true);
                    MyApplication.getInstance().db.userDao().updateUser(nowLoginUser);
                    refreshToken(nowLoginUser);
                } else {
                    go(AddUserActivity.class);
                    finish();
                }

            }
        });

        right_btn.setOnClickListener(v1 -> alertDialog.dismiss());


    }

    private void refreshToken(UserBean loginUser) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("username", loginUser.getUserName());
            jsonObject.put("password", loginUser.getPassword());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        OkHttpUtil.getInstance().doPost(API.login, jsonObject.toString(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() != null) {

                    LoginBean loginBean = new Gson().fromJson(response.body().string(), LoginBean.class);

                    if (loginBean != null && loginBean.getCode() == 200) {

                        updateToken(loginBean.getData().getAccess_token(), loginUser.getNickName(), loginUser.getAvatar(), String.valueOf(loginUser.getUserId()), loginUser.getPhonenumber());

                        runOnUiThread(() -> {
                            go(AddUserActivity.class);
                            finish();
                        });
                    }


                }
            }
        });
    }


    class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.ViewHolder> {


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.famil_info_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

            FamilyBean.DataDTO rowsDTO = familyBeans.get(position);

            holder.family_count.setText("家属" + (position + 1));
            holder.family_name.setText(rowsDTO.getFamilyMemberName());
            holder.family_number.setText(rowsDTO.getFamilyMemberPhone());


            holder.family_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    rowsDTO.setFamilyMemberName(holder.family_name.getText().toString());

                }
            });

            holder.family_number.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    rowsDTO.setFamilyMemberPhone(s.toString());


                }
            });


        }

        @Override
        public int getItemCount() {
            return familyBeans.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private ImageView add, remove;
            private TextView family_count;
            private EditText family_name, family_number;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                remove = itemView.findViewById(R.id.remove);
                family_count = itemView.findViewById(R.id.family_count);
                add = itemView.findViewById(R.id.add);

                family_name = itemView.findViewById(R.id.family_name);

                family_number = itemView.findViewById(R.id.family_number);


            }
        }
    }


}