package com.mindorks.test.infinite;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.mindorks.placeholderview.InfinitePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.infinite.LoadMore;
import com.mindorks.test.Image;
import com.mindorks.test.R;
import com.mindorks.test.gallery.ImageTypeBig;

import java.util.List;

/**
 * Created by janisharali on 05/10/16.
 */
@Layout(R.layout.load_more_view)
public class LoadMoreView {

    public static final int LOAD_VIEW_SET_COUNT = 5;

    private InfinitePlaceHolderView mLoadMoreView;
    private List<Image> mImageList;

    public LoadMoreView(InfinitePlaceHolderView loadMoreView, List<Image> imageList) {
        this.mLoadMoreView = loadMoreView;
        this.mImageList = imageList;
    }

    @LoadMore
    private void onLoadMore(){
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
                    for (int i = count - 1; i < (count - 1 + LoadMoreView.LOAD_VIEW_SET_COUNT) && mImageList.size() > i; i++) {
                        mLoadMoreView.addView(new ImageTypeBig(mLoadMoreView.getContext(), mLoadMoreView, mImageList.get(i).getUrl()));
                        if(i == mImageList.size() - 1){
                            mLoadMoreView.noMoreToLoad();
                        }
                    }
                    mLoadMoreView.loadingDone();
                }
            });
        }
    }
}
