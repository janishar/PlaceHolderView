package com.mindorks.placeholderview;

import android.animation.Animator;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.*;
import android.view.animation.Animation;
import android.widget.FrameLayout;

/**
 * Created by janisharali on 26/08/16.
 */
public class SwipeViewBinder<T, V extends View> extends ViewBinder<T, V>{

    private V mLayoutView;
    private SwipeCallback mCallback;
    private Animator.AnimatorListener mAnimatorListener;

    public SwipeViewBinder(T resolver) {
        super(resolver);
    }

    public void setSwipeCallback(SwipeCallback callback) {
        mCallback = callback;
    }

    @Override
    protected void bindView(V promptsView, int position) {
        super.bindView(promptsView, position);
        mLayoutView = promptsView;
        setTouchListener(promptsView);
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

    private void setTouchListener(final V view){
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
                            if(pointerCurrentPoint.x < displayMetrics.widthPixels / 2){transX = -v.getWidth();}
                            if(pointerCurrentPoint.y < displayMetrics.heightPixels / 2){transY = -v.getHeight();}
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

    protected V getLayoutView() {
        return mLayoutView;
    }

    protected interface SwipeCallback{
        void onRemoveView(SwipeViewBinder swipeViewBinder);
    }
}
