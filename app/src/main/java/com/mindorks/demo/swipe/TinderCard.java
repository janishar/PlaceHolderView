package com.mindorks.demo.swipe;

import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindorks.demo.R;
import com.mindorks.placeholderview.core.annotations.Click;
import com.mindorks.placeholderview.core.annotations.Layout;
import com.mindorks.placeholderview.core.annotations.NonReusable;
import com.mindorks.placeholderview.core.annotations.Resolve;
import com.mindorks.placeholderview.core.annotations.View;
import com.mindorks.placeholderview.core.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.core.annotations.swipe.SwipeHead;
import com.mindorks.placeholderview.core.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.core.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.core.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.core.annotations.swipe.SwipeOutState;

/**
 * Created by janisharali on 19/08/16.
 */
@NonReusable
@Layout(R.layout.tinder_card_view)
public class TinderCard {

    private static int count;

    @View(R.id.profileImageView)
    private ImageView profileImageView;

    @View(R.id.nameAgeTxt)
    private TextView nameAgeTxt;

    @View(R.id.locationNameTxt)
    private TextView locationNameTxt;

    @Click(R.id.profileImageView)
    private void onClick(){
        Log.d("DEBUG", "profileImageView");
    }

    @Resolve
    private void onResolve(){
        nameAgeTxt.setText("Name " + count++);
    }

    @SwipeOut
    private void onSwipedOut(){
        Log.d("DEBUG", "onSwipedOut");
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("DEBUG", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn(){
        Log.d("DEBUG", "onSwipedIn");
    }

    @SwipeInState
    private void onSwipeInState(){
        Log.d("DEBUG", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d("DEBUG", "onSwipeOutState");
    }

    @SwipeHead
    private void onSwipeHead() {
        profileImageView.setBackgroundColor(Color.BLUE);
        Log.d("DEBUG", "onSwipeHead");
    }
}
