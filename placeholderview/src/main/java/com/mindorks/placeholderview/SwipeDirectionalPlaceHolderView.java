package com.mindorks.placeholderview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import java.util.ArrayList;

/**
 * Created by janisharali on 09/08/17.
 */

public class SwipeDirectionalPlaceHolderView extends SwipePlaceHolderView {

    public SwipeDirectionalPlaceHolderView(Context context) {
        super(context);
        setupView(new ArrayList<SwipeDirectionalViewBinder<
                        Object, FrameView, SwipeDirectionalOption, SwipeDecor>>(),
                new SwipeViewBuilder<>(this),
                new SwipeDirectionalOption(),
                new SwipeDecor());
    }

    public SwipeDirectionalPlaceHolderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(new ArrayList<SwipeDirectionalViewBinder<
                        Object, FrameView, SwipeDirectionalOption, SwipeDecor>>(),
                new SwipeViewBuilder<>(this),
                new SwipeDirectionalOption(),
                new SwipeDecor());
    }

    public SwipeDirectionalPlaceHolderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView(new ArrayList<SwipeDirectionalViewBinder<
                        Object, FrameView, SwipeDirectionalOption, SwipeDecor>>(),
                new SwipeViewBuilder<>(this),
                new SwipeDirectionalOption(),
                new SwipeDecor());
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SwipeDirectionalPlaceHolderView(
            Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupView(new ArrayList<SwipeDirectionalViewBinder<
                        Object, FrameView, SwipeDirectionalOption, SwipeDecor>>(),
                new SwipeViewBuilder<>(this),
                new SwipeDirectionalOption(),
                new SwipeDecor());
    }


    protected void setSwipeHorizontalThreshold(int threshold) {
        ((SwipeDirectionalOption) mSwipeOption).setSwipeHorizontalThreshold(threshold);
    }

    protected void setSwipeVerticalThreshold(int threshold) {
        ((SwipeDirectionalOption) mSwipeOption).setSwipeVerticalThreshold(threshold);
    }

    protected static class SwipeDirectionalOption extends SwipeOption {

        private int mSwipeHorizontalThreshold;
        private int mSwipeVerticalThreshold;

        public SwipeDirectionalOption() {
            super();
            mSwipeHorizontalThreshold = Utils.dpToPx(5);
            mSwipeVerticalThreshold = Utils.dpToPx(5);
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
