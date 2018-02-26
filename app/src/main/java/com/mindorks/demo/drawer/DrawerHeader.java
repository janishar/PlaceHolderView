package com.mindorks.demo.drawer;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindorks.demo.R;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Recycle;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

/**
 * Created by janisharali on 19/08/16.
 */
@NonReusable
@Layout(R.layout.drawer_header)
public class DrawerHeader {

    @View(R.id.profileImageView)
    ImageView profileImage;

    @View(R.id.nameTxt)
    TextView nameTxt;

    @View(R.id.emailTxt)
    TextView emailTxt;

    @Resolve
    public void onResolved() {
        nameTxt.setText("Janishar Ali");
        emailTxt.setText("janishar.ali@gmail.com");
    }

    @Recycle
    public void onRecycled() {
        Log.i("DEBUG", "onRecycled");
    }
}
