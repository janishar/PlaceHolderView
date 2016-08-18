package com.mindorks.placeholderview;

import android.content.Context;

/**
 * Created by janisharali on 18/08/16.
 */
public class ViewResolver {

    private Context mContext;

    public ViewResolver(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }
}
