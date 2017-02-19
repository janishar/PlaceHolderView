package com.mindorks.test.swipe;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeBottomLeft;
import com.mindorks.placeholderview.annotations.swipe.SwipeBottomRight;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;
import com.mindorks.placeholderview.annotations.swipe.SwipeTopLeft;
import com.mindorks.placeholderview.annotations.swipe.SwipeTopRight;
import com.mindorks.test.R;

import java.util.Locale;

/**
 * Created by janisharali on 19/08/16.
 */
@NonReusable
@Layout(R.layout.tinder_card_view)
public class TinderCard {

    private static final String TAG = TinderCard.class.getSimpleName();

    private int count;

    @View(R.id.profileImageView)
    private ImageView profileImageView;

    @View(R.id.nameAgeTxt)
    private TextView nameAgeTxt;

    @View(R.id.locationNameTxt)
    private TextView locationNameTxt;

    @Click(R.id.profileImageView)
    private void onClick() {
        Log.d(TAG, "profileImageView");
    }

    @Resolve
    private void onResolve() {
        nameAgeTxt.setText(String.format(Locale.getDefault(), "Name %d", count++));
    }

    @SwipeOut
    private void onSwipedOut() {
        Log.d(TAG, "onSwipedOut");
    }

    @SwipeCancelState
    private void onSwipeCancelState() {
        Log.d(TAG, "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn() {
        Log.d(TAG, "onSwipedIn");
    }

    @SwipeInState
    private void onSwipeInState() {
        Log.d(TAG, "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState() {
        Log.d(TAG, "onSwipeOutState");
    }

    @SwipeTopLeft
    private void onSwipeTopLeft() {
        Log.d(TAG, "onSwipeTopLeft");
    }

    @SwipeTopRight
    private void onSwipeTopRight() {
        Log.d(TAG, "onSwipeTopRight");
    }

    @SwipeBottomLeft
    private void onSwipeBottomLeft() {
        Log.d(TAG, "onSwipeBottomLeft");
    }

    @SwipeBottomRight
    private void onSwipeBottomRight() {
        Log.d(TAG, "onSwipeBottomRight");
    }
}
