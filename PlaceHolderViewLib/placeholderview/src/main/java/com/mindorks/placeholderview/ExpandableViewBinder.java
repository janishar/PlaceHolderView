package com.mindorks.placeholderview;

import android.view.View;

import com.mindorks.placeholderview.annotations.expand.ChildPosition;
import com.mindorks.placeholderview.annotations.expand.Collapse;
import com.mindorks.placeholderview.annotations.expand.Expand;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;
import com.mindorks.placeholderview.annotations.expand.SingleTop;
import com.mindorks.placeholderview.annotations.expand.Toggle;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by janisharali on 18/08/16.
 */
public class ExpandableViewBinder<T, V extends android.view.View> extends ViewBinder<T, V>{

    private boolean isParent = false;
    private boolean isExpanded = false;
    private boolean isSingleTop = false;
    private List<ExpandableViewBinder<T, V>> mChildList;
    private ExpansionCallback mCallback;
    private int mChildPosition;
    private int mParentPosition;
    private ExpandableViewBinder<T,V> mParentViewBinder;

    /**
     *
     * @param resolver
     */
    public ExpandableViewBinder(T resolver) {
        super(resolver);
        bindCollapseProperty(resolver);
        mChildList = new ArrayList<>();
    }

    /**
     *
     * @return
     */
    protected boolean bindViewType(){
        T resolver = getResolver();
        Parent parent = resolver.getClass().getAnnotation(Parent.class);
        if(parent != null) {
            isParent = parent.value();
        }
        return isParent;
    }

    /**
     *
     * @param resolver
     */
    protected void bindCollapseProperty(final T resolver){
        SingleTop singleTop = resolver.getClass().getAnnotation(SingleTop.class);
        if(singleTop != null) {
            isSingleTop = singleTop.value();
        }
    }

    /**
     *
     * @param position
     */
    protected void bindParentPosition(int position){
        mParentPosition = position;
        T resolver = getResolver();
        for(final Field field : resolver.getClass().getDeclaredFields()) {
            ParentPosition annotation = field.getAnnotation(ParentPosition.class);
            if(annotation != null) {
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
     * @param position
     */
    protected void bindChildPosition(int position){
        mChildPosition = position;
        T resolver = getResolver();
        for(final Field field : resolver.getClass().getDeclaredFields()) {
            ChildPosition annotation = field.getAnnotation(ChildPosition.class);
            if(annotation != null) {
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
     * @param promptsView
     * @param position
     */
    @Override
    protected void bindView(V promptsView, int position) {
        super.bindView(promptsView, position);
        if(isParent){
            bindToggle(getResolver(), promptsView);
        }
    }

    /**
     *
     * @param resolver
     * @param promptsView
     */
    private void bindToggle(final T resolver,final V promptsView){
        boolean toggleSet = false;
        for(final Field field : resolver.getClass().getDeclaredFields()){
            Toggle toggle = field.getAnnotation(Toggle.class);
            if(toggle != null) {
                android.view.View view = promptsView.findViewById(toggle.value());
                view.setOnClickListener(new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        try {
                            if (isExpanded) {collapse();}
                            else {expand();}
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                toggleSet = true;
            }
        }

        if(!toggleSet){
            promptsView.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    try {
                        if (isExpanded) {collapse();}
                        else {expand();}
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     *
     * @param resolver
     */
    private void bindCollapse(final T resolver){
        for(final Method method : resolver.getClass().getDeclaredMethods()){
            Collapse annotation = method.getAnnotation(Collapse.class);
            if(annotation != null) {
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
     */
    private void bindExpand(final T resolver){
        for(final Method method : resolver.getClass().getDeclaredMethods()){
            Expand annotation = method.getAnnotation(Expand.class);
            if(annotation != null) {
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
     */
    @Override
    @Deprecated
    protected void unbind() {}

    /**
     *
     * @param deviceWidth
     * @param deviceHeight
     * @param view
     */
    @Override
    @Deprecated
    protected void bindAnimation(int deviceWidth, int deviceHeight, V view) {}

    /**
     *
     * @param callback
     */
    protected void setCallback(ExpansionCallback callback) {
        mCallback = callback;
    }

    /**
     *
     * @return
     */
    protected List<ExpandableViewBinder<T, V>> getChildList() {
        return mChildList;
    }

    /**
     *
     * @param <T>
     */
    protected interface ExpansionCallback<T>{
        void onExpand(ExpandableViewBinder<T, View> parentBinder);
        void onCollapse(ExpandableViewBinder<T, View> parentBinder);
    }

    /**
     *
     */
    protected void collapse(){
        if(isParent && mCallback != null && isExpanded){
            mCallback.onCollapse(this);
            bindCollapse(getResolver());
        }
        isExpanded = false;
    }

    /**
     *
     */
    protected void expand(){
        if(isParent && mCallback != null && !isExpanded){
            mCallback.onExpand(this);
            bindExpand(getResolver());
        }
        isExpanded = true;
    }

    /**
     *
     * @return
     */
    protected boolean isParent() {
        return isParent;
    }

    /**
     *
     * @return
     */
    protected boolean isExpanded() {
        return isExpanded;
    }

    /**
     *
     * @return
     */
    protected boolean isSingleTop() {
        return isSingleTop;
    }

    /**
     *
     * @return
     */
    protected int getChildPosition() {
        return mChildPosition;
    }

    /**
     *
     * @return
     */
    protected int getParentPosition() {
        return mParentPosition;
    }

    /**
     *
     * @return
     */
    protected ExpandableViewBinder<T, V> getParentViewBinder() {
        return mParentViewBinder;
    }

    /**
     *
     * @param mParentViewBinder
     */
    protected void setParentViewBinder(ExpandableViewBinder<T, V> mParentViewBinder) {
        this.mParentViewBinder = mParentViewBinder;
    }
}
