---
id: iphv-intro
title: Introduction
sidebar_label: Introduction
---

This class provides support for load more view and callback when the last item is scrolled by the user. The callback can be used to fetch and show more data from server or other data source into the item view list.

## Load more item view class definition
All the properties of item view for PlaceHoldeView is available in load more view. [Refer docs](terminology.md)

> The callback to show more views after all items has been scrolled is provided through `@LoadMore` annotation.
Use this callback to fetch more data from the server or some data store.

Example:
```java
@NonReusable
@Layout(R.layout.load_more_view)
public class LoadMoreView {

    private InfinitePlaceHolderView feedListView;

    public LoadMoreView(InfinitePlaceHolderView feedListView) {
        this.feedListView = feedListView;
    }

    @LoadMore
    public void onLoadMore() {
        /*
         * fetch more data and 
         * then call feedListView.loadingDone();
         * if all the data is fetch 
         * then call feedListView.noMoreToLoad();
         */
    }
}

```

## Define load more view in XML
Example:
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:gravity="center"
    android:padding="5dp">
    <ProgressBar
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>
</LinearLayout>
```



