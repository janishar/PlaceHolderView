---
id: introduction
title: Introduction
sidebar_label: Introduction
---
PlaceHolderView is build on top of RecyclerView. There are two verison available for use.
1. Old version (branch 1.x): Uses Java reflection
2. New version (branch 2.x): Uses Annotation Processing.

**How To Use New Version(2.x) with annotation processing**:

Gradle dependency:

```groovy
dependencies {
    ...
    compile 'com.mindorks.android:placeholderview:1.0.0'
    annotationProcessor 'com.mindorks.android:placeholderview-compiler:1.0.0'

    // RecyclerView dependency is added to override the
    // default 25.4.1 RecyclerView dependency stated by placeholderview
    // if we are using the support libraries other than 25.x in the project
    compile 'com.android.support:recyclerview-v7:<current-version>' // example: 27.1.0
}
```

**To migrate from 1.x to 2.x see [Migration](migration.md).**

## This library provides 5 different type of views

1. **PlaceHolderView**<br/>
It is build on top of RecyclerView and abstracts most of the boiler plate. it provides APIs through annotations.

2. **InfinitePlaceHolderView**<br/>
It is build on PlaceHolderView and adds the functionality of handelling load more view when the user has scrolled to the bottom of the list.

3. **ExpandablePlaceHolderView**<br/>
It is build on PlaceHolderView and creates ExpandableListView with parent-child relation.

4. **SwipePlaceHolderView**<br/>
It is not build on RecyclerView. It a ground up implementation. We can create a variety of card stack views using this class. It provides APIs that could easily build Tinder alike cards.

5. **DirectionalPlaceHolderView**<br/>
It is build on top of SwipePlaceHolderView. It provides call back for swipe directions and touch events.