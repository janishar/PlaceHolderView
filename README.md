# PlaceHolderView
![](https://github.com/janishar/janishar.github.io/blob/master/images/logo-phv.png)

#Simple, Easy and Superfast dynamic view creation for RecyclerView with Awesome animations prebuild!

##Few Implementations
![](https://github.com/janishar/janishar.github.io/blob/master/gifs/vid_drawer.gif)     ![](https://github.com/janishar/janishar.github.io/blob/master/gifs/vid_slides.gif)

![](https://github.com/janishar/janishar.github.io/blob/master/gifs/vid_fade.gif)       ![](https://github.com/janishar/janishar.github.io/blob/master/gifs/vid_scale.gif)

##STEP 1: Define a PlaceHolderView inside XML layout
```java
//PlaceHolderView to wrap around the recycler view in XML
<com.mindorks.placeholderview.PlaceHolderView
        android:id="@+id/galleryView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:scrollbars="vertical"/>

```
##STEP 2: Create item views XML, example: gallery_item_big.xml
```java
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
##STEP 3: Create item class to bind and define view operations
```java
@Animate(Animation.ENTER_LEFT_DESC)
@NonReusable
@Layout(R.layout.gallery_item_big)
public class ImageTypeBig {

    @View(R.id.imageView)
    private ImageView imageView;

    private String mUlr;
    private Context mContext;
    private PlaceHolderView mPlaceHolderView;

    public ImageTypeBig(Context context, PlaceHolderView placeHolderView, String ulr) {
        mContext = context;
        mPlaceHolderView = placeHolderView;
        mUlr = ulr;
    }

    @Resolve
    private void onResolved() {
        Glide.with(mContext).load(mUlr).into(imageView);
    }

    @LongClick(R.id.imageView)
    private void onLongClick(){
        mPlaceHolderView.removeView(this);
    }

}
```

##STEP 4 : Add views to the PlaceHolderView
```java
 
 PlaceHolderView placeHolderView = (PlaceHolderView)findViewById(R.id.placeHolderView);

// (Optional): If customisation is Required then use Builder with the PlaceHolderView
// placeHolderView.getBuilder()
//      .setHasFixedSize(false)
//      .setItemViewCacheSize(10)
//      .setLayoutManager(new GridLayoutManager(this, 3));

  placeHolderView
          .addView(new View1())
          .addView(new View2())
          .addView(new View3())
          .addView(new View1())
          .addView(new View2());
```
##*That's All! Cheers!*

#NOTES:

##Annotations
1. @Layout: Bind the XML layout with the class
2. @View: Bind the variable with the view defined in the above layout
3. @Click: Bind the OnClickListener to the view
4. @LongClick: Binf the long click listerner 
5. @Resolve: Any operation being performed on the view reference defined by @View should be annotated with @Resolve
6. @Animate(Animation.ENTER_LEFT_DESC) : Sets the defined animations in the Animation class on all the item view
7. @NonReusable : Releases the view reference and it should not be used again in the addView() 

##PlaceHolderView
1. getBuilder(): Get builder for the PlaceHolderView to modify the default properties
2. setLayoutManager(layoutManager): Add custom layout manager
3. addView(): Add views to the PlaceHolderView
4. removeView(): Removes the existing view
5. SmoothLinearLayoutManager: This class is bundled with the library, and should be used for those view which have dynamic images. It reduces the screen flikering on bind

##There is no adapter configuration required and the process is straight forward

#Gradle
```java
dependencies {
        compile 'com.mindorks:placeholderview:0.0.1-beta3'
}
```
#Dependency: It depends on the RecyclerView
```java
        com.android.support:recyclerview-v7:23.+
```

