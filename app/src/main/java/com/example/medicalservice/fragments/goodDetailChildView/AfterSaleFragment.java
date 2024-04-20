package com.example.medicalservice.fragments.goodDetailChildView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseFragment;
import com.example.medicalservice.databinding.FragmentAfterSaleBinding;

import org.json.JSONException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AfterSaleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AfterSaleFragment extends BaseFragment<FragmentAfterSaleBinding> {



    private AfterSaleFragment() {
        // Required empty public constructor
    }

    public static AfterSaleFragment newInstance() {
        AfterSaleFragment fragment = new AfterSaleFragment();
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
    }

    @Override
    public void initData() {
        super.initData();
    }
}