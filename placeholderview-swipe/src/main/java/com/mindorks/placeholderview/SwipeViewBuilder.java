package com.mindorks.placeholderview;

/**
 * Created by janisharali on 26/08/16.
 */
public class SwipeViewBuilder<S extends SwipePlaceHolderView> {

    protected S mSwipePlaceHolderView;

    public SwipeViewBuilder(S swipePlaceHolderView) {
        mSwipePlaceHolderView = swipePlaceHolderView;
    }

    public SwipeViewBuilder<S> setSwipeType(int swipeType) {
        if(swipeType == SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL){
            mSwipePlaceHolderView.setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL);
        }
        else if(swipeType == SwipePlaceHolderView.SWIPE_TYPE_VERTICAL){
            mSwipePlaceHolderView.setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_VERTICAL);
        }
        else{
            mSwipePlaceHolderView.setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_DEFAULT);
        }
        return this;
    }

    public SwipeViewBuilder<S> setDisplayViewCount(int viewCount) {
        if(viewCount < 0){
            mSwipePlaceHolderView.setDisplayViewCount(SwipePlaceHolderView.DEFAULT_DISPLAY_VIEW_COUNT);
        }else{
            mSwipePlaceHolderView.setDisplayViewCount(viewCount);
        }
        return this;
    }

    public SwipeViewBuilder<S> setDisplayReverse(boolean reverse) {
        mSwipePlaceHolderView.setIsReverse(reverse);
        return this;
    }

    public SwipeViewBuilder<S> setSwipeDecor(SwipeDecor decor) {
        mSwipePlaceHolderView.setSwipeDecor(decor);
        return this;
    }

    public SwipeViewBuilder<S> setWidthSwipeDistFactor(float factor) {
        if(factor >= 1) {
            mSwipePlaceHolderView.setWidthSwipeDistFactor(factor);
        }
        return this;
    }

    public SwipeViewBuilder<S> setHeightSwipeDistFactor(float factor) {
        if(factor >= 1) {
            mSwipePlaceHolderView.setHeightSwipeDistFactor(factor);
        }
        return this;
    }

    public SwipeViewBuilder<S> setIsUndoEnabled(boolean enabled) {
        mSwipePlaceHolderView.setIsUndoEnabled(enabled);
        return this;
    }

    public S getSwipePlaceHolderView() {
        return mSwipePlaceHolderView;
    }
}
