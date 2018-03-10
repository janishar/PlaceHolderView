---
id: sphv-intro
title: Introduction
sidebar_label: Introduction
---

SwipePlaceHolderView provides support to build stacked view. A famous use case is to build Tinder's card like view.

## Swipe Type
Three different swipe mode is available with SwipePlaceHolderView.

1. **SWIPE_TYPE_DEFAULT**: In this mode the view in the stack can be moved in all direction in the horizontal plane.

2. **SWIPE_TYPE_HORIZONTAL**: In this mode the view in the stack can only be moved in Left-Right direction.

3. **SWIPE_TYPE_VERTICAL**: In this mode the view in the stack can only be moved in Top-Bottom direction.

## Swipe terminology
1. Swipe in means moved out to right or bottom direction.

2. Swipe out means moved out to left or top direction.

## View Stack
1. The views are added into the SwipePlaceHolderView get displayed in a stack of **DEFAULT_DISPLAY_VIEW_COUNT**, which is equal to 20. This can be configured through SwipeViewBuilder.

2. The top view is drawn slightly above the bottom view and the bottom view is smaller in size than the top view.

3. When the card is moving in either swipe in direction or swipe out direction, then a swipe in/out message view is displayed and is toggled while the move direction changes.