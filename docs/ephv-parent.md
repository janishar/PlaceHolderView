---
id: ephv-parent
title: Parent View Definition
sidebar_label: Parent View Definition
---

## View Definition

Example:
```java
@Parent
@SingleTop
@Layout(R.layout.feed_heading)
public class HeadingView {

    @View(R.id.heading_txt)
    TextView headingTxt;

    @View(R.id.toggle_icon)
    ImageView toggleIcon;

    @Toggle(R.id.toggle_view)
    LinearLayout toggleView;

    @ParentPosition
    int mParentPosition;

    private Context mContext;
    private String mHeading;

    public HeadingView(Context context, String heading) {
        mContext = context;
        mHeading = heading;
    }

    @Resolve
    public void onResolved() {
        toggleIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_white_24dp));
        headingTxt.setText(mHeading);
    }

    @Expand
    public void onExpand() {
        toggleIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_white_24dp));
    }

    @Collapse
    public void onCollapse() {
        toggleIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_white_24dp));
    }
}
```
> Most of the annotations are same as in PlaceHolderView's and their detail can be found in [terminology docs](terminology.md)

## Parent Definition
1. A class is defined as a parent item view through `@Parent` class annotation.

2. A parent item view on expand will collapse other parent item views when annotated with `@SingleTop`. If not provided with this annotation then all the parent item will remain in their expanded state irrespective of whether other parent expand or collapse.

## Toggle View
A view act as a toggle view i.e. clicking on it will expand its parent. A view in the parent's item view is defined as toggle view via `@Toggle` annotation. If not provided then the entire view of parent item view is considered as toggle view.

## Position
A parent can find its position using `@ParentPosition` annotation. 
> `@Position` as defined in PlaceHolderView will give the adapter position and it will also include other parents and their children positions. So, its not meaningful for this class.

## Expand and collapse callback
A parent gets callback when it is expanded or collapsed through `@Expand` and `@Collapse` annotations respectively. They can be used to handle state changed. For Example: we can change the indicator icon.