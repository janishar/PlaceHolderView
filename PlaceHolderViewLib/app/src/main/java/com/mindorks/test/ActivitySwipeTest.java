package com.mindorks.test;

import android.animation.Animator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.mindorks.placeholderview.*;
import com.mindorks.test.swipe.SwipeCard;

public class ActivitySwipeTest extends AppCompatActivity {

    private SwipePlaceHolderView mSwipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_test);
        mSwipView = (SwipePlaceHolderView) findViewById(R.id.swipeView);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mSwipView.getBuilder()
                .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_DEFAULT)
                .setDisplayViewCount(3)
                .setSwipeDecor(
                        new SwipeDecor()
                                .setPaddingTop(10)
                                .setRelativeScale(0.01f)
                                .setSwipeInMsgLayoutId(R.layout.swipe_in_msg_view)
                                .setSwipeOutMsgLayoutId(R.layout.swipe_out_msg_view));
        mSwipView
                .addView(new SwipeCard())
                .addView(new SwipeCard())
                .addView(new SwipeCard())
                .addView(new SwipeCard())
                .addView(new SwipeCard())
                .addView(new SwipeCard())
                .addView(new SwipeCard())
                .addView(new SwipeCard())
                .addView(new SwipeCard())
                .addView(new SwipeCard());
    }
}
