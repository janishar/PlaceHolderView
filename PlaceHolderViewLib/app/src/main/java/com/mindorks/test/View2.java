package com.mindorks.test;

import android.widget.Button;
import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

/**
 * Created by janisharali on 19/08/16.
 */
@Layout(R.layout.item_view_2)
public class View2{

    @View(R.id.txt1)
    public TextView txt1;

    @View(R.id.txt2)
    public TextView txt2;

    @Resolve
    public void onResolved() {
        txt1.setText("ALI");
        txt2.setText("ANWAR");
    }

    @Click(R.id.btn)
    public void onClick(){
        txt1.setText("ALI2");
        txt2.setText("ANWAR2");
    }
}
