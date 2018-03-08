---
author: Janishar Ali
authorURL: https://twitter.com/janisharali
authorFBID: 100001054239147
title: Gallery Example
---

This example demonstrate creation of a list of images with animation using PlaceHolderView. For the sake of this example we will be placing our images in the drawable folder.
<!--truncate-->

```xml
<!--PlaceHolderView to wrap around the recycler view in XML-->
<com.mindorks.placeholderview.PlaceHolderView
        android:id="@+id/galleryView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="10dp">
    <android.support.v7.widget.CardView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:cardCornerRadius="6dp"
        app:cardElevation="6dp">
        <ImageView
            android:id="@+id/imageView"
            android:layout_width="match_parent"
            android:layout_height="250dp"
            android:scaleType="centerCrop"
            android:src="@android:color/holo_orange_dark"/>
    </android.support.v7.widget.CardView>
</LinearLayout>
```
```java
@Animate(Animation.ENTER_LEFT_DESC)
@NonReusable
@Layout(R.layout.gallery_item_big)
public class ImageTypeBig {

    @View(R.id.imageView)
    private ImageView imageView;

    private String mUrl;
    private Context mContext;
    private PlaceHolderView mPlaceHolderView;

    public ImageTypeBig(Context context, PlaceHolderView placeHolderView, String url) {
        mContext = context;
        mPlaceHolderView = placeHolderView;
        mUrl = url;
    }

    @Resolve
    private void onResolved() {
        Glide.with(mContext).load(mUrl).into(imageView);
    }

    @LongClick(R.id.imageView)
    private void onLongClick(){
        mPlaceHolderView.removeView(this);
    }

}
```
```java
PlaceHolderView mGalleryView = (PlaceHolderView)findViewById(R.id.galleryView);

// (Optional): If customisation is Required then use Builder with the PlaceHolderView
// placeHolderView.getBuilder()
//      .setHasFixedSize(false)
//      .setItemViewCacheSize(10)
//      .setLayoutManager(new GridLayoutManager(this, 3));

  mGalleryView
        .addView(new ImageTypeBig(this.getApplicationContext(), mGalleryView, url1));
        .addView(new ImageTypeBig(this.getApplicationContext(), mGalleryView, url2));
        .addView(new ImageTypeBig(this.getApplicationContext(), mGalleryView, url3));
        .addView(new ImageTypeBig(this.getApplicationContext(), mGalleryView, url4));
```