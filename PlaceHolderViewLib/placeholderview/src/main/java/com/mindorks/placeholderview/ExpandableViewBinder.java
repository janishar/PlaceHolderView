package com.mindorks.placeholderview;

import com.mindorks.placeholderview.annotations.Parent;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by janisharali on 18/08/16.
 */
public class ExpandableViewBinder<T, V extends android.view.View> extends ViewBinder<T, V>{

    private boolean isParent = true;
    private boolean isExpanded = true;
    private List<ExpandableViewBinder<T, V>> mChildList;
    private ExpandableViewBinder<T, V> mParent;
    private ExpansionCallback mCallback;

    public ExpandableViewBinder(T resolver) {
        super(resolver);
        if(isParent) mChildList = new ArrayList<>();
    }


    protected boolean bindViewType(final T resolver){
        Annotation annotation = resolver.getClass().getAnnotation(Parent.class);
        if(annotation instanceof Parent) {
            isParent = true;
        }else{
            isParent = false;
        }
        return isParent;
    }

    @Override
    protected void bindView(V promptsView, int position) {
        super.bindView(promptsView, position);
        if(isParent){
            promptsView.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    try {
                        if(mCallback != null) {
                            if (isExpanded)
                                mCallback.onCollapse(ExpandableViewBinder.this);
                            else
                                mCallback.onExpand(ExpandableViewBinder.this);
                            isExpanded = !isExpanded;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    protected void addChildToList(ExpandableViewBinder childBinder) {
        if(isParent && mChildList != null){
            mChildList.add(childBinder);
            childBinder.mParent = this;
        }
    }

    protected void removeChildFromList(ExpandableViewBinder childBinder) {
        if(isParent && mChildList != null && mChildList.contains(childBinder)){
            mChildList.remove(childBinder);
        }
    }

    protected void setCallback(ExpansionCallback callback) {
        mCallback = callback;
    }

    public List<ExpandableViewBinder<T, V>> getChildList() {
        return mChildList;
    }

    interface ExpansionCallback<T, V extends android.view.View>{
        void onExpand(ExpandableViewBinder<T, V> parentBinder);
        void onCollapse(ExpandableViewBinder<T, V> parentBinder);
    }
}
