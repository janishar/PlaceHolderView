---
id: phv-view-operations
title: View Operations
sidebar_label: View Operations
---

## Add item view
Add item view to the PlaceHolderView.
```java
placeHolderView.addView(new SampleItemView());
```

Add item view at provided position. It will shift the other views below.
```java
placeHolderView.addView(2, new SampleItemView());
```

Add an item view before an existing item view.
```java
placeHolderView.addViewBefore(sampleItemView1, new SampleItemView());
```

Add an item view after an existing item view.
```java
placeHolderView.addViewAfter(sampleItemView1, new SampleItemView());
```

## Remove an added item view
Remove the view added in the PlaceHolderView using the item view reference.
```java
placeHolderView.removeView(view);
```

Remove view from a position.
```java
placeHolderView.removeView(1);
```

Remove all the added views.
```java
placeHolderView.removeAllViews();
```

## Refresh
Refresh the entire list.
```java
placeHolderView.refresh();
```

Refresh a paticular item view with its reference.
```java
placeHolderView.refreshView(itemView);
```

Refresh a paticular item view with its position.
```java
placeHolderView.refreshView(2);
```

## Total views count
Get total added item views count.
```java
placeHolderView.getViewResolverCount();
```

## Get added item view
Get an item view added at a position.
```java
placeHolderView.getViewResolverAtPosition(2);
```

Get all the item views added.
```java
placeHolderView.getAllViewResolvers();
```