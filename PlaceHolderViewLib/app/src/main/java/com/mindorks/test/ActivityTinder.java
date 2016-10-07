package com.mindorks.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;
import com.mindorks.butterknifelite.annotations.OnClick;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.test.swipe.SwipeCard;
import com.mindorks.test.swipe.TinderCard;

public class ActivityTinder extends AppCompatActivity {

    @BindView(R.id.swipeView)
    private SwipePlaceHolderView mSwipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinder_swipe);
        ButterKnifeLite.bind(this);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mSwipView.getBuilder()
                .setDisplayViewCount(2)
                .setSwipeDecor(new SwipeDecor()
                                .setPaddingTop(20)
                                .setRelativeScale(0.01f)
                                .setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg_view)
                                .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg_view));

        mSwipView.addView(new TinderCard(getWindowManager()))
                .addView(new TinderCard(getWindowManager()))
                .addView(new TinderCard(getWindowManager()))
                .addView(new TinderCard(getWindowManager()))
                .addView(new TinderCard(getWindowManager()))
                .addView(new TinderCard(getWindowManager()))
                .addView(new TinderCard(getWindowManager()))
                .addView(new TinderCard(getWindowManager()))
                .addView(new TinderCard(getWindowManager()))
                .addView(new TinderCard(getWindowManager()));
    }

    @OnClick(R.id.rejectBtn)
    private void onRejectClick(){
        mSwipView.doSwipe(false);
    }

    @OnClick(R.id.acceptBtn)
    private void onAcceptClick(){
        mSwipView.doSwipe(true);
    }
}
