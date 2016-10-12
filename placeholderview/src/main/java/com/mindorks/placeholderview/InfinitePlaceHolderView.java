package com.mindorks.placeholderview;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.infinite.LoadMore;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by janisharali on 05/10/16.
 */

public class InfinitePlaceHolderView extends PlaceHolderView {

    private boolean mIsLoadingMore = false;
    private boolean mNoMoreToLoad = false;
    private Object mLoadMoreResolver;
    private PlaceHolderView.OnScrollListener mOnScrollListener;

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
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        LayoutManager layoutManager = recyclerView.getLayoutManager();
                        if(layoutManager instanceof LinearLayoutManager) {
                            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                            int totalItemCount = linearLayoutManager.getItemCount();
                            int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                            if (!mIsLoadingMore
                                    && !mNoMoreToLoad
                                    && totalItemCount > 0
                                    && totalItemCount == lastVisibleItem + 1) {
                                mIsLoadingMore = true;
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        addView(mLoadMoreResolver);
                                        bindLoadMore(mLoadMoreResolver);
                                    }
                                });
                            }
                        }
                    }
                };
        addOnScrollListener(mOnScrollListener);
    }

    public <T>void setLoadMoreResolver(T loadMoreResolver) {
        this.mLoadMoreResolver = loadMoreResolver;
        mNoMoreToLoad = false;
        setLoadMoreListener();
    }

    public void noMoreToLoad(){
        mNoMoreToLoad = true;
        removeOnScrollListener(mOnScrollListener);
    }

    public void loadingDone(){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                removeView(mLoadMoreResolver);
                mIsLoadingMore = false;
            }
        });
    }

    private <T>void bindLoadMore(final T resolver){
        for(final Method method : resolver.getClass().getDeclaredMethods()) {
            LoadMore annotation = method.getAnnotation(LoadMore.class);
            if(annotation != null) {
                try {
                    method.setAccessible(true);
                    method.invoke(resolver);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getViewCount() {
        return super.getViewResolverCount() - 1;
    }
}
