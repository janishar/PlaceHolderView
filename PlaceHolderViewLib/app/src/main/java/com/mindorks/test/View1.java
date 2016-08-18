package com.mindorks.test;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.mindorks.placeholderview.ViewResolver;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.View;

/**
 * Created by janisharali on 19/08/16.
 */
@Layout(R.layout.item_view_1)
public class View1 extends ViewResolver{

    @View(R.id.txt)
    public TextView txt;

    public View1(Context context) {
        super(context);
        super.bind(this);
        txt.setText("ALI");
    }

    @Click(R.id.btn)
    public void onClick(){
        txt.setText("ALI1");
    }
}
