package com.mindorks.placeholderview;

/**
 * Created by janisharali on 09/08/17.
 */

public class SwipeDirectionalViewBuilder<S extends SwipeDirectionalView>
        extends SwipeViewBuilder<S> {

    public SwipeDirectionalViewBuilder(S SwipeDirectionalPlaceHolderView) {
        super(SwipeDirectionalPlaceHolderView);
    }

    @Override
    public SwipeDirectionalViewBuilder<S> setSwipeType(int swipeType) {
        return (SwipeDirectionalViewBuilder<S>) super.setSwipeType(swipeType);
    }

    @Override
    public SwipeDirectionalViewBuilder<S> setDisplayViewCount(int viewCount) {
        return (SwipeDirectionalViewBuilder<S>) super.setDisplayViewCount(viewCount);
    }

    @Override
    public SwipeDirectionalViewBuilder<S> setDisplayReverse(boolean reverse) {
        return (SwipeDirectionalViewBuilder<S>) super.setDisplayReverse(reverse);
    }

    @Override
    public SwipeDirectionalViewBuilder<S> setSwipeDecor(SwipeDecor decor) {
        return (SwipeDirectionalViewBuilder<S>) super.setSwipeDecor(decor);
    }

    @Override
    public SwipeDirectionalViewBuilder<S> setWidthSwipeDistFactor(float factor) {
        return (SwipeDirectionalViewBuilder<S>) super.setWidthSwipeDistFactor(factor);
    }

    @Override
    public SwipeDirectionalViewBuilder<S> setHeightSwipeDistFactor(float factor) {
        return (SwipeDirectionalViewBuilder<S>) super.setHeightSwipeDistFactor(factor);
    }

    @Override
    public SwipeDirectionalViewBuilder<S> setIsUndoEnabled(boolean enabled) {
        return (SwipeDirectionalViewBuilder<S>) super.setIsUndoEnabled(enabled);
    }

    public SwipeDirectionalViewBuilder<S> setSwipeHorizontalThreshold(int threshold) {
        if (threshold >= 0) {
            getSwipePlaceHolderView().setSwipeHorizontalThreshold(threshold);
        }
        return this;
    }

    public SwipeDirectionalViewBuilder<S> setSwipeVerticalThreshold(int threshold) {
        if (threshold >= 0) {
            getSwipePlaceHolderView().setSwipeVerticalThreshold(threshold);
        }
        return this;
    }
}
