package com.mindorks.placeholderview;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.LongClick;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Position;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import com.mindorks.placeholderview.annotations.Click;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by janisharali on 18/08/16.
 */
public class ViewBinder<T, V extends android.view.View> {

    private int mLayoutId;
    private T mResolver;
    private AnimationResolver mAnimationResolver;
    private boolean isNullable = false;

    /**
     *
     * @param resolver
     */
    protected ViewBinder(final T resolver){
        mResolver = resolver;
        bindLayout(resolver);
        getNullable(resolver);
        mAnimationResolver = new AnimationResolver<>();
    }

    /**
     *
     * @param promptsView
     */
    protected void bindView(V promptsView, int position){
        bindViews(mResolver, promptsView);
        bindViewPosition(mResolver, position);
        bindClick(mResolver, promptsView);
        bindLongPress(mResolver, promptsView);
        resolveView(mResolver);
    }

    /**
     *
     * @param deviceWidth
     * @param deviceHeight
     * @param view
     */
    protected void bindAnimation(int deviceWidth, int deviceHeight, V view){
        mAnimationResolver.bindAnimation(deviceWidth,deviceHeight, mResolver, view);
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

    /**
     *
     * @param resolver
     */
    private void getNullable(final T resolver){
        Annotation annotation = resolver.getClass().getAnnotation(NonReusable.class);
        if(annotation instanceof NonReusable) {
            NonReusable nonReusable = (NonReusable) annotation;
            isNullable = nonReusable.value();
        }
    }

    /**
     *
     * @param resolver
     * @param promptsView
     */
    private void bindViews(final T resolver, V promptsView){
        for(final Field field : resolver.getClass().getDeclaredFields()) {
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
     * @param position
     */
    private void bindViewPosition(final T resolver, int position){
        for(final Field field : resolver.getClass().getDeclaredFields()) {
            Annotation annotation = field.getAnnotation(Position.class);
            if(annotation instanceof Position) {
                try {
                    field.setAccessible(true);
                    field.set(resolver, position);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *
     * @param resolver
     */
    private void resolveView(final T resolver){
        for(final Method method : resolver.getClass().getDeclaredMethods()) {
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
     * @param promptsView
     */
    private void bindClick(final T resolver,final V promptsView){
        for(final Method method : resolver.getClass().getDeclaredMethods()){
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
     * @param promptsView
     */
    private void bindLongPress(final T resolver,final V promptsView){
        for(final Method method : resolver.getClass().getDeclaredMethods()){
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
                    if(!field.getType().isPrimitive()) {
                        field.setAccessible(true);
                        field.set(mResolver, null);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            mResolver = null;
            mAnimationResolver = null;
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
