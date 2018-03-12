package com.mindorks.placeholderview.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by janisharali on 18/08/16.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Animate {

    int ENTER_LEFT_DESC = 1;
    int ENTER_LEFT_ASC = 2;
    int ENTER_RIGHT_DESC = 3;
    int ENTER_RIGHT_ASC = 4;

    int ENTER_TOP_DESC = 5;
    int ENTER_TOP_ASC = 6;
    int ENTER_BOTTOM_DESC = 7;
    int ENTER_BOTTOM_ASC = 8;

    int SCALE_UP_DESC = 9;
    int SCALE_UP_ASC = 10;
    int SCALE_DOWN_DESC = 11;
    int SCALE_DOWN_ASC = 12;

    int FADE_IN_DESC = 13;
    int FADE_IN_ASC = 14;

    int CARD_LEFT_IN_DESC = 15;
    int CARD_LEFT_IN_ASC = 16;
    int CARD_RIGHT_IN_DESC = 17;
    int CARD_RIGHT_IN_ASC = 18;

    int CARD_TOP_IN_DESC = 19;
    int CARD_TOP_IN_ASC = 20;
    int CARD_BOTTOM_IN_DESC = 21;
    int CARD_BOTTOM_IN_ASC = 22;

    int ANIM_DURATION = 300;
    float ANIM_INTERPOLATION_FACTOR = 0.3f;

    float ANIM_SCALE_FACTOR_MIN = 0.5f;
    float ANIM_SCALE_FACTOR_ORIGINAL = 1.0f;
    float ANIM_SCALE_FACTOR_MAX = 1.25f;

    float ANIM_ALPHA_MIN = 0f;
    float ANIM_ALPHA_MAX = 1.0f;

    int value() default ENTER_LEFT_DESC;

    int duration() default ANIM_DURATION;

    float factor() default ANIM_INTERPOLATION_FACTOR;
}
