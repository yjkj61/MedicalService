package com.example.medicalservice.fragments.submitOrderFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.medicalservice.R;
import com.example.medicalservice.activity.shopChildView.SubmitOrderView;
import com.example.medicalservice.baseFile.BaseFragment;
import com.example.medicalservice.databinding.FragmentPayBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PayFragment extends BaseFragment<FragmentPayBinding> {

    public PayFragment() {
        // Required empty public constructor
    }

    public static PayFragment newInstance() {
        PayFragment fragment = new PayFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void initView() {
        super.initView();
        CheckBox[] checkBoxes = new CheckBox[]{viewBinding.checkBoxPay, viewBinding.checkBoxPayWx, viewBinding.checkBoxCard};
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = (buttonView, isChecked) -> {
            // 如果当前CheckBox被选中，则取消其他所有CheckBox的选中状态
            if (isChecked) {
                for (CheckBox checkBox : checkBoxes) {
                    if (checkBox != buttonView) {
                        checkBox.setChecked(false);
                    }
                }
            }
        };
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
        }
        viewBinding.lastStep.setOnClickListener(v -> SubmitOrderView.mActivity.tabSwitch(1));
    }
}