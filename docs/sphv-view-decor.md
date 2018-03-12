---
id: sphv-view-decor
title: SwipeDecor
sidebar_label: SwipeDecor
---

This class defines the various configurations and settings related to item view used in SwipePlaceHolderView.

## Usage
Provide the new instance of SwipeDecor with custom settings to the SwipeViewBuilder instance available in the SwipePlaceHolderView.

```java
swipePlaceHolderView.getBuilder()
        .setSwipeDecor(new SwipeDecor()
            .setPaddingTop(20)
            .setSwipeMaxChangeAngle(2f)
            // other settings
```
## Settings and Configurations

### Top/Bottom Padding
<div>
    <span>
        <img src="https://janishar.github.io/PlaceHolderView/img/swipe-diagrams/padding-top-20-plus.png" width="300">
    </span>
    <span>
        <img src="https://janishar.github.io/PlaceHolderView/img/swipe-diagrams/padding-top-20-minus.png" width="300" >
    </span>
 </div>

```java
new SwipeDecor().setPaddingTop(20)// in dp
new SwipeDecor().setPaddingTop(-20)// in dp
```
Positive value set item views arranged top to bottom and negative value set item views arranges bottom to top.

### Left/Right Padding
<div>
    <span>
        <img src="https://janishar.github.io/PlaceHolderView/img/swipe-diagrams/padding-left-20-plus.png" width="300">
    </span>
    <span>
        <img src="https://janishar.github.io/PlaceHolderView/img/swipe-diagrams/padding-left-20-minus.png" width="300" >
    </span>
 </div>
 
```java
new SwipeDecor().setPaddingLeft(20)
new SwipeDecor().setPaddingLeft(-20)
```

Positive value set item views arranged left to right and negative value set item views arranges right to left.

### Margin
```java
new SwipeDecor().setMarginTop(200) // Top margin
new SwipeDecor().setMarginLeft(200) // Left margin
```
Set the margin of the item views with respect to the SwipePlaceHolderView display area.

### Relative Scale
<div>
    <span>
        <img src="https://janishar.github.io/PlaceHolderView/img/swipe-diagrams/padding-100-rs-0.1.png" width="300">
    </span>
    <span>
        <img src="https://janishar.github.io/PlaceHolderView/img/swipe-diagrams/padding-100-rs-0.01.png" width="300" >
    </span>
 </div>

```java
new SwipeDecor()
    .setPaddingTop(100)
    .setRelativeScale(0.1f) // higher scaling
// Versus
new SwipeDecor()
    .setPaddingTop(100)
    .setRelativeScale(0.01f) // lower scaling
```

Relative scaling defines the size of item views placed below with respect to item view place above.

### Swipe in/out state display view
<div>
    <span>
        <img src="https://janishar.github.io/PlaceHolderView/img/swipe-diagrams/swipe-out-msg.png" width="300" >
    </span>
    <span>
        <img src="https://janishar.github.io/PlaceHolderView/img/swipe-diagrams/swipe-in-msg.png" width="300">
    </span>
 </div>

We can provide layouts that shows up when an item view is in the state of either swipe in or swipe out.

```java
new SwipeDecor()
    .setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg_view)
    .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg_view)
```

Example: Swipe in layout R.layout.tinder_swipe_in_msg_view

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="350dp"
    android:layout_height="425dp">
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="32sp"
        android:textStyle="bold"
        android:layout_margin="40dp"
        android:textColor="@android:color/holo_green_light"
        android:text="Accept"/>
</LinearLayout>
```
