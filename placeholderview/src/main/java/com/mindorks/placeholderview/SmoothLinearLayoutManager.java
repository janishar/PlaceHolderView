package com.mindorks.placeholderview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by janisharali on 27/03/16.
 */
public class SmoothLinearLayoutManager extends LinearLayoutManager {

    private Context mContext;
    private int mPreloadSize = 0;

    public SmoothLinearLayoutManager(Context context){
        super(context, LinearLayoutManager.VERTICAL, false);
        mContext = context;
    }

    public SmoothLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        mContext = context;
        mPreloadSize = 4 * mContext.getResources().getDisplayMetrics().heightPixels;
    }

    public SmoothLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        mPreloadSize = 4 * mContext.getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    protected int getExtraLayoutSpace(RecyclerView.State state) {
        return mPreloadSize;
    }

    public void setPreloadSize(int mPreloadSize) {
        this.mPreloadSize = mPreloadSize;
    }
}
