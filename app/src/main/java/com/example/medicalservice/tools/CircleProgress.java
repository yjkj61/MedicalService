package com.example.medicalservice.tools;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;

import com.example.medicalservice.diyView.QQStepView;

public class CircleProgress {


    public static void set(QQStepView stepView,int max,int progress,String bottomText){
        stepView.setStepMax(7);
        //属性动画
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 4);
        //先快后慢的效果
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(animation -> {
            float animatedValue = (float) animation.getAnimatedValue();
            stepView.setCurrentStep((int) animatedValue,"day");
        });
        stepView.postDelayed(valueAnimator::start, 1000);
    }
}
