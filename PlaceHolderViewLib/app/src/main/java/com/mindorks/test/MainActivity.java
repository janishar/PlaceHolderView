package com.mindorks.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mindorks.butterknifelite.annotations.BindView;
import com.mindorks.placeholderview.PlaceHolderView;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.placeHolderView)
    PlaceHolderView placeHolderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        placeHolderView.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(5);

//        placeHolderView.addView();
    }
}
