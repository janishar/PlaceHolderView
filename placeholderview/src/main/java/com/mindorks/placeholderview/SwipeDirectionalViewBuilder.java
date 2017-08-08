package com.mindorks.placeholderview;

/**
 * Created by janisharali on 09/08/17.
 */

public class SwipeDirectionalViewBuilder<S extends SwipeDirectionalPlaceHolderView>
        extends SwipeViewBuilder {

    public SwipeDirectionalViewBuilder(S SwipeDirectionalPlaceHolderView) {
        super(SwipeDirectionalPlaceHolderView);
    }

    public SwipeViewBuilder setSwipeHorizontalThreshold(int threshold) {
        if (threshold >= 0) {
            ((SwipeDirectionalPlaceHolderView) getSwipePlaceHolderView())
                    .setSwipeHorizontalThreshold(threshold);
        }
        return this;
    }

    public SwipeViewBuilder setSwipeVerticalThreshold(int threshold) {
        if (threshold >= 0) {
            ((SwipeDirectionalPlaceHolderView) getSwipePlaceHolderView())
                    .setSwipeVerticalThreshold(threshold);
        }
        return this;
    }
}
