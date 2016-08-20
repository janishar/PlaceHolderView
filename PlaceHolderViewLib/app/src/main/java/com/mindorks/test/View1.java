package com.mindorks.test;

import android.graphics.Color;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mindorks.placeholderview.Animation;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Animate;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.LongClick;
import com.mindorks.placeholderview.annotations.Nullable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

/**
 * Created by janisharali on 19/08/16.
 */
@Animate(Animation.ENTER_RIGHT_DESC)
@Nullable
@Layout(R.layout.item_view_1)
public class View1{

    @View(R.id.txt)
    private TextView txt;

    protected PlaceHolderView mPlaceHolderView;

    public View1(PlaceHolderView placeHolderView) {
        mPlaceHolderView = placeHolderView;
    }

    @Resolve
    private void onResolved() {
        txt.setText(String.valueOf(System.currentTimeMillis() / 1000));
    }

    @Click(R.id.btn)
    private void onClick(){
        txt.setText(String.valueOf(System.currentTimeMillis() / 1000));
    }

    @LongClick(R.id.mainView)
    private void onMainViewLongClick(){
        mPlaceHolderView.removeView(this);
    }
}
