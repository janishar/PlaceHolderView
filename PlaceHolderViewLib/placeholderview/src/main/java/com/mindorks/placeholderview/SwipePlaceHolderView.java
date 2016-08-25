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
    public static final int DEFAULT_SPACING_IN_DP = 10;
    public static final int SWIPE_TYPE_DEFAULT = 1;
    public static final int SWIPE_TYPE_HORIZONTAL = 2;
    public static final int SWIPE_TYPE_VERTICAL = 3;

    private List<SwipeViewBinder<Object,View>> mSwipeViewBinderList;
    private SwipeViewBuilder mSwipeViewBuilder;
    private LayoutInflater mLayoutInflater;
    private int mDisplayViewCount = DEFAULT_DISPLAY_VIEW_COUNT;
    private int mSwipeType = SWIPE_TYPE_DEFAULT;
    private int mSpacing = DEFAULT_SPACING_IN_DP;

    public SwipePlaceHolderView(Context context) {
        super(context);
        setupView(new ArrayList<SwipeViewBinder<Object, View>>(), new SwipeViewBuilder(this));
    }

    public SwipePlaceHolderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(new ArrayList<SwipeViewBinder<Object, View>>(), new SwipeViewBuilder(this));
    }

    public SwipePlaceHolderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView(new ArrayList<SwipeViewBinder<Object, View>>(), new SwipeViewBuilder(this));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SwipePlaceHolderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupView(new ArrayList<SwipeViewBinder<Object, View>>(), new SwipeViewBuilder(this));
    }

    private void setupView(List<SwipeViewBinder<Object, View>> swipeViewBinderList, SwipeViewBuilder swipeViewBuilder){
        mSwipeViewBinderList = swipeViewBinderList;
        mSwipeViewBuilder = swipeViewBuilder;
        mLayoutInflater =  LayoutInflater.from(getContext());
    }

    public SwipeViewBuilder getBuilder() {
        return mSwipeViewBuilder;
    }

    protected void setDisplayViewCount(int displayViewCount) {
        mDisplayViewCount = displayViewCount;
    }

    public void setSpacing(int spacing) {
        mSpacing = spacing;
    }

    public void setSwipeType(int swipeType) {
        mSwipeType = swipeType;
    }

    public <T>SwipePlaceHolderView addView(T resolver){
        SwipeViewBinder<Object,View> swipeViewBinder = new SwipeViewBinder<>((Object)resolver);
        View promptsView = mLayoutInflater.inflate(swipeViewBinder.getLayoutId(), null);
        mSwipeViewBinderList.add(swipeViewBinder);
        if(mSwipeViewBinderList.size() <= mDisplayViewCount){

            FrameLayout frameLayout = new FrameLayout(getContext());

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int spacing = mSpacing *  mSwipeViewBinderList.indexOf(swipeViewBinder);
            layoutParams.setMargins(spacing, spacing, 0, 0);
            layoutParams.gravity = Gravity.CENTER;

            frameLayout.setLayoutParams(layoutParams);
            frameLayout.addView(promptsView);
            addView(frameLayout);
            swipeViewBinder.bindView(frameLayout, mSwipeViewBinderList.indexOf(swipeViewBinder));
            swipeViewBinder.setSwipeCallback(this);
        }
        return this;
    }

    @Override
    public void onRemoveView(SwipeViewBinder swipeViewBinder) {
        mSwipeViewBinderList.remove(swipeViewBinder);
        removeView(swipeViewBinder.getLayoutView());
        swipeViewBinder.unbind();
    }
}
