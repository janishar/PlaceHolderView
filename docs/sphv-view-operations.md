---
id: sphv-view-operations
title: View Operations
sidebar_label: View Operations
---

## Get Builder
```java
swipeView.getBuilder();
```

## Add View
```java
swipeView.addView(new ItemView());
```

## Programmatic Swipe
We can programmatically make swipes apart from manual swiping.

### Swipe a particular item view.
```java
swipeView.doSwipe(itemView, true); // swipe in
swipeView.doSwipe(itemView, false); // swipe out
```

### Swipe the top most item view.
```java
swipeView.doSwipe(true); // swipe in
swipeView.doSwipe(false); // swipe out
```

## Lock/Unlock item views from swiping.
```java
swipeView.lockViews(); // swiping blocked
swipeView.unlockViews(); // swiping unblocked
```

## Put back the item view when it is swiped.
This feature blocks the item views from being swiped in/out. The user is able to move the card but when the card is moved away then it is put back onto the stack with animation.

```java
swipeView.activatePutBack();
swipeView.deactivatePutBack();
```

## Disable/Enable touch swipes
We can disable manual swipes and only allow programmatic swiping.
```java
swipeView.disableTouchSwipe();
swipeView.enableTouchSwipe();
```

## Undo last swipe
This is active when we enable Undo Functionality. Refer [SwipeViewBuilder doc](sphv-view-builder.md)
```java
swipeView.undoLastSwipe();
```

## Item Remove Listener
This gives the callback when an item view is removed. It can be used to fetch more data and add into the SwipePlaceHolderView when count reach a specific value.
```java
swipeView.addItemRemoveListener(new ItemRemovedListener() {
        @Override
        public void onItemRemoved(int count) {
            // do something
        }
    });
               
```

## Get all added item views
```java
swipeView.getAllResolvers(); // gives the list of object
```

## Remove all added item views
```java
swipeView.removeAllViews();
```