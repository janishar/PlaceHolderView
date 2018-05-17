package com.mindorks.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;
import com.mindorks.demo.infinite.InfiniteFeedInfo;
import com.mindorks.demo.infinite.ItemView;
import com.mindorks.demo.infinite.LoadMoreView;
import com.mindorks.placeholderview.InfinitePlaceHolderView;

import java.util.Comparator;
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
        mLoadMoreView.setPaginationMargin(5);
        Log.d("DEBUG", "LoadMoreView.LOAD_VIEW_SET_COUNT " + LoadMoreView.LOAD_VIEW_SET_COUNT);
        for(int i = 0; i < LoadMoreView.LOAD_VIEW_SET_COUNT; i++){
            mLoadMoreView.addView(new ItemView(this.getApplicationContext(), feedList.get(i)));
        }

        // Testing the sorting
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadMoreView.sort(new Comparator<Object>() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        if (o1 instanceof ItemView && o2 instanceof ItemView) {
                            ItemView view1 = (ItemView) o1;
                            ItemView view2 = (ItemView) o2;
                            return view1.getInfo().getTitle().compareTo(view2.getInfo().getTitle());
                        }
                        return 0;
                    }
                });
            }
        }, 8000);
    }
}