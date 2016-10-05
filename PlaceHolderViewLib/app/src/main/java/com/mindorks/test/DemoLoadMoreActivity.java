package com.mindorks.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;
import com.mindorks.placeholderview.InfinitePlaceHolderView;
import com.mindorks.test.feed.HeadingView;
import com.mindorks.test.gallery.ImageTypeBig;
import com.mindorks.test.infinite.InfiniteFeedInfo;
import com.mindorks.test.infinite.ItemView;
import com.mindorks.test.infinite.LoadMoreView;

import java.util.List;


public class DemoLoadMoreActivity extends AppCompatActivity {

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

        List<InfiniteFeedInfo> feedList = Utils.loadInfiniteFeeds(this.getApplicationContext());
        mLoadMoreView.setLoadMoreResolver(new LoadMoreView(mLoadMoreView, feedList));
        Log.d("DEBUG", "LoadMoreView.LOAD_VIEW_SET_COUNT " + LoadMoreView.LOAD_VIEW_SET_COUNT);
        for(int i = 0; i < LoadMoreView.LOAD_VIEW_SET_COUNT; i++){
            mLoadMoreView.addView(new ItemView(this.getApplicationContext(), feedList.get(i)));
        }
    }
}
