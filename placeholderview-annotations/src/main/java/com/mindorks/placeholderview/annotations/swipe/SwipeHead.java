package com.mindorks.placeholderview.annotations.swipe;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by janisharali on 18/08/16.
 */

/**
 * This annotation should be used on method without any members variables
 * Example : @SwipeHead private void onSwipeHead(){//something here}
 * It is called when the card comes on top of the stack
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface SwipeHead {
}
