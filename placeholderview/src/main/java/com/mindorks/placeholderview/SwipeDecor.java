package com.mindorks.placeholderview;

import android.view.Gravity;

/**
 * Created by janisharali on 26/08/16.
 */
public class SwipeDecor {

    public static final int PRIMITIVE_NULL = -1;

    private int mViewWidth;
    private int mViewHeight;
    private int mViewGravity;
    private int mPaddingTop;
    private int mPaddingLeft;
    private int mMarginTop;
    private int mMarginLeft;
    private float mRelativeScale;
    private boolean mAnimateScale;
    private int mSwipeInMsgLayoutId;
    private int mSwipeOutMsgLayoutId;
    private int mSwipeInMsgGravity;
    private int mSwipeOutMsgGravity;
    private int mSwipeDistToDisplayMsg;
    private int mSwipeAnimTime;
    private float mSwipeAnimFactor;
    private int mSwipeRotationAngle;
    private float mSwipeMaxChangeAngle;

    public SwipeDecor() {
        mViewWidth = 0;
        mViewHeight = 0;
        mPaddingTop = 0;
        mPaddingLeft = 0;
        mMarginTop = 0;
        mMarginLeft = 0;
        mRelativeScale = 0.05f;
        mAnimateScale = true;
        mSwipeInMsgLayoutId = PRIMITIVE_NULL;
        mSwipeOutMsgLayoutId = PRIMITIVE_NULL;
        mViewGravity = Gravity.CENTER ;
        mSwipeInMsgGravity = Gravity.CENTER ;
        mSwipeOutMsgGravity = Gravity.CENTER ;
        mSwipeDistToDisplayMsg = 30;
        mSwipeAnimTime = 200;
        mSwipeAnimFactor = 0.75f;
        mSwipeRotationAngle = 15;
        mSwipeMaxChangeAngle = 2f;
    }

    /**
     * @param animate
     * @return
     */
    public SwipeDecor isAnimateScale(boolean animate) {
        mAnimateScale = animate;
        return this;
    }

    /**
     *
     * @return
     */
    public int getPaddingTop() {
        return mPaddingTop;
    }

    /**
     *
     * @param padding
     * @return
     */
    public SwipeDecor setPaddingTop(int padding){
        mPaddingTop = padding;
        return this;
    }

    /**
     *
     * @return
     */
    public int getPaddingLeft() {
        return mPaddingLeft;
    }

    /**
     *
     * @param padding
     * @return
     */
    public SwipeDecor setPaddingLeft(int padding){
        mPaddingLeft = padding;
        return this;
    }

    public int getMarginLeft() {
        return mMarginLeft;
    }

    public SwipeDecor setMarginLeft(int margin){
        mMarginLeft = margin;
        return this;
    }

    public int getMarginTop() {
        return mMarginTop;
    }

    public SwipeDecor setMarginTop(int margin){
        mMarginTop = margin;
        return this;
    }

    /**
     *
     * @return
     */
    public float getRelativeScale() {
        return mRelativeScale;
    }

    /**
     *
     * @param scale
     * @return
     */
    public SwipeDecor setRelativeScale(float scale){
        if(scale > 1){
            scale = 1;
        }else if(scale < 0){
            scale = 1;
        }
        mRelativeScale = scale;
        return this;
    }

    /**
     *
     * @return
     */
    public boolean isAnimateScale() {
        return mAnimateScale;
    }

    /**
     *
     * @return
     */
    public int getSwipeInMsgLayoutId() {
        return mSwipeInMsgLayoutId;
    }

    /**
     *
     * @param layoutId
     * @return
     */
    public SwipeDecor setSwipeInMsgLayoutId(int layoutId) {
        if (layoutId < 0) {
            layoutId = PRIMITIVE_NULL;
        }
        mSwipeInMsgLayoutId = layoutId;
        return this;
    }

    /**
     *
     * @return
     */
    public int getSwipeOutMsgLayoutId() {
        return mSwipeOutMsgLayoutId;
    }

    /**
     *
     * @param layoutId
     * @return
     */
    public SwipeDecor setSwipeOutMsgLayoutId(int layoutId) {
        if (layoutId < 0) {
            layoutId = PRIMITIVE_NULL;
        }
        mSwipeOutMsgLayoutId = layoutId;
        return this;
    }

    /**
     *
     * @return
     */
    public int getSwipeInMsgGravity() {
        return mSwipeInMsgGravity;
    }

    /**
     *
     * @param gravity
     * @return
     */
    public SwipeDecor setSwipeInMsgGravity(int gravity) {
        mSwipeInMsgGravity = gravity;
        return this;
    }

    /**
     *
     * @return
     */
    public int getSwipeOutMsgGravity() {
        return mSwipeOutMsgGravity;
    }

    /**
     *
     * @param gravity
     * @return
     */
    public SwipeDecor setSwipeOutMsgGravity(int gravity) {
        mSwipeOutMsgGravity = gravity;
        return this;
    }

    /**
     *
     * @return
     */
    public int getSwipeDistToDisplayMsg() {
        return mSwipeDistToDisplayMsg;
    }

    /**
     *
     * @param swipeDistToDisplayMsg
     * @return
     */
    public SwipeDecor setSwipeDistToDisplayMsg(int swipeDistToDisplayMsg) {
        mSwipeDistToDisplayMsg = swipeDistToDisplayMsg;
        return this;
    }

    /**
     *
     * @return
     */
    public float getSwipeAnimFactor() {
        return mSwipeAnimFactor;
    }

    /**
     *
     * @param factor
     * @return
     */
    public SwipeDecor setSwipeAnimFactor(float factor) {
        mSwipeAnimFactor = factor;
        return this;
    }

    /**
     *
     * @return
     */
    public int getSwipeAnimTime() {
        return mSwipeAnimTime;
    }

    /**
     *
     * @param millis
     * @return
     */
    public SwipeDecor setSwipeAnimTime(int millis) {
        mSwipeAnimTime = millis;
        return this;
    }

    /**
     *
     * @return
     */
    public int getSwipeRotationAngle() {
        return mSwipeRotationAngle;
    }

    /**
     *
     * @param degree
     * @return
     */
    public SwipeDecor setSwipeRotationAngle(int degree) {
        mSwipeRotationAngle = degree;
        return this;
    }

    public int getViewWidth() {
        return mViewWidth;
    }

    public SwipeDecor setViewWidth(int width) {
        mViewWidth = width;
        return this;
    }

    public int getViewHeight() {
        return mViewHeight;
    }

    public SwipeDecor setViewHeight(int height) {
        mViewHeight = height;
        return this;
    }

    public int getViewGravity() {
        return mViewGravity;
    }

    public SwipeDecor setViewGravity(int gravity) {
        mViewGravity = gravity;
        return this;
    }

    public float getSwipeMaxChangeAngle() {
        return mSwipeMaxChangeAngle;
    }

    public SwipeDecor setSwipeMaxChangeAngle(float maxChangeAngle) {
        mSwipeMaxChangeAngle = maxChangeAngle;
        return this;
    }
}
