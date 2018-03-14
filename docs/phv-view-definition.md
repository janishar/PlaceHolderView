---
id: phv-view-definition
title: View Definition
sidebar_label: View Definition
---

## Sample view class definition

```java
/*
 * item_gallery_image is defined in the XML 
 * This class gets bind with the item_gallery_image.xml
 */
@NonReusable
@Animate(Animate.CARD_TOP_IN_DESC)
@Layout(R.layout.item_gallery_image)
public class GalleryImage {

    @View(R.id.card_view)
    CardView cardView;

    @View(R.id.image_view)
    ImageView imageView;

    @Position
    int position;

    private Context context;
    private String url;    

    public GalleryImage(Context context, String url) {
        this.context = context;
        this.url = url;
    }

    /*
     * This method is called when the view is rendered
     * onResolved method could be named anything, Example: onAttach
     */
    @Resolve
    public void onResolved() {
        // do something here
        // example: load imageView with url image
    }

    /*
     * This method is called when the view holder is recycled 
     * and used to display view for the next data set
     */
    @Recycle
    public void onRecycled(){
        // do something here
        // Example: clear some references used by earlier rendering
    }

    /*
     * This method is called when the view with id image_view is clicked.
     * onImageViewClick method could be named anything.
     */
    @Click(R.id.image_view)
    public void onImageViewClick(){
        // do something
    }

    @LongClick(R.id.image_view)
    public void onImageViewLongClick() {
        // do something
    }

}
```

> The various annotations used here can be found in [terminology docs](terminology.md)

**item_gallery_image.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="10dp">
    <android.support.v7.widget.CardView
        android:id="@+id/card_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:cardCornerRadius="6dp"
        app:cardElevation="6dp">
        <ImageView
            android:id="@+id/image_view"
            android:layout_width="match_parent"
            android:layout_height="250dp"
            android:scaleType="centerCrop"
            android:src="@android:color/holo_orange_dark"/>
    </android.support.v7.widget.CardView>
</LinearLayout>
```

**PlaceHolderView use in activity/fragment view xml**

```xml
<com.mindorks.placeholderview.PlaceHolderView
        android:id="@+id/phv_gallery"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```

**PlaceHolderView view addition in activity/fragment class**

```java
PlaceHolderView phvGallery = (PlaceHolderView)findViewById(R.id.phv_gallery);

// (Optional): If customization is Required then use Builder with the PlaceHolderView
phvGallery.getBuilder()
    .setHasFixedSize(false)
    .setItemViewCacheSize(10)
    .setLayoutManager(new GridLayoutManager(this, 3));

phvGallery
    .addView(new GalleryImage(getApplicationContext(), url1));
    .addView(new GalleryImage(getApplicationContext(), url2));
    .addView(new GalleryImage(getApplicationContext(), url3));
    .addView(new GalleryImage(getApplicationContext(), url4));
```