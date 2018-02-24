package com.mindorks.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

public class QuestionActivity extends AppCompatActivity {

    SwipePlaceHolderView mCardsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        mCardsContainer = findViewById(R.id.cards_container);

        mCardsContainer.getBuilder()
//                .setDisplayViewCount(1)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f));

        mCardsContainer.addView(new CardPresenter("QUESTION1"));
        mCardsContainer.addView(new CardPresenter("QUESTION2"));
        mCardsContainer.addView(new CardPresenter("QUESTION3"));
    }

}
