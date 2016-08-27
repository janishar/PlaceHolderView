package com.mindorks.placeholderview;

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
    private boolean mSwipeInMsgAttachEnds;
    private boolean mSwipeInMsgAttachCorners;
    private boolean mSwipeInMsgAttachCenters;

    public SwipeDecor() {
        mPaddingTop = 0;
        mPaddingLeft = 0;
        mRelativeScale = 0.05f;
        mAnimateScale = true;
        mSwipeInMsgLayoutId = PRIMITIVE_NULL;
        mSwipeOutMsgLayoutId = PRIMITIVE_NULL;
        mSwipeInMsgAttachEnds = false;
        mSwipeInMsgAttachCorners = false;
        mSwipeInMsgAttachCenters = false;
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

    public SwipeDecor setSwipeInMsgAttachEnds(boolean value){
        mSwipeInMsgAttachEnds = value;
        return this;
    }

    public SwipeDecor setSwipeInMsgAttachCornerss(boolean value){
        mSwipeInMsgAttachCorners = value;
        return this;
    }

    public SwipeDecor setSwipeInMsgAttachCenters(boolean value){
        mSwipeInMsgAttachCenters = value;
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

    public boolean isSwipeInMsgAttachEnds() {
        return mSwipeInMsgAttachEnds;
    }

    public boolean isSwipeInMsgAttachCorners() {
        return mSwipeInMsgAttachCorners;
    }

    public boolean isSwipeInMsgAttachCenters() {
        return mSwipeInMsgAttachCenters;
    }
}
