package com.example.medicalservice.activity.healthcareChildActivity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.AddressCatering;
import com.example.medicalservice.bean.GoodBean;
import com.example.medicalservice.bean.HealthReportBean;
import com.example.medicalservice.databinding.ActivityImagingReportBinding;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.BitmapUtil;
import com.example.medicalservice.tools.ContentUriUtil;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.OkHttpUtil;
import com.example.medicalservice.tools.StringUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ImagingReport extends BaseActivity<ActivityImagingReportBinding> {

    private ActivityResultLauncher<Intent> intentActivityResultLauncher;
    HealthReportBean.RowsDTO healthReport = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        super.initView();

        viewBinding.back.setOnClickListener(v -> finish());

    }

    @Override
    public void initData() {
        super.initData();
        intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getData() != null && result.getResultCode() == Activity.RESULT_OK) {
                Uri uri = result.getData().getData();
                if (uri != null) {

                    String path = ContentUriUtil.getPath(getApplicationContext(), uri);

                    String type = getFileType(path);
                    File file = new File(BitmapUtil.compressImage(path));
                    if (type.equals("jpg") || type.equals("png")) {
                        OkHttpUtil.getInstance().upImageFunc(file, type, new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                                Log.d("TAG", "onFailure: " + e);

                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {


                                if (response.body() != null) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.body().string());
                                        if (jsonObject.getInt("code") == 200) {
                                            runOnUiThread(() -> {
                                                try {
                                                    if (healthReport == null) {
                                                        addDialog("", "", jsonObject.getJSONObject("data").getString("url"), 0);
                                                    } else {
                                                        addDialog(healthReport.getHReportName(), healthReport.getHReportCreateTime(), jsonObject.getJSONObject("data").getString("url"), healthReport.getHReportId());
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            });


                                        }
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }

                                }

                            }
                        }); //uploadImage(path);
                    } else {
                        showToast("您的图片格式不正确");
                    }

                }
            }
        });
        getReportList();
    }


    private void getReportList() {
        OkHttpUtil.getInstance().doGet(API.reportList, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.body() != null) {
                    String data = response.body().string();

                    HealthReportBean healthReportBean = new Gson().fromJson(data, HealthReportBean.class);

                    HealthReportBean.RowsDTO rowsDTO = new HealthReportBean.RowsDTO();

                    rowsDTO.setHReportContent("");
                    rowsDTO.setCreateTime("");
                    rowsDTO.setHReportName("新建报告");

                    healthReportBean.getRows().add(rowsDTO);

                    runOnUiThread(() -> {
                        MsAdapter msAdapter = new MsAdapter<HealthReportBean.RowsDTO>(healthReportBean.getRows(), R.layout.ct_report_item) {
                            @Override
                            public void bindView(ViewHolder holder, HealthReportBean.RowsDTO obj) {

                                TextView name = holder.getView(R.id.name);
                                TextView time = holder.getView(R.id.time);
                                ImageView image = holder.getView(R.id.image);
                                TextView edit = holder.getView(R.id.edit);
                                TextView delete = holder.getView(R.id.delete);

                                name.setText(obj.getHReportName());
                                time.setText(obj.getHReportCreateTime());


                                if (obj.getHReportName().equals("新建报告")) {
                                    image.setOnClickListener(v -> {
                                        healthReport = null;
                                        if (getPermissions()) {
                                            Intent intent = new Intent(Intent.ACTION_PICK, null);
                                            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                            intentActivityResultLauncher.launch(intent);
                                        } else {
                                            String[] strings = new String[]{
                                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                            ActivityCompat.requestPermissions(activity, strings, 1);
                                        }
                                    });

                                    edit.setText("");
                                    delete.setText("");
                                }

                                GlideUtils.load(activity, obj.getHReportContent(), image, R.drawable.report_add, 20);
                                edit.setOnClickListener(v -> {
                                    healthReport = obj;
                                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                    intentActivityResultLauncher.launch(intent);
                                });

                                delete.setOnClickListener(v -> {

                                    OkHttpUtil.getInstance().doDelete(API.reportDelete(String.valueOf(obj.getHReportId())), new Callback() {
                                        @Override
                                        public void onFailure(@NonNull Call call1, @NonNull IOException e) {

                                        }

                                        @Override
                                        public void onResponse(@NonNull Call call1, @NonNull Response response1) throws IOException {
                                            getReportList();
                                        }
                                    });
                                });
                            }
                        };

                        viewBinding.imageList.setAdapter(msAdapter);
                    });


                }

            }
        });
    }


    private void addDialog(String reportName, String time, String url, int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Dialog_style);
        View view = LayoutInflater.from(activity).inflate(R.layout.report_add_dialog, null);

        builder.setView(view);
        TextView left_btn, right_btn;
        DatePicker timeSelect;
        EditText name;

        left_btn = view.findViewById(R.id.yes);
        right_btn = view.findViewById(R.id.no);
        name = view.findViewById(R.id.name);
        timeSelect = view.findViewById(R.id.time);

//                    if (!time.isEmpty()) {
//                String times[] = time.split("-");
//                timeSelect.setC
//                        set(Integer.parseInt(times[0]), Integer.parseInt(times[1]), Integer.parseInt(times[2]));
//            }



//        timeSelect.setOnClickListener(v -> {
//
//            Calendar startCal = Calendar.getInstance();
//            Calendar sal = Calendar.getInstance();
//
//            if (!time.isEmpty()) {
//                String times[] = time.split("/");
//                sal.set(Integer.parseInt(times[0]), Integer.parseInt(times[1]), Integer.parseInt(times[2]));
//            }
//
//            startCal.set(1998, 0, 1, 0, 0, 0);
//            TimePickerView pvTime = new TimePickerBuilder(activity, (date, v1) -> timeSelect.setText(StringUtils.forDataTimeYMD(date)))
//                    .setDate(sal)
//                    .setRangDate(startCal, StringUtils.strForCalendar("2029-12-31 23:59:59"))//起始终止年月日设定// 如果不设置的话，默认是系统时间*/
//                    .setType(new boolean[]{true, true, true, false, false, false})
//                    .setCancelText("取消")
//                    .setSubmitText("确定")
//                    .build();
//            pvTime.show();
//        });


        if (!reportName.isEmpty()) {
            name.setText(reportName);

        }


        AlertDialog alertDialog = builder.create();
        alertDialog.show();


        left_btn.setOnClickListener(v1 -> alertDialog.dismiss());

        right_btn.setOnClickListener(v1 -> {
            if (name.getText().toString().isEmpty()) {
                showToast("请填写完整");
                return;
            }

            HealthReportBean.RowsDTO rowsDTO = new HealthReportBean.RowsDTO();
            rowsDTO.setHUserId(Integer.parseInt(MyApplication.getInstance().getUserId()));

            rowsDTO.setHReportName(name.getText().toString());

            rowsDTO.setCreateTime(timeSelect.getYear()+"-"+timeSelect.getMonth()+"-"+timeSelect.getDayOfMonth()+" 00:00:00");

            rowsDTO.setHReportContent(url);


            if (!reportName.isEmpty()) {
                rowsDTO.setHReportId(id);
                OkHttpUtil.getInstance().doPut(API.report, new Gson().toJson(rowsDTO), new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        alertDialog.dismiss();

                        getReportList();
                    }
                });
                return;
            }
            OkHttpUtil.getInstance().doPost(API.report, new Gson().toJson(rowsDTO), new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    alertDialog.dismiss();

                    Log.d("TAG", "onResponse: "+response.body().string());
                    getReportList();
                }
            });
        });


    }


    private boolean getPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

    }

    public static String getFileType(String name) {
        File file = new File(name);
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return suffix;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                String[] strings = {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
                ActivityCompat.requestPermissions(this, strings, 1);


            } else {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intentActivityResultLauncher.launch(intent);
            }

        }


    }

}