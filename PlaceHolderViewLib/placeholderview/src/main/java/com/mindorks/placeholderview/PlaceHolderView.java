package com.mindorks.placeholderview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

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
        setupView(context);
    }

    public PlaceHolderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setupView(context);
    }

    public PlaceHolderView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        setupView(context);
    }

    private void setupView(Context context){
        mViewAdapter = new ViewAdapter();
        mBuilder = new PlaceHolderViewBuilder(context, this);
        setAdapter(mViewAdapter);
    }

    private PlaceHolderViewBuilder builder(){
        return mBuilder;
    }

    public void refresh(){
        mViewAdapter.notifyDataSetChanged();
    }

    public <T extends ViewResolver>PlaceHolderView addView(T viewResolver)throws IndexOutOfBoundsException{
        mViewAdapter.addView(viewResolver);
        return this;
    }

    public  <T extends ViewResolver>PlaceHolderView removeView(T viewResolver)throws IndexOutOfBoundsException{
        mViewAdapter.removeView(viewResolver);
        return this;
    }

    public <T extends ViewResolver>PlaceHolderView addView(int position, T viewResolver)throws IndexOutOfBoundsException{
        mViewAdapter.addView(position, viewResolver);
        return this;
    }

    public PlaceHolderView removeView(int position) throws IndexOutOfBoundsException{
        mViewAdapter.removeView(position);
        return this;
    }

    public PlaceHolderViewBuilder getBuilder() {
        return mBuilder;
    }
}
