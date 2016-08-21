# PlaceHolderView
###No ListView : No GridView : No RecyclerView : Only one View for all : PlaceHolderView

#Simple, Easy and superfast dynamic view creation for RecyclerView with awesome animations

![](https://github.com/janishar/janishar.github.io/blob/master/gifs/vid_drawer.gif)![](https://github.com/janishar/janishar.github.io/blob/master/gifs/vid_slides.gif)
![](https://github.com/janishar/janishar.github.io/blob/master/gifs/vid_fade.gif)![](https://github.com/janishar/janishar.github.io/blob/master/gifs/vid_scale.gif)

##STEP 1: Define a PlaceHolderView inside any XML layout
```java
//PlaceHolderView to wrap around the recycler view in XML
<com.mindorks.placeholderview.PlaceHolderView
        android:id="@+id/placeHolderView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

```
##STEP 2: Create item views XML, example : item_view_1.xml
```java
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">
    <ImageView
        android:layout_width="match_parent"
        android:layout_height="150dp"
        android:src="@android:color/holo_orange_dark"
        android:padding="10dp"/>
    <TextView
        android:id="@+id/txt"
        android:layout_gravity="center"
        android:textSize="16dp"
        android:textColor="@android:color/white"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
    <Button
        android:id="@+id/btn"
        android:layout_gravity="bottom|center"
        android:layout_marginBottom="10dp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="CLICK"/>
</FrameLayout>
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

##STEP 4: Build the PlaceHolderView in the main view
```java
 PlaceHolderView placeHolderView = (PlaceHolderView)findViewById(R.id.placeHolderView);
 placeHolderView.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10);
//                .setLayoutManager(new GridLayoutManager(this, 3));

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

##PlaceHolderView can be nested by using PlaceHolderView inside the item_view_1.xml

#Gradle
```java
compile 'com.mindorks:placeholderview:0.0.1-beta3'
```
#Dependency: It depends on the RecyclerView
```java
compile 'com.android.support:recyclerview-v7:23.+'
```

