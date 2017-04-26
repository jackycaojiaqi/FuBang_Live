package com.fubang.live.widget;

/**
 * 获取左右滑动事件，如果滑动的距离超过屏幕宽度的1/5，就返回该滑动事件给activity或者fragment
 * 使用方法：
 * gestureLayout.setSwipeGestureListener(new onSwipeGestureListener() {
			
			@Override
			public void onRightSwipe() {
				Log.i("debug", "onRightSwipe");
			}
			
			@Override
			public void onLeftSwipe() {
				Log.i("debug", "onLeftSwipe");
			}
		});
 */

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

public class DetectTouchGestureLayout extends FrameLayout{
	private int mTouchSlop, mDownX, mDownY, mTempX, totalMoveX, viewWidth;
	private boolean isSilding;
	private onSwipeGestureListener swipeListener;
	
	public DetectTouchGestureLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		totalMoveX = 0;
	}
	
	public interface onSwipeGestureListener{
		public void onLeftSwipe();
		public void onRightSwipe();
	}
	
	public void setSwipeGestureListener(onSwipeGestureListener listener) {
		this.swipeListener = listener;
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		viewWidth = this.getWidth();
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mDownX = mTempX = (int) ev.getRawX();
			mDownY = (int) ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			int moveX = (int) ev.getRawX();
			// 满足此条件屏蔽SildingFinishLayout里面子类的touch事件
			if (Math.abs(moveX - mDownX) > mTouchSlop
					&& Math.abs((int) ev.getRawY() - mDownY) < mTouchSlop) {
				return true;
			}
			break;
		}

		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			int moveX = (int) event.getRawX();
			int deltaX = mTempX - moveX;
//			Log.i("debug", "deltaX:" + deltaX + "mTouchSlop:" + mTouchSlop);
			mTempX = moveX;
			if (Math.abs(moveX - mDownX) > mTouchSlop
					&& Math.abs((int) event.getRawY() - mDownY) < mTouchSlop) {
				isSilding = true;
			}

			if (Math.abs(moveX - mDownX) >= 0 && isSilding) {
//				mContentView.scrollBy(deltaX, 0);
				totalMoveX += deltaX;
			}
			break;
		case MotionEvent.ACTION_UP:
			isSilding = false;
//			Log.i("debug", "TotoalMoveX:" + totalMoveX + "viewVidth:" + viewWidth);
			if (Math.abs(totalMoveX) >= viewWidth / 5) {
				if (totalMoveX > 0) {
					swipeListener.onLeftSwipe();
				}else {
					swipeListener.onRightSwipe();
				}
			}
			totalMoveX = 0;
			break;
		}

		return true;
	}
	
}
