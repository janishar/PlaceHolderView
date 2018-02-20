package com.mindorks.placeholderview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by janisharali on 19/08/16.
 */
public class PlaceHolderViewBuilder {

    private int mItemViewCacheSize = PlaceHolderView.DEFAULT_ITEM_VIEW_CACHE_SIZE;
    private boolean mHasFixedSize = PlaceHolderView.DEFAULT_HAS_FIXED_SIZE;

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;

    public PlaceHolderViewBuilder(Context context, RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mRecyclerView.setLayoutManager(new SmoothLinearLayoutManager(context));
    }

    public <T extends RecyclerView.LayoutManager>PlaceHolderViewBuilder setLayoutManager(T layoutManager){
        mLayoutManager = layoutManager;
        mRecyclerView.setLayoutManager(layoutManager);
        return this;
    }

    public <T extends GridLayoutManager>PlaceHolderViewBuilder setLayoutManager(T layoutManager, final int spanSizeLookup){
        mLayoutManager = layoutManager;
        ((GridLayoutManager)mLayoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return spanSizeLookup;
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        return this;
    }

    public PlaceHolderViewBuilder setItemViewCacheSize(int viewCacheSize){
        mItemViewCacheSize = viewCacheSize;
        return this;
    }

    public PlaceHolderViewBuilder setHasFixedSize(boolean val){
        mHasFixedSize = val;
        return this;
    }
}
