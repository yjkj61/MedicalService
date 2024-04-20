package com.example.medicalservice.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseFragment;
import com.example.medicalservice.databinding.FragmentTourismBinding;
import com.example.medicalservice.recycleAdapter.TourisumAdapter;

import java.util.ArrayList;
import java.util.List;

public class Tourism extends BaseFragment<FragmentTourismBinding> {

    public Tourism() {
        // Required empty public constructor
    }

    public static Tourism newInstance() {
        Tourism fragment = new Tourism();
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
        setBottomRec();
    }

    private void setBottomRec() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        viewBinding.bottomRec.setLayoutManager(linearLayoutManager);

        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            integers.add(R.drawable.tourism_home_image);
        }

        TourisumAdapter tourisumAdapter = new TourisumAdapter(integers);

        viewBinding.bottomRec.setAdapter(tourisumAdapter);
    }

}