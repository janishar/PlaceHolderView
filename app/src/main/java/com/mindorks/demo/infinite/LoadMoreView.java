package com.mindorks.demo.infinite;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.mindorks.demo.R;
import com.mindorks.placeholderview.InfinitePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.infinite.LoadMore;

import java.util.List;

/**
 * Created by janisharali on 05/10/16.
 */
@Layout(R.layout.load_more_view)
public class LoadMoreView {

    public static final int LOAD_VIEW_SET_COUNT = 6;

    private InfinitePlaceHolderView mLoadMoreView;
    private List<InfiniteFeedInfo> mFeedList;

    public LoadMoreView(InfinitePlaceHolderView loadMoreView, List<InfiniteFeedInfo> feedList) {
        this.mLoadMoreView = loadMoreView;
        this.mFeedList = feedList;
    }

    @LoadMore
    public void onLoadMore() {
        Log.d("DEBUG", "onLoadMore");
        new ForcedWaitedLoading();
    }

    class ForcedWaitedLoading implements Runnable{

        public ForcedWaitedLoading() {
            new Thread(this).start();
        }

        @Override
        public void run() {

            try {
                Thread.currentThread().sleep(2000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    int count = mLoadMoreView.getViewCount();
                    Log.d("DEBUG", "count " + count);
                    for (int i = count - 1;
                         i < (count - 1 + LoadMoreView.LOAD_VIEW_SET_COUNT) && mFeedList.size() > i;
                         i++) {
                        mLoadMoreView.addView(new ItemView(mLoadMoreView.getContext(), mFeedList.get(i)));

                        if(i == mFeedList.size() - 1){
                            mLoadMoreView.noMoreToLoad();
                            break;
                        }
                    }
                    mLoadMoreView.loadingDone();
                }
            });
        }
    }
}
