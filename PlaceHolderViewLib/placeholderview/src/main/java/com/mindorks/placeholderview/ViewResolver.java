package com.mindorks.placeholderview;

import android.content.Context;

/**
 * Created by janisharali on 18/08/16.
 */
public class ViewResolver {

    private Context mContext;
    private ViewBinder<ViewResolver> mViewBinder;

    public ViewResolver(Context context, ViewResolver resolver) {
        mContext = context;
        mViewBinder = new ViewBinder<>(resolver);
    }

    public Context getContext() {
        return mContext;
    }

    public ViewBinder<ViewResolver> getViewBinder() {
        return mViewBinder;
    }
}
