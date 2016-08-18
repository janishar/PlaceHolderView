package com.mindorks.placeholderview;

import android.content.Context;

/**
 * Created by janisharali on 18/08/16.
 */
public abstract class ViewResolver {

    private Context mContext;
    private ViewBinder<ViewResolver> mViewBinder;

    public ViewResolver(Context context) {
        mContext = context;
    }

    public void onBind(ViewResolver resolver){
        mViewBinder = new ViewBinder<>(mContext, resolver);
    }

    public Context getContext() {
        return mContext;
    }

    public ViewBinder<ViewResolver> getViewBinder() {
        return mViewBinder;
    }
}
