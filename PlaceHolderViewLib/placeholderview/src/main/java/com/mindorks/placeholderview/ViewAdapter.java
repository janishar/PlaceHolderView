package com.mindorks.placeholderview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by janisharali on 18/08/16.
 */
public class ViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<ViewBinder<ViewResolver>> mViewBinderList;

    public ViewAdapter(List<ViewBinder<ViewResolver>> viewBinderList) {
        mViewBinderList = viewBinderList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mViewBinderList.get(position).bindView(holder.itemView);
        mViewBinderList.get(position).getResolver().onResolved();
    }

    @Override
    public int getItemViewType(int position) {
        return mViewBinderList.get(position).getLayoutId();
    }

    @Override
    public int getItemCount() {
        return mViewBinderList.size();
    }

    protected void removeViewAtPosition(int position){
        mViewBinderList.remove(position);
        notifyItemRemoved(position);
    }

    protected <T extends ViewResolver>void addView(T viewResolver){
        mViewBinderList.add(new ViewBinder<ViewResolver>(viewResolver));
        notifyItemInserted(mViewBinderList.size() - 1);
    }

    protected  <T extends ViewResolver>void removeView(T viewResolver){
        int position = -1;
        for(ViewBinder viewBinder : mViewBinderList){
            if(viewBinder.getResolver() == viewResolver){
                position = mViewBinderList.indexOf(viewBinder);
            }
        }
        if(position != -1){
            mViewBinderList.remove(position);
            notifyItemRemoved(position);
        }
    }

    protected <T extends ViewResolver>void addViewAtPosition(int position, T viewResolver){
        mViewBinderList.add(position, new ViewBinder<ViewResolver>(viewResolver));
        notifyItemInserted(position);
    }

}
