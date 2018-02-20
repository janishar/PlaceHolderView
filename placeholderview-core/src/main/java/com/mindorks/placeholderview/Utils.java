package com.mindorks.placeholderview;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;

/**
 * Created by janisharali on 20/08/16.
 */
public class Utils {

    protected static int getDeviceWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    protected static int getDeviceHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    protected static boolean isAboveApi21(){
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP;
    }

    public static int dpToPx(Context context,int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    public static int dpToPx(float dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
