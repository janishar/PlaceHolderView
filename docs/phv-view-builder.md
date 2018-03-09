---
id: phv-view-builder
title: PlaceHolderViewBuilder
sidebar_label: PlaceHolderViewBuilder
---

## PlaceHolderView configuration
This class defines the various configurable properties of the PlaceHolderView.

> Default: <br/>
Cache size is 10<br/>
Fixed size is false

## Following LayoutManagers are supported
1. LinearLayoutManager
2. GridLayoutManager
3. StaggeredGridLayoutManager

## Example:
```java
placeHolderView.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(
                    context, 
                    LinearLayoutManager.HORIZONTAL, 
                    false));
```