package com.mindorks.demo.gallery;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mindorks.demo.R;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.core.Animation;
import com.mindorks.placeholderview.core.annotations.Animate;
import com.mindorks.placeholderview.core.annotations.Layout;
import com.mindorks.placeholderview.core.annotations.LongClick;
import com.mindorks.placeholderview.core.annotations.NonReusable;
import com.mindorks.placeholderview.core.annotations.Resolve;
import com.mindorks.placeholderview.core.annotations.View;

/**
 * Created by janisharali on 19/08/16.
 */
@Animate(Animation.CARD_TOP_IN_DESC)
@NonReusable
@Layout(R.layout.gallery_item_small)
public class ImageTypeSmall {

    @View(R.id.imageView)
    private ImageView imageView;

    private String mUlr;
    private Context mContext;
    private PlaceHolderView mPlaceHolderView;

    public ImageTypeSmall(Context context, PlaceHolderView placeHolderView, String ulr) {
        mContext = context;
        mPlaceHolderView = placeHolderView;
        mUlr = ulr;
    }

    @Resolve
    private void onResolved() {
        Glide.with(mContext).load(mUlr).into(imageView);
    }

    @LongClick(R.id.imageView)
    private void onLongClick(){
        mPlaceHolderView.removeView(this);
    }

}
