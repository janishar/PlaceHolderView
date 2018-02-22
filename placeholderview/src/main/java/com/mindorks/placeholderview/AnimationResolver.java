package com.mindorks.placeholderview;

import android.view.View;

import com.mindorks.placeholderview.annotations.Animate;

/**
 * Created by janisharali on 20/08/16.
 */

public class AnimationResolver<T, V extends View> {

    protected void bindAnimation(int deviceWidth, int deviceHeight,T resolver, V view){
        Animate animate = resolver.getClass().getAnnotation(Animate.class);
        if(animate != null) {
            switch (animate.value()){
                case Animate.ENTER_LEFT_DESC:
                    Animation.itemAnimFromXDesc(view, deviceWidth, view.getTranslationX(), animate.factor(), animate.duration());
                    break;
                case Animate.ENTER_LEFT_ASC:
                    Animation.itemAnimFromXAsc(view, deviceWidth, view.getTranslationX(), animate.factor(), animate.duration());
                    break;
                case Animate.ENTER_RIGHT_DESC:
                    Animation.itemAnimFromXDesc(view, -deviceWidth, view.getTranslationX(), animate.factor(), animate.duration());
                    break;
                case Animate.ENTER_RIGHT_ASC:
                    Animation.itemAnimFromXAsc(view, -deviceWidth, view.getTranslationX(), animate.factor(), animate.duration());
                    break;
                case Animate.ENTER_TOP_DESC:
                    Animation.itemAnimFromYDesc(view, -view.getHeight(), view.getTranslationY(), animate.factor(), animate.duration());
                    break;
                case Animate.ENTER_TOP_ASC:
                    Animation.itemAnimFromYAsc(view, -view.getHeight(), view.getTranslationY(), animate.factor(), animate.duration());
                    break;
                case Animate.ENTER_BOTTOM_DESC:
                    Animation.itemAnimFromYDesc(view, deviceHeight, view.getTranslationY(), animate.factor(), animate.duration());
                    break;
                case Animate.ENTER_BOTTOM_ASC:
                    Animation.itemAnimFromXAsc(view, deviceHeight, view.getTranslationY(), animate.factor(), animate.duration());
                    break;
                case Animate.SCALE_UP_DESC:
                    Animation.itemAnimScaleDesc(view, Animate.ANIM_SCALE_FACTOR_MIN, Animate.ANIM_SCALE_FACTOR_ORIGINAL, animate.factor(), animate.duration());
                    break;
                case Animate.SCALE_UP_ASC:
                    Animation.itemAnimScaleAsc(view, Animate.ANIM_SCALE_FACTOR_MIN, Animate.ANIM_SCALE_FACTOR_ORIGINAL, animate.factor(), animate.duration());
                    break;
                case Animate.SCALE_DOWN_DESC:
                    Animation.itemAnimScaleDesc(view, Animate.ANIM_SCALE_FACTOR_MAX, Animate.ANIM_SCALE_FACTOR_ORIGINAL, animate.factor(), animate.duration());
                    break;
                case Animate.SCALE_DOWN_ASC:
                    Animation.itemAnimScaleAsc(view, Animate.ANIM_SCALE_FACTOR_MAX, Animate.ANIM_SCALE_FACTOR_ORIGINAL, animate.factor(), animate.duration());
                    break;
                case Animate.FADE_IN_DESC:
                    Animation.itemAnimFadeDesc(view, Animate.ANIM_ALPHA_MIN, Animate.ANIM_ALPHA_MAX, animate.factor(), animate.duration());
                    break;
                case Animate.FADE_IN_ASC:
                    Animation.itemAnimFadeAsc(view, Animate.ANIM_ALPHA_MIN, Animate.ANIM_ALPHA_MAX, animate.factor(), animate.duration());
                    break;
                case Animate.CARD_LEFT_IN_DESC:
                    Animation.itemAnimFromXDesc(view, view.getTranslationX() + view.getWidth() / 2f, view.getTranslationX(), animate.factor(), animate.duration());
                    break;
                case Animate.CARD_LEFT_IN_ASC:
                    Animation.itemAnimFromXAsc(view, view.getTranslationX() + view.getWidth() / 2f, view.getTranslationX(), animate.factor(), animate.duration());
                    break;
                case Animate.CARD_RIGHT_IN_DESC:
                    Animation.itemAnimFromXDesc(view, view.getTranslationX() - view.getWidth() / 2f, view.getTranslationX(), animate.factor(), animate.duration());
                    break;
                case Animate.CARD_RIGHT_IN_ASC:
                    Animation.itemAnimFromXAsc(view, view.getTranslationX() - view.getWidth() / 2f, view.getTranslationX(), animate.factor(), animate.duration());
                    break;
                case Animate.CARD_TOP_IN_DESC:
                    Animation.itemAnimFromYDesc(view, view.getTranslationY() - view.getHeight() / 2f, view.getTranslationX(), animate.factor(), animate.duration());
                    break;
                case Animate.CARD_TOP_IN_ASC:
                    Animation.itemAnimFromYAsc(view, view.getTranslationY() - view.getHeight() / 2f, view.getTranslationX(), animate.factor(), animate.duration());
                    break;
                case Animate.CARD_BOTTOM_IN_DESC:
                    Animation.itemAnimFromYDesc(view, view.getTranslationY() + view.getHeight() / 2f, view.getTranslationX(), animate.factor(), animate.duration());
                    break;
                case Animate.CARD_BOTTOM_IN_ASC:
                    Animation.itemAnimFromYAsc(view, view.getTranslationX() + view.getHeight() / 2f, view.getTranslationX(), animate.factor(), animate.duration());
                    break;
            }

        }
    }

}
