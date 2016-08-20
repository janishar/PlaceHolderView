package com.mindorks.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.animation.DecelerateInterpolator;

import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;
import com.mindorks.placeholderview.PlaceHolderView;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.placeHolderView)
    PlaceHolderView mPlaceHolderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnifeLite.bind(this);
        mPlaceHolderView.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10);
//                .setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        mPlaceHolderView
                .addView(new View1(mPlaceHolderView))
                .addView(new View2())
                .addView(new View3(this))
                .addView(new View1(mPlaceHolderView))
                .addView(new View1(mPlaceHolderView))
                .addView(new View1(mPlaceHolderView))
                .addView(new View1(mPlaceHolderView))
                .addView(new View1(mPlaceHolderView))
                .addView(new View1(mPlaceHolderView))
                .addView(new View1(mPlaceHolderView))
                .addView(new View2());

        mPlaceHolderView.setTranslationY(this.getResources().getDisplayMetrics().heightPixels);
        mPlaceHolderView.animate().translationY(0)
                .setInterpolator(new DecelerateInterpolator(3.f))
                .setDuration(700)
                .start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnifeLite.unbind(this);
    }
}
