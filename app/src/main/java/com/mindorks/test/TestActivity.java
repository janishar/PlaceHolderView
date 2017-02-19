package com.mindorks.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;
import com.mindorks.placeholderview.ExpandablePlaceHolderView;
import com.mindorks.test.expandable.ChildItem;
import com.mindorks.test.expandable.ParentItem;


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
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ParentItem(this.getApplicationContext()))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ParentItem(this.getApplicationContext()))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ParentItem(this.getApplicationContext()))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ParentItem(this.getApplicationContext()))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ParentItem(this.getApplicationContext()))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ParentItem(this.getApplicationContext()))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ParentItem(this.getApplicationContext()))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ParentItem(this.getApplicationContext()))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView));
    }
}
