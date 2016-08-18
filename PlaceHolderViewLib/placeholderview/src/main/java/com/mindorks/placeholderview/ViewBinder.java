package com.mindorks.placeholderview;

import com.mindorks.placeholderview.annotations.Layout;
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
public class ViewBinder<T extends ViewResolver> {

    private int mLayoutId;
    private T mResolver;

    protected ViewBinder(final T resolver){
        mResolver = resolver;
        bindLayout(resolver);
    }

    protected void bindView(android.view.View promptsView){
        bindViews(mResolver, mResolver.getClass().getFields(), promptsView);
        bindClick(mResolver, mResolver.getClass().getMethods(), promptsView);
    }

    private void bindLayout(final T resolver){
        Annotation annotation = resolver.getClass().getAnnotation(Layout.class);
        if(annotation instanceof Layout) {
            Layout layout = (Layout) annotation;
            mLayoutId = layout.value();
        }
    }

    private void bindViews(final T resolver,final Field[] fields, android.view.View promptsView){
        for(final Field field : fields) {
            Annotation annotation = field.getAnnotation(View.class);
            if(annotation instanceof View) {
                View viewAnnotation = (View) annotation;
                android.view.View view = promptsView.findViewById(viewAnnotation.value());
                try {
                    field.set(resolver, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

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

    protected int getLayoutId() {
        return mLayoutId;
    }

    protected T getResolver() {
        return mResolver;
    }

}
