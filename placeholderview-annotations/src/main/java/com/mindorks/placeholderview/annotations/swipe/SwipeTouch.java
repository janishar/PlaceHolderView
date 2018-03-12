package com.mindorks.placeholderview.annotations.swipe;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by janisharali on 09/08/17.
 */

/**
 * This has to be used on method with four float members variables.
 * xStart, yStart, xCurrent, yCurrent
 * xStart: It is the initial touch location x co-ordinate
 * yStart: It is the initial touch location y co-ordinate
 * xCurrent: It is the current touch location x co-ordinate
 * yCurrent: It is the current touch location x co-ordinate
 * distance moved can be calculated as: sqrt(sq(xCurrent - xStart) + sq(yCurrent - yStart))
 * : Example : @SwipeTouch private void onSwipeTouch(
 * float xStart, float yStart, float xCurrent, float yCurrent){//something here}
 * This works only with SwipeDirectionalView
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface SwipeTouch {
}
