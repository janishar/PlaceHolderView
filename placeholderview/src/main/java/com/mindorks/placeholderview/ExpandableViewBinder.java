package com.mindorks.placeholderview;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by janisharali on 18/08/16.
 */
public abstract class ExpandableViewBinder<T, V extends android.view.View> extends ViewBinder<T, V> {

    private boolean mParent = false;
    private boolean mExpanded = false;
    private boolean mSingleTop = false;
    private List<ExpandableViewBinder<T, V>> mChildList;
    private ExpansionCallback<T, V> mCallback;
    private int mChildPosition;
    private int mParentPosition;
    private ExpandableViewBinder<T,V> mParentViewBinder;

    protected ExpandableViewBinder(T resolver, int layoutId, boolean nullable,
                                   boolean parent, boolean singleTop) {
        super(resolver, layoutId, nullable);
        mParent = parent;
        mSingleTop = singleTop;
        mChildList = new ArrayList<>();
    }

    @Override
    protected void bindView(V itemView, int position) {
        super.bindView(itemView, position);
        if (mParent) {
            bindToggle(getResolver(), itemView);
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

    protected void collapse(){
        if (mParent && mCallback != null && mExpanded) {
            mCallback.onCollapse(this);
            bindCollapse(getResolver());
        }
        mExpanded = false;
    }

    protected void expand(){
        if (mParent && mCallback != null && !mExpanded) {
            mCallback.onExpand(this);
            bindExpand(getResolver());
        }
        mExpanded = true;
    }

    protected boolean isParent() {
        return mParent;
    }

    protected boolean isExpanded() {
        return mExpanded;
    }

    protected boolean isSingleTop() {
        return mSingleTop;
    }

    protected int getChildPosition() {
        return mChildPosition;
    }

    public void setChildPosition(int position) {
        mChildPosition = position;
    }

    protected int getParentPosition() {
        return mParentPosition;
    }

    public void setParentPosition(int position) {
        mParentPosition = position;
    }

    protected ExpandableViewBinder<T, V> getParentViewBinder() {
        return mParentViewBinder;
    }

    protected void setParentViewBinder(ExpandableViewBinder<T, V> mParentViewBinder) {
        this.mParentViewBinder = mParentViewBinder;
    }

    protected abstract void bindParentPosition(int position);

    protected abstract void bindChildPosition(int position);

    protected abstract void bindToggle(T resolver, V itemView);

    protected abstract void bindCollapse(T resolver);

    protected abstract void bindExpand(T resolver);

    protected interface ExpansionCallback<T, V extends View> {
        void onExpand(ExpandableViewBinder<T, V> parentBinder);

        void onCollapse(ExpandableViewBinder<T, V> parentBinder);
    }
}
