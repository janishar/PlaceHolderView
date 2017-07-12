**PlaceHolderView**
<!---Change the img.shields.io badge with https://api.bintray.com/packages/janishar/mindorks/placeholderview/images/download.svg once the bintray badge is fixed --->
[![Download](https://img.shields.io/badge/placeholderview-0.6.2-brightgreen.svg)](https://bintray.com/janishar/mindorks/placeholderview/_latestVersion)
[![Mindorks](https://img.shields.io/badge/mindorks-placeholderview-blue.svg)](https://mindorks.com/open-source-projects)
[![Open Source Love](https://badges.frapsoft.com/os/v1/open-source.svg?v=102)](https://opensource.org/licenses/Apache-2.0)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

![](https://janishar.github.io/images/logo-phv.png)

###### Simple, Easy and Superfast dynamic view creation with Awesome animations prebuilt!

## Few Implementations
![](https://janishar.github.io/gifs/vid_tinder.gif)  ![](https://janishar.github.io/gifs/feed_vid.gif)  ![](https://janishar.github.io/gifs/vid_slides.gif)

![](https://janishar.github.io/gifs/vid_fade.gif)  ![](https://janishar.github.io/gifs/vid_drawer.gif)  ![](https://janishar.github.io/gifs/infinite_vid.gif)    

## STEP 1: Define a PlaceHolderView inside XML layout
```xml
//PlaceHolderView to wrap around the recycler view in XML
<com.mindorks.placeholderview.PlaceHolderView
        android:id="@+id/galleryView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

```
## STEP 2: Create item views XML, example: gallery_item_big.xml
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
## STEP 3: Create item class to bind and define view operations
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

## STEP 4 : Add views to the PlaceHolderView
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
## **That's All! Cheers!** :beer:

## Classes OverView

# `PlaceHolderView`
1. `getBuilder()`: Get builder for the PlaceHolderView to modify the default properties
2. `setLayoutManager(layoutManager)`: Add custom layout manager
3. `addView()`: Add views to the PlaceHolderView
4. `removeView()`: Removes the existing view
5. `SmoothLinearLayoutManager`: This class is bundled with the library, and should be used for those view which has dynamic heavy contents. It reduces the screen flickering on bind

## Annotations
1. `@Layout`: Bind the XML layout with the class.
2. `@View`: Bind the variable with the view defined in the above layout.
3. `@Click`: Bind the `OnClickListener` to a view.
4. `@LongClick`: Bind the long click listener to a view.
5. `@Resolve`: Any operation being performed on the view reference defined by `@View` should be annotated with this.
6. `@Animate(Animation.ENTER_LEFT_DESC)`: Sets the defined animations in the Animation class on this item view.
7. `@NonReusable`: Releases the view reference along with all the attached references in the view object. This view object should not be used again in the `addView()`.
8. `@Position`: This annotation binds an int variable to the position of the item view after the item view is attached to the display.

## Important
1. `PlaceHolderView` will recycle the viewItems and will try to use similar/same viewtype viewItem to populate the data of the current viewItem. So, the method annotated with `@Resolve` will be called everytime the viewItem is attached to the window. Meaning if you don't explicitly manage to populate the viewItem in method annotated with `@Resolve` then that viewItem may show history of the reused viewItem.
2. Try to instantiate any class in the constructor rather than method annotated with `@Resolve`.
3. If the itemView contains PlaceHolderView/ListViews and item are being adding through method annotated with `@Resolve`, then first make the list empty. This is required because duplicate viewitems may get added if the recycled view contains PlaceHolderView/ListViews of other itemView. For PlaceHolderView: call removeAllViews() before adding views in method annotated with `@Resolve`.

# `ExpandablePlaceHolderView`
#### This class is build upon the `PlaceHolderView` and implements all the features of `ExpandableListView` but with much power and lot easier

## Annotations(`ExpandablePlaceHolderView`)
1. `@Parent`: Defines the class to be used as the parent in the expandable list.
2. `@SingleTop`: Makes only one parent remain in expanded state.
3. `@Collapse`: Bind a method of the parent class to listen to the collapse event.
4. `@Expand`: Bind a method of the parent class to listen to the Expand event.
5. `@ParentPosition`: Bind an int variable to update with relative position among parents.
6. `@ChildPosition`: Bind an int variable to update with relative position among children of a parent.
7. `@Toggle`: Bind a view to be used as a trigger for expanding or collapsing a parent view. If not provided then the parent view becomes a toggle by default.

## There is no adapter configuration required

# `SwipePlaceHolderView`
#### This class is bundled with view that can create beautiful card stacks like Tinder, LinkdIn and Card Games. This class provides methods to customize the behavior of the stack, gesture inputs and animations.

## `SwipeDecor`
#### This class contains view behaviour settings and animation controls for SwipePlaceHolderView.

## Builder(`SwipePlaceHolderView`)
#### The stack structure and type configurations are done using builder class

## Annotations(`SwipePlaceHolderView`)
1. `@SwipeIn`: It binds a method and calls it when a view is swiped in/accepted
2. `@SwipeOut`: It binds a method and calls it when a view is swiped out/rejected.
3. `@SwipeCancelState`: It binds a method and calls it when a card is put back in the stack/canceled.
4. `@SwipeInState`: It binds a method and pings it till a card is moving in the direction of swiping in/accepted
5. `@SwipeOutState`: It binds a method and pings it till a card is moving in the direction of swiping out/rejected
6. `@SwipeView`: It binds the android.view.View reference to the tinder view 

# `InfinitePlaceHolderView`
#### This class provides a mechanism to load the data in bunches for infinite loading. If the scroll reaches the last item, it calls for LoadMore and show the defined loadmore indicator view. When new data it added the indication is removed. To get the callback for loadmore create a class like that used in PlaceHolderView and define a method with `@LoadMore` annotation. This method should be used to do network calls and to add new fetched views.

# Methods(`InfinitePlaceHolderView`)
1. `setLoadMoreResolver(T loadMoreResolver)`: This method is used to add a class to be used as indicator for load more. The class can define a view with a progessbar to reflect loading. The class has to be defined in the same way as a class that is used in the PlaceHolderView. See above for PlaceHolderView example.
2. `loadingDone()`: After the view has beed added from the new fetch, call this method to remove the loading indicator view.
3. `noMoreToLoad()`: When all the data has been fetched, call this method to stop the infinite loading.

# Annotations(`InfinitePlaceHolderView`)
1. `@LoadMore`: This annotation calls a method of the class provided in `setLoadMoreResolver` with callback when load more is required, i.e. when last item has been seen.

###### The Full Documentation is in the process of writing. For any query post it in the discussion or [janishar.ali@gmail.com](mailto:janishar.ali@gmail.com)

# Gradle
```groovy
dependencies {
    compile 'com.mindorks:placeholderview:0.6.2'
}
```
# Dependency: It depends on the RecyclerView
```groovy
    com.android.support:recyclerview-v7:25.+
```

# Proguard Note:
### If you are using proguard, then add this rule in proguard-project.txt
```groovy
  -keepattributes *Annotation*
  -keepclassmembers class ** {
    @com.mindorks.placeholderview.annotations.** <methods>;
  }
```

### If this library helps you in anyway, show your love :heart: by putting a :star: on this project :v:

# Examples
[Android Navigation Drawer](https://medium.com/@janishar.ali/navigation-drawer-android-example-8dfe38c66f59#.vmlw4zb00)

[Android Beginner Image Gallery](https://medium.com/@janishar.ali/android-beginner-image-gallery-example-da73a596f4d5#.p42z8w83o)

[Android Advance Image Gallery](https://medium.com/@janishar.ali/android-advance-image-gallery-example-3ec6ddf85ed9#.6n8ouof9k)

[Android Expandable News Feed](https://medium.com/@janishar.ali/android-expandable-news-feed-example-4b4544e1fe7e#.3n9k18x2s)

[Android Tinder Swipe](https://medium.com/@janishar.ali/android-tinder-swipe-view-example-3eca9b0d4794#.413dgor3o)

[Android Infinite List with Load More](https://medium.com/@janishar.ali/android-infinite-list-with-load-more-example-3749ea7bc33#.e3juocg6s)

# Recent Library: [`JPost`](https://github.com/janishar/JPost)
#### `JPost` is a pubsub library based on massages over a channel. It's very efficient and much powerful than other pubsub libraries. It prevents memory leak and increases code control. Also, provide a mechanism to run code asynchronously.

# License

```
   Copyright (C) 2016 Janishar Ali Anwar

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License

```

