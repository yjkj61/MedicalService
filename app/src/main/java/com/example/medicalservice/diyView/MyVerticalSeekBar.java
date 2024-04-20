package com.example.medicalservice.diyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.SeekBar;

public class MyVerticalSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {
    private Drawable mThumb;

    public interface OnSeekBarChangeListener {
        void onProgressChanged(MyVerticalSeekBar VerticalSeekBar, int progress, boolean fromUser);

        void onStartTrackingTouch(MyVerticalSeekBar VerticalSeekBar);

        void onStopTrackingTouch(MyVerticalSeekBar VerticalSeekBar);
    }

    void onStartTrackingTouch() {
        if (mOnSeekBarChangeListener != null) {
            mOnSeekBarChangeListener.onStartTrackingTouch(this);
        }
    }

    void onStopTrackingTouch() {
        if (mOnSeekBarChangeListener != null) {
            mOnSeekBarChangeListener.onStopTrackingTouch(this);
        }
    }

    void onProgressRefresh(float scale, boolean fromUser) {
        Log.i("6", "onProgressRefresh==>scale" + scale);
        Drawable thumb = mThumb;
        if (thumb != null) {
            setThumbPos(getHeight(), thumb, scale, Integer.MIN_VALUE);
            invalidate();
        }
        if (mOnSeekBarChangeListener != null) {
            mOnSeekBarChangeListener.onProgressChanged(this, getProgress(), fromUser);
        }
    }

    public void setOnSeekBarChangeListener(MyVerticalSeekBar.OnSeekBarChangeListener l) {
        mOnSeekBarChangeListener = l;
    }

    private void setThumbPos(int w, Drawable thumb, float scale, int gap) {
        Log.i("6", "setThumbPos==>w" + w);
        int available = w + getPaddingLeft() - getPaddingRight();
        int thumbWidth = thumb.getIntrinsicWidth();
        int thumbHeight = thumb.getIntrinsicHeight();
        available -= thumbWidth;
        // The extra space for the thumb to move on the track
        available += getThumbOffset() * 2;
        int thumbPos = (int) (scale * available);
        int topBound, bottomBound;
        if (gap == Integer.MIN_VALUE) {
            Rect oldBounds = thumb.getBounds();
            topBound = oldBounds.top;
            bottomBound = oldBounds.bottom;
        } else {
            topBound = gap;
            bottomBound = gap + thumbHeight;
        }
        thumb.setBounds(thumbPos, topBound, thumbPos + thumbWidth, bottomBound);
    }

    private MyVerticalSeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener;

    public MyVerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public MyVerticalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public MyVerticalSeekBar(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    protected void onDraw(Canvas c) {
        c.rotate(-90);
        c.translate(-getHeight(), 0);

        super.onDraw(c);
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
        onSizeChanged(getWidth(), getHeight(), 0, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("TAG", "onKeyDown: 22222222222222222222222");
                onStartTrackingTouch();
                setProgress(getMax() - (int) (getMax() * event.getY() / getHeight()));
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                break;
            case MotionEvent.ACTION_MOVE:
                setProgress(getMax() - (int) (getMax() * event.getY() / getHeight()));
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                break;
            case MotionEvent.ACTION_UP:
                onStopTrackingTouch();
                setProgress(getMax() - (int) (getMax() * event.getY() / getHeight()));
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                break;

            case MotionEvent.ACTION_CANCEL:
                onStopTrackingTouch();
                break;
        }

        return true;
    }

}
