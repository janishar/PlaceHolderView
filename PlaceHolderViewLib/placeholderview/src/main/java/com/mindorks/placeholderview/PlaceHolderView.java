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

    /**
     *
     * @param context
     */
    public PlaceHolderView(Context context) {
        super(context);
        mContext = context;
        setupView(context);
    }

    /**
     *
     * @param context
     * @param attrs
     */
    public PlaceHolderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setupView(context);
    }

    /**
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public PlaceHolderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        setupView(context);
    }

    /**
     *
     * @param context
     */
    private void setupView(Context context){
        mViewAdapter = new ViewAdapter();
        mBuilder = new PlaceHolderViewBuilder(context, this);
        setAdapter(mViewAdapter);
    }

    /**
     *
     * @return
     */
    private PlaceHolderViewBuilder builder(){
        return mBuilder;
    }

    /**
     *
     */
    public void refresh(){
        mViewAdapter.notifyDataSetChanged();
    }

    /**
     *
     * @param viewResolver
     * @param <T>
     * @return
     * @throws IndexOutOfBoundsException
     */
    public <T>PlaceHolderView addView(T viewResolver)throws IndexOutOfBoundsException{
        mViewAdapter.addView(viewResolver);
        return this;
    }

    /**
     *
     * @param viewResolver
     * @param <T>
     * @return
     * @throws IndexOutOfBoundsException
     */
    public  <T>PlaceHolderView removeView(T viewResolver)throws IndexOutOfBoundsException{
        mViewAdapter.removeView(viewResolver);
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
    public <T>PlaceHolderView addView(int position, T viewResolver)throws IndexOutOfBoundsException{
        mViewAdapter.addView(position, viewResolver);
        return this;
    }

    /**
     *
     * @param position
     * @return
     * @throws IndexOutOfBoundsException
     */
    public PlaceHolderView removeView(int position) throws IndexOutOfBoundsException{
        mViewAdapter.removeView(position);
        return this;
    }

    /**
     *
     * @return
     */
    public PlaceHolderViewBuilder getBuilder() {
        return mBuilder;
    }
}
