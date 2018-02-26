package com.mindorks.demo.swipe;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindorks.demo.R;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

/**
 * Created by janisharali on 19/08/16.
 */
@NonReusable
@Layout(R.layout.tinder_card_view)
public class TinderCard2 {

    private static int count;
    @View(R.id.profileImageView)
    ImageView profileImageView;
    @View(R.id.nameAgeTxt)
    TextView nameAgeTxt;
    @View(R.id.locationNameTxt)
    TextView locationNameTxt;
    private CardCallback callback;

    public TinderCard2(CardCallback callback) {
        this.callback = callback;
    }

    @Click(R.id.profileImageView)
    public void onClick() {
        Log.d("DEBUG", "profileImageView");
    }

    @Resolve
    public void onResolve() {
        nameAgeTxt.setText("Name " + count++);
    }

    @SwipeOut
    public void onSwipedOut() {
        callback.onSwipingEnd();
    }

    @SwipeCancelState
    public void onSwipeCancelState() {
        callback.onSwipingEnd();
    }

    @SwipeIn
    public void onSwipeIn() {
        callback.onSwipingEnd();
    }

    @SwipeInState
    public void onSwipeInState() {
        callback.onSwiping();
    }

    @SwipeOutState
    public void onSwipeOutState() {
        callback.onSwiping();
    }

    public interface CardCallback{
        void onSwiping();
        void onSwipingEnd();
    }
}
