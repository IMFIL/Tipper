package com.uottawa.tipper;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;
import java.lang.reflect.Field;

/**
 * Created by filipslatinac on 2017-05-16.
 */

public class CustomViewPager extends ViewPager {
    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    private boolean goRight = false;
    private boolean goLeft = false;

    public CustomViewPager(Context context) {
        super(context);
        setMyScroller();
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMyScroller();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return onMotion(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return onMotion(event);
    }

    private void setMyScroller() {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            scroller.set(this, new MyScroller(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyScroller extends Scroller {
        public MyScroller(Context context) {
            super(context, new DecelerateInterpolator());
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, 350 /*1 secs*/);
        }
    }

    public void changeSlideRightOption(boolean changed){
        goRight = changed;
    }

    public void changeSlideLeftOption(boolean changed){
        goLeft = changed;
    }

    private boolean onMotion(MotionEvent event){
//
//        final int action = MotionEventCompat.getActionMasked(event);
//
//        switch (action){
//            case MotionEvent.ACTION_DOWN:
//                x1 = event.getX();
//                break;
//            case MotionEvent.ACTION_UP:
//                x2 = event.getX();
//                float deltaX = x2-x1;
//                if (x2 > x1){
//                    if (Math.abs(deltaX) > MIN_DISTANCE) {
//                        return goLeft;
//                    }
//                    return true;
//                }
//
//                else if (x2 < x1){
//                    if (Math.abs(deltaX) > MIN_DISTANCE) {
//                        return goRight;
//                    }
//                    return true;
//                }
//
//                else{
//                    return true;
//                }
//        }

        return false;
    }
}
