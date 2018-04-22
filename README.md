<div>
    <p align="center"><img src="https://janishar.github.io/PlaceHolderView/img/logo.svg" width="300" ></p>
    <p align="center"><h1 align="center">PlaceHolderView</br> An advance view for lists and stacks</h1></p>
</div>

[![Download 2.x](https://api.bintray.com/packages/janishar/mindorks/placeholderview-2/images/download.svg) ](https://bintray.com/janishar/mindorks/placeholderview-2/_latestVersion)
[![Mindorks](https://img.shields.io/badge/mindorks-opensource-blue.svg)](https://mindorks.com/open-source-projects)
[![Mindorks Community](https://img.shields.io/badge/join-community-blue.svg)](https://mindorks.com/join-community)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# Some Implementations
![](https://janishar.github.io/gifs/vid_tinder.gif)  ![](https://janishar.github.io/gifs/feed_vid.gif)  ![](https://janishar.github.io/gifs/vid_slides.gif)  ![](https://janishar.github.io/gifs/vid_tinder_v2.gif)  ![](https://janishar.github.io/gifs/vid_drawer.gif)  ![](https://janishar.github.io/gifs/infinite_vid.gif)    

-----
# Documentation
You can find the PlaceHolderView documentation [here](http://janishar.com/PlaceHolderView/docs/introduction.html) which has extended usage instructions and other useful information. 

<a href="http://janishar.com/PlaceHolderView" target="_blank"><img src="https://janishar.github.io/images/get-started-button.jpg" width="150" height="45"/></a>

-----
# About PlaceHolderView
Some of the views in PlaceHolderView library is build on top of RecyclerView and rest are written in its own.

**All the annotations are processed during build time to generate the binding classes**

There are two versions available for use.
1. Old version (branch 1.x): Uses Java reflection
2. **New version (branch 2.x): Uses Annotation Processing.**

## This library provides 5 different type of views

1. **PlaceHolderView**<br/>
It is build on top of RecyclerView and abstracts most of the boiler plate. It provides APIs through annotations.

2. **InfinitePlaceHolderView**<br/>
It is build on PlaceHolderView and adds the functionality of handling load more views when the user has scrolled to the bottom of the list.

3. **ExpandablePlaceHolderView**<br/>
It is build on PlaceHolderView and creates ExpandableListView with parent-child relation.

4. **SwipePlaceHolderView**<br/>
It is not build on RecyclerView. Its a ground up implementation. We can create a variety of card stack views using this class. It provides APIs that could easily build Tinder like cards.

5. **SwipeDirectionalView**<br/>
It is build on top of SwipePlaceHolderView. It provides callbacks for swipe directions and touch events.

# How to use newer version (2.x branch) with annotation processing

## Gradle dependency:
**Java**
```groovy
dependencies {
    ...
    compile 'com.mindorks.android:placeholderview:1.0.3'
    annotationProcessor 'com.mindorks.android:placeholderview-compiler:1.0.3'

    // RecyclerView dependency is added to override the
    // default 25.4.1 RecyclerView dependency used by placeholderview.
    // If you are using the support libraries other than 25.x in the project
    compile 'com.android.support:recyclerview-v7:<current-version>' // example: 27.1.0
}
```
**Kotlin**
```groovy
dependencies {
    ...
    // RecyclerView dependency is added to override the
    // default 25.4.1 RecyclerView dependency stated by placeholderview
    // since we are using the 27.+ support libraries in the project
    implementation 'com.android.support:recyclerview-v7:27.1.0'
    implementation 'com.mindorks.android:placeholderview:1.0.3'
    kapt 'com.mindorks.android:placeholderview-compiler:1.0.3'
}
```
## For use with Kotlin refer to [Kotlin docs](http://janishar.com/PlaceHolderView/docs/kotlin.html).

# Migration: 
It is super easy to migrate to 2.x branch library version.

## To migrate from 1.x to 2.x see [Migration doc here](http://janishar.com/PlaceHolderView/docs/migration.html).

---
## How to Use older version (1.x branch)
If you want to use older version of PlaceHoldeView that used reflection, then you can use below dependecies. 

### Gradle dependency:
```groovy
dependencies {
    compile 'com.mindorks:placeholderview:0.7.3'
}
```
### Dependency: It depends on the RecyclerView
Add below lines in your app's build.gradle if recyclerview above v7:25 is being used
```groovy
    // NOTE: change the version of recyclerview same as the your project's support library version
    com.android.support:recyclerview-v7:25.+
```

### Proguard Note:
If you are using proguard, then add this rule in proguard-project.txt
```groovy
  -keepattributes *Annotation*
  -keepclassmembers class ** {
    @com.mindorks.placeholderview.annotations.** <methods>;
  }
```

# If this library helps you in anyway, show your love :heart: by putting a :star: on this project :v:

# TO-DOs
- [X] Update/Refresh the View already shown in the stack.
- [X] Callback when a card comes on top of the stack.
- [ ] Provide Undo for the entire swipe history.
- [X] Provide Sort for the PlaceHolderView.

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

