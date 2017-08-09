package com.mindorks.test.swipe;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindorks.placeholderview.SwipeDirection;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeInDirectional;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutDirectional;
import com.mindorks.placeholderview.annotations.swipe.SwipeTouch;
import com.mindorks.placeholderview.annotations.swipe.SwipingDirection;
import com.mindorks.test.R;

/**
 * Created by janisharali on 09/08/17.
 */

@NonReusable
@Layout(R.layout.tinder_card_view)
public class TinderDirectionalCard {

    private static int count;

    @View(R.id.profileImageView)
    private ImageView profileImageView;

    @View(R.id.nameAgeTxt)
    private TextView nameAgeTxt;

    @View(R.id.locationNameTxt)
    private TextView locationNameTxt;

    @Click(R.id.profileImageView)
    private void onClick() {
        Log.d("DEBUG", "profileImageView");
    }

    @Resolve
    private void onResolve() {
        nameAgeTxt.setText("Name " + count++);
    }

    @SwipeOutDirectional
    private void onSwipeOutDirectional(SwipeDirection direction) {
        Log.d("DEBUG", "SwipeOutDirectional " + direction.name());
    }

    @SwipeCancelState
    private void onSwipeCancelState() {
        Log.d("DEBUG", "onSwipeCancelState");
    }

    @SwipeInDirectional
    private void onSwipeInDirectional(SwipeDirection direction) {
        Log.d("DEBUG", "SwipeInDirectional " + direction.name());
    }

    @SwipingDirection
    private void onSwipingDirection(SwipeDirection direction) {
        Log.d("DEBUG", "SwipingDirection " + direction.name());
    }

    @SwipeTouch
    private void onSwipeTouch(float xStart, float yStart, float xCurrent, float yCurrent) {
        Log.d("DEBUG", "onSwipeTouch "
                + " xStart : " + xStart
                + " yStart : " + yStart
                + " xCurrent : " + xCurrent
                + " yCurrent : " + yCurrent
                + " distance : "
                + Math.sqrt(Math.pow(xCurrent - xStart, 2) + (Math.pow(yCurrent - yStart, 2)))
        );
    }
}
