package com.mindorks.placeholderview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.mindorks.placeholderview.listeners.ItemRemovedListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by janisharali on 26/08/16.
 */
public class SwipePlaceHolderView extends FrameLayout implements
        SwipeViewBinder.SwipeCallback<SwipeViewBinder<Object, SwipePlaceHolderView.FrameView>>{

    public static final int DEFAULT_DISPLAY_VIEW_COUNT = 20;
    public static final int SWIPE_TYPE_DEFAULT = 1;
    public static final int SWIPE_TYPE_HORIZONTAL = 2;
    public static final int SWIPE_TYPE_VERTICAL = 3;

    private List<SwipeViewBinder<Object, FrameView>> mSwipeViewBinderList;
    private SwipeViewBuilder mSwipeViewBuilder;
    private LayoutInflater mLayoutInflater;
    private int mDisplayViewCount = DEFAULT_DISPLAY_VIEW_COUNT;
    private int mSwipeType = SWIPE_TYPE_DEFAULT;
    private boolean mIsReverse = false;
    private SwipeDecor mSwipeDecor;
    private SwipeOption mSwipeOption;
    private boolean mIsBtnSwipeDone = true;
    private boolean mIsUndoEnabled;
    private Object mRestoreResolverOnUndo;
    private int mRestoreResolverPosition;
    private ItemRemovedListener mItemRemovedListener;

    /**
     *
     * @param context
     */
    public SwipePlaceHolderView(Context context) {
        super(context);
        setupView(new ArrayList<SwipeViewBinder<Object, FrameView>>(), new SwipeViewBuilder(this));
    }

    /**
     *
     * @param context
     * @param attrs
     */
    public SwipePlaceHolderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(new ArrayList<SwipeViewBinder<Object, FrameView>>(), new SwipeViewBuilder(this));
    }

    /**
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public SwipePlaceHolderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView(new ArrayList<SwipeViewBinder<Object, FrameView>>(), new SwipeViewBuilder(this));
    }

    /**
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SwipePlaceHolderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupView(new ArrayList<SwipeViewBinder<Object, FrameView>>(), new SwipeViewBuilder(this));
    }

    /**
     *
     * @param swipeViewBinderList
     * @param swipeViewBuilder
     */
    private void setupView(List<SwipeViewBinder<Object, FrameView>> swipeViewBinderList,
                           SwipeViewBuilder swipeViewBuilder){
        mSwipeViewBinderList = swipeViewBinderList;
        mSwipeViewBuilder = swipeViewBuilder;
        mLayoutInflater =  LayoutInflater.from(getContext());
        mSwipeDecor = new SwipeDecor();
        mSwipeOption = new SwipeOption();
        setChildrenDrawingOrderEnabled(true);
    }

    /**
     *
     * @param childCount
     * @param i
     * @return
     */
    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        if(mIsReverse) {
            return super.getChildDrawingOrder(childCount, i);
        }else{
            return super.getChildDrawingOrder(childCount, childCount - 1 - i);
        }
    }

    /**
     *
     * @return
     */
    public SwipeViewBuilder getBuilder() {
        return mSwipeViewBuilder;
    }

    /**
     *
     * @param displayViewCount
     */
    protected void setDisplayViewCount(int displayViewCount) {
        mDisplayViewCount = displayViewCount;
    }

    /**
     *
     * @param swipeType
     */
    protected void setSwipeType(int swipeType) {
        mSwipeType = swipeType;
    }

    /**
     *
     * @param isReverse
     */
    protected void setIsReverse(boolean isReverse) {
        mIsReverse = isReverse;
    }

    /**
     *
     * @param swipeDecor
     */
    protected void setSwipeDecor(SwipeDecor swipeDecor) {
        if(swipeDecor != null) {
            mSwipeDecor = swipeDecor;
        }
    }

    /**
     *
     * @param factor
     */
    protected void setWidthSwipeDistFactor(float factor) {
        mSwipeOption.setWidthSwipeDistFactor(factor);
    }

    /**
     *
     * @param factor
     */
    protected void setHeightSwipeDistFactor(float factor) {
        mSwipeOption.setHeightSwipeDistFactor(factor);
    }

    /**
     *
     * @param resolver
     * @param <T>
     * @return
     */
    public <T>SwipePlaceHolderView addView(T resolver){
        SwipeViewBinder<Object, FrameView> swipeViewBinder = new SwipeViewBinder<>((Object)resolver);
        mSwipeViewBinderList.add(swipeViewBinder);
        if(mSwipeViewBinderList.size() <= mDisplayViewCount){
            int position = mSwipeViewBinderList.indexOf(swipeViewBinder);
            FrameView frameView = new FrameView(getContext());
            frameView.setLayoutParams(getLayoutParamsWithSwipeDecor(position, mSwipeDecor));
            mLayoutInflater.inflate(swipeViewBinder.getLayoutId(), frameView, true);
            attachSwipeInfoViews(frameView, swipeViewBinder, mSwipeDecor);
            addView(frameView);
            setRelativeScale(frameView, position, mSwipeDecor);
            swipeViewBinder.bindView(frameView, position, mSwipeType, mSwipeDecor, mSwipeOption, this);

            if(mSwipeViewBinderList.indexOf(swipeViewBinder) == 0){
                swipeViewBinder.setOnTouch();
            }
        }
        return this;
    }

    private <T>void addView(T resolver, int position){
        if(position >= 0 && position <= mDisplayViewCount){
            SwipeViewBinder<Object, FrameView> swipeViewBinder = new SwipeViewBinder<>((Object)resolver);
            mSwipeViewBinderList.add(position, swipeViewBinder);
            int binderPosition = mSwipeViewBinderList.indexOf(swipeViewBinder);
            FrameView frameView = new FrameView(getContext());
            frameView.setLayoutParams(getLayoutParamsWithSwipeDecor(binderPosition, mSwipeDecor));
            mLayoutInflater.inflate(swipeViewBinder.getLayoutId(), frameView, true);
            attachSwipeInfoViews(frameView, swipeViewBinder, mSwipeDecor);
            addView(frameView, position);
            setRelativeScale(frameView, binderPosition, mSwipeDecor);
            swipeViewBinder.bindView(frameView, binderPosition, mSwipeType, mSwipeDecor, mSwipeOption, this);

            if(mSwipeViewBinderList.indexOf(swipeViewBinder) == 0){
                swipeViewBinder.setOnTouch();
            }
        }
    }

    /**
     *
     * @param swipeViewBinder
     * @param <T>
     */
    protected  <T>void addPendingView(SwipeViewBinder<T, FrameView> swipeViewBinder){
        int position = mSwipeViewBinderList.indexOf(swipeViewBinder);
        FrameView frameView = new FrameView(getContext());
        frameView.setLayoutParams(getLayoutParamsWithSwipeDecor(position, mSwipeDecor));
        mLayoutInflater.inflate(swipeViewBinder.getLayoutId(), frameView, true);
        attachSwipeInfoViews(frameView, swipeViewBinder, mSwipeDecor);
        addView(frameView);
        setRelativeScale(frameView, position, mSwipeDecor);
        swipeViewBinder.bindView(frameView, position, mSwipeType, mSwipeDecor, mSwipeOption, this);
    }

    /**
     *
     * @param frame
     * @param swipeViewBinder
     * @param swipeDecor
     * @param <V>
     * @param <T>
     */
    protected <V extends FrameLayout, T extends SwipeViewBinder>void attachSwipeInfoViews(V frame, T swipeViewBinder, SwipeDecor swipeDecor){

        if(swipeDecor.getSwipeInMsgLayoutId() != SwipeDecor.PRIMITIVE_NULL
                && swipeDecor.getSwipeOutMsgLayoutId() != SwipeDecor.PRIMITIVE_NULL){

            FrameLayout swipeInMsgView = new FrameLayout(getContext());
            FrameLayout swipeOutMsgView = new FrameLayout(getContext());

            FrameLayout.LayoutParams layoutParamsInMsg = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            FrameLayout.LayoutParams layoutParamsOutMsg = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            layoutParamsInMsg.gravity = mSwipeDecor.getSwipeInMsgGravity();
            layoutParamsOutMsg.gravity = mSwipeDecor.getSwipeOutMsgGravity();

            swipeInMsgView.setLayoutParams(layoutParamsInMsg);
            swipeOutMsgView.setLayoutParams(layoutParamsOutMsg);

            mLayoutInflater.inflate(swipeDecor.getSwipeInMsgLayoutId(), swipeInMsgView, true);
            mLayoutInflater.inflate(swipeDecor.getSwipeOutMsgLayoutId(), swipeOutMsgView, true);

            frame.addView(swipeInMsgView);
            frame.addView(swipeOutMsgView);

            swipeInMsgView.setVisibility(GONE);
            swipeOutMsgView.setVisibility(GONE);

            swipeViewBinder.setSwipeInMsgView(swipeInMsgView);
            swipeViewBinder.setSwipeOutMsgView(swipeOutMsgView);
        }
    }

    /**
     *
     * @param position
     * @param decor
     * @return
     */
    protected FrameLayout.LayoutParams getLayoutParamsWithSwipeDecor(int position, SwipeDecor decor){

        if(decor.getViewHeight() != 0 && decor.getViewWidth() != 0) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(decor.getViewWidth(), decor.getViewHeight());
            layoutParams.gravity = decor.getViewGravity();
            layoutParams.setMargins(decor.getMarginLeft() + decor.getPaddingLeft() * position,
                    decor.getMarginTop() + decor.getPaddingTop() * position, 0, 0);
            return layoutParams;
        }else{
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = decor.getViewGravity();
            layoutParams.setMargins(decor.getMarginLeft() + decor.getPaddingLeft() * position,
                    decor.getMarginTop() + decor.getPaddingTop() * position, 0, 0);
            return layoutParams;
        }
    }

    /**
     *
     * @param frame
     * @param position
     * @param decor
     * @param <V>
     */
    protected <V extends  FrameLayout>void setLayoutParamsWithSwipeDecor(V frame, int position, SwipeDecor decor){
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)frame.getLayoutParams();
        layoutParams.setMargins(decor.getMarginLeft() + decor.getPaddingLeft() * position,
                decor.getMarginTop() + decor.getPaddingTop() * position, 0, 0);
        frame.setLayoutParams(layoutParams);
    }

    /**
     *
     * @param view
     * @param position
     * @param swipeDecor
     * @param <V>
     * @param <T>
     */
    protected <V extends View, T extends SwipeDecor>void setRelativeScale(V view, int position,  T swipeDecor){
        view.setScaleX( 1 - position * swipeDecor.getRelativeScale());
        view.setScaleY(1 - position * swipeDecor.getRelativeScale());
    }

    /**
     *
     * @param resolver
     * @param isSwipeIn
     */
    public void doSwipe(Object resolver, boolean isSwipeIn){
        if(mIsBtnSwipeDone){
            mIsBtnSwipeDone = false;
            SwipeViewBinder swipeViewBinder = null;
            for(SwipeViewBinder viewBinder : mSwipeViewBinderList){
                if(viewBinder.getResolver() == resolver){
                    swipeViewBinder = viewBinder;
                    break;
                }
            }

            if(swipeViewBinder != null){
                swipeViewBinder.doSwipe(isSwipeIn);
            }
            new CountDownTimer((int)(2.25 * mSwipeDecor.getSwipeAnimTime()), mSwipeDecor.getSwipeAnimTime()) {
                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() {
                    mIsBtnSwipeDone = true;
                }
            }.start();
        }
    }

    /**
     *
     * @param isSwipeIn
     */
    public void doSwipe(boolean isSwipeIn){
        if(mIsBtnSwipeDone) {
            mIsBtnSwipeDone = false;
            if (mSwipeViewBinderList.size() > 0) {
                mSwipeViewBinderList.get(0).doSwipe(isSwipeIn);
            }

            new CountDownTimer((int)(2.25 * mSwipeDecor.getSwipeAnimTime()), mSwipeDecor.getSwipeAnimTime()) {
                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() {
                    mIsBtnSwipeDone = true;
                }
            }.start();
        }
    }

    public void lockViews(){
        mSwipeOption.setIsViewLocked(true);
    }

    public void unlockViews(){
        mSwipeOption.setIsViewLocked(false);
    }

    public void activatePutBack(){
        mSwipeOption.setIsPutBackActive(true);
    }

    public void deactivatePutBack(){
        mSwipeOption.setIsPutBackActive(false);
    }

    public void disableTouchSwipe() {
        mSwipeOption.setIsTouchSwipeLocked(true);
    }

    public void enableTouchSwipe() {
        mSwipeOption.setIsTouchSwipeLocked(false);
    }

    protected void setIsUndoEnabled(boolean isUndoEnabled) {
        this.mIsUndoEnabled = isUndoEnabled;
    }

    /**
     *
     * @param swipeViewBinder
     */
    @Override
    public void onRemoveView(SwipeViewBinder<Object, FrameView> swipeViewBinder) {
        SwipeViewBinder<Object, FrameView> newSwipeViewBinder = null;
        int position = SwipeDecor.PRIMITIVE_NULL;

        if(mSwipeViewBinderList.size() > mDisplayViewCount){
            newSwipeViewBinder = mSwipeViewBinderList.get(mDisplayViewCount);
            position = mSwipeViewBinderList.indexOf(newSwipeViewBinder);
        }

        mSwipeViewBinderList.remove(swipeViewBinder);
        removeView(swipeViewBinder.getLayoutView());
        if(newSwipeViewBinder != null && position != SwipeDecor.PRIMITIVE_NULL){
            addPendingView(newSwipeViewBinder);
            position -= 1;
        }else{
            position = mSwipeViewBinderList.size() - 1;
        }
        resetViewOrientation(position, mSwipeDecor);

        if(mSwipeViewBinderList.size() > 0){
            mSwipeViewBinderList.get(0).setOnTouch();
        }
        if(mIsUndoEnabled) {
            mRestoreResolverOnUndo = swipeViewBinder.getResolver();
            mRestoreResolverPosition = position;
        }
        swipeViewBinder.unbind();
        if(mItemRemovedListener != null){
            mItemRemovedListener.onItemRemoved(mSwipeViewBinderList.size());
        }
    }

    /**
     *
     * @param distXMoved
     * @param distYMoved
     * @param finalXDist
     * @param finalYDist
     * @param swipeViewBinder
     */
    @Override
    public void onAnimateView(float distXMoved, float distYMoved, float finalXDist,
                              float finalYDist, SwipeViewBinder<Object, FrameView> swipeViewBinder) {

        float distXMovedAbs = distXMoved > 0 ? distXMoved : -distXMoved;
        float distYMovedAbs = distYMoved > 0 ? distYMoved : -distYMoved;

        if(mSwipeDecor.isAnimateScale() && mSwipeViewBinderList.contains(swipeViewBinder)
                && distXMovedAbs <= finalXDist && distYMovedAbs <= finalYDist){
            int count;
            float distMoved;
            float finalDist;
            if(distXMovedAbs > distYMovedAbs){
                distMoved = distXMovedAbs;
                finalDist = finalXDist;
            }else{
                distMoved = distYMovedAbs;
                finalDist = finalYDist;
            }

            if(mSwipeViewBinderList.size() > mDisplayViewCount){
                count = mDisplayViewCount;
            }else{
                count  = mSwipeViewBinderList.size();
            }

            for(int i = mSwipeViewBinderList.indexOf(swipeViewBinder) +  1; i < count; i++){

                SwipeViewBinder<Object, FrameView> swipeViewBinderBelow = mSwipeViewBinderList.get(i);
                float scaleDefault = 1 - i * mSwipeDecor.getRelativeScale();
                float scaleOfAboveViewDefault = 1 - (i - 1) * mSwipeDecor.getRelativeScale();
                float scale = ((scaleOfAboveViewDefault - scaleDefault) / finalDist) * distMoved + scaleDefault;
                swipeViewBinderBelow.getLayoutView().setScaleX(scale);
                swipeViewBinderBelow.getLayoutView().setScaleY(scale);

                FrameLayout.LayoutParams layoutParams =
                        (FrameLayout.LayoutParams) swipeViewBinderBelow.getLayoutView().getLayoutParams();
                float value = (-mSwipeDecor.getPaddingTop() / finalDist) * distMoved
                        + (mSwipeDecor.getMarginTop()+ mSwipeDecor.getPaddingTop() * i);
                layoutParams.topMargin = (int) value;

                value = (-mSwipeDecor.getPaddingLeft() / finalDist) * distMoved
                        + (mSwipeDecor.getMarginLeft() + mSwipeDecor.getPaddingLeft() * i);
                layoutParams.leftMargin = (int) value;

                swipeViewBinderBelow.getLayoutView().setLayoutParams(layoutParams);
            }

            float angleMax = 0;
            if(distXMoved > 0 && distYMoved > 0){
                angleMax = mSwipeDecor.getSwipeRotationAngle();
            }
            else if(distXMoved > 0 && distYMoved < 0){
                angleMax = -mSwipeDecor.getSwipeRotationAngle();
            }
            else if(distXMoved < 0 && distYMoved > 0){
                angleMax = -mSwipeDecor.getSwipeRotationAngle();
            }
            else if(distXMoved < 0 && distYMoved < 0){
                angleMax = mSwipeDecor.getSwipeRotationAngle();
            }

            float angle = angleMax / finalDist * distMoved;
            swipeViewBinder.getLayoutView().setRotation(angle);
        }

        if((distXMovedAbs > mSwipeDecor.getSwipeDistToDisplayMsg()
                || distYMovedAbs > mSwipeDecor.getSwipeDistToDisplayMsg())){

            boolean isSwipeIn = false;
            if (distXMoved > 0) {
                isSwipeIn = true;
            } else if (distXMoved < 0) {
                isSwipeIn = false;
            } else {
                if (distYMoved > 0) {
                    isSwipeIn = true;
                } else if (distYMoved < 0) {
                    isSwipeIn = false;
                }
            }

            if (isSwipeIn) {
                swipeViewBinder.bindSwipeInState();
            } else {
                swipeViewBinder.bindSwipeOutState();
            }

            if (mSwipeDecor.getSwipeInMsgLayoutId() != SwipeDecor.PRIMITIVE_NULL
                    && mSwipeDecor.getSwipeOutMsgLayoutId() != SwipeDecor.PRIMITIVE_NULL) {

                if (isSwipeIn) {
                    if (swipeViewBinder.getSwipeInMsgView() != null
                            && swipeViewBinder.getSwipeInMsgView().getVisibility() == GONE) {
                        swipeViewBinder.getSwipeInMsgView().setVisibility(VISIBLE);
                    }
                    if (swipeViewBinder.getSwipeOutMsgView() != null
                            && swipeViewBinder.getSwipeOutMsgView().getVisibility() == VISIBLE) {
                        swipeViewBinder.getSwipeOutMsgView().setVisibility(GONE);
                    }
                } else {
                    if (swipeViewBinder.getSwipeOutMsgView() != null
                            && swipeViewBinder.getSwipeOutMsgView().getVisibility() == GONE) {
                        swipeViewBinder.getSwipeOutMsgView().setVisibility(VISIBLE);
                    }
                    if (swipeViewBinder.getSwipeInMsgView() != null
                            && swipeViewBinder.getSwipeInMsgView().getVisibility() == VISIBLE) {
                        swipeViewBinder.getSwipeInMsgView().setVisibility(GONE);
                    }
                }
            }
        }
    }

    /**
     *
     * @param swipeViewBinder
     */
    @Override
    public void onResetView(SwipeViewBinder<Object, FrameView> swipeViewBinder) {
        if(mSwipeViewBinderList.size() > mDisplayViewCount){
            resetViewOrientation(mDisplayViewCount - 1, mSwipeDecor);
        }else{
            resetViewOrientation(mSwipeViewBinderList.size() - 1, mSwipeDecor);
        }

        if(mSwipeDecor.getSwipeInMsgLayoutId() != SwipeDecor.PRIMITIVE_NULL
                && mSwipeDecor.getSwipeOutMsgLayoutId() != SwipeDecor.PRIMITIVE_NULL) {
            if (swipeViewBinder.getSwipeInMsgView() != null
                    && swipeViewBinder.getSwipeInMsgView().getVisibility() == VISIBLE) {
                swipeViewBinder.getSwipeInMsgView().setVisibility(GONE);
            }

            if (swipeViewBinder.getSwipeOutMsgView() != null
                    && swipeViewBinder.getSwipeOutMsgView().getVisibility() == VISIBLE) {
                swipeViewBinder.getSwipeOutMsgView().setVisibility(GONE);
            }
        }
        swipeViewBinder.getLayoutView().setRotation(0);
        swipeViewBinder.bindSwipeCancelState();
        swipeViewBinder.getLayoutView().reset();
    }

    /**
     *
     * @param lastPosition
     * @param swipeDecor
     * @param <T>
     */
    protected <T extends SwipeDecor>void resetViewOrientation(int lastPosition, T swipeDecor){
        if(swipeDecor.isAnimateScale() && lastPosition >= 0) {
            for (int i = 0; i <= lastPosition; i++) {
                if (mSwipeViewBinderList.get(i) != null) {
                    SwipeViewBinder<Object, FrameView> swipeViewBinder = mSwipeViewBinderList.get(i);
                    setRelativeScale(swipeViewBinder.getLayoutView(), i, swipeDecor);
                    setLayoutParamsWithSwipeDecor(swipeViewBinder.getLayoutView(), i, swipeDecor);
                }
            }
        }
    }

    public void undoLastSwipe(){
        if(mIsUndoEnabled && mRestoreResolverOnUndo != null){
            if(mRestoreResolverPosition >= 0 && mRestoreResolverPosition >= mDisplayViewCount - 1){
                mSwipeViewBinderList.remove(mRestoreResolverPosition);
                removeViewAt(mRestoreResolverPosition);
            }
            addView(mRestoreResolverOnUndo, 0);
            mRestoreResolverOnUndo = null;
            if(mSwipeViewBinderList.size() > 1){
                mSwipeViewBinderList.get(1).blockTouch();
            }
            if(mRestoreResolverPosition >= 0 && mRestoreResolverPosition >= mDisplayViewCount - 1) {
                resetViewOrientation(mRestoreResolverPosition, mSwipeDecor);
            }else{
                resetViewOrientation(mRestoreResolverPosition + 1, mSwipeDecor);
            }
        }
    }

    public void addItemRemoveListener(ItemRemovedListener listener){
        mItemRemovedListener = listener;
    }

    /**
     *
     * @param view
     */
    @Deprecated
    @Override
    public void removeView(View view) {
        super.removeView(view);
    }

    /**
     *
     * @param view
     */
    @Deprecated
    @Override
    public void removeViewInLayout(View view) {
        super.removeViewInLayout(view);
    }

    /**
     *
     * @param start
     * @param count
     */
    @Deprecated
    @Override
    public void removeViewsInLayout(int start, int count) {
        super.removeViewsInLayout(start, count);
    }

    /**
     *
     * @param index
     */
    @Deprecated
    @Override
    public void removeViewAt(int index) {
        super.removeViewAt(index);
    }

    /**
     *
     * @param start
     * @param count
     */
    @Deprecated
    @Override
    public void removeViews(int start, int count) {
        super.removeViews(start, count);
    }

    /**
     *
     */
    @Override
    public void removeAllViews() {
        Iterator<SwipeViewBinder<Object, FrameView>> iterator = mSwipeViewBinderList.iterator();
        while (iterator.hasNext()){
            SwipeViewBinder<Object, FrameView> swipeViewBinder = iterator.next();
            if(swipeViewBinder != null){
                swipeViewBinder.unbind();
            }
            iterator.remove();
        }
        mRestoreResolverOnUndo = null;
        super.removeAllViews();
    }

    /**
     *
     */
    @Deprecated
    @Override
    public void removeAllViewsInLayout() {
        super.removeAllViewsInLayout();
    }

    /**
     *
     * @param child
     * @param animate
     */
    @Deprecated
    @Override
    protected void removeDetachedView(View child, boolean animate) {
        super.removeDetachedView(child, animate);
    }

    /**
     *
     * @param child
     */
    @Deprecated
    @Override
    public void addView(View child) {
        super.addView(child);
    }

    /**
     *
     * @param child
     * @param index
     */
    @Deprecated
    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
    }

    /**
     *
     * @param child
     * @param width
     * @param height
     */
    @Deprecated
    @Override
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
    }

    /**
     *
     * @param child
     * @param params
     */
    @Deprecated
    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
    }

    /**
     *
     * @param child
     * @param index
     * @param params
     */
    @Deprecated
    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
    }

    /**
     *
     * @param child
     * @param index
     * @param params
     * @return
     */
    @Deprecated
    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params) {
        return super.addViewInLayout(child, index, params);
    }

    /**
     *
     * @param child
     * @param index
     * @param params
     * @param preventRequestLayout
     * @return
     */
    @Deprecated
    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params, boolean preventRequestLayout) {
        return super.addViewInLayout(child, index, params, preventRequestLayout);
    }

    /**
     * Frame layout custom view to control the touch event propagation
     */
    protected class FrameView extends FrameLayout{

        private int mTouchSlop;
        private boolean mIsBeingDragged = false;
        private int mLastMotionY;
        private int mLastMotionX;

        /**
         *
         * @param context
         */
        public FrameView(Context context) {
            super(context);
            ViewConfiguration vc = ViewConfiguration.get(getContext());
            mTouchSlop = vc.getScaledTouchSlop();
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            final int action = ev.getAction();
            if ((action == MotionEvent.ACTION_MOVE) && (mIsBeingDragged)) {
                return true;
            }

            switch (action & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_MOVE: {
                    final int y = (int) ev.getRawY();
                    final int x = (int) ev.getRawX();
                    final int yDiff = Math.abs(y - mLastMotionY);
                    final int xDiff = Math.abs(x - mLastMotionX);
                    if (yDiff > mTouchSlop || xDiff > mTouchSlop) {
                        mIsBeingDragged = true;
                        mLastMotionY = y;
                        mLastMotionX = x;
                    }
                    break;
                }

                case MotionEvent.ACTION_DOWN: {
                    mLastMotionY = (int) ev.getRawY();
                    mLastMotionX = (int) ev.getRawX();
                    break;
                }

                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    mIsBeingDragged = false;
                    break;
            }

            return mIsBeingDragged;
        }

        public void reset() {
            mIsBeingDragged = false;
        }
    }

    protected class SwipeOption{
        private float mWidthSwipeDistFactor = 3f;
        private float mHeightSwipeDistFactor = 3f;
        private AtomicBoolean mIsViewLocked;
        private AtomicBoolean mIsPutBackActive;
        private AtomicBoolean mIsViewToRestoreOnLock;
        private AtomicBoolean mIsViewToRestoreOnTouchLock;
        private AtomicBoolean mIsTouchSwipeLocked;

        public SwipeOption() {
            mIsViewLocked = new AtomicBoolean(false);
            mIsPutBackActive = new AtomicBoolean(false);
            mIsViewToRestoreOnLock = new AtomicBoolean(true);
            mIsViewToRestoreOnTouchLock = new AtomicBoolean(true);
            mIsTouchSwipeLocked = new AtomicBoolean(false);
        }

        protected boolean getIsViewLocked() {
            return mIsViewLocked.get();
        }

        protected void setIsViewLocked(boolean isViewLocked) {
            this.mIsViewToRestoreOnLock.set(true);
            this.mIsViewLocked.set(isViewLocked);
        }

        protected boolean getIsPutBackActive() {
            return mIsPutBackActive.get();
        }

        protected void setIsPutBackActive(boolean isPutBackActive) {
            this.mIsPutBackActive.set(isPutBackActive);
        }

        protected boolean getIsViewToRestoredOnLock() {
            return mIsViewToRestoreOnLock.get();
        }

        protected void setIsViewToRestoredOnLock(boolean isViewToRestoredOnLock) {
            this.mIsViewToRestoreOnLock.set(isViewToRestoredOnLock);
        }

        protected boolean getIsViewToRestoreOnTouchLock() {
            return mIsViewToRestoreOnTouchLock.get();
        }

        protected void setIsViewToRestoreOnTouchLock(boolean isViewToRestoreOnTouchLock) {
            this.mIsViewToRestoreOnTouchLock.set(isViewToRestoreOnTouchLock);
        }

        protected boolean getIsTouchSwipeLocked(){
            return mIsTouchSwipeLocked.get();
        }

        protected void setIsTouchSwipeLocked(boolean locked){
            this.mIsViewToRestoreOnTouchLock.set(true);
            mIsTouchSwipeLocked.set(locked);
        }

        public float getWidthSwipeDistFactor() {
            return mWidthSwipeDistFactor;
        }

        public void setWidthSwipeDistFactor(float widthSwipeDistFactor) {
            this.mWidthSwipeDistFactor = widthSwipeDistFactor;
        }

        public float getHeightSwipeDistFactor() {
            return mHeightSwipeDistFactor;
        }

        public void setHeightSwipeDistFactor(float heightSwipeDistFactor) {
            this.mHeightSwipeDistFactor = heightSwipeDistFactor;
        }
    }
}
