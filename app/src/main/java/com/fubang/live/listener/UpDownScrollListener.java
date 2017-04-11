package com.fubang.live.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.socks.library.KLog;

/**
 * Created by jacky on 17/3/27.
 */
public abstract class UpDownScrollListener extends RecyclerView.OnScrollListener {
    private static final int HIDE_THRESHOLD = 30;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;
    private int mItemSize = 0;

    public UpDownScrollListener() {

    }

    /**
     * @param recyclerView
     * @param dx           横向的滚动距离
     * @param dy           纵向的滚动距离
     *                     记录的是两个滚动事件之间的偏移量，而不是总的滚动距离。
     */
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        KLog.e(dy);
        if (dy > 10) {
            onHide();
        } else if (dy < -20) {
            onShow();
        }
    }

    public abstract void onHide();

    public abstract void onShow();
}
