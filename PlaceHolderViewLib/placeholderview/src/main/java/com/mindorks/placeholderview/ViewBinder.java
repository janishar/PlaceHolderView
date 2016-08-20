package com.mindorks.placeholderview;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.LongClick;
import com.mindorks.placeholderview.annotations.Nullable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import com.mindorks.placeholderview.annotations.Click;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by janisharali on 18/08/16.
 */
public class ViewBinder<T> {

    private int mLayoutId;
    private T mResolver;
    private boolean isNullable = false;

    /**
     *
     * @param resolver
     */
    protected ViewBinder(final T resolver){
        mResolver = resolver;
        bindLayout(resolver);
        getNullable(resolver);
    }

    /**
     *
     * @param promptsView
     */
    protected void bindView(android.view.View promptsView){
        bindViews(mResolver, mResolver.getClass().getDeclaredFields(), promptsView);
        bindClick(mResolver, mResolver.getClass().getDeclaredMethods(), promptsView);
        bindLongPress(mResolver, mResolver.getClass().getDeclaredMethods(), promptsView);
        resolveView(mResolver, mResolver.getClass().getDeclaredMethods());
    }

    /**
     *
     * @param resolver
     */
    private void bindLayout(final T resolver){
        Annotation annotation = resolver.getClass().getAnnotation(Layout.class);
        if(annotation instanceof Layout) {
            Layout layout = (Layout) annotation;
            mLayoutId = layout.value();
        }
    }

    private void getNullable(final T resolver){
        Annotation annotation = resolver.getClass().getAnnotation(Nullable.class);
        if(annotation instanceof Nullable) {
            Nullable nullable = (Nullable) annotation;
            isNullable = nullable.value();
        }
    }

    /**
     *
     * @param resolver
     * @param fields
     * @param promptsView
     */
    private void bindViews(final T resolver,final Field[] fields, android.view.View promptsView){
        for(final Field field : fields) {
            Annotation annotation = field.getAnnotation(View.class);
            if(annotation instanceof View) {
                View viewAnnotation = (View) annotation;
                android.view.View view = promptsView.findViewById(viewAnnotation.value());
                try {
                    field.setAccessible(true);
                    field.set(resolver, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *
     * @param resolver
     * @param methods
     */
    private void resolveView(final T resolver,final Method[] methods){
        for(final Method method : methods) {
            Annotation annotation = method.getAnnotation(Resolve.class);
            if(annotation instanceof Resolve) {
                try {
                    method.setAccessible(true);
                    method.invoke(resolver);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *
     * @param resolver
     * @param methods
     * @param promptsView
     */
    private void bindClick(final T resolver,final Method[] methods,final android.view.View promptsView){
        for(final Method method : methods){
            Annotation annotation = method.getAnnotation(Click.class);
            if(annotation instanceof Click) {
                Click clickAnnotation = (Click) annotation;
                android.view.View view = promptsView.findViewById(clickAnnotation.value());
                view.setOnClickListener(new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        try {
                            method.setAccessible(true);
                            method.invoke(resolver);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    /**
     *
     * @param resolver
     * @param methods
     * @param promptsView
     */
    private void bindLongPress(final T resolver,final Method[] methods,final android.view.View promptsView){
        for(final Method method : methods){
            Annotation annotation = method.getAnnotation(LongClick.class);
            if(annotation instanceof LongClick) {
                LongClick longClickAnnotation = (LongClick) annotation;
                android.view.View view = promptsView.findViewById(longClickAnnotation.value());
                view.setOnLongClickListener(new android.view.View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(android.view.View v) {
                        try {
                            method.setAccessible(true);
                            method.invoke(resolver);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                });
            }
        }
    }

    /**
     * Remove all the references in the original class
     */
    protected void unbind(){
        if(mResolver != null && isNullable) {
            for (final Field field : mResolver.getClass().getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    field.set(mResolver, null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            mResolver = null;
        }
    }

    /**
     *
     * @return
     */
    protected int getLayoutId() {
        return mLayoutId;
    }

    /**
     *
     * @return
     */
    protected T getResolver() {
        return mResolver;
    }

}
