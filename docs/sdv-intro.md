---
id: sdv-intro
title: Introduction
sidebar_label: Introduction
---
It is build on top of SwipePlaceHolderView. This View provides more refines directional callbacks for the swipes. All the properties of SwipePlaceHolderView is available to SwipeDirectionalView.

## View Design
![](https://janishar.github.io/images/swipe-direction-diagram.png) 

The swipe distance in SwipeDirectionalView for swipe in/out is calculated from the initial touch point as oppose to the device width and height fraction in the SwipePlaceHolderView.

## Swipe Thresholds
```xml
<com.mindorks.placeholderview.SwipeDirectionalView
    android:id="@+id/swipeDirectionalView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

```java
private SwipeDirectionalView mSwipeView;
...

mSwipeView.getBuilder()
                .setSwipeVerticalThreshold(Utils.dpToPx(50))
                .setSwipeHorizontalThreshold(Utils.dpToPx(50))
                ...
```

1. `setSwipeVerticalThreshold`: It defines the vertical area from the initial touch location.
2. `setSwipeHorizontalThreshold`: It defines the horizontal area from the initial touch location.

If the swipe take place within these areas then it will be considered for TOP, BOTTOM, LEFT or RIGHT direction swipe else the direction will be RIGHT_TOP, RIGHT_BOTTOM, LEFT_TOP or LEFT_BOTTOM

## SwipeDirection
These are the pre defined directions for the SwipeDirection.
```java
TOP(0),
LEFT(1),
BOTTOM(2),
RIGHT(3),
RIGHT_TOP(4),
RIGHT_BOTTOM(5),
LEFT_TOP(6),
LEFT_BOTTOM(7);
```