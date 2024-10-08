package com.example.medicalservice.tools.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

/**
 * @description 解决webview软键盘挡住输入框的问题
 * @author: Lenovo
 * @date: 2024/5/17
 */
public class AndroidBug5497Workaround {

    // For more information, see https://code.google.com/p/android/issues/detail?id=5497
    // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.

    public static void assistActivity(Activity activity) {
        new AndroidBug5497Workaround(activity);
    }

    private View mChildOfContent;
    private int usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;

    private AndroidBug5497Workaround(Activity activity) {
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // keyboard probably just became visible
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = usableHeightSansKeyboard - getNavigationBarHeight(mChildOfContent.getContext());
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }

    private int getNavigationBarHeight(Context context) {
        boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
        if (!hasMenuKey) {
            // This device has a navigation bar
            int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            if (checkNavigationBarShow(mChildOfContent.getContext()) && resourceId > 0) {
                return context.getResources().getDimensionPixelSize(resourceId);
            } else {
                return 0;
            }
        }
        return 0;
    }

    private boolean checkNavigationBarShow(@NonNull Context context) {
        boolean show = false;
        if (context instanceof Activity) {
            Window window = ((Activity) context).getWindow();
            Display display = window.getWindowManager().getDefaultDisplay();
            Point point = new Point();
            display.getRealSize(point);

            View decorView = window.getDecorView();
            Configuration conf = context.getResources().getConfiguration();
            if (Configuration.ORIENTATION_LANDSCAPE == conf.orientation) {
                View contentView = decorView.findViewById(android.R.id.content);
                show = (point.x != contentView.getWidth());
            } else {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                show = (rect.bottom != point.y);
            }
        }
        return show;
    }


}
