package com.example.medicalservice;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MainActivityPageAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fs;

    public MainActivityPageAdapter(FragmentManager fm, ArrayList<Fragment> fs) {
        super(fm);
        this.fs = fs;
    }


    @Override
    public Fragment getItem(int i) {
        return fs.get(i);
    }

    @Override
    public int getCount() {
        return fs.size();
    }
}