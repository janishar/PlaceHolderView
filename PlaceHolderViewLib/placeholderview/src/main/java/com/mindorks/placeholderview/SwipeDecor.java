package com.mindorks.placeholderview;

import android.view.Gravity;

/**
 * Created by janisharali on 26/08/16.
 */
public class SwipeDecor {

    public static final int PRIMITIVE_NULL = -1;

    private int mPaddingTop;
    private int mPaddingLeft;
    private float mRelativeScale;
    private boolean mAnimateScale;
    private int mSwipeInMsgLayoutId;
    private int mSwipeOutMsgLayoutId;
    private int mSwipeInMsgGravity;
    private int mSwipeOutMsgGravity;
    private int mSwipeDistToDisplayMsg;
    private int mSwipeAnimTime;
    private float mSwipeAnimFactor;

    public SwipeDecor() {
        mPaddingTop = 0;
        mPaddingLeft = 0;
        mRelativeScale = 0.05f;
        mAnimateScale = true;
        mSwipeInMsgLayoutId = PRIMITIVE_NULL;
        mSwipeOutMsgLayoutId = PRIMITIVE_NULL;
        mSwipeInMsgGravity = Gravity.CENTER ;
        mSwipeOutMsgGravity = Gravity.CENTER ;
        mSwipeDistToDisplayMsg = 30;
        mSwipeAnimTime = 200;
        mSwipeAnimFactor = 0.75f;
    }

    public SwipeDecor setPaddingTop(int padding){
        mPaddingTop = padding;
        return this;
    }

    public SwipeDecor setPaddingLeft(int padding){
        mPaddingLeft = padding;
        return this;
    }

    public SwipeDecor setSwipeInMsgLayoutId(int layoutId){
        if(layoutId < 0){
            layoutId = PRIMITIVE_NULL;
        }
        mSwipeInMsgLayoutId = layoutId;
        return this;
    }

    public SwipeDecor setSwipeOutMsgLayoutId(int layoutId){
        if(layoutId < 0){
            layoutId = PRIMITIVE_NULL;
        }
        mSwipeOutMsgLayoutId = layoutId;
        return this;
    }

    public SwipeDecor setSwipeInMsgGravity(int gravity){
        mSwipeInMsgGravity = gravity;
        return this;
    }

    public SwipeDecor setSwipeOutMsgGravity(int gravity){
        mSwipeOutMsgGravity = gravity;
        return this;
    }

    public SwipeDecor setRelativeScale(float scale){
        if(scale > 1){
            scale = 1;
        }else if(scale < 0){
            scale = 1;
        }
        mRelativeScale = scale;
        return this;
    }

    public SwipeDecor isAnimateScale(boolean animate){
        mAnimateScale = animate;
        return this;
    }

    public SwipeDecor setSwipeDistToDisplayMsg(int swipeDistToDisplayMsg) {
        mSwipeDistToDisplayMsg = swipeDistToDisplayMsg;
        return this;
    }

    public SwipeDecor setSwipeAnimTime(int millis) {
        mSwipeAnimTime = millis;
        return this;
    }

    public SwipeDecor setSwipeAnimFactor(float factor) {
        mSwipeAnimFactor = factor;
        return this;
    }

    public int getPaddingTop() {
        return mPaddingTop;
    }

    public int getPaddingLeft() {
        return mPaddingLeft;
    }

    public float getRelativeScale() {
        return mRelativeScale;
    }

    public boolean isAnimateScale() {
        return mAnimateScale;
    }

    public int getSwipeInMsgLayoutId() {
        return mSwipeInMsgLayoutId;
    }

    public int getSwipeOutMsgLayoutId() {
        return mSwipeOutMsgLayoutId;
    }

    public int getSwipeInMsgGravity() {
        return mSwipeInMsgGravity;
    }

    public int getSwipeOutMsgGravity() {
        return mSwipeOutMsgGravity;
    }

    public int getSwipeDistToDisplayMsg() {
        return mSwipeDistToDisplayMsg;
    }

    public float getSwipeAnimFactor() {
        return mSwipeAnimFactor;
    }

    public int getSwipeAnimTime() {
        return mSwipeAnimTime;
    }
}
