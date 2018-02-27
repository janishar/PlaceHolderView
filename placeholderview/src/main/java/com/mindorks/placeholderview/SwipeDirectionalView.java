package com.mindorks.placeholderview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import java.util.ArrayList;

/**
 * Created by janisharali on 09/08/17.
 */

public class SwipeDirectionalView extends SwipePlaceHolderView {

    private ArrayList<SwipeDirectionalViewBinder
            <Object, FrameView, SwipeDirectionalOption, SwipeDecor>> mSwipeViewBinderList;

    public SwipeDirectionalView(Context context) {
        super(context);
        mSwipeViewBinderList = new ArrayList<>();
        setupView(mSwipeViewBinderList,
                new SwipeDirectionalViewBuilder<>(this),
                new SwipeDirectionalOption(),
                new SwipeDecor());
    }

    public SwipeDirectionalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSwipeViewBinderList = new ArrayList<>();
        setupView(mSwipeViewBinderList,
                new SwipeDirectionalViewBuilder<>(this),
                new SwipeDirectionalOption(),
                new SwipeDecor());
    }

    public SwipeDirectionalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSwipeViewBinderList = new ArrayList<>();
        setupView(mSwipeViewBinderList,
                new SwipeDirectionalViewBuilder<>(this),
                new SwipeDirectionalOption(),
                new SwipeDecor());
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SwipeDirectionalView(
            Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mSwipeViewBinderList = new ArrayList<>();
        setupView(mSwipeViewBinderList,
                new SwipeDirectionalViewBuilder<>(this),
                new SwipeDirectionalOption(),
                new SwipeDecor());
    }

    @Override
    protected <T,
            F extends FrameView,
            P extends SwipeOption,
            Q extends SwipeDecor,
            V extends SwipeViewBinder<T, F, P, Q>>
    V getViewBinder(T resolver) {
        return (V) Binding.bindSwipeDirectionalViewResolver(resolver);
    }

    protected void setSwipeHorizontalThreshold(int threshold) {
        ((SwipeDirectionalOption) getSwipeOption()).setSwipeHorizontalThreshold(threshold);
    }

    protected void setSwipeVerticalThreshold(int threshold) {
        ((SwipeDirectionalOption) getSwipeOption()).setSwipeVerticalThreshold(threshold);
    }

    @Override
    public SwipeDirectionalViewBuilder<SwipeDirectionalView> getBuilder() {
        return super.<SwipeDirectionalView, SwipeDirectionalViewBuilder<SwipeDirectionalView>>getBuilder();
    }

    public static class SwipeDirectionalOption extends SwipeOption {

        private int mSwipeHorizontalThreshold;
        private int mSwipeVerticalThreshold;

        public SwipeDirectionalOption() {
            super();
            mSwipeHorizontalThreshold = Utils.dpToPx(30);
            mSwipeVerticalThreshold = Utils.dpToPx(30);
        }

        protected int getSwipeHorizontalThreshold() {
            return mSwipeHorizontalThreshold;
        }

        protected void setSwipeHorizontalThreshold(int threshold) {
            this.mSwipeHorizontalThreshold = threshold;
        }

        protected int getSwipeVerticalThreshold() {
            return mSwipeVerticalThreshold;
        }

        protected void setSwipeVerticalThreshold(int threshold) {
            this.mSwipeVerticalThreshold = threshold;
        }
    }
}
