package com.mindorks.placeholderview;

/**
 * Created by janisharali on 18/08/16.
 */
public abstract class ViewBinder<T, V extends android.view.View> {

    private int mLayoutId;
    private int mPosition;
    private T mResolver;
    private AnimationResolver mAnimationResolver;
    private boolean isNullable = false;

    protected ViewBinder(final T resolver) {
        mResolver = resolver;
        bindLayout(resolver);
        getNullable(resolver);
        mAnimationResolver = new AnimationResolver<>();
    }

    protected void bindView(V promptsView, int position) {
        bindViews(mResolver, promptsView);
        bindViewPosition(mResolver, position);
        bindClick(mResolver, promptsView);
        bindLongPress(mResolver, promptsView);
        resolveView(mResolver);
    }

    protected void bindAnimation(int deviceWidth, int deviceHeight, V view) {
        mAnimationResolver.bindAnimation(deviceWidth, deviceHeight, mResolver, view);
    }

    protected abstract void bindLayout(final T resolver);

    protected abstract void getNullable(final T resolver);

    protected abstract void bindViews(final T resolver, V promptsView);

    protected abstract void bindViewPosition(final T resolver, int position);

    protected abstract void resolveView(final T resolver);

    protected abstract void bindClick(final T resolver, final V promptsView);

    protected abstract void bindLongPress(final T resolver, final V promptsView);

    protected abstract void recycleView();

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
}
