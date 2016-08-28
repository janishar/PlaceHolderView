package com.mindorks.test.swipe;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.Animation;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Animate;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.LongClick;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Position;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;
import com.mindorks.test.R;
import com.mindorks.test.Utils;

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

//    @View(R.id.txtView)
//    private TextView txtView;

//    @Resolve
//    private void onResolved() {
//        txtView.setText(String.valueOf(System.nanoTime()));
//        switch (mPosition){
//            case 0:
//                mainView.setBackgroundColor(Color.RED);
//                break;
//            case 1:
//                mainView.setBackgroundColor(Color.GREEN);
//                break;
//            case 2:
//                mainView.setBackgroundColor(Color.BLACK);
//                break;
//            case 3:
//                mainView.setBackgroundColor(Color.BLUE);
//                break;
//            case 4:
//                mainView.setBackgroundColor(Color.YELLOW);
//                break;
//            case 5:
//                mainView.setBackgroundColor(Color.MAGENTA);
//                break;
//            case 6:
//                mainView.setBackgroundColor(Color.GRAY);
//            case 7:
//                mainView.setBackgroundColor(Color.RED);
//                break;
//            case 8:
//                mainView.setBackgroundColor(Color.GREEN);
//                break;
//            case 9:
//                mainView.setBackgroundColor(Color.BLACK);
//                break;
//            case 10:
//                mainView.setBackgroundColor(Color.BLUE);
//                break;
//            case 11:
//                mainView.setBackgroundColor(Color.YELLOW);
//                break;
//            case 12:
//                mainView.setBackgroundColor(Color.MAGENTA);
//                break;
//            case 13:
//                mainView.setBackgroundColor(Color.GRAY);
//                break;
//        }
//    }

//    @Click(R.id.txtView)
//    private void onTxtClick(){
//        Log.d("DEBUG", "onTxtClick");
//    }

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
