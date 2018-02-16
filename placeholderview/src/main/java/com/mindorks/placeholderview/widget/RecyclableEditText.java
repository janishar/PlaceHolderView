package com.mindorks.placeholderview.widget;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by janisharali on 16/02/18.
 */

public class RecyclableEditText extends EditText {

    private OnTextChangeListener mOnTextChangeListener;

    public RecyclableEditText(Context context) {
        super(context);
    }

    public RecyclableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (mOnTextChangeListener != null) {
            mOnTextChangeListener.onTextChanged(text, start, lengthBefore, lengthAfter);
        }
    }

    public void setTextChangedListener(@NonNull OnTextChangeListener listener) {
        mOnTextChangeListener = listener;
    }

    public void removeTextChangedListener() {
        mOnTextChangeListener = null;
    }

    public interface OnTextChangeListener {
        void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter);
    }
}
