---
id: kotlin
title: Kotlin
sidebar_label: Kotlin
---
If you are using PlaceHolderView with Kotlin then you will have to do few changes.

## Kotlin Annotation Processor

1. Add plugin `'kotlin-kapt'` in the app's `build.gradle`

```groovy
apply plugin: 'kotlin-kapt'
```

2. Use gradle dependency:

```groovy
dependencies {
    
    ...
    // RecyclerView dependency is added to override the
    // default 25.4.1 RecyclerView dependency stated by placeholderview
    // since we are using the 27.+ support libraries in the project
    implementation 'com.android.support:recyclerview-v7:27.1.0'
    implementation 'com.mindorks.android:placeholderview:1.0.2'
    kapt 'com.mindorks.android:placeholderview-compiler:1.0.2'
}
```

## Annotations Usage
1. Use `lateinit` for non primitive variables
2. Use `@JvmField` for primitive variables.

Example:

```kotlin
@Layout(R.layout.tinder_card_view)
class TinderCard(private val context: Context,
                 private val profile: Profile,
                 private val cardViewHolderSize: Point,
                 private val callback: Callback) {

    @View(R.id.profileImageView)
    lateinit var profileImageView: ImageView

    @View(R.id.nameAgeTxt)
    lateinit var nameAgeTxt: TextView

    @View(R.id.locationNameTxt)
    lateinit var locationNameTxt: TextView

    @SwipeView
    lateinit var swipeView: android.view.View

    @JvmField
    @Position
    var position: Int = 0;

    @Resolve
    fun onResolved() {
        Glide.with(context).load(profile.imageUrl).bitmapTransform(
                RoundedCornersTransformation(
                        context,
                        Utils.dpToPx(7),
                        0,
                        RoundedCornersTransformation.CornerType.TOP))
                .into(profileImageView)
        nameAgeTxt.text = "${profile.name},  ${profile.age}"
        locationNameTxt.text = profile.location
        swipeView.alpha = 1f
    }

    @Click(R.id.profileImageView)
    fun onClick() {
        Log.d("EVENT", "profileImageView click")
    }

    @SwipeOutDirectional
    fun onSwipeOutDirectional(direction: SwipeDirection) {
        Log.d("DEBUG", "SwipeOutDirectional " + direction.name)
        if (direction.direction == SwipeDirection.TOP.direction) {
            callback.onSwipeUp()
        }
    }

    @SwipeCancelState
    fun onSwipeCancelState() {
        Log.d("DEBUG", "onSwipeCancelState")
        swipeView.alpha = 1f
    }

    @SwipeInDirectional
    fun onSwipeInDirectional(direction: SwipeDirection) {
        Log.d("DEBUG", "SwipeInDirectional " + direction.name)
    }

    @SwipingDirection
    fun onSwipingDirection(direction: SwipeDirection) {
        Log.d("DEBUG", "SwipingDirection " + direction.name)
    }

    @SwipeTouch
    fun onSwipeTouch(xStart: Float, yStart: Float, xCurrent: Float, yCurrent: Float) {

        val cardHolderDiagonalLength =
                sqrt(Math.pow(cardViewHolderSize.x.toDouble(), 2.0)
                        + (Math.pow(cardViewHolderSize.y.toDouble(), 2.0)))
        val distance = sqrt(Math.pow(xCurrent.toDouble() - xStart.toDouble(), 2.0)
                + (Math.pow(yCurrent.toDouble() - yStart, 2.0)))

        val alpha = 1 - distance / cardHolderDiagonalLength

        Log.d("DEBUG", "onSwipeTouch "
                + " xStart : " + xStart
                + " yStart : " + yStart
                + " xCurrent : " + xCurrent
                + " yCurrent : " + yCurrent
                + " distance : " + distance
                + " TotalLength : " + cardHolderDiagonalLength
                + " alpha : " + alpha
        )

        swipeView.alpha = alpha.toFloat();
    }

    interface Callback {
        fun onSwipeUp()
    }
}
```