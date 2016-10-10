package com.mindorks.placeholderview;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by janisharali on 18/08/16.
 */

public class ViewAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    private List<ViewBinder<T, View>> mViewBinderList;
    private Context mContext;

    /**
     *
     * @param context
     */
    public ViewAdapter(Context context) {
        mContext = context;
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
        mViewBinderList.get(position).bindView(holder.itemView, position);
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if(holder.getLayoutPosition() > RecyclerView.NO_POSITION && holder.getLayoutPosition() < mViewBinderList.size()) {
            mViewBinderList.get(holder.getLayoutPosition()).bindAnimation(
                    Utils.getDeviceWidth(mContext),
                    Utils.getDeviceHeight(mContext),
                    holder.itemView);
        }
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
     * @throws IndexOutOfBoundsException
     */
    protected void addView(T viewResolver)throws IndexOutOfBoundsException{
        mViewBinderList.add(new ViewBinder<>(viewResolver));
        notifyItemInserted(mViewBinderList.size() - 1);
    }

    /**
     *
     * @param viewResolver
     * @throws IndexOutOfBoundsException
     */
    protected  void removeView(T viewResolver)throws IndexOutOfBoundsException{
        int position = -1;
        for(ViewBinder viewBinder : mViewBinderList){
            if(viewBinder.getResolver() == viewResolver){
                position = mViewBinderList.indexOf(viewBinder);
                break;
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
     * @throws IndexOutOfBoundsException
     */
    protected void addView(int position, T viewResolver)throws IndexOutOfBoundsException{
        mViewBinderList.add(position, new ViewBinder<>(viewResolver));
        notifyItemInserted(position);
    }

    /**
     *
     * @param resolverOld
     * @param resolverNew
     * @param after
     * @throws Resources.NotFoundException
     */
    protected void addView(T resolverOld, T resolverNew, boolean after)throws Resources.NotFoundException{
        int position = -1;
        for(ViewBinder viewBinder : mViewBinderList){
            if(viewBinder.getResolver() == resolverOld){
                position = mViewBinderList.indexOf(viewBinder);
                break;
            }
        }
        if(position != -1){
            if(after)position++;
            mViewBinderList.add(position, new ViewBinder<>(resolverNew));
            notifyItemInserted(position);
        }else{
            throw new Resources.NotFoundException("Old view don't Exists in the list");
        }
    }

    /**
     *
     * @return
     */
    protected List<ViewBinder<T, View>> getViewBinderList() {
        return mViewBinderList;
    }

    /**
     *
     * @return
     */
    protected Context getContext() {
        return mContext;
    }

    /**
     *
     * @return
     */
    protected  int getViewBinderListSize(){
        return mViewBinderList.size();
    }

    /**
     *
     * @param position
     * @return
     * @throws IndexOutOfBoundsException
     */
    protected  T getViewResolverAtPosition(int position) throws IndexOutOfBoundsException{
        return mViewBinderList.get(position).getResolver();
    }

    protected  int getViewResolverPosition(T resolver){
        for(int i = 0; i < mViewBinderList.size(); i++){
            if(mViewBinderList.get(i).getResolver() == resolver){
                return i;
            }
        }
        return -1;
    }

    /**
     *
     * @return
     */
    protected  List<T> getAllViewResolvers() {
        List<T> resolverList = new ArrayList<>();
        for(ViewBinder<T, View> viewBinder : mViewBinderList){
            resolverList.add(viewBinder.getResolver());
        }
        return resolverList;
    }

    protected void removeAllViewBinders(){
        for(ViewBinder<T, View> viewBinder : mViewBinderList){
            viewBinder.unbind();
        }
        mViewBinderList.clear();
    }
}
