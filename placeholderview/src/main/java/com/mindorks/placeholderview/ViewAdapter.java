package com.mindorks.placeholderview;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by janisharali on 18/08/16.
 */

public class ViewAdapter<T> extends RecyclerView.Adapter<ViewHolder<T, ViewBinder<T, View>>> {

    private List<ViewBinder<T, View>> mViewBinderList;
    private Context mContext;

    public ViewAdapter(Context context) {
        mContext = context;
        mViewBinderList = new ArrayList<>();
    }

    @Override
    public ViewHolder<T, ViewBinder<T, View>> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ViewHolder<>(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder<T, ViewBinder<T, View>> holder, int position) {
        holder.bind(mViewBinderList.get(position), position);
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder<T, ViewBinder<T, View>> holder) {
        super.onViewAttachedToWindow(holder);
        if (holder.getLayoutPosition() > RecyclerView.NO_POSITION && holder.getLayoutPosition() < mViewBinderList.size()) {
            mViewBinderList.get(holder.getLayoutPosition()).bindAnimation(
                    Utils.getDeviceWidth(mContext),
                    Utils.getDeviceHeight(mContext),
                    holder.itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mViewBinderList.get(position).getLayoutId();
    }

    @Override
    public int getItemCount() {
        return mViewBinderList.size();
    }

    protected void removeView(int position) throws IndexOutOfBoundsException {
        mViewBinderList.get(position).unbind();
        mViewBinderList.remove(position);
        notifyItemRemoved(position);
    }

    protected void addView(T viewResolver) throws IndexOutOfBoundsException {
        mViewBinderList.add(Binding.bindViewResolver(viewResolver));
        notifyItemInserted(mViewBinderList.size() - 1);
    }

    protected void removeView(T viewResolver) throws IndexOutOfBoundsException {
        int position = -1;
        for (ViewBinder viewBinder : mViewBinderList) {
            if (viewBinder.getResolver() == viewResolver) {
                position = mViewBinderList.indexOf(viewBinder);
                break;
            }
        }
        if (position != -1) {
            mViewBinderList.get(position).unbind();
            mViewBinderList.remove(position);
            notifyItemRemoved(position);
        }
    }

    protected void addView(int position, T viewResolver) throws IndexOutOfBoundsException {
        mViewBinderList.add(position, Binding.bindViewResolver(viewResolver));
        notifyItemInserted(position);
    }

    protected void addView(T resolverOld, T resolverNew, boolean after) throws Resources.NotFoundException {
        int position = -1;
        for (ViewBinder viewBinder : mViewBinderList) {
            if (viewBinder.getResolver() == resolverOld) {
                position = mViewBinderList.indexOf(viewBinder);
                break;
            }
        }
        if (position != -1) {
            if (after) position++;
            mViewBinderList.add(position, Binding.bindViewResolver(resolverNew));
            notifyItemInserted(position);
        } else {
            throw new Resources.NotFoundException("Old view don't Exists in the list");
        }
    }

    protected List<ViewBinder<T, View>> getViewBinderList() {
        return mViewBinderList;
    }

    protected Context getContext() {
        return mContext;
    }

    protected int getViewBinderListSize() {
        return mViewBinderList.size();
    }

    protected T getViewResolverAtPosition(int position) throws IndexOutOfBoundsException {
        return mViewBinderList.get(position).getResolver();
    }

    protected int getViewResolverPosition(T resolver) {
        for (int i = 0; i < mViewBinderList.size(); i++) {
            if (mViewBinderList.get(i).getResolver() == resolver) {
                return i;
            }
        }
        return -1;
    }

    protected List<T> getAllViewResolvers() {
        List<T> resolverList = new ArrayList<>();
        for (ViewBinder<T, View> viewBinder : mViewBinderList) {
            resolverList.add(viewBinder.getResolver());
        }
        return resolverList;
    }

    protected void removeAllViewBinders() {
        for (ViewBinder<T, View> viewBinder : mViewBinderList) {
            viewBinder.unbind();
        }
        mViewBinderList.clear();
        notifyDataSetChanged();
    }

    protected void sort(List<T> sortedViewResolverList) {
        final Map<T, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < sortedViewResolverList.size(); i++)
            indexMap.put(sortedViewResolverList.get(i), i);

        Collections.sort(mViewBinderList, new Comparator<ViewBinder<T, View>>() {
            @Override
            public int compare(ViewBinder<T, View> binder1, ViewBinder<T, View> binder2) {
                int index1 = indexMap.get(binder1.getResolver());
                int index2 = indexMap.get(binder2.getResolver());
                if (index1 > index2) return 1;
                if (index1 < index2) return -1;
                return 0;
            }
        });
        notifyDataSetChanged();
    }

    @Override
    public void onViewRecycled(ViewHolder<T, ViewBinder<T, View>> holder) {
        super.onViewRecycled(holder);
        holder.recycle();
    }
}
