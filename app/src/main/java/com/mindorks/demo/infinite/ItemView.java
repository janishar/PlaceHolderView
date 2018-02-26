package com.mindorks.demo.infinite;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.demo.R;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

/**
 * Created by janisharali on 24/08/16.
 */
@Layout(R.layout.load_more_item_view)
public class ItemView {

    @View(R.id.titleTxt)
    TextView titleTxt;

    @View(R.id.captionTxt)
    TextView captionTxt;

    @View(R.id.timeTxt)
    TextView timeTxt;

    @View(R.id.imageView)
    ImageView imageView;

    private InfiniteFeedInfo mInfo;
    private Context mContext;

    public ItemView(Context context, InfiniteFeedInfo info) {
        mContext = context;
        mInfo = info;
    }

    @Resolve
    public void onResolved() {
        titleTxt.setText(mInfo.getTitle());
        captionTxt.setText(mInfo.getCaption());
        timeTxt.setText(mInfo.getTime());
        Glide.with(mContext).load(mInfo.getImageUrl()).into(imageView);
    }

    public InfiniteFeedInfo getInfo() {
        return mInfo;
    }
}
