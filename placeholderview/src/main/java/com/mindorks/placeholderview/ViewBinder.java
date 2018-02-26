package com.mindorks.placeholderview;

/**
 * Created by janisharali on 18/08/16.
 */
public abstract class ViewBinder<T, V extends android.view.View> {

    private int mLayoutId;
    private int mPosition;
    private T mResolver;
    private AnimationResolver<T, V> mAnimationResolver;
    private boolean mNullable = false;

    protected ViewBinder(final T resolver, int layoutId, boolean nullable) {
        mResolver = resolver;
        mLayoutId = layoutId;
        mNullable = nullable;
        mAnimationResolver = new AnimationResolver<>();
    }

    protected void bindView(V promptsView, int position) {
        bindViews(mResolver, promptsView);
        bindViewPosition(mResolver, position);
        bindClick(mResolver, promptsView);
        bindLongClick(mResolver, promptsView);
        resolveView(mResolver);
    }

    protected void recycle() {
        recycleView(mResolver);
    }

    protected void bindAnimation(int deviceWidth, int deviceHeight, V view) {
        mAnimationResolver.bindAnimation(deviceWidth, deviceHeight, mResolver, view);
    }

    protected abstract void bindViews(T resolver, V promptsView);

    protected abstract void bindViewPosition(T resolver, int position);

    protected abstract void resolveView(T resolver);

    protected abstract void bindClick(T resolver, V promptsView);

    protected abstract void bindLongClick(T resolver, V promptsView);

    protected abstract void recycleView(T resolver);

    /**
     * Remove all the references in the original class
     */
    protected abstract void unbind();

    protected int getLayoutId() {
        return mLayoutId;
    }

    protected T getResolver() {
        return mResolver;
    }

    protected int getPosition() {
        return mPosition;
    }

    protected AnimationResolver getAnimationResolver() {
        return mAnimationResolver;
    }

    protected boolean isNullable() {
        return mNullable;
    }
}
