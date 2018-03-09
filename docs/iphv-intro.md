---
id: iphv-intro
title: Load More View
sidebar_label: Load More View
---

InfinitePlaceHolderView class provides support for load more view and callback when the last item is scrolled by the user. The callback can be used to fetch and show more data from server or other data source into the item view list.

## Load more item view class definition
All the properties of item view for PlaceHoldeView is available in load more view. [Refer docs](terminology.md)

> The callback to show more views after all items has been scrolled is provided through `@LoadMore` annotation.
Use this callback to fetch more data from the server or some data store.

Example:
```java
@Layout(R.layout.load_more_view)
public class LoadMoreView {

    private Callback callback;

    public LoadMoreView(Callback callback) {
        this.callback = callback;
    }

    @LoadMore
    public void onLoadMore() {
         callback.onShowMore();
    }

    public interface Callback{
        void onShowMore();
    }
}
```
## Attach load more view to InfinitePlaceHolderView
```java
public class FeedActivity extends AppCompatActivity {

    private InfinitePlaceHolderView feedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        feedView = findViewById(R.id.iphv_feed);
        ...
        feedView.setLoadMoreResolver(new LoadMoreView(new LoadMoreView.Callback{
            @Override
            void onShowMore(){
                //fetch more data and then call
                feedView.loadingDone();

                //if all the data is fetch then call 
                feedView.noMoreToLoad();
            }
        }));
        ...
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