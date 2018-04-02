package com.mindorks.placeholderview;

import com.mindorks.placeholderview.annotations.internal.BindingSuffix;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by janisharali on 24/02/18.
 */

public class Binding {

    private Binding() {
        // not to be instantiated in public
    }

    @SuppressWarnings("unchecked")
    private static <T, C> C getBinderInstance(T target, String suffix) {
        Class<?> targetClass = target.getClass();
        String className = targetClass.getName();
        try {
            Class<?> bindingClass = targetClass
                    .getClassLoader()
                    .loadClass(className + suffix);
            Constructor<?> classConstructor = bindingClass.getConstructor(targetClass);
            try {
                return (C) classConstructor.newInstance(target);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Unable to invoke " + classConstructor, e);
            } catch (InstantiationException e) {
                throw new RuntimeException("Unable to invoke " + classConstructor, e);
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (cause instanceof RuntimeException) {
                    throw (RuntimeException) cause;
                }
                if (cause instanceof Error) {
                    throw (Error) cause;
                }
                throw new RuntimeException("Unable to create instance.", cause);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to find Class for " + className + suffix + "\n" +
                    "PLEASE ADD >> annotationProcessor 'com.mindorks.android:placeholderview-compiler:<LATEST-VERSION>' << in build.gradle", e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Unable to find constructor for " + className + suffix, e);
        }
    }

    protected static <T, V extends android.view.View> ViewBinder<T, V> bindViewResolver(T resolver) {
        return getBinderInstance(resolver, BindingSuffix.CLASS_VIEW_BINDER_SUFFIX);
    }

    protected static <T> LoadMoreCallbackBinder bindLoadMoreCallback(T resolver) {
        return getBinderInstance(resolver, BindingSuffix.CLASS_LOAD_MORE_VIEW_BINDER_SUFFIX);
    }

    protected static <T, V extends android.view.View> ExpandableViewBinder<T, V> bindExpandableViewResolver(T resolver) {
        return getBinderInstance(resolver, BindingSuffix.CLASS_EXPANDABLE_VIEW_BINDER_SUFFIX);
    }

    protected static <T,
            V extends SwipePlaceHolderView.FrameView,
            P extends SwipePlaceHolderView.SwipeOption,
            Q extends SwipeDecor> SwipeViewBinder<T, V, P, Q> bindSwipeViewResolver(T resolver) {
        return getBinderInstance(resolver, BindingSuffix.CLASS_SWIPE_VIEW_BINDER_SUFFIX);
    }

    protected static <T,
            V extends SwipePlaceHolderView.FrameView,
            P extends SwipeDirectionalView.SwipeDirectionalOption,
            Q extends SwipeDecor> SwipeDirectionalViewBinder<T, V, P, Q> bindSwipeDirectionalViewResolver(T resolver) {
        return getBinderInstance(resolver, BindingSuffix.CLASS_SWIPE_DIRECTIONAL_VIEW_BINDER_SUFFIX);
    }
}
