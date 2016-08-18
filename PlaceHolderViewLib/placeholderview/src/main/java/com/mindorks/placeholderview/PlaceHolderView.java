package com.mindorks.placeholderview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by janisharali on 18/08/16.
 */
public class PlaceHolderView extends RecyclerView {

    private ViewAdapter mViewAdapter;
    private PlaceHolderViewBuilder mBuilder;
    private Context mContext;

    public PlaceHolderView(Context context) {
        super(context);
        mContext = context;
        mViewAdapter = new ViewAdapter(ViewBinder.getViewBinderList());
        mBuilder = new PlaceHolderViewBuilder(context, this);
    }

    public PlaceHolderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mViewAdapter = new ViewAdapter(ViewBinder.getViewBinderList());
        mBuilder = new PlaceHolderViewBuilder(context, this);
    }

    public PlaceHolderView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        mViewAdapter = new ViewAdapter(ViewBinder.getViewBinderList());
        mBuilder = new PlaceHolderViewBuilder(context, this);
    }

    private PlaceHolderViewBuilder builder(){
        return mBuilder;
    }

    public void refresh(){
        mViewAdapter.notifyDataSetChanged();
    }

    protected <T extends ViewResolver>PlaceHolderView addView(T viewResolver){
        mViewAdapter.addView(viewResolver);
        return this;
    }

    protected  <T extends ViewResolver>PlaceHolderView removeView(T viewResolver){
        mViewAdapter.removeView(viewResolver);
        return this;
    }

    protected <T extends ViewResolver>PlaceHolderView addViewAtPosition(int position, T viewResolver){
        mViewAdapter.addViewAtPosition(position, viewResolver);
        return this;
    }

    public PlaceHolderView removeViewAtPosition(int position){
        mViewAdapter.removeViewAtPosition(position);
        return this;
    }

    public PlaceHolderViewBuilder getBuilder() {
        return mBuilder;
    }
}
