package com.mindorks.placeholderview;

/**
 * Created by janisharali on 09/08/17.
 */

public enum SwipeDirection {

    TOP(0),
    LEFT(1),
    BOTTOM(2),
    RIGHT(3),
    RIGHT_TOP(4),
    RIGHT_BOTTOM(5),
    LEFT_TOP(6),
    LEFT_BOTTOM(7);

    private final int mDirection;

    SwipeDirection(int direction) {
        mDirection = direction;
    }

    public int getDirection() {
        return mDirection;
    }
}