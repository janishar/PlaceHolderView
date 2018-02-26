package com.mindorks.placeholderview;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by janisharali on 18/08/16.
 */
public class ExpandablePlaceHolderView extends PlaceHolderView {

    public ExpandablePlaceHolderView(Context context) {
        super(context);
        setupView(context, new ExpandableViewAdapter<>(context), new PlaceHolderViewBuilder(context, this));
    }

    public ExpandablePlaceHolderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context, new ExpandableViewAdapter<>(context), new PlaceHolderViewBuilder(context, this));
    }

    public ExpandablePlaceHolderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupView(context, new ExpandableViewAdapter<>(context), new PlaceHolderViewBuilder(context, this));
    }

    @Override
    protected void setupView(Context context, ViewAdapter viewAdapter, PlaceHolderViewBuilder builder) {
        super.setupView(context, viewAdapter, builder);
    }

    public <T> PlaceHolderView addChildView(T parentResolver, T childResolver) throws Resources.NotFoundException {
        ((ExpandableViewAdapter<T>)getViewAdapter()).addChildView(parentResolver, childResolver);
        return this;
    }

    public <T> PlaceHolderView addChildView(int parentPosition, T childResolver) throws Resources.NotFoundException {
        ((ExpandableViewAdapter<T>)getViewAdapter()).addChildView(parentPosition, childResolver);
        return this;
    }

    @Deprecated
    @Override
    public <T> PlaceHolderView addView(int position, T viewResolver) throws IndexOutOfBoundsException {
        return super.addView(position, viewResolver);
    }

    @Deprecated
    @Override
    public <T> PlaceHolderView addViewBefore(T resolverOld, T resolverNew) throws Resources.NotFoundException {
        return super.addViewBefore(resolverOld, resolverNew);
    }

    @Deprecated
    @Override
    public <T> PlaceHolderView addViewAfter(T resolverOld, T resolverNew) throws Resources.NotFoundException {
        return super.addViewAfter(resolverOld, resolverNew);
    }

    public <T> void expand(T resolver) throws Resources.NotFoundException {
        toggle(resolver, true);
    }

    public void expand(int position) throws Resources.NotFoundException {
        toggle(position, true);
    }

    public <T> void collapse(T resolver) throws Resources.NotFoundException {
        toggle(resolver, false);
    }

    public void collapse(int position) throws Resources.NotFoundException {
        toggle(position, false);
    }

    public void collapseAll() {
        List<ExpandableViewBinder> parentBinderList = new ArrayList<>();
        for (ViewBinder viewBinder : getViewAdapter().getViewBinderList()) {
            ExpandableViewBinder binder = (ExpandableViewBinder) viewBinder;
            if (binder.isParent()) {
                parentBinderList.add(binder);
            }
        }

        for (ExpandableViewBinder binder : parentBinderList) {
            binder.collapse();
        }
    }

    protected <T> void toggle(T resolver, boolean isToExpand) throws Resources.NotFoundException {
        ExpandableViewBinder<T, View> binder = ((ExpandableViewAdapter<T>) getViewAdapter())
                .getBinderForResolver(resolver);

        if (resolver == null) {
            throw new Resources.NotFoundException(
                    "Parent view don't exists in the ExpandablePlaceHolderView");
        }

        if (isToExpand) {
            binder.expand();
        } else {
            binder.collapse();
        }
    }

    protected void toggle(int position, boolean isToExpand) throws Resources.NotFoundException {

        ExpandableViewBinder<Object, View> binder =
                ((ExpandableViewAdapter<Object>) getViewAdapter())
                        .getParentBinderAtPosition(position);

        if (binder == null) {
            throw new Resources.NotFoundException(
                    "Parent view don't exists in the ExpandablePlaceHolderView");
        }

        if (isToExpand) {
            binder.expand();
        } else {
            binder.collapse();
        }
    }
}
