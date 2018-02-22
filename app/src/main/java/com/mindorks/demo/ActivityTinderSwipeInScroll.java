package com.mindorks.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mindorks.demo.swipe.TinderCard2;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

public class ActivityTinderSwipeInScroll extends AppCompatActivity implements TinderCard2.CardCallback{

    private SwipePlaceHolderView mSwipView;

    private CustomScrollview scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_in_scroll);
        mSwipView = findViewById(R.id.swipeView);
        scroll = findViewById(R.id.scroll);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mSwipView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg_view));

        mSwipView.addView(new TinderCard2(this))
                .addView(new TinderCard2(this))
                .addView(new TinderCard2(this))
                .addView(new TinderCard2(this))
                .addView(new TinderCard2(this))
                .addView(new TinderCard2(this))
                .addView(new TinderCard2(this))
                .addView(new TinderCard2(this))
                .addView(new TinderCard2(this))
                .addView(new TinderCard2(this));
    }

    @Override
    public void onSwiping() {
        scroll.setScrollable(false);
    }

    @Override
    public void onSwipingEnd() {
        scroll.setScrollable(true);
    }
}
