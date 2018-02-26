package com.mindorks.placeholderview;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by janisharali on 18/08/16.
 */
public abstract class ExpandableViewBinder<T, V extends android.view.View> extends ViewBinder<T, V> {

    private boolean isParent = false;
    private boolean isExpanded = false;
    private boolean isSingleTop = false;
    private List<ExpandableViewBinder<T, V>> mChildList;
    private ExpansionCallback mCallback;
    private int mChildPosition;
    private int mParentPosition;
    private ExpandableViewBinder<T,V> mParentViewBinder;

    protected ExpandableViewBinder(T resolver, int layoutId, boolean nullable) {
        super(resolver, layoutId, nullable);
        bindCollapseProperty(resolver);
        mChildList = new ArrayList<>();
    }

    protected abstract boolean bindViewType();

    protected abstract void bindCollapseProperty(final T resolver);

    protected abstract void bindParentPosition(int position);

    protected abstract void bindChildPosition(int position);

    @Override
    protected void bindView(V promptsView, int position) {
        super.bindView(promptsView, position);
        if(isParent){
            bindToggle(getResolver(), promptsView);
        }
    }

    protected abstract void bindToggle(T resolver, V promptsView);

    protected abstract void bindCollapse(T resolver);

    protected abstract void bindExpand(T resolver);

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

    protected interface ExpansionCallback<T> {
        void onExpand(ExpandableViewBinder<T, View> parentBinder);

        void onCollapse(ExpandableViewBinder<T, View> parentBinder);
    }
}
