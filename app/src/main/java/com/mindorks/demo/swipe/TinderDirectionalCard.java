package com.mindorks.demo.swipe;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindorks.demo.R;
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

/**
 * Created by janisharali on 09/08/17.
 */

@NonReusable
@Layout(R.layout.tinder_card_view)
public class TinderDirectionalCard {

    private static int count;

    @View(R.id.profileImageView)
    ImageView profileImageView;

    @View(R.id.nameAgeTxt)
    TextView nameAgeTxt;

    @View(R.id.locationNameTxt)
    TextView locationNameTxt;

    @Click(R.id.profileImageView)
    public void onClick() {
        Log.d("DEBUG", "profileImageView");
    }

    @Resolve
    public void onResolve() {
        nameAgeTxt.setText("Name " + count++);
    }

    @SwipeOutDirectional
    public void onSwipeOutDirectional(SwipeDirection direction) {
        Log.d("DEBUG", "SwipeOutDirectional " + direction.name());
    }

    @SwipeCancelState
    public void onSwipeCancelState() {
        Log.d("DEBUG", "onSwipeCancelState");
    }

    @SwipeInDirectional
    public void onSwipeInDirectional(SwipeDirection direction) {
        Log.d("DEBUG", "SwipeInDirectional " + direction.name());
    }

    @SwipingDirection
    public void onSwipingDirection(SwipeDirection direction) {
        Log.d("DEBUG", "SwipingDirection " + direction.name());
    }

    @SwipeTouch
    public void onSwipeTouch(float xStart, float yStart, float xCurrent, float yCurrent) {
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
