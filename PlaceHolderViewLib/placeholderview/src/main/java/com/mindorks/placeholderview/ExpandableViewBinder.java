package com.mindorks.placeholderview;

import com.mindorks.placeholderview.annotations.expand.ChildPosition;
import com.mindorks.placeholderview.annotations.expand.Collapse;
import com.mindorks.placeholderview.annotations.expand.Expand;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;
import com.mindorks.placeholderview.annotations.expand.SingleTop;

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

    public ExpandableViewBinder(T resolver) {
        super(resolver);
        bindCollapseProperty(resolver);
        mChildList = new ArrayList<>();
    }

    protected boolean bindViewType(){
        T resolver = getResolver();
        Annotation annotation = resolver.getClass().getAnnotation(Parent.class);
        if(annotation instanceof Parent) {
            Parent parent = (Parent) annotation;
            isParent = parent.value();
        }
        return isParent;
    }

    protected void bindCollapseProperty(final T resolver){
        Annotation annotation = resolver.getClass().getAnnotation(SingleTop.class);
        if(annotation instanceof SingleTop) {
            SingleTop singleTop = (SingleTop) annotation;
            isSingleTop = singleTop.value();
        }
    }

    protected void bindParentPosition(int position){
        mParentPosition = position;
        T resolver = getResolver();
        for(final Field field : resolver.getClass().getDeclaredFields()) {
            Annotation annotation = field.getAnnotation(ParentPosition.class);
            if(annotation instanceof ParentPosition) {
                try {
                    field.setAccessible(true);
                    field.set(resolver, position);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void bindChildPosition(int position){
        mChildPosition = position;
        T resolver = getResolver();
        for(final Field field : resolver.getClass().getDeclaredFields()) {
            Annotation annotation = field.getAnnotation(ChildPosition.class);
            if(annotation instanceof ChildPosition) {
                try {
                    field.setAccessible(true);
                    field.set(resolver, position);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void bindView(V promptsView, int position) {
        super.bindView(promptsView, position);
        if(isParent){
            promptsView.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    try {
                        if (isExpanded)
                            collapse();
                        else
                            expand();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void bindCollapse(final T resolver){
        for(final Method method : resolver.getClass().getDeclaredMethods()){
            Annotation annotation = method.getAnnotation(Collapse.class);
            if(annotation instanceof Collapse) {
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

    private void bindExpand(final T resolver){
        for(final Method method : resolver.getClass().getDeclaredMethods()){
            Annotation annotation = method.getAnnotation(Expand.class);
            if(annotation instanceof Expand) {
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

    @Override
    @Deprecated
    protected void unbind() {}

    @Override
    @Deprecated
    protected void bindAnimation(int deviceWidth, int deviceHeight, V view) {}

    protected void setCallback(ExpansionCallback callback) {
        mCallback = callback;
    }

    protected List<ExpandableViewBinder<T, V>> getChildList() {
        return mChildList;
    }

    protected interface ExpansionCallback<T, V extends android.view.View>{
        void onExpand(ExpandableViewBinder<T, V> parentBinder);
        void onCollapse(ExpandableViewBinder<T, V> parentBinder);
    }

    protected void collapse(){
        if(isParent && mCallback != null && isExpanded){
            mCallback.onCollapse(this);
            bindCollapse(getResolver());
        }
        isExpanded = false;
    }

    protected void expand(){
        if(isParent && mCallback != null && !isExpanded){
            mCallback.onExpand(this);
            bindExpand(getResolver());
        }
        isExpanded = true;
    }

    protected boolean isParent() {
        return isParent;
    }

    protected boolean isExpanded() {
        return isExpanded;
    }

    protected boolean isSingleTop() {
        return isSingleTop;
    }

    protected int getChildPosition() {
        return mChildPosition;
    }

    protected int getParentPosition() {
        return mParentPosition;
    }

    protected ExpandableViewBinder<T, V> getParentViewBinder() {
        return mParentViewBinder;
    }

    protected void setParentViewBinder(ExpandableViewBinder<T, V> mParentViewBinder) {
        this.mParentViewBinder = mParentViewBinder;
    }
}
