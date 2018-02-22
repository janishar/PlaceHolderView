package com.mindorks.placeholderview;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by janisharali on 18/08/16.
 */
public class PlaceHolderView extends RecyclerView {

    public static final int DEFAULT_ITEM_VIEW_CACHE_SIZE = 10;
    public static final boolean DEFAULT_HAS_FIXED_SIZE = false;

    private ViewAdapter<Object> mViewAdapter;
    private PlaceHolderViewBuilder mBuilder;


    public PlaceHolderView(Context context) {
        super(context);
        setupView(context, new ViewAdapter<>(context), new PlaceHolderViewBuilder(context, this));
    }


    public PlaceHolderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context, new ViewAdapter<>(context), new PlaceHolderViewBuilder(context, this));
    }


    public PlaceHolderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupView(context, new ViewAdapter<>(context), new PlaceHolderViewBuilder(context, this));
    }


    protected void setupView(Context context, ViewAdapter viewAdapter, PlaceHolderViewBuilder builder){
        mViewAdapter = viewAdapter;
        mBuilder = builder;
        super.setAdapter(mViewAdapter);
    }


    @Deprecated
    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
    }


    private PlaceHolderViewBuilder builder(){
        return mBuilder;
    }


    public void refresh(){
        mViewAdapter.notifyDataSetChanged();
    }


    public <T>PlaceHolderView addView(T viewResolver)throws IndexOutOfBoundsException{
        mViewAdapter.addView(viewResolver);
        return this;
    }


    public <T>PlaceHolderView removeView(T viewResolver)throws IndexOutOfBoundsException{
        mViewAdapter.removeView(viewResolver);
        return this;
    }


    public <T>PlaceHolderView addView(int position, T viewResolver)throws IndexOutOfBoundsException{
        mViewAdapter.addView(position, viewResolver);
        return this;
    }


    public PlaceHolderView removeView(int position) throws IndexOutOfBoundsException{
        mViewAdapter.removeView(position);
        return this;
    }


    public <T>PlaceHolderView addViewBefore(T resolverOld,T resolverNew )throws Resources.NotFoundException {
        mViewAdapter.addView(resolverOld, resolverNew, false);
        return this;
    }


    public <T>PlaceHolderView addViewAfter(T resolverOld,T resolverNew )throws Resources.NotFoundException{
        mViewAdapter.addView(resolverOld, resolverNew, true);
        return this;
    }


    public int getViewResolverCount(){
        return mViewAdapter.getViewBinderListSize();
    }


    public Object getViewResolverAtPosition(int position) throws IndexOutOfBoundsException{
        return mViewAdapter.getViewResolverAtPosition(position);
    }

    public List<Object> getAllViewResolvers(){
        return mViewAdapter.getAllViewResolvers();
    }


    public PlaceHolderViewBuilder getBuilder() {
        return mBuilder;
    }

    @Override
    public void removeAllViews() {
        super.removeAllViews();
        if(mViewAdapter != null)
            mViewAdapter.removeAllViewBinders();
    }


    public ViewAdapter<Object> getViewAdapter() {
        return mViewAdapter;
    }

    public <T>int getViewResolverPosition(T resolver){
        return mViewAdapter.getViewResolverPosition(resolver);
    }

    public <T>void refreshView(T resolver){
        int position = getViewResolverPosition(resolver);
        if(position != -1){
            mViewAdapter.notifyItemChanged(position);
        }
    }

    public void refreshView(int position){
        if(position >= 0){
            mViewAdapter.notifyItemChanged(position);
        }
    }

    /**
     * @param comparator Provide the Comparator that compares the ItemView Object added
     *                   into the PlaceHolderView. Cast the ItemView into Class Reference
     *                   by first checking with instanceof operator when making comparisons.
     */
    public void sort(Comparator<Object> comparator) {
        List<Object> viewResolverList = getAllViewResolvers();
        Collections.sort(viewResolverList, comparator);
        mViewAdapter.sort(viewResolverList);
    }
}
