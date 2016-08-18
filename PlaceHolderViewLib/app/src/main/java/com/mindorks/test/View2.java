package com.mindorks.test;

import android.widget.Button;

import com.mindorks.placeholderview.ViewResolver;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.View;

/**
 * Created by janisharali on 19/08/16.
 */
@Layout(R.layout.item_view_2)
public class View2 extends ViewResolver{

    @View(R.id.txt1)
    public Button txt1;

    @View(R.id.txt2)
    public Button txt2;

    @Override
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
