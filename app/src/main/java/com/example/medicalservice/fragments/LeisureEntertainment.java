package com.example.medicalservice.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.R;
import com.example.medicalservice.activity.ChatView;
import com.example.medicalservice.baseFile.BaseFragment;
import com.example.medicalservice.bean.HomeGridBean;
import com.example.medicalservice.databinding.FragmentLeisureEntertainmentBinding;
import com.example.medicalservice.tools.ViewUtils;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;


public class LeisureEntertainment extends BaseFragment<FragmentLeisureEntertainmentBinding> {

    public LeisureEntertainment() {
        // Required empty public constructor
    }

    public static LeisureEntertainment newInstance() {
        LeisureEntertainment fragment = new LeisureEntertainment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        super.initView();
        initPlay();
        viewBinding.voiceWeidgt.input.setOnClickListener(v -> go(ChatView.class));

        viewBinding.voiceWeidgt.reboot.setOnClickListener(v -> go(ChatView.class));
    }

    private void initPlay() {

        List<HomeGridBean> homeGridBeans = new ArrayList<>();

        homeGridBeans.add(new HomeGridBean("", R.drawable.leisure_entertainment_image_tiktok));
        homeGridBeans.add(new HomeGridBean("", R.drawable.leisure_entertainment_image_wx));
        homeGridBeans.add(new HomeGridBean("", R.drawable.leisure_entertainment_image_iqiyi));
        homeGridBeans.add(new HomeGridBean("", R.drawable.leisure_entertainment_image_iqiyi));
        homeGridBeans.add(new HomeGridBean("", R.drawable.leisure_entertainment_image_qqmusic));
        homeGridBeans.add(new HomeGridBean("", R.drawable.leisure_entertainment_image_radio));
        homeGridBeans.add(new HomeGridBean("", R.drawable.leisure_entertainment_image_camera));
        homeGridBeans.add(new HomeGridBean("", R.drawable.leisure_entertainment_image_photo));
        homeGridBeans.add(new HomeGridBean("", R.drawable.leisure_entertainment_image_reader));
        homeGridBeans.add(new HomeGridBean("", R.drawable.leisure_entertainment_image_jump));
        homeGridBeans.add(new HomeGridBean("", R.drawable.leisure_entertainment_image_mj));
        homeGridBeans.add(new HomeGridBean("", R.drawable.leisure_entertainment_image_chess));
        homeGridBeans.add(new HomeGridBean("", R.drawable.leisure_entertainment_image_ddz));
        homeGridBeans.add(new HomeGridBean("", R.drawable.leisure_entertainment_image_fivechess));
        homeGridBeans.add(new HomeGridBean("", R.drawable.leisure_entertainment_image_zuma));


        DisplayMetrics outMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(outMetrics);
        int widthPixel = outMetrics.widthPixels;

        float width = (float) widthPixel / 5 - 54;

        float height = (float) (width / 1.63);

        MsAdapter msAdapter = new MsAdapter<HomeGridBean>(homeGridBeans, R.layout.leisure_entertainment_grid_item) {
            @Override
            public void bindView(ViewHolder holder, HomeGridBean obj) {

                ImageView tools_icon = holder.getView(R.id.image);

                tools_icon.setLayoutParams(new LinearLayout.LayoutParams((int) width, (int) height));

                tools_icon.setImageResource(obj.getDrawable());


            }
        };
        viewBinding.imageList.setAdapter(msAdapter);
    }

}