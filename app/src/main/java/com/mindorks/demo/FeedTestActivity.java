package com.mindorks.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;
import com.mindorks.demo.feed.HeadingView;
import com.mindorks.demo.feed.InfoView;
import com.mindorks.demo.feed.data.Feed;
import com.mindorks.demo.feed.data.Info;
import com.mindorks.placeholderview.ExpandablePlaceHolderView;


public class FeedTestActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    private Toolbar mToolbar;

    @BindView(R.id.expandableView)
    private ExpandablePlaceHolderView mExpandableView;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_test);
        ButterKnifeLite.bind(this);

        mContext = this.getApplicationContext();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        for(Feed feed : Utils.loadFeeds(this.getApplicationContext())){
            mExpandableView.addView(new HeadingView(mContext, feed.getHeading()));
            for(Info info : feed.getInfoList()){
                mExpandableView.addView(new InfoView(mContext, info));
            }
        }
    }
}
