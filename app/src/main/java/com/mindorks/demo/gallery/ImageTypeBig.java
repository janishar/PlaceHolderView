package com.mindorks.demo.gallery;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mindorks.demo.R;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.core.Animation;
import com.mindorks.placeholderview.core.annotations.Animate;
import com.mindorks.placeholderview.core.annotations.Layout;
import com.mindorks.placeholderview.core.annotations.LongClick;
import com.mindorks.placeholderview.core.annotations.NonReusable;
import com.mindorks.placeholderview.core.annotations.Position;
import com.mindorks.placeholderview.core.annotations.Recycle;
import com.mindorks.placeholderview.core.annotations.Resolve;
import com.mindorks.placeholderview.core.annotations.View;

/**
 * Created by janisharali on 19/08/16.
 */
@Animate(Animation.ENTER_LEFT_DESC)
@NonReusable
@Layout(R.layout.gallery_item_big)
public class ImageTypeBig {

    @View(R.id.imageView)
    private ImageView imageView;

    @Position
    private int position;

    private String mUlr;
    private Context mContext;
    private PlaceHolderView mPlaceHolderView;

    public ImageTypeBig(Context context, PlaceHolderView placeHolderView, String ulr) {
        mContext = context;
        mPlaceHolderView = placeHolderView;
        mUlr = ulr;
    }

    @Resolve
    private void onResolved() {
        Glide.with(mContext).load(mUlr).into(imageView);
        Log.d("DEBUG", "onResolved position " + position);
    }

    @Recycle
    private void onRecycled() {
        Log.d("DEBUG", "onRecycled position " + position);
    }

    @LongClick(R.id.imageView)
    private void onLongClick(){
        mPlaceHolderView.removeView(this);
    }

}
