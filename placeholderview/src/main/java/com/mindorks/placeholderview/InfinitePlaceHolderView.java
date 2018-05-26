package com.mindorks.placeholderview;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by janisharali on 05/10/16.
 */

public class InfinitePlaceHolderView extends PlaceHolderView {

    private boolean mIsLoadingMore = false;
    private boolean mNoMoreToLoad = false;
    private Object mLoadMoreResolver;
    private LoadMoreCallbackBinder mLoadMoreCallbackBinder;
    private PlaceHolderView.OnScrollListener mOnScrollListener;
    private int mPaginationMargin = 0;

    public InfinitePlaceHolderView(Context context) {
        super(context);
    }

    public InfinitePlaceHolderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InfinitePlaceHolderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void setLoadMoreListener() {
        mOnScrollListener =
                new PlaceHolderView.OnScrollListener() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        LayoutManager layoutManager = recyclerView.getLayoutManager();
                        if (layoutManager instanceof LinearLayoutManager) {
                            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                            int totalItemCount = linearLayoutManager.getItemCount();
                            int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                            int visibleItemCount = linearLayoutManager.getChildCount();
                            if (!mIsLoadingMore
                                    && !mNoMoreToLoad
                                    && totalItemCount > 0
                                    && visibleItemCount + firstVisibleItem >= totalItemCount - mPaginationMargin) {
                                mIsLoadingMore = true;
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        addView(mLoadMoreResolver);
                                        mLoadMoreCallbackBinder.bindLoadMore(mLoadMoreResolver);
                                    }
                                });
                            }
                        }
                    }
                };
        addOnScrollListener(mOnScrollListener);
    }

    public <T> void setLoadMoreResolver(T loadMoreResolver) {
        mLoadMoreResolver = loadMoreResolver;
        mLoadMoreCallbackBinder = Binding.bindLoadMoreCallback(loadMoreResolver);
        mNoMoreToLoad = false;
        setLoadMoreListener();
    }

    public void noMoreToLoad() {
        mNoMoreToLoad = true;
        removeOnScrollListener(mOnScrollListener);
    }

    public void loadingDone() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                removeView(mLoadMoreResolver);
                mIsLoadingMore = false;
            }
        });
    }

    public int getViewCount() {
        return super.getViewResolverCount() - 1;
    }

    public void setPaginationMargin(int margin) {
        mPaginationMargin = margin;
    }
}