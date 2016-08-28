package com.mindorks.placeholderview;

import android.view.View;

/**
 * Created by janisharali on 26/08/16.
 */
public class SwipeViewBuilder {

    private SwipePlaceHolderView mSwipePlaceHolderView;

    public SwipeViewBuilder(SwipePlaceHolderView swipePlaceHolderView) {
        mSwipePlaceHolderView = swipePlaceHolderView;
    }

    public SwipeViewBuilder setSwipeType(int swipeType){
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

    public SwipeViewBuilder setDisplayViewCount(int viewCount){
        if(viewCount < 0){
            mSwipePlaceHolderView.setDisplayViewCount(SwipePlaceHolderView.DEFAULT_DISPLAY_VIEW_COUNT);
        }else{
            mSwipePlaceHolderView.setDisplayViewCount(viewCount);
        }
        return this;
    }

    public SwipeViewBuilder setDisplayReverse(boolean reverse) {
        mSwipePlaceHolderView.setIsReverse(reverse);
        return this;
    }

    public SwipeViewBuilder setSwipeDecor(SwipeDecor decor) {
        mSwipePlaceHolderView.setSwipeDecor(decor);
        return this;
    }

    public SwipeViewBuilder setWidthSwipeDistFactor(int factor) {
        mSwipePlaceHolderView.setWidthSwipeDistFactor(factor);
        return this;
    }

    public SwipeViewBuilder setHeightSwipeDistFactor(int factor) {
        mSwipePlaceHolderView.setHeightSwipeDistFactor(factor);
        return this;
    }
}
