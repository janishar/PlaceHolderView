---
id: sphv-view-builder
title: SwipeViewBuilder
sidebar_label: SwipeViewBuilder
---

This class help configure stack properties of the SwipePlaceHolderView

## Swipe Type
We can set the swipe type as described in [introduction doc](sphv-intro.md).

```java
swipeView.getBuilder()
        .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_VERTICAL);
// or
swipeView.getBuilder()
        .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL);
//or
swipeView.getBuilder()
        .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_DEFAULT);
```

## Set the number of item views to be shown at a time in the stack.

Example: To show 5 item views (if available) together and keep adding next item views below when top item view is removed.
```java
swipeView.getBuilder().setDisplayViewCount(5);
```

## Reverse stack display
```java
swipeView.getBuilder().setDisplayReverse(true);
```

## Set SwipeDecor instance
The configurations through the SwipeDecor is passed into the SwipePlaceHolderView through SwipeViewBuilder. The SwipeDecor docs can be found [here](sphv-view-decor.md).
```java
swipeView.getBuilder()
    .setSwipeDecor(new SwipeDecor()
        .setPaddingTop(20));
```

## Set width and height distance to move for swipe in/out.
The factor is the fraction of the total display width and height. This factor gives the distance that item view must move so that it can be considered as swipe in/out. 
```java
swipeView.getBuilder()
    .setWidthSwipeDistFactor(4) // horizontal distance = display width / 4
    .setHeightSwipeDistFactor(6); // vertical distance = display height / 6
```

## Enable Undo Functionality
We can undo the last step (only the most recent last one step) programmatically. To use this feature it must be enabled through SwipeViewBuilder.
```java
swipeView.getBuilder().setIsUndoEnabled(true);
```
