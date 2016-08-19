package com.mindorks.test;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

/**
 * Created by janisharali on 19/08/16.
 */
@Layout(R.layout.item_view_3)
public class View3 {

    @View(R.id.itemPlaceHolderView)
    public PlaceHolderView placeHolderView;

    private Context mContext;

    public View3(Context mContext) {
        this.mContext = mContext;
    }

    @Resolve
    public void onResolved() {
        placeHolderView.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        placeHolderView
                .addView(new View1())
                .addView(new View2())
                .addView(new View1())
                .addView(new View1())
                .addView(new View2());
    }
}
