package com.mindorks.placeholderview;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by janisharali on 20/08/16.
 */
public class Animation {

    protected static <V extends View>void itemAnimFromXDesc(V view, float xInit, float xFinal, float factor, int duration){
        view.setTranslationX(xInit);
        view.animate()
                .translationX(xFinal)
                .setInterpolator(new DecelerateInterpolator(factor))
                .setDuration(duration)
                .start();
    }

    protected static <V extends View>void itemAnimFromXAsc(V view, float xInit, float xFinal, float factor, int duration){
        view.setTranslationX(xInit);
        view.animate()
                .translationX(xFinal)
                .setInterpolator(new AccelerateInterpolator(factor))
                .setDuration(duration)
                .start();
    }

    protected static <V extends View>void itemAnimFromYDesc(V view, float yInit, float yFinal, float factor, int duration){
        view.setTranslationY(yInit);
        view.animate()
                .translationY(yFinal)
                .setInterpolator(new DecelerateInterpolator(factor))
                .setDuration(duration)
                .start();
    }

    protected static <V extends View>void itemAnimFromYAsc(V view, float yInit, float yFinal, float factor, int duration){
        view.setTranslationY(yInit);
        view.animate()
                .translationY(yFinal)
                .setInterpolator(new AccelerateInterpolator(factor))
                .setDuration(duration)
                .start();
    }

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

    protected static <V extends View>void itemAnimFadeDesc(V view, float alphaInitial, float alphaFinal, float factor, int duration){
        view.setAlpha(alphaInitial);
        view.animate()
                .alpha(alphaFinal)
                .setInterpolator(new DecelerateInterpolator(factor))
                .setDuration(duration)
                .start();
    }

    protected static <V extends View>void itemAnimFadeAsc(V view, float alphaInitial, float alphaFinal, float factor, int duration){
        view.setAlpha(alphaInitial);
        view.animate()
                .alpha(alphaFinal)
                .setInterpolator(new AccelerateInterpolator(factor))
                .setDuration(duration)
                .start();
    }
}
