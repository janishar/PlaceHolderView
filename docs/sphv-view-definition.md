---
id: sphv-view-definition
title: View Definition
sidebar_label: View Definition
---

## Sample view definition

```java
@NonReusable
@Layout(R.layout.tinder_card_view)
public class TinderCard {

    @View(R.id.profileImageView)
    ImageView profileImageView;

    @View(R.id.nameAgeTxt)
    TextView nameAgeTxt;

    @View(R.id.locationNameTxt)
    TextView locationNameTxt;

    @SwipeView
    android.view.View view;

    @Resolve
    public void onResolve() {
        nameAgeTxt.setText("Name " + this.hashCode());
    }

    @SwipeHead
    public void onSwipeHead() {
        profileImageView.setBackgroundColor(Color.BLUE);
        Log.d("DEBUG", "onSwipeHead");
    }

    @SwipeOut
    public void onSwipedOut() {
        Log.d("DEBUG", "onSwipedOut");
    }

    @SwipeCancelState
    public void onSwipeCancelState() {
        Log.d("DEBUG", "onSwipeCancelState");
    }

    @SwipeIn
    public void onSwipeIn() {
        Log.d("DEBUG", "onSwipedIn");
    }

    @SwipeInState
    public void onSwipeInState() {
        Log.d("DEBUG", "onSwipeInState");
    }

    @SwipeOutState
    public void onSwipeOutState() {
        Log.d("DEBUG", "onSwipeOutState");
    }

    @Click(R.id.profileImageView)
    public void onProfileImageViewClick() {
        Log.d("DEBUG", "onProfileImageViewClick");
    }

    @LongClick(R.id.profileImageView)
    public void onProfileImageViewLongClick() {
        Log.d("DEBUG", "onProfileImageViewLongClick");
    }
}
```

This sample item view definition contains all the callbacks available for this type.

> `@NonReusable`, `@layout`, `@View`, `@Resolve`, ` @Click`, and `@LongClick` does the same thing as for all the other views. They can be found in docs: [terminology](terminology.md)

## Get in instance of the container view
`@SwipeView` can be used to get the instance of the container view that wraps the provided item view layout.
> It provides `android.view.View` type object and internally it is the instance of `SwipePlaceHolderView.FrameView`.

## Swipe direction Callbacks
1. `@SwipeOut`: It is called when the item view is moved to the left/top side.

2. `@SwipeIn`: It is called when the item view is moved to the right/bottom side.

3. `@SwipeInState`: It is called repeatedly as the the item view moves in the left/top direction. 

4. `@SwipeOutState`: It is called repeatedly as the the item view moves in the right/bottom direction.

5. `@SwipeCancelState`: It is called when the item view is not swiped either in or out. It is restored into the stack.
 
## Top of stack callback
> `@Resolve` is called when the card is inflated and put into the stack. So when the number of displayed card in the stack is more than one and the top card is swiped then the lower card becomes the head of the stack but `@Resolve` is not called.

**When a card takes the head of the stack then `@SwipeHead` is called.**

It can be used to do layout changes or animation when the card takes the top position.

