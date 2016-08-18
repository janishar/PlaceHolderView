package com.mindorks.placeholderview;

import android.content.Context;
import android.view.LayoutInflater;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.View;

import com.mindorks.placeholderview.annotations.Click;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by janisharali on 18/08/16.
 */
public class ViewBinder<T extends ViewResolver> {

    private static List<ViewBinder<ViewResolver>> mViewBinderList = new ArrayList<>();
    private int mLayoutId;
    private android.view.View mItemView;
    private T mResolver;

    protected ViewBinder(Context context, final T resolver){
        mResolver = resolver;
        mItemView = bindLayout(context, resolver);
        bindView(mItemView);
        mViewBinderList.add((ViewBinder<ViewResolver>) this);
    }

    protected void bindView(android.view.View itemView){
        bindViews(mResolver, mResolver.getClass().getFields(), itemView);
        bindClick(mResolver, mResolver.getClass().getMethods(), itemView);
    }

    private android.view.View bindLayout(Context context, final T resolver){
        Layout layoutAnnotation = resolver.getClass().getAnnotation(Layout.class);
        int id = layoutAnnotation.value();
        mLayoutId = id;
        return LayoutInflater.from(context).inflate(id, null);
    }

    private void bindViews(final T resolver,final Field[] fields, android.view.View itemView){
        for(final Field field : fields) {
            View viewAnnotation = field.getAnnotation(View.class);
            int id = viewAnnotation.value();
            android.view.View view = itemView.findViewById(id);
            try {
                field.set(resolver, view);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void bindClick(final T resolver,final Method[] methods,final android.view.View itemView){
        for(final Method method : methods){
            Click onClick = method.getAnnotation(Click.class);
            int id = onClick.value();
            android.view.View view =  itemView.findViewById(id);
            view.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    try {
                        method.invoke(resolver);
                    }catch (IllegalAccessException e){
                        e.printStackTrace();
                    }
                    catch (InvocationTargetException e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    protected int getLayoutId() {
        return mLayoutId;
    }

    protected T getResolver() {
        return mResolver;
    }

    public android.view.View getmItemView() {
        return mItemView;
    }

    protected static List<ViewBinder<ViewResolver>> getViewBinderList() {
        return mViewBinderList;
    }

    @Override
    protected void finalize() throws Throwable {
        if(mViewBinderList.contains(this)){
            mViewBinderList.remove(this);
        }
        super.finalize();
    }
}
