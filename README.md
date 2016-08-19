# PlaceHolderView
##No ListView : No GridView : No RecyclerView : Only one View for all : *PlaceHolderView*

##This library tends to remove the boiler plate code involved in creating RecyclerView. In the process making it extremely simple and easy to create any type of dynamic view with just few lines of code. No adapter is required to build and manipulate the view, rather annotation based model in a very simple form is sufficient. It makes the code modular and lean, yet much more powerful.

##STEP 1: Define a PlaceHolderView inside any XML layout
```java
//PlaceHolderView to wrap around the recycler view in XML
<com.mindorks.placeholderview.PlaceHolderView
        android:id="@+id/placeHolderView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

```
##STEP 2: Create item views XML's, example : item_view_1.xml
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
@Layout(R.layout.item_view_1)
public class View1{

    @View(R.id.txt)
    public TextView txt;

    @Resolve
    public void onResolved() {
        txt.setText(String.valueOf(System.currentTimeMillis() / 1000));
    }

    @Click(R.id.btn)
    public void onClick(){
        txt.setText(String.valueOf(System.currentTimeMillis() / 1000));
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
          .addView(new View3(this))
          .addView(new View1())
          .addView(new View2());
```
##*That's All! Cheers!*

#NOTES:

##Anotations
1. @Layout: Bind the XML layout with the class
2. @View: Bind the variable with the view defined in the above layout
3. @Click: Bind the OnClickListener to the view
4. @Resolve: Any operation being performed on the view reference defined by @View should be annotated with @Resolve

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
compile 'com.mindorks:placeholderview:0.0.1-beta2'
```
#Dependency: It depends on the RecyclerView
```java
compile 'com.android.support:recyclerview-v7:23.+'
```

