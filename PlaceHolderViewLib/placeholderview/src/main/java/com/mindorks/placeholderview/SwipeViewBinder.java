package com.mindorks.placeholderview;

import android.animation.Animator;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.*;
import android.widget.FrameLayout;


import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by janisharali on 26/08/16.
 */
public class SwipeViewBinder<T, V extends View> extends ViewBinder<T, V>{

    private V mLayoutView;
    private SwipeCallback mCallback;
    private Animator.AnimatorListener mAnimatorListener;
    private int mSwipeType = SwipePlaceHolderView.SWIPE_TYPE_DEFAULT;
    private boolean mIsReverse;

    public SwipeViewBinder(T resolver) {
        super(resolver);
    }

    public void setSwipeCallback(SwipeCallback callback) {
        mCallback = callback;
    }

    protected void bindView(V promptsView, int position, int swipeType) {
        super.bindView(promptsView, position);
        mLayoutView = promptsView;
        mSwipeType = swipeType;
        switch (swipeType){
            case SwipePlaceHolderView.SWIPE_TYPE_DEFAULT:
                setDefaultTouchListener(promptsView);
                break;
            case SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL:
                setHorizontalTouchListener(promptsView);
                break;
            case SwipePlaceHolderView.SWIPE_TYPE_VERTICAL:
                setVerticalTouchListener(promptsView);
                break;
        }
    }

    private void bindSwipeIn(final T resolver){
        for(final Method method : resolver.getClass().getDeclaredMethods()) {
            Annotation annotation = method.getAnnotation(SwipeIn.class);
            if(annotation instanceof SwipeIn) {
                try {
                    method.setAccessible(true);
                    method.invoke(resolver);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void bindSwipeOut(final T resolver){
        for(final Method method : resolver.getClass().getDeclaredMethods()) {
            Annotation annotation = method.getAnnotation(SwipeOut.class);
            if(annotation instanceof SwipeOut) {
                try {
                    method.setAccessible(true);
                    method.invoke(resolver);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void serAnimatorListener(){
        mAnimatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(mCallback != null){
                    mCallback.onRemoveView(SwipeViewBinder.this);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };
    }

    private void setDefaultTouchListener(final V view){
        serAnimatorListener();
        final FrameLayout.LayoutParams originalParamsInitial = (FrameLayout.LayoutParams) view.getLayoutParams();
        final int originalTopMargin = originalParamsInitial.topMargin;
        final int originalLeftMargin = originalParamsInitial.leftMargin;
        view.setOnTouchListener(new View.OnTouchListener() {
            private float dx;
            private float dy;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final PointF pointerCurrentPoint = new PointF(event.getRawX(), event.getRawY());
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        FrameLayout.LayoutParams layoutParamsInitial = (FrameLayout.LayoutParams) v.getLayoutParams();
                        dx = pointerCurrentPoint.x - layoutParamsInitial.leftMargin;
                        dy = pointerCurrentPoint.y - layoutParamsInitial.topMargin;
                        break;
                    case MotionEvent.ACTION_UP:
                        float distSlideX = pointerCurrentPoint.x - dx;
                        float distSlideY = pointerCurrentPoint.y - dy;

                        distSlideX = distSlideX < 0 ? -distSlideX : distSlideX;
                        distSlideY = distSlideY < 0 ? -distSlideY : distSlideY;
                        if(distSlideX < v.getWidth() / 2 && distSlideY < v.getHeight() / 2){
                            FrameLayout.LayoutParams layoutParamsFinal = (FrameLayout.LayoutParams) v.getLayoutParams();
                            layoutParamsFinal.topMargin = originalTopMargin;
                            layoutParamsFinal.leftMargin = originalLeftMargin;
                            v.setLayoutParams(layoutParamsFinal);
                        }else{
                            DisplayMetrics displayMetrics = v.getContext().getResources().getDisplayMetrics();
                            float transX = displayMetrics.widthPixels;
                            float transY = displayMetrics.heightPixels;

                            if(pointerCurrentPoint.x > displayMetrics.widthPixels / 2
                                    && pointerCurrentPoint.y > displayMetrics.heightPixels / 2){
                                bindSwipeIn(getResolver());
                            }
                            else if(pointerCurrentPoint.x > displayMetrics.widthPixels / 2
                                    && pointerCurrentPoint.y < displayMetrics.heightPixels / 2){
                                transY = -v.getHeight();
                                bindSwipeIn(getResolver());
                            }
                            else if(pointerCurrentPoint.x < displayMetrics.widthPixels / 2
                                    && pointerCurrentPoint.y > displayMetrics.heightPixels / 2){
                                transX = -v.getWidth();
                                bindSwipeOut(getResolver());
                            }
                            else if(pointerCurrentPoint.x < displayMetrics.widthPixels / 2
                                    && pointerCurrentPoint.y < displayMetrics.heightPixels / 2){
                                transY = -v.getHeight();
                                transX = -v.getWidth();
                                bindSwipeOut(getResolver());
                            }

                            view.animate()
                                    .translationX(transX)
                                    .translationY(transY)
                                    .setInterpolator(new DecelerateInterpolator(0.3f))
                                    .setDuration(100)
                                    .setListener(mAnimatorListener)
                                    .start();
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        FrameLayout.LayoutParams layoutParamsTemp = (FrameLayout.LayoutParams) v.getLayoutParams();
                        layoutParamsTemp.topMargin = (int)(pointerCurrentPoint.y - dy);
                        layoutParamsTemp.leftMargin = (int)(pointerCurrentPoint.x - dx);
                        v.setLayoutParams(layoutParamsTemp);
                        break;
                }
                return true;
            }
        });
    }

    private void setHorizontalTouchListener(final V view){
        serAnimatorListener();
        final FrameLayout.LayoutParams originalParamsInitial = (FrameLayout.LayoutParams) view.getLayoutParams();
        final int originalLeftMargin = originalParamsInitial.leftMargin;
        view.setOnTouchListener(new View.OnTouchListener() {
            private float dx;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final float x = event.getRawX();
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        FrameLayout.LayoutParams layoutParamsInitial = (FrameLayout.LayoutParams) v.getLayoutParams();
                        dx = x - layoutParamsInitial.leftMargin;
                        break;
                    case MotionEvent.ACTION_UP:
                        float distSlideX = x - dx;

                        distSlideX = distSlideX < 0 ? -distSlideX : distSlideX;
                        if(distSlideX < v.getWidth() / 2){
                            FrameLayout.LayoutParams layoutParamsFinal = (FrameLayout.LayoutParams) v.getLayoutParams();
                            layoutParamsFinal.leftMargin = originalLeftMargin;
                            v.setLayoutParams(layoutParamsFinal);
                        }else{
                            DisplayMetrics displayMetrics = v.getContext().getResources().getDisplayMetrics();
                            float transX = displayMetrics.widthPixels;
                            if(x < displayMetrics.widthPixels / 2){
                                transX = -v.getWidth();
                                bindSwipeOut(getResolver());
                            }else{
                                bindSwipeIn(getResolver());
                            }
                            view.animate()
                                    .translationX(transX)
                                    .setInterpolator(new DecelerateInterpolator(0.3f))
                                    .setDuration(100)
                                    .setListener(mAnimatorListener)
                                    .start();

                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        FrameLayout.LayoutParams layoutParamsTemp = (FrameLayout.LayoutParams) v.getLayoutParams();
                        layoutParamsTemp.leftMargin = (int)(x - dx);
                        v.setLayoutParams(layoutParamsTemp);
                        break;
                }
                return true;
            }
        });
    }

    private void setVerticalTouchListener(final V view){
        serAnimatorListener();
        final FrameLayout.LayoutParams originalParamsInitial = (FrameLayout.LayoutParams) view.getLayoutParams();
        final int originalTopMargin = originalParamsInitial.topMargin;
        view.setOnTouchListener(new View.OnTouchListener() {
            private float dy;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final float y = event.getRawY();
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        FrameLayout.LayoutParams layoutParamsInitial = (FrameLayout.LayoutParams) v.getLayoutParams();
                        dy = y - layoutParamsInitial.topMargin;
                        break;
                    case MotionEvent.ACTION_UP:
                        float distSlideY = y - dy;

                        distSlideY = distSlideY < 0 ? -distSlideY : distSlideY;
                        if(distSlideY < v.getHeight() / 2){
                            FrameLayout.LayoutParams layoutParamsFinal = (FrameLayout.LayoutParams) v.getLayoutParams();
                            layoutParamsFinal.topMargin = originalTopMargin;
                            v.setLayoutParams(layoutParamsFinal);
                        }else{
                            DisplayMetrics displayMetrics = v.getContext().getResources().getDisplayMetrics();
                            float transY = displayMetrics.heightPixels;
                            if(y < displayMetrics.heightPixels / 2){
                                transY = -v.getHeight();
                                bindSwipeOut(getResolver());
                            }else{
                                bindSwipeIn(getResolver());
                            }
                            view.animate()
                                    .translationY(transY)
                                    .setInterpolator(new DecelerateInterpolator(0.3f))
                                    .setDuration(100)
                                    .setListener(mAnimatorListener)
                                    .start();
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        FrameLayout.LayoutParams layoutParamsTemp = (FrameLayout.LayoutParams) v.getLayoutParams();
                        layoutParamsTemp.topMargin = (int)(y - dy);
                        v.setLayoutParams(layoutParamsTemp);
                        break;
                }
                return true;
            }
        });
    }

    protected V getLayoutView() {
        return mLayoutView;
    }

    protected interface SwipeCallback{
        void onRemoveView(SwipeViewBinder swipeViewBinder);
    }
}
