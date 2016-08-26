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
public class SwipePlaceHolderView extends FrameLayout implements SwipeViewBinder.SwipeCallback{

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

    private void setupView(List<SwipeViewBinder<Object, FrameLayout>> swipeViewBinderList, SwipeViewBuilder swipeViewBuilder){
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
            frameLayout.setLayoutParams(buildSwipeDecor(position, mSwipeDecor));
            frameLayout.addView(promptsView);
            addView(frameLayout);
            setRelativeScale(frameLayout, position, mSwipeDecor);

            swipeViewBinder.bindView(frameLayout, position, mSwipeType);
            swipeViewBinder.setSwipeCallback(this);
        }
        return this;
    }

    protected  <T>void addPendingView(SwipeViewBinder<T, FrameLayout> swipeViewBinder, int oldPosition){

        int position = mSwipeViewBinderList.indexOf(swipeViewBinder);
        View promptsView = mLayoutInflater.inflate(swipeViewBinder.getLayoutId(), null);

        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setLayoutParams(buildSwipeDecor(position, mSwipeDecor));
        frameLayout.addView(promptsView);
        addView(frameLayout);
        setRelativeScale(frameLayout, position, mSwipeDecor);

        swipeViewBinder.bindView(frameLayout, oldPosition - 1, mSwipeType);
        swipeViewBinder.setSwipeCallback(this);
    }

    protected FrameLayout.LayoutParams buildSwipeDecor(int position, SwipeDecor decor){

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(decor.getPaddingLeft() * position, decor.getPaddingTop() * position,
                decor.getPaddingRight() * position, decor.getPaddingBottom() * position);
        return layoutParams;
    }

    protected <V extends  FrameLayout>void rebuildSwipeDecor(V frame, int position, SwipeDecor decor){
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)frame.getLayoutParams();
        layoutParams.setMargins(decor.getPaddingLeft() * position, decor.getPaddingTop() * position,
                decor.getPaddingRight() * position, decor.getPaddingBottom() * position);
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

    protected <T extends SwipeDecor>void resetViewOrientation(int lastPosition,  T swipeDecor){
        if(swipeDecor.isAnimateScale()) {
            for (int i = 0; i <= lastPosition && mSwipeViewBinderList.get(i) != null; i++) {
                SwipeViewBinder<Object, FrameLayout> swipeViewBinder = mSwipeViewBinderList.get(i);
                setRelativeScale(swipeViewBinder.getLayoutView(), i, swipeDecor);
                rebuildSwipeDecor(swipeViewBinder.getLayoutView(), i, swipeDecor);
            }
        }
    }
}
