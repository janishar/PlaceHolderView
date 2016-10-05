package com.mindorks.test;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;
import com.mindorks.placeholderview.InfinitePlaceHolderView;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.test.drawer.DrawerHeader;
import com.mindorks.test.drawer.DrawerMenuItem;
import com.mindorks.test.gallery.ImageTypeBig;
import com.mindorks.test.gallery.ImageTypeSmallPlaceHolder;
import com.mindorks.test.infinite.LoadMoreView;

import java.util.ArrayList;
import java.util.List;


public class LoadMoreActivity extends AppCompatActivity {

    @BindView(R.id.loadMoreView)
    private InfinitePlaceHolderView mLoadMoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more_test);
        ButterKnifeLite.bind(this);
        setupView();
    }

    private void setupView(){

        List<Image> imageList = Utils.loadImages(this.getApplicationContext());
        for (int i = 0; i <  LoadMoreView.LOAD_VIEW_SET_COUNT && imageList.size() > i; i++) {
            mLoadMoreView.addView(new ImageTypeBig(this.getApplicationContext(), mLoadMoreView, imageList.get(i).getUrl()));
        }
        mLoadMoreView.setLoadMoreResolver(new LoadMoreView(mLoadMoreView, imageList));
    }
}
