package com.mindorks.test.swipe;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.Animation;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Animate;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.LongClick;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Position;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.test.R;
import com.mindorks.test.Utils;

/**
 * Created by janisharali on 19/08/16.
 */
@NonReusable
@Layout(R.layout.swipe_card_view)
public class SwipeCard {

    @View(R.id.mainView)
    private LinearLayout mainView;

    @Position
    private int mPosition;

    @View(R.id.txtView)
    private TextView txtView;

    @Resolve
    private void onResolved() {
        txtView.setText(String.valueOf(System.nanoTime()));
        switch (mPosition){
            case 0:
                mainView.setBackgroundColor(Color.RED);
                break;
            case 1:
                mainView.setBackgroundColor(Color.GREEN);
                break;
            case 2:
                mainView.setBackgroundColor(Color.BLACK);
                break;
            case 3:
                mainView.setBackgroundColor(Color.BLUE);
                break;
            case 4:
                mainView.setBackgroundColor(Color.YELLOW);
                break;
            case 5:
                mainView.setBackgroundColor(Color.MAGENTA);
                break;
            case 6:
                mainView.setBackgroundColor(Color.GRAY);
                break;
        }
    }
}
