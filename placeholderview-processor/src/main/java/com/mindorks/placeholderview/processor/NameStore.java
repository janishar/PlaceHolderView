package com.mindorks.placeholderview.processor;

/**
 * Created by janisharali on 25/02/18.
 */

public class NameStore {

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
    }

    public static class Class {
        public static final String VIEW_BINDER = "ViewBinder";
        public static final String EXPANDABLE_VIEW_BINDER = "ExpandableViewBinder";
        public static final String ANDROID_VIEW = "View";
        public static final String ANDROID_VIEW_ON_CLICK_LISTENER = "OnClickListener";
        public static final String ANDROID_VIEW_ON_LONG_CLICK_LISTENER = "OnLongClickListener";
        public static final String PLACE_HOLDER_VIEW = "PlaceHolderView";
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
    }

    public static class Variable {
        public static final String RESOLVER = "resolver";
        public static final String ITEM_VIEW = "itemView";
        public static final String POSITION = "position";
        public static final String ANDROID_VIEW = "view";

        public static final String LAYOUT_ID = "layoutId";
        public static final String ANIMATION_RESOLVER = "AnimationResolver";
        public static final String NULLABLE = "nullable";
    }
}
