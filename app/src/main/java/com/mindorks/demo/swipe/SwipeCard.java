package com.mindorks.demo.swipe;

import android.util.Log;

import com.mindorks.demo.R;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Position;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

/**
 * Created by janisharali on 19/08/16.
 */
@NonReusable
@Layout(R.layout.swipe_card_view)
public class SwipeCard {

//    @View(R.id.mainView)
//    private LinearLayout mainView;

    @Position
    private int mPosition;

    private SwipePlaceHolderView mSwipePlaceHolderView;

    public SwipeCard(SwipePlaceHolderView swipePlaceHolderView) {
        mSwipePlaceHolderView = swipePlaceHolderView;
    }

    @Click(R.id.rejectBtn)
    private void rejectBtnClick(){
        mSwipePlaceHolderView.doSwipe(this, false);
        Log.d("DEBUG", "rejectBtn");
    }

    @Click(R.id.acceptBtn)
    private void acceptBtnClick(){
        mSwipePlaceHolderView.doSwipe(this, true);
        Log.d("DEBUG", "acceptBtn");
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
}
