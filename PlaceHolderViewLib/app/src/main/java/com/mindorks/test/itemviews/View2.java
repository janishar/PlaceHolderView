package com.mindorks.test.itemviews;

import android.widget.Button;
import android.widget.TextView;

import com.mindorks.placeholderview.Animation;
import com.mindorks.placeholderview.annotations.Animate;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.test.R;

/**
 * Created by janisharali on 19/08/16.
 */
@Layout(R.layout.item_view_2)
public class View2{

    @View(R.id.txt1)
    private TextView txt1;

    @View(R.id.txt2)
    private TextView txt2;

    @Resolve
    public void onResolved() {
        txt1.setText("A");
        txt2.setText("B");
    }

    @Click(R.id.btn)
    private void onClick(){
        String str1 = txt1.getText().toString();
        String str2 = txt2.getText().toString();
        txt1.setText(str2);
        txt2.setText(str1);
    }
}
