package com.mindorks.placeholderview;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.ArrayList;

/**
 * Created by janisharali on 18/08/16.
 */
public class ExpandablePlaceHolderView extends PlaceHolderView {

    public ExpandablePlaceHolderView(Context context) {
        super(context);
        setupView(context, new ExpandableViewAdapter(context), new PlaceHolderViewBuilder(context, this));
    }

    public ExpandablePlaceHolderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context, new ExpandableViewAdapter(context), new PlaceHolderViewBuilder(context, this));
    }

    public ExpandablePlaceHolderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupView(context, new ExpandableViewAdapter(context), new PlaceHolderViewBuilder(context, this));
    }

    @Override
    protected void setupView(Context context, ViewAdapter viewAdapter, PlaceHolderViewBuilder builder) {
        super.setupView(context, viewAdapter, builder);
    }
}
