package com.mindorks.placeholderview.annotations.swipe;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by janisharali on 18/08/16.
 */

/**
 * This annotation is used with SwipeDirectionalView and
 * has to be used on method with SwipeDirection members variables
 * Example : @SwipeOutDirectional private void onSwipedOut(
 * SwipeDirection direction) {//something here}
 * It is called when the card is either left swiped or top swiped
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface SwipeOutDirectional {
}
