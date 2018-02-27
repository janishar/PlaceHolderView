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

    protected void bindView(V itemView, int position) {
        bindViews(mResolver, itemView);
        bindViewPosition(mResolver, position);
        bindClick(mResolver, itemView);
        bindLongClick(mResolver, itemView);
        resolveView(mResolver);
    }

    protected void bindAnimation(int deviceWidth, int deviceHeight, V view) {
        mAnimationResolver.bindAnimation(deviceWidth, deviceHeight, mResolver, view);
    }

    protected abstract void bindViews(T resolver, V itemView);

    protected abstract void bindViewPosition(T resolver, int position);

    protected abstract void resolveView(T resolver);

    protected abstract void bindClick(T resolver, V itemView);

    protected abstract void bindLongClick(T resolver, V itemView);

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

    public void setResolver(T resolver) {
        mResolver = resolver;
    }

    protected int getPosition() {
        return mPosition;
    }

    protected AnimationResolver getAnimationResolver() {
        return mAnimationResolver;
    }

    public void setAnimationResolver(AnimationResolver<T, V> animationResolver) {
        mAnimationResolver = animationResolver;
    }

    protected boolean isNullable() {
        return mNullable;
    }
}
