package com.aboluo.XUtils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Add the callback of reaching bottom into RecyclerView
 * Support three LayoutManager(Linear、Grid、StaggeredGrid)
 * Created by wiky on 2016/3/26.
 * 这个和RBCallbkRecyclerView唯一不同的是
 *  @Override
 *  protected void onMeasure(int widthSpec, int heightSpec) {
 *  int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
 *  MeasureSpec.AT_MOST);
 *  super.onMeasure(widthSpec, expandSpec);
}
 */
public class RBCallbkRecyclerView2 extends RecyclerView {
    private OnReachBottomListener onReachBottomListener;
    private boolean isInTheBottom = false;
    /**
     * reachBottomRow = 1;(default)
     * mean : when the lastVisibleRow is lastRow , call the onReachBottom();
     * reachBottomRow = 2;
     * mean : when the lastVisibleRow is Penultimate Row , call the onReachBottom();
     * And so on
     */
    private int reachBottomRow = 1;

    public RBCallbkRecyclerView2(Context context) {
        super(context);
    }

    public RBCallbkRecyclerView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RBCallbkRecyclerView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);

        if (onReachBottomListener != null) {
            LayoutManager layoutManager = getLayoutManager();
            if (layoutManager == null) { //it maybe unnecessary
                throw new RuntimeException("LayoutManager is null,Please check it!");
            }
            Adapter adapter = getAdapter();
            if (adapter == null) { //it maybe unnecessary
                throw new RuntimeException("Adapter is null,Please check it!");
            }
            boolean isReachBottom = false;
            //is GridLayoutManager
            if (layoutManager instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                int rowCount = adapter.getItemCount() / gridLayoutManager.getSpanCount();
                int lastVisibleRowPosition = gridLayoutManager.findLastVisibleItemPosition() / gridLayoutManager.getSpanCount();
                isReachBottom = (lastVisibleRowPosition >= rowCount - reachBottomRow);
            }
            //is LinearLayoutManager
            else if (layoutManager instanceof LinearLayoutManager) {
                int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                int rowCount = adapter.getItemCount();
                if (reachBottomRow > rowCount)
                    reachBottomRow = 1;
                isReachBottom = (lastVisibleItemPosition >= rowCount - reachBottomRow);
            }
            //is StaggeredGridLayoutManager
            else if (layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                int spanCount = staggeredGridLayoutManager.getSpanCount();
                int[] into = new int[spanCount];
                int[] eachSpanListVisibleItemPosition = staggeredGridLayoutManager.findLastVisibleItemPositions(into);
                for (int i = 0; i < spanCount; i++) {
                    if (eachSpanListVisibleItemPosition[i] > adapter.getItemCount() - reachBottomRow * spanCount) {
                        isReachBottom = true;
                        break;
                    }
                }
            }

            if (!isReachBottom) {
                isInTheBottom = false;
            } else if (!isInTheBottom) {
                onReachBottomListener.onReachBottom();
                isInTheBottom = true;
                Log.d("RBCallbkRecyclerView", "onReachBottom");
            }
        }

    }

    public void setReachBottomRow(int reachBottomRow) {
        if (reachBottomRow < 1)
            reachBottomRow = 1;
        this.reachBottomRow = reachBottomRow;
    }

    public interface OnReachBottomListener {
        void onReachBottom();
    }

    public void setOnReachBottomListener(OnReachBottomListener onReachBottomListener) {
        this.onReachBottomListener = onReachBottomListener;
    }

}