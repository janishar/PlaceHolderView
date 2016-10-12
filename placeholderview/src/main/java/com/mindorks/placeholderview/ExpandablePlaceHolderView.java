package com.mindorks.placeholderview;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by janisharali on 18/08/16.
 */
public class ExpandablePlaceHolderView extends PlaceHolderView {

    /**
     *
     * @param context
     */
    public ExpandablePlaceHolderView(Context context) {
        super(context);
        setupView(context, new ExpandableViewAdapter<>(context), new PlaceHolderViewBuilder(context, this));
    }

    /**
     *
     * @param context
     * @param attrs
     */
    public ExpandablePlaceHolderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context, new ExpandableViewAdapter<>(context), new PlaceHolderViewBuilder(context, this));
    }

    /**
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public ExpandablePlaceHolderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupView(context, new ExpandableViewAdapter<>(context), new PlaceHolderViewBuilder(context, this));
    }

    /**
     *
     * @param context
     * @param viewAdapter
     * @param builder
     */
    @Override
    protected void setupView(Context context, ViewAdapter viewAdapter, PlaceHolderViewBuilder builder) {
        super.setupView(context, viewAdapter, builder);
    }

    /**
     *
     * @param parentResolver
     * @param childResolver
     * @param <T>
     * @return
     * @throws Resources.NotFoundException
     */
    public <T> PlaceHolderView addChildView(T parentResolver, T childResolver) throws Resources.NotFoundException {
        ((ExpandableViewAdapter<T>)getViewAdapter()).addChildView(parentResolver, childResolver);
        return this;
    }

    /**
     *
     * @param parentPosition
     * @param childResolver
     * @param <T>
     * @return
     * @throws Resources.NotFoundException
     */
    public <T> PlaceHolderView addChildView(int parentPosition, T childResolver) throws Resources.NotFoundException {
        ((ExpandableViewAdapter<T>)getViewAdapter()).addChildView(parentPosition, childResolver);
        return this;
    }

    /**
     *
     * @param position
     * @param viewResolver
     * @param <T>
     * @return
     * @throws IndexOutOfBoundsException
     */
    @Deprecated
    @Override
    public <T> PlaceHolderView addView(int position, T viewResolver) throws IndexOutOfBoundsException {
        return super.addView(position, viewResolver);
    }

    /**
     *
     * @param resolverOld
     * @param resolverNew
     * @param <T>
     * @return
     * @throws Resources.NotFoundException
     */
    @Deprecated
    @Override
    public <T> PlaceHolderView addViewBefore(T resolverOld, T resolverNew) throws Resources.NotFoundException {
        return super.addViewBefore(resolverOld, resolverNew);
    }

    /**
     *
     * @param resolverOld
     * @param resolverNew
     * @param <T>
     * @return
     * @throws Resources.NotFoundException
     */
    @Deprecated
    @Override
    public <T> PlaceHolderView addViewAfter(T resolverOld, T resolverNew) throws Resources.NotFoundException {
        return super.addViewAfter(resolverOld, resolverNew);
    }
}
