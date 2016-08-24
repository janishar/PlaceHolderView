package com.mindorks.test;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;
import com.mindorks.placeholderview.ExpandablePlaceHolderView;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.test.drawer.DrawerHeader;
import com.mindorks.test.drawer.DrawerMenuItem;
import com.mindorks.test.expandable.ChildItem;
import com.mindorks.test.expandable.ParentItem;
import com.mindorks.test.gallery.ImageTypeBig;
import com.mindorks.test.gallery.ImageTypeSmallPlaceHolder;

import java.util.ArrayList;
import java.util.List;


public class TestActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    private Toolbar mToolbar;

    @BindView(R.id.expandableView)
    private ExpandablePlaceHolderView mExpandableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnifeLite.bind(this);

        mExpandableView
                .addView(new ParentItem(this.getApplicationContext()))
                .addView(new ChildItem())
                .addView(new ChildItem())
                .addView(new ChildItem())
                .addView(new ChildItem())
                .addView(new ChildItem())
                .addView(new ParentItem(this.getApplicationContext()))
                .addView(new ChildItem())
                .addView(new ChildItem())
                .addView(new ChildItem())
                .addView(new ChildItem())
                .addView(new ParentItem(this.getApplicationContext()))
                .addView(new ChildItem())
                .addView(new ChildItem())
                .addView(new ChildItem())
                .addView(new ChildItem())
                .addView(new ParentItem(this.getApplicationContext()))
                .addView(new ChildItem())
                .addView(new ChildItem())
                .addView(new ChildItem())
                .addView(new ChildItem());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnifeLite.unbind(this);
    }
}
