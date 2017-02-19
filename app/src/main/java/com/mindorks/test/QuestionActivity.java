package com.mindorks.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

public class QuestionActivity extends AppCompatActivity {

    SwipePlaceHolderView mCardsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        mCardsContainer = (SwipePlaceHolderView) findViewById(R.id.cards_container);

        mCardsContainer.getBuilder()
//                .setDisplayViewCount(1)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f));

        mCardsContainer.addView(new CardPresenter("QUESTION1"));
        mCardsContainer.addView(new CardPresenter("QUESTION2"));
        mCardsContainer.addView(new CardPresenter("QUESTION3"));
    }

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
        private void onResolved() {
            mTextView.setText(this.question);
        }
    }
}
