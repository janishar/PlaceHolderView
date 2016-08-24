package com.mindorks.placeholderview;

import com.mindorks.placeholderview.annotations.Parent;
import com.mindorks.placeholderview.annotations.SingleTop;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
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

    public ExpandableViewBinder(T resolver) {
        super(resolver);
        bindCollapseProperty(resolver);
        mChildList = new ArrayList<>();
    }


    protected boolean bindViewType(final T resolver){
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
        }
        isExpanded = false;
    }

    protected void expand(){
        if(isParent && mCallback != null && !isExpanded){
            mCallback.onExpand(this);
        }
        isExpanded = true;
    }

    protected boolean isParent() {
        return isParent;
    }

    protected boolean isExpanded() {
        return isExpanded;
    }

    public boolean isSingleTop() {
        return isSingleTop;
    }
}
