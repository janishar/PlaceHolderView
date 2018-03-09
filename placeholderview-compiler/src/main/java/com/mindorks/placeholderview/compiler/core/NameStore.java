package com.mindorks.placeholderview.compiler.core;

/**
 * Created by janisharali on 25/02/18.
 */

public final class NameStore {

    private NameStore() {
        // not to be instantiated
    }

    public static String getClassNameWithPackage(String pkgName, String clsName) {
        return pkgName + "." + clsName;
    }

    public static String getGlobalVariableName(String variableName) {
        String prefix = "m";
        if (variableName.length() == 1)
            return prefix + variableName.substring(0, 1).toUpperCase();
        else if (variableName.length() > 1)
            return prefix + variableName.substring(0, 1).toUpperCase() + variableName.substring(1);
        else return variableName;
    }

    public static class Package {
        public static final String PLACE_HOLDER_VIEW = "com.mindorks.placeholderview";
        public static final String ANDROID_VIEW = "android.view";
        public static final String R = "com.mindorks.placeholderview.$";
    }

    public static class Class {
        // Android
        public static final String ANDROID_VIEW = "View";
        public static final String ANDROID_VIEW_ON_CLICK_LISTENER = "OnClickListener";
        public static final String ANDROID_VIEW_ON_LONG_CLICK_LISTENER = "OnLongClickListener";

        public static final String VIEW_BINDER = "ViewBinder";
        public static final String LOAD_MORE_CALLBACK_BINDER = "LoadMoreCallbackBinder";
        public static final String EXPANDABLE_VIEW_BINDER = "ExpandableViewBinder";
        public static final String SWIPE_VIEW_BINDER = "SwipeViewBinder";
        public static final String SWIPE_DIRECTIONAL_VIEW_BINDER = "SwipeDirectionalViewBinder";

        public static final String SWIPE_PLACE_HOLDER_VIEW = "SwipePlaceHolderView";
        public static final String SWIPE_DIRECTIONAL_VIEW = "SwipeDirectionalView";
        public static final String FRAME_VIEW = "FrameView";
        public static final String SWIPE_OPTION = "SwipeOption";
        public static final String SWIPE_DECOR = "SwipeDecor";
        public static final String SWIPE_DIRECTIONAL_OPTION = "SwipeDirectionalOption";
        public static final String SWIPE_DIRECTION = "SwipeDirection";

        // R
        public static final String R = "R";
        public static final String LAYOUT = "layout";
        public static final String ID = "id";
    }

    public static class Method {
        // Android
        public static final String ANDROID_VIEW_ON_CLICK = "onClick";
        public static final String ANDROID_VIEW_ON_LONG_CLICK = "onLongClick";

        // ViewBinder
        public static final String BIND_VIEWS = "bindViews";
        public static final String BIND_VIEW_POSITION = "bindViewPosition";
        public static final String BIND_CLICK = "bindClick";
        public static final String BIND_LONG_CLICK = "bindLongClick";
        public static final String UNBIND = "unbind";
        public static final String RESOLVE_VIEW = "resolveView";
        public static final String RECYCLE_VIEW = "recycleView";
        public static final String GET_RESOLVER = "getResolver";
        public static final String IS_NULLABLE = "isNullable";
        public static final String SET_RESOLVER = "setResolver";
        public static final String SET_ANIMATION_RESOLVER = "setAnimationResolver";

        //InfiniteViewBinder
        public static final String BIND_LOAD_MORE = "bindLoadMore";

        // ExpandableViewBinder
        public static final String BIND_PARENT_POSITION = "bindParentPosition";
        public static final String BIND_CHILD_POSITION = "bindChildPosition";
        public static final String SET_PARENT_POSITION = "setParentPosition";
        public static final String SET_CHILD_POSITION = "setChildPosition";
        public static final String BIND_ANIMATION = "bindAnimation";
        public static final String BIND_TOGGLE = "bindToggle";
        public static final String COLLAPSE = "collapse";
        public static final String EXPAND = "expand";
        public static final String IS_EXPANDED = "isExpanded";
        public static final String BIND_EXPAND = "bindExpand";
        public static final String BIND_COLLAPSE = "bindCollapse";

        // SwipeViewBinder
        public static final String BIND_SWIPE_VIEW = "bindSwipeView";
        public static final String BIND_SWIPE_IN = "bindSwipeIn";
        public static final String BIND_SWIPE_OUT = "bindSwipeOut";
        public static final String BIND_SWIPE_IN_STATE = "bindSwipeInState";
        public static final String BIND_SWIPE_OUT_STATE = "bindSwipeOutState";
        public static final String BIND_SWIPE_CANCEL_STATE = "bindSwipeCancelState";
        public static final String BIND_SWIPE_HEAD = "bindSwipeHead";

        // SwipeDirectionalViewBinder
        public static final String BIND_SWIPING_DIRECTION = "bindSwipingDirection";
        public static final String BIND_SWIPE_IN_DIRECTION = "bindSwipeInDirectional";
        public static final String BIND_SWIPE_OUT_DIRECTION = "bindSwipeOutDirectional";
        public static final String BIND_SWIPE_TOUCH = "bindSwipeTouch";
    }

    public static class Variable {
        public static final String RESOLVER = "resolver";
        public static final String ITEM_VIEW = "itemView";
        public static final String POSITION = "position";
        public static final String ANDROID_VIEW = "view";

        public static final String LAYOUT_ID = "layoutId";
        public static final String ANIMATION_RESOLVER = "AnimationResolver";
        public static final String NULLABLE = "nullable";
        public static final String DIRECTION = "direction";
        public static final String X_START = "xStart";
        public static final String Y_START = "yStart";
        public static final String X_CURRENT = "xCurrent";
        public static final String Y_CURRENT = "yCurrent";
    }
}
