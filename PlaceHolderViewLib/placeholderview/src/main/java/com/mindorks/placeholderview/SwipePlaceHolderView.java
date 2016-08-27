package com.mindorks.placeholderview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by janisharali on 26/08/16.
 */
public class SwipePlaceHolderView extends FrameLayout implements
        SwipeViewBinder.SwipeCallback<SwipeViewBinder<Object, FrameLayout>>{

    public static final int DEFAULT_DISPLAY_VIEW_COUNT = 20;
    public static final int SWIPE_TYPE_DEFAULT = 1;
    public static final int SWIPE_TYPE_HORIZONTAL = 2;
    public static final int SWIPE_TYPE_VERTICAL = 3;

    private List<SwipeViewBinder<Object, FrameLayout>> mSwipeViewBinderList;
    private SwipeViewBuilder mSwipeViewBuilder;
    private LayoutInflater mLayoutInflater;
    private int mDisplayViewCount = DEFAULT_DISPLAY_VIEW_COUNT;
    private int mSwipeType = SWIPE_TYPE_DEFAULT;
    private boolean mIsReverse = false;
    private SwipeDecor mSwipeDecor;

    public SwipePlaceHolderView(Context context) {
        super(context);
        setupView(new ArrayList<SwipeViewBinder<Object, FrameLayout>>(), new SwipeViewBuilder(this));
    }

    public SwipePlaceHolderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(new ArrayList<SwipeViewBinder<Object, FrameLayout>>(), new SwipeViewBuilder(this));
    }

    public SwipePlaceHolderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView(new ArrayList<SwipeViewBinder<Object, FrameLayout>>(), new SwipeViewBuilder(this));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SwipePlaceHolderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupView(new ArrayList<SwipeViewBinder<Object, FrameLayout>>(), new SwipeViewBuilder(this));
    }

    private void setupView(List<SwipeViewBinder<Object, FrameLayout>> swipeViewBinderList,
                           SwipeViewBuilder swipeViewBuilder){
        mSwipeViewBinderList = swipeViewBinderList;
        mSwipeViewBuilder = swipeViewBuilder;
        mLayoutInflater =  LayoutInflater.from(getContext());
        mSwipeDecor = new SwipeDecor();
        setChildrenDrawingOrderEnabled(true);
    }


    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        if(mIsReverse) {
            return super.getChildDrawingOrder(childCount, i);
        }else{
            return super.getChildDrawingOrder(childCount, childCount - 1 - i);
        }
    }

    public SwipeViewBuilder getBuilder() {
        return mSwipeViewBuilder;
    }

    protected void setDisplayViewCount(int displayViewCount) {
        mDisplayViewCount = displayViewCount;
    }

    protected void setSwipeType(int swipeType) {
        mSwipeType = swipeType;
    }

    protected void setIsReverse(boolean isReverse) {
        mIsReverse = isReverse;
    }

    protected void setSwipeDecor(SwipeDecor swipeDecor) {
        if(swipeDecor != null) {
            mSwipeDecor = swipeDecor;
        }
    }

    public <T>SwipePlaceHolderView addView(T resolver){
        SwipeViewBinder<Object, FrameLayout> swipeViewBinder = new SwipeViewBinder<>((Object)resolver);
        mSwipeViewBinderList.add(swipeViewBinder);
        if(mSwipeViewBinderList.size() <= mDisplayViewCount){
            int position = mSwipeViewBinderList.indexOf(swipeViewBinder);
            View promptsView = mLayoutInflater.inflate(swipeViewBinder.getLayoutId(), null);

            FrameLayout frameLayout = new FrameLayout(getContext());
            frameLayout.setLayoutParams(getLayoutParamsWithSwipeDecor(position, mSwipeDecor));
            frameLayout.addView(promptsView);
            attachSwipeInfoViews(promptsView, frameLayout, swipeViewBinder, mSwipeDecor);
            addView(frameLayout);
            setRelativeScale(frameLayout, position, mSwipeDecor);
            swipeViewBinder.bindView(frameLayout, position, mSwipeType, this);
        }
        return this;
    }

    protected  <T>void addPendingView(SwipeViewBinder<T, FrameLayout> swipeViewBinder, int oldPosition){

        int position = mSwipeViewBinderList.indexOf(swipeViewBinder);
        View promptsView = mLayoutInflater.inflate(swipeViewBinder.getLayoutId(), null);

        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setLayoutParams(getLayoutParamsWithSwipeDecor(position, mSwipeDecor));
        frameLayout.addView(promptsView);
        attachSwipeInfoViews(promptsView, frameLayout, swipeViewBinder, mSwipeDecor);
        addView(frameLayout);
        setRelativeScale(frameLayout, position, mSwipeDecor);
        swipeViewBinder.bindView(frameLayout, oldPosition - 1, mSwipeType, this);
    }

    protected <V extends View, F extends FrameLayout, T extends SwipeViewBinder>void attachSwipeInfoViews(
            V view, F frame, T swipeViewBinder, SwipeDecor swipeDecor){

        if(swipeDecor.getSwipeInMsgLayoutId() != SwipeDecor.PRIMITIVE_NULL
                && swipeDecor.getSwipeOutMsgLayoutId() != SwipeDecor.PRIMITIVE_NULL){
            View swipeInMsgLayout = mLayoutInflater.inflate(swipeDecor.getSwipeInMsgLayoutId(), null);
            View swipeOutMsgLayout = mLayoutInflater.inflate(swipeDecor.getSwipeOutMsgLayoutId(), null);

            FrameLayout swipeInMsgView = new FrameLayout(getContext());
            FrameLayout swipeOutMsgView = new FrameLayout(getContext());

            FrameLayout.LayoutParams layoutParamsInMsg = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            FrameLayout.LayoutParams layoutParamsOutMsg = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            if(swipeDecor.isSwipeInMsgAttachCenters()){
                layoutParamsInMsg.gravity = Gravity.CENTER;
                layoutParamsOutMsg.gravity = Gravity.CENTER;
            }
            else if(swipeDecor.isSwipeInMsgAttachCorners()){
                layoutParamsInMsg.gravity = Gravity.RIGHT|Gravity.TOP;
                layoutParamsOutMsg.gravity = Gravity.LEFT|Gravity.TOP;
            }
            else if(swipeDecor.isSwipeInMsgAttachEnds()){
                layoutParamsInMsg.gravity = Gravity.RIGHT|Gravity.CENTER;
                layoutParamsOutMsg.gravity = Gravity.LEFT|Gravity.CENTER;
            }

            swipeInMsgLayout.setLayoutParams(layoutParamsInMsg);
            swipeOutMsgLayout.setLayoutParams(layoutParamsOutMsg);

            swipeInMsgView.addView(swipeInMsgLayout);
            swipeOutMsgView.addView(swipeOutMsgLayout);

            frame.addView(swipeInMsgView);
            frame.addView(swipeOutMsgView);

            swipeInMsgView.setVisibility(GONE);
            swipeOutMsgView.setVisibility(GONE);

            swipeViewBinder.setSwipeInMsgView(swipeInMsgView);
            swipeViewBinder.setSwipeOutMsgView(swipeOutMsgView);
        }
    }

    protected FrameLayout.LayoutParams getLayoutParamsWithSwipeDecor(int position, SwipeDecor decor){
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(decor.getPaddingLeft() * position, decor.getPaddingTop() * position, 0, 0);
        return layoutParams;
    }

    protected <V extends  FrameLayout>void getLayoutParamsWithSwipeDecor(V frame, int position, SwipeDecor decor){
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)frame.getLayoutParams();
        layoutParams.setMargins(decor.getPaddingLeft() * position, decor.getPaddingTop() * position, 0, 0);
        frame.setLayoutParams(layoutParams);
    }

    protected <V extends View, T extends SwipeDecor>void setRelativeScale(V view, int position,  T swipeDecor){
        view.setScaleX( 1 - position * swipeDecor.getRelativeScale());
        view.setScaleY(1 - position * swipeDecor.getRelativeScale());
    }

    @Override
    public void onRemoveView(SwipeViewBinder swipeViewBinder) {

        SwipeViewBinder<Object, FrameLayout> newSwipeViewBinder = null;
        int position = SwipeDecor.PRIMITIVE_NULL;

        if(mSwipeViewBinderList.size() > mDisplayViewCount){
            newSwipeViewBinder = mSwipeViewBinderList.get(mDisplayViewCount);
            position = mSwipeViewBinderList.indexOf(newSwipeViewBinder);
        }

        mSwipeViewBinderList.remove(swipeViewBinder);
        removeView(swipeViewBinder.getLayoutView());
        swipeViewBinder.unbind();

        if(newSwipeViewBinder != null && position != SwipeDecor.PRIMITIVE_NULL){
            addPendingView(newSwipeViewBinder, position);
            resetViewOrientation(position - 1, mSwipeDecor);
        }else{
            resetViewOrientation(mSwipeViewBinderList.size() - 1, mSwipeDecor);
        }
    }

    @Override
    public void onAnimateView(float distXMoved, float distYMoved, float finalXDist,
                              float finalYDist, SwipeViewBinder<Object, FrameLayout> swipeViewBinder) {

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

                SwipeViewBinder<Object, FrameLayout> swipeViewBinderBelow = mSwipeViewBinderList.get(i);
                float scaleDefault = 1 - i * mSwipeDecor.getRelativeScale();
                float scaleOfAboveViewDefault = 1 - (i - 1) * mSwipeDecor.getRelativeScale();
                float scale = ((scaleOfAboveViewDefault - scaleDefault) / finalDist) * distMoved + scaleDefault;
                swipeViewBinderBelow.getLayoutView().setScaleX(scale);
                swipeViewBinderBelow.getLayoutView().setScaleY(scale);

                FrameLayout.LayoutParams layoutParams =
                        (FrameLayout.LayoutParams) swipeViewBinderBelow.getLayoutView().getLayoutParams();
                float value = (-mSwipeDecor.getPaddingTop() / finalDist) * distMoved + mSwipeDecor.getPaddingTop() * i;
                layoutParams.topMargin = (int) value;

                value = (-mSwipeDecor.getPaddingLeft() / finalDist) * distMoved + mSwipeDecor.getPaddingLeft() * i;
                layoutParams.leftMargin = (int) value;

                swipeViewBinderBelow.getLayoutView().setLayoutParams(layoutParams);
            }
        }

        if((distXMovedAbs > mSwipeDecor.getSwipeDistToDisplayMsg() || distYMovedAbs > mSwipeDecor.getSwipeDistToDisplayMsg())
                && mSwipeDecor.getSwipeInMsgLayoutId() != SwipeDecor.PRIMITIVE_NULL
                && mSwipeDecor.getSwipeOutMsgLayoutId() != SwipeDecor.PRIMITIVE_NULL) {
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

            if(isSwipeIn){
                if(swipeViewBinder.getSwipeInMsgView() != null
                        && swipeViewBinder.getSwipeInMsgView().getVisibility() == GONE) {
                    swipeViewBinder.getSwipeInMsgView().setVisibility(VISIBLE);
                }
                if(swipeViewBinder.getSwipeOutMsgView() != null
                        && swipeViewBinder.getSwipeOutMsgView().getVisibility() == VISIBLE){
                    swipeViewBinder.getSwipeOutMsgView().setVisibility(GONE);
                }
            }

            else{
                if(swipeViewBinder.getSwipeOutMsgView() != null
                        && swipeViewBinder.getSwipeOutMsgView().getVisibility() == GONE) {
                    swipeViewBinder.getSwipeOutMsgView().setVisibility(VISIBLE);
                }
                if(swipeViewBinder.getSwipeInMsgView() != null
                        && swipeViewBinder.getSwipeInMsgView().getVisibility() == VISIBLE){
                    swipeViewBinder.getSwipeInMsgView().setVisibility(GONE);
                }
            }
        }
    }

    @Override
    public void onResetView(SwipeViewBinder swipeViewBinder) {
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
    }

    protected <T extends SwipeDecor>void resetViewOrientation(int lastPosition, T swipeDecor){
        if(swipeDecor.isAnimateScale()) {
            for (int i = 0; i <= lastPosition && mSwipeViewBinderList.get(i) != null; i++) {
                SwipeViewBinder<Object, FrameLayout> swipeViewBinder = mSwipeViewBinderList.get(i);
                setRelativeScale(swipeViewBinder.getLayoutView(), i, swipeDecor);
                getLayoutParamsWithSwipeDecor(swipeViewBinder.getLayoutView(), i, swipeDecor);
            }
        }
    }
}
