package com.example.medicalservice.fragments.healthMyStepFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.medicalservice.MsAdapter;
import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseFragment;
import com.example.medicalservice.databinding.FragmentDayBinding;
import com.example.medicalservice.tools.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DayFragment extends BaseFragment<FragmentDayBinding> {


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DayFragment() {
        // Required empty public constructor
    }
    int rice = 5;
    float progress = 0.9f;

    public static DayFragment newInstance() {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initView() {
        super.initView();
        viewBinding.step.setText("1500");
        viewBinding.qianka.setText("300");
        viewBinding.rice.setText("相当于"+rice+"碗米饭");
        viewBinding.stepBox.post(() -> {
            int width =  viewBinding.gridBox.getMeasuredWidth();

            viewBinding.progress.setLayoutParams(new FrameLayout.LayoutParams((int) (width*progress),60));
        });

        viewBinding.stepText.setText("您的日均步数建议是7000步，当前已完成"+progress*100+"%");
    }

    @Override
    public void initData() {
        super.initData();



        List<Integer> integers =new ArrayList<>();
        for (int i = 0; i < rice; i++) {
            integers.add(R.drawable.rice);
        }



        viewBinding.gridBox.post(() -> {
            int number =  viewBinding.gridBox.getMeasuredWidth() / 150;
            viewBinding.gridView.setNumColumns(number);
            MsAdapter msAdapter = new MsAdapter<Integer>(integers,R.layout.image_item) {

                @Override
                public void bindView(ViewHolder holder, Integer obj) {
                    ImageView  imageView = holder.getView(R.id.image);
                    imageView.setImageResource(obj);
                }
            };
            viewBinding.gridView.setAdapter(msAdapter);
        });

    }
}