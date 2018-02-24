package com.mindorks.placeholderview;

/**
 * Created by janisharali on 24/02/18.
 */

public class Binding {

    private Binding() {
        // not to be instantiated in public
    }

    protected static <T, V extends android.view.View> ViewBinder<T, V> bindViewResolver(T resolver) {
        return null;
    }

    protected static <T, V extends android.view.View> ExpandableViewBinder<T, V> bindExpandableViewResolver(T resolver) {
        return null;
    }

    protected static <T,
            V extends SwipePlaceHolderView.FrameView,
            P extends SwipePlaceHolderView.SwipeOption,
            Q extends SwipeDecor> SwipeViewBinder<T, V, P, Q> bindSwipeViewResolver(T resolver) {
        return null;
    }

    protected static <T,
            V extends SwipePlaceHolderView.FrameView,
            P extends SwipeDirectionalView.SwipeDirectionalOption,
            Q extends SwipeDecor> SwipeDirectionalViewBinder<T, V, P, Q> bindSwipeDirectionalViewResolver(T resolver) {
        return null;
    }
}
