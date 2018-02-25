package com.mindorks.placeholderview.processor;

/**
 * Created by janisharali on 25/02/18.
 */

public class NameStore {

    private NameStore() {
        // not to be instantiated in public
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
        public static final String ANDROID_VIEW = "View";
        public static final String PLACE_HOLDER_VIEW = "PlaceHolderView";

    }

    public static class Method {
        public static final String BIND_VIEWS = "bindViews";
        public static final String BIND_VIEW_POSITION = "bindViewPosition";
        public static final String BIND_CLICK = "bindClick";
        public static final String BIND_LONG_PRESS = "bindLongPress";
        public static final String UNBIND = "unbind";
        public static final String RESOLVE_VIEW = "resolveView";
        public static final String RECYCLE_VIEW = "recycleView";
    }

    public static class Variable {
        public static final String RESOLVER = "resolver";
        public static final String ITEM_VIEW = "itemView";
        public static final String POSITION = "position";

        public static final String LAYOUT_ID = "layoutId";
        public static final String ANIMATION_RESOLVER = "AnimationResolver";
        public static final String NULLABLE = "nullable";
    }
}
