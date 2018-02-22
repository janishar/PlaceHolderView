package com.mindorks.demo;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;
import com.mindorks.butterknifelite.annotations.OnClick;
import com.mindorks.demo.expandable.ChildItem;
import com.mindorks.demo.expandable.ParentItem;
import com.mindorks.placeholderview.ExpandablePlaceHolderView;


public class ExpandableActivity extends AppCompatActivity {

    ParentItem parentItem;
    @BindView(R.id.toolbar)
    private Toolbar mToolbar;
    @BindView(R.id.expandableView)
    private ExpandablePlaceHolderView mExpandableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnifeLite.bind(this);

        parentItem = new ParentItem(this.getApplicationContext());

        mExpandableView
                .addView(new ParentItem(this.getApplicationContext()))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(new ChildItem(mExpandableView))
                .addView(parentItem)
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

//    @OnClick(R.id.collapse)
//    void onCollapse() {
//        try {
//            mExpandableView.collapse(0);
//        }catch (Resources.NotFoundException e){
//            e.printStackTrace();
//        }
//    }
//
//    @OnClick(R.id.expand)
//    void onExpand() {
//        try {
//            mExpandableView.expand(0);
//        }catch (Resources.NotFoundException e){
//            e.printStackTrace();
//        }
//    }

    @OnClick(R.id.collapse)
    void onCollapse() {
        try {
            mExpandableView.collapse(parentItem);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.expand)
    void onExpand() {
        try {
            mExpandableView.expand(parentItem);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }
}
