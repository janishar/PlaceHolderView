package com.mindorks.placeholderview;

import android.graphics.PointF;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;

/**
 * Created by janisharali on 09/08/17.
 */

public abstract class SwipeDirectionalViewBinder<T, V extends SwipePlaceHolderView.FrameView,
        P extends SwipeDirectionalView.SwipeDirectionalOption,
        Q extends SwipeDecor> extends SwipeViewBinder<T, V, P, Q> {

    //    TODO: Make mHasInterceptedEvent a AtomicBoolean, to make it thread safe.
    private boolean mHasInterceptedEvent = false;
    private int mOriginalLeftMargin;
    private int mOriginalTopMargin;
    private float mTransXToRestore;
    private float mTransYToRestore;

    protected SwipeDirectionalViewBinder(T resolver, int layoutId, boolean nullable) {
        super(resolver, layoutId, nullable);
    }

    @Override
    protected void setDefaultTouchListener(final V view) {
        setAnimatorListener();
        final DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();

        FrameLayout.LayoutParams layoutParamsOriginal = (FrameLayout.LayoutParams) view.getLayoutParams();
        mOriginalLeftMargin = layoutParamsOriginal.leftMargin;
        mOriginalTopMargin = layoutParamsOriginal.topMargin;

        mTransXToRestore = view.getTranslationX();
        mTransYToRestore = view.getTranslationY();

        view.setOnTouchListener(new View.OnTouchListener() {
            private float dx;
            private float dy;
            private int activePointerId = SwipeDecor.PRIMITIVE_NULL;
            private boolean resetDone = false;
            private PointF pointerCurrentPoint = new PointF();
            private PointF pointerStartingPoint = new PointF();

            @Override
            public boolean onTouch(final View v, MotionEvent event) {

                if (getSwipeOption().getIsTouchSwipeLocked()) {
                    if (getSwipeOption().getIsViewToRestoreOnTouchLock()) {
                        getSwipeOption().setIsViewToRestoreOnTouchLock(false);
                        animateSwipeRestore(v, mOriginalTopMargin, mOriginalLeftMargin, getSwipeType());
                    }
                    return true;
                }

                if (getSwipeOption().getIsViewLocked()) {
                    if (getSwipeOption().getIsViewToRestoredOnLock()) {
                        getSwipeOption().setIsViewToRestoredOnLock(false);
                        animateSwipeRestore(v, mOriginalTopMargin, mOriginalLeftMargin, getSwipeType());
                    }
                    return true;
                }

                if (!mHasInterceptedEvent) {
                    pointerStartingPoint.set(event.getRawX(), event.getRawY());
                    pointerCurrentPoint.set(event.getRawX(), event.getRawY());
                    activePointerId = event.getPointerId(0);
                    resetDone = false;
                    FrameLayout.LayoutParams layoutParamsOriginal = (FrameLayout.LayoutParams) v.getLayoutParams();
                    dx = pointerCurrentPoint.x - layoutParamsOriginal.leftMargin;
                    dy = pointerCurrentPoint.y - layoutParamsOriginal.topMargin;
                    mHasInterceptedEvent = true;
                }

                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        if (event.getPointerId(event.getActionIndex()) == activePointerId && !resetDone) {
                        } else {
                            break;
                        }
                    case MotionEvent.ACTION_UP:
                        if (!resetDone) {
                            float distSlideX = pointerCurrentPoint.x - dx;
                            float distSlideY = pointerCurrentPoint.y - dy;

                            distSlideX = distSlideX < 0 ? -distSlideX : distSlideX;
                            distSlideY = distSlideY < 0 ? -distSlideY : distSlideY;
                            if (distSlideX < displayMetrics.widthPixels / getSwipeOption().getWidthSwipeDistFactor()
                                    && distSlideY < displayMetrics.heightPixels / getSwipeOption().getHeightSwipeDistFactor()) {
                                animateSwipeRestore(v, mOriginalTopMargin, mOriginalLeftMargin, getSwipeType());
                            } else {
                                if (!getSwipeOption().getIsPutBackActive()) {
                                    blockTouch();
                                }

                                float transX = displayMetrics.widthPixels;
                                float transY = displayMetrics.heightPixels;

                                float delX = pointerCurrentPoint.x - pointerStartingPoint.x;
                                float delY = pointerCurrentPoint.y - pointerStartingPoint.y;

                                if (pointerCurrentPoint.x >= pointerStartingPoint.x
                                        && pointerCurrentPoint.y >= pointerStartingPoint.y) {

                                    // RIGHT-BOTTOM
                                    if (delX > getSwipeOption().getSwipeVerticalThreshold()
                                            && delY <= getSwipeOption().getSwipeHorizontalThreshold()) {
                                        transY = v.getTranslationY();
                                        bindSwipeInDirectional(SwipeDirection.RIGHT);

                                    } else if (delX <= getSwipeOption().getSwipeVerticalThreshold()
                                            && delY > getSwipeOption().getSwipeHorizontalThreshold()) {
                                        transX = v.getTranslationX();
                                        bindSwipeInDirectional(SwipeDirection.BOTTOM);

                                    } else {
                                        bindSwipeInDirectional(SwipeDirection.RIGHT_BOTTOM);
                                    }

                                } else if (pointerCurrentPoint.x > pointerStartingPoint.x
                                        && pointerCurrentPoint.y < pointerStartingPoint.y) {

                                    // RIGHT-TOP
                                    transY = -v.getHeight();
                                    delY *= -1;

                                    if (delX > getSwipeOption().getSwipeVerticalThreshold()
                                            && delY <= getSwipeOption().getSwipeHorizontalThreshold()) {
                                        transY = v.getTranslationY();
                                        bindSwipeInDirectional(SwipeDirection.RIGHT);

                                    } else if (delX <= getSwipeOption().getSwipeVerticalThreshold()
                                            && delY > getSwipeOption().getSwipeHorizontalThreshold()) {
                                        transX = v.getTranslationX();
                                        bindSwipeOutDirectional(SwipeDirection.TOP);

                                    } else {
                                        bindSwipeOutDirectional(SwipeDirection.RIGHT_TOP);
                                    }

                                } else if (pointerCurrentPoint.x < pointerStartingPoint.x
                                        && pointerCurrentPoint.y >= pointerStartingPoint.y) {

                                    // LEFT-BOTTOM
                                    transX = -v.getWidth();
                                    delX *= -1;

                                    if (delX > getSwipeOption().getSwipeVerticalThreshold()
                                            && delY <= getSwipeOption().getSwipeHorizontalThreshold()) {
                                        transY = v.getTranslationY();
                                        bindSwipeOutDirectional(SwipeDirection.LEFT);

                                    } else if (delX <= getSwipeOption().getSwipeVerticalThreshold()
                                            && delY > getSwipeOption().getSwipeHorizontalThreshold()) {
                                        transX = v.getTranslationX();
                                        bindSwipeInDirectional(SwipeDirection.BOTTOM);

                                    } else {
                                        bindSwipeOutDirectional(SwipeDirection.LEFT_BOTTOM);
                                    }

                                } else if (pointerCurrentPoint.x <= pointerStartingPoint.x
                                        && pointerCurrentPoint.y < pointerStartingPoint.y) {

                                    // LEFT-TOP
                                    transY = -v.getHeight();
                                    transX = -v.getWidth();

                                    delX *= -1;
                                    delY *= -1;

                                    if (delX > getSwipeOption().getSwipeVerticalThreshold()
                                            && delY <= getSwipeOption().getSwipeHorizontalThreshold()) {
                                        transY = v.getTranslationY();
                                        bindSwipeOutDirectional(SwipeDirection.LEFT);

                                    } else if (delX <= getSwipeOption().getSwipeVerticalThreshold()
                                            && delY > getSwipeOption().getSwipeHorizontalThreshold()) {
                                        transX = v.getTranslationX();
                                        bindSwipeOutDirectional(SwipeDirection.TOP);

                                    } else {
                                        bindSwipeOutDirectional(SwipeDirection.LEFT_TOP);
                                    }
                                }

                                view.animate()
                                        .translationX(transX)
                                        .translationY(transY)
                                        .setInterpolator(new AccelerateInterpolator(getSwipeDecor().getSwipeAnimFactor()))
                                        .setDuration((long) (getSwipeDecor().getSwipeAnimTime() * 1.25))
                                        .setListener(getViewRemoveAnimatorListener())
                                        .start();
                            }
                            new CountDownTimer(getSwipeDecor().getSwipeAnimTime(), getSwipeDecor().getSwipeAnimTime()) {
                                public void onTick(long millisUntilFinished) {
                                }

                                public void onFinish() {
                                    mHasInterceptedEvent = false;
                                }
                            }.start();
                            resetDone = true;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (!resetDone && event.findPointerIndex(activePointerId) != SwipeDecor.PRIMITIVE_NULL) {
                            pointerCurrentPoint.set(event.getRawX(), event.getRawY());
                            FrameLayout.LayoutParams layoutParamsTemp = (FrameLayout.LayoutParams) v.getLayoutParams();
                            layoutParamsTemp.topMargin = (int) (pointerCurrentPoint.y - dy);
                            layoutParamsTemp.leftMargin = (int) (pointerCurrentPoint.x - dx);
                            v.setLayoutParams(layoutParamsTemp);

                            int distanceMovedTop = layoutParamsTemp.topMargin - mOriginalTopMargin;
                            int distanceMovedLeft = layoutParamsTemp.leftMargin - mOriginalLeftMargin;
                            getCallback().onAnimateView(distanceMovedLeft, distanceMovedTop, displayMetrics.widthPixels / getSwipeOption().getWidthSwipeDistFactor(),
                                    displayMetrics.heightPixels / getSwipeOption().getHeightSwipeDistFactor(), SwipeDirectionalViewBinder.this);

                            broadcastMoveDirection(pointerCurrentPoint.x, pointerCurrentPoint.y,
                                    pointerStartingPoint.x, pointerStartingPoint.y);
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void broadcastMoveDirection(float xCurrent, float yCurrent, float xStart, float yStart) {

        // send the touch points
        bindSwipeTouch(xStart, yStart, xCurrent, yCurrent);

        float delX = xCurrent - xStart;
        float delY = yCurrent - yStart;

        if (xCurrent >= xStart && yCurrent >= yStart) {

            // RIGHT-BOTTOM
            if (delX > getSwipeOption().getSwipeVerticalThreshold()
                    && delY <= getSwipeOption().getSwipeHorizontalThreshold()) {
                bindSwipingDirection(SwipeDirection.RIGHT);

            } else if (delX <= getSwipeOption().getSwipeVerticalThreshold()
                    && delY > getSwipeOption().getSwipeHorizontalThreshold()) {
                bindSwipingDirection(SwipeDirection.BOTTOM);

            } else {
                bindSwipingDirection(SwipeDirection.RIGHT_BOTTOM);
            }

        } else if (xCurrent > xStart && yCurrent < yStart) {

            // RIGHT-TOP
            delY *= -1;

            if (delX > getSwipeOption().getSwipeVerticalThreshold()
                    && delY <= getSwipeOption().getSwipeHorizontalThreshold()) {
                bindSwipingDirection(SwipeDirection.RIGHT);

            } else if (delX <= getSwipeOption().getSwipeVerticalThreshold()
                    && delY > getSwipeOption().getSwipeHorizontalThreshold()) {
                bindSwipingDirection(SwipeDirection.TOP);

            } else {
                bindSwipingDirection(SwipeDirection.RIGHT_TOP);
            }

        } else if (xCurrent < xStart && yCurrent >= yStart) {

            // LEFT-BOTTOM
            delX *= -1;

            if (delX > getSwipeOption().getSwipeVerticalThreshold()
                    && delY <= getSwipeOption().getSwipeHorizontalThreshold()) {
                bindSwipingDirection(SwipeDirection.LEFT);

            } else if (delX <= getSwipeOption().getSwipeVerticalThreshold()
                    && delY > getSwipeOption().getSwipeHorizontalThreshold()) {
                bindSwipingDirection(SwipeDirection.BOTTOM);

            } else {
                bindSwipingDirection(SwipeDirection.LEFT_BOTTOM);
            }

        } else if (xCurrent <= xStart && yCurrent < yStart) {

            // LEFT-TOP
            delX *= -1;
            delY *= -1;

            if (delX > getSwipeOption().getSwipeVerticalThreshold()
                    && delY <= getSwipeOption().getSwipeHorizontalThreshold()) {
                bindSwipingDirection(SwipeDirection.LEFT);

            } else if (delX <= getSwipeOption().getSwipeVerticalThreshold()
                    && delY > getSwipeOption().getSwipeHorizontalThreshold()) {
                bindSwipingDirection(SwipeDirection.TOP);

            } else {
                bindSwipingDirection(SwipeDirection.LEFT_TOP);
            }
        }
    }

    protected abstract void bindSwipingDirection(SwipeDirection direction);

    protected abstract void bindSwipeInDirectional(SwipeDirection direction);

    protected abstract void bindSwipeOutDirectional(SwipeDirection direction);

    protected abstract void bindSwipeTouch(float xStart, float yStart, float xCurrent, float yCurrent);

}
