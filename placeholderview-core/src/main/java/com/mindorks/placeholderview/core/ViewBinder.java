package com.mindorks.placeholderview.core;

import com.mindorks.placeholderview.core.annotations.Click;
import com.mindorks.placeholderview.core.annotations.Layout;
import com.mindorks.placeholderview.core.annotations.LongClick;
import com.mindorks.placeholderview.core.annotations.NonReusable;
import com.mindorks.placeholderview.core.annotations.Position;
import com.mindorks.placeholderview.core.annotations.Recycle;
import com.mindorks.placeholderview.core.annotations.Resolve;
import com.mindorks.placeholderview.core.annotations.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by janisharali on 18/08/16.
 */
public class ViewBinder<T, V extends android.view.View> {

    private int mLayoutId;
    private int mPosition;
    private T mResolver;
    private AnimationResolver mAnimationResolver;
    private boolean isNullable = false;

    public ViewBinder(final T resolver) {
        mResolver = resolver;
        bindLayout(resolver);
        getNullable(resolver);
        mAnimationResolver = new AnimationResolver<>();
    }

    public void bindView(V promptsView, int position) {
        bindViews(mResolver, promptsView);
        bindViewPosition(mResolver, position);
        bindClick(mResolver, promptsView);
        bindLongPress(mResolver, promptsView);
        resolveView(mResolver);
    }

    public void bindAnimation(int deviceWidth, int deviceHeight, V view) {
        mAnimationResolver.bindAnimation(deviceWidth, deviceHeight, mResolver, view);
    }

    private void bindLayout(final T resolver) {
        Layout layout = resolver.getClass().getAnnotation(Layout.class);
        if (layout != null) {
            mLayoutId = layout.value();
        }
    }

    private void getNullable(final T resolver) {
        NonReusable nonReusable = resolver.getClass().getAnnotation(NonReusable.class);
        if (nonReusable != null) {
            isNullable = nonReusable.value();
        }
    }

    public void bindViews(final T resolver, V promptsView) {
        for (final Field field : resolver.getClass().getDeclaredFields()) {
            View viewAnnotation = field.getAnnotation(View.class);
            if (viewAnnotation != null) {
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

    public void bindViewPosition(final T resolver, int position) {
        mPosition = position;
        for (final Field field : resolver.getClass().getDeclaredFields()) {
            Position annotation = field.getAnnotation(Position.class);
            if (annotation != null) {
                try {
                    field.setAccessible(true);
                    field.set(resolver, position);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void resolveView(final T resolver) {
        for (final Method method : resolver.getClass().getDeclaredMethods()) {
            Resolve annotation = method.getAnnotation(Resolve.class);
            if (annotation != null) {
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

    public void bindClick(final T resolver, final V promptsView) {
        for (final Method method : resolver.getClass().getDeclaredMethods()) {
            Click clickAnnotation = method.getAnnotation(Click.class);
            if (clickAnnotation != null) {
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

    public void bindLongPress(final T resolver, final V promptsView) {
        for (final Method method : resolver.getClass().getDeclaredMethods()) {
            LongClick longClickAnnotation = method.getAnnotation(LongClick.class);
            if (longClickAnnotation != null) {
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
    public void unbind() {
        if (mResolver != null && isNullable) {
            for (final Field field : mResolver.getClass().getDeclaredFields()) {
                try {
                    if (!field.getType().isPrimitive()) {
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

    public int getLayoutId() {
        return mLayoutId;
    }

    public T getResolver() {
        return mResolver;
    }

    public int getPosition() {
        return mPosition;
    }

    public void recycleView() {
        if (mResolver != null) {
            for (final Method method : mResolver.getClass().getDeclaredMethods()) {
                Recycle annotation = method.getAnnotation(Recycle.class);
                if (annotation != null) {
                    try {
                        method.setAccessible(true);
                        method.invoke(mResolver);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
