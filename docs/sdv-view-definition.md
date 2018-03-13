---
id: sdv-view-definition
title:  View Definition
sidebar_label: View Definition
---
## Example

```java
@Layout(R.layout.tinder_card_view)
public class TinderCard {

    @View(R.id.profileImageView)
    ImageView profileImageView;

    @View(R.id.nameAgeTxt)
    TextView nameAgeTxt;

    @View(R.id.locationNameTxt)
    TextView locationNameTxt;

    @SwipeView
    android.view.View mSwipeView;

    private Profile mProfile;
    private Context mContext;
    private Point mCardViewHolderSize;
    private Callback mCallback;

    public TinderCard(Context context, Profile profile, Point cardViewHolderSize, Callback callback) {
        mContext = context;
        mProfile = profile;
        mCardViewHolderSize = cardViewHolderSize;
        mCallback = callback;
    }

    @Resolve
    public void onResolved() {
        Glide.with(mContext).load(mProfile.getImageUrl())
                .bitmapTransform(new RoundedCornersTransformation(mContext, Utils.dpToPx(7), 0,
                        RoundedCornersTransformation.CornerType.TOP))
                .into(profileImageView);
        nameAgeTxt.setText(mProfile.getName() + ", " + mProfile.getAge());
        locationNameTxt.setText(mProfile.getLocation());
        mSwipeView.setAlpha(1);
    }

    @Click(R.id.profileImageView)
    public void onClick() {
        Log.d("EVENT", "profileImageView click");
    }

    @SwipeOutDirectional
    public void onSwipeOutDirectional(SwipeDirection direction) {
        Log.d("DEBUG", "SwipeOutDirectional " + direction.name());
        if (direction.getDirection() == SwipeDirection.TOP.getDirection()) {
            mCallback.onSwipeUp();
        }
    }

    @SwipeCancelState
    public void onSwipeCancelState() {
        Log.d("DEBUG", "onSwipeCancelState");
        mSwipeView.setAlpha(1);
    }

    @SwipeInDirectional
    public void onSwipeInDirectional(SwipeDirection direction) {
        Log.d("DEBUG", "SwipeInDirectional " + direction.name());
    }

    @SwipingDirection
    public void onSwipingDirection(SwipeDirection direction) {
        Log.d("DEBUG", "SwipingDirection " + direction.name());
    }

    @SwipeTouch
    public void onSwipeTouch(float xStart, float yStart, float xCurrent, float yCurrent) {

        float cardHolderDiagonalLength =
                (float) Math.sqrt(Math.pow(mCardViewHolderSize.x, 2) + (Math.pow(mCardViewHolderSize.y, 2)));
        float distance = (float) Math.sqrt(Math.pow(xCurrent - xStart, 2) + (Math.pow(yCurrent - yStart, 2)));

        float alpha = 1 - distance / cardHolderDiagonalLength;

        Log.d("DEBUG", "onSwipeTouch "
                + " xStart : " + xStart
                + " yStart : " + yStart
                + " xCurrent : " + xCurrent
                + " yCurrent : " + yCurrent
                + " distance : " + distance
                + " TotalLength : " + cardHolderDiagonalLength
                + " alpha : " + alpha
        );

        ((FrameLayout)mSwipeView).setAlpha(alpha);
    }

    interface Callback {
        void onSwipeUp();
    }
}
```

## Callbacks
> Most of the callbacks and configurations are same as for SwipePlaceHolderView. Refer [docs](sphv-into.md)

For receiving the directional callbacks for `SwipeDirectionalView` these annotations are provided.

1. `@SwipeOutDirectional`: It is called when the item view is either left swiped or top swiped.
```java
@SwipeOutDirectional
private void onSwipeOutDirectional(SwipeDirection direction) {
    Log.d("DEBUG", "SwipeOutDirectional " + direction.name());
}
```
2. `@SwipeInDirectional`: It is called when the item view is either right swiped or bottom swiped.
```java
@SwipeInDirectional
private void onSwipeInDirectional(SwipeDirection direction) {
    Log.d("DEBUG", "SwipeInDirectional " + direction.name());
}
```
3. `@SwipingDirection`: It is called when the item view is dragging in some direction.
```java
@SwipingDirection
private void onSwipingDirection(SwipeDirection direction) {
    Log.d("DEBUG", "SwipingDirection " + direction.name());
}
```

4. `SwipeTouch`: It provides the starting and current touch point's coordinates on the item view while it is getting dragged.
```java
@SwipeTouch
private void onSwipeTouch(float xStart, float yStart, float xCurrent, float yCurrent) {
    Log.d("DEBUG", "onSwipeTouch "
            + " xStart : " + xStart
            + " yStart : " + yStart
            + " xCurrent : " + xCurrent
            + " yCurrent : " + yCurrent
            + " distance : "
            + Math.sqrt(Math.pow(xCurrent - xStart, 2) + (Math.pow(yCurrent - yStart, 2)))
    );
}
```

