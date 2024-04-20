package com.example.medicalservice.diyView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class ChildRecyclerview extends RecyclerView {


    public ChildRecyclerview(@NonNull Context context) {
        super(context);
    }

    public ChildRecyclerview(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChildRecyclerview(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
