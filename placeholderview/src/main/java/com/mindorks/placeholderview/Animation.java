package com.mindorks.placeholderview;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by janisharali on 20/08/16.
 */
public class Animation {

    public static final int ENTER_LEFT_DESC = 1;
    public static final int ENTER_LEFT_ASC = 2;
    public static final int ENTER_RIGHT_DESC = 3;
    public static final int ENTER_RIGHT_ASC = 4;

    public static final int ENTER_TOP_DESC = 5;
    public static final int ENTER_TOP_ASC = 6;
    public static final int ENTER_BOTTOM_DESC = 7;
    public static final int ENTER_BOTTOM_ASC = 8;

    public static final int SCALE_UP_DESC = 9;
    public static final int SCALE_UP_ASC = 10;
    public static final int SCALE_DOWN_DESC = 11;
    public static final int SCALE_DOWN_ASC = 12;

    public static final int FADE_IN_DESC = 13;
    public static final int FADE_IN_ASC = 14;

    public static final int CARD_LEFT_IN_DESC = 15;
    public static final int CARD_LEFT_IN_ASC = 16;
    public static final int CARD_RIGHT_IN_DESC = 17;
    public static final int CARD_RIGHT_IN_ASC = 18;

    public static final int CARD_TOP_IN_DESC = 19;
    public static final int CARD_TOP_IN_ASC = 20;
    public static final int CARD_BOTTOM_IN_DESC = 21;
    public static final int CARD_BOTTOM_IN_ASC = 22;

    public static final int ANIM_DURATION = 300;
    public static final float ANIM_INTERPOLATION_FACTOR = 0.3f;

    protected static final float ANIM_SCALE_FACTOR_MIN = 0.5f;
    protected static final float ANIM_SCALE_FACTOR_ORIGINAL = 1.0f;
    protected static final float ANIM_SCALE_FACTOR_MAX = 1.25f;

    protected static final float ANIM_ALPHA_MIN = 0f;
    protected static final float ANIM_ALPHA_MAX = 1.0f;

    /**
     *
     * @param view
     * @param xInit
     * @param xFinal
     * @param factor
     * @param duration
     * @param <V>
     */
    protected static <V extends View>void itemAnimFromXDesc(V view, float xInit, float xFinal, float factor, int duration){
        view.setTranslationX(xInit);
        view.animate()
                .translationX(xFinal)
                .setInterpolator(new DecelerateInterpolator(factor))
                .setDuration(duration)
                .start();
    }

    /**
     *
     * @param view
     * @param xInit
     * @param xFinal
     * @param factor
     * @param duration
     * @param <V>
     */
    protected static <V extends View>void itemAnimFromXAsc(V view, float xInit, float xFinal, float factor, int duration){
        view.setTranslationX(xInit);
        view.animate()
                .translationX(xFinal)
                .setInterpolator(new AccelerateInterpolator(factor))
                .setDuration(duration)
                .start();
    }

    /**
     *
     * @param view
     * @param yInit
     * @param yFinal
     * @param factor
     * @param duration
     * @param <V>
     */
    protected static <V extends View>void itemAnimFromYDesc(V view, float yInit, float yFinal, float factor, int duration){
        view.setTranslationY(yInit);
        view.animate()
                .translationY(yFinal)
                .setInterpolator(new DecelerateInterpolator(factor))
                .setDuration(duration)
                .start();
    }

    /**
     *
     * @param view
     * @param yInit
     * @param yFinal
     * @param factor
     * @param duration
     * @param <V>
     */
    protected static <V extends View>void itemAnimFromYAsc(V view, float yInit, float yFinal, float factor, int duration){
        view.setTranslationY(yInit);
        view.animate()
                .translationY(yFinal)
                .setInterpolator(new AccelerateInterpolator(factor))
                .setDuration(duration)
                .start();
    }

    /**
     *
     * @param view
     * @param scaleInitial
     * @param scaleFinal
     * @param factor
     * @param duration
     * @param <V>
     */
    protected static <V extends View>void itemAnimScaleDesc(V view, float scaleInitial, float scaleFinal, float factor, int duration){
        view.setScaleX(scaleInitial);
        view.setScaleY(scaleInitial);
        view.animate()
                .scaleX(scaleFinal)
                .scaleY(scaleFinal)
                .setInterpolator(new DecelerateInterpolator(factor))
                .setDuration(duration)
                .start();
    }

    /**
     *
     * @param view
     * @param scaleInitial
     * @param scaleFinal
     * @param factor
     * @param duration
     * @param <V>
     */
    protected static <V extends View>void itemAnimScaleAsc(V view, float scaleInitial, float scaleFinal, float factor, int duration){
        view.setScaleX(scaleInitial);
        view.setScaleY(scaleInitial);
        view.animate()
                .scaleX(scaleFinal)
                .scaleY(scaleFinal)
                .setInterpolator(new AccelerateInterpolator(factor))
                .setDuration(duration)
                .start();
    }

    /**
     *
     * @param view
     * @param alphaInitial
     * @param alphaFinal
     * @param factor
     * @param duration
     * @param <V>
     */
    protected static <V extends View>void itemAnimFadeDesc(V view, float alphaInitial, float alphaFinal, float factor, int duration){
        view.setAlpha(alphaInitial);
        view.animate()
                .alpha(alphaFinal)
                .setInterpolator(new DecelerateInterpolator(factor))
                .setDuration(duration)
                .start();
    }

    /**
     *
     * @param view
     * @param alphaInitial
     * @param alphaFinal
     * @param factor
     * @param duration
     * @param <V>
     */
    protected static <V extends View>void itemAnimFadeAsc(V view, float alphaInitial, float alphaFinal, float factor, int duration){
        view.setAlpha(alphaInitial);
        view.animate()
                .alpha(alphaFinal)
                .setInterpolator(new AccelerateInterpolator(factor))
                .setDuration(duration)
                .start();
    }
}
