package com.mindorks.test.expandable;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Parent;
import com.mindorks.placeholderview.annotations.Position;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.test.R;

/**
 * Created by janisharali on 19/08/16.
 */

@Layout(R.layout.drawer_item)
public class ChildItem {

    @Position
    private int mMenuPosition;


    @View(R.id.itemNameTxt)
    private TextView itemNameTxt;

    @View(R.id.itemIcon)
    private ImageView itemIcon;

    @View(R.id.mainView)
    private LinearLayout mainView;

    @Resolve
    private void onResolved() {
        mainView.setBackgroundColor(Color.GRAY);
    }

//    @Click(R.id.mainView)
//    private void onMenuItemClick(){
//
//    }
}
