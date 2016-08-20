package com.mindorks.placeholderview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by janisharali on 18/08/16.
 */
public class ViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<ViewBinder> mViewBinderList;

    public ViewAdapter() {
        mViewBinderList = new ArrayList<>();
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ViewHolder(view);
    }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mViewBinderList.get(position).bindView(holder.itemView);
    }

    /**
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mViewBinderList.get(position).getLayoutId();
    }

    /**
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mViewBinderList.size();
    }

    /**
     *
     * @param position
     * @throws IndexOutOfBoundsException
     */
    protected void removeView(int position)throws IndexOutOfBoundsException{
        mViewBinderList.get(position).unbind();
        mViewBinderList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     *
     * @param viewResolver
     * @param <T>
     * @throws IndexOutOfBoundsException
     */
    protected <T>void addView(T viewResolver)throws IndexOutOfBoundsException{
        mViewBinderList.add(new ViewBinder(viewResolver));
        notifyItemInserted(mViewBinderList.size() - 1);
    }

    /**
     *
     * @param viewResolver
     * @param <T>
     * @throws IndexOutOfBoundsException
     */
    protected  <T>void removeView(T viewResolver)throws IndexOutOfBoundsException{
        int position = -1;
        for(ViewBinder viewBinder : mViewBinderList){
            if(viewBinder.getResolver() == viewResolver){
                position = mViewBinderList.indexOf(viewBinder);
            }
        }
        if(position != -1){
            mViewBinderList.get(position).unbind();
            mViewBinderList.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     *
     * @param position
     * @param viewResolver
     * @param <T>
     * @throws IndexOutOfBoundsException
     */
    protected <T>void addView(int position, T viewResolver)throws IndexOutOfBoundsException{
        mViewBinderList.add(position, new ViewBinder(viewResolver));
        notifyItemInserted(position);
    }
}
