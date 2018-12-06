package com.nickrman.alias.screens.card;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


import com.nickrman.alias.services.Navigator;
import com.nickrman.alias.services.navigation.BackNavigator;

import io.reactivex.Observable;

public interface CardContract {

    interface View {

        Observable<Object> flipCard();

        Observable<MotionEvent> currentCard();

        void startAnimation();


        void acceptCard();

        void dismissCard();

        void startTextVisibility(boolean visibility);

    }

    interface Presenter {

        void start();

        void stop();

        void setupView();

        void setupAction();

        void setNavigator(Navigator navigator);

        void setBackNavigator(BackNavigator backNavigator);

        void startAnimation();

        void startFlipAnimation();
    }
}
