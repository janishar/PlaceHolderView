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
 * has to be used on method with SwipeDirection members variables.
 * Example : @SwipeInDirectional private void onSwipeInDirectional(
 * SwipeDirection direction) {//something here}
 * It is called when the card is either right swiped or bottom swiped
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface SwipeInDirectional {
}
