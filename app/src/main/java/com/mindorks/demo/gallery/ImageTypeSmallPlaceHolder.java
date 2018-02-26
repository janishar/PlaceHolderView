package com.mindorks.demo.gallery;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.mindorks.demo.Image;
import com.mindorks.demo.R;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Animate;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import java.util.List;

/**
 * Created by janisharali on 19/08/16.
 */
@Animate(Animate.CARD_TOP_IN_DESC)
@NonReusable
@Layout(R.layout.gallery_item_small_placeholder)
public class ImageTypeSmallPlaceHolder {

    @View(R.id.placeholderview)
    PlaceHolderView mPlaceHolderView;

    private Context mContext;
    private List<Image> mImageList;

    public ImageTypeSmallPlaceHolder(Context context, List<Image> imageList) {
        mContext = context;
        mImageList = imageList;
    }

    @Resolve
    public void onResolved() {
        mPlaceHolderView.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        for(Image image : mImageList) {
            mPlaceHolderView.addView(new ImageTypeSmall(mContext, mPlaceHolderView, image.getUrl()));
        }
    }
}
