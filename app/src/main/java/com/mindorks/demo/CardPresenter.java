package com.mindorks.demo;

import android.util.Log;
import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

/**
 * Created by janisharali on 25/02/18.
 */
@NonReusable
@Layout(R.layout.card_layout)
public class CardPresenter {
    @View(R.id.question_caption)
    TextView mTextView;

    String question;

    public CardPresenter(String question) {
        this.question = question;
    }

    @Click(R.id.btn_answer1)
    public void onClickQ1() {
        String message = String.format(
                "The question is: %s. The answer is: %s.",
                this.question,
                "ANSWER1");
        Log.i("PlaceholderView", message);
        mTextView.setText(message);
    }

    @Click(R.id.btn_answer2)
    public void onClickQ2() {
        String message = String.format(
                "The question is: %s. The answer is: %s.",
                this.question,
                "ANSWER2");
        Log.i("PlaceholderView", message);
        mTextView.setText(message);
    }

    @Click(R.id.btn_answer3)
    public void onClickQ3() {
        String message = String.format(
                "The question is: %s. The answer is: %s.",
                this.question,
                "ANSWER3");
        Log.i("PlaceholderView", message);
        mTextView.setText(message);
    }

    @Resolve
    public void onResolved() {
        mTextView.setText(this.question);
    }
}
