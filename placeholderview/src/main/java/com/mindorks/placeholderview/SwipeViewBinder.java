package com.mindorks.placeholderview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import static android.view.View.VISIBLE;

/**
 * Created by janisharali on 26/08/16.
 */
public abstract class SwipeViewBinder<
        T,
        V extends SwipePlaceHolderView.FrameView,
        P extends SwipePlaceHolderView.SwipeOption,
        Q extends SwipeDecor> extends ViewBinder<T, V> {

    private static int mFinalLeftMargin;
    private static int mFinalTopMargin;
    private V mLayoutView;
    private SwipeCallback<SwipeViewBinder<T, V, P, Q>> mCallback;
    private Animator.AnimatorListener mViewRemoveAnimatorListener;
    private Animator.AnimatorListener mViewRestoreAnimatorListener;
    private Animator.AnimatorListener mViewPutBackAnimatorListener;
    private int mSwipeType = SwipePlaceHolderView.SWIPE_TYPE_DEFAULT;
    private View mSwipeInMsgView;
    private View mSwipeOutMsgView;
    private P mSwipeOption;
    private Q mSwipeDecor;
//    TODO: Make mHasInterceptedEvent a AtomicBoolean, to make it thread safe.
    private boolean mHasInterceptedEvent = false;
    private int mOriginalLeftMargin;
    private int mOriginalTopMargin;
    private float mTransXToRestore;
    private float mTransYToRestore;

    protected SwipeViewBinder(T resolver, int layoutId, boolean nullable) {
        super(resolver, layoutId, nullable);
    }

    protected void bindView(V promptsView, int position, int swipeType, Q decor,
                            P swipeOption, SwipeCallback<SwipeViewBinder<T, V, P, Q>> callback) {
        mLayoutView = promptsView;
        mSwipeType = swipeType;
        mSwipeOption = swipeOption;
        mSwipeDecor = decor;
        mCallback = callback;

        bindSwipeView(promptsView);
        bindViews(getResolver(), promptsView);
        bindViewPosition(getResolver(), position);
        resolveView(getResolver());
    }

    protected void setOnTouch(){
        bindClick(getResolver(), getLayoutView());
        bindLongClick(getResolver(), getLayoutView());
        bindSwipeHead(getResolver());
        switch (mSwipeType){
            case SwipePlaceHolderView.SWIPE_TYPE_DEFAULT:
                setDefaultTouchListener(mLayoutView);
                break;
            case SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL:
                setHorizontalTouchListener(mLayoutView);
                break;
            case SwipePlaceHolderView.SWIPE_TYPE_VERTICAL:
                setVerticalTouchListener(mLayoutView);
                break;
        }
    }

    protected void setAnimatorListener() {
        mViewRemoveAnimatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                if(mSwipeOption.getIsPutBackActive()){
                    mLayoutView.animate()
                            .translationX(mTransXToRestore)
                            .translationY(mTransYToRestore)
                            .setInterpolator(new AccelerateInterpolator(mSwipeDecor.getSwipeAnimFactor()))
                            .setDuration((long)(mSwipeDecor.getSwipeAnimTime()))
                            .setListener(mViewPutBackAnimatorListener)
                            .start();
                }else if(mCallback != null){
                    mCallback.onRemoveView(SwipeViewBinder.this);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        };

        mViewRestoreAnimatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                if(mCallback != null){
                    mCallback.onResetView(SwipeViewBinder.this);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        };

        mViewPutBackAnimatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                animateSwipeRestore(mLayoutView, mOriginalTopMargin, mOriginalLeftMargin, mSwipeType);
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        };
    }

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

                if(mSwipeOption.getIsTouchSwipeLocked()){
                    if(mSwipeOption.getIsViewToRestoreOnTouchLock()){
                        mSwipeOption.setIsViewToRestoreOnTouchLock(false);
                        animateSwipeRestore(v, mOriginalTopMargin, mOriginalLeftMargin, mSwipeType);
                    }
                    return true;
                }

                if(mSwipeOption.getIsViewLocked()){
                    if(mSwipeOption.getIsViewToRestoredOnLock()){
                        mSwipeOption.setIsViewToRestoredOnLock(false);
                        animateSwipeRestore(v, mOriginalTopMargin, mOriginalLeftMargin, mSwipeType);
                    }
                    return true;
                }

                if(!mHasInterceptedEvent){
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
                        if (event.getPointerId(event.getActionIndex()) == activePointerId && !resetDone) {}
                        else{break;}
                    case MotionEvent.ACTION_UP:
                        if(!resetDone) {
                            float distSlideX = pointerCurrentPoint.x - dx;
                            float distSlideY = pointerCurrentPoint.y - dy;

                            distSlideX = distSlideX < 0 ? -distSlideX : distSlideX;
                            distSlideY = distSlideY < 0 ? -distSlideY : distSlideY;
                            if (distSlideX < displayMetrics.widthPixels / mSwipeOption.getWidthSwipeDistFactor()
                                    && distSlideY < displayMetrics.heightPixels / mSwipeOption.getHeightSwipeDistFactor()) {
                                animateSwipeRestore(v, mOriginalTopMargin, mOriginalLeftMargin, mSwipeType);
                            }
                            else {
                                if(!mSwipeOption.getIsPutBackActive()) {
                                    blockTouch();
                                }

                                float transX = displayMetrics.widthPixels;
                                float transY = displayMetrics.heightPixels;

                                if (pointerCurrentPoint.x >= pointerStartingPoint.x
                                        && pointerCurrentPoint.y >= pointerStartingPoint.y) {
                                    bindSwipeIn(getResolver());
                                } else if (pointerCurrentPoint.x > pointerStartingPoint.x
                                        && pointerCurrentPoint.y < pointerStartingPoint.y) {
                                    transY = -v.getHeight();
                                    bindSwipeIn(getResolver());
                                } else if (pointerCurrentPoint.x < pointerStartingPoint.x
                                        && pointerCurrentPoint.y >= pointerStartingPoint.y) {
                                    transX = -v.getWidth();
                                    bindSwipeOut(getResolver());
                                } else if (pointerCurrentPoint.x <= pointerStartingPoint.x
                                        && pointerCurrentPoint.y < pointerStartingPoint.y) {
                                    transY = -v.getHeight();
                                    transX = -v.getWidth();
                                    bindSwipeOut(getResolver());
                                }

                                view.animate()
                                        .translationX(transX)
                                        .translationY(transY)
                                        .setInterpolator(new AccelerateInterpolator(mSwipeDecor.getSwipeAnimFactor()))
                                        .setDuration((long)(mSwipeDecor.getSwipeAnimTime() * 1.25))
                                        .setListener(mViewRemoveAnimatorListener)
                                        .start();
                            }
                            new CountDownTimer(mSwipeDecor.getSwipeAnimTime(), mSwipeDecor.getSwipeAnimTime()) {
                                public void onTick(long millisUntilFinished) {}
                                public void onFinish() {mHasInterceptedEvent = false;}
                            }.start();
                            resetDone = true;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(!resetDone && event.findPointerIndex(activePointerId) != SwipeDecor.PRIMITIVE_NULL) {
                            pointerCurrentPoint.set(event.getRawX(), event.getRawY());
                            FrameLayout.LayoutParams layoutParamsTemp = (FrameLayout.LayoutParams) v.getLayoutParams();
                            layoutParamsTemp.topMargin = (int) (pointerCurrentPoint.y - dy);
                            layoutParamsTemp.leftMargin = (int) (pointerCurrentPoint.x - dx);
                            v.setLayoutParams(layoutParamsTemp);

                            int distanceMovedTop = layoutParamsTemp.topMargin - mOriginalTopMargin;
                            int distanceMovedLeft = layoutParamsTemp.leftMargin - mOriginalLeftMargin;
                            mCallback.onAnimateView(distanceMovedLeft, distanceMovedTop, displayMetrics.widthPixels / mSwipeOption.getWidthSwipeDistFactor(),
                                    displayMetrics.heightPixels / mSwipeOption.getHeightSwipeDistFactor(), SwipeViewBinder.this);
                        }
                        break;
                }
                return true;
            }
        });
    }

    protected void setHorizontalTouchListener(final V view) {
        setAnimatorListener();
        final DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();

        FrameLayout.LayoutParams layoutParamsOriginal = (FrameLayout.LayoutParams) view.getLayoutParams();
        mOriginalLeftMargin = layoutParamsOriginal.leftMargin;
        mOriginalTopMargin = layoutParamsOriginal.topMargin;
        mTransXToRestore = view.getTranslationX();
        mTransYToRestore = view.getTranslationY();

        view.setOnTouchListener(new View.OnTouchListener() {
            private float xStart;
            private float x;
            private float dx;
            private int activePointerId = SwipeDecor.PRIMITIVE_NULL;
            private boolean resetDone = false;
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(mSwipeOption.getIsTouchSwipeLocked()){
                    if(mSwipeOption.getIsViewToRestoreOnTouchLock()){
                        mSwipeOption.setIsViewToRestoreOnTouchLock(false);
                        animateSwipeRestore(v, mOriginalTopMargin, mOriginalLeftMargin, mSwipeType);
                    }
                    return true;
                }

                if(mSwipeOption.getIsViewLocked()){
                    if(mSwipeOption.getIsViewToRestoredOnLock()){
                        mSwipeOption.setIsViewToRestoredOnLock(false);
                        animateSwipeRestore(v, mOriginalTopMargin, mOriginalLeftMargin, mSwipeType);
                    }
                    return true;
                }

                if(!mHasInterceptedEvent){
                    xStart = event.getRawX();
                    x = event.getRawX();
                    activePointerId = event.getPointerId(0);
                    resetDone = false;
                    FrameLayout.LayoutParams layoutParamsCurrent = (FrameLayout.LayoutParams) v.getLayoutParams();
                    dx = x - layoutParamsCurrent.leftMargin;
                    mHasInterceptedEvent = true;
                }

                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        if (event.getPointerId(event.getActionIndex()) == activePointerId && !resetDone) {}
                        else{break;}
                    case MotionEvent.ACTION_UP:
                        if(!resetDone) {
                            float distSlideX = x - dx;
                            distSlideX = distSlideX < 0 ? -distSlideX : distSlideX;
                            if (distSlideX < displayMetrics.widthPixels / mSwipeOption.getWidthSwipeDistFactor()) {
                                animateSwipeRestore(v, mOriginalTopMargin, mOriginalLeftMargin, mSwipeType);
                            } else {
                                if(!mSwipeOption.getIsPutBackActive()) {
                                    blockTouch();
                                }

                                float transX = displayMetrics.widthPixels;
                                if (x < xStart) {
                                    transX = -v.getWidth();
                                    bindSwipeOut(getResolver());
                                } else {
                                    bindSwipeIn(getResolver());
                                }

                                view.animate()
                                        .translationX(transX)
                                        .setInterpolator(new AccelerateInterpolator(mSwipeDecor.getSwipeAnimFactor()))
                                        .setDuration((long)(mSwipeDecor.getSwipeAnimTime() * 1.25))
                                        .setListener(mViewRemoveAnimatorListener)
                                        .start();
                            }
                            new CountDownTimer(mSwipeDecor.getSwipeAnimTime(), mSwipeDecor.getSwipeAnimTime()) {
                                public void onTick(long millisUntilFinished) {}
                                public void onFinish() {mHasInterceptedEvent = false;}
                            }.start();
                            resetDone = true;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(!resetDone && event.findPointerIndex(activePointerId) != SwipeDecor.PRIMITIVE_NULL) {
                            x = event.getRawX();
                            FrameLayout.LayoutParams layoutParamsTemp = (FrameLayout.LayoutParams) v.getLayoutParams();
                            layoutParamsTemp.leftMargin = (int) (x - dx);
                            v.setLayoutParams(layoutParamsTemp);
                            int distanceMoved = layoutParamsTemp.leftMargin - mOriginalLeftMargin;
                            mCallback.onAnimateView(distanceMoved, 0, displayMetrics.widthPixels / mSwipeOption.getWidthSwipeDistFactor(),
                                    displayMetrics.heightPixels / mSwipeOption.getHeightSwipeDistFactor(), SwipeViewBinder.this);
                        }
                        break;
                }
                return true;
            }
        });
    }

    protected void setVerticalTouchListener(final V view) {
        setAnimatorListener();
        final DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();

        FrameLayout.LayoutParams layoutParamsOriginal = (FrameLayout.LayoutParams) view.getLayoutParams();
        mOriginalLeftMargin = layoutParamsOriginal.leftMargin;
        mOriginalTopMargin = layoutParamsOriginal.topMargin;
        mTransXToRestore = view.getTranslationX();
        mTransYToRestore = view.getTranslationY();

        view.setOnTouchListener(new View.OnTouchListener() {
            private float yStart;
            private float y;
            private float dy;
            private int activePointerId = SwipeDecor.PRIMITIVE_NULL;
            private boolean resetDone = false;
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(mSwipeOption.getIsTouchSwipeLocked()){
                    if(mSwipeOption.getIsViewToRestoreOnTouchLock()){
                        mSwipeOption.setIsViewToRestoreOnTouchLock(false);
                        animateSwipeRestore(v, mOriginalTopMargin, mOriginalLeftMargin, mSwipeType);
                    }
                    return true;
                }

                if(mSwipeOption.getIsViewLocked()){
                    if(mSwipeOption.getIsViewToRestoredOnLock()){
                        mSwipeOption.setIsViewToRestoredOnLock(false);
                        animateSwipeRestore(v, mOriginalTopMargin, mOriginalLeftMargin, mSwipeType);
                    }
                    return true;
                }

                if(!mHasInterceptedEvent){
                    yStart = event.getRawY();
                    y = event.getRawY();
                    activePointerId = event.getPointerId(0);
                    resetDone = false;
                    FrameLayout.LayoutParams layoutParamsOriginal = (FrameLayout.LayoutParams) v.getLayoutParams();
                    dy = y - layoutParamsOriginal.topMargin;
                    mHasInterceptedEvent = true;
                }

                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        if (event.getPointerId(event.getActionIndex()) == activePointerId && !resetDone) {}
                        else{break;}
                    case MotionEvent.ACTION_UP:
                        if(!resetDone) {
                            float distSlideY = y - dy;
                            distSlideY = distSlideY < 0 ? -distSlideY : distSlideY;
                            if (distSlideY < displayMetrics.heightPixels / mSwipeOption.getHeightSwipeDistFactor()) {
                                animateSwipeRestore(v, mOriginalTopMargin, mOriginalLeftMargin, mSwipeType);
                            } else {
                                if(!mSwipeOption.getIsPutBackActive()) {
                                    blockTouch();
                                }

                                float transY = displayMetrics.heightPixels;
                                if (y < yStart) {
                                    transY = -v.getHeight();
                                    bindSwipeOut(getResolver());
                                } else {
                                    bindSwipeIn(getResolver());
                                }
                                view.animate()
                                        .translationY(transY)
                                        .setInterpolator(new AccelerateInterpolator(mSwipeDecor.getSwipeAnimFactor()))
                                        .setDuration((long)(mSwipeDecor.getSwipeAnimTime() * 1.25))
                                        .setListener(mViewRemoveAnimatorListener)
                                        .start();

                            }
                            new CountDownTimer(mSwipeDecor.getSwipeAnimTime(), mSwipeDecor.getSwipeAnimTime()) {
                                public void onTick(long millisUntilFinished) {}
                                public void onFinish() {mHasInterceptedEvent = false;}
                            }.start();
                            resetDone = true;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(!resetDone && event.findPointerIndex(activePointerId) != SwipeDecor.PRIMITIVE_NULL) {
                            y = event.getRawY();
                            FrameLayout.LayoutParams layoutParamsTemp = (FrameLayout.LayoutParams) v.getLayoutParams();
                            layoutParamsTemp.topMargin = (int) (y - dy);
                            v.setLayoutParams(layoutParamsTemp);

                            int distanceMoved = layoutParamsTemp.topMargin - mOriginalTopMargin;
                            mCallback.onAnimateView(0, distanceMoved, displayMetrics.widthPixels / mSwipeOption.getWidthSwipeDistFactor(),
                                    displayMetrics.heightPixels / mSwipeOption.getHeightSwipeDistFactor(), SwipeViewBinder.this);
                        }
                        break;
                }
                return true;
            }
        });
    }

    protected void blockTouch(){
        mLayoutView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent rawEvent) {
                return true;
            }
        });
    }

    protected void animateSwipeRestore(final View v, int originalTopMargin,
                                       int originalLeftMargin, int swipeType) {

        final FrameLayout.LayoutParams layoutParamsFinal =
                (FrameLayout.LayoutParams) v.getLayoutParams();

        ValueAnimator animatorX = null;
        ValueAnimator animatorY = null;
        int animTime = mSwipeDecor.getSwipeAnimTime();
        DecelerateInterpolator decelerateInterpolator =
                new DecelerateInterpolator(mSwipeDecor.getSwipeAnimFactor());
        ViewPropertyAnimator animatorR = v.animate()
                .rotation(0)
                .setInterpolator(decelerateInterpolator)
                .setDuration(animTime)
                .setListener(mViewRestoreAnimatorListener);

        if(swipeType == SwipePlaceHolderView.SWIPE_TYPE_DEFAULT
                || swipeType == SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL){
            animatorX = ValueAnimator.ofInt(layoutParamsFinal.leftMargin, originalLeftMargin);
            animatorX.setInterpolator(decelerateInterpolator);
            animatorX.setDuration(animTime);
            animatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    layoutParamsFinal.leftMargin = (Integer) valueAnimator.getAnimatedValue();
                    v.setLayoutParams(layoutParamsFinal);
                }

            });
        }
        if(swipeType == SwipePlaceHolderView.SWIPE_TYPE_DEFAULT
                || swipeType == SwipePlaceHolderView.SWIPE_TYPE_VERTICAL){
            animatorY = ValueAnimator.ofInt(layoutParamsFinal.topMargin, originalTopMargin);
            animatorY.setInterpolator(decelerateInterpolator);
            animatorY.setDuration(animTime);
            animatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    layoutParamsFinal.topMargin = (Integer) valueAnimator.getAnimatedValue();
                    v.setLayoutParams(layoutParamsFinal);
                }
            });
        }

        if(animatorX != null){
            animatorX.start();
        }
        if(animatorY != null){
            animatorY.start();
        }
        animatorR.start();
    }

    protected void doSwipe(boolean isSwipeIn){
        if(mLayoutView != null && mViewRemoveAnimatorListener != null && !mSwipeOption.getIsViewLocked()) {
            if(!mSwipeOption.getIsPutBackActive()) {
              blockTouch();
            }

            if (isSwipeIn) {
                if (getSwipeInMsgView() != null) {
                    getSwipeInMsgView().setVisibility(VISIBLE);
                }
            } else {
                if (getSwipeOutMsgView() != null) {
                    getSwipeOutMsgView().setVisibility(VISIBLE);
                }
            }

            DisplayMetrics displayMetrics = mLayoutView.getResources().getDisplayMetrics();
            ViewPropertyAnimator animator = mLayoutView.animate();

            float transX = displayMetrics.widthPixels;
            float transY = displayMetrics.heightPixels;
            switch (mSwipeType){
                case SwipePlaceHolderView.SWIPE_TYPE_DEFAULT:
                    if(isSwipeIn){
                        bindSwipeIn(getResolver());
                        animator.rotation(-mSwipeDecor.getSwipeRotationAngle());
                    }else{
                        bindSwipeOut(getResolver());
                        transX = -mLayoutView.getWidth();
                        animator.rotation(mSwipeDecor.getSwipeRotationAngle());
                    }
                    animator.translationX(transX).translationY(transY);
                    break;
                case SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL:
                    if(isSwipeIn){
                        bindSwipeIn(getResolver());
                    }else{
                        bindSwipeOut(getResolver());
                        transX = -mLayoutView.getWidth();
                    }
                    animator.translationX(transX);
                    break;
                case SwipePlaceHolderView.SWIPE_TYPE_VERTICAL:
                    if(isSwipeIn){
                        bindSwipeIn(getResolver());
                    }else{
                        bindSwipeOut(getResolver());
                        transY = -mLayoutView.getHeight();
                    }
                    animator.translationY(transY);
                    break;
            }

            animator.setDuration((long) (mSwipeDecor.getSwipeAnimTime() * 1.25))
                    .setInterpolator(new AccelerateInterpolator(mSwipeDecor.getSwipeAnimFactor()))
                    .setListener(mViewRemoveAnimatorListener)
                    .start();
        }
    }

    protected View getSwipeInMsgView() {
        return mSwipeInMsgView;
    }

    protected void setSwipeInMsgView(View swipeInMsgView) {
        mSwipeInMsgView = swipeInMsgView;
    }

    protected View getSwipeOutMsgView() {
        return mSwipeOutMsgView;
    }

    protected void setSwipeOutMsgView(View swipeOutMsgView) {
        mSwipeOutMsgView = swipeOutMsgView;
    }

    protected V getLayoutView() {
        return mLayoutView;
    }

    protected SwipeCallback getCallback() {
        return mCallback;
    }

    protected int getSwipeType() {
        return mSwipeType;
    }

    protected P getSwipeOption() {
        return mSwipeOption;
    }

    protected Q getSwipeDecor() {
        return mSwipeDecor;
    }

    protected Animator.AnimatorListener getViewRemoveAnimatorListener() {
        return mViewRemoveAnimatorListener;
    }

    protected Animator.AnimatorListener getViewRestoreAnimatorListener() {
        return mViewRestoreAnimatorListener;
    }

    protected Animator.AnimatorListener getViewPutBackAnimatorListener() {
        return mViewPutBackAnimatorListener;
    }

    public int getFinalLeftMargin() {
        return mFinalLeftMargin;
    }

    public void setFinalLeftMargin(int finalLeftMargin) {
        mFinalLeftMargin = finalLeftMargin;
    }

    public int getFinalTopMargin() {
        return mFinalTopMargin;
    }

    public void setFinalTopMargin(int finalTopMargin) {
        mFinalTopMargin = finalTopMargin;
    }

    protected void doUndoAnimation() {
        if (mSwipeOption.isUndoEnabled()) {

            final FrameLayout.LayoutParams layoutParamsFinal =
                    (FrameLayout.LayoutParams) getLayoutView().getLayoutParams();

            layoutParamsFinal.leftMargin = getFinalLeftMargin();
            layoutParamsFinal.topMargin = getFinalTopMargin();

            ValueAnimator animatorX = null;
            ValueAnimator animatorY = null;
            int animTime = mSwipeDecor.getSwipeAnimTime();
            DecelerateInterpolator decelerateInterpolator =
                    new DecelerateInterpolator(mSwipeDecor.getSwipeAnimFactor());
            ViewPropertyAnimator animatorR = getLayoutView().animate()
                    .rotation(0)
                    .setInterpolator(decelerateInterpolator)
                    .setDuration(animTime);

            if (mSwipeType == SwipePlaceHolderView.SWIPE_TYPE_DEFAULT
                    || mSwipeType == SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL) {
                animatorX = ValueAnimator.ofInt(layoutParamsFinal.leftMargin, mOriginalLeftMargin);
                animatorX.setInterpolator(decelerateInterpolator);
                animatorX.setDuration(animTime);
                animatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        layoutParamsFinal.leftMargin = (Integer) valueAnimator.getAnimatedValue();
                        getLayoutView().setLayoutParams(layoutParamsFinal);
                    }

                });
            }
            if (mSwipeType == SwipePlaceHolderView.SWIPE_TYPE_DEFAULT
                    || mSwipeType == SwipePlaceHolderView.SWIPE_TYPE_VERTICAL) {
                animatorY = ValueAnimator.ofInt(layoutParamsFinal.topMargin, mOriginalTopMargin);
                animatorY.setInterpolator(decelerateInterpolator);
                animatorY.setDuration(animTime);
                animatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        layoutParamsFinal.topMargin = (Integer) valueAnimator.getAnimatedValue();
                        getLayoutView().setLayoutParams(layoutParamsFinal);
                    }
                });
            }

            if (animatorX != null) {
                animatorX.start();
            }
            if (animatorY != null) {
                animatorY.start();
            }
            animatorR.start();
        }
    }

    protected abstract void bindSwipeView(V promptsView);

    protected abstract void bindSwipeIn(T resolver);

    protected abstract void bindSwipeOut(T resolver);

    protected abstract void bindSwipeInState();

    protected abstract void bindSwipeOutState();

    protected abstract void bindSwipeCancelState();

    protected abstract void bindSwipeHead(T resolver);

    protected interface SwipeCallback<T extends
            SwipeViewBinder<?,
                    ? extends FrameLayout,
                    ? extends SwipePlaceHolderView.SwipeOption,
                    ? extends SwipeDecor>> {
        void onRemoveView(T swipeViewBinder);
        void onResetView(T swipeViewBinder);
        void onAnimateView(float distXMoved, float distYMoved, float finalXDist, float finalYDist, T swipeViewBinder);
    }
}
