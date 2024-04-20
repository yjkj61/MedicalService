package com.example.medicalservice.activity.healthcareChildActivity.useMadChildView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.bean.UseMedHistoryBean;
import com.example.medicalservice.databinding.ActivityAddMadBinding;
import com.example.medicalservice.tools.BitmapUtil;
import com.example.medicalservice.tools.ContentUriUtil;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.DataEdit;
import com.example.medicalservice.tools.OkHttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//新增药物
public class AddMadActivity extends BaseActivity<ActivityAddMadBinding> {

    private ActivityResultLauncher<Intent> intentActivityResultLauncher;

    public static AddMadActivity mActivity;

    private String url = "";

    private UseMedHistoryBean.DataDTO rowsDTO;


    @Override
    public void initView() {
        super.initView();

        mActivity = this;
        rowsDTO = DataEdit.getInstance().getRowsDTO();

        if(rowsDTO !=null){
            viewBinding.medName.setText(rowsDTO.getMedicinalName());
            url = rowsDTO.getImg();

            GlideUtils.load(getApplicationContext(),url,viewBinding.header);
        }

        viewBinding.nextStep.setOnClickListener(v -> {
            if (viewBinding.medName.getText().toString().equals("")) {
                showToast("请输入药品名称");
                return;
            }
            if(url.isEmpty()){
                showToast("请选择药物图片");
                return;
            }
            go(UseMadType.class,viewBinding.medName.getText().toString(),url);
        });


        viewBinding.header.setOnClickListener(v -> {
            if (getPermissions()) {

                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intentActivityResultLauncher.launch(intent);


            } else {
                String[] strings = new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this, strings, 1);
            }
        });

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


                                if(response.body()!=null){
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.body().string());
                                        if(jsonObject.getInt("code")==200){
                                            url = jsonObject.getJSONObject("data").getString("url");
                                            activity.runOnUiThread(() -> GlideUtils.load(getApplicationContext(),url,viewBinding.header));
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