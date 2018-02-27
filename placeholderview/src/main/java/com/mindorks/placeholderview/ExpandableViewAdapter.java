package com.mindorks.placeholderview;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by janisharali on 18/08/16.
 */

public class ExpandableViewAdapter<T> extends ViewAdapter<T>
        implements ExpandableViewBinder.ExpansionCallback<T, View> {

    private ExpandableViewBinder<T, View> mParentBinder;

    public ExpandableViewAdapter(Context context) {
        super(context);
    }

    @Override
    protected void addView(T viewResolver) throws IndexOutOfBoundsException {
        ExpandableViewBinder<T, View> expandableViewBinder = Binding.bindExpandableViewResolver(viewResolver);
        if (expandableViewBinder.isParent()) {
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

    protected void addChildView(T parentResolver, T childResolver) throws Resources.NotFoundException {
        ExpandableViewBinder<T, View> parentBinder = getBinderForResolver(parentResolver);
        if(parentBinder != null && parentBinder.isParent()){
            ExpandableViewBinder<T, View> childViewBinder = Binding.bindExpandableViewResolver(childResolver);
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

    protected void addChildView(int parentPosition, T childResolver) throws Resources.NotFoundException {
        for(ViewBinder<T, View> viewBinder : getViewBinderList()){
            ExpandableViewBinder<T, View> parentBinder = (ExpandableViewBinder<T, View>)viewBinder;
            if(parentBinder.isParent() && parentBinder.getParentPosition() == parentPosition){
                ExpandableViewBinder<T, View> childViewBinder = Binding.bindExpandableViewResolver(childResolver);
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

    @Override
    protected void addView(int position, T viewResolver) throws IndexOutOfBoundsException {}

    @Override
    protected void addView(T resolverOld, T resolverNew, boolean after) throws Resources.NotFoundException {}

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

    @Override
    public void onExpand(ExpandableViewBinder<T, View> parentBinder) {
        int position = getViewBinderList().indexOf(parentBinder) + 1;
        for(ExpandableViewBinder<T, View> viewBinder : parentBinder.getChildList()) {
            getViewBinderList().add(position, viewBinder);
            notifyItemInserted(position);
            position++;
        }
        if(parentBinder.isSingleTop())
            collapseOthers(parentBinder);
    }

    @Override
    public void onCollapse(ExpandableViewBinder<T, View> parentBinder) {
        for(ExpandableViewBinder<T, View> viewBinder : parentBinder.getChildList()){
            super.removeView(viewBinder.getResolver());
        }
    }

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

    private int getChildPosition(ExpandableViewBinder<T, View> parentViewBinder, ExpandableViewBinder<T, View> childViewBinder){
        return parentViewBinder.getChildList().indexOf(childViewBinder);
    }

    private ExpandableViewBinder<T, View> getLastParentBinder() {
        for (int i = getViewBinderList().size() - 1; i >= 0; i--){
            ExpandableViewBinder<T, View> expandableViewBinder = (ExpandableViewBinder<T, View>) getViewBinderList().get(i);
            if (expandableViewBinder.isParent()) {
                return expandableViewBinder;
            }
        }
        return null;
    }

    protected ExpandableViewBinder<T, View> getBinderForResolver(T viewResolver){
        for(ViewBinder<T, View> viewBinder : getViewBinderList()){
            ExpandableViewBinder<T, View> expandableViewBinder = (ExpandableViewBinder<T, View>)viewBinder;
            if(expandableViewBinder.getResolver() == viewResolver) {
                return expandableViewBinder;
            }
        }
        return null;
    }

    protected ExpandableViewBinder<T, View> getParentBinderAtPosition(int position) {

        ExpandableViewBinder<T, View> parentBinder = null;

        for (ViewBinder<T, View> viewBinder : getViewBinderList()) {

            ExpandableViewBinder<T, View> expandableViewBinder =
                    (ExpandableViewBinder<T, View>) viewBinder;

            if (position >= 0
                    && expandableViewBinder.isParent()
                    && expandableViewBinder.getParentPosition() == position) {
                parentBinder = expandableViewBinder;
                break;
            }
        }
        return parentBinder;
    }
}
