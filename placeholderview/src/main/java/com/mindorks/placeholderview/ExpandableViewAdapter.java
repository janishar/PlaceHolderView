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

public class ExpandableViewAdapter<T> extends ViewAdapter<T>
        implements ExpandableViewBinder.ExpansionCallback<T>{

    private ExpandableViewBinder<T, View> mParentBinder;

    /**
     *
     * @param context
     */
    public ExpandableViewAdapter(Context context) {
        super(context);
    }

    /**
     *
     * @param viewResolver
     * @throws IndexOutOfBoundsException
     */
    @Override
    protected void addView(T viewResolver) throws IndexOutOfBoundsException {
        ExpandableViewBinder<T, View> expandableViewBinder = new ExpandableViewBinder<>(viewResolver);
        if(expandableViewBinder.bindViewType()){
            expandableViewBinder.setCallback(this);
            mParentBinder = expandableViewBinder;
            getViewBinderList().add(mParentBinder);
            mParentBinder.bindParentPosition(getParentPosition(mParentBinder));
            notifyItemInserted(getViewBinderList().size() - 1);
        }
        else{
            if(mParentBinder == null){
                mParentBinder = getLastParentBinder();
            }
            if(mParentBinder != null){
                mParentBinder.getChildList().add(expandableViewBinder);
                expandableViewBinder.setParentViewBinder(mParentBinder);
                expandableViewBinder.bindParentPosition(getParentPosition(mParentBinder));
                expandableViewBinder.bindChildPosition(getChildPosition(mParentBinder, expandableViewBinder));
            }
        }
    }

    /**
     *
     * @param parentResolver
     * @param childResolver
     * @throws Resources.NotFoundException
     */
    protected void addChildView(T parentResolver, T childResolver) throws Resources.NotFoundException {
        ExpandableViewBinder<T, View> parentBinder = getBinderForResolver(parentResolver);
        if(parentBinder != null && parentBinder.isParent()){
            ExpandableViewBinder<T, View> childViewBinder = new ExpandableViewBinder<>(childResolver);
            parentBinder.getChildList().add(childViewBinder);
            if(parentBinder.isExpanded()){
                int position;
                position = getViewBinderList().indexOf(parentBinder) + parentBinder.getChildList().size();
                getViewBinderList().add(position, childViewBinder);
                childViewBinder.setParentViewBinder(parentBinder);
                childViewBinder.bindParentPosition(getParentPosition(parentBinder));
                childViewBinder.bindChildPosition(getChildPosition(parentBinder, childViewBinder));
                notifyItemInserted(position);
            }
        }
    }

    /**
     *
     * @param parentPosition
     * @param childResolver
     * @throws Resources.NotFoundException
     */
    protected void addChildView(int parentPosition, T childResolver) throws Resources.NotFoundException {
        for(ViewBinder<T, View> viewBinder : getViewBinderList()){
            ExpandableViewBinder<T, View> parentBinder = (ExpandableViewBinder<T, View>)viewBinder;
            if(parentBinder.isParent() && parentBinder.getParentPosition() == parentPosition){
                ExpandableViewBinder<T, View> childViewBinder = new ExpandableViewBinder<>(childResolver);
                parentBinder.getChildList().add(childViewBinder);
                if(parentBinder.isExpanded()){
                    int position;
                    position = getViewBinderList().indexOf(parentBinder) + parentBinder.getChildList().size();
                    getViewBinderList().add(position, childViewBinder);
                    childViewBinder.setParentViewBinder(parentBinder);
                    childViewBinder.bindParentPosition(getParentPosition(parentBinder));
                    childViewBinder.bindChildPosition(getChildPosition(parentBinder, childViewBinder));
                    notifyItemInserted(position);
                }
                break;
            }
        }
    }

    /**
     *
     * @param position
     * @param viewResolver
     * @throws IndexOutOfBoundsException
     */
    @Override
    protected void addView(int position, T viewResolver) throws IndexOutOfBoundsException {}

    /**
     *
     * @param resolverOld
     * @param resolverNew
     * @param after
     * @throws Resources.NotFoundException
     */
    @Override
    protected void addView(T resolverOld, T resolverNew, boolean after) throws Resources.NotFoundException {}

    /**
     *
     * @param viewResolver
     * @throws IndexOutOfBoundsException
     */
    @Override
    protected void removeView(T viewResolver) throws IndexOutOfBoundsException {
        ExpandableViewBinder<T, View> expandableViewBinder = getBinderForResolver(viewResolver);
        if(expandableViewBinder != null){
            if(expandableViewBinder.isParent()){
                onCollapse(expandableViewBinder);
            }else{
                expandableViewBinder.getParentViewBinder().getChildList().remove(expandableViewBinder);
            }
        }
        super.removeView(viewResolver);
    }

    /**
     *
     * @param position
     * @throws IndexOutOfBoundsException
     */
    @Override
    protected void removeView(int position) throws IndexOutOfBoundsException {
        ExpandableViewBinder<T, View> expandableViewBinder = (ExpandableViewBinder<T, View>)getViewBinderList().get(position);
        if(expandableViewBinder != null){
            if(expandableViewBinder.isParent()){
                onCollapse(expandableViewBinder);
            }else{
                expandableViewBinder.getParentViewBinder().getChildList().remove(expandableViewBinder);
            }
        }
        super.removeView(position);
    }

    /**
     *
     * @param parentBinder
     */
    @Override
    public void onExpand(ExpandableViewBinder<T, View> parentBinder) {
        int position = getViewBinderList().indexOf(parentBinder) + 1;
        for(ExpandableViewBinder<T, View> viewBinder : parentBinder.getChildList()) {
            getViewBinderList().add(position, viewBinder);
            notifyItemInserted(position);
        }
        if(parentBinder.isSingleTop())
            collapseOthers(parentBinder);
    }

    /**
     *
     * @param parentBinder
     */
    @Override
    public void onCollapse(ExpandableViewBinder<T, View> parentBinder) {
        for(ExpandableViewBinder<T, View> viewBinder : parentBinder.getChildList()){
            super.removeView(viewBinder.getResolver());
        }
    }

    /**
     *
     * @param parentBinder
     */
    private void collapseOthers(ExpandableViewBinder<T, View> parentBinder){
        List<ExpandableViewBinder<T,View>> expandableViewBinderList = new ArrayList<>();
        for (ViewBinder viewBinder : getViewBinderList()){
            ExpandableViewBinder expandableViewBinder = (ExpandableViewBinder)viewBinder;
            if(expandableViewBinder != parentBinder){
                expandableViewBinderList.add(expandableViewBinder);
            }
        }
        for(ExpandableViewBinder viewBinder : expandableViewBinderList){
            viewBinder.collapse();
        }
    }

    /**
     *
     * @param parentViewBinder
     * @return
     */
    private int getParentPosition(ExpandableViewBinder<T, View> parentViewBinder){
        int position = -1;
        for (ViewBinder viewBinder : getViewBinderList()) {
            ExpandableViewBinder<T, View> expandableViewBinder = (ExpandableViewBinder<T, View>) viewBinder;
            if(expandableViewBinder.isParent()) {
                if (parentViewBinder == expandableViewBinder) {
                    return ++position;
                }
                position++;
            }
        }
        return position;
    }

    /**
     *
     * @param parentViewBinder
     * @param childViewBinder
     * @return
     */
    private int getChildPosition(ExpandableViewBinder<T, View> parentViewBinder, ExpandableViewBinder<T, View> childViewBinder){
        return parentViewBinder.getChildList().indexOf(childViewBinder);
    }

    /**
     *
     * @return
     */
    private ExpandableViewBinder<T, View> getLastParentBinder() {
        for (int i = getViewBinderList().size() - 1; i >= 0; i--){
            ExpandableViewBinder<T, View> expandableViewBinder = (ExpandableViewBinder<T, View>) getViewBinderList().get(i);
            if (expandableViewBinder.isParent()) {
                return expandableViewBinder;
            }
        }
        return null;
    }

    /**
     *
     * @param viewResolver
     * @return
     */
    protected ExpandableViewBinder<T, View> getBinderForResolver(T viewResolver){
        for(ViewBinder<T, View> viewBinder : getViewBinderList()){
            ExpandableViewBinder<T, View> expandableViewBinder = (ExpandableViewBinder<T, View>)viewBinder;
            if(expandableViewBinder.getResolver() == viewResolver) {
                return expandableViewBinder;
            }
        }
        return null;
    }
}
