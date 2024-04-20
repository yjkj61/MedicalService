package com.example.medicalservice.tools;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int horizontalSpacing;
    private final int verticalSpacing;

    public GridSpaceItemDecoration(int horizontalSpacing, int verticalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
        this.verticalSpacing = verticalSpacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int spanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();

        // 计算每个item的列索引
        int column = position % spanCount;

        // 计算item之间的水平间距
        outRect.left = column * horizontalSpacing / spanCount;
        outRect.right = horizontalSpacing - (column + 1) * horizontalSpacing / spanCount;

        // 计算item之间的垂直间距
        if (position >= spanCount) {
            outRect.top = verticalSpacing;
        }

        // 设置下边距
        outRect.bottom = verticalSpacing;
    }
}
