---
id: phv-view-sort
title: Sort Views
sidebar_label: Sort Views
---

**We can apply sort to the item views added to the PlaceHolderView. This will refresh the item views with new sort order**

Example:

If the item views added to the `PlaceHolderView` is of class `ItemView`, then we have to supply a `Comparator` that defines the comparison between the itemView objects. Here the itemView's title string is being compared for the sorting.

> NOTE: Check item1 and item2 are of type ItemView for this example. This is required because any type of item view class can be added into the PlaceHolderView

```java
placeHolderView.sort(new Comparator<Object>() {
    @Override
    public int compare(Object item1, Object item2) {
        if (item1 instanceof ItemView && item2 instanceof ItemView) {
            ItemView view1 = (ItemView) item1;
            ItemView view2 = (ItemView) item2;
            return view1.getTitle().compareTo(view2.getTitle());
        }
        return 0;
    }
});
```
