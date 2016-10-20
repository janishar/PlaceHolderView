package com.mindorks.placeholderview;

import android.view.Gravity;

import java.util.concurrent.atomic.AtomicBoolean;

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
    private AtomicBoolean mIsViewLocked;
    private AtomicBoolean mIsPutBackActive;
    private AtomicBoolean mIsViewToRestoreOnLock;
    private AtomicBoolean mIsViewToRestoreOnTouchLock;
    private AtomicBoolean mIsTouchSwipeLocked;

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
        mIsViewLocked = new AtomicBoolean(false);
        mIsPutBackActive = new AtomicBoolean(false);
        mIsViewToRestoreOnLock = new AtomicBoolean(true);
        mIsViewToRestoreOnTouchLock = new AtomicBoolean(true);
        mIsTouchSwipeLocked = new AtomicBoolean(false);
    }

    public SwipeDecor setViewWidth(int width){
        mViewWidth = width;
        return this;
    }

    public SwipeDecor setViewHeight(int height){
        mViewHeight = height;
        return this;
    }

    public SwipeDecor setViewGravity(int gravity){
        mViewGravity = gravity;
        return this;
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
     * @param padding
     * @return
     */
    public SwipeDecor setPaddingLeft(int padding){
        mPaddingLeft = padding;
        return this;
    }

    public SwipeDecor setMarginLeft(int margin){
        mMarginLeft = margin;
        return this;
    }

    public SwipeDecor setMarginTop(int margin){
        mMarginTop = margin;
        return this;
    }

    /**
     *
     * @param layoutId
     * @return
     */
    public SwipeDecor setSwipeInMsgLayoutId(int layoutId){
        if(layoutId < 0){
            layoutId = PRIMITIVE_NULL;
        }
        mSwipeInMsgLayoutId = layoutId;
        return this;
    }

    /**
     *
     * @param layoutId
     * @return
     */
    public SwipeDecor setSwipeOutMsgLayoutId(int layoutId){
        if(layoutId < 0){
            layoutId = PRIMITIVE_NULL;
        }
        mSwipeOutMsgLayoutId = layoutId;
        return this;
    }

    /**
     *
     * @param gravity
     * @return
     */
    public SwipeDecor setSwipeInMsgGravity(int gravity){
        mSwipeInMsgGravity = gravity;
        return this;
    }

    /**
     *
     * @param gravity
     * @return
     */
    public SwipeDecor setSwipeOutMsgGravity(int gravity){
        mSwipeOutMsgGravity = gravity;
        return this;
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
     * @param animate
     * @return
     */
    public SwipeDecor isAnimateScale(boolean animate){
        mAnimateScale = animate;
        return this;
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
     * @param millis
     * @return
     */
    public SwipeDecor setSwipeAnimTime(int millis) {
        mSwipeAnimTime = millis;
        return this;
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
     * @param degree
     * @return
     */
    public SwipeDecor setSwipeRotationAngle(int degree) {
        mSwipeRotationAngle = degree;
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
     * @return
     */
    public int getPaddingLeft() {
        return mPaddingLeft;
    }

    public int getMarginLeft() {
        return mMarginLeft;
    }

    public int getMarginTop() {
        return mMarginTop;
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
     * @return
     */
    public int getSwipeOutMsgLayoutId() {
        return mSwipeOutMsgLayoutId;
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
     * @return
     */
    public int getSwipeOutMsgGravity() {
        return mSwipeOutMsgGravity;
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
     * @return
     */
    public float getSwipeAnimFactor() {
        return mSwipeAnimFactor;
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
     * @return
     */
    public int getSwipeRotationAngle() {
        return mSwipeRotationAngle;
    }

    public int getViewWidth() {
        return mViewWidth;
    }

    public int getViewHeight() {
        return mViewHeight;
    }

    public int getViewGravity() {
        return mViewGravity;
    }

    protected boolean getIsViewLocked() {
        return mIsViewLocked.get();
    }

    protected void setIsViewLocked(boolean isViewLocked) {
        this.mIsViewToRestoreOnLock.set(true);
        this.mIsViewLocked.set(isViewLocked);
    }

    protected boolean getIsPutBackActive() {
        return mIsPutBackActive.get();
    }

    protected void setIsPutBackActive(boolean isPutBackActive) {
        this.mIsPutBackActive.set(isPutBackActive);
    }

    protected boolean getIsViewToRestoredOnLock() {
        return mIsViewToRestoreOnLock.get();
    }

    protected void setIsViewToRestoredOnLock(boolean isViewToRestoredOnLock) {
        this.mIsViewToRestoreOnLock.set(isViewToRestoredOnLock);
    }

    protected boolean getIsViewToRestoreOnTouchLock() {
        return mIsViewToRestoreOnTouchLock.get();
    }

    protected void setIsViewToRestoreOnTouchLock(boolean isViewToRestoreOnTouchLock) {
        this.mIsViewToRestoreOnTouchLock.set(isViewToRestoreOnTouchLock);
    }

    protected boolean getIsTouchSwipeLocked(){
        return mIsTouchSwipeLocked.get();
    }

    protected void setIsTouchSwipeLocked(boolean locked){
        this.mIsViewToRestoreOnTouchLock.set(true);
        mIsTouchSwipeLocked.set(locked);
    }
}
